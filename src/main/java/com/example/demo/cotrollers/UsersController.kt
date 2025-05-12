package com.example.demo.controllers

import com.example.demo.cotrollers.AuthenticationRequest
import com.example.demo.models.User
import com.example.demo.repositories.UserRepository
import com.example.demo.security.JwtUtil
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.*
import java.sql.DriverManager.println
import java.time.LocalDateTime
import java.util.*

@RestController
@RequestMapping("/api")
class UsersController(
    private val userRepository: UserRepository,
    private val authenticationManager: AuthenticationManager,
    private val passwordEncoder: PasswordEncoder,
    private val jwtUtil: JwtUtil
) {

    @PostMapping("/users")
    fun addUser(@RequestBody userFromRequest: User): ResponseEntity<out MutableMap<String, out Any?>?> {
        val encoder = BCryptPasswordEncoder()
        val hashedPassword = encoder.encode(userFromRequest.password)

        println("Password antes de hashear: ${userFromRequest.password}")
        println("FirstName: ${userFromRequest.firstName}")
        println("Password hasheada: $hashedPassword")

        val hashedUser = User()
        hashedUser.username = userFromRequest.username
        hashedUser.password = hashedPassword
        hashedUser.firstName = userFromRequest.firstName
        hashedUser.lastName = userFromRequest.lastName
        hashedUser.createdAt = LocalDateTime.now()
        hashedUser.isActive = true
        hashedUser.role = "user"


        println("User: $userFromRequest")
        val newUser = userRepository.save(hashedUser)

        val response = mutableMapOf(
            "id" to newUser.id,
            "username" to newUser.username,
        )
        return ResponseEntity.status(HttpStatus.CREATED).body(response)
    }

    @PostMapping("/login")
    fun login(@RequestBody authRequest: AuthenticationRequest): ResponseEntity<Any> {
        println("AuthRequest: $authRequest")
        val userOpt = userRepository.findByUsername(authRequest.username ?: "")

        println("User: $userOpt")

        if (userOpt.isEmpty) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(mapOf("error" to "Usuario no encontrado"))
        }

        val user = userOpt.get()

        println("Usuario: ${user.username}")

        // Verifica la contraseña ingresada con la almacenada (hasheada)
        val passwordMatches = passwordEncoder.matches(
            (authRequest.password ?: "") as CharSequence?,
            user.password ?: ""
        )

        if (!passwordMatches) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(mapOf("error" to "Contraseña incorrecta"))
        }

        val jwt = jwtUtil.generateToken(user.username!!)

        println("jwt: $jwt")
        val response = mapOf(
            "token" to jwt,
            "role" to user.role
        )

        return ResponseEntity.ok(response)
    }

    @GetMapping("/users")
    fun getUsers(): List<User> = userRepository.findAll()
}