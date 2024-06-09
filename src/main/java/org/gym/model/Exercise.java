package org.gym.model;

public class Exercise {
    private Integer id;
    private String name;
    private String musclesActivates;

    public Exercise() {}

    public Exercise(Integer id, String name, String musclesActivates) {
        this.id = id;
        this.name = name;
        this.musclesActivates = musclesActivates;
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

    public String getMusclesActivates() {
        return musclesActivates;
    }

    public void setMusclesActivates(String musclesActivates) {
        this.musclesActivates = musclesActivates;
    }

    @Override
    public String toString() {
        return "Exercise{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", musclesActivates='" + musclesActivates + '\'' +
                '}';
    }
}
