package com.example.myhandlocksstore.models;

public class BrandsModel {
    String name;
    String description;
    String img_url;
    String brand;

    public BrandsModel() {
    }

    public BrandsModel(String name, String description, String img_url, String brand) {
        this.name = name;
        this.description = description;
        this.img_url = img_url;
        this.brand = brand;
    }

    public String getBrand() {
        return brand;
    }

    public void setType(String type) {
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

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }
}
