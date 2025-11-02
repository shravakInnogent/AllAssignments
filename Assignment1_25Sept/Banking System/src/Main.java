import java.util.HashSet;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

// Configuration constants for future maintenance
class BankingConstants {
    // Validation limits
    public static final double MIN_DEPOSIT = 1.0;
    public static final double MAX_DEPOSIT = 1000000.0;
    public static final double MIN_WITHDRAWAL = 1.0;
    public static final double MIN_BALANCE = 0.0;
    public static final double MAX_BALANCE = 100000000.0;
    
    // Loan limits
    public static final double MIN_LOAN_AMOUNT = 10000.0;
    public static final double MAX_LOAN_AMOUNT = 50000000.0;
    public static final int MIN_TENURE_YEARS = 1;
    public static final int MAX_TENURE_YEARS = 30;
    
    // Account validation
    public static final int MIN_NAME_LENGTH = 2;
    public static final int MAX_NAME_LENGTH = 100;
    public static final int MIN_ADDRESS_LENGTH = 5;
    public static final int MAX_ADDRESS_LENGTH = 200;
    public static final int MOBILE_NUMBER_LENGTH = 10;
    
    // Loan types
    public static final double HOME_LOAN_RATE = 8.5;
    public static final double GOLD_LOAN_RATE = 6.5;
}

// Custom exception for validation errors
class ValidationException extends Exception {
    public ValidationException(String message) {
        super(message);
    }
}

// Input validation utility class
class InputValidator {
    private static final Pattern NAME_PATTERN = Pattern.compile("^[a-zA-Z][a-zA-Z ]{1,}$");
    private static final Pattern MOBILE_PATTERN = Pattern.compile("^[6-9][0-9]{9}$");
    private static final Pattern ADDRESS_PATTERN = Pattern.compile("^[a-zA-Z0-9\\s,.-]+$");
    
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
                System.out.println("Input error occurred. Please try again.");
                return min; // Default fallback
            }
        }
    }
    
    // Validate and get double input with range
    public static double getValidDoubleInput(Scanner sc, String prompt, double min, double max) {
        double value;
        while (true) {
            System.out.println(prompt);
            try {
                if (sc.hasNextDouble()) {
                    value = sc.nextDouble();
                    sc.nextLine(); // Consume newline
                    
                    // Check for special values
                    if (Double.isNaN(value) || Double.isInfinite(value)) {
                        System.out.println("Invalid number! Please enter a valid amount.");
                        continue;
                    }
                    
                    if (value >= min && value <= max) {
                        return Math.round(value * 100.0) / 100.0; // Round to 2 decimal places
                    } else {
                        System.out.printf("Please enter amount between ₹%.2f and ₹%.2f\n", min, max);
                    }
                } else {
                    System.out.println("Invalid input! Please enter a valid number.");
                    sc.next(); // Clear invalid input
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input! Please enter a valid number.");
                sc.nextLine(); // Clear buffer
            } catch (NoSuchElementException e) {
                System.out.println("Input error occurred. Please try again.");
                return min; // Default fallback
            }
        }
    }
    
    // Validate name
    public static String getValidName(Scanner sc) throws ValidationException {
        while (true) {
            System.out.println("Enter Your Full Name:");
            String name = sc.nextLine().trim();
            
            if (name.isEmpty()) {
                System.out.println("Name cannot be empty!");
                continue;
            }
            
            if (name.length() < BankingConstants.MIN_NAME_LENGTH) {
                System.out.println("Name must be at least " + BankingConstants.MIN_NAME_LENGTH + " characters!");
                continue;
            }
            
            if (name.length() > BankingConstants.MAX_NAME_LENGTH) {
                System.out.println("Name must not exceed " + BankingConstants.MAX_NAME_LENGTH + " characters!");
                continue;
            }
            
            Matcher matcher = NAME_PATTERN.matcher(name);
            if (!matcher.matches()) {
                System.out.println("Invalid name! Only letters and spaces allowed, must start with a letter.");
                continue;
            }
            
            return name;
        }
    }
    
    // Validate mobile number
    public static String getValidMobileNumber(Scanner sc) {
        while (true) {
            System.out.println("Enter Your Mobile Number (10 digits, starting with 6-9):");
            String mobileNumber = sc.nextLine().trim();
            
            if (mobileNumber.isEmpty()) {
                System.out.println("Mobile number cannot be empty!");
                continue;
            }
            
            if (mobileNumber.length() != BankingConstants.MOBILE_NUMBER_LENGTH) {
                System.out.println("Mobile number must be exactly 10 digits!");
                continue;
            }
            
            Matcher matcher = MOBILE_PATTERN.matcher(mobileNumber);
            if (!matcher.matches()) {
                System.out.println("Invalid mobile number! Must be 10 digits starting with 6-9.");
                continue;
            }
            
            return mobileNumber;
        }
    }
    
    // Validate address
    public static String getValidAddress(Scanner sc) {
        while (true) {
            System.out.println("Enter Your Address:");
            String address = sc.nextLine().trim();
            
            if (address.isEmpty()) {
                System.out.println("Address cannot be empty!");
                continue;
            }
            
            if (address.length() < BankingConstants.MIN_ADDRESS_LENGTH) {
                System.out.println("Address must be at least " + BankingConstants.MIN_ADDRESS_LENGTH + " characters!");
                continue;
            }
            
            if (address.length() > BankingConstants.MAX_ADDRESS_LENGTH) {
                System.out.println("Address must not exceed " + BankingConstants.MAX_ADDRESS_LENGTH + " characters!");
                continue;
            }
            
            Matcher matcher = ADDRESS_PATTERN.matcher(address);
            if (!matcher.matches()) {
                System.out.println("Invalid address! Only letters, numbers, spaces, commas, dots, and hyphens allowed.");
                continue;
            }
            
            return address;
        }
    }
}

