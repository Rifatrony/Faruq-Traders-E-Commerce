package com.binaryit.faruqtraders.Response;

import java.util.ArrayList;

public class VisitedProductResponse {

    public ArrayList<Product> products;

    public VisitedProductResponse(ArrayList<Product> products) {
        this.products = products;
    }

    public VisitedProductResponse() {
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }

    public class Product{
        public String id;
        public String name;
        public String slug;
        public String thumbnail;
        public String discount;
        public String price;
        public Object discounted_price;
        public boolean has_attribute;
    }

}
