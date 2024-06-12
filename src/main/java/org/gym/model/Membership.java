package org.gym.model;

import java.time.LocalDate;

public class Membership {
    private Integer id;
    private Integer studentId;
    private Integer planId;

    private Integer currentWorkoutId;
    private LocalDate startDate;

    private String cardNumber;
    private String cardHolderName;
    private String cardExpiryDate;
    private String cardCvv;

    public Membership() {}

    public Membership(Integer id, Integer studentId, Integer planId, Integer currentWorkoutId, LocalDate startDate, String cardNumber, String cardHolderName, String cardExpiryDate, String cardCvv) {
        this.id = id;
        this.studentId = studentId;
        this.planId = planId;
        this.currentWorkoutId = currentWorkoutId;
        this.startDate = startDate;
        this.cardNumber = cardNumber;
        this.cardHolderName = cardHolderName;
        this.cardExpiryDate = cardExpiryDate;
        this.cardCvv = cardCvv;
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

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getCardHolderName() {
        return cardHolderName;
    }

    public void setCardHolderName(String cardHolderName) {
        this.cardHolderName = cardHolderName;
    }

    public String getCardExpiryDate() {
        return cardExpiryDate;
    }

    public void setCardExpiryDate(String cardExpireDate) {
        this.cardExpiryDate = cardExpireDate;
    }

    public String getCardCvv() {
        return cardCvv;
    }

    public void setCardCvv(String cardCvv) {
        this.cardCvv = cardCvv;
    }

    @Override
    public String toString() {
        return "Membership{" +
                "id=" + id +
                ", studentId=" + studentId +
                ", planId=" + planId +
                ", currentWorkoutId=" + currentWorkoutId +
                ", startDate=" + startDate +
                ", cardNumber='" + cardNumber + '\'' +
                ", cardHolderName='" + cardHolderName + '\'' +
                ", cardExpiryDate='" + cardExpiryDate + '\'' +
                ", cardCvv='" + cardCvv + '\'' +
                '}';
    }
}
