package com.sparta.todo.spartatodo.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Controller
@RequestMapping("/")
public class HomeController {

    // Spring creates the Model
    // Controller assigns values to attributes within that model
    // Thymeleaf reads these
    // Model is discarded after teh response
    @GetMapping
    public String index(Model model){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE d MMMM yyyy");
        String now = LocalDateTime.now().format(formatter);
        model.addAttribute("date", now);
        return "index";
    }
}
