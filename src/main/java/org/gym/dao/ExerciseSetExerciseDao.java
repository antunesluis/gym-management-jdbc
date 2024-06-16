package org.gym.dao;

import org.gym.model.ExerciseSetExercise;

import java.util.List;

public interface ExerciseSetExerciseDao {
    void insert(ExerciseSetExercise exerciseSetExercise);
    void deleteByExerciseSetId(int exerciseSetId);
    List<ExerciseSetExercise> findByExerciseSetId(int exerciseSetId);
}