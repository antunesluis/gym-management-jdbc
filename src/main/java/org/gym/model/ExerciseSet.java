package org.gym.model;

public class ExerciseSet {
    private Integer id;
    private Integer exerciseId;

    private Integer seriesCount;
    private Integer minReps;
    private Integer maxReps;
    private Integer rest_time;

    public ExerciseSet() {}

    public ExerciseSet(Integer id, Integer exerciseId, Integer seriesCount, Integer minReps, Integer maxReps, Integer rest_time) {
        this.id = id;
        this.exerciseId = exerciseId;
        this.seriesCount = seriesCount;
        this.minReps = minReps;
        this.maxReps = maxReps;
        this.rest_time = rest_time;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getExerciseId() {
        return exerciseId;
    }

    public void setExerciseId(Integer exerciseId) {
        this.exerciseId = exerciseId;
    }

    public Integer getSeriesCount() {
        return seriesCount;
    }

    public void setSeriesCount(Integer seriesCount) {
        this.seriesCount = seriesCount;
    }

    public Integer getMinReps() {
        return minReps;
    }

    public void setMinReps(Integer minReps) {
        this.minReps = minReps;
    }

    public Integer getMaxReps() {
        return maxReps;
    }

    public void setMaxReps(Integer maxReps) {
        this.maxReps = maxReps;
    }

    public Integer getRest_time() {
        return rest_time;
    }

    public void setRest_time(Integer rest_time) {
        this.rest_time = rest_time;
    }

    @Override
    public String toString() {
        return "ExerciseSet{" +
                "id=" + id +
                ", exerciseId=" + exerciseId +
                ", seriesCount=" + seriesCount +
                ", minReps=" + minReps +
                ", maxReps=" + maxReps +
                ", rest_time=" + rest_time +
                '}';
    }
}
