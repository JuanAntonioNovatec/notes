package com.example.demo.sevices

import com.example.demo.models.AuthenticationRequest
import com.example.demo.models.User
import com.example.demo.security.JwtUtil
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import java.sql.DriverManager
import org.springframework.security.crypto.password.PasswordEncoder


@Service
class AuthService(
    private val passwordEncoder: PasswordEncoder,
    private val jwtUtil: JwtUtil
) : BaseUsersService() {

    /**
     * Ad a new user to the database with the password hashed by security reasons.
     */
    fun login(authRequest: AuthenticationRequest):
            ResponseEntity<Any>
    {
        DriverManager.println("AuthRequest: $authRequest")
        val userOpt = userRepository.findByUsername(authRequest.username ?: "")

        DriverManager.println("User: $userOpt")

        if (userOpt.isEmpty) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(mapOf("error" to "Usuario no encontrado"))
        }

        val user = userOpt.get()

        DriverManager.println("Usuario: ${user.username}")

        // Verifica la contraseña ingresada con la almacenada (hasheada)
        val passwordMatches = passwordEncoder.matches(
            (authRequest.password ?: "") as CharSequence?,
            user.password ?: ""
        )

        if (!passwordMatches) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(mapOf("error" to "Contraseña incorrecta"))
        }

        val jwt = jwtUtil.generateToken(user.username!!)

        DriverManager.println("jwt: $jwt")
        val response = mapOf(
            "token" to jwt,
            "role" to user.role
        )

        return ResponseEntity.ok(response)}

    fun getAllUsers(): List<User> = userRepository.findAll()





}