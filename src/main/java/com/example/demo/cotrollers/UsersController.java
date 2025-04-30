package com.example.demo.cotrollers;
import com.example.demo.models.User;
import com.example.demo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api")
public class UsersController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/users")
    public ResponseEntity<Map<String, Object>> addUser(@RequestBody User user) {

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode(user.getPassword());

        System.out.println("Password antes de hashear: " + user.getPassword());
        System.out.println("Password hasheada: " + hashedPassword);

        User user2 = new User();
        user2.setUsername(user2.getUsername());

        user2.setPassword(hashedPassword);
        user2.setFirstName(user2.getFirstName());
        user2.setLastName(user2.getLastName());
        user2.setCreatedAt(LocalDateTime.now());
        user2.setIsActive(true);

        System.out.println("User2: " + user2);
        User newUser = userRepository.save(user);

        // Crear un mapa para devolver la información relevante
        Map<String, Object> response = new HashMap<>();
        response.put("id", newUser.getId());
        response.put("username", newUser.getUsername());
        response.put("email", newUser.getEmail());

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
        // return userRepository.save(user2);
    }

    @GetMapping("/users")
    public List<User> getUsers() {
        return userRepository.findAll();
    }


//    @PutMapping("/users/{id}")
//    public ResponseEntity<User> updateNote(@PathVariable Long id, @RequestBody User newUser) {
//        Optional<User> userOpt = userRepository.findById(id);
//
//        if (userOpt.isPresent()) {
//            User user = userOpt.get();
//            user.setEmail(newUser.getEmail()); // AUpdate the note
//            final Note updatedNote = noteRepository.save(note); // ave updated note
//            return ResponseEntity.ok(updatedNote);
//        } else {
//            return ResponseEntity.notFound().build(); // Return 404 if the note with id is not found
//        }
//    }

//    @DeleteMapping("/notes/{id}")
//    public ResponseEntity<String> deleteNote(@PathVariable Long id) {
//        Optional<Note> notaOptional = noteRepository.findById(id);
//        if (notaOptional.isPresent()) {
//            noteRepository.delete(notaOptional.get());
//            return ResponseEntity.noContent().build();
//        } else {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Note not found, check the id in the url.");
//        }
//    }

}
