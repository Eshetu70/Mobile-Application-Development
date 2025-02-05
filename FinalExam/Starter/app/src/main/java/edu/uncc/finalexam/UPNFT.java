package edu.uncc.finalexam;

public class UPNFT {
    public String banner_image_url, collection_name, doc_id, image_thumbnail_url,name, user_id;


    public UPNFT() {
    }

    public UPNFT(String banner_image_url, String collection_name, String doc_id, String image_thumbnail_url, String name, String user_id) {
        this.banner_image_url = banner_image_url;
        this.collection_name = collection_name;
        this.doc_id = doc_id;
        this.image_thumbnail_url = image_thumbnail_url;
        this.name = name;
        this.user_id = user_id;
    }

    public String getBanner_image_url() {
        return banner_image_url;
    }

    public void setBanner_image_url(String banner_image_url) {
        this.banner_image_url = banner_image_url;
    }

    public String getCollection_name() {
        return collection_name;
    }

    public void setCollection_name(String collection_name) {
        this.collection_name = collection_name;
    }

    public String getDoc_id() {
        return doc_id;
    }

    public void setDoc_id(String doc_id) {
        this.doc_id = doc_id;
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

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
}
