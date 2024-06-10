package org.gym.dao;

import org.gym.util.DB;

public class DaoFactory {

    public static StudentDao createStudentDao() {
        return new StudentDaoJDBC(DB.getConnection());
    }

    public static ExerciseDao createExerciseDao() {
        return new ExerciseDaoJDBC();
    }

    public static PlanDao createPlanDao() {
        return new PlanDaoJDBC();
    }
}
