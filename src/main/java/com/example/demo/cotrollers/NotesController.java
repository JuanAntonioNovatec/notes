package com.example.demo.cotrollers;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/hi-notes")
public class NotesController {

    @GetMapping
    public String hi() {
        return "Hello, World!";
    }


}
