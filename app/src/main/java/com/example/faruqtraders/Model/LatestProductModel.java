package com.example.faruqtraders.Model;

import java.io.Serializable;

public class LatestProductModel implements Serializable {

    private String name, category;
    double discount_price, main_price;
    private int  image;


    public LatestProductModel(String name, String category, double discount_price, double main_price, int image) {
        this.name = name;
        this.category = category;
        this.discount_price = discount_price;
        this.main_price = main_price;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getDiscount_price() {
        return discount_price;
    }

    public void setDiscount_price(double discount_price) {
        this.discount_price = discount_price;
    }

    public double getMain_price() {
        return main_price;
    }

    public void setMain_price(double main_price) {
        this.main_price = main_price;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
