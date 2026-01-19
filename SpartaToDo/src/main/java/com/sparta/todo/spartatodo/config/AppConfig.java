package com.sparta.todo.spartatodo.config;

import com.sparta.todo.spartatodo.models.Todo;
import com.sparta.todo.spartatodo.repositories.TodoRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.util.List;

@Configuration
public class AppConfig {
    @Bean
    public CommandLineRunner loadData(TodoRepository todoRepository) {
        return args -> {
            // Check if the database is empty
            if (todoRepository.count() == 0) {
                List<Todo> todos = List.of(
                        new Todo("Buy groceries", "Milk, eggs, bread", false, LocalDate.now()),
                        new Todo("Complete project", "Finish the Spring Boot MVC module", true, LocalDate.now().minusDays(2)),
                        new Todo("Call mum", "Check in and say hello", false, LocalDate.now().minusDays(1)),
                        new Todo("Workout", "Go for a 30-minute run", true, LocalDate.now().minusWeeks(1))
                );

                // Save the data only if the database is empty
                todoRepository.saveAll(todos);
            }
        };
    }
}