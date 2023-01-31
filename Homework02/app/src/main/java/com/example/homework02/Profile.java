package com.example.homework02;

import java.io.Serializable;

public class Profile implements Serializable {
    double weight;
    String gender;
    public Profile() {
    }

    public Profile(double weight, String gender) {
        this.weight = weight;
        this.gender = gender;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
