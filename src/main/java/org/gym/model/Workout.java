package org.gym.model;

import java.time.LocalDate;

public class Workout {
    private Integer id;
    private Integer exerciseSetsId;
    private Integer membershipId;

    private String name;
    private LocalDate startDate;
    private LocalDate endDate;

    public Workout() {}

    public Workout(Integer id, Integer exerciseSetsId, Integer membershipId, String name, LocalDate startDate, LocalDate endDate) {
        this.id = id;
        this.exerciseSetsId = exerciseSetsId;
        this.membershipId = membershipId;
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getExerciseSetsId() {
        return exerciseSetsId;
    }

    public void setExerciseSetsId(Integer exerciseSetsId) {
        this.exerciseSetsId = exerciseSetsId;
    }

    public Integer getMembershipId() {
        return membershipId;
    }

    public void setMembershipId(Integer membershipId) {
        this.membershipId = membershipId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        return "Workout{" +
                "id=" + id +
                ", exerciseSetsId=" + exerciseSetsId +
                ", membershipId=" + membershipId +
                ", name='" + name + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                '}';
    }
}
