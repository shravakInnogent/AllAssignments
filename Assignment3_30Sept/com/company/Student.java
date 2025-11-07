package com.company;

import java.io.Serializable;

public class Student implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private String name;
    private int classId;
    private int marks;
    private String gender;
    private int age;
    private String status;  
    private int rank;       

    public Student() {
        this.status = "Not Evaluated";
        this.rank = 0;
    }

    public String toString() {
        return String.format("%-5d | %-20s | %-6d | %-8s | %-4d | %-8d | %-6s | Rank %-3d",
                id, name, marks, gender, age, classId, status, rank);
    }

    public String toDetailedString() {
        return "\n═══════════════════════════════════════\n" +
                "Student ID     : " + id + "\n" +
                "Name           : " + name + "\n" +
                "Marks          : " + marks + "\n" +
                "Status         : " + status + "\n" +
                "Rank           : " + rank + "\n" +
                "Gender         : " + gender + "\n" +
                "Age            : " + age + "\n" +
                "Class ID       : " + classId + "\n" +
                "═══════════════════════════════════════";
    }

    // NEW: Evaluate pass/fail status based on marks
    public void evaluateStatus() {
        if (marks < StudentManagementConstants.PASSING_MARKS) {
            this.status = "Failed";
        } else {
            this.status = "Passed";
        }
    }

    // Setters with validation
    public void setId(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("ID must be positive");
        }
        this.id = id;
    }

    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty");
        }
        this.name = name.trim();
    }

    public void setClassId(int classId) {
        if (classId <= 0) {
            throw new IllegalArgumentException("Class ID must be positive");
        }
        this.classId = classId;
    }

    public void setGender(String gender) {
        if (gender == null || gender.trim().isEmpty()) {
            throw new IllegalArgumentException("Gender cannot be null or empty");
        }
        this.gender = gender.trim();
    }

    public void setMarks(int marks) throws InvalidMarksException {
        if (marks < StudentManagementConstants.MIN_MARKS ||
                marks > StudentManagementConstants.MAX_MARKS) {
            throw new InvalidMarksException(
                    String.format("Marks must be between %d and %d",
                            StudentManagementConstants.MIN_MARKS,
                            StudentManagementConstants.MAX_MARKS));
        }
        this.marks = marks;
        evaluateStatus();  // Auto-evaluate when marks are set
    }

    public void setAge(int age) throws InvalidAgeException {
        // UPDATED: Age > 20 should fail
        if (age < StudentManagementConstants.MIN_AGE ||
                age > StudentManagementConstants.MAX_AGE) {
            throw new InvalidAgeException(
                    String.format("Age must be between %d and %d (students over 20 not allowed)",
                            StudentManagementConstants.MIN_AGE,
                            StudentManagementConstants.MAX_AGE));
        }
        this.age = age;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    // Getters
    public int getId() { return id; }
    public String getName() { return name; }
    public String getGender() { return gender; }
    public int getAge() { return age; }
    public int getClassId() { return classId; }
    public int getMarks() { return marks; }
    public String getStatus() { return status; }
    public int getRank() { return rank; }
}
