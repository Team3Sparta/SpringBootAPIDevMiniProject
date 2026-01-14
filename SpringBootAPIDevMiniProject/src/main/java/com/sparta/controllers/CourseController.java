package com.sparta.controllers;

import com.sparta.dtos.CourseDto;
import com.sparta.services.CourseService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/courses")
public class CourseController {

    private final CourseService service;

    public CourseController(CourseService service){
        this.service = service;
    }

    @Operation(summary = "get all courses", description = "Get list of all vourses")
    @GetMapping(value = "/")
    public ResponseEntity<List<CourseDto>> getAllCourses(){
        List<CourseDto> books = service.getAllCourses();
        return ResponseEntity.ok(books);
    }

}
