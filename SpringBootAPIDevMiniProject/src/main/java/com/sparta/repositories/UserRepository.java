package com.sparta.repositories;



import com.sparta.entities.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<AppUser, Integer> {
    @Query("SELECT s FROM AppUser s WHERE s.username = :username")
    AppUser findByUsername(@Param("username") String username);

//    @Query("SELECT s FROM User s LEFT JOIN FETCH s.todoItems")
//    List<User> findAllWithTodos();
}
