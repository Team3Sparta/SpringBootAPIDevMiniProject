package com.sparta.services;

import com.sparta.dtos.CourseDTO;
import com.sparta.dtos.CourseMapper;
import com.sparta.entities.Course;
import com.sparta.repositories.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class CourseService {

    private final CourseRepository entityRepository;
    private final CourseMapper entityMapper;


    @Autowired
    public CourseService(CourseRepository entityRepository,
                         CourseMapper entityMapper) {
        if(entityRepository == null || entityMapper == null){
            throw new IllegalArgumentException("Repository cannot be null");
        }
        this.entityRepository = entityRepository;
        this.entityMapper = entityMapper;
    }

    public List<CourseDTO> getAllCourses() {
        return entityRepository.findAll().stream().map(c -> entityMapper.toDTO(c)).toList();
    }

    public CourseDTO saveCourse(CourseDTO entityParam) {
        if(entityParam == null){
            throw new IllegalArgumentException("Course cannot be null");
        }else if(entityParam.getCourseName().length() < 8){
            throw new IllegalArgumentException("Course name length is < 8");
        }
        Course entity = entityMapper.toEntity(entityParam);
        return entityMapper.toDTO(entityRepository.save(entity));
    }

    /*
    public CourseDTO getEntityByID(Integer id) {
        if (entityRepository.existsById(id) == false) {
            throw new IllegalArgumentException("Customer does not exist!!!");
        } else {
            return entityMapper.toDTO(entityRepository.findById(id).orElse(null));
        }
    }
    */


    public CourseDTO getEntityByID(Integer id) {
        if (entityRepository.existsById(id) == false) {
            throw new IllegalArgumentException("Customer does not exist!!!");
        } else {
            return entityMapper.toDTO(entityRepository.findById(id).orElse(null));
        }
    }

    public List<CourseDTO> excludeCourse(String name) {
        return entityRepository.findByCourseNameNotContainingIgnoreCase(name).stream().map(c -> entityMapper.toDTO(c)).toList();
    }

    public List<CourseDTO> searchCourse(String name) {
        return entityRepository.findByCourseNameContainingIgnoreCase(name).stream().map(c -> entityMapper.toDTO(c)).toList();
    }

    public boolean deleteCourse(Integer id) {
        if (entityRepository.existsById(id)) {
            entityRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public CourseDTO updateCourse(CourseDTO courseDTO) {
        Integer id = courseDTO.getId();
        if (!entityRepository.existsById(id)) {
            throw new NoSuchElementException("Course with ID " + id + " does not exist.");
        }
        Course entity = entityMapper.toEntity(courseDTO);
        Course saved = entityRepository.save(entity);
        return entityMapper.toDTO(saved);
    }

}
