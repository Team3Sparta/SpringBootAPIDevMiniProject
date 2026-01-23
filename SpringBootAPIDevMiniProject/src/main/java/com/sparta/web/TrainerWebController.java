package com.sparta.web;



import com.sparta.dtos.TrainerDTO;
import com.sparta.entities.Trainer;
import com.sparta.services.TrainerService;
import jakarta.validation.Valid;
import org.springframework.security.access.AccessDeniedException;
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
        boolean isTrainer = UtilityClass.isTrainer();

        if(isTrainer == false  ){
            throw new AccessDeniedException("You do not have permission to access this page.");
        }
        UtilityClass.addIsTrainerToModel(model);
        return "trainers/index";
    }

    @GetMapping("/{id}")
    public String viewEntity(@PathVariable int id, Model model) {
        TrainerDTO entity = entityService.getTrainerById(id);
        //.orElseThrow(() -> new IllegalArgumentException("Invalid ID: " + id));
        model.addAttribute("entity", entity);
        UtilityClass.addIsTrainerToModel(model);
        return "trainers/view";
    }

    @PostMapping("/{id}/update")
    public String updateEntity(@PathVariable int id, @ModelAttribute TrainerDTO updateTrainer) {
        entityService.updateTrainer(updateTrainer);
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
        UtilityClass.addIsTrainerToModel(model);
        return "trainers/new";
    }

    @PostMapping("/save")
    public String createTrainer(@ModelAttribute TrainerDTO newEntity) {
        entityService.createTrainer(newEntity);
        return "redirect:/trainers";
    }

}