
package com.company;

import java.io.Serializable;

public class Address implements Serializable {
    private static final long serialVersionUID = 1L;

    private String city;
    private int id;
    private int pinCode;
    private int studentId;

    public String toString() {
        return String.format("%-15s | %d", city, pinCode);
    }

    public String toDetailedString() {
        return "\n───────────────────────────────────────\n" +
                "City           : " + city + "\n" +
                "Pincode        : " + pinCode + "\n" +
                "───────────────────────────────────────";
    }

    // Getters
    public String getCity() { return city; }
    public int getId() { return id; }
    public int getPinCode() { return pinCode; }
    public int getStudentId() { return studentId; }

    // Setters with validation
    public void setCity(String city) {
        if (city == null || city.trim().isEmpty()) {
            throw new IllegalArgumentException("City cannot be null or empty");
        }
        this.city = city.trim();
    }

    public void setID(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("ID must be positive");
        }
        this.id = id;
    }

    public void setPinCode(int pinCode) {
        if (pinCode < StudentManagementConstants.MIN_PINCODE ||
                pinCode > StudentManagementConstants.MAX_PINCODE) {
            throw new IllegalArgumentException(
                    String.format("Pincode must be a 6-digit number between %d and %d",
                            StudentManagementConstants.MIN_PINCODE,
                            StudentManagementConstants.MAX_PINCODE));
        }
        this.pinCode = pinCode;
    }

    public void setStudentId(int studentId) {
        if (studentId <= 0) {
            throw new IllegalArgumentException("Student ID must be positive");
        }
        this.studentId = studentId;
    }
}
