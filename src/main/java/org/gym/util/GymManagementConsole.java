package org.gym.util;

import org.gym.handler.ExerciseHandler;
import org.gym.handler.StudentHandler;
import org.gym.handler.PlanHandler;
import org.gym.handler.WorkoutHandler;

import java.sql.Connection;
import java.util.Scanner;

public class GymManagementConsole {
    private final Scanner scanner;
    private final StudentHandler studentHandler;
    private final PlanHandler planHandler;
    private final WorkoutHandler workoutHandler;
    private final ExerciseHandler exerciseHandler;

    public GymManagementConsole(Connection conn) {
        this.scanner = new Scanner(System.in);
        this.studentHandler = new StudentHandler(conn, scanner);
        this.planHandler = new PlanHandler(conn, scanner);
        this.workoutHandler = new WorkoutHandler(conn, scanner);
        this.exerciseHandler = new ExerciseHandler(conn, scanner);
    }

    public void start() {
        String option;
        do {
            displayMenu();
            option = scanner.nextLine();
            try {
                switch (option) {
                    case "1":
                        studentHandler.execute();
                        break;
                    case "2":
                        planHandler.execute();
                        break;
                    case "3":
                        workoutHandler.execute();
                        break;
                    case "4":
                        exerciseHandler.execute();
                        break;
                    case "0":
                        System.out.println("Exiting the system...");
                        break;
                    default:
                        System.out.println("Invalid option. Please try again.");
                        break;
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        } while (!option.equals("0"));
        scanner.close();
    }

    private void displayMenu() {
        System.out.println("\nSelect a module to manage:");
        System.out.println("1 - Manage Students");
        System.out.println("2 - Manage Plans");
        System.out.println("3 - Manage Workouts");
        System.out.println("4 - Manage Exercises");
        System.out.println("0 - Exit");
        System.out.print("Option: ");
    }
}