// News.java
package com.example.techupnews;

public class News {
    private int id;
    private String category;
    private String description;
    private int imageResId;

    public News(int id, String category, String description, int imageResId) {
        this.id = id;
        this.category = category;
        this.description = description;
        this.imageResId = imageResId;
    }

    public int getId() { return id; }
    public String getCategory() { return category; }
    public String getDescription() { return description; }
    public int getImageResId() { return imageResId; }
}
