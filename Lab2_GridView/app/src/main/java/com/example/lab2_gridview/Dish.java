package com.example.lab2_gridview;

public class Dish {
    private String dishName;
    private int thumbnail;
    private boolean hasPromotion;

    public Dish(String dishName, int thumbnail, boolean hasPromotion) {
        this.dishName = dishName;
        this.thumbnail = thumbnail;
        this.hasPromotion = hasPromotion;
    }

    public String getDishName() {
        return dishName;
    }

    public void setDishName(String dishName) {
        this.dishName = dishName;
    }

    public int getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(int thumbnail) {
        this.thumbnail = thumbnail;
    }

    public boolean hasPromotion() {
        return hasPromotion;
    }

    public void setHasPromotion(boolean hasPromotion) {
        this.hasPromotion = hasPromotion;
    }
}
