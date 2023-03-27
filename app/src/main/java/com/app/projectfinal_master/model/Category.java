package com.app.projectfinal_master.model;

public class Category {

    private int id;
    private String sex, title, image;

    public Category(int idCategory, String sex, String title, String image) {
        this.id = idCategory;
        this.sex = sex;
        this.title = title;
        this.image = image;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
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
