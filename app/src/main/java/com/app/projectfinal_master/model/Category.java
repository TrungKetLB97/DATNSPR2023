package com.app.projectfinal_master.model;

public class Category {

    private int idCategory;
    private String sex, category, image;

    public Category() {
    }

    public Category(int idCategory, String category, String sex, String image) {
        this.idCategory = idCategory;
        this.category = category;
        this.sex = sex;
        this.image = image;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public int getIdCategory() {
        return idCategory;
    }

    public void setIdCategory(int idCategory) {
        this.idCategory = idCategory;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
