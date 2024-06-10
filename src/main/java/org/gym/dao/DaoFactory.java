package org.gym.dao;

public class DaoFactory {

    public static StudentDao createStudentDao() {
        return new StudentDaoJDBC();
    }

    public static ExerciseDao createExerciseDao() {
        return new ExerciseDaoJDBC();
    }

    public static PlanDao createPlanDao() {
        return new PlanDaoJDBC();
    }
}
