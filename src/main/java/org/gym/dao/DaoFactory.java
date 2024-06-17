package org.gym.dao;

import org.gym.util.DB;

import java.sql.Connection;

public class DaoFactory {
    public static StudentDao createStudentDao(Connection conn) {
        return new StudentDaoJDBC(conn);
    }

    public static ExerciseDao createExerciseDao(Connection conn) {
        return new ExerciseDaoJDBC(conn);
    }

    public static PlanDao createPlanDao(Connection conn) {
        return new PlanDaoJDBC(conn);
    }

    public static MembershipDao createMembershipDao(Connection conn) {
        return new MembershipDaoJDBC(conn);
    }

    public static ExerciseSetDao createExerciseSetDao(Connection conn) {
        return new ExerciseSetDaoJDBC(conn);
    }

    public static WorkoutDao createWorkoutDao(Connection conn) {
        return new WorkoutDaoJDBC(conn);
    }

    public static WorkoutRecordDao createWorkoutRecordDao(Connection conn) {
        return new WorkoutRecordDaoJDBC(conn);
    }

    public static ExerciseSetExerciseDao createExerciseSetExerciseDao(Connection conn) {
        return new ExerciseSetExerciseDaoJDBC(conn);
    }
}
