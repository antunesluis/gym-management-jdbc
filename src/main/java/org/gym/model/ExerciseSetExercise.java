package org.gym.model;

public class ExerciseSetExercise {
    private int exerciseSetId;
    private int exerciseId;

    public ExerciseSetExercise() {
    }

    public ExerciseSetExercise(int exerciseSetId, int exerciseId) {
        this.exerciseSetId = exerciseSetId;
        this.exerciseId = exerciseId;
    }

    public int getExerciseSetId() {
        return exerciseSetId;
    }

    public void setExerciseSetId(int exerciseSetId) {
        this.exerciseSetId = exerciseSetId;
    }

    public int getExerciseId() {
        return exerciseId;
    }

    public void setExerciseId(int exerciseId) {
        this.exerciseId = exerciseId;
    }

    @Override
    public String toString() {
        return "ExerciseSet{" +
                "exerciseSetId=" + exerciseSetId +
                ", exerciseId=" + exerciseId +
                '}';
    }
}