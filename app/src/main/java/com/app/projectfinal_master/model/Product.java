package com.app.projectfinal_master.model;

import org.json.JSONArray;
import org.json.JSONObject;

public class Product {

    private int id;
    private int id_category;
    private int id_color;
    private int id_size;
    private String code;
    private String name;
    private String image_thumb;
    private JSONArray image_large;
    private String selling_price;
    private String quantity;
    private String description;
    private String rate;
    private boolean discount;

    public Product(int id_category, int id_color, int id_size, String code, String name, JSONArray image_large, String selling_price, int quantity, String description, String rate, boolean discount) {
        this.id_category = id_category;
        this.id_color = id_color;
        this.id_size = id_size;
        this.code = code;
        this.name = name;
        this.image_large = image_large;
        this.selling_price = selling_price;
        this.description = description;
        this.rate = rate;
        this.discount = discount;
    }

    public Product(int id_category, int id_color, String code, String name, String image_thumb, String selling_price) {
        this.id_category = id_category;
        this.id_color = id_color;
        this.code = code;
        this.name = name;
        this.image_thumb = image_thumb;
        this.selling_price = selling_price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_category() {
        return id_category;
    }

    public void setId_category(int id_category) {
        this.id_category = id_category;
    }

    public int getId_color() {
        return id_color;
    }

    public void setId_color(int id_color) {
        this.id_color = id_color;
    }

    public int getId_size() {
        return id_size;
    }

    public void setId_size(int id_size) {
        this.id_size = id_size;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage_thumb() {
        return image_thumb;
    }

    public void setImage_thumb(String image_thumb) {
        this.image_thumb = image_thumb;
    }

    public JSONArray getImage_large() {
        return image_large;
    }

    public void setImage_large(JSONArray image_large) {
        this.image_large = image_large;
    }

    public String getSelling_price() {
        return selling_price;
    }

    public void setSelling_price(String selling_price) {
        this.selling_price = selling_price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public boolean isDiscount() {
        return discount;
    }

    public void setDiscount(boolean discount) {
        this.discount = discount;
    }
}
