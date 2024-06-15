package org.gym.handler;

import org.gym.dao.DaoFactory;
import org.gym.dao.ExerciseSetDao;

import java.sql.Connection;
import java.util.Scanner;

public class ExerciseSetHandler implements UserActionHandler {
    private final Scanner scanner;
    private final ExerciseSetDao exerciseSetDao;

    public ExerciseSetHandler(Connection conn, Scanner scanner) {
        this.scanner = scanner;
        this.exerciseSetDao = DaoFactory.createExerciseSetDao(conn);
    }

    @Override
    public void execute() {
    }
}
