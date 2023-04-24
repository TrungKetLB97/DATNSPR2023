package com.app.projectfinal_master.model;

public class Color {

    private int id;
    private String color;
    private String code;

    public Color() {
    }

    public Color(int id, String color, String code) {
        this.id = id;
        this.color = color;
        this.code = code;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
