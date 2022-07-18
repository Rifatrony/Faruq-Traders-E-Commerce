package com.example.faruqtraders.Response;

import java.util.ArrayList;

public class FilterResponseModel {

    public static Products products;

    public FilterResponseModel(Products products) {
        this.products = products;
    }

    public FilterResponseModel() {
    }

    public Products getProducts() {
        return products;
    }

    public void setProducts(Products products) {
        this.products = products;
    }

    public static class Datum{
        public String id;
        public String name;
        public String slug;
        public String thumbnail;
        public String discount;
        public String price;
        public double discounted_price;
        public boolean has_attribute;
    }

    public static class Pagination{
        public int total;
        public int count;
        public int per_page;
        public int current_page;
        public int total_pages;
    }

    public static class Products{
        public ArrayList<Datum> data;
        public Pagination pagination;
    }
}
