package org.gym.handler;

import org.gym.dao.DaoFactory;
import org.gym.dao.PlanDao;
import org.gym.model.Membership;
import org.gym.model.Plan;
import org.gym.model.Student;
import org.gym.util.DbException;
import org.gym.util.ValidationException;

import java.sql.Connection;
import java.util.List;
import java.util.Scanner;

public class PlanHandler implements UserActionHandler {
    private final Scanner scanner;
    private final PlanDao planDao;

    public PlanHandler(Connection conn, Scanner scanner) {
        this.scanner = scanner;
        this.planDao = DaoFactory.createPlanDao(conn);
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
                        addPlan();
                        break;
                    case "2":
                        updatePlan();
                        break;
                    case "3":
                        deletePlan();
                        break;
                    case "4":
                        listPlans();
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

    private void addPlan() {
        try {
            System.out.print("Name: ");
            String name = scanner.nextLine();

            System.out.print("Monthly fee: ");
            Double monthlyFee = Double.parseDouble(scanner.nextLine());

            Plan plan = new Plan(null, name, monthlyFee);
            planDao.addPlan(plan);
            System.out.println("Plan added successfully!");
        } catch (DbException e ) {
            System.out.println("Error adding plan: " + e.getMessage());
        }
    }

    private void updatePlan() {
        try {
            System.out.print("Enter the ID of the plan to update: ");
            int id = Integer.parseInt(scanner.nextLine());
            if (!planDao.existsById(id)) {
                throw new ValidationException("Exercise not found.");
            }

            Plan existingPlan = planDao.getPlanById(id);
            System.out.print("Name (current: " + existingPlan.getName() + "): ");
            String name = scanner.nextLine();

            System.out.print("Monthly fee (current: " + existingPlan.getMonthlyFee() + "): ");
            Double monthlyFee = Double.parseDouble(scanner.nextLine());

            Plan plan = new Plan(id, name, monthlyFee);
            planDao.updatePlan(plan);
            System.out.println("Plan updated successfully!");
        } catch (DbException e ) {
            System.out.println("Error updating Plan: " + e.getMessage());
        } catch (NumberFormatException e ) {
            System.out.println("Invalid data input. Please enter a number");
        }
    }

    private void deletePlan() {
        try {
            System.out.print("Enter the ID of the plan to delete: ");
            int id = Integer.parseInt(scanner.nextLine());

            planDao.deletePlanById(id);
            System.out.println("Plan deleted successfully!");
        } catch (DbException e ) {
            System.out.println("Error deleting plan: " + e.getMessage());
        } catch (NumberFormatException e ) {
            System.out.println("Invalid ID format. Please enter a number");
        }
    }

    private void listPlans() {
        try {
            List<Plan> plans = planDao.getAllPlans();

            if (plans.isEmpty()) {
                System.out.println("No plans found.");
                return;
            }

            System.out.println("Listing all plans:");
            for (Plan plan : plans) {
                System.out.println(plan);
            }
        } catch (DbException e) {
            System.out.println("Error while listing memberships: " + e.getMessage());
        }
    }

    private void displayMenu() {
        System.out.println("\n-- Manage Plans --");
        System.out.println("1 - Add Plan");
        System.out.println("2 - Update Plan");
        System.out.println("3 - Delete Plan");
        System.out.println("4 - List P");
        System.out.println("0 - Back");
        System.out.print("Select an option: ");
    }
}
