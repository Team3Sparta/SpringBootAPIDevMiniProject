package com.sparta.dtos;

import com.sparta.entities.Course;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-01-14T14:52:15+0000",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.9 (Microsoft)"
)
@Component
public class CourseMapperImpl implements CourseMapper {

    @Override
    public CourseDto toDTO(Course entity) {
        if ( entity == null ) {
            return null;
        }

        CourseDto courseDto = new CourseDto();

        courseDto.setId( entity.getId() );
        courseDto.setCourseName( entity.getCourseName() );

        return courseDto;
    }

    @Override
    public Course toEntity(CourseDto entityDto) {
        if ( entityDto == null ) {
            return null;
        }

        Course course = new Course();

        course.setId( entityDto.getId() );
        course.setCourseName( entityDto.getCourseName() );

        return course;
    }
}
