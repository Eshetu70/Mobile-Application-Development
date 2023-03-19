package com.example.midterm.models;

import org.json.JSONObject;

public class Review {
    String review_text, genre, created_at;

    public Review() {
    }

    public Review(JSONObject json) {
/*
 {
            "review_text": "This is an amazing album !!",
            "genre": "Country",
            "created_at": "2023-03-16 06:00:05"
        }
 */
    }

    public String getReview_text() {
        return review_text;
    }

    public void setReview_text(String review_text) {
        this.review_text = review_text;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }
}
