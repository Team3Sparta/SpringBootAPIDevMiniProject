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
                            response.sendRedirect("/trainee/");
                        } else if (isTrainer) {
                            response.sendRedirect("/trainer/");
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
                mohammed.setFirstName("Mohammed");
                mohammed.setLastName("Qadir");

                traineeRepository.save(younis);
                traineeRepository.save(mohammed);

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

                System.out.println("Course seed data added");
            } else {
                System.out.println("Course seed skipped");
            }

            AppUser trainer = new AppUser("nish@spartaglobal.com", encoder.encode("trainerpass"), "TRAINER");
            AppUser rick = new AppUser("Rick@spartaglobal.com", encoder.encode("rickpass"), "TRAINEE");
            AppUser morty = new AppUser("Morty@spartaglobal.com", encoder.encode("mortypass"), "TRAINEE");
            userRepo.saveAll(List.of(trainer, rick, morty));


        };
    }
}