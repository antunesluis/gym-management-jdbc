package org.gym.dao;

import org.gym.model.ExerciseSet;
import org.gym.util.DB;
import org.gym.util.DbException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ExerciseSetDaoJDBC implements ExerciseSetDao {
    private Connection conn;

    public ExerciseSetDaoJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void addExerciseSet(ExerciseSet exerciseSet) {
        String sql = "INSERT INTO exercise_sets (series_count, min_reps, max_reps, rest_time) VALUES (?, ?, ?, ?)";

        try (PreparedStatement st = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            st.setInt(1, exerciseSet.getSeriesCount());
            st.setInt(2, exerciseSet.getMinReps());
            st.setInt(3, exerciseSet.getMaxReps());
            st.setInt(4, exerciseSet.getRestTime());

            int rowsAffected = st.executeUpdate();

            if (rowsAffected <= 0) {
                throw new DbException("Unexpected error! No rows affected.");
            }

            try (ResultSet rs = st.getGeneratedKeys()) {
                if (rs.next()) {
                    int id = rs.getInt(1);
                    exerciseSet.setId(id);
                }
            }
        } catch (SQLException e) {
            throw new DbException("Error while adding exercise set: " + e.getMessage());
        }
    }

    @Override
    public void updateExerciseSet(ExerciseSet exerciseSet) {
        String sql = "UPDATE exercise_sets SET series_count = ?, min_reps = ?, max_reps = ?, rest_time = ? WHERE id = ?";

        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setInt(1, exerciseSet.getSeriesCount());
            st.setInt(2, exerciseSet.getMinReps());
            st.setInt(3, exerciseSet.getMaxReps());
            st.setInt(4, exerciseSet.getRestTime());
            st.setInt(5, exerciseSet.getId());

            int rowsAffected = st.executeUpdate();

            if (rowsAffected <= 0) {
                throw new DbException("Unexpected error! No rows affected.");
            }
        } catch (SQLException e) {
            throw new DbException("Error while updating exercise set: " + e.getMessage());
        }
    }

    @Override
    public void deleteExerciseSetById(int id) {
        String sql = "DELETE FROM exercise_sets WHERE id = ?";
        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setInt(1, id);

            int rowsAffected = st.executeUpdate();

            if (rowsAffected <= 0) {
                throw new DbException("Unexpected error! No rows affected.");
            }
        } catch (SQLException e) {
            throw new DbException("Error while deleting exercise set: " + e.getMessage());
        }
    }

    @Override
    public ExerciseSet getExerciseSetById(int id) {
        String sql = "SELECT id, series_count, min_reps, max_reps, rest_time FROM exercise_sets WHERE id = ?";

        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setInt(1, id);

            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    return instantiateExerciseSet(rs);
                }
                return null;
            }
        } catch (SQLException e) {
            throw new DbException("Error while getting exercise set by id: " + e.getMessage());
        }
    }

    @Override
    public List<ExerciseSet> getAllExercisesSets() {
        String sql = "SELECT id, series_count, min_reps, max_reps, rest_time FROM exercise_sets";

        try (PreparedStatement st = conn.prepareStatement(sql);
             ResultSet rs = st.executeQuery()) {

            List<ExerciseSet> exerciseSets = new ArrayList<>();

            while (rs.next()) {
                exerciseSets.add(instantiateExerciseSet(rs));
            }

            return exerciseSets;
        } catch (SQLException e) {
            throw new DbException("Error while getting all exercise sets: " + e.getMessage());
        }
    }

    @Override
    public boolean existsById(int id) {
        String sql = "SELECT 1 FROM exercise_sets WHERE id = ?";

        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setInt(1, id);
            try (ResultSet rs = st.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            throw new DbException("Error checking exercise set ID: " + e.getMessage());
        }
    }

    private ExerciseSet instantiateExerciseSet(ResultSet rs) throws SQLException {
        ExerciseSet exerciseSet = new ExerciseSet();
        exerciseSet.setId(rs.getInt("id"));
        exerciseSet.setSeriesCount(rs.getInt("series_count"));
        exerciseSet.setMinReps(rs.getInt("min_reps"));
        exerciseSet.setMaxReps(rs.getInt("max_reps"));
        exerciseSet.setRestTime(rs.getInt("rest_time"));
        return exerciseSet;
    }
}