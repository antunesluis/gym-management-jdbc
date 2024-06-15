package org.gym.handler;

import org.gym.dao.DaoFactory;
import org.gym.dao.WorkoutDao;

import java.sql.Connection;
import java.util.Scanner;

public class WorkoutHandler implements UserActionHandler {
    private final Scanner scanner;
    private final WorkoutDao workoutDao;

    public WorkoutHandler(Connection conn, Scanner scanner) {
        this.scanner = scanner;
        this.workoutDao = DaoFactory.createWorkoutDao(conn);
    }

    @Override
    public void execute() {
    }
}
