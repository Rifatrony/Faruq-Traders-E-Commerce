package com.binaryit.faruqtraders.Response;

import java.util.ArrayList;

public class OrderDetailsResponse {

    public ArrayList<Datum> data;
    public Pagination pagination;

    public OrderDetailsResponse() {
    }

    public OrderDetailsResponse(ArrayList<Datum> data, Pagination pagination) {
        this.data = data;
        this.pagination = pagination;
    }

    public ArrayList<Datum> getData() {
        return data;
    }

    public void setData(ArrayList<Datum> data) {
        this.data = data;
    }

    public Pagination getPagination() {
        return pagination;
    }

    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }

    public class Datum{
        public String id;
        public String orderid;
        public String status;
        public double amount;
        public String payment;
        public int quantity;
    }

    public class Pagination{
        public int total;
        public int count;
        public int per_page;
        public int current_page;
        public int total_pages;
    }

}
