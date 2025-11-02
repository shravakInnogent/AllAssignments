package com.innogent.demo.entity;

import lombok.Data;

@Data
public class StudentEnrollWrapper {
    private String firstName;
    private String lastName;
    private String email;
    private String courseName;
    private int courseId;
    private String description;
    private String instructor;
}
