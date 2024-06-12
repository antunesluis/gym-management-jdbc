package org.gym.model;

import java.time.LocalDate;

public class WorkoutRecord {
    private Integer id;
    private Integer workoutId;
    private Integer exerciseId;

    private Boolean exerciseCompleted;
    private Integer load;
    private LocalDate completionDate;

    public WorkoutRecord() {}

    public WorkoutRecord(Integer id, Integer workoutId, Integer exerciseId, Boolean exerciseCompleted, Integer load, LocalDate completionDate) {
        this.id = id;
        this.workoutId = workoutId;
        this.exerciseId = exerciseId;
        this.exerciseCompleted = exerciseCompleted;
        this.load = load;
        this.completionDate = completionDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getWorkoutId() {
        return workoutId;
    }

    public void setWorkoutId(Integer workoutId) {
        this.workoutId = workoutId;
    }

    public Integer getExerciseId() {
        return exerciseId;
    }

    public void setExerciseId(Integer exerciseId) {
        this.exerciseId = exerciseId;
    }

    public Boolean getExerciseCompleted() {
        return exerciseCompleted;
    }

    public void setExerciseCompleted(Boolean exerciseCompleted) {
        this.exerciseCompleted = exerciseCompleted;
    }

    public Integer getLoad() {
        return load;
    }

    public void setLoad(Integer load) {
        this.load = load;
    }

    public LocalDate getCompletionDate() {
        return completionDate;
    }

    public void setCompletionDate(LocalDate completionDate) {
        this.completionDate = completionDate;
    }

    @Override
    public String toString() {
        return "WorkoutRecord{" +
                "id=" + id +
                ", workoutId=" + workoutId +
                ", exerciseId=" + exerciseId +
                ", exerciseCompleted=" + exerciseCompleted +
                ", load=" + load +
                ", completionDate=" + completionDate +
                '}';
    }
}
