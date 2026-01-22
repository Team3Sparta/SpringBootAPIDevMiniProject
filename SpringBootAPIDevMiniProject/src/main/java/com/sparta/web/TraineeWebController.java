package com.sparta.web;


import com.sparta.dtos.CourseDTO;
import com.sparta.dtos.TraineeDTO;
import com.sparta.services.TraineeService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/trainee")
public class TraineeWebController {

    private final TraineeService entityService;

    public TraineeWebController(TraineeService entityService) {
        this.entityService = entityService;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("entities", entityService.getAllTrainees());
        return "trainee/index";
    }
    @GetMapping("/{id}")
    public String viewEntity(@PathVariable int id, Model model) {
        TraineeDTO entity = entityService.getTraineeById(id);
//        .orElseThrow(() -> new IllegalArgumentException("Invalid ID: " + id));
        model.addAttribute("entity", entity);
        return "trainee/view";
    }

    @PostMapping("/{id}/update")
    public String updateEntity(@PathVariable int id, @ModelAttribute TraineeDTO updatedEntity) {
        entityService.updateTrainee(updatedEntity);
        return "redirect:/trainee"; // Redirects to the /trainee page
    }
    @PostMapping("/save")
    public String saveEntity(@ModelAttribute TraineeDTO newEntity) {
        entityService.createTrainee(newEntity);
        return "redirect:/trainee";
    }

    @PostMapping("/{id}/delete")
    public String deleteEntity(@PathVariable int id) {
        entityService.deleteTrainee(id);
        return "redirect:/trainee";
    }

    @GetMapping("/new")
    public String newEntityForm(Model model) {
        model.addAttribute("entity", new TraineeDTO());
        return "trainee/new";
    }



}