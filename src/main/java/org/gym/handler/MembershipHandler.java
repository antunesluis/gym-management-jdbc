package org.gym.handler;

import org.gym.dao.DaoFactory;
import org.gym.dao.MembershipDao;
import org.gym.dao.PlanDao;
import org.gym.dao.StudentDao;
import org.gym.model.Membership;
import org.gym.util.DbException;
import org.gym.util.ValidationException;

import java.sql.Connection;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class MembershipHandler implements UserActionHandler {
    private final Scanner scanner;
    private final MembershipDao membershipDao;
    private final StudentDao studentDao;
    private final PlanDao planDao;

    public MembershipHandler(Connection conn, Scanner scanner) {
        this.scanner = scanner;
        this.membershipDao = DaoFactory.createMembershipDao(conn);
        this.studentDao = DaoFactory.createStudentDao(conn);
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
                        addMembership();
                        break;
                    case "2":
                        updateMembership();
                        break;
                    case "3":
                        deleteMembership();
                        break;
                    case "4":
                        listMemberships();
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
            }
            catch (Exception e) {
                System.out.println("An unexpected error occurred: " + e.getMessage());
            }
        } while (!option.equals("0"));
    }

    private void addMembership() {
        try {
            System.out.print("Student ID: ");
            int studentId = Integer.parseInt(scanner.nextLine());
            if (!studentDao.existsById(studentId)) {
                throw new ValidationException("Student ID not found.");
            }

            System.out.print("Plan ID: ");
            int planId = Integer.parseInt(scanner.nextLine());
            if (!planDao.existsById(planId)) {
                throw new ValidationException("Plan ID not found.");
            }

            System.out.print("Start Date (yyyy-mm-dd): ");
            String startDateString = scanner.nextLine();
            LocalDate startDate;
            try {
                startDate = LocalDate.parse(startDateString);
            } catch (DateTimeParseException e) {
                throw new ValidationException("Invalid date format. Please use yyyy-mm-dd.");
            }

            System.out.print("Card Number: ");
            String cardNumber = scanner.nextLine();
            System.out.print("Card Holder Name: ");
            String cardHolderName = scanner.nextLine();
            System.out.print("Card Expiry Date (MM/YY): ");
            String cardExpiryDate = scanner.nextLine();
            System.out.print("Card CVV: ");
            String cardCvv = scanner.nextLine();

            Membership membership = new Membership(null, studentId, planId, null, startDate, cardNumber, cardHolderName, cardExpiryDate, cardCvv);
            membershipDao.addMembership(membership);
            System.out.println("Membership added successfully!");
        } catch (ValidationException e) {
            System.out.println("Validation error: " + e.getMessage());
        } catch (DbException e) {
            System.out.println("Error adding membership: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a valid number.");
        }
    }

    private void updateMembership() {
        try {
            System.out.print("Enter the ID of the exercise to update: ");
            int id = Integer.parseInt(scanner.nextLine());
            if (!membershipDao.existsById(id)) {
                throw new ValidationException("Workout ID not found.");
            }
            Membership existingMembership = membershipDao.getMembershipById(id);

            System.out.print("Student ID (current: " + existingMembership.getStudentId() + "): ");
            int studentId = Integer.parseInt(scanner.nextLine());
            if (!studentDao.existsById(studentId)) {
                throw new ValidationException("Student ID not found.");
            }

            System.out.print("Plan ID (current: " + existingMembership.getPlanId() + "): ");
            int planId = Integer.parseInt(scanner.nextLine());
            if (!planDao.existsById(planId)) {
                throw new ValidationException("Plan ID not found.");
            }

            System.out.print("Start Date (yyyy-mm-dd) (current: " + existingMembership.getStartDate() + "): ");
            String startDateString = scanner.nextLine();
            LocalDate startDate;
            try {
                startDate = LocalDate.parse(startDateString);
            } catch (DateTimeParseException e) {
                throw new ValidationException("Invalid date format. Please use yyyy-mm-dd.");
            }

            System.out.print("Card Number (current: " + existingMembership.getCardNumber() + "): ");
            String cardNumber = scanner.nextLine();
            System.out.print("Card Holder Name (current: " + existingMembership.getCardHolderName() + "): ");
            String cardHolderName = scanner.nextLine();
            System.out.print("Card Expiry Date (MM/YY) (current: " + existingMembership.getCardExpiryDate() + "): ");
            String cardExpiryDate = scanner.nextLine();
            System.out.print("Card CVV (current: " + existingMembership.getCardCvv() + "): ");
            String cardCvv = scanner.nextLine();

            membershipDao.updateMembership(existingMembership);
            System.out.println("Membership Update successfully!");
        } catch (DbException e) {
            System.out.println("Error updating exercise: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Invalid ID format. Please enter a number.");
        } catch (ValidationException e) {
            System.out.println("Validation error: " + e.getMessage());
        }
    }

    private void deleteMembership() {
        try {
            System.out.print("Enter the ID of the membership to delete: ");
            int id = Integer.parseInt(scanner.nextLine());

            membershipDao.deleteMembershipById(id);
            System.out.println("Plan deleted successfully!");
        } catch (DbException e) {
            System.out.println("Error deleting plan: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Invalid ID format. Please enter a number");
        }
    }

    private void listMemberships() {
        try {
            List<Membership> memberships = membershipDao.getAllMemberships();

            if (memberships.isEmpty()) {
                System.out.println("No memberships found.");
                return;
            }

            System.out.println("Listing all plans:");
            for (Membership membership : memberships) {
                System.out.println(membership);
            }
        } catch (DbException e) {
            System.out.println("Error while listing memberships: " + e.getMessage());
        }
    }

    private void displayMenu() {
        System.out.println("\n-- Manage Memberships --");
        System.out.println("1 - Add Membership");
        System.out.println("2 - Update Membership");
        System.out.println("3 - Delete Membership");
        System.out.println("4 - List Memberships");
        System.out.println("0 - Back");
        System.out.print("Select an option: ");    }
}
