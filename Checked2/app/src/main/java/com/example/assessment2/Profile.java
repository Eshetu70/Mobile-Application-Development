package com.example.assessment2;

import java.io.Serializable;

public class Profile implements Serializable {
    double Weight;
    String gender;
    public Profile() {
    }

    public Profile(double weight, String gender) {
        Weight = weight;
        this.gender = gender;
    }

    public double getWeight() {
        return Weight;
    }

    public void setWeight(double weight) {
        Weight = weight;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
