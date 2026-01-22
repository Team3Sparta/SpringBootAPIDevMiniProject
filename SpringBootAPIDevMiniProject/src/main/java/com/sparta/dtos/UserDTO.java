package com.sparta.dtos;

import jakarta.validation.constraints.NotNull;

public class UserDTO {

    @NotNull

    private int id;
    @NotNull

    private String username;
    @NotNull

    private String role;
    @NotNull

    private String password;

   // private List<TodoDTO> todoItemsDto = new ArrayList<>();


    // Constructors
    public UserDTO() {
    }

//    public UserDTO(String username, String password, String role/*, List<TodoDTO> todoItemsDto*/) {
//        this(username, password, role);
//        this.todoItemsDto = todoItemsDto;
//    }

    public UserDTO(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
//    public void setTodoItems(List<TodoDTO> todoItemsDto) {
//        this.todoItemsDto = todoItemsDto;
//    }
//
//    // Add a single Todo item
//    public void addTodoItemDto(TodoDTO todoDto) {
//        if (this.todoItemsDto == null) {
//            this.todoItemsDto = new ArrayList<>();
//        }
//        this.todoItemsDto.add(todoDto);
//        todoDto.setSpartanDTO(this);  // <-- back reference set here
//    }
//
//
//    public List<TodoDTO> getTodoItemsDto(){
//        return this.todoItemsDto;
//    }


}
