package org.gym.handler;

import org.gym.dao.DaoFactory;
import org.gym.dao.ExerciseDao;
import org.gym.model.Exercise;
import org.gym.util.ConsoleUtils;
import org.gym.util.DbException;
import org.gym.util.ValidationException;

import java.sql.Connection;
import java.util.List;
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
            int id = getValidExerciseId();

            Exercise existingExercise = exerciseDao.getExerciseById(id);
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
        } catch (ValidationException e) {
            System.out.println("Validation error: " + e.getMessage());
        }
    }

    private void deleteExercise() {
        try {
            int id = getValidExerciseId();
            exerciseDao.deleteExerciseById(id);
            System.out.println("Exercise deleted successfully!");
        } catch (DbException e) {
            System.out.println("Error deleting exercise: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Invalid ID format. Please enter a number.");
        } catch (ValidationException e) {
            System.out.println("Validation error: " + e.getMessage());
        }
    }

    private void listExercise() {
        try {
            List<Exercise> exercises = exerciseDao.getAllExercises();

            if (exercises.isEmpty()) {
                System.out.println("No exercises found.");
                return;
            }

            System.out.println("Listing all exercises:");
            for (Exercise exercise : exercises) {
                System.out.println(exercise);
            }
        } catch (DbException e) {
            System.out.println("Error while listing exercises: " + e.getMessage());
        }
    }

    private int getValidExerciseId() throws ValidationException, NumberFormatException {
        System.out.print("Enter the ID of the Plan: ");
        int id = Integer.parseInt(scanner.nextLine());
        if (!exerciseDao.existsById(id)) {
            throw new ValidationException("Exercise not found.");
        }
        return id;
    }

    private void displayMenu() {
        System.out.println("\n-- Manage Exercises --");
        System.out.println("1 - Add Exercise");
        System.out.println("2 - Update Exercise");
        System.out.println("3 - Delete Exercise");
        System.out.println("4 - List Exercises");
        System.out.println("0 - Back");
        System.out.print("Select an option: ");
    }
}
