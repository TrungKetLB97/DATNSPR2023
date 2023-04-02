package com.app.projectfinal_master.model;

public class CartItem {
    int amount;
    String code_product, name, price, image_thumb;
    boolean favorite;



    public CartItem(String code_product, String name, int amount, String price, String image_thumb, boolean favorite) {
        this.code_product = code_product;
        this.name = name;
        this.amount = amount;
        this.price = price;
        this.image_thumb = image_thumb;
        this.favorite = favorite;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getCode_product() {
        return code_product;
    }

    public void setCode_product(String code_product) {
        this.code_product = code_product;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getImage_thumb() {
        return image_thumb;
    }

    public void setImage_thumb(String image_thumb) {
        this.image_thumb = image_thumb;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }
}
