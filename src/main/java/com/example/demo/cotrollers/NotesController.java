package com.example.demo.cotrollers;
import com.example.demo.models.Note;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class NotesController {

    @GetMapping("/hi")
    public String hi() {
        return "Hello, World!";
    }

    @PostMapping("/notas")
    public String añadirNota(@RequestBody Note nota) {

        return "Nota añadida: " + nota.getText();
    }


}
