package org.gym.handler;

import org.gym.dao.DaoFactory;
import org.gym.dao.ExerciseDao;
import org.gym.dao.WorkoutDao;
import org.gym.dao.WorkoutRecordDao;
import org.gym.model.WorkoutRecord;
import org.gym.util.ConsoleUtils;
import org.gym.util.DbException;
import org.gym.util.ValidationException;

import java.sql.Connection;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class WorkoutRecordHandler implements UserActionHandler {
    private final Scanner scanner;
    private final WorkoutRecordDao workoutRecordDao;
    private final WorkoutDao workoutDao;
    private final ExerciseDao exerciseDao;

    public WorkoutRecordHandler(Connection conn, Scanner scanner) {
        this.scanner = scanner;
        this.workoutRecordDao = DaoFactory.createWorkoutRecordDao(conn);
        this.workoutDao = DaoFactory.createWorkoutDao(conn);
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
                        addWorkoutRecord();
                        break;
                    case "2":
                        updateWorkoutRecord();
                        break;
                    case "3":
                        deleteWorkoutRecord();
                        break;
                    case "4":
                        listWorkoutRecordsinWorkout();
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
            } catch (Exception e) {
                System.out.println("An unexpected error occurred: " + e.getMessage());
            }
            ConsoleUtils.waitForEnter();
        } while (!option.equals("0"));
    }

    private void addWorkoutRecord() {
        try {
            int workoutId = getValidWorkoutId();
            int exerciseId = getValidExerciseId();

            System.out.print("Was this exercise completed? (y/n): ");
            String exerciseCompletedInput = scanner.nextLine();
            boolean exerciseCompleted = parseBoolean(exerciseCompletedInput);

            System.out.print("Load used in the exercise: ");
            int load = Integer.parseInt(scanner.nextLine());

            LocalDate completionDate = parseDate("Exercise completion date (yyyy-mm-dd): ");

            WorkoutRecord workoutRecord = new WorkoutRecord(null, workoutId, exerciseId, exerciseCompleted, load, completionDate);
            workoutRecordDao.addWorkoutRecord(workoutRecord);
            System.out.println("Workout record added successfully.");
        } catch (ValidationException e) {
            System.out.println("Validation error: " + e.getMessage());
        } catch (DbException e) {
            System.out.println("Error adding workout record: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a valid number.");
        }
    }

    private void updateWorkoutRecord() {
        try {
            int id = getValidWorkoutRecordId();
            WorkoutRecord existingWorkoutRecord = workoutRecordDao.getWorkoutRecordById(id);

            int workoutId = getValidWorkoutId();
            int exerciseId = getValidExerciseId();

            System.out.print("Was this exercise completed (current: " + (existingWorkoutRecord.getExerciseCompleted() ? "y" : "n") + ")? (y/n): ");
            String exerciseCompletedInput = scanner.nextLine();
            boolean exerciseCompleted = parseBoolean(exerciseCompletedInput);

            System.out.print("Load used in the exercise (current: " + existingWorkoutRecord.getLoad() + "): ");
            int load = Integer.parseInt(scanner.nextLine());

            LocalDate completionDate = parseDate("Exercise completion date (yyyy-mm-dd) (current: " + existingWorkoutRecord.getCompletionDate() + "): ");

            WorkoutRecord updatedWorkoutRecord = new WorkoutRecord(id, workoutId, exerciseId, exerciseCompleted, load, completionDate);
            workoutRecordDao.updateWorkoutRecord(updatedWorkoutRecord);
            System.out.println("Workout record updated successfully.");
        } catch (DbException e) {
            System.out.println("Error updating workout record: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Invalid ID format. Please enter a number.");
        } catch (ValidationException e) {
            System.out.println("Validation error: " + e.getMessage());
        }
    }

    private void deleteWorkoutRecord() {
        try {
            int id = getValidWorkoutRecordId();
            workoutRecordDao.deleteWorkoutRecordById(id);
            System.out.println("Workout record deleted successfully!");
        } catch (DbException e) {
            System.out.println("Error deleting workout record: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Invalid ID format. Please enter a number.");
        } catch (ValidationException e) {
            System.out.println("Validation error: " + e.getMessage());
        }
    }

    private void listWorkoutRecordsinWorkout() {
        try {
            int workoutId = getValidWorkoutId();

            List<WorkoutRecord> records = workoutRecordDao.getWorkoutRecordsByWorkoutId(workoutId);

            if (records.isEmpty()) {
                System.out.println("No workout records found for the specified workout.");
                return;
            }

            System.out.println("Listing all workout records for workout ID " + workoutId + ":");
            for (WorkoutRecord record : records) {
                System.out.println(record);
            }
        } catch (DbException e) {
            System.out.println("Error while listing workout records: " + e.getMessage());
        } catch (ValidationException e) {
            System.out.println("Validation error: " + e.getMessage());
        }
    }

    private int getValidWorkoutRecordId() throws ValidationException, NumberFormatException {
        System.out.print("Enter the workout record ID: ");
        int id = Integer.parseInt(scanner.nextLine());
        if (!workoutRecordDao.existsById(id)) {
            throw new ValidationException("Workout record not found.");
        }
        return id;
    }

    private int getValidWorkoutId() throws ValidationException, NumberFormatException {
        System.out.print("Enter the workout ID: ");
        int id = Integer.parseInt(scanner.nextLine());
        if (!workoutDao.existsById(id)) {
            throw new ValidationException("Workout not found.");
        }
        return id;
    }

    private int getValidExerciseId() throws NumberFormatException, ValidationException {
        System.out.print("Enter the Exercise ID: ");
        int id = Integer.parseInt(scanner.nextLine());
        if (!exerciseDao.existsById(id)) {
            throw new ValidationException("Exercise not found.");
        }
        return id;
    }

    private LocalDate parseDate(String prompt) throws ValidationException {
        System.out.print(prompt);
        String dateString = scanner.nextLine();
        try {
            return LocalDate.parse(dateString);
        } catch (DateTimeParseException e) {
            throw new ValidationException("Invalid date format. Please use yyyy-mm-dd.");
        }
    }

    private boolean parseBoolean(String input) throws ValidationException {
        if (input.equalsIgnoreCase("y")) {
            return true;
        } else if (input.equalsIgnoreCase("n")) {
            return false;
        } else {
            throw new ValidationException("Invalid input. Please enter 'y' for yes or 'n' for no.");
        }
    }

    private void displayMenu() {
        System.out.println("\n-- Manage Workouts Records --");
        System.out.println("1 - Create Workout Record");
        System.out.println("2 - Update Workout Record");
        System.out.println("3 - Delete Workout Record");
        System.out.println("4 - List Workouts Record in Workout");
        System.out.println("0 - Back");
        System.out.print("Select an option: ");
    }
}