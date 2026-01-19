package com.sparta.todo.spartatodo.controllers;

import com.sparta.todo.spartatodo.repositories.TodoRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/todos")
public class TodoController {
    private final TodoRepository todoRepository;

    public TodoController(TodoRepository todoRepository){
        this.todoRepository = todoRepository;
    }

    @GetMapping("/")
    public String listTodos(Model model){
        model.addAttribute("todos", todoRepository.findAll());
        return "todos/index";
    }
}
