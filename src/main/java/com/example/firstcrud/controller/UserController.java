package com.example.firstcrud.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @GetMapping("/")
    public String helloWorld(){
        return "Hello World jaime chunchaso";
    }
}
