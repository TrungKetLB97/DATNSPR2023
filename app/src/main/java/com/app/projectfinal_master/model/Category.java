package com.app.projectfinal_master.model;

public class Category {

    private int id;
    private String title, image;

    public Category(int idCategory, String title, String image) {
        this.id = idCategory;
        this.title = title;
        this.image = image;
    }

    public Category(String title, String image) {
        this.title = title;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
