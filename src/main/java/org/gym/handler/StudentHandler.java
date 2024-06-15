package org.gym.handler;

import org.gym.dao.DaoFactory;
import org.gym.dao.StudentDao;
import org.gym.model.Student;

import java.sql.Connection;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class StudentHandler implements UserActionHandler {
    private final Scanner scanner;
    private final StudentDao studentDao;

    public StudentHandler(Connection conn, Scanner scanner) {
        this.scanner = scanner;
        this.studentDao = DaoFactory.createStudentDao(conn);
    }

    @Override
    public void execute() {
        String option;
        do {
            displayMenu();
            option = scanner.nextLine();
            try {
                switch (option) {
                    case "1":
                        addStudent();
                        break;
                    case "2":
                        updateStudent();
                        break;
                    case "3":
                        deleteStudent();
                        break;
                    case "4":
                        listStudents();
                        break;
                    case "0":
                        System.out.println("Returning to the main menu...");
                        return;
                    default:
                        System.out.println("Invalid option. Please try again.");
                        break;
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        } while (!option.equals("0"));
    }

    private void displayMenu() {
        System.out.println("\n-- Manage Students --");
        System.out.println("1 - Add Student");
        System.out.println("2 - Update Student");
        System.out.println("3 - Delete Student");
        System.out.println("4 - List Students");
        System.out.println("0 - Back");
        System.out.print("Select an option: ");
    }

    private void addStudent() {
        System.out.print("CPF: ");
        String cpf = scanner.nextLine();
        System.out.print("Name: ");
        String name = scanner.nextLine();
        System.out.print("Date of Birth (YYYY-MM-DD): ");
        String birthDate = scanner.nextLine();

        Student student = new Student(null, cpf, name, LocalDate.parse(birthDate));
        studentDao.addStudent(student);
        System.out.println("Student added successfully!");
    }

    private void updateStudent() {
        System.out.print("Student ID: ");
        int id = Integer.parseInt(scanner.nextLine());
        System.out.print("CPF: ");
        String cpf = scanner.nextLine();
        System.out.print("Name: ");
        String name = scanner.nextLine();
        System.out.print("Date of Birth (YYYY-MM-DD): ");
        String birthDate = scanner.nextLine();

        Student student = new Student(id, cpf, name, LocalDate.parse(birthDate));
        studentDao.updateStudent(student);
        System.out.println("Student updated successfully!");
    }

    private void deleteStudent() {
        System.out.print("Student ID: ");
        int id = Integer.parseInt(scanner.nextLine());
        studentDao.deleteStudentById(id);
        System.out.println("Student deleted successfully!");
    }

    private void listStudents() {
        List<Student> students = studentDao.getAllStudents();
        for (Student student : students) {
            System.out.println(student);
        }
    }
}