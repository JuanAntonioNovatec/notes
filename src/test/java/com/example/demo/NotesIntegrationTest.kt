import com.example.demo.models.Note
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.OutputStream
import java.net.HttpURLConnection
import java.net.URL
import java.util.List
import kotlin.test.assertTrue


public class NotesIntegrationTest {

	private val objectMapper = jacksonObjectMapper()
	@Test
	fun testHelloWorldEndpoint() {
		val urlString = "http://localhost:8080/api/hi"
		val url = URL(urlString)
		val connection = url.openConnection() as HttpURLConnection
		connection.requestMethod = "GET"

		// Check 200 status code
		assertEquals(200, connection.responseCode)

		// Leer la respuesta
		val response = connection.inputStream.bufferedReader().use { it.readText() }

		// Comprobar la respuesta
		assertEquals("Hello, World!", response)
	}

	@Test
	fun testUnexistentEndpoint() {
		val urlString = "http://localhost:8080/api/unexistent" // Asegúrate del puerto correcto
		val url = URL(urlString)
		val connection = url.openConnection() as HttpURLConnection
		connection.requestMethod = "GET"

		// Comprobar que el código de respuesta es 404
		assertEquals(404, connection.responseCode)
	}

	@Test
	fun testPostAndGetANote() {
		val postUrl = "http://localhost:8080/api/notes"
		val getUrl = "http://localhost:8080/api/notes"

		// Paso 1: Realizar el POST
		val messageToPost = """{"text": "taza"}""" // JSON
		val url = URL(postUrl)
		val postCon = url.openConnection() as HttpURLConnection
		postCon.requestMethod = "POST"
		postCon.setRequestProperty("Content-Type", "application/json")
		postCon.doOutput = true

		// Enviar datos
		val os: OutputStream = postCon.outputStream
		os.use {
			it.write(messageToPost.toByteArray(Charsets.UTF_8))
		}

		assertEquals(200, postCon.responseCode)

		// Paso 2: Realizar el GET
		val getUrlObj = URL(getUrl)
		val getCon = getUrlObj.openConnection() as HttpURLConnection
		getCon.requestMethod = "GET"

		assertEquals(200, getCon.responseCode)

		// Leer la respuesta
		val response = getCon.inputStream.bufferedReader().use { it.readText() }

		// Parsear JSON en lista de Notes
		val messages: List<Note> = objectMapper.readValue(response, object : com.fasterxml.jackson.core.type.TypeReference<List<Note>>() {})

		// Verificar que "taza" esté en los textos
		assertTrue(messages.any { it.text == "taza" }, "El mensaje 'taza' no se encontró en la lista")
	}


	@Test
	fun testPostAndDeleteNote() {
		val postUrl = "http://localhost:8080/api/notes"
		val deleteUrl = "http://localhost:8080/api/notes/"

		val jsonInputString = """{"text": "taza"}"""
		val url = URL(postUrl)
		val postCon = url.openConnection() as HttpURLConnection
		postCon.requestMethod = "POST"
		postCon.setRequestProperty("Content-Type", "application/json")
		postCon.doOutput = true

		// Enviamos el JSON del POST
		postCon.outputStream.use { os ->
			os.write(jsonInputString.toByteArray(Charsets.UTF_8))
		}
		assertEquals(200, postCon.responseCode)

		// Leemos la respuesta del POST para obtener el ID
		val response = postCon.inputStream.bufferedReader().use { it.readText() }
		val objectMapper = jacksonObjectMapper()
		val note: Note = objectMapper.readValue(response, Note::class.java)
		val noteId = note.id

		// Realizar DELETE
		val deleteUrlObj = URL("$deleteUrl$noteId")
		val deleteRequest = deleteUrlObj.openConnection() as HttpURLConnection
		deleteRequest.requestMethod = "DELETE"
		assertEquals(204, deleteRequest.responseCode)
	}
}

