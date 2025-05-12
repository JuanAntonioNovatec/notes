package com.example.demo.cotrollers

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api")
class TestController {
    @GetMapping("/test")
    fun hi() = "Hello, World!"
}