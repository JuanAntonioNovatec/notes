package com.example.demo.models
import jakarta.persistence.*
import java.time.LocalDateTime
@Entity
class Note {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
    var text: String? = null

    constructor(id: Long?, text: String?) {
        this.id = id
        this.text = text
    }
}