package com.example.faruqtraders.Response;

import java.util.ArrayList;

public class BannerResponse {

    public ArrayList<Datum> data;

    public BannerResponse(ArrayList<Datum> data) {
        this.data = data;
    }

    public ArrayList<Datum> getData() {
        return data;
    }

    public void setData(ArrayList<Datum> data) {
        this.data = data;
    }

    public class Datum{
        public String title;
        public String url;
        public String image;
    }

}
