package org.gym.model;

public class ExerciseSet {
    private Integer id;

    private Integer seriesCount;
    private Integer minReps;
    private Integer maxReps;
    private Integer restTime;

    public ExerciseSet() {}

    public ExerciseSet(Integer id, Integer seriesCount, Integer minReps, Integer maxReps, Integer restTime) {
        this.id = id;
        this.seriesCount = seriesCount;
        this.minReps = minReps;
        this.maxReps = maxReps;
        this.restTime = restTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Integer getRestTime() {
        return restTime;
    }

    public void setRestTime(Integer restTime) {
        this.restTime = restTime;
    }

    @Override
    public String toString() {
        return "ExerciseSet{" +
                "id=" + id +
                ", seriesCount=" + seriesCount +
                ", minReps=" + minReps +
                ", maxReps=" + maxReps +
                ", restTime=" + restTime +
                '}';
    }
}
