package com.sparta.controllers;

import com.sparta.dtos.CourseDto;
import com.sparta.services.CourseService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<List<CourseDto>> getAllEntities(){
        List<CourseDto> books = service.getAllCourses();
        return ResponseEntity.ok(books);
    }

    @Operation(summary = "Get a course by ID", description = "Retrieve a course from the database using their unique ID")
    @GetMapping(value = "/{id}")
    public ResponseEntity<CourseDto> getEntityById(@PathVariable Integer id){

        CourseDto c = this.service.getEntityByID(id);

        if(c!=null){
            return ResponseEntity.ok(c);
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Add a new course", description = "Create a new course in the database")
    @PostMapping("/")
    public ResponseEntity<CourseDto> addCourse(@RequestBody CourseDto entity){

        CourseDto savedEntity = service.saveCourse(entity);
        if(savedEntity != null){
            return ResponseEntity.ok(savedEntity);
        }else{
            return ResponseEntity.notFound().build();
        }
    }

}
