package com.sparta.web;


import com.sparta.dtos.CourseDTO;
import com.sparta.services.CourseService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/courses")
public class CourseWebController {

    private final CourseService entityService;

    public CourseWebController(CourseService entityService) {
        this.entityService = entityService;
    }

    @GetMapping("/search")
    public String searchEntities(@RequestParam("query") String query, Model model) {
        // Search for courses by title or description
        List<CourseDTO> searchResults = entityService.searchCourse(query);
        model.addAttribute("entities", searchResults);
        return "courses/index"; // Return the same index.html template with filtered results
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("entities", entityService.getAllCourses());
        return "courses/index";
    }

    @GetMapping("/{id}")
    public String viewEntity(@PathVariable int id, Model model) {
        CourseDTO entity = entityService.getEntityByID(id);
                //.orElseThrow(() -> new IllegalArgumentException("Invalid ID: " + id));
        model.addAttribute("entity", entity);
        return "courses/view";
    }

    @PostMapping("/{id}/update")
    public String updateEntity(@PathVariable int id, @ModelAttribute CourseDTO updatedEntity) {
        entityService.saveCourse(updatedEntity);
        return "redirect:/courses"; // Redirects to the /courses page
    }

    @PostMapping("/{id}/delete")
    public String deleteEntity(@PathVariable int id) {
        entityService.deleteCourse(id);
        return "redirect:/courses";
    }

    @GetMapping("/new")
    public String newEntityForm(Model model) {
        model.addAttribute("entity", new CourseDTO());
        return "courses/new";
    }

    @PostMapping("/save")
    public String saveEntity(@ModelAttribute CourseDTO newEntity) {
        entityService.saveCourse(newEntity);
        return "redirect:/courses";
    }

    @GetMapping("/exclude")
    public String excludeSearch(@RequestParam("query") String query, Model model) {
        // Search for courses by title or description
        List<CourseDTO> searchResults = entityService.excludeCourse(query);
        model.addAttribute("entities", searchResults);
        return "courses/index";
    
}