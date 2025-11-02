import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

class Employee implements Comparable<Employee> {
    int id;
    String name;
    String department;
    double salary;

    Employee(int id, String name, String department, double salary) {
        this.id = id;
        this.department = department;
        this.salary = salary;
        this.name = name;
    }
    public String toString() {
        return id + " " + name + " " + department + " " + salary;
    }
    public int compareTo (Employee em){
            return Double.compare(em.salary, this.salary);
    }
        static class departmentNameSalaryComparator implements Comparator<Employee> {
            public int compare(Employee e1, Employee e2) {
                int cmp = e1.department.compareTo(e2.department);
                if (cmp != 0) return cmp;
                cmp = e1.name.compareTo(e2.name);
                if (cmp != 0) return cmp;
                return Double.compare(e2.salary, e1.salary);
            }
        }
    }
public class Main {
    public static void main(String[] args){
        List <Employee> employeeList =  new ArrayList<>();
        employeeList.add(new Employee(1,"Ritik","Sales",38000));
        employeeList.add(new Employee(2,"Shravak","IT",37000));
        employeeList.add(new Employee(3,"Chanchal", "Teaching", 20000));
        employeeList.add(new Employee(4,"Kuldeep", "Hr",25000));
        employeeList.add(new Employee(5,"Akshay", "IT",32000));
        employeeList.add(new Employee(6,"Deepak", "Sales",37000));
        employeeList.add(new Employee(7,"Chanchal", "Teaching",23000));

        employeeList.sort(new Employee.departmentNameSalaryComparator());
        System.out.println("Sorted by Department, Name, Salary:");
        Iterator<Employee> it = employeeList.iterator();
        while(it.hasNext()){
            System.out.println(it.next());
        }
        Collections.sort(employeeList);
        System.out.println("\nSorted by Salary Descending:");
        Iterator<Employee> ite  = employeeList.iterator();
        while(ite.hasNext()){
            System.out.println(ite.next());
        }
    }
}