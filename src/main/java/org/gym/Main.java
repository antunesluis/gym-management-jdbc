package org.gym;

import org.gym.dao.DaoFactory;
import org.gym.dao.ExerciseDao;
import org.gym.dao.PlanDao;
import org.gym.dao.StudentDao;
import org.gym.util.DB;

import java.sql.Connection;

public class Main {
    public static void main(String[] args) {
        Connection conn = DB.getConnection();

        StudentDao student = DaoFactory.createStudentDao();
        ExerciseDao exercise = DaoFactory.createExerciseDao();
        PlanDao plan = DaoFactory.createPlanDao();

        DB.closeConnection();
    }
}