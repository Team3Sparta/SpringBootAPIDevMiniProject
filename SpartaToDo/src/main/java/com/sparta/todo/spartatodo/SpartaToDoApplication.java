package com.sparta.todo.spartatodo;

import com.sparta.todo.spartatodo.models.Todo;
import com.sparta.todo.spartatodo.repositories.TodoRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;


/**TODO:
 * Make ID Read only
 * Make it look nicer
 * TODO: Create a new method in the HomeController called viewTodo - it's just a get request -> return "todos/view" and show the details of the appropriate to do item
 Implement a method which
 returns a new form to be filled in
 TODO: The form should be called
 new.html and placed in the todos directory
 And when all details are added
 and you click save, then that to-do
 item should be added to the database and
 you should be redirected back to your
 list of to do items
 Include a cancel button on the form as well
 */

@SpringBootApplication
public class SpartaToDoApplication {

    public static void main(String[] args) {

        ApplicationContext context = SpringApplication.run(SpartaToDoApplication.class, args);
        TodoRepository todoRepository = context.getBean(TodoRepository.class);
        for(Todo todo: todoRepository.findAll()){
            System.out.println(todo);
        }
    }

}
