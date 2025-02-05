package edu.uncc.finalexam;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class NFT {

    public  String image_thumbnail_url, name, collection_name, banner_image_url, user_id, doc_id;

    public NFT(String image_thumbnail_url, String name, String collection_name, String banner_image_url) {
        this.image_thumbnail_url = image_thumbnail_url;
        this.name = name;
        this.collection_name = collection_name;
        this.banner_image_url = banner_image_url;
    }

    public NFT() {
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getDoc_id() {
        return doc_id;
    }

    public void setDoc_id(String doc_id) {
        this.doc_id = doc_id;
    }

    public NFT(JSONObject json) throws JSONException {
        this.image_thumbnail_url=json.getString("image_thumbnail_url");
        this.name=json.getString("name");
        this.collection_name=json.getJSONObject("collection").getString("name");
        this.banner_image_url =json.getJSONObject("collection").getString("banner_image_url");
    }

    public String getImage_thumbnail_url() {
        return image_thumbnail_url;
    }

    public void setImage_thumbnail_url(String image_thumbnail_url) {
        this.image_thumbnail_url = image_thumbnail_url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCollection_name() {
        return collection_name;
    }

    public void setCollection_name(String collection_name) {
        this.collection_name = collection_name;
    }

    public String getBanner_image_url() {
        return banner_image_url;
    }

    public void setBanner_image_url(String banner_image_url) {
        this.banner_image_url = banner_image_url;
    }
}
