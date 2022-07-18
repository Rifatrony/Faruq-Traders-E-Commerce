package com.example.faruqtraders.Model;

public class Clothing {
    private int image;
    private String offer;

    public Clothing(int image, String offer) {
        this.image = image;
        this.offer = offer;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getOffer() {
        return offer;
    }

    public void setOffer(String offer) {
        this.offer = offer;
    }
}
