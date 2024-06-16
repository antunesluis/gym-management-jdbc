package org.gym.handler;

import org.gym.dao.DaoFactory;
import org.gym.dao.ExerciseDao;

import java.sql.Connection;
import java.util.Scanner;

public class ExerciseHandler implements UserActionHandler {
    private final Scanner scanner;
    private final ExerciseDao exerciseDao;

    public ExerciseHandler(Connection conn, Scanner scanner) {
        this.scanner = scanner;
        this.exerciseDao = DaoFactory.createExerciseDao(conn);
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
                        addExercise();
                        break;
                    case "2":
                        updateExercise();
                        break;
                    case "3":
                        deleteExercise();
                        break;
                    case "4":
                        listExercise();
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
        System.out.println("1 - Add Exercise");
        System.out.println("2 - Update Exercise");
        System.out.println("3 - Delete Exercise");
        System.out.println("4 - List Students");
        System.out.println("0 - Back");
        System.out.print("Select an option: ");
    }
}
