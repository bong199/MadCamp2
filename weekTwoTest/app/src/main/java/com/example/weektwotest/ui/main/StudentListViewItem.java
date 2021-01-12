package com.example.weektwotest.ui.main;

import android.graphics.drawable.Drawable;

public class StudentListViewItem {
    private Drawable iconDrawable ;
    private String textStr ;

    public void setIcon(Drawable icon) {
        iconDrawable = icon ;
    }
    public void setText(String text) {
        textStr = text ;
    }

    public Drawable getIcon() {
        return this.iconDrawable ;
    }
    public String getText() {
        return this.textStr ;
    }
}