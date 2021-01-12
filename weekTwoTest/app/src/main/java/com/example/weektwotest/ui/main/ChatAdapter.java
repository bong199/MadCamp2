package com.example.weektwotest.ui.main;

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

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ItemViewHolder>{

    private Context context;

    private ArrayList<ImageClass> mData;

    public  ChatAdapter(Context context, ArrayList<ImageClass> mData){
        this.context = context;
        this.mData = mData;
    }

    @NonNull
    @Override
    public ChatAdapter.ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ChatAdapter.ItemViewHolder Me = new ItemViewHolder(
                LayoutInflater.from(context).inflate(R.layout.chat_item_right, parent, false)
        );
        ChatAdapter.ItemViewHolder friend = new ItemViewHolder(
                LayoutInflater.from(context).inflate(R.layout.comment_left, parent, false)
        );
        if(viewType == 1){
            return Me;
        }else{
            return friend;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ChatAdapter.ItemViewHolder holder, int position) {

        holder.post_content.setText(mData.get(position).getContent());
        holder.post_date.setText(mData.get(position).getDate());
        holder.post_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("edit_button","button click????????");
                Intent intent = new Intent(v.getContext(), CommentActivity.class);
                /*intent.putExtra("Name", phoneNumbers.get(position).getName());
                intent.putExtra("Number", phoneNumbers.get(position).getNumber());
                intent.putExtra("Email", phoneNumbers.get(position).getEmail());*/
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
    @Override
    public int getItemViewType(int position) {
        if (1==1){
            return 1;
        } else {
            return 0;
        }
    }
}
