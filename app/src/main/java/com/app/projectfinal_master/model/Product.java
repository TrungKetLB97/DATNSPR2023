package com.app.projectfinal_master.model;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import org.json.JSONArray;

import java.io.Serializable;
import java.util.Objects;

public class Product implements Serializable {
    private String codeProduct;
    private int idCategory;
    private String nameCategory;
    private int idColor;
    private String color;
    private int idSize;
    private String size;
    private String name;
    private String sex;
    private String imageThumb;
    private JSONArray imageLarge;
    private String sellingPrice;
    private int quantity;
    private double rate;
    private String description;
    private int discount;

    public Product() {
    }

    public Product(String codeProduct, int idCategory, String name, String sex, String imageThumb, String sellingPrice, int quantity, int discount) {
        this.codeProduct = codeProduct;
        this.idCategory = idCategory;
        this.name = name;
        this.sex = sex;
        this.imageThumb = imageThumb;
        this.sellingPrice = sellingPrice;
        this.quantity = quantity;
        this.discount = discount;
    }

    public Product(String codeProduct, int idColor, int idSize, String name, String imageThumb, String sellingPrice, int quantity, int discount) {
        this.codeProduct = codeProduct;
        this.idColor = idColor;
        this.idSize = idSize;
        this.name = name;
        this.imageThumb = imageThumb;
        this.sellingPrice = sellingPrice;
        this.quantity = quantity;
        this.discount = discount;
    }

    public Product(String codeProduct, int idColor, int idSize, String name, String sex, String imageThumb, JSONArray imageLarge, String sellingPrice, int quantity, double rate, String description, int discount) {
        this.codeProduct = codeProduct;
        this.idColor = idColor;
        this.idSize = idSize;
        this.name = name;
        this.sex = sex;
        this.imageThumb = imageThumb;
        this.imageLarge = imageLarge;
        this.sellingPrice = sellingPrice;
        this.quantity = quantity;
        this.rate = rate;
        this.description = description;
        this.discount = discount;
    }

    public int getIdCategory() {
        return idCategory;
    }

    public void setIdCategory(int idCategory) {
        this.idCategory = idCategory;
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

    public String getNameCategory() {
        return nameCategory;
    }

    public void setNameCategory(String nameCategory) {
        this.nameCategory = nameCategory;
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

    public int getDiscount() {
        return discount;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getCodeProduct() {
        return codeProduct;
    }

    public void setCodeProduct(String codeProduct) {
        this.codeProduct = codeProduct;
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

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int isDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Double.compare(product.rate, rate) == 0 && discount == product.discount && Objects.equals(codeProduct, product.codeProduct) && Objects.equals(nameCategory, product.nameCategory) && Objects.equals(color, product.color) && Objects.equals(size, product.size) && Objects.equals(name, product.name) && Objects.equals(sex, product.sex) && Objects.equals(imageThumb, product.imageThumb) && Objects.equals(imageLarge, product.imageLarge) && Objects.equals(sellingPrice, product.sellingPrice) && Objects.equals(quantity, product.quantity) && Objects.equals(description, product.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codeProduct, nameCategory, color, size, name, sex, imageThumb, imageLarge, sellingPrice, quantity, rate, description, discount);
    }

    public static DiffUtil.ItemCallback<Product> itemCallback = new DiffUtil.ItemCallback<Product>() {
        @Override
        public boolean areItemsTheSame(@NonNull Product oldItem, @NonNull Product newItem) {
            return oldItem.getCodeProduct().equals(newItem.getCodeProduct());
        }

        @Override
        public boolean areContentsTheSame(@NonNull Product oldItem, @NonNull Product newItem) {
            return oldItem.equals(newItem);
        }
    };
}
