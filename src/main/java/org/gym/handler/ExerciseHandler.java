package org.gym.handler;

import org.gym.dao.DaoFactory;
import org.gym.dao.ExerciseDao;
import org.gym.model.Exercise;
import org.gym.util.DbException;

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
            } catch (DbException e) {
                System.out.println("Database error: " + e.getMessage());
            }
            catch (Exception e) {
                System.out.println("An unexpected error occurred: " + e.getMessage());
            }
        } while (!option.equals("0"));
    }

    private void addExercise() {
        try {
            System.out.print("Name: ");
            String name = scanner.nextLine();

            System.out.print("Muscles activated: ");
            String musclesActivated = scanner.nextLine();

            Exercise exercise = new Exercise(null, name, musclesActivated);
            exerciseDao.addExercise(exercise);
            System.out.println("Exercise added successfully!");
        } catch (DbException e) {
            System.out.println("Error adding exercise: " + e.getMessage());
        }
    }

    private void updateExercise() {
        try {
            System.out.print("Enter the ID of the exercise to update: ");
            int id = Integer.parseInt(scanner.nextLine());
            Exercise existingExercise = exerciseDao.getExerciseById(id);

            if (existingExercise == null) {
                System.out.println("Exercise not found.");
                return;
            }
            System.out.print("Name (current: " + existingExercise.getName() + "): ");
            String name = scanner.nextLine();
            System.out.print("Muscles activated (current: " + existingExercise.getMusclesActivates() + "): ");
            String musclesActivated = scanner.nextLine();

            existingExercise.setName(name.isEmpty() ? existingExercise.getName() : name);
            existingExercise.setMusclesActivates(musclesActivated.isEmpty() ? existingExercise.getMusclesActivates() : musclesActivated);

            exerciseDao.updateExercise(existingExercise);
            System.out.println("Exercise updated successfully!");
        } catch (DbException e) {
            System.out.println("Error updating exercise: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Invalid ID format. Please enter a number.");
        }
    }

    private void deleteExercise() {
        try {
            System.out.print("Enter the ID of the exercise to delete: ");
            int id = Integer.parseInt(scanner.nextLine());

            exerciseDao.deleteExerciseById(id);
            System.out.println("Exercise deleted successfully!");
        } catch (DbException e) {
            System.out.println("Error deleting exercise: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Invalid ID format. Please enter a number.");
        }
    }

    private void listExercise() {
        try {
            System.out.println("Listing all exercises:");
            exerciseDao.getAllExercises().forEach(exercise ->
                    System.out.println(exercise.getId() + ": " + exercise.getName() + " - " + exercise.getMusclesActivates()));
        } catch (DbException e) {
            System.out.println("Error retrieving exercises: " + e.getMessage());
        }
    }


    private void displayMenu() {
        System.out.println("\n-- Manage Students --");
        System.out.println("1 - Add Exercise");
        System.out.println("2 - Update Exercise");
        System.out.println("3 - Delete Exercise");
        System.out.println("4 - Exercises Students");
        System.out.println("0 - Back");
        System.out.print("Select an option: ");
    }
}
