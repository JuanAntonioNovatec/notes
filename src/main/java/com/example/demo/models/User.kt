package com.example.demo.models

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
class User {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    var id: Long? = null

    var username: String? = null
    var password: String? = null
    var firstName: String? = null
    var lastName: String? = null
    var createdAt: LocalDateTime? = null
    var isActive: Boolean? = null
    var role: String? = null
}