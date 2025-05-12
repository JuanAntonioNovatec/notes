package com.example.demo.sevices

import com.example.demo.models.Note
import com.example.demo.repositories.NoteRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import java.util.Optional


@Service
class NotesService {

    @Autowired
    private lateinit var noteRepository: NoteRepository

    /**
     * Get all notes saved or a empty list if there's not in the database.
     */
    fun getAllNotes(): List<Note> {
        return noteRepository.findAll() ?: emptyList()
    }

    /**
     * Save a new note
     */
    fun saveNote(note: Note): Note {
        return noteRepository.save(note)
    }

    fun updateNote(noteId: Long, newDetails: Note): ResponseEntity<Note> {
        val noteToUpdate: Optional<Note?> = noteRepository.findById(noteId)
        return if (noteToUpdate.isPresent) {
            val note = noteToUpdate.get()
            note.text = newDetails.text
            val updatedNote = noteRepository.save(note)
            ResponseEntity.ok(updatedNote)
        } else {
            ResponseEntity.notFound().build()
        }
    }

    /**
     * Delete a note finding it by his id.
     */
    fun deleteNote(noteId: Long): ResponseEntity<String> {
        val notaOptional = noteRepository.findById(noteId)
        return if (notaOptional.isPresent) {
            noteRepository.delete(notaOptional.get())
            ResponseEntity.noContent().build()
        } else {
            ResponseEntity.status(HttpStatus.NOT_FOUND).body("Note not found, check the id in the url.")
        }
    }
    /**
     * Delete all notes on the database.
     */
    fun deleteAllNotes(): ResponseEntity<String> {
        val count: Long = noteRepository.count()
        return if (count > 0) {
            noteRepository.deleteAll()
            ResponseEntity.ok("{\"message\": \"Removed all notes in the database.\"}")
        } else {
            ResponseEntity.ok("{\"message\": \"There are no notes in the database.\"}")
        }
    }

}