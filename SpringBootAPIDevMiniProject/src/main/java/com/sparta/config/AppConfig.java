package com.sparta.config;

import org.springframework.context.annotation.Configuration;
import com.sparta.entities.Trainer;
import com.sparta.entities.Course;
import com.sparta.repositories.TrainerRepository;
import com.sparta.repositories.CourseRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.Transactional;

@Configuration
public class AppConfig {

    @Bean
    @Transactional
    public CommandLineRunner loadData(TrainerRepository trainerRepository, CourseRepository courseRepository) {
        return args -> {
            System.out.println("DataLoader running...");

            // Seed Trainers
            if (trainerRepository.count() == 0) {
                Trainer khurrum  = new Trainer();
                khurrum.setFirstName("Khurrum");
                khurrum.setLastName("Arif");

                Trainer mohammed = new Trainer();
                mohammed.setFirstName("Mohammed");
                mohammed.setLastName("Qadir");

                trainerRepository.save(khurrum);
                trainerRepository.save(mohammed);

                System.out.println("Trainer seed data added");
            } else {
                System.out.println("Trainer seed skipped");
            }

            // Seed Courses
            if (courseRepository.count() == 0) {
                Course javaCourse   = new Course();
                javaCourse.setCourseName("Java Basics");

                Course springCourse = new Course();
                springCourse.setCourseName("Spring Boot");

                courseRepository.save(javaCourse);
                courseRepository.save(springCourse);

                System.out.println("Course seed data added");
            } else {
                System.out.println("Course seed skipped");
            }
        };
    }
}