package com.example.finalexam;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

public class Photo implements Serializable {
  public String id, description, thumb, username, profile_image, url, created_at, favorite, userId, docId, html;
    public ArrayList<String> likes;

    public ArrayList<String> getLikes() {
        return likes;
    }

    public String getDocId() {
        return docId;
    }

    public String getHtml() {
        return html;
    }

    public void setHtml(String html) {
        this.html = html;
    }

    public void setDocId(String docId) {
        this.docId = docId;
    }

    public void setLikes(ArrayList<String> likes) {
        this.likes = likes;
    }

    public Photo(String description, String thumb, String username, String profile_image, String url, String created_at) {
        this.description = description;

        this.thumb = thumb;
        this.username = username;
        this.profile_image = profile_image;
        this.url = url;
        this.created_at = created_at;
    }

    public Photo() {
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Photo(JSONObject json) throws JSONException {

        this.created_at = json.getString("created_at");
        this.description = json.getString("description");
        this.thumb = json.getJSONObject("urls").getString("thumb");
        this.username = json.getJSONObject("user").getString("username");
        this.profile_image = json.getJSONObject("user").getJSONObject("profile_image").getString("small");
        this.id = json.getString("id");
        this.favorite = "false";
        this.html = json.getJSONObject("user").getJSONObject("links").getString("html");



    }

    public String getFavorite() {
        return favorite;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setFavorite(String favorite) {
        this.favorite = favorite;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getProfile_image() {
        return profile_image;
    }

    public void setProfile_image(String profile_image) {
        this.profile_image = profile_image;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }
}
