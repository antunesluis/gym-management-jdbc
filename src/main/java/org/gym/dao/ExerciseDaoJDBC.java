package org.gym.dao;

import org.gym.model.Exercise;
import org.gym.util.DbException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ExerciseDaoJDBC implements ExerciseDao {
    private Connection conn;

    public ExerciseDaoJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void addExercise(Exercise exercise) {
        String sql = "INSERT INTO exercises (name, muscles_activated) VALUES (?, ?)";

        try (PreparedStatement st = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            st.setString(1, exercise.getName());
            st.setString(2, exercise.getMusclesActivates());

            int rowsAffected = st.executeUpdate();

            if (rowsAffected <= 0) {
                throw new DbException("Unexpected error! No rows affected.");
            }

            try (ResultSet rs = st.getGeneratedKeys()) {
                if (rs.next()) {
                    int id = rs.getInt(1);
                    exercise.setId(id);
                }
            }
        } catch (SQLException e) {
            throw new DbException("Error while adding exercise: " + e.getMessage());
        }
    }

    @Override
    public void updateExercise(Exercise exercise) {
        String sql = "UPDATE exercises SET name = ?, muscles_activated = ? WHERE id = ?";

        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setString(1, exercise.getName());
            st.setString(2, exercise.getMusclesActivates());
            st.setInt(3, exercise.getId());

            int rowsAffected = st.executeUpdate();

            if (rowsAffected <= 0) {
                throw new DbException("Unexpected error! No rows affected.");
            }
        } catch (SQLException e) {
            throw new DbException("Error while updating exercise: " + e.getMessage());
        }
    }

    @Override
    public void deleteExerciseById(int id) {
        String sql = "DELETE FROM exercises WHERE id = ?";

        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setInt(1, id);

            int rowsAffected = st.executeUpdate();

            if (rowsAffected <= 0) {
                throw new DbException("Unexpected error! No rows affected.");
            }
        } catch (SQLException e) {
            throw new DbException("Error while deleting exercise: " + e.getMessage());
        }
    }

    @Override
    public Exercise getExerciseById(int id) {
        String sql = "SELECT id, name, muscles_activated FROM exercises WHERE id = ?";

        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setInt(1, id);

            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    return instantiateExercise(rs);
                }
                return null;
            }
        } catch (SQLException e) {
            throw new DbException("Error while getting exercise by id: " + e.getMessage());
        }
    }

    @Override
    public List<Exercise> getAllExercises() {
        String sql = "SELECT id, name, muscles_activated FROM exercises";

        try (PreparedStatement st = conn.prepareStatement(sql);
             ResultSet rs = st.executeQuery()) {

            List<Exercise> exercises = new ArrayList<>();

            while (rs.next()) {
                exercises.add(instantiateExercise(rs));
            }

            return exercises;
        } catch (SQLException e) {
            throw new DbException("Error while getting all exercises: " + e.getMessage());
        }
    }

    private Exercise instantiateExercise(ResultSet rs) throws SQLException {
        Exercise exercise = new Exercise();
        exercise.setId(rs.getInt("id"));
        exercise.setName(rs.getString("name"));
        exercise.setMusclesActivates(rs.getString("muscles_activated"));
        return exercise;
    }
}