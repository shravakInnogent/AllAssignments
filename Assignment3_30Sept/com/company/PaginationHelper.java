package com.company;

import java.util.*;

public class PaginationHelper {

    // Get specific page of data
    public static List<Student> getPage(List<Student> allStudents, int pageNumber, int pageSize) {
        List<Student> pageData = new ArrayList<>();

        // Check if list is empty
        if (allStudents == null || allStudents.isEmpty()) {
            return pageData;
        }

        // Check page number
        if (pageNumber < 1) {
            pageNumber = 1;
        }

        // Calculate start and end positions
        int startIndex = (pageNumber - 1) * pageSize;
        int endIndex = startIndex + pageSize;

        // Check if start position is valid
        if (startIndex >= allStudents.size()) {
            System.out.println("⚠ Page number too high!");
            return pageData;
        }

        // Make sure end doesn't exceed list size
        if (endIndex > allStudents.size()) {
            endIndex = allStudents.size();
        }

        // Copy students for this page
        for (int i = startIndex; i < endIndex; i++) {
            pageData.add(allStudents.get(i));
        }

        return pageData;
    }
    public static List<Student> sortStudents(List<Student> students, String sortBy) {
        return switch (sortBy.toLowerCase()){
            case "name" -> sortByName(students);
            case  "age" -> sortByAge(students);
            case "marks" -> sortByMarks(students);
            case "rank" -> sortByRank(students);
            default -> students;
        };
    }
    // Sort students by name (A to Z) - Using Bubble Sort
    public static List<Student> sortByName(List<Student> students) {
        if (students == null || students.isEmpty()) {
            return students;
        }
        int n = students.size();

        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                // Compare names
                if (students.get(j).getName().compareTo(students.get(j + 1).getName()) > 0) {
                    // Swap if out of order
                    Student temp = students.get(j);
                    students.set(j, students.get(j + 1));
                    students.set(j + 1, temp);
                }
            }
        }
        return students;
    }

    public static List<Student> sortByMarks(List<Student> students) {
        if (students == null || students.isEmpty()) {
            return students;
        }

        int n = students.size();

        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                // Compare marks - reverse order (highest first)
                if (students.get(j).getMarks() < students.get(j + 1).getMarks()) {
                    // Swap
                    Student temp = students.get(j);
                    students.set(j, students.get(j + 1));
                    students.set(j + 1, temp);
                }
            }
        }
        return students;
    }

    public static List<Student> sortByAge(List<Student> students) {
        if (students == null || students.isEmpty()) {
            return students;
        }

        int n = students.size();

        // Bubble sort
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                // Compare ages
                if (students.get(j).getAge() > students.get(j + 1).getAge()) {
                    // Swap
                    Student temp = students.get(j);
                    students.set(j, students.get(j + 1));
                    students.set(j + 1, temp);
                }
            }
        }
       return students;
    }

    // Sort students by rank (Rank 1 first) - Using Bubble Sort
    public static List<Student> sortByRank(List<Student> students) {
        if (students == null || students.isEmpty()) {
            return students;
        }

        int n = students.size();

        // Bubble sort
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                // Compare ranks
                if (students.get(j).getRank() > students.get(j + 1).getRank()) {
                    // Swap
                    Student temp = students.get(j);
                    students.set(j, students.get(j + 1));
                    students.set(j + 1, temp);
                }
            }
        }
        return students;
    }

    public static int getTotalPages(int totalStudents, int pageSize) {
        if (totalStudents == 0 || pageSize == 0) {
            return 0;
        }
        int totalPages = totalStudents / pageSize;

        if (totalStudents % pageSize != 0) {
            totalPages++;
        }

        return totalPages;
    }

    public static void displayPaginationInfo(int currentPage, int pageSize, int totalStudents) {
        int totalPages = getTotalPages(totalStudents, pageSize);
        int startRecord = (currentPage - 1) * pageSize + 1;
        int endRecord = currentPage * pageSize;

        // Make sure end doesn't exceed total
        if (endRecord > totalStudents) {
            endRecord = totalStudents;
        }
    }
}