package com.sparta.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "trainer")
public class Trainer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "trainer_id", nullable = false)
    private Integer id;

    @Size(max = 30)
    @NotNull
    @Column(name = "first_name", nullable = false, length = 30)
    private String firstName;

    @Size(max = 30)
    @NotNull
    @Column(name = "last_name", nullable = false, length = 30)
    private String lastName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

}