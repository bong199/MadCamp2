package com.example.weektwotest.ui.main;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
public class ImageRoader {
    private final String serverUrl = "http://192.249.18.227:3000/";
    public ImageRoader() {
        new ThreadPolicy();//왜 있어야하는거지??? --> 모르겠음 ...
    }
    public Bitmap getBitmapImg(String imgStr) {
        Bitmap bitmapImg = null;
        System.out.println(imgStr);
        try {
            URL url = new URL(serverUrl + "uploads/" + imgStr);
//                    URLEncoder.encode(imgStr, "utf-8"));
            // Character is converted to 'UTF-8' to prevent broken
            HttpURLConnection conn = (HttpURLConnection) url
                    .openConnection();
            conn.setDoInput(true);
            conn.connect();
            InputStream is = conn.getInputStream();
            bitmapImg = BitmapFactory.decodeStream(is);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return bitmapImg;
    }
}