package com.binaryit.faruqtraders.Response;

import java.util.ArrayList;

public class CartResponseModel {


    public ArrayList<Datum> data;


    public CartResponseModel(ArrayList<Datum> data) {
        this.data = data;
    }


    public CartResponseModel() {
    }


    public ArrayList<Datum> getData() {
        return data;
    }

    public void setData(ArrayList<Datum> data) {
        this.data = data;
    }

    public static class Datum{
        public String quantity;
        public String price;
        public double total;
        public Product product;


        public Datum(String quantity, String price, double total, Product product) {
            this.quantity = quantity;
            this.price = price;
            this.total = total;
            this.product = product;
        }

        public Datum() {
        }


    }

    public static class Product{
        public String id;
        public String name;
        public String slug;
        public String thumbnail;
        public String discount;
        public String price;
        public Object discounted_price;
        public boolean has_attribute;


        public Product(String id, String name, String slug, String thumbnail, String discount, String price, Object discounted_price, boolean has_attribute) {
            this.id = id;
            this.name = name;
            this.slug = slug;
            this.thumbnail = thumbnail;
            this.discount = discount;
            this.price = price;
            this.discounted_price = discounted_price;
            this.has_attribute = has_attribute;
        }


        public Product() {
        }
    }

}
