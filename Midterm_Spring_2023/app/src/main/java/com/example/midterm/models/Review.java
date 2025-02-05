package com.example.midterm.models;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class Review implements Serializable {
    String review, rating, created_at;

    public Review() {
    }

    public Review(JSONObject json) throws JSONException {

        /*
         {
            "review": "This is not a good product !!",
            "rating": "1",
            "created_at": "2023-03-09 01:12:08"
        },
         */
        this.review = json.getString("review");
        this.rating = json.getString("rating");
        this.created_at =json.getString("created_at");

    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    @Override
    public String toString() {
        return "Review{" +
                "review='" + review + '\'' +
                ", rating='" + rating + '\'' +
                ", created_at='" + created_at + '\'' +
                '}';
    }
}
