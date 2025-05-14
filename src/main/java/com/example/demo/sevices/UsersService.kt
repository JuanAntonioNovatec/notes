package com.example.demo.sevices

import com.example.demo.models.Note
import com.example.demo.models.User
import com.example.demo.repositories.NoteRepository
import com.example.demo.repositories.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import java.sql.DriverManager
import java.time.LocalDateTime
import com.example.demo.sevices.BaseUsersService
import java.util.Optional


@Service
class UsersService : BaseUsersService() {


    /**
     * Ad a new user to the database with the password hashed by security reasons.
     */
    fun addANewUser(userFromRequest: User):
            ResponseEntity<out MutableMap<String, out Any?>?>
    {
        val encoder = BCryptPasswordEncoder()
        val hashedPassword = encoder.encode(userFromRequest.password)
        val hashedUser = User()
        hashedUser.username = userFromRequest.username
        hashedUser.password = hashedPassword
        hashedUser.firstName = userFromRequest.firstName
        hashedUser.lastName = userFromRequest.lastName
        hashedUser.createdAt = LocalDateTime.now()
        hashedUser.isActive = true
        hashedUser.role = "user"


        DriverManager.println("User: $userFromRequest")
        val newUser = userRepository.save(hashedUser)

        val response = mutableMapOf(
            "id" to newUser.id,
            "username" to newUser.username,
        )
        return ResponseEntity.status(HttpStatus.CREATED).body(response)
    }

    fun getAllUsers(): List<User> = userRepository.findAll()





}