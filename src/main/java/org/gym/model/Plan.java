package org.gym.model;

public class Plan {
    private Integer id;
    private String name;
    private Double monthlyFee;

    public Plan() {}

    public Plan(Integer id, String name, Double monthlyFee) {
        this.id = id;
        this.name = name;
        this.monthlyFee = monthlyFee;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getMonthlyFee() {
        return monthlyFee;
    }

    public void setMonthlyFee(Double monthlyFee) {
        this.monthlyFee = monthlyFee;
    }

    @Override
    public String toString() {
        return "Plan{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", monthlyFee=" + monthlyFee +
                '}';
    }
}
