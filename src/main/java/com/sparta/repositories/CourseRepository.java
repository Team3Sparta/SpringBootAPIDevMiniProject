package com.sparta.repositories;

import com.sparta.entities.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

@Repository
@RepositoryRestResource(exported = false)
public interface CourseRepository extends JpaRepository<Course, Integer> {
    public java.util.List<Course> findByCourseNameNotContainingIgnoreCase (String name);
    public java.util.List<Course> findByCourseNameContainingIgnoreCase (String name);

}