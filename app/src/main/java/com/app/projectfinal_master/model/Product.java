package com.app.projectfinal_master.model;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import org.json.JSONArray;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Product implements Serializable {
    private String idProduct;
    private int idCategory;
    private String name;
    private String sex;
    private String imageThumb;
    private JSONArray imageLarge;
    private String sellingPrice;
    private String color;
    private String size;
    private String quantity;
    private double rate;
    private String description;
    private int discount;
    private List<Product> products = new ArrayList<>();

    public Product() {
    }

    public Product(String idProduct, int idCategory, String name, String sex, String imageThumb, String sellingPrice, int discount) {
        this.idProduct = idProduct;
        this.idCategory = idCategory;
        this.name = name;
        this.sex = sex;
        this.imageThumb = imageThumb;
        this.sellingPrice = sellingPrice;
        this.discount = discount;
    }

    public Product(String idProduct, int idCategory, String name, String sex, String imageThumb, JSONArray imageLarge, String sellingPrice, String color, String size, String quantity, double rate, String description, int discount) {
        this.idProduct = idProduct;
        this.idCategory = idCategory;
        this.name = name;
        this.sex = sex;
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

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
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
        return idCategory == product.idCategory && Double.compare(product.rate, rate) == 0 && discount == product.discount && Objects.equals(idProduct, product.idProduct) && Objects.equals(name, product.name) && Objects.equals(sex, product.sex) && Objects.equals(imageThumb, product.imageThumb) && Objects.equals(imageLarge, product.imageLarge) && Objects.equals(sellingPrice, product.sellingPrice) && Objects.equals(color, product.color) && Objects.equals(size, product.size) && Objects.equals(quantity, product.quantity) && Objects.equals(description, product.description) && Objects.equals(products, product.products);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idProduct, idCategory, name, sex, imageThumb, imageLarge, sellingPrice, color, size, quantity, rate, description, discount, products);
    }

    public static DiffUtil.ItemCallback<Product> itemCallback = new DiffUtil.ItemCallback<Product>() {
        @Override
        public boolean areItemsTheSame(@NonNull Product oldItem, @NonNull Product newItem) {
            return oldItem.getIdProduct().equals(newItem.getIdProduct());
        }

        @Override
        public boolean areContentsTheSame(@NonNull Product oldItem, @NonNull Product newItem) {
            return oldItem.equals(newItem);
        }
    };
}
