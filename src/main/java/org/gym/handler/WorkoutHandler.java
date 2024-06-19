package org.gym.handler;

import org.gym.dao.DaoFactory;
import org.gym.dao.MembershipDao;
import org.gym.dao.WorkoutDao;
import org.gym.dao.ExerciseSetDao;
import org.gym.model.Workout;
import org.gym.util.ConsoleUtils;
import org.gym.util.DbException;
import org.gym.util.ValidationException;

import java.io.Console;
import java.sql.Connection;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class WorkoutHandler implements UserActionHandler {
    private final Scanner scanner;
    private final WorkoutDao workoutDao;
    private final ExerciseSetDao exerciseSetDao;
    private final MembershipDao membershipDao;

    public WorkoutHandler(Connection conn, Scanner scanner) {
        this.scanner = scanner;
        this.workoutDao = DaoFactory.createWorkoutDao(conn);
        this.exerciseSetDao = DaoFactory.createExerciseSetDao(conn);
        this.membershipDao = DaoFactory.createMembershipDao(conn);
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
                        addWorkout();
                        break;
                    case "2":
                        updateWorkout();
                        break;
                    case "3":
                        deleteWorkout();
                        break;
                    case "4":
                        listWorkouts();
                        break;
                    case "5":
                        addExerciseSetToWorkout();
                        break;
                    case "6":
                        addFinishDate();
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

    private void addWorkout() {
        try {
            int exerciseSetId = getValidExerciseSetId();
            int membershipId = getValidMembershipId();

            System.out.print("Workout name: ");
            String name = scanner.nextLine();

            LocalDate startDate = parseDate("Start date (yyyy-mm-dd): ");

            Workout workout = new Workout(null, exerciseSetId, membershipId, name, startDate, null);
            workoutDao.addWorkout(workout);
            System.out.println("Workout added successfully!");
        } catch (ValidationException e) {
            System.out.println("Validation error: " + e.getMessage());
        } catch (DbException e) {
            System.out.println("Error adding workout: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a valid number.");
        }
    }

    private void updateWorkout() {
        try {
            int id = getValidWorkoutId();
            Workout existingWorkout = workoutDao.getWorkoutById(id);

            int exerciseSetId = getValidExerciseSetId();
            int membershipId = getValidMembershipId();

            System.out.print("Name (current: " + existingWorkout.getName() + "): ");
            String name = scanner.nextLine();

            LocalDate startDate = parseDate("Start date (yyyy-mm-dd) (current: " + existingWorkout.getStartDate() + "): ");

            Workout updatedWorkout = new Workout(id, exerciseSetId, membershipId, name, startDate, null);
            workoutDao.updateWorkout(updatedWorkout);
            System.out.println("Workout updated successfully!");
        } catch (DbException e) {
            System.out.println("Error updating workout: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Invalid ID format. Please enter a number.");
        } catch (ValidationException e) {
            System.out.println("Validation error: " + e.getMessage());
        }
    }

    private void deleteWorkout() {
        try {
            int id = getValidWorkoutId();
            workoutDao.deleteWorkoutById(id);
            System.out.println("Workout deleted successfully!");
        } catch (DbException e) {
            System.out.println("Error deleting workout: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Invalid ID format. Please enter a number.");
        } catch (ValidationException e) {
            System.out.println("Validation error: " + e.getMessage());
        }
    }

    private void listWorkouts() {
        try {
            List<Workout> workouts = workoutDao.getAllWorkouts();

            if (workouts.isEmpty()) {
                System.out.println("No workouts found.");
                return;
            }

            System.out.println("Listing all workouts:");
            for (Workout workout : workouts) {
                System.out.println(workout);
            }
        } catch (DbException e) {
            System.out.println("Error while listing workouts: " + e.getMessage());
        }
    }

    private void addExerciseSetToWorkout() {
        try {
            int workoutId = getValidWorkoutId();
            int exerciseSetId = getValidExerciseSetId();

            Workout workout = workoutDao.getWorkoutById(workoutId);
            workout.setExerciseSetsId(exerciseSetId);
            workoutDao.updateWorkout(workout);

            System.out.println("Exercise set added to workout successfully!");
        } catch (DbException e) {
            System.out.println("Error adding exercise set to workout: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Invalid ID format. Please enter a number.");
        } catch (ValidationException e) {
            System.out.println("Validation error: " + e.getMessage());
        }
    }

    private void addFinishDate() {
        try {
            int workoutId = getValidWorkoutId();

            LocalDate endDate = parseDate("End date (yyyy-mm-dd): ");

            Workout workout = workoutDao.getWorkoutById(workoutId);
            workout.setEndDate(endDate);
            workoutDao.updateWorkout(workout);

            System.out.println("Finish date added to workout successfully!");
        } catch (DbException e) {
            System.out.println("Error adding finish date to workout: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Invalid ID format. Please enter a number.");
        } catch (ValidationException e) {
            System.out.println("Validation error: " + e.getMessage());
        }
    }

    private int getValidWorkoutId() throws ValidationException, NumberFormatException {
        System.out.print("Enter the workout ID: ");
        int id = Integer.parseInt(scanner.nextLine());
        if (!workoutDao.existsById(id)) {
            throw new ValidationException("Workout not found.");
        }
        return id;
    }

    private int getValidExerciseSetId() throws ValidationException, NumberFormatException {
        System.out.print("Enter the exercise set ID: ");
        int id = Integer.parseInt(scanner.nextLine());
        if (!exerciseSetDao.existsById(id)) {
            throw new ValidationException("Exercise set not found.");
        }
        return id;
    }

    private int getValidMembershipId() throws NumberFormatException, ValidationException {
        System.out.print("Enter the membership ID: ");
        int id = Integer.parseInt(scanner.nextLine());
        if (!membershipDao.existsById(id)) {
            throw new ValidationException("Membership not found.");
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

    private void displayMenu() {
        System.out.println("\n-- Manage Workouts --");
        System.out.println("1 - Create Workout");
        System.out.println("2 - Update Workout");
        System.out.println("3 - Delete Workout");
        System.out.println("4 - List Workouts");
        System.out.println("5 - Add/Update Exercise Set to Workout");
        System.out.println("6 - Finish Workout");
        System.out.println("0 - Back");
        System.out.print("Select an option: ");
    }
}
