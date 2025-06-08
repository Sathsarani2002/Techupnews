package com.example.techupnews;

public class NewsItem {
    private int imageResId;
    private String description;

    public NewsItem(int imageResId, String description) {
        this.imageResId = imageResId;
        this.description = description;
    }

    public int getImageResId() {
        return imageResId;
    }

    public String getDescription() {
        return description;
    }
}
