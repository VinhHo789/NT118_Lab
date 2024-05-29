package com.example.lab2_gridview;

public enum Thumbnail {
    Thumbnail1("Thumbnail 1", R.drawable.thumbnail1),
    Thumbnail2("Thumbnail 2", R.drawable.thumbnail2),
    Thumbnail3("Thumbnail 3", R.drawable.thumbnail3),
    Thumbnail4("Thumbnail 4", R.drawable.thumbnail4);

    private final String name;
    private final int imageResource;

    Thumbnail(String name, int imageResource) {
        this.name = name;
        this.imageResource = imageResource;
    }

    public String getName() {
        return name;
    }

    public int getImageResource() {
        return imageResource;
    }
}
