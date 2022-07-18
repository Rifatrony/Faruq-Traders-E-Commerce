package com.example.faruqtraders.Model;

public class RelatedProductModel {

    int image;
    String product_name, product_price;

    public RelatedProductModel(int image, String product_name, String product_price) {
        this.image = image;
        this.product_name = product_name;
        this.product_price = product_price;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getProduct_price() {
        return product_price;
    }

    public void setProduct_price(String product_price) {
        this.product_price = product_price;
    }
}
