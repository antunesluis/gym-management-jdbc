package org.gym.handler;

import org.gym.dao.DaoFactory;
import org.gym.dao.StudentDao;
import org.gym.model.Student;
import org.gym.util.ConsoleUtils;
import org.gym.util.DbException;
import org.gym.util.ValidationException;

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
            } catch (ValidationException e) {
                System.out.println("Validation error: " + e.getMessage());
            } catch (DbException e) {
                System.out.println("Database error: " + e.getMessage());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
            catch (Exception e) {
                System.out.println("An unexpected error occurred: " + e.getMessage());
            }
            ConsoleUtils.waitForEnter();
        } while (!option.equals("0"));
    }

    private void addStudent() {
        try {
            System.out.print("CPF: ");
            String cpf = scanner.nextLine();
            System.out.print("Name: ");
            String name = scanner.nextLine();
            System.out.print("Date of Birth (YYYY-MM-DD): ");
            String birthDate = scanner.nextLine();

            Student student = new Student(null, cpf, name, LocalDate.parse(birthDate));
            studentDao.addStudent(student);
            System.out.println("Student added successfully!");
        } catch (DbException e ) {
            System.out.println("Error adding student: " + e.getMessage());
        }
    }

    private void updateStudent() {
        try {
            int id = getValidStudentId();
            Student existingStudent = studentDao.getStudentById(id);

            System.out.print("CPF (current: " + existingStudent.getCpf() + "): ");
            String cpf = scanner.nextLine();

            System.out.print("Name (current: " + existingStudent.getName() + "): ");
            String name = scanner.nextLine();

            System.out.print("Date of Birth (YYYY-MM-DD) (current: " + existingStudent.getBirthDate() + "): ");
            String birthDate = scanner.nextLine();

            Student student = new Student(id, cpf, name, LocalDate.parse(birthDate));
            studentDao.updateStudent(student);
            System.out.println("Student updated successfully!");
        } catch (DbException e ) {
            System.out.println("Error updating Student: " + e.getMessage());
        } catch (NumberFormatException e ) {
            System.out.println("Invalid ID format. Please enter a number");
        } catch (ValidationException e) {
            System.out.println("Validation error: " + e.getMessage());
        }
    }

    private void deleteStudent() {
        try {
            int id = getValidStudentId();

            studentDao.deleteStudentById(id);
            System.out.println("Student deleted successfully!");
        } catch (DbException e ) {
            System.out.println("Error deleting student: " + e.getMessage());
        } catch (NumberFormatException e ) {
            System.out.println("Invalid ID format. Please enter a number");
        } catch (ValidationException e ) {
            System.out.println("Validation error: " + e.getMessage());
        }
    }

    private void listStudents() {
        try {
            List<Student> students = studentDao.getAllStudents();

            if (students.isEmpty()) {
                System.out.println("No students found.");
                return;
            }

            System.out.println("Listing all students:");
            for (Student student : students) {
                System.out.println(student);
            }
        } catch (DbException e) {
            System.out.println("Error while listing students: " + e.getMessage());
        }
    }

    private int getValidStudentId() throws ValidationException, NumberFormatException {
        System.out.print("Enter the ID of the Student: ");
        int id = Integer.parseInt(scanner.nextLine());
        if (!studentDao.existsById(id)) {
            throw new ValidationException("Student not found.");
        }
        return id;
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
}
