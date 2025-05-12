import com.example.demo.models.Note;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class NotesIntegrationTest {

	@Test
	public void testHelloWorldEndpoint() throws Exception {
		String urlString = "http://localhost:8080/api/hi";

		URL url = new URL(urlString);
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestMethod("GET");

		// Check 200 status code
		assertEquals(200, con.getResponseCode());

		// Check response content
		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		StringBuilder responseString = new StringBuilder();
		String line;
		while ((line = in.readLine()) != null) {
			responseString.append(line);
		}
		in.close();

		// Check response.
		assertEquals("Hello, World!", responseString.toString());
	}

	@Test
	public void testUnexistentEndpoint() throws Exception {
		String urlString = "http://localhost:8080/api/unexistent"; // Asegúrate de que el puerto sea correcto

		URL url = new URL(urlString);
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestMethod("GET");

		// Check 404 status code
		assertEquals(404, con.getResponseCode());

	}

	@Test
	public void testPostAndGetANote() throws Exception {
		String postUrl = "http://localhost:8080/api/notes";
		String getUrl = "http://localhost:8080/api/notes";

		// Paso 1: Realizar el POST
		String messageToPost = "{\"text\": \"taza\"}"; // Formato JSON
		URL url = new URL(postUrl);
		HttpURLConnection postCon = (HttpURLConnection) url.openConnection();
		postCon.setRequestMethod("POST");
		postCon.setRequestProperty("Content-Type", "application/json");
		postCon.setDoOutput(true);


		try (OutputStream os = postCon.getOutputStream()) {
			byte[] input = messageToPost.getBytes("utf-8");
			os.write(input, 0, input.length);
		}
		assertEquals(200, postCon.getResponseCode());
		URL getUrlObj = new URL(getUrl);
		HttpURLConnection getCon = (HttpURLConnection) getUrlObj.openConnection();
		getCon.setRequestMethod("GET");
		assertEquals(200, getCon.getResponseCode());
		BufferedReader in = new BufferedReader(new InputStreamReader(getCon.getInputStream()));
		StringBuilder responseString = new StringBuilder();
		String line;
		while ((line = in.readLine()) != null) {
			responseString.append(line);
		}
		in.close();
		ObjectMapper objectMapper = new ObjectMapper();
		List<Note> messages = objectMapper.readValue(responseString.toString(),
				objectMapper.getTypeFactory().constructCollectionType(List.class, Note.class));

		assertTrue(messages.stream().anyMatch(message -> "taza".equals(message.getText())), "El mensaje 'taza' no se encontró en la lista");

	}

	@Test
	public void testPostAndDeleteNote() throws Exception {
		String postUrl = "http://localhost:8080/api/notes";
		String deleteUrl = "http://localhost:8080/api/notes/";
		String jsonInputString = "{\"text\": \"taza\"}";
		URL url = new URL(postUrl);
		HttpURLConnection postCon = (HttpURLConnection) url.openConnection();
		postCon.setRequestMethod("POST");
		postCon.setRequestProperty("Content-Type", "application/json");
		postCon.setDoOutput(true);
		try (OutputStream os = postCon.getOutputStream()) {
			byte[] input = jsonInputString.getBytes("utf-8");
			os.write(input, 0, input.length);
		}
		assertEquals(200, postCon.getResponseCode());
		BufferedReader in = new BufferedReader(new InputStreamReader(postCon.getInputStream()));
		StringBuilder responseString = new StringBuilder();
		String line;
		while ((line = in.readLine()) != null) { responseString.append(line); }
		in.close();
		ObjectMapper objectMapper = new ObjectMapper();
		Note note = objectMapper.readValue(responseString.toString(), Note.class);
		int noteId = Math.toIntExact(note.getId());
		URL deleteUrlObj = new URL(deleteUrl + noteId);
		HttpURLConnection deleteRequest = (HttpURLConnection) deleteUrlObj.openConnection();
		deleteRequest.setRequestMethod("DELETE");
		assertEquals(204, deleteRequest.getResponseCode());
	}
}

