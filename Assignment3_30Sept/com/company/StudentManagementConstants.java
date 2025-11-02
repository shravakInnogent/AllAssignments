package com.company;

public class StudentManagementConstants {
    // Student validation limits (UPDATED per requirement)
    public static final int MIN_AGE = 5;
    public static final int MAX_AGE = 20;
    public static final int MIN_MARKS = 0;
    public static final int MAX_MARKS = 100;
    public static final int PASSING_MARKS = 50;  // NEW: Pass/Fail threshold
    public static final int MIN_NAME_LENGTH = 2;
    public static final int MAX_NAME_LENGTH = 50;

    // Address validation limits
    public static final int MIN_PINCODE = 100000;
    public static final int MAX_PINCODE = 999999;
    public static final int MIN_CITY_LENGTH = 2;
    public static final int MAX_CITY_LENGTH = 50;

    // Class validation
    public static final int MIN_CLASS_LENGTH = 1;
    public static final int MAX_CLASS_LENGTH = 10;

    // Gender validation
    public static final String[] VALID_GENDERS = {"Male", "Female", "Other"};

    // Menu limits
    public static final int MIN_MENU_CHOICE = 1;
    public static final int MAX_MENU_CHOICE = 10;  // Extended menu

    // File paths
    public static final String STUDENT_FILE = "student.csv";
    public static final String RANKER_FILE = "RankHolderStudent.csv";

    // Top rankers limit
    public static final int TOP_RANKERS_COUNT = 3;

    // Pagination
    public static final int DEFAULT_PAGE_SIZE = 10;
}
