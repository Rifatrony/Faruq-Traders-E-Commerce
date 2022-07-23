package com.binaryit.faruqtraders.Model;

public class CartModel {

    String image, product_name;
    int product_price, product_quantity, sub_total_price;

    public CartModel(String image, String product_name, int product_price, int product_quantity, int sub_total_price) {
        this.image = image;
        this.product_name = product_name;
        this.product_price = product_price;
        this.product_quantity = product_quantity;
        this.sub_total_price = sub_total_price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public int getProduct_price() {
        return product_price;
    }

    public void setProduct_price(int product_price) {
        this.product_price = product_price;
    }

    public int getProduct_quantity() {
        return product_quantity;
    }

    public void setProduct_quantity(int product_quantity) {
        this.product_quantity = product_quantity;
    }

    public int getSub_total_price() {
        return sub_total_price;
    }

    public void setSub_total_price(int sub_total_price) {
        this.sub_total_price = sub_total_price;
    }
}
