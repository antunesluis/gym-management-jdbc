package org.gym.util;

import org.gym.handler.*;

import java.sql.Connection;
import java.util.Scanner;

public class GymManagementConsole {
    private final Scanner scanner;
    private final StudentHandler studentHandler;
    private final PlanHandler planHandler;
    private final ExerciseHandler exerciseHandler;
    private final MembershipHandler membershipHandler;
    private final WorkoutHandler workoutHandler;
    private final WorkoutRecordHandler workoutRecordHandler;
    private final ExerciseSetHandler exerciseSetHandler;

    public GymManagementConsole(Connection conn) {
        this.scanner = new Scanner(System.in);
        this.studentHandler = new StudentHandler(conn, scanner);
        this.exerciseHandler = new ExerciseHandler(conn, scanner);
        this.planHandler = new PlanHandler(conn, scanner);
        this.membershipHandler = new MembershipHandler(conn, scanner);
        this.workoutHandler = new WorkoutHandler(conn, scanner);
        this.workoutRecordHandler = new WorkoutRecordHandler(conn, scanner);
        this.exerciseSetHandler = new ExerciseSetHandler(conn, scanner);
    }

    public void start() {
        String option;
        do {
            ConsoleUtils.clearScreen();
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
                        exerciseHandler.execute();
                        break;
                    case "4":
                        membershipHandler.execute();
                        break;
                    case "5":
                        exerciseSetHandler.execute();
                        break;
                    case "6":
                        workoutHandler.execute();
                        break;
                    case "7":
                        workoutRecordHandler.execute();
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
                ConsoleUtils.waitForEnter();
            }
        } while (!option.equals("0"));
        scanner.close();
    }

    private void displayMenu() {
        System.out.println("\nSelect a module to manage:");
        System.out.println("1 - Manage Students");
        System.out.println("2 - Manage Plans");
        System.out.println("3 - Manage Exercises");
        System.out.println("4 - Manage Memberships");
        System.out.println("5 - Manage Exercise Sets");
        System.out.println("6 - Manage Workouts");
        System.out.println("7 - Manage Workout Records");
        System.out.println("0 - Exit");
        System.out.print("Option: ");
    }
}