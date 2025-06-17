package com.example.YoonONI_BackEnd.User.Controller;

import org.springframework.web.bind.annotation.*;

import com.example.YoonONI_BackEnd.Entity.User;
import com.example.YoonONI_BackEnd.Repository.UserRepository;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @PostMapping
    public User createUser(@RequestBody User user) {
        return userRepository.save(user);
    }
}
