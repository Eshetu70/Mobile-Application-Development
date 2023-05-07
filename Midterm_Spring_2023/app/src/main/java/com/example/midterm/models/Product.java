package com.example.midterm.models;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class Product implements Serializable {
    private String pid, name, img_url, price, description, review_count;


    public Product(){

    }

    public Product(JSONObject json) throws JSONException {


        this.pid= json.getString("pid");
        this.name =json.getString("name");
        this.img_url =json.getString("img_url");
        this.price=json.getString("price");
        this.description=json.getString("description");
        this.review_count =json.getString("review_count");
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getReview_count() {
        return review_count;
    }

    public void setReview_count(String review_count) {
        this.review_count = review_count;
    }
}
