package com.example.demo;
import com.example.demo.models.Note
import com.example.demo.repositories.NoteRepository
import com.example.demo.sevices.NotesService
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.BeforeEach
import org.mockito.Mockito.*
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class NotesServiceTest {

    @Mock
    lateinit var noteRepository: NoteRepository

    @InjectMocks
    lateinit var notesService: NotesService

    @BeforeEach
    fun setUp() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun `test getAllNotes returns list`() {
        val notesList = listOf(Note(id=1, text="Nota 1"), Note(id=2, text="Nota 2"))
        `when`(noteRepository.findAll()).thenReturn(notesList)

        val result = notesService.getAllNotes()

        assertNotNull(result)
        assertEquals(2, result.size)
        verify(noteRepository).findAll()
    }

    @Test
    fun `test saveNote saves correctly`() {
        val note = Note(id=1, text="Nueva nota")
        `when`(noteRepository.save(note)).thenReturn(note)

        val savedNote = notesService.saveNote(note)

        assertNotNull(savedNote)
        assertEquals(note.id, savedNote.id)
        verify(noteRepository).save(note)
    }
}