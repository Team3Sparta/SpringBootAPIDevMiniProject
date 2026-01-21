package com.sparta.controllers;

import com.sparta.dtos.CourseDTO;
import com.sparta.dtos.TrainerDTO;
import com.sparta.services.CourseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/courses")
@Tag(
        name = "Course Management",
        description = "Operations related to courses"
)
public class CourseController {

    private final CourseService service;

    public CourseController(CourseService service){
        this.service = service;
    }

    @Operation(summary = "Get all courses", description = "Get list of all courses")
    @GetMapping(value = "/")
    public ResponseEntity<List<CourseDTO>> getAllEntities(){
        List<CourseDTO> books = service.getAllCourses();
        return ResponseEntity.ok(books);
    }

    @Operation(summary = "Get a course by ID", description = "Retrieve a course from the database using their unique ID")
    @GetMapping(value = "/{id}")
    public ResponseEntity<CourseDTO> getEntityById(@PathVariable Integer id){

        CourseDTO c = this.service.getEntityByID(id);

        if(c!=null){
            return ResponseEntity.ok(c);
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Add a new course", description = "Create a new course in the database")
    @PostMapping("/")
    public ResponseEntity<CourseDTO> addCourse(@RequestBody CourseDTO entity){

        CourseDTO savedEntity = service.saveCourse(entity);
        if(savedEntity != null){
            return ResponseEntity.ok(savedEntity);
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Get all courses that do not match the name", description = "Get list of all courses")
    @GetMapping(params = "excludeName")
    public ResponseEntity<List<CourseDTO>> getExcludedEntities(
            @RequestParam("excludeName") String name    ) {
        return ResponseEntity.ok(service.excludeCourse(name));
    }
    @Operation(summary = "Update course", description = "Update an existing course")
    @PutMapping("/{id}")
    public ResponseEntity<CourseDTO> updateCourse(@PathVariable int id, @RequestBody CourseDTO courseDTO) {

        courseDTO.setId(id);
        try {
            CourseDTO updatedCourse = service.updateCourse(courseDTO);
            return ResponseEntity.ok(updatedCourse);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Delete course", description = "Delete a course by ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCourse(@PathVariable int id) {
        return service.deleteCourse(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}
