package org.gym.dao;

import org.gym.model.Student;
import org.gym.model.Workout;
import org.gym.util.DB;
import org.gym.util.DbException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class WorkoutDaoJDBC implements WorkoutDao {
    private Connection conn;

    public WorkoutDaoJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void addWorkout(Workout workout) {
        String sql = "INSERT INTO workouts (exercise_sets_id, membership_id, name, start_date, end_date) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement st = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            st.setInt(1, workout.getExerciseSetsId());
            st.setInt(2, workout.getMembershipId());
            st.setString(3, workout.getName());
            st.setDate(4, Date.valueOf(workout.getStartDate()));
            if (workout.getEndDate() != null) {
                st.setDate(5, Date.valueOf(workout.getEndDate()));
            } else {
                st.setNull(5, Types.DATE);
            }

            int rowsAffected = st.executeUpdate();

            if (rowsAffected <= 0) {
                throw new DbException("Unexpected error! No rows affected.");
            }

            try (ResultSet rs = st.getGeneratedKeys()) {
                if (rs.next()) {
                    int id = rs.getInt(1);
                    workout.setId(id);
                }
            }
        } catch (SQLException e) {
            throw new DbException("Error while adding workout: " + e.getMessage());
        }
    }

    @Override
    public void updateWorkout(Workout workout) {
        String sql = "UPDATE workouts SET exercise_sets_id = ?, membership_id = ?, name = ?, start_date = ?, end_date = ? WHERE id = ?";

        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setInt(1, workout.getExerciseSetsId());
            st.setInt(2, workout.getMembershipId());
            st.setString(3, workout.getName());
            st.setDate(4, Date.valueOf(workout.getStartDate()));
            if (workout.getEndDate() != null) {
                st.setDate(5, Date.valueOf(workout.getEndDate()));
            } else {
                st.setNull(5, Types.DATE);
            }
            st.setInt(6, workout.getId());

            int rowsAffected = st.executeUpdate();

            if (rowsAffected <= 0) {
                throw new DbException("Unexpected error! No rows affected.");
            }
        } catch (SQLException e) {
            throw new DbException("Error while updating workout: " + e.getMessage());
        }
    }

    @Override
    public void deleteWorkoutById(int id) {
        String sql = "DELETE FROM workouts WHERE id = ?";

        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setInt(1, id);
            st.executeUpdate();
        } catch (SQLException e) {
            throw new DbException("Error while deleting workout: " + e.getMessage());
        }
    }

    @Override
    public Workout getWorkoutById(int id) {
        String sql = "SELECT id, exercise_sets_id, membership_id, name, start_date, end_date FROM workouts WHERE id = ?";

        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setInt(1, id);

            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    return instantiateWorkout(rs);
                }
                return null;
            }
        } catch (SQLException e) {
            throw new DbException("Error while getting workout by id: " + e.getMessage());
        }
    }

    @Override
    public List<Workout> getAllWorkouts() {
        String sql = "SELECT id, exercise_sets_id, membership_id, name, start_date, end_date FROM workouts";

        try (PreparedStatement st = conn.prepareStatement(sql);
             ResultSet rs = st.executeQuery()) {

            List<Workout> workouts = new ArrayList<>();

            while (rs.next()) {
                workouts.add(instantiateWorkout(rs));
            }

            return workouts;
        } catch (SQLException e) {
            throw new DbException("Error while getting all workouts: " + e.getMessage());
        }
    }

    private Workout instantiateWorkout(ResultSet rs) throws SQLException {
        Workout workout = new Workout();
        workout.setId(rs.getInt("id"));
        workout.setExerciseSetsId(rs.getInt("exercise_sets_id"));
        workout.setMembershipId(rs.getInt("membership_id"));
        workout.setName(rs.getString("name"));
        workout.setStartDate(rs.getDate("start_date").toLocalDate());
        Date endDate = rs.getDate("end_date");
        if (endDate != null) {
            workout.setEndDate(endDate.toLocalDate());
        }
        return workout;
    }
}
