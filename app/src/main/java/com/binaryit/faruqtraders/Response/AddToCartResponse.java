package com.binaryit.faruqtraders.Response;

import java.util.List;

public class AddToCartResponse {

    public static List<Data> data;

    public AddToCartResponse(List<Data> data) {
        this.data = data;
    }

    public List<Data> getData() {
        return data;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }

    public class Data{
        public int quantity;
        public int price;
        public int total;
        public Product product;
    }

    public class Product{
        public String id;
        public String name;
        public String slug;
        public String thumbnail;
        public String discount;
        public String price;
        public int discounted_price;
        public boolean has_attribute;
    }

}
