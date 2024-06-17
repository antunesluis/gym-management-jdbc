package org.gym.handler;

import org.gym.dao.DaoFactory;
import org.gym.dao.ExerciseSetExerciseDao;

import java.sql.Connection;
import java.util.Scanner;

public class ExerciseSetExerciseHandler implements UserActionHandler{
    private final Scanner scanner;
    private final ExerciseSetExerciseDao exerciseSetExerciseDao;

    public ExerciseSetExerciseHandler(Connection conn, Scanner scanner) {
        this.scanner = scanner;
        this.exerciseSetExerciseDao = DaoFactory.createExerciseSetExerciseDao(conn);
    }

    @Override
    public void execute() {
    }
}
