package com.sparta.todo.spartatodo.repositories;

import com.sparta.todo.spartatodo.models.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TodoRepository extends JpaRepository<Todo, Integer> {
}
