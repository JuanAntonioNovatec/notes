package com.example.demo.sevices

import com.example.demo.repositories.UserRepository
import org.springframework.beans.factory.annotation.Autowired



abstract class BaseUsersService {

    @Autowired
    protected lateinit var userRepository: UserRepository
}