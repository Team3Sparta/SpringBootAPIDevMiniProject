package com.sparta.config;

import com.sparta.entities.AppUser;
import com.sparta.entities.Trainee;
import com.sparta.repositories.UserRepository;
import org.springframework.context.annotation.Configuration;
import com.sparta.entities.Trainer;
import com.sparta.entities.Course;
import com.sparta.repositories.TraineeRepository;
import com.sparta.repositories.TrainerRepository;
import com.sparta.repositories.CourseRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class AppConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http
//                .authorizeHttpRequests(auth -> auth.anyRequest().authenticated())
//                .formLogin(form -> form.defaultSuccessUrl("/trainee/").permitAll())
//                .csrf(csrf -> csrf.disable());
//        return http.build();
//    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
            .authorizeHttpRequests(auth -> auth
                    .requestMatchers("/trainee/**").hasRole("TRAINEE")
                    .requestMatchers("/trainer/**").hasRole("TRAINER")
                    .anyRequest().authenticated()
            )
            .formLogin(form -> form
                    .successHandler((request, response, authentication) -> {
                        boolean isTrainee = authentication.getAuthorities().stream()
                                .anyMatch(a -> a.getAuthority().equals("ROLE_TRAINEE"));
                        boolean isTrainer = authentication.getAuthorities().stream()
                                .anyMatch(a -> a.getAuthority().equals("ROLE_TRAINER"));

                        if (isTrainee) {
                            response.sendRedirect("/trainees");
                        } else if (isTrainer) {
                            response.sendRedirect("/trainers");
                        } else {
                            response.sendRedirect("/"); // fallback
                        }
                    })
                    .permitAll()
            )
            .csrf(csrf -> csrf.disable());

    return http.build();
}





    @Bean
    @Transactional
    public CommandLineRunner loadData(TrainerRepository trainerRepository, CourseRepository courseRepository, TraineeRepository traineeRepository, UserRepository userRepo, PasswordEncoder encoder) {
        return args -> {
            System.out.println("DataLoader running...");

            // Seed Trainers
            if (traineeRepository.count() == 0) {
                Trainee younis  = new Trainee();
                younis.setFirstName("Younis");
                younis.setLastName("Raja");

                Trainee mohammed = new Trainee();
                mohammed.setFirstName("Muhammad");
                mohammed.setLastName("Qadir");

                Trainee mercy = new Trainee();
                mercy.setFirstName("Mercy");
                mercy.setLastName("Njeru");


                traineeRepository.save(younis);
                traineeRepository.save(mohammed);
                traineeRepository.save(mercy);

                System.out.println("Trainee seed data added");
            } else {
                System.out.println("Trainee seed skipped");
            }

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

                Course javaAdvanced = new Course();
                javaAdvanced.setCourseName("Advanced Java");
                courseRepository.save(javaAdvanced);

                System.out.println("Course seed data added");
            } else {
                System.out.println("Course seed skipped");
            }

            AppUser phil = new AppUser("Phil", encoder.encode("a"), "TRAINER");
            AppUser khurum= new AppUser("Khurum", encoder.encode("b"), "TRAINEE");
            AppUser mercy = new AppUser("Mercy", encoder.encode("c"), "TRAINEE");
            userRepo.saveAll(List.of(phil, khurum, mercy));
        };
    }
}

