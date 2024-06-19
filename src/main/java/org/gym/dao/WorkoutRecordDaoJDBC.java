package org.gym.dao;

import org.gym.model.WorkoutRecord;
import org.gym.util.DbException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class WorkoutRecordDaoJDBC implements WorkoutRecordDao {
    private Connection conn;

    public WorkoutRecordDaoJDBC(Connection conn) {
        this.conn = conn;
    }

    public void addWorkoutRecord(WorkoutRecord workoutRecord) {
        String sql = "INSERT INTO workout_records (workout_id, exercise_id, exercise_completed, load, completion_date) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement st = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            st.setInt(1, workoutRecord.getWorkoutId());
            st.setInt(2, workoutRecord.getExerciseId());
            st.setBoolean(3, workoutRecord.getExerciseCompleted());
            st.setInt(4, workoutRecord.getLoad());
            st.setDate(5, Date.valueOf(workoutRecord.getCompletionDate()));

            int rowsAffected = st.executeUpdate();

            if (rowsAffected <= 0) {
                throw new DbException("Unexpected error! No rows affected.");
            }

            try (ResultSet rs = st.getGeneratedKeys()) {
                if (rs.next()) {
                    int id = rs.getInt(1);
                    workoutRecord.setId(id);
                }
            }
        } catch (SQLException e) {
            throw new DbException("Error while adding workout record: " + e.getMessage());
        }
    }

    @Override
    public void updateWorkoutRecord(WorkoutRecord workoutRecord) {
        String sql = "UPDATE workout_records SET workout_id = ?, exercise_id = ?, exercise_completed = ?, load = ?, completion_date = ? WHERE id = ?";

        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setInt(1, workoutRecord.getWorkoutId());
            st.setInt(2, workoutRecord.getExerciseId());
            st.setBoolean(3, workoutRecord.getExerciseCompleted());
            st.setInt(4, workoutRecord.getLoad());
            st.setDate(5, Date.valueOf(workoutRecord.getCompletionDate()));
            st.setInt(6, workoutRecord.getId());

            int rowsAffected = st.executeUpdate();

            if (rowsAffected <= 0) {
                throw new DbException("Unexpected error! No rows affected.");
            }
        } catch (SQLException e) {
            throw new DbException("Error while updating workout record: " + e.getMessage());
        }
    }

    @Override
    public void deleteWorkoutRecordById(int id) {
        String sql = "DELETE FROM workout_records WHERE id = ?";

        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setInt(1, id);
            st.executeUpdate();
        }
        catch (SQLException e) {
            throw new DbException("Error while deleting workout record: " + e.getMessage());
        }
    }

    @Override
    public WorkoutRecord getWorkoutRecordById(int id) {
        String sql = "SELECT id, workout_id, exercise_id, exercise_completed, load, completion_date FROM workout_records WHERE id = ?";

        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setInt(1, id);
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    return instantiateWorkoutRecord(rs);
                }
                return null;
            }
        } catch (SQLException e) {
            throw new DbException("Error while fetching workout record by ID: " + e.getMessage());
        }
    }

    @Override
    public List<WorkoutRecord> getAllWorkoutRecord() {
        String sql = "SELECT id, workout_id, exercise_id, exercise_completed, load, completion_date FROM workout_records";

        try (PreparedStatement st = conn.prepareStatement(sql);
             ResultSet rs = st.executeQuery()) {
            List<WorkoutRecord> workoutRecords = new ArrayList<>();

            while (rs.next()) {
                WorkoutRecord workoutRecord = instantiateWorkoutRecord(rs);
                workoutRecords.add(workoutRecord);
            }

            return workoutRecords;
        } catch (SQLException e) {
            throw new DbException("Error while fetching all workout records: " + e.getMessage());
        }
    }

    @Override
    public boolean existsById(int id) {
        String sql = "SELECT 1 FROM workout_records WHERE id = ?";

        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setInt(1, id);
            try (ResultSet rs = st.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            throw new DbException("Error checking workout record ID: " + e.getMessage());
        }
    }

    @Override
    public List<WorkoutRecord> getWorkoutRecordsByWorkoutId(int workoutId) {
        String sql = "SELECT id, workout_id, exercise_id, exercise_completed, load, completion_date FROM workout_records WHERE workout_id = ?";

        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setInt(1, workoutId);
            try (ResultSet rs = st.executeQuery()) {
                List<WorkoutRecord> workoutRecords = new ArrayList<>();
                while (rs.next()) {
                    WorkoutRecord workoutRecord = instantiateWorkoutRecord(rs);
                    workoutRecords.add(workoutRecord);
                }
                return workoutRecords;
            }
        } catch (SQLException e) {
            throw new DbException("Error while fetching workout records by workout ID: " + e.getMessage());
        }
    }

    private WorkoutRecord instantiateWorkoutRecord(ResultSet rs) throws SQLException {
        WorkoutRecord workoutRecord = new WorkoutRecord();
        workoutRecord.setId(rs.getInt("id"));
        workoutRecord.setWorkoutId(rs.getInt("workout_id"));
        workoutRecord.setExerciseId(rs.getInt("exercise_id"));
        workoutRecord.setExerciseCompleted(rs.getBoolean("exercise_completed"));
        workoutRecord.setLoad(rs.getInt("load"));
        workoutRecord.setCompletionDate(rs.getDate("completion_date").toLocalDate());
        return workoutRecord;
    }
}