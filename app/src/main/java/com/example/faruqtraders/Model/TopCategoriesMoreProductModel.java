package com.example.faruqtraders.Model;

public class TopCategoriesMoreProductModel {

    int image, discount_price, main_price;
    String name;

    public TopCategoriesMoreProductModel(int image, int discount_price, int main_price, String name) {
        this.image = image;
        this.discount_price = discount_price;
        this.main_price = main_price;
        this.name = name;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public int getDiscount_price() {
        return discount_price;
    }

    public void setDiscount_price(int discount_price) {
        this.discount_price = discount_price;
    }

    public int getMain_price() {
        return main_price;
    }

    public void setMain_price(int main_price) {
        this.main_price = main_price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
