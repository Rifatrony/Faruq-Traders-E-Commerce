package com.binaryit.faruqtraders.Model;

import java.io.Serializable;

public class BestSellingModel implements Serializable {

    private String name, category;
    double main_price, discount_price;
    private int image;

    public BestSellingModel(String name, String category, double main_price, double discount_price, int image) {
        this.name = name;
        this.category = category;
        this.main_price = main_price;
        this.discount_price = discount_price;
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

    public double getMain_price() {
        return main_price;
    }

    public void setMain_price(double main_price) {
        this.main_price = main_price;
    }

    public double getDiscount_price() {
        return discount_price;
    }

    public void setDiscount_price(double discount_price) {
        this.discount_price = discount_price;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
