package org.gym.handler;

import org.gym.dao.DaoFactory;
import org.gym.dao.WorkoutRecordDao;

import java.sql.Connection;
import java.util.Scanner;

public class WorkoutRecordHandler implements UserActionHandler {
    private final Scanner scanner;
    private final WorkoutRecordDao workoutRecordDao;

    public WorkoutRecordHandler(Connection conn, Scanner scanner) {
        this.scanner = scanner;
        this.workoutRecordDao = DaoFactory.createWorkoutRecordDao(conn);
    }

    @Override
    public void execute() {
    }
}
