package com.sparta.dtos;


import com.fasterxml.jackson.annotation.JsonIgnore;
//import io.swagger.v3.oas.annotations.media.Schema;

public class CourseDTO {

    @JsonIgnore
    //@Schema(hidden = true)
    private Integer id;
    private String courseName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }


}
