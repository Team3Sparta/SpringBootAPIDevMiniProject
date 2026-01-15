package com.sparta.dtos;

import com.sparta.entities.Course;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CourseMapper {
    CourseDTO toDTO(Course entity);
    Course toEntity(CourseDTO entityDto);
}
