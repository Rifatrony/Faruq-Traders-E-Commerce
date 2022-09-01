package com.binaryit.faruqtraders.Response;

import java.util.ArrayList;

public class DetailsOrderResponse {

    public String id, name, email, phone, address, orderid, status, sub_total, totalpayment, delivery, delivery_rate;
    public Object payment_method;
    public ArrayList<Item> items;


    public DetailsOrderResponse(String id, String name, String email, String phone, String address, String orderid, String status, String sub_total, String totalpayment, String delivery, String delivery_rate, Object payment_method, ArrayList<Item> items) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.orderid = orderid;
        this.status = status;
        this.sub_total = sub_total;
        this.totalpayment = totalpayment;
        this.delivery = delivery;
        this.delivery_rate = delivery_rate;
        this.payment_method = payment_method;
        this.items = items;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSub_total() {
        return sub_total;
    }

    public void setSub_total(String sub_total) {
        this.sub_total = sub_total;
    }

    public String getTotalpayment() {
        return totalpayment;
    }

    public void setTotalpayment(String totalpayment) {
        this.totalpayment = totalpayment;
    }

    public String getDelivery() {
        return delivery;
    }

    public void setDelivery(String delivery) {
        this.delivery = delivery;
    }

    public String getDelivery_rate() {
        return delivery_rate;
    }

    public void setDelivery_rate(String delivery_rate) {
        this.delivery_rate = delivery_rate;
    }

    public Object getPayment_method() {
        return payment_method;
    }

    public void setPayment_method(Object payment_method) {
        this.payment_method = payment_method;
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public void setItems(ArrayList<Item> items) {
        this.items = items;
    }

    public class Item{
        public String id;
        public String name;
        public String quantity;
        public String price;
        public int total;
    }

}
