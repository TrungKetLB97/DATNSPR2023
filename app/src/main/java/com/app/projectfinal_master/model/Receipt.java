package com.app.projectfinal_master.model;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import java.io.Serializable;
import java.util.Objects;

public class Receipt implements Serializable {
    String codeReceipt, username, address, phoneNumber, receiptStatus, payStatus, orderDate, deliveringDate, confirmationDate, sumPrice;
    int quantity;
    ItemizedReceipt itemizedReceipt;

    public Receipt(String codeReceipt, String username, String address, String phoneNumber, int quantity, String sumPrice, String receiptStatus, String payStatus, String orderDate, String deliveringDate, String confirmationDate, ItemizedReceipt itemizedReceipt) {
        this.codeReceipt = codeReceipt;
        this.username = username;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.quantity = quantity;
        this.sumPrice = sumPrice;
        this.receiptStatus = receiptStatus;
        this.payStatus = payStatus;
        this.orderDate = orderDate;
        this.deliveringDate = deliveringDate;
        this.confirmationDate = confirmationDate;
        this.itemizedReceipt = itemizedReceipt;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCodeReceipt() {
        return codeReceipt;
    }

    public void setCodeReceipt(String codeReceipt) {
        this.codeReceipt = codeReceipt;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getReceiptStatus() {
        return receiptStatus;
    }

    public void setReceiptStatus(String receiptStatus) {
        this.receiptStatus = receiptStatus;
    }

    public String getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(String payStatus) {
        this.payStatus = payStatus;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getDeliveringDate() {
        return deliveringDate;
    }

    public void setDeliveringDate(String deliveringDate) {
        this.deliveringDate = deliveringDate;
    }

    public String getConfirmationDate() {
        return confirmationDate;
    }

    public void setConfirmationDate(String confirmationDate) {
        this.confirmationDate = confirmationDate;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getSumPrice() {
        return sumPrice;
    }

    public void setSumPrice(String sumPrice) {
        this.sumPrice = sumPrice;
    }

    public ItemizedReceipt getItemizedReceipt() {
        return itemizedReceipt;
    }

    public void setItemizedReceipt(ItemizedReceipt itemizedReceipt) {
        this.itemizedReceipt = itemizedReceipt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Receipt receipt = (Receipt) o;
        return quantity == receipt.quantity && Objects.equals(codeReceipt, receipt.codeReceipt) && Objects.equals(address, receipt.address) && Objects.equals(phoneNumber, receipt.phoneNumber) && Objects.equals(receiptStatus, receipt.receiptStatus) && Objects.equals(payStatus, receipt.payStatus) && Objects.equals(orderDate, receipt.orderDate) && Objects.equals(deliveringDate, receipt.deliveringDate) && Objects.equals(confirmationDate, receipt.confirmationDate) && Objects.equals(sumPrice, receipt.sumPrice) && Objects.equals(itemizedReceipt, receipt.itemizedReceipt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codeReceipt, address, phoneNumber, receiptStatus, payStatus, orderDate, deliveringDate, confirmationDate, sumPrice, quantity, itemizedReceipt);
    }

    public static DiffUtil.ItemCallback<Receipt> itemCallback = new DiffUtil.ItemCallback<Receipt>() {
        @Override
        public boolean areItemsTheSame(@NonNull Receipt oldItem, @NonNull Receipt newItem) {
            return oldItem.equals(newItem);
        }

        @Override
        public boolean areContentsTheSame(@NonNull Receipt oldItem, @NonNull Receipt newItem) {
            return oldItem.equals(newItem);
        }
    };
}
