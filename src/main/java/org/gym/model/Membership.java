package org.gym.model;

import java.time.LocalDate;

public class Membership {
    private Integer id;
    private Integer studentId;
    private Integer planId;

    private Integer currentWorkoutId;
    private LocalDate startDate;

    public Membership() {}

    public Membership(Integer id, Integer studentId, Integer planId, Integer currentWorkoutId, LocalDate startDate) {
        this.id = id;
        this.studentId = studentId;
        this.planId = planId;
        this.currentWorkoutId = currentWorkoutId;
        this.startDate = startDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    public Integer getPlanId() {
        return planId;
    }

    public void setPlanId(Integer planId) {
        this.planId = planId;
    }

    public Integer getCurrentWorkoutId() {
        return currentWorkoutId;
    }

    public void setCurrentWorkoutId(Integer currentWorkoutId) {
        this.currentWorkoutId = currentWorkoutId;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    @Override
    public String toString() {
        return "Membership{" +
                "id=" + id +
                ", studentId=" + studentId +
                ", PlanId=" + planId +
                ", currentWorkoutId=" + currentWorkoutId +
                ", startDate=" + startDate +
                '}';
    }
}
