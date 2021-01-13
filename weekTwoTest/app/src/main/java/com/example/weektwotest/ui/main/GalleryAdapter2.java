package com.example.weektwotest.ui.main;

import android.content.Context;
import android.content.Intent;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;
import com.example.weektwotest.R;

import java.util.ArrayList;
import java.util.List;

public class GalleryAdapter2 extends RecyclerView.Adapter<GalleryAdapter2.ItemViewHolder> {
    private Context context;
    private ArrayList<String> imageIDs;
    protected  PhotoListener photoListener;

    private ArrayList<ImageClass> mData;

    public  GalleryAdapter2(Context context, ArrayList<ImageClass> mData, PhotoListener photoListener){
        this.context = context;
        //this.imageIDs = imageIDs;
        this.mData = mData;
        this.photoListener = photoListener;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        return new ItemViewHolder(
                LayoutInflater.from(context).inflate(R.layout.each_image2, parent, false)
        );
    }
    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position){
//        holder.image.setImageResource(images[position]);
        Glide.with(context).load("http://192.249.18.247:3000/uploads/"+mData.get(position).getImage()).into(holder.image);
        holder.content.setText(mData.get(position).getContent());
        holder.date.setText(mData.get(position).getDate());

        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        System.out.println("평생 학습을 해야하는건 너무나도 신나는 일입니다아러ㅣㄴ아허ㅣㄹ하ㅓ리아허ㅣㄹ아허링하ㅓ");
        holder.commenting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("평생 학습을 해야하는건 너무나도 신나는 일입니다아러ㅣㄴ아허ㅣㄹ하ㅓ리아허ㅣㄹ아허링하ㅓ");
                Log.d("edit_button","button click????????");
                Intent intent = new Intent(v.getContext(), CommentActivity.class);
                intent.putExtra("image_path",mData.get(position).getImage());
                context.startActivity(intent);
            }
        });
    }
    @Override
    public int getItemCount(){
        return mData.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView content;
        TextView date;
        private Button edit;
        private ImageButton commenting;
        private Button share;

        public ItemViewHolder(View inflate) {
            super(inflate);
            image = inflate.findViewById(R.id.image);
            content = inflate.findViewById(R.id.textView);
            date = inflate.findViewById(R.id.date);
            edit = inflate.findViewById(R.id.button2);
            commenting = inflate.findViewById(R.id.button3);
            //share = inflate.findViewById(R.id.button4);


        }
    }

    public interface PhotoListener{
        void onPhotoClick(String path);
    }
}