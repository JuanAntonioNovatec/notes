package com.example.demo.repositories

import com.example.demo.models.Note
import org.springframework.data.jpa.repository.JpaRepository

interface NoteRepository : JpaRepository<Note, Long>
