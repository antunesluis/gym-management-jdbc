package org.gym.handler;

import org.gym.dao.DaoFactory;
import org.gym.dao.ExerciseDao;
import org.gym.dao.ExerciseSetDao;
import org.gym.dao.ExerciseSetExerciseDao;
import org.gym.model.Exercise;
import org.gym.model.ExerciseSet;
import org.gym.model.ExerciseSetExercise;
import org.gym.util.ConsoleUtils;
import org.gym.util.DbException;
import org.gym.util.ValidationException;

import java.sql.Connection;
import java.util.List;
import java.util.Scanner;

public class ExerciseSetHandler implements UserActionHandler {
    private final Scanner scanner;
    private final ExerciseSetDao exerciseSetDao;
    private final ExerciseDao exerciseDao;
    private final ExerciseSetExerciseDao exerciseSetExerciseDao;

    public ExerciseSetHandler(Connection conn, Scanner scanner) {
        this.scanner = scanner;
        this.exerciseSetDao = DaoFactory.createExerciseSetDao(conn);
        this.exerciseDao = DaoFactory.createExerciseDao(conn);
        this.exerciseSetExerciseDao = DaoFactory.createExerciseSetExerciseDao(conn);
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
                        addExerciseSet();
                        break;
                    case "2":
                        updateExerciseSet();
                        break;
                    case "3":
                        deleteExerciseSet();
                        break;
                    case "4":
                        listExerciseSet();
                        break;
                    case "5":
                        addExerciseInExerciseSet();
                        break;
                    case "6":
                        deleteExerciseInExerciseSet();
                        break;
                    case "7":
                        listExercisesInExerciseSet();
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

    private void addExerciseSet() {
        try {
            System.out.print("Series count: ");
            int seriesCount = Integer.parseInt(scanner.nextLine());

            System.out.print("Min reps: ");
            int minReps = Integer.parseInt(scanner.nextLine());

            System.out.print("Max reps: ");
            int maxReps = Integer.parseInt(scanner.nextLine());

            System.out.print("Rest time: ");
            int restTime = Integer.parseInt(scanner.nextLine());

            ExerciseSet exerciseSet = new ExerciseSet(null, seriesCount, minReps, maxReps, restTime);
            exerciseSetDao.addExerciseSet(exerciseSet);
            System.out.println("Exercise set added successfully!");
        } catch (DbException e) {
            System.out.println("Error adding Exercise set: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a valid number.");
        }
    }

    private void updateExerciseSet() {
        try {
            int id = getValidExerciseSetId();
            ExerciseSet existingExerciseSet = exerciseSetDao.getExerciseSetById(id);

            System.out.print("Series count (current: " + existingExerciseSet.getSeriesCount() + "): ");
            int seriesCount = Integer.parseInt(scanner.nextLine());

            System.out.print("Min reps (current: " + existingExerciseSet.getMinReps() + "): ");
            int minReps = Integer.parseInt(scanner.nextLine());

            System.out.print("Max reps (current: " + existingExerciseSet.getMaxReps() + "): ");
            int maxReps = Integer.parseInt(scanner.nextLine());

            System.out.print("Rest time (current: " + existingExerciseSet.getRestTime() + "): ");
            int restTime = Integer.parseInt(scanner.nextLine());

            ExerciseSet updatedExerciseSet = new ExerciseSet(id, seriesCount, minReps, maxReps, restTime);
            exerciseSetDao.updateExerciseSet(updatedExerciseSet);
            System.out.println("Exercise set updated successfully!");
        } catch (DbException e) {
            System.out.println("Error updating exercise set: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Invalid ID format. Please enter a number.");
        } catch (ValidationException e) {
            System.out.println("Validation error: " + e.getMessage());
        }
    }

    private void deleteExerciseSet() {
        try {
            int id = getValidExerciseSetId();
            exerciseSetDao.deleteExerciseSetById(id);
            System.out.println("Exercise Set deleted successfully!");
        } catch (DbException e) {
            System.out.println("Error deleting Exercise Set: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Invalid ID format. Please enter a number");
        } catch (ValidationException e) {
            System.out.println("Validation error: " + e.getMessage());
        }
    }

    private void listExerciseSet() {
        try {
            List<ExerciseSet> exerciseSets = exerciseSetDao.getAllExercisesSets();

            if (exerciseSets.isEmpty()) {
                System.out.println("No Exercise sets found.");
                return;
            }

            System.out.println("");
            System.out.println("Listing all Exercise Sets:");
            for (ExerciseSet exerciseSet : exerciseSets) {
                System.out.println(exerciseSet);
            }
        } catch (DbException e) {
            System.out.println("Error while listing Exercise Sets: " + e.getMessage());
        }
    }

    private void addExerciseInExerciseSet() {
       try {
           int idExerciseSet = getValidExerciseSetId();
           int idExercise = getValidExerciseId();

           ExerciseSetExercise exerciseSetExercise = new ExerciseSetExercise(idExerciseSet, idExercise);
           exerciseSetExerciseDao.insert(exerciseSetExercise);
           System.out.println("Exercise added to Exercise Set successfully!");
       } catch (DbException e) {
           System.out.println("Error adding Exercise in Exercise Set: " + e.getMessage());
       } catch (NumberFormatException e) {
           System.out.println("Invalid ID format. Please enter a number");
       } catch (ValidationException e) {
           System.out.println("Validation error: " + e.getMessage());
       }
    }

    private void deleteExerciseInExerciseSet() {
        try {
            int idExerciseSet = getValidExerciseSetId();
            int idExercise = getValidExerciseId();

            exerciseSetExerciseDao.deleteByExerciseSetIdAndExerciseId(idExerciseSet, idExercise);
            System.out.println("Exercise deleted in Exercise set successfully!");
        } catch (DbException e) {
            System.out.println("Error deleting Exercise in Exercise set: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Invalid ID format. Please enter a number");
        } catch (ValidationException e) {
            System.out.println("Validation error: " + e.getMessage());
        }
    }

    private void listExercisesInExerciseSet() {
        try {
            int idExerciseSet = getValidExerciseSetId();
            List<ExerciseSetExercise> exerciseSetExercises = exerciseSetExerciseDao.findByExerciseSetId(idExerciseSet);

            if (exerciseSetExercises.isEmpty()) {
                System.out.println("No memberships found.");
                return;
            }

            System.out.println("Listing all Exercises in Exercise Set:");
            for (ExerciseSetExercise exerciseSetExercise : exerciseSetExercises) {
                Exercise exercise = exerciseDao.getExerciseById(exerciseSetExercise.getExerciseId());
                System.out.println(exercise);
            }
        } catch (DbException e) {
            System.out.println("Error while listing memberships: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Invalid ID format. Please enter a number");
        }
    }

    private int getValidExerciseSetId() throws ValidationException, NumberFormatException {
        System.out.print("Enter the ID of the Exercise Set: ");
        int id = Integer.parseInt(scanner.nextLine());
        if (!exerciseSetDao.existsById(id)) {
            throw new ValidationException("Exercise Set not found.");
        }
        return id;
    }

    private int getValidExerciseId() throws ValidationException, NumberFormatException {
        System.out.print("Enter the ID of the Exercise: ");
        int id = Integer.parseInt(scanner.nextLine());
        if (!exerciseDao.existsById(id)) {
            throw new ValidationException("Exercise not found.");
        }
        return id;
    }

    private void displayMenu() {
        System.out.println("\n-- Manage Exercise Set --");
        System.out.println("1 - Create Exercise Set");
        System.out.println("2 - Update Exercise Set");
        System.out.println("3 - Delete Exercise Set");
        System.out.println("4 - List Exercises Set");
        System.out.println("5 - Add exercise to Exercise Set");
        System.out.println("6 - Delete exercise from the exercise set");
        System.out.println("7 - List exercises from the exercise set");
        System.out.println("0 - Back");
        System.out.print("Select an option: ");
    }
}
