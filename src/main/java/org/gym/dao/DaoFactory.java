package org.gym.dao;

import org.gym.util.DB;

public class DaoFactory {

    public static StudentDao createStudentDao() {
        return new StudentDaoJDBC(DB.getConnection());
    }

    public static ExerciseDao createExerciseDao() {
        return new ExerciseDaoJDBC(DB.getConnection());
    }

    public static PlanDao createPlanDao() {
        return new PlanDaoJDBC(DB.getConnection());
    }
}
