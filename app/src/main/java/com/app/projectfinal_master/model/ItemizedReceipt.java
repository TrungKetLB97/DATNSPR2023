package com.app.projectfinal_master.model;

import java.io.Serializable;

public class ItemizedReceipt implements Serializable {
    String code_product;
    int id_color;
    int id_size;
    int quantity;
    String price;
    String name;
    String image_thumb;
    String color;
    String size;

    public ItemizedReceipt(String code_product, int id_color, int id_size, int quantity, String price) {
        this.code_product = code_product;
        this.id_color = id_color;
        this.id_size = id_size;
        this.quantity = quantity;
        this.price = price;
    }

    public ItemizedReceipt(String code_product, int quantity, String price, String name, String image_thumb, String color, String size) {
        this.code_product = code_product;
        this.quantity = quantity;
        this.price = price;
        this.name = name;
        this.image_thumb = image_thumb;
        this.color = color;
        this.size = size;
    }

    public String getImage_thumb() {
        return image_thumb;
    }

    public void setImage_thumb(String image_thumb) {
        this.image_thumb = image_thumb;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode_product() {
        return code_product;
    }

    public void setCode_product(String code_product) {
        this.code_product = code_product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
