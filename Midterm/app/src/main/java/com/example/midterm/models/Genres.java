package com.example.midterm.models;

import org.json.JSONException;
import org.json.JSONObject;

public class Genres {
 String genreId, name, url;

    public Genres() {
    }


    public Genres(JSONObject json) throws JSONException {
        this.genreId =json.getString("genreId");
        this.name =json.getString("name");
        this.url =json.getString("url");
    }

    public String getGenreId() {
        return genreId;
    }

    public void setGenreId(String genreId) {
        this.genreId = genreId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
