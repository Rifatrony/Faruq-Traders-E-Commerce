package com.example.faruqtraders.Model;

import androidx.recyclerview.widget.RecyclerView;

public class TopInCategoriesModel {

    private String image, name, category,price;

    public TopInCategoriesModel(String image, String name, String category, String price) {
        this.image = image;
        this.name = name;
        this.category = category;
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
