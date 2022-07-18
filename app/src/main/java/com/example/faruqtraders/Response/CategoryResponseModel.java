package com.example.faruqtraders.Response;

import java.util.ArrayList;

public class CategoryResponseModel {

    public Categories categories;

    public CategoryResponseModel(Categories categories) {
        this.categories = categories;
    }

    public CategoryResponseModel() {
    }

    public Categories getCategories() {
        return categories;
    }

    public void setCategories(Categories categories) {
        this.categories = categories;
    }

    public class Datum{
        public String name;
        public String slug;
        public String icon;
        public String image;
        public Object description;
        public Object parent;
        public ArrayList<Object> child;
    }

    public class Pagination{
        public int total;
        public int count;
        public int per_page;
        public int current_page;
        public int total_pages;
    }

    public class Categories{
        public ArrayList<Datum> data;
        public Pagination pagination;
    }


}