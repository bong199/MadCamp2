package com.example.weektwotest.ui.main;

import android.graphics.drawable.Drawable;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class ImageClass {

    private String content;
    private String Date;
    private String image;

    java.util.Date currentTime = Calendar.getInstance().getTime();
    String date_text = new SimpleDateFormat("yyyy년 MM월 dd일 EE요일", Locale.getDefault()).format(currentTime);

    public void setContent(String content) {
        this.content = content;
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
        return date_text;
    }

    public String getImage() {
        return image;
    }
}
