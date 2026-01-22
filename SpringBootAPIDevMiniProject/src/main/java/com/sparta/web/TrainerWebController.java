package com.sparta.web;



import com.sparta.dtos.TrainerDTO;
import com.sparta.entities.Trainer;
import com.sparta.services.TrainerService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/trainers")
public class TrainerWebController {

    private final TrainerService entityService;

    public TrainerWebController(TrainerService entityService) {
        this.entityService = entityService;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("entities", entityService.getAllTrainers());
        return "trainers/index";
    }

    @GetMapping("/{id}")
    public String viewEntity(@PathVariable int id, Model model) {
        TrainerDTO entity = entityService.getTrainerById(id);
        //.orElseThrow(() -> new IllegalArgumentException("Invalid ID: " + id));
        model.addAttribute("entity", entity);
        return "trainers/view";
    }

    @PostMapping("/{id}/update")
    public String updateEntity(@PathVariable int id, @ModelAttribute TrainerDTO updatedTodo) {
        entityService.createTrainer(updatedTodo);
        return "redirect:/trainers"; // Redirects to the /todos page
    }

    @PostMapping("/{id}/delete")
    public String deleteEntity(@PathVariable int id) {
        entityService.deleteTrainer(id);
        return "redirect:/trainers";
    }

    @GetMapping("/new")
    public String newEntityForm(Model model) {
        model.addAttribute("entity", new TrainerDTO());
        return "trainers/new";
    }

    @PostMapping("/save")
    public String saveTodo(@ModelAttribute TrainerDTO newEntity) {
        entityService.updateTrainer(newEntity);
        return "redirect:/trainers";
    }

}