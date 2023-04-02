package com.app.projectfinal_master.model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;

public class Product implements Serializable {
    private String idProduct;
    private int idCategory;
    private String name;
    private String imageThumb;
    private JSONArray imageLarge;
    private String sellingPrice;
    private String color;
    private String size;
    private String quantity;
    private String rate;
    private String description;
    private boolean discount;

    public Product(String idProduct, int idCategory, String name, String imageThumb, String sellingPrice, boolean discount) {
        this.idProduct = idProduct;
        this.idCategory = idCategory;
        this.name = name;
        this.imageThumb = imageThumb;
        this.sellingPrice = sellingPrice;
        this.discount = discount;
    }

    public Product(String idProduct, int idCategory, String name, String imageThumb, JSONArray imageLarge, String sellingPrice, String color, String size, String quantity, String rate, String description, boolean discount) {
        this.idProduct = idProduct;
        this.idCategory = idCategory;
        this.name = name;
        this.imageThumb = imageThumb;
        this.imageLarge = imageLarge;
        this.sellingPrice = sellingPrice;
        this.color = color;
        this.size = size;
        this.quantity = quantity;
        this.rate = rate;
        this.description = description;
        this.discount = discount;
    }

    public String getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(String idProduct) {
        this.idProduct = idProduct;
    }

    public int getIdCategory() {
        return idCategory;
    }

    public void setIdCategory(int idCategory) {
        this.idCategory = idCategory;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageThumb() {
        return imageThumb;
    }

    public void setImageThumb(String imageThumb) {
        this.imageThumb = imageThumb;
    }

    public JSONArray getImageLarge() {
        return imageLarge;
    }

    public void setImageLarge(JSONArray imageLarge) {
        this.imageLarge = imageLarge;
    }

    public String getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(String sellingPrice) {
        this.sellingPrice = sellingPrice;
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

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isDiscount() {
        return discount;
    }

    public void setDiscount(boolean discount) {
        this.discount = discount;
    }
}
