package com.example.weektwotest.ui.main;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.BaseAdapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;
import com.example.weektwotest.R;

import java.util.ArrayList;
import java.util.List;

public class GalleryAdapter2 extends RecyclerView.Adapter<GalleryAdapter2.ViewHolder> {
    private Context context;
    private ArrayList<String> imageIDs;
    protected  PhotoListener photoListener;

    public  GalleryAdapter2(Context context, ArrayList<String> imageIDs, PhotoListener photoListener){
        this.context = context;
        this.imageIDs = imageIDs;
        this.photoListener = photoListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        return new ViewHolder(
                LayoutInflater.from(context).inflate(R.layout.each_image2, parent, false)
        );
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position){
//        holder.image.setImageResource(images[position]);
        Glide.with(context).load("http://192.249.18.247:3000/uploads/"+imageIDs.get(position)).into(holder.image);
    }
    @Override
    public int getItemCount(){
        return imageIDs.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;

        public ViewHolder(@NonNull View itemView){
            super(itemView);
            image = itemView.findViewById(R.id.image);
        }
    }

    public interface PhotoListener{
        void onPhotoClick(String path);
    }
}