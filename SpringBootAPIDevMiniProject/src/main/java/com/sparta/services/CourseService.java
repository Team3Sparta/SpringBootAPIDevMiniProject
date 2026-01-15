package com.sparta.services;

import com.sparta.dtos.CourseDto;
import com.sparta.dtos.CourseMapper;
import com.sparta.entities.Course;
import com.sparta.repositories.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public List<CourseDto> getAllCourses() {
        return entityRepository.findAll().stream().map(c -> entityMapper.toDTO(c)).toList();
    }

    public CourseDto saveCourse(CourseDto entityParam) {
        if(entityParam == null){
            throw new IllegalArgumentException("Course cannot be null");
        }else if(entityParam.getCourseName().length() < 8){
            throw new IllegalArgumentException("Course name length is < 8");
        }
        Course entity = entityMapper.toEntity(entityParam);
        return entityMapper.toDTO(entityRepository.save(entity));
    }

    public CourseDto getEntityByID(Integer id) {
        if (entityRepository.existsById(id) == false) {
            throw new IllegalArgumentException("Customer does not exist!!!");
        } else {
            return entityMapper.toDTO(entityRepository.findById(id).orElse(null));
        }
    }

}
