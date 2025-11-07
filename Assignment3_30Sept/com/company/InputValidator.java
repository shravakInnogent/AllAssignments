package com.company;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class InputValidator {
    private static final Pattern NAME_PATTERN = Pattern.compile("^[a-zA-Z][a-zA-Z ]{1,}$");
    private static final Pattern CITY_PATTERN = Pattern.compile("^[a-zA-Z][a-zA-Z ]{1,}$");
    private static final Pattern CLASS_PATTERN = Pattern.compile("^[a-zA-Z0-9]+$");
//    private static final Logger logger = Logger.getLogger(InputValidator.class.getName());
    // Validate and get integer input with range
    public static int getValidIntInput(Scanner sc, String prompt, int min, int max) {
        int value;
        while (true) {
            System.out.println(prompt);
            try {
                if (sc.hasNextInt()) {
                    value = sc.nextInt();
                    sc.nextLine(); // Consume newline
                    if (value >= min && value <= max) {
                        return value;
                    } else {
                        System.out.printf("Please enter value between %d and %d\n", min, max);
                    }
                } else {
                    System.out.println("Invalid input! Please enter a valid integer.");
                    sc.next(); // Clear invalid input
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input! Please enter a valid integer.");
                sc.nextLine(); // Clear buffer
            } catch (NoSuchElementException e) {
                System.out.println("Input stream closed or unavailable.");
                return min; // Default fallback
            }
        }
    }

    // Validate name (alphabets and spaces only)
    public static String getValidName(Scanner sc, String prompt) {

        while (true) {
            System.out.println(prompt);
            String name = sc.nextLine().trim();

            if (name.isEmpty()) {
                System.out.println("Name cannot be empty!");
                continue;
            }

            if (name.length() < StudentManagementConstants.MIN_NAME_LENGTH) {
                System.out.printf("Name must be at least %d characters!\n",
                        StudentManagementConstants.MIN_NAME_LENGTH);
                continue;
            }

            if (name.length() > StudentManagementConstants.MAX_NAME_LENGTH) {
                System.out.printf("Name must not exceed %d characters!\n",
                        StudentManagementConstants.MAX_NAME_LENGTH);
                continue;
            }

            Matcher matcher = NAME_PATTERN.matcher(name);
            if (!matcher.matches()) {
                System.out.println("Invalid name! Only letters and spaces allowed, must start with a letter.");
                continue;
            }
//            logger.info("Name is valid");
            return name;
        }
    }

    // Validate gender
    public static String getValidGender(Scanner sc, String prompt) {
        while (true) {
            System.out.println(prompt + " (Male/Female/Other):");
            String gender = sc.nextLine().trim();

            if (gender.isEmpty()) {
                System.out.println("Gender cannot be empty!");
                continue;
            }

            for (String validGender : StudentManagementConstants.VALID_GENDERS) {
                if (gender.equalsIgnoreCase(validGender)) {
                    return validGender; // Return standardized format
                }
            }

            System.out.println("Invalid gender! Please enter Male, Female, or Other.");
        }
    }

    // Validate city name
    public static String getValidCity(Scanner sc, String prompt) {
        while (true) {
            System.out.println(prompt);
            String city = sc.nextLine().trim();

            if (city.isEmpty()) {
                System.out.println("City name cannot be empty!");
                continue;
            }

            if (city.length() < StudentManagementConstants.MIN_CITY_LENGTH) {
                System.out.printf("City name must be at least %d characters!\n",
                        StudentManagementConstants.MIN_CITY_LENGTH);
                continue;
            }

            if (city.length() > StudentManagementConstants.MAX_CITY_LENGTH) {
                System.out.printf("City name must not exceed %d characters!\n",
                        StudentManagementConstants.MAX_CITY_LENGTH);
                continue;
            }

            Matcher matcher = CITY_PATTERN.matcher(city);
            if (!matcher.matches()) {
                System.out.println("Invalid city name! Only letters and spaces allowed.");
                continue;
            }

            return city;
        }
    }

    // Validate pincode (6 digits for India)
    public static int getValidPincode(Scanner sc, String prompt) {
        return getValidIntInput(sc, prompt,
                StudentManagementConstants.MIN_PINCODE,
                StudentManagementConstants.MAX_PINCODE);
    }

    // Validate class name
    public static String getValidClassName(Scanner sc, String prompt) {
        while (true) {
            System.out.println(prompt);
            String className = sc.nextLine().trim();

            if (className.isEmpty()) {
                System.out.println("⚠ Class name cannot be empty!");
                continue;
            }

            if (className.length() < StudentManagementConstants.MIN_CLASS_LENGTH) {
                System.out.printf("⚠ Class name must be at least %d character!\n",
                        StudentManagementConstants.MIN_CLASS_LENGTH);
                continue;
            }

            if (className.length() > StudentManagementConstants.MAX_CLASS_LENGTH) {
                System.out.printf("⚠ Class name must not exceed %d characters!\n",
                        StudentManagementConstants.MAX_CLASS_LENGTH);
                continue;
            }

            Matcher matcher = CLASS_PATTERN.matcher(className);
            if (!matcher.matches()) {
                System.out.println("Invalid class name! Only letters and numbers allowed.");
                continue;
            }

            return className.toUpperCase(); // Standardize to uppercase
        }
    }

    // Validate marks with custom exception handling
    public static int getValidMarks(Scanner sc, String prompt) {
        while (true) {
            int marks = getValidIntInput(sc, prompt,
                    StudentManagementConstants.MIN_MARKS,
                    StudentManagementConstants.MAX_MARKS);
            return marks;
        }
    }

    // Validate age with custom exception handling
    public static int getValidAge(Scanner sc, String prompt) {
        while (true) {
            int age = getValidIntInput(sc, prompt,
                    StudentManagementConstants.MIN_AGE,
                    StudentManagementConstants.MAX_AGE);
            return age;
        }
    }
}
