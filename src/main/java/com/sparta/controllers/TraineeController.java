package com.sparta.controllers;

import com.sparta.dtos.TraineeDTO;
import com.sparta.entities.Trainee;
import com.sparta.services.TraineeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("api/trainees")
@Tag(
        name = "Trainee Management",
        description = "Operations related to trainees"
)
public class TraineeController {
    private final TraineeService traineeService;

    public TraineeController(TraineeService traineeService) {
        this.traineeService = traineeService;
    }

    @Operation(summary = "Get list of trainees", description = "Retrieve a list of all trainees.")
    @GetMapping("/")
    public ResponseEntity<List<TraineeDTO>> getAllTrainees() {
        return ResponseEntity.ok(traineeService.getAllTrainees());
    }

    @Operation(summary = "Get trainee by ID", description = "Retrieve a trainee by their unique ID")
    @GetMapping("/{id}")
    public ResponseEntity<TraineeDTO> getTraineeById(@PathVariable int id) {
        return ResponseEntity.ok(traineeService.getTraineeById(id));
    }

    @Operation(summary = "Create new trainee.", description = "Add a new trainee to the database given the info")
    @PostMapping("/")
    public ResponseEntity<TraineeDTO> createTrainee(@RequestBody TraineeDTO traineeDTO) {
        return ResponseEntity.status(201).body(traineeService.createTrainee(traineeDTO));
    }

    @Operation(summary = "Update existing trainee info", description = "Update the trainee information with provided info")
    @PutMapping("/")
    public ResponseEntity<TraineeDTO> updateTrainee(@RequestBody TraineeDTO traineeDTO) {
        try {
            return ResponseEntity.ok(traineeService.createTrainee(traineeDTO));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Delete chosen trainee from database", description = "Delete trainee from the database using the ID.")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTrainee(@PathVariable int id) {
        boolean deleted = traineeService.deleteTrainee(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
