package com.example.demo;

import com.example.demo.models.User;
import com.example.demo.repositories.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import java.sql.DriverManager.println

@Component
class DataInitializer(
        private val userRepository: UserRepository,
        private val passwordEncoder: PasswordEncoder
) : CommandLineRunner {

    @PostConstruct
    fun init() {
        println("Esto se ejecuta al inicializar el bean")
    }

    override fun run(vararg args: String?) {
        if (userRepository.findByUsername("adminUser").isEmpty()) {
            val admin = User()
            admin.username = "adminUser"
            admin.password = passwordEncoder.encode("1234")
            admin.role = "admin"
            userRepository.save(admin)
            println("Usuario admin creado")
        }
    }
}