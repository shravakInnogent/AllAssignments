
package com.company;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

class FileOperation {

    // Create student file with error handling
    void fileCreation() {
        File file = new File(StudentManagementConstants.STUDENT_FILE);
        try {
            if (file.createNewFile()) {
                System.out.println("File created: " + file.getName());
            } else {
                System.out.println("File already exists: " + file.getName());
            }
        } catch (IOException e) {
            System.err.println("Error creating file: " + e.getMessage());
        }
    }

    // Create rankers file with error handling
    void rankersFileCreation() {
        File file = new File(StudentManagementConstants.RANKER_FILE);
        try {
            if (file.createNewFile()) {
                System.out.println(" Ranker file created: " + file.getName());
            } else {
                System.out.println(" Ranker file already exists: " + file.getName());
            }
        } catch (IOException e) {
            System.err.println("Error creating ranker file: " + e.getMessage());
        }
    }

    // Write top rankers to file using try-with-resources
    void writeInRankFile(List<Student> rankers) {
        if (rankers == null || rankers.isEmpty()) {
            System.out.println("⚠ No students available to write to ranker file.");
            return;
        }

        int count = Math.min(StudentManagementConstants.TOP_RANKERS_COUNT, rankers.size());

        try (FileOutputStream fos = new FileOutputStream(StudentManagementConstants.RANKER_FILE);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {

            for (int i = 0; i < count; i++) {
                oos.writeObject(rankers.get(i));
            }
            System.out.println("✓ Top " + count + " rankers written to file successfully.");

        } catch (IOException e) {
            System.err.println("⚠ Error writing to ranker file: " + e.getMessage());
        }
    }

    // Write all data to main file using try-with-resources
    void writeFile(List<Student> students, List<Address> addresses, List<Class> classes) {
        if (students == null || addresses == null || classes == null) {
            System.out.println("Cannot write null data to file.");
            return;
        }

        try (FileOutputStream fos = new FileOutputStream(StudentManagementConstants.STUDENT_FILE);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {

            // Write students
            for (Student s : students) {
                oos.writeObject(s);
            }

            // Write addresses
            for (Address a : addresses) {
                oos.writeObject(a);
            }

            // Write classes
            for (Class c : classes) {
                oos.writeObject(c);
            }

            System.out.println("✓ All data written to file successfully.");

        } catch (IOException e) {
            System.err.println("⚠ Error writing to student file: " + e.getMessage());
        }
    }

    // Read and display top rankers using try-with-resources
    void readRankFile() {
        File file = new File(StudentManagementConstants.RANKER_FILE);

        if (!file.exists()) {
            System.out.println("Ranker file does not exist. Please add students first.");
            return;
        }

        if (file.length() == 0) {
            System.out.println("Ranker file is empty.");
            return;
        }

        try (FileInputStream fis = new FileInputStream(StudentManagementConstants.RANKER_FILE);
             ObjectInputStream ois = new ObjectInputStream(fis)) {

            System.out.println("-------------- TOP 3 RANKERS -----------------");
            System.out.println("\nID    | Name                 | Marks  | Gender   | Age  | ClassID");
            System.out.println("───────────────────────────────────────────────────────────────────");

            int rank = 1;
            while (true) {
                try {
                    Student student = (Student) ois.readObject();
                    System.out.printf("Rank %d: %s\n", rank++, student.toString());
                } catch (EOFException e) {
                    // End of file reached - this is expected
                    break;
                }
            }

        } catch (ClassNotFoundException e) {
            System.err.println("Error: Class not found - " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Error reading ranker file: " + e.getMessage());
        }
    }
    boolean isFileReadable(String filename) {
        File file = new File(filename);
        return file.exists() && file.canRead() && file.length() > 0;
    }
}
