import com.example.demo.models.Note
import com.example.demo.controllers.NotesController
import com.example.demo.cotrollers.TestController
import com.example.demo.sevices.NotesService
import com.example.demo.repositories.NoteRepository

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertNotNull

import org.mockito.Mock
import org.mockito.InjectMocks
import org.mockito.Mockito.mock

import org.mockito.kotlin.verify
import org.mockito.kotlin.times
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

class NotesTest {

    @Mock
    lateinit var noteRepository: NoteRepository

    @Mock
    lateinit var notesService: NotesService
    @Mock
    lateinit var testController: TestController

    lateinit var notesController: NotesController

    private lateinit var closeable: AutoCloseable

    @BeforeEach
    fun setUp() {
        closeable = MockitoAnnotations.openMocks(this)
        // Crear la instancia de NotesController pasando los mocks
        notesController = NotesController(noteRepository, notesService)
    }

    @AfterEach
    fun tearDown() {
        closeable.close()
    }

    @Test
    fun testHiEndpoint() {
        // Crear mock del repositorio (esto ya lo hace @Mock y @InjectMocks)
        // No es necesario crear manualmente el controller, gracias a @InjectMocks
        // pero si quieres hacerlo manualmente:

        val response = testController.hi()
        assertEquals("Hello, World!", response)
    }


    @Test
    fun testAddNote() {
        val note = Note(id = null, text = "Test Note")
        val savedNote = Note(id = 1L, text = "Test Note")

        // Mockea noteRepository.save() para devolver savedNote
        `when`(noteRepository.save(note)).thenReturn(savedNote)


        assertNotNull(notesController)
        println(notesController.noteRepository)
        // Llamada al método del controlador
        val result = notesController.addNote(note)

        // Verificación
        assertEquals("Test Note", result.text)

        // Verificamos que se llamó a save en el repositorio
        verify(noteRepository, times(1)).save(note)

        // También puedes verificar que se llamó a saveNote en el servicio
        verify(notesService, times(1)).saveNote(note)
    }
}