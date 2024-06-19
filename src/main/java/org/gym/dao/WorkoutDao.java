package org.gym.dao;

import org.gym.model.Membership;
import org.gym.model.Workout;

import java.util.List;

public interface WorkoutDao {
    void addWorkout(Workout workout);
    void updateWorkout(Workout workout);
    void deleteWorkoutById(int id);
    Workout getWorkoutById(int id);
    List<Workout> getAllWorkouts();
    boolean existsById(int id);
}
