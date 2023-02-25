package com.example.myhandlocksstore.models;

public class PopularModel {
    String name;
    String description;
    String quality;
    String type;
    String brand;
    String img_url;

    public PopularModel() {

    }

    public PopularModel(String name, String description, String quality, String type, String brand, String img_url) {
        this.name = name;
        this.description = description;
        this.quality = quality;
        this.type = type;
        this.brand = brand;
        this.img_url = img_url;
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

    public String getQuality() {
        return quality;
    }

    public void setQuality(String quality) {
        this.quality = quality;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }
}
