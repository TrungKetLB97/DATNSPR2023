package com.app.projectfinal_master.model;

import java.io.Serializable;

public class Cart implements Serializable {
    Product product;
    int quantity;
    int idColor;
    int idSize;
    boolean isChose;

    public Cart() {
    }

    public Cart(Product product, int idColor, int idSize, int quantity) {
        this.product = product;
        this.idColor = idColor;
        this.idSize = idSize;
        this.quantity = quantity;
    }

    public int getIdColor() {
        return idColor;
    }

    public void setIdColor(int idColor) {
        this.idColor = idColor;
    }

    public int getIdSize() {
        return idSize;
    }

    public void setIdSize(int idSize) {
        this.idSize = idSize;
    }

    public boolean isChose() {
        return isChose;
    }

    public void setChose(boolean chose) {
        isChose = chose;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