class AccountOpening {
    private final String name;
    private final String mobileNumber;
    private final String address;
    private final int accountNumber;
    private static final Set<Integer> usedAccountNumbers = new HashSet<>();
    private static final Random random = new Random();
    
    AccountOpening(String name, String mobileNumber, String address) throws ValidationException {
        if (name == null || name.trim().isEmpty()) {
            throw new ValidationException("Name cannot be null or empty");
        }
        if (mobileNumber == null || mobileNumber.trim().isEmpty()) {
            throw new ValidationException("Mobile number cannot be null or empty");
        }
        if (address == null || address.trim().isEmpty()) {
            throw new ValidationException("Address cannot be null or empty");
        }
        
        this.name = name.trim();
        this.mobileNumber = mobileNumber.trim();
        this.address = address.trim();
        this.accountNumber = generateUniqueAccountNumber();
    }
    
    private int generateUniqueAccountNumber() {
        int accNo;
        do {
            accNo = 100000 + random.nextInt(900000); // 6-digit account number
        } while (usedAccountNumbers.contains(accNo));
        usedAccountNumbers.add(accNo);
        return accNo;
    }
    String getName(){
        return name;
    }
     int getAccountNumber(){
        return accountNumber;
    }
     String getAddress(){
        return address;
    }
     String getMobileNumber(){
        return mobileNumber;
    }
    String getAccountType(){
        return "Account Type: Saving Account";
    } 
}

interface AccountOperations {
    boolean deposit(double amount);
    boolean withdraw(double amount);
    double getBalance();
}

abstract class Account implements AccountOperations {
    protected int accountNo;
    protected double balance;
    
    public Account(int accountNo, double initialBalance) {
        this.accountNo = accountNo;
        this.balance = Math.max(0, initialBalance); // Ensure non-negative
    }
    
    public abstract boolean deposit(double amount);
    public abstract double getBalance();
    public abstract boolean withdraw(double amount);
    
    public int getAccountNo() {
        return accountNo;
    }
}

class SavingAccount extends Account {
    public SavingAccount(int accountNo, double initialBalance) {
        super(accountNo, initialBalance);
    }
    
