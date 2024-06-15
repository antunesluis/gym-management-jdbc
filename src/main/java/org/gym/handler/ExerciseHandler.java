package org.gym.handler;

import org.gym.dao.DaoFactory;
import org.gym.dao.ExerciseDao;

import java.sql.Connection;
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
    }
}
