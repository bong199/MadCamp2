package com.example.weektwotest.ui.main;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.weektwotest.R;

import java.util.ArrayList;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ItemViewHolder>{

    private Context context;

    private ArrayList<ImageClass> mData;
    private Activity activity;

    public  PostAdapter(Context context, ArrayList<ImageClass> mData){
        this.context = context;
        this.mData = mData;
        //this.activity = activity;
    }

    @NonNull
    @Override
    public PostAdapter.ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ItemViewHolder(
                LayoutInflater.from(context).inflate(R.layout.post_item, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull PostAdapter.ItemViewHolder holder, int position) {

        Glide.with(context).load("http://192.249.18.247:3000/uploads/"+mData.get(position).getImage()).into(holder.post_image);
        holder.post_content.setText(mData.get(position).getContent());
        holder.post_date.setText(mData.get(position).getDate());
        holder.post_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("edit_button","button click????????");
                Intent intent = new Intent(v.getContext(), CommentActivity.class);
                intent.putExtra("image_path",mData.get(position).getImage());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        ImageView post_image;
        TextView post_content;
        TextView post_date;
        private Button like;
        Button post_comment;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            post_image = itemView.findViewById(R.id.post_image);
            post_content = itemView.findViewById(R.id.post_content);
            post_date = itemView.findViewById(R.id.post_date);
            like = itemView.findViewById(R.id.like);
            post_comment = itemView.findViewById(R.id.post_comment);

        }
    }
}
