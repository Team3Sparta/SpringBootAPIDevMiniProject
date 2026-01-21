package com.sparta.controllers;

import com.sparta.dtos.TrainerDTO;
import com.sparta.entities.Trainer;
import com.sparta.services.TrainerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("api/trainers")
@Tag(
        name = "Trainer Management",
        description = "Operations related to trainers"
)
public class TrainerController {

    private final TrainerService service;

    public TrainerController(TrainerService service) {
        this.service = service;
    }

    @Operation(summary = "Get all trainers", description = "Retrieve a list of all trainers")
    @GetMapping("/")
    public ResponseEntity<List<TrainerDTO>> getAllTrainers() {
        return ResponseEntity.ok(service.getAllTrainers());
    }

    @Operation(summary = "Get trainer by ID", description = "Retrieve a trainer by their unique ID")
    @GetMapping("/{id}")
    public ResponseEntity<TrainerDTO> getTrainerById(@PathVariable int id) {
        try {
            return ResponseEntity.ok(service.getTrainerById(id));
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Add new trainer", description = "Create a new trainer")
    @PostMapping("/")
    public ResponseEntity<TrainerDTO> addTrainer(@RequestBody TrainerDTO trainerDTO) {
        TrainerDTO createdTrainer = service.createTrainer(trainerDTO);
        return ResponseEntity.status(201).body(createdTrainer);
    }

    @Operation(summary = "Update trainer", description = "Update an existing trainer")
    @PutMapping("/{id}")
    public ResponseEntity<TrainerDTO> updateTrainer(@PathVariable int id, @RequestBody TrainerDTO trainerDTO) {

        trainerDTO.setId(id);
        try {
            TrainerDTO updatedTrainer = service.updateTrainer(trainerDTO);
            return ResponseEntity.ok(updatedTrainer);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Delete trainer", description = "Delete a trainer by ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTrainer(@PathVariable int id) {
        return service.deleteTrainer(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}