    @Override
    public boolean deposit(double amount) {
        if (amount <= 0) {
            System.out.println("Deposit amount must be positive!");
            return false;
        }
        
        if (amount < BankingConstants.MIN_DEPOSIT) {
            System.out.printf("Minimum deposit amount is ₹%.2f\n", BankingConstants.MIN_DEPOSIT);
            return false;
        }
        
        if (amount > BankingConstants.MAX_DEPOSIT) {
            System.out.printf("Maximum single deposit limit is ₹%.2f\n", BankingConstants.MAX_DEPOSIT);
            return false;
        }
        
        // Check for balance overflow
        if (balance + amount > BankingConstants.MAX_BALANCE) {
            System.out.printf("Transaction would exceed maximum balance limit of ₹%.2f\n", BankingConstants.MAX_BALANCE);
            return false;
        }
        
        balance += amount;
        System.out.printf("₹%.2f deposited successfully!\n", amount);
        System.out.printf("New Balance: ₹%.2f\n", balance);
        return true;
    }
    
    @Override
    public double getBalance() {
        return balance;
    }
    
    @Override
    public boolean withdraw(double amount) {
        if (amount <= 0) {
            System.out.println("Withdrawal amount must be positive!");
            return false;
        }
        
        if (amount < BankingConstants.MIN_WITHDRAWAL) {
            System.out.printf("Minimum withdrawal amount is ₹%.2f\n", BankingConstants.MIN_WITHDRAWAL);
            return false;
        }
        
        if (balance < amount) {
            System.out.printf("Insufficient funds! Available balance: ₹%.2f\n", balance);
            return false;
        }
        
        balance -= amount;
        System.out.printf("✓ ₹%.2f withdrawn successfully!\n", amount);
        System.out.printf("  Remaining Balance: ₹%.2f\n", balance);
        return true;
    }
}

class LoanOperations {
    private static final int EMI_PER_YEAR = 12;
    
    public double calculateEMI(double principal, int tenureYears, double loanInterestRate) 
            throws ValidationException {
        // Validate inputs
        if (principal <= 0) {
            throw new ValidationException("Principal amount must be positive");
        }
        if (tenureYears <= 0) {
            throw new ValidationException("Tenure must be positive");
        }
        if (loanInterestRate < 0) {
            throw new ValidationException("Interest rate cannot be negative");
        }
        
        // Handle zero interest rate edge case
        if (loanInterestRate == 0) {
            return principal / (tenureYears * EMI_PER_YEAR);
        }
        
        double monthlyInterestRate = loanInterestRate / (EMI_PER_YEAR * 100);
        int totalEMIs = tenureYears * EMI_PER_YEAR;
        
        // Check for potential overflow
        double powerValue = Math.pow(1 + monthlyInterestRate, totalEMIs);
        if (Double.isInfinite(powerValue) || Double.isNaN(powerValue)) {
            throw new ValidationException("Interest calculation resulted in invalid value");
        }
        
        double emi = (principal * monthlyInterestRate * powerValue) / (powerValue - 1);
        
        if (Double.isNaN(emi) || Double.isInfinite(emi)) {
            throw new ValidationException("EMI calculation resulted in invalid value");
        }
        
        return emi;
    }
}

public class Main {
    private static Account savingAccount = null;
    private static Scanner scanner = new Scanner(System.in);
    
    public static void main(String[] args) {

        System.out.println("║   WELCOME TO SECURE BANKING SYSTEM    ║");
    
        try {
            takeInput();
        } catch (Exception e) {
            System.out.println("Critical error occurred: " + e.getMessage());
        } finally {
            if (scanner != null) {
                scanner.close();
            }
        }
    }
    
