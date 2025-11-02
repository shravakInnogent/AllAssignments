package com.company;

import java.util.*;
import java.util.stream.Collectors;

public class StudentManagement {
    private static int studentIdCounter = 0;
    private static int addressIdCounter = 10;
    private static int classIdCounter = 0;

    public static void main(String[] args) {
        List<Student> students = new ArrayList<>();
        List<Address> addresses = new ArrayList<>();
        List<Class> classes = new ArrayList<>();

        // Initialize with sample data
        initializeSampleData(students, addresses, classes);

        // Assign ranks to all students
        assignRanks(students);

        // Initialize file operations
        FileOperation fileOp = new FileOperation();
        fileOp.fileCreation();
        fileOp.rankersFileCreation();
        fileOp.writeFile(students, addresses, classes);
        fileOp.writeInRankFile(students);

        // Start interactive menu
        try (Scanner sc = new Scanner(System.in)) {
            runInteractiveMenu(sc, students, addresses, classes, fileOp);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    private static void assignRanks(List<Student> students) {
        // Sort by marks (highest first)
        PaginationHelper.sortByMarks(students);

        int rank = 1;
        int previousMarks = -1;
        int sameRankCount = 0;

        for (Student student : students) {
            if (student.getMarks() == previousMarks) {
                student.setRank(rank - sameRankCount);
                sameRankCount++;
            } else {
                rank += sameRankCount;
                student.setRank(rank);
                previousMarks = student.getMarks();
                sameRankCount = 1;
            }
        }
    }

    private static void initializeSampleData(List<Student> students,
                                             List<Address> addresses,
                                             List<Class> classes) {
        try {
            // Sample students with various ages and marks
            addSampleStudent(students, "Shravak", 15, 78, "Male", 2);
            addSampleStudent(students, "Chanchal", 18, 88, "Female", 2);
            addSampleStudent(students, "Sakshi", 12, 45, "Female", 1);  // Failed
            addSampleStudent(students, "Sejal", 11, 95, "Female", 3);
            addSampleStudent(students, "Akshay", 17, 90, "Male", 2);
            addSampleStudent(students, "Priya", 20, 65, "Female", 1);  // Age 20
            addSampleStudent(students, "Rohit", 14, 35, "Male", 3);     // Failed

            // Sample addresses - multiple cities
            addSampleAddress(addresses, "Pune", 411001, 1);
            addSampleAddress(addresses, "Indore", 482002, 2);
            addSampleAddress(addresses, "Ujjain", 456001, 3);
            addSampleAddress(addresses, "Mumbai", 400001, 4);
            addSampleAddress(addresses, "Indore", 482002, 5);
            addSampleAddress(addresses, "Pune", 411002, 6);
            addSampleAddress(addresses, "Indore", 482010, 7);

            // Sample classes
            addSampleClass(classes, "A");
            addSampleClass(classes, "B");
            addSampleClass(classes, "C");
            addSampleClass(classes, "D");

            System.out.println("Sample data initialized successfully.");

        } catch (Exception e) {
            System.err.println(" Error initializing sample data: " + e.getMessage());
        }
    }

    private static void addSampleStudent(List<Student> students, String name,
                                         int age, int marks, String gender, int classId) {
        try {
            Student student = new Student();
            student.setName(name);
            student.setAge(age);
            student.setMarks(marks);  // This also evaluates pass/fail status
            student.setGender(gender);
            student.setId(++studentIdCounter);
            student.setClassId(classId);
            students.add(student);
        } catch (InvalidAgeException | InvalidMarksException e) {
            System.err.println(" Error adding student " + name + ": " + e.getMessage());
        }
    }

    private static void addSampleAddress(List<Address> addresses, String city,
                                         int pincode, int studentId) {
        Address address = new Address();
        address.setCity(city);
        address.setPinCode(pincode);
        address.setID(++addressIdCounter);
        address.setStudentId(studentId);
        addresses.add(address);
    }

    private static void addSampleClass(List<Class> classes, String className) {
        Class cls = new Class();
        cls.setId(++classIdCounter);
        cls.setCls(className);
        classes.add(cls);
    }

    private static void runInteractiveMenu(Scanner sc, List<Student> students,
                                           List<Address> addresses, List<Class> classes,
                                           FileOperation fileOp) {
        boolean exit = false;

        while (!exit) {
            try {
                System.out.println("\n---------------- MAIN MENU ----------------");
                System.out.println("1.  INSERT New Student");
                System.out.println("2.  Find Students by Pincode (with filters)");
                System.out.println("3.  Find Students by City (with filters)");
                System.out.println("4.  Find Students by Class (with filters)");
                System.out.println("5.  Get Passed Students (with filters)");
                System.out.println("6.  Get Failed Students (with filters)");
                System.out.println("7.  DELETE Student");
                System.out.println("8.  Paginated Student View");
                System.out.println("9.  Top Rankers");
                System.out.println("10. Exit");

                int choice = InputValidator.getValidIntInput(sc, "Enter your choice (1-10):",
                        StudentManagementConstants.MIN_MENU_CHOICE,
                        StudentManagementConstants.MAX_MENU_CHOICE);

                switch (choice) {
                    case 1 -> insertStudent(sc, students, addresses, classes, fileOp);
                    case 2 -> findByPincodeWithFilters(sc, students, addresses, classes);
                    case 3 -> findByCityWithFilters(sc, students, addresses, classes);
                    case 4 -> findByClassWithFilters(sc, students, addresses, classes);
                    case 5 -> getPassedStudents(sc, students, addresses, classes);
                    case 6 -> getFailedStudents(sc, students, addresses, classes);
                    case 7 -> deleteStudent(sc, students, addresses, classes);
                    case 8 -> paginatedView(sc, students, addresses);
                    case 9 -> {
                        assignRanks(students);
                        fileOp.writeInRankFile(students);
                        fileOp.readRankFile();
                    }
                    case 10 -> {
                        exit = true;
                        System.out.println(" Thank You! Visit Again! ");
                    }
                }

            } catch (Exception e) {
                System.err.println(e.getMessage());
                sc.nextLine(); // Clear buffer
            }
        }
    }

    private static void insertStudent(Scanner sc, List<Student> students,
                                      List<Address> addresses, List<Class> classes,
                                      FileOperation fileOp) {
        try {
            System.out.println("\n------------ ADD NEW STUDENT ------------");

            Student student = new Student();
            Address address = new Address();
            Class cls = new Class();

            // Get student details with validations
            String name = InputValidator.getValidName(sc, "Enter student name:");
            student.setName(name);

            String gender = InputValidator.getValidGender(sc, "Enter gender");
            student.setGender(gender);

            int marks = InputValidator.getValidMarks(sc, "Enter marks (0-100):");
            student.setMarks(marks);  // Automatically evaluates pass/fail

            int age = InputValidator.getValidAge(sc, "Enter age (5-20):");
            student.setAge(age);

            // Set IDs
            student.setId(++studentIdCounter);
            student.setClassId(++classIdCounter);
            cls.setId(classIdCounter);
            address.setStudentId(studentIdCounter);
            address.setID(++addressIdCounter);

            // Get address details
            String city = InputValidator.getValidCity(sc, "Enter city:");
            address.setCity(city);

            int pincode = InputValidator.getValidPincode(sc, "Enter pincode (6 digits):");
            address.setPinCode(pincode);

            // Get class details
            String className = InputValidator.getValidClassName(sc, "Enter class:");
            cls.setCls(className);

            // Add to lists
            students.add(student);
            addresses.add(address);
            classes.add(cls);

            System.out.println("\nStudent details submitted successfully!");
            System.out.println(student.toDetailedString());
            System.out.println(address.toDetailedString());
            System.out.println(cls.toDetailedString());

            // Reassign ranks and update files
            assignRanks(students);
            fileOp.writeFile(students, addresses, classes);
            fileOp.writeInRankFile(students);

        } catch (InvalidMarksException | InvalidAgeException e) {
            System.err.println("Validation error: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error adding student: " + e.getMessage());
        }
    }

    // NEW: Find students by pincode with optional filters
    private static void findByPincodeWithFilters(Scanner sc, List<Student> students,
                                                 List<Address> addresses, List<Class> classes) {
        System.out.println("\n-------------FIND BY PINCODE -------------");

        int pincode = InputValidator.getValidPincode(sc, "Enter pincode:");

        FilterCriteria criteria = new FilterCriteria();
        criteria.setPincode(pincode);

        // Ask for optional filters
        System.out.println("\nApply additional filters? (y/n):");
        String applyFilters = sc.nextLine().trim();

        if (applyFilters.equalsIgnoreCase("y")) {
            applyAdditionalFilters(sc, criteria, true, true, true);
        }

        List<Student> results = StudentFilter.filterStudents(students, addresses, criteria);
        displaySearchResults(results, addresses, classes);
    }

    // NEW: Find students by city with optional filters
    private static void findByCityWithFilters(Scanner sc, List<Student> students,
                                              List<Address> addresses, List<Class> classes) {
        System.out.println("\n----------------- FIND BY CITY ---------------");

        String city = InputValidator.getValidCity(sc, "Enter city name:");

        FilterCriteria criteria = new FilterCriteria();
        criteria.setCity(city);

        // Ask for optional filters
        System.out.println("\nApply additional filters? (y/n):");
        String applyFilters = sc.nextLine().trim();

        if (applyFilters.equalsIgnoreCase("y")) {
            applyAdditionalFilters(sc, criteria, true, true, true);
        }

        List<Student> results = StudentFilter.filterStudents(students, addresses, criteria);
        displaySearchResults(results, addresses, classes);
    }

    // NEW: Find students by class with optional filters
    private static void findByClassWithFilters(Scanner sc, List<Student> students,
                                               List<Address> addresses, List<Class> classes) {
        System.out.println("\n-------------------FIND BY CLASS ---------------");

        // Show available classes
        System.out.println("\nAvailable classes:");
        classes.stream()
                .map(Class::getCls)
                .distinct()
                .forEach(cls -> System.out.println("  - " + cls));

        String className = InputValidator.getValidClassName(sc, "\nEnter class name:");

        // Find class ID
        Integer classId = classes.stream()
                .filter(c -> c.getCls().equalsIgnoreCase(className))
                .map(Class::getId)
                .findFirst()
                .orElse(null);

        if (classId == null) {
            System.out.println("Class not found.");
            return;
        }

        FilterCriteria criteria = new FilterCriteria();
        criteria.setClassId(classId);

        // Ask for optional filters
        System.out.println("\nApply additional filters? (y/n):");
        String applyFilters = sc.nextLine().trim();

        if (applyFilters.equalsIgnoreCase("y")) {
            applyAdditionalFilters(sc, criteria, true, true, true);
        }

        List<Student> results = StudentFilter.filterStudents(students, addresses, criteria);
        displaySearchResults(results, addresses, classes);
    }

    // NEW: Get all passed students with optional filters
    private static void getPassedStudents(Scanner sc, List<Student> students,
                                          List<Address> addresses, List<Class> classes) {
        System.out.println("\n-------------PASSED STUDENTS ----------------");

        FilterCriteria criteria = new FilterCriteria();
        criteria.setStatus("Passed");

        // Ask for optional filters
        System.out.println("\nApply additional filters? (y/n):");
        String applyFilters = sc.nextLine().trim();

        if (applyFilters.equalsIgnoreCase("y")) {
            applyAdditionalFilters(sc, criteria, true, true, true);
        }

        List<Student> results = StudentFilter.filterStudents(students, addresses, criteria);
        displaySearchResults(results, addresses, classes);
    }

    // NEW: Get all failed students with optional filters
    private static void getFailedStudents(Scanner sc, List<Student> students,
                                          List<Address> addresses, List<Class> classes) {
        System.out.println("\n-------------- FAILED STUDENTS --------------");

        FilterCriteria criteria = new FilterCriteria();
        criteria.setStatus("Failed");

        // Ask for optional filters
        System.out.println("\nApply additional filters? (y/n):");
        String applyFilters = sc.nextLine().trim();

        if (applyFilters.equalsIgnoreCase("y")) {
            applyAdditionalFilters(sc, criteria, true, true, true);
        }

        List<Student> results = StudentFilter.filterStudents(students, addresses, criteria);
        displaySearchResults(results, addresses, classes);
    }

    // Helper method to apply additional filters
    private static void applyAdditionalFilters(Scanner sc, FilterCriteria criteria,
                                               boolean allowGender, boolean allowAge, boolean allowClass) {
        if (allowGender) {
            System.out.println("Filter by gender? (y/n):");
            if (sc.nextLine().equalsIgnoreCase("y")) {
                String gender = InputValidator.getValidGender(sc, "Enter gender");
                criteria.setGender(gender);
            }
        }

        if (allowAge) {
            System.out.println("Filter by age range? (y/n):");
            if (sc.nextLine().equalsIgnoreCase("y")) {
                int minAge = InputValidator.getValidAge(sc, "Enter minimum age:");
                int maxAge = InputValidator.getValidAge(sc, "Enter maximum age:");
                criteria.setMinAge(minAge);
                criteria.setMaxAge(maxAge);
            }
        }

        if (allowClass) {
            System.out.println("Filter by class? (y/n):");
            if (sc.nextLine().equalsIgnoreCase("y")) {
                String className = InputValidator.getValidClassName(sc, "Enter class name:");
                // Would need to look up class ID, simplified here
                criteria.setClassId(null); // Implement class ID lookup as needed
            }
        }
    }

    // NEW: Paginated view with filters and sorting
    private static void paginatedView(Scanner sc, List<Student> students, List<Address> addresses) {
        System.out.println("\n---------------- PAGINATED VIEW --------------");

        if (students.isEmpty()) {
            System.out.println("⚠ No students available.");
            return;
        }

        // Ask for filters
        FilterCriteria criteria = new FilterCriteria();
        System.out.println("Apply filters? (y/n):");
        if (sc.nextLine().equalsIgnoreCase("y")) {
            applyAdditionalFilters(sc, criteria, true, true, false);
        }

        List<Student> filteredStudents = StudentFilter.filterStudents(students, addresses, criteria);

        if (filteredStudents.isEmpty()) {
            System.out.println("⚠ No students match the criteria.");
            return;
        }

        // Ask for sorting
        System.out.println("\nSort by? (name/marks/age/rank) [press Enter for default]:");
        String sortBy = sc.nextLine().trim();
        if (!sortBy.isEmpty()) {
            filteredStudents = PaginationHelper.sortStudents(filteredStudents, sortBy);
        }

        // Pagination parameters
        int pageSize = InputValidator.getValidIntInput(sc,
                "Enter page size (records per page):", 1, 100);
        int totalRecords = filteredStudents.size();
        int totalPages = PaginationHelper.getTotalPages(totalRecords, pageSize);

        System.out.println("\nEnter page number or range:");
        System.out.println("Examples: '1' for page 1, '1-3' for pages 1 to 3");
        String pageInput = sc.nextLine().trim();

        if (pageInput.contains("-")) {
            // Range of pages
            String[] range = pageInput.split("-");
            int startPage = Integer.parseInt(range[0]);
            int endPage = Integer.parseInt(range[1]);

            for (int page = startPage; page <= endPage && page <= totalPages; page++) {
                displayPage(filteredStudents, page, pageSize, totalRecords);
            }
        } else {
            // Single page
            int page = Integer.parseInt(pageInput);
            displayPage(filteredStudents, page, pageSize, totalRecords);
        }
    }

    private static void displayPage(List<Student> students, int page, int pageSize, int totalRecords) {
        List<Student> pageData = PaginationHelper.getPage(students, page, pageSize);

        if (pageData.isEmpty()) {
            return;
        }

        PaginationHelper.displayPaginationInfo(page, pageSize, totalRecords);
        System.out.println("\nID    | Name                 | Marks  | Gender   | Age  | ClassID | Status | Rank");
        System.out.println("─".repeat(90));

        pageData.forEach(System.out::println);
    }

    // Enhanced delete with class cleanup
    private static void deleteStudent(Scanner sc, List<Student> students,
                                      List<Address> addresses, List<Class> classes) {
        if (students.isEmpty()) {
            System.out.println("No students available to delete.");
            return;
        }

        String name = InputValidator.getValidName(sc, "Enter student name to delete:");

        List<Student> toDelete = students.stream()
                .filter(s -> s.getName().equalsIgnoreCase(name))
                .collect(Collectors.toList());

        if (toDelete.isEmpty()) {
            System.out.println("⚠ No record found for: " + name);
        } else {
            for (Student s : toDelete) {
                int classId = s.getClassId();

                students.remove(s);
                addresses.removeIf(a -> a.getStudentId() == s.getId());

                // NEW: Check if class is now empty and delete it
                boolean hasStudentsInClass = students.stream()
                        .anyMatch(st -> st.getClassId() == classId);

                if (!hasStudentsInClass) {
                    classes.removeIf(c -> c.getId() == classId);
                    System.out.println("✓ Class ID " + classId + " deleted (no students remaining).");
                }
            }

            System.out.println(name + "'s record deleted successfully.");

            // Reassign ranks after deletion
            assignRanks(students);
        }
    }

    private static void displaySearchResults(List<Student> results,
                                             List<Address> addresses, List<Class> classes) {
        if (results.isEmpty()) {
            System.out.println("\n⚠ No students found matching the criteria.");
            return;
        }

        System.out.println("\n" + "═".repeat(90));
        System.out.println("SEARCH RESULTS - Total: " + results.size() + " student(s)");
        System.out.println("═".repeat(90));
        System.out.println("\nID    | Name                 | Marks  | Gender   | Age  | ClassID | Status | Rank");
        System.out.println("─".repeat(90));

        for (Student s : results) {
            System.out.println(s);

            // Display address if available
            Address addr = addresses.stream()
                    .filter(a -> a.getStudentId() == s.getId())
                    .findFirst()
                    .orElse(null);
            if (addr != null) {
                System.out.println("      Address: " + addr);
            }

            // Display class if available
            Class cls = classes.stream()
                    .filter(c -> c.getId() == s.getClassId())
                    .findFirst()
                    .orElse(null);
            if (cls != null) {
                System.out.println("      " + cls);
            }
            System.out.println();
        }
    }
}
