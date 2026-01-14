package com.sparta.config;


import com.sparta.entities.Course;

import com.sparta.repositories.CourseRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.Transactional;

@Configuration
public class AppConfig {

    @Bean
    @Transactional
    public CommandLineRunner loadData(CourseRepository courseRepository) {
        return args -> {
            System.out.println("DataLoader running...");
            if (courseRepository.count() == 0) {
                var java = new Course("Java");
                var bython   = new Course("Bython");

                courseRepository.save(java);
                courseRepository.save(bython);

                System.out.println("Seed data added");
            } else {
                System.out.println("Seed skipped");
            }
        };
    }
}