package com.app.projectfinal_master.model;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import java.io.Serializable;
import java.util.Objects;

public class Address implements Serializable {
    private int idAddress;
    private String receiver;
    private String city;
    private String cityCode;
    private String district;
    private String districtCode;
    private String commune;
    private String street;
    private String phoneNumber;
    private int setDefault;

    public Address(String city, String district, String commune, String street, String phoneNumber) {
        this.city = city;
        this.district = district;
        this.commune = commune;
        this.street = street;
        this.phoneNumber = phoneNumber;
    }

    public Address(int idAddress, String receiver, String city, String district, String commune, String street, String phoneNumber, int setDefault) {
        this.idAddress = idAddress;
        this.receiver = receiver;
        this.city = city;
        this.district = district;
        this.commune = commune;
        this.street = street;
        this.phoneNumber = phoneNumber;
        this.setDefault = setDefault;
    }

    public Address() {
    }

    public int getIdAddress() {
        return idAddress;
    }

    public void setIdAddress(int idAddress) {
        this.idAddress = idAddress;
    }

    public int getSetDefault() {
        return setDefault;
    }

    public void setSetDefault(int setDefault) {
        this.setDefault = setDefault;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getDistrictCode() {
        return districtCode;
    }

    public void setDistrictCode(String districtCode) {
        this.districtCode = districtCode;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getCommune() {
        return commune;
    }

    public void setCommune(String commune) {
        this.commune = commune;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getAddress() {
        if (city != null && district == null) {
            return city;
        } else if (city == null && district != null) {
            return district;
        } else {
            return commune;
        }
    }

    @Override
    public String toString() {
        return city + ", " + district + ", " + commune;
    }

    public String toAddress() {
        return street + ", " + commune + ", " + district + ", " + city;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;
        return city == address.city && district == address.district && commune == address.commune;
    }

    @Override
    public int hashCode() {
        return Objects.hash(city, district, commune);
    }

    public static DiffUtil.ItemCallback<Address> itemCallback = new DiffUtil.ItemCallback<Address>() {
        @Override
        public boolean areItemsTheSame(@NonNull Address oldItem, @NonNull Address newItem) {
            return oldItem.equals(newItem);
        }

        @Override
        public boolean areContentsTheSame(@NonNull Address oldItem, @NonNull Address newItem) {
            return oldItem.equals(newItem);
        }
    };
}
