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

    public static MembershipDao createMembershipDao() {
        return new MembershipDaoJDBC(DB.getConnection());
    }

    public static ExerciseSetDao createExerciseSetDao() {
        return new ExerciseSetDaoJDBC(DB.getConnection());
    }

    public static WorkoutDao createWorkoutDao() {
        return new WorkoutDaoJDBC(DB.getConnection());
    }

    public static WorkoutRecordDao createWorkoutRecordDao() {
        return new WorkoutRecordDaoJDBC(DB.getConnection());
    }
}
