package com.example.homework02;

import java.io.Serializable;

public class Drink implements Serializable {
    double alcohol, size;

    public Drink() {
    }

    public Drink(double alcohol, double size) {
        this.alcohol = alcohol;
        this.size = size;
    }

    public double getAlcohol() {
        return alcohol;
    }

    public void setAlcohol(double alcohol) {
        this.alcohol = alcohol;
    }

    public double getSize() {
        return size;
    }

    public void setSize(double size) {
        this.size = size;
    }
}
