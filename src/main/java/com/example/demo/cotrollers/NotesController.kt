package com.example.demo.cotrollers;
import com.example.demo.models.Note;
import com.example.demo.repositories.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class NotesController {

    @Autowired
    private NoteRepository noteRepository;

    @GetMapping("/hi")
    public String hi() {
        return "Hello, World!";
    }

    @PostMapping("/notes")
    public Note addNote(@RequestBody Note nota) {
        return noteRepository.save(nota);
    }

    @GetMapping("/notes")
    public List<Note> getNotes() {
        return noteRepository.findAll();
    }


    @PutMapping("/notes/{id}")
    public ResponseEntity<Note> updateNote(@PathVariable Long id, @RequestBody Note newDetails) {
        Optional<Note> notaOpt = noteRepository.findById(id);

        if (notaOpt.isPresent()) {
            Note note = notaOpt.get();
            note.setText(newDetails.getText()); // AUpdate the note
            final Note updatedNote = noteRepository.save(note); // ave updated note
            return ResponseEntity.ok(updatedNote);
        } else {
            return ResponseEntity.notFound().build(); // Return 404 if the note with id is not found
        }
    }

    @DeleteMapping("/notes/{id}")
    public ResponseEntity<String> deleteNote(@PathVariable Long id) {
        Optional<Note> notaOptional = noteRepository.findById(id);
        if (notaOptional.isPresent()) {
            noteRepository.delete(notaOptional.get());
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Note not found, check the id in the url.");
        }
    }

}
