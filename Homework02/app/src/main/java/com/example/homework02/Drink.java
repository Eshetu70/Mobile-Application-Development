package com.example.homework02;

import java.io.Serializable;
import java.util.Date;

public class Drink implements Serializable {
    double alcohol, size;
    Date addedON;

    public Date getAddedON() {
        return addedON;
    }

    public void setAddedON(Date addedON) {
        this.addedON = addedON;
    }

    public Drink() {
        this.addedON = new Date();
    }

    public Drink(double alcohol, double size) {
        this.alcohol = alcohol;
        this.size = size;
        this.addedON = new Date();
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
