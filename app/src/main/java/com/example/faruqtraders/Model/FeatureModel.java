package com.example.faruqtraders.Model;

public class FeatureModel {

    private String name, slug, thumbnail;

    private double price, discounted_price;

    public FeatureModel(String name, String slug, String thumbnail, double price, double discounted_price) {
        this.name = name;
        this.slug = slug;
        this.thumbnail = thumbnail;
        this.price = price;
        this.discounted_price = discounted_price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getDiscounted_price() {
        return discounted_price;
    }

    public void setDiscounted_price(double discounted_price) {
        this.discounted_price = discounted_price;
    }
}
