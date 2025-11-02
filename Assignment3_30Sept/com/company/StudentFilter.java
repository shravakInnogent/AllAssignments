package com.company;


import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class StudentFilter {

    public static List<Student> filterStudents(List<Student> students,
                                               List<Address> addresses,
                                               FilterCriteria criteria) {
        Predicate<Student> predicate = s -> true; // Start with always-true predicate

        // Apply gender filter
        if (criteria.getGender() != null && !criteria.getGender().isEmpty()) {
            predicate = predicate.and(s -> s.getGender().equalsIgnoreCase(criteria.getGender()));
        }

        // Apply age filter
        if (criteria.getMinAge() != null) {
            predicate = predicate.and(s -> s.getAge() >= criteria.getMinAge());
        }
        if (criteria.getMaxAge() != null) {
            predicate = predicate.and(s -> s.getAge() <= criteria.getMaxAge());
        }

        // Apply class filter
        if (criteria.getClassId() != null) {
            predicate = predicate.and(s -> s.getClassId() == criteria.getClassId());
        }

        // Apply status filter (Passed/Failed)
        if (criteria.getStatus() != null && !criteria.getStatus().isEmpty()) {
            predicate = predicate.and(s -> s.getStatus().equalsIgnoreCase(criteria.getStatus()));
        }

        // Apply city/pincode filters (requires address lookup)
        List<Student> filteredStudents = students.stream()
                .filter(predicate)
                .collect(Collectors.toList());

        // Further filter by city if specified
        if (criteria.getCity() != null && !criteria.getCity().isEmpty()) {
            filteredStudents = filteredStudents.stream()
                    .filter(s -> {
                        Address addr = findAddressByStudentId(addresses, s.getId());
                        return addr != null && addr.getCity().equalsIgnoreCase(criteria.getCity());
                    })
                    .collect(Collectors.toList());
        }

        // Further filter by pincode if specified
        if (criteria.getPincode() != null) {
            filteredStudents = filteredStudents.stream()
                    .filter(s -> {
                        Address addr = findAddressByStudentId(addresses, s.getId());
                        return addr != null && addr.getPinCode() == criteria.getPincode();
                    })
                    .collect(Collectors.toList());
        }

        return filteredStudents;
    }

    private static Address findAddressByStudentId(List<Address> addresses, int studentId) {
        return addresses.stream()
                .filter(a -> a.getStudentId() == studentId)
                .findFirst()
                .orElse(null);
    }
}

// Filter criteria class
class FilterCriteria {
    private String gender;
    private Integer minAge;
    private Integer maxAge;
    private Integer classId;
    private String city;
    private Integer pincode;
    private String status; // Passed or Failed

    // Constructors
    public FilterCriteria() {}

    // Getters and Setters
    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }

    public Integer getMinAge() { return minAge; }
    public void setMinAge(Integer minAge) { this.minAge = minAge; }

    public Integer getMaxAge() { return maxAge; }
    public void setMaxAge(Integer maxAge) { this.maxAge = maxAge; }

    public Integer getClassId() { return classId; }
    public void setClassId(Integer classId) { this.classId = classId; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public Integer getPincode() { return pincode; }
    public void setPincode(Integer pincode) { this.pincode = pincode; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
