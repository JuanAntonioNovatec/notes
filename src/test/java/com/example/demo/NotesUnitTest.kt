package com.example.demo;

import com.example.demo.controllers.NotesController;
import com.example.demo.models.Note;
import com.example.demo.repositories.NoteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class NotesUnitTest {

    @InjectMocks
    private NotesController noteController;

    @Mock
    private NoteRepository noteRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this); // Inicializa los mocks
    }

    @Test
    public void testHiEndpoint() {
        // Crear mock del repositorio
        NoteRepository mockRepo = mock(NoteRepository.class);
        // Arrange
        NotesController controller = new NotesController(mockRepo);

        // Act
        String response = controller.hi();

        // Assert
        assertEquals("Hello, World!", response);
    }

    @Test
    public void testAddNote() {
        // Arrange: Crea una nota de prueba
        Note note = new Note();
        note.setText("Test Note");

        // Configure the emuated repository
        when(noteRepository.save(note)).thenReturn(note);


        Note createdNote = noteController.addNote(note);


        assertEquals("Test Note", createdNote.getText());
        verify(noteRepository, times(1)).save(note);
    }
}
