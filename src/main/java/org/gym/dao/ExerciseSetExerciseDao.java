package org.gym.dao;

import org.gym.model.ExerciseSetExercise;

import java.util.List;

public interface ExerciseSetExerciseDao {
    void insert(ExerciseSetExercise exerciseSetExercise);
    void deleteByExerciseSetIdAndExerciseId(int exerciseSetId, int exerciseId);
    List<ExerciseSetExercise> findByExerciseSetId(int exerciseSetId);
}