
//public class EmployeeManagementSystem {
//
//}

2


import java.io.*;
import java.util.*;

class Employee implements Serializable {
    private static final long serialVersionUID = 1L;
    private int id;
    private String name;
    private String position;
    private double salary;
    private String department;
    private String hireDate;

    public Employee(int id, String name, String position, double salary, String department, String hireDate) {
        this.id = id;
        this.name = name;
        this.position = position;
        this.salary = salary;
        this.department = department;
        this.hireDate = hireDate;
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getHireDate() {
        return hireDate;
    }

    public void setHireDate(String hireDate) {
        this.hireDate = hireDate;
    }

    @Override
    public String toString() {
        return "ID: " + id + ", Name: " + name + ", Position: " + position + ", Salary: " + salary +
                ", Department: " + department + ", Hire Date: " + hireDate;
    }
}

class FileManager {
    private static final String FILE_NAME = "employees.dat";

    public static void writeEmployees(List<Employee> employees) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(employees);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    public static List<Employee> readEmployees() {
        List<Employee> employees = new ArrayList<>();
        File file = new File(FILE_NAME);
        if (!file.exists() || file.length() == 0) {
            return employees;
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            employees = (List<Employee>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return employees;
    }
}

public class EmployeeManagementSystem {
    private static List<Employee> employees = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        loadEmployees(); // Load employees from file

        while (true) {
            System.out.println("\nEmployee Management System");
            System.out.println("1. Add Employee");
            System.out.println("2. View Employees");
            System.out.println("3. Update Employee Position");
            System.out.println("4. Delete Employee");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    addEmployee();
                    break;
                case 2:
                    viewEmployees();
                    break;
                case 3:
                    updateEmployeePosition();
                    break;
                case 4:
                    deleteEmployee();
                    break;
                case 5:
                    saveEmployees(); // Save employees to file before exiting
                    System.out.println("Exiting... Thank you!");
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void loadEmployees() {
        employees = FileManager.readEmployees();
    }

    private static void saveEmployees() {
        FileManager.writeEmployees(employees);
    }

    private static void addEmployee() {
        System.out.print("Enter employee ID: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        System.out.print("Enter employee name: ");
        String name = scanner.nextLine();

        System.out.print("Enter employee position: ");
        String position = scanner.nextLine();

        System.out.print("Enter employee salary: ");
        double salary = scanner.nextDouble();
        scanner.nextLine(); // Consume newline

        System.out.print("Enter employee department: ");
        String department = scanner.nextLine();

        System.out.print("Enter employee hire date: ");
        String hireDate = scanner.nextLine();

        employees.add(new Employee(id, name, position, salary, department, hireDate));
        System.out.println("Employee added successfully.");
    }

    private static void viewEmployees() {
        if (employees.isEmpty()) {
            System.out.println("No employees to display.");
            return;
        }
        System.out.println("List of Employees:");
        for (Employee emp : employees) {
            System.out.println(emp);
        }
    }

    private static void updateEmployeePosition() {
        System.out.print("Enter employee ID to update position: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        boolean found = false;
        for (Employee emp : employees) {
            if (emp.getId() == id) {
                System.out.print("Enter new position for employee " + emp.getName() + ": ");
                String newPosition = scanner.nextLine();
                emp.setPosition(newPosition);
                System.out.println("Position updated successfully.");
                found = true;
                break;
            }
        }
        if (!found) {
            System.out.println("Employee with ID " + id + " not found.");
        }
    }

    private static void deleteEmployee() {
        System.out.print("Enter employee ID to delete: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        Iterator<Employee> iterator = employees.iterator();
        boolean found = false;
        while (iterator.hasNext()) {
            Employee emp = iterator.next();
            if (emp.getId() == id) {
                iterator.remove();
                System.out.println("Employee deleted successfully.");
                found = true;
                break;
            }
        }
        if (!found) {
            System.out.println("Employee with ID " + id + " not found.");
        }
    }
}
