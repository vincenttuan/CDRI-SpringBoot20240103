package com.example.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "Welcome to the home page!";
    }
    
    @GetMapping("/hello")
    public String hello() {
        return "Hello !";
    }
}

