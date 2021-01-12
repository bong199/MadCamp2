package com.example.weektwotest.ui.main;

import android.graphics.drawable.Drawable;

public class ImageClass {

    private String content;
    private String Date;
    private String image;

    public void setContent(String content) {
        this.content = "Examples";
    }

    public void setDate(String date) {
        Date = "2000.10.12";
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getContent() {
        return content;
    }

    public String getDate() {
        return Date;
    }

    public String getImage() {
        return image;
    }
}