    public static void takeInput() {
        boolean exit = false;
        
        while (!exit) {
            try {
                System.out.println("\n--------------- MAIN MENU -----------------");
                System.out.println("1. Open Account");
                System.out.println("2. Deposit Money");
                System.out.println("3. Withdraw Money");
                System.out.println("4. Check Balance");
                System.out.println("5. Loan Enquiry");
                System.out.println("6. Exit");
                
                int choice = InputValidator.getValidIntInput(scanner, "Enter your choice (1-6):", 1, 6);
                
                switch (choice) {
                    case 1 -> savingAccount = accountOpen();
                    
                    case 2 -> {
                        if (!checkAccountExists()) break;
                        double depositAmount = InputValidator.getValidDoubleInput(
                            scanner,
                            "Enter amount to deposit:",
                            BankingConstants.MIN_DEPOSIT,
                            BankingConstants.MAX_DEPOSIT
                        );
                        savingAccount.deposit(depositAmount);
                    }
                    
                    case 3 -> {
                        if (!checkAccountExists()) break;
                        if (savingAccount.getBalance() <= 0) {
                            System.out.println("Insufficient balance for withdrawal!");
                            break;
                        }
                        double withdrawAmount = InputValidator.getValidDoubleInput(
                            scanner,
                            "Enter amount to withdraw:",
                            BankingConstants.MIN_WITHDRAWAL,
                            savingAccount.getBalance()
                        );
                        savingAccount.withdraw(withdrawAmount);
                    }
                    
                    case 4 -> {
                        if (!checkAccountExists()) break;
                        System.out.printf("Account Number: %d\n", savingAccount.getAccountNo());
                        System.out.printf("Current Balance: ₹%.2f\n", savingAccount.getBalance());
                    }
                    
                    case 5 -> loanMethod();
                    
                    case 6 -> {
                        exit = true;
                        System.out.println("║  Thank you for banking with us!       ║");
                        System.out.println("║  Visit again! 😊                       ║");
                    }
                }
            } catch (Exception e) {
                System.out.println("An error occurred: " + e.getMessage());
                scanner.nextLine(); // Clear buffer
            }
        }
    }
    
    private static boolean checkAccountExists() {
        if (savingAccount == null) {
            System.out.println("\nPlease open an account first!");
            return false;
        }
        return true;
    }
    
    public static Account accountOpen() {
        try {
            System.out.println("\n--------------ACCOUNT OPENING -------------");
            
            String name = InputValidator.getValidName(scanner);
            String mobileNumber = InputValidator.getValidMobileNumber(scanner);
            String address = InputValidator.getValidAddress(scanner);
            
            AccountOpening acc = new AccountOpening(name, mobileNumber, address);
            acc.getName();
            acc.getAddress();
            acc.getMobileNumber();
            acc.getAccountType();
            acc.getAccountNumber();
            
            return new SavingAccount(acc.getAccountNumber(), 0.0);
            
        } catch (ValidationException e) {
            System.out.println("Validation error: " + e.getMessage());
            System.out.println("Please try again.");
            return null;
        } catch (Exception e) {
            System.out.println("Error creating account: " + e.getMessage());
            return null;
        }
    }
    
    public static void loanMethod() {
        try {
            System.out.println("\n-------------LOAN CALCULATOR --------------");
            
            double principal = InputValidator.getValidDoubleInput(
                scanner,
                "Enter loan amount:",
                BankingConstants.MIN_LOAN_AMOUNT,
                BankingConstants.MAX_LOAN_AMOUNT
            );
            
            int tenureYears = InputValidator.getValidIntInput(
                scanner,
                "Enter time period (in years):",
                BankingConstants.MIN_TENURE_YEARS,
                BankingConstants.MAX_TENURE_YEARS
            );
            
            System.out.println("\nSelect Loan Type:");
            System.out.println("1. Home Loan (8.5% interest)");
            System.out.println("2. Gold Loan (6.5% interest)");
            
            int loanType = InputValidator.getValidIntInput(scanner, "Enter choice (1 or 2):", 1, 2);
            
            LoanOperations loan = new LoanOperations();
            double interestRate = (loanType == 1) 
                ? BankingConstants.HOME_LOAN_RATE 
                : BankingConstants.GOLD_LOAN_RATE;
            
            double emi = loan.calculateEMI(principal, tenureYears, interestRate);
            double totalAmountPaid = emi * tenureYears * 12;
            double totalInterest = totalAmountPaid - principal;
            
            System.out.println("\n------------ LOAN DETAILS-------------");
            System.out.printf("Loan Amount: ₹%.2f\n", principal);
            System.out.printf("Interest Rate: %.2f%% p.a.\n", interestRate);
            System.out.printf("Tenure: %d years (%d months)\n", tenureYears, tenureYears * 12);
            System.out.println("────────────────────────────────────");
            System.out.printf("Monthly EMI: ₹%.2f\n", emi);
            System.out.printf("Total Interest: ₹%.2f\n", totalInterest);
            System.out.printf("Total Amount Payable: ₹%.2f\n", totalAmountPaid);
            
        } catch (ValidationException e) {
            System.out.println("Validation error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error calculating loan: " + e.getMessage());
        }
    }
}
