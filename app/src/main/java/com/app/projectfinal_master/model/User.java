package com.app.projectfinal_master.model;

public class User {
    String idUser, email, nameUser, phone_number, avatar, birth, sex;

    public User() {
    }

    public User(String idUser, String email, String nameUser, String phone_number, String avatar, String birth, String sex) {
        this.idUser = idUser;
        this.email = email;
        this.nameUser = nameUser;
        this.phone_number = phone_number;
        this.avatar = avatar;
        this.birth = birth;
        this.sex = sex;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNameUser() {
        return nameUser;
    }

    public void setNameUser(String nameUser) {
        this.nameUser = nameUser;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
}
