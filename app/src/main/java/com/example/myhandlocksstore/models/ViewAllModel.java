package com.example.myhandlocksstore.models;

import java.io.Serializable;

public class ViewAllModel implements Serializable {
    String name;
    String description;
    String img_url;
    String type;
    String brand;
    int price;

    public ViewAllModel() {
    }


    public ViewAllModel(String name, String description, String img_url, String type, String brand, int price) {
        this.name = name;
        this.description = description;
        this.img_url = img_url;
        this.type = type;
        this.brand = brand;
        this.price = price;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_name(String img_url) {
        this.img_url = img_url;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
