package com.example.SIS3.controller;

import com.example.SIS3.repository.UserRepository;
import com.example.SIS3.service.User;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {

    public final UserRepository userRepository;

    public UserController(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @PostMapping("/adduser")
    public User createItem(@RequestBody User user) {
        return userRepository.save(user);
    }

    @GetMapping("/user/all")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}