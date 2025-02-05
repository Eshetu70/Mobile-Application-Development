package com.example.assessment8.db;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.Date;
@Entity(tableName = "drink")
public class Drink implements Serializable {
    @PrimaryKey(autoGenerate = true)
    public int did;
    public double alcohol, size;
    public String type;
    public Long addedOn; //needs to be stored as a long
    public int id;


    public Drink(double alcohol, double size, String type) {
        this.alcohol = alcohol;
        this.size = size;
        this.type = type;
        addedOn = (new Date()).getTime(); //storing the date as a long
    }

    public Drink() {
    }

    public Long getAddedOn() {
        return addedOn;
    }

    public void setAddedOn(Long addedOn) {
        this.addedOn = addedOn;
    }

    public Date getAddedOnAsDate() {
        return new Date(this.addedOn);
    }

    public void setAddedOnAsDate(Date addedOnAsDate) {
        this.addedOn = addedOnAsDate.getTime();
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
