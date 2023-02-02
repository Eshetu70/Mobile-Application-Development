package com.example.activityinclass;

import java.io.Serializable;

public class Profile implements Serializable {
    String name, email, id, depertment;

    public Profile() {

    }

    public Profile(String name, String email, String id, String depertment) {
        this.name = name;
        this.email = email;
        this.id = id;
        this.depertment = depertment;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDepertment() {
        return depertment;
    }

    public void setDepertment(String depertment) {
        this.depertment = depertment;
    }
}
