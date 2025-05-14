package com.example.demo.controllers

import com.example.demo.models.Note
import com.example.demo.repositories.NoteRepository
import com.example.demo.sevices.NotesService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api")
open class NotesController(
    val noteRepository: NoteRepository,
    private val notesService: NotesService
) {
    @PostMapping("/notes")
    fun addNote(@RequestBody nota: Note): Note =  notesService.saveNote(nota)

    @GetMapping("/notes")
    fun getNotes(): List<Note> = notesService.getAllNotes()

    @PutMapping("/notes/{id}")
    fun updateNote(@PathVariable id: Long, @RequestBody newDetails: Note): ResponseEntity<Note> =
        notesService.updateNote(id, newDetails)

    @DeleteMapping("/notes/{id}")
    fun deleteNote(@PathVariable id: Long):
            ResponseEntity<String> = notesService.deleteNote(id)

    @DeleteMapping("/notes/all")
    fun deleteAllNotes(): ResponseEntity<String> =
        notesService.deleteAllNotes();

}