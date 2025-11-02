
import java.util.Scanner;

class Factorial {

    int fact(int n) {
        if (n == 0 || n == 1) return 1;
        return n * fact(n - 1);
    }

    int factIterator(int n) {
        int ans = 1;
        for (int i = 1; i <= n; i++) {
            ans *= i;
        }
        return ans;
    }
}
public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n;
        while (true) {
            System.out.print("Enter a number: ");
            try {
                n = sc.nextInt();
                if (n < 0) {
                    System.out.println("Please enter a non-negative number.");
                    continue;
                }
                break;
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter an integer.");
                sc.nextLine();
            }
        }

        int choice;
        while (true) {
            System.out.println("Choose method:\n1. Recursion\n2. Iteration");
            try {
                choice = sc.nextInt();
                if (choice == 1 || choice == 2) break;
                System.out.println("Enter 1 or 2 only.");
            } catch (Exception e) {
                System.out.println("Invalid input. Enter number only.");
                sc.nextLine();
            }
        }
        Factorial obj = new Factorial();
        int ans = (choice == 1) ? obj.fact(n) : obj.factIterator(n);

        System.out.println("Factorial of " + n + " = " + ans);
    }
}
