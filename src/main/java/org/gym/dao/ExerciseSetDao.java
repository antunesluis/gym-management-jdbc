package org.gym.dao;

import org.gym.model.Exercise;
import org.gym.model.ExerciseSet;

import java.util.List;

public interface ExerciseSetDao {
    void addExerciseSet(ExerciseSet exerciseSet);
    void updateExerciseSet(ExerciseSet exerciseSet);
    void deleteExerciseSetById(int id);
    ExerciseSet getExerciseSetById(int id);
    List<ExerciseSet> getAllExercisesSets();
}

