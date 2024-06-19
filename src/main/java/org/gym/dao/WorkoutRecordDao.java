package org.gym.dao;

import org.gym.model.WorkoutRecord;

import java.util.List;

public interface WorkoutRecordDao {
    void addWorkoutRecord(WorkoutRecord workoutRecord);
    void updateWorkoutRecord(WorkoutRecord workoutRecord);
    void deleteWorkoutRecordById(int id);
    WorkoutRecord getWorkoutRecordById(int id);
    List<WorkoutRecord> getAllWorkoutRecord();
    List<WorkoutRecord> getWorkoutRecordsByWorkoutId(int workoutId);
    boolean existsById(int id);
}
