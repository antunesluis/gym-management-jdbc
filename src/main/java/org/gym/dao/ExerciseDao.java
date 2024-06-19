package org.gym.dao;

import org.gym.model.Exercise;

import java.util.List;

public interface ExerciseDao {
    boolean existsById(int id);
    void addExercise(Exercise exercise);
    void updateExercise(Exercise exercise);
    void deleteExerciseById(int id);
    Exercise getExerciseById(int id);
    List<Exercise> getAllExercises();
}
