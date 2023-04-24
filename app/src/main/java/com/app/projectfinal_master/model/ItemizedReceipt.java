package com.app.projectfinal_master.model;

public class ItemizedReceipt {
    String code_product;
    int quantity;
    String price;

    public ItemizedReceipt(String code_product, int quantity, String price) {
        this.code_product = code_product;
        this.quantity = quantity;
        this.price = price;
    }
}
