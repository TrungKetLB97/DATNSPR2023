package com.app.projectfinal_master.model;

public class Cart {
    String id_product, id_shop, name_product, amount, price, img_product;

    public Cart(String id_product, String id_shop, String name_product, String amount, String price, String img_product) {
        this.id_product = id_product;
        this.id_shop = id_shop;
        this.name_product = name_product;
        this.amount = amount;
        this.price = price;
        this.img_product = img_product;
    }

    public String getName_product() {
        return name_product;
    }

    public void setName_product(String name_product) {
        this.name_product = name_product;
    }

    public String getId_product() {
        return id_product;
    }

    public void setId_product(String id_product) {
        this.id_product = id_product;
    }

    public String getId_shop() {
        return id_shop;
    }

    public void setId_shop(String id_shop) {
        this.id_shop = id_shop;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getImg_product() {
        return img_product;
    }

    public void setImg_product(String img_product) {
        this.img_product = img_product;
    }
}
