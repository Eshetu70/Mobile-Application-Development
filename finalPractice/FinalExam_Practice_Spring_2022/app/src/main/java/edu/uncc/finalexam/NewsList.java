package edu.uncc.finalexam;

import java.io.Serializable;

public class NewsList implements Serializable {
    public String title, author, urlToImage, url, name, uid, listName, listId, userId;

    public NewsList(String title, String author, String urlToImage, String url, String name) {
        this.title = title;
        this.author = author;
        this.urlToImage = urlToImage;
        this.url = url;
        this.name = name;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public NewsList(String title, String author, String urlToImage, String url, String name, String uid, String listName, String listId, String userId, double quantity) {
        this.title = title;
        this.author = author;
        this.urlToImage = urlToImage;
        this.url = url;
        this.name = name;
        this.uid = uid;
        this.listName = listName;
        this.listId = listId;
        this.userId = userId;
        this.quantity = quantity;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getUrlToImage() {
        return urlToImage;
    }

    public void setUrlToImage(String urlToImage) {
        this.urlToImage = urlToImage;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public double quantity;
    public NewsList() {
    }

    public String getListId() {
        return listId;
    }

    public void setListId(String listId) {
        this.listId = listId;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public String getListName() {
        return listName;
    }

    public void setListName(String listName) {
        this.listName = listName;
    }

    public NewsList(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    @Override
    public String toString() {
        return listName + "  "  + quantity;
    }
}

