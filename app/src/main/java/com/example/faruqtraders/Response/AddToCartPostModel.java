package com.example.faruqtraders.Response;

public class AddToCartPostModel {


    public int quantity;
    public Options options;

    public AddToCartPostModel(int quantity, Options options) {
        this.quantity = quantity;
        this.options = options;
    }

    public AddToCartPostModel() {
    }

    public static class Options{
        public Object size;
        public String length;

        public Options(Object size, String length) {
            this.size = size;
            this.length = length;
        }


    }
}
