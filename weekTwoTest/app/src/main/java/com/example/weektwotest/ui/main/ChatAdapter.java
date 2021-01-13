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

    private ArrayList<Chatclass> mData;
    private String user_id;

    public  ChatAdapter(Context context, ArrayList<Chatclass> mData,String user_id){
        this.context = context;
        this.mData = mData;
        this.user_id = user_id;
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

        holder.commenting.setText(mData.get(position).getComment());
        if(!(mData.get(position).getNickname().equals(user_id))){
            holder.nickname.setText(mData.get(position).getNickname());
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView commenting;
        TextView nickname;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);

            nickname = itemView.findViewById(R.id.nickname);
            commenting = itemView.findViewById(R.id.show_message);

        }
    }
    @Override
    public int getItemViewType(int position) {
        if (mData.get(position).getNickname().equals(user_id)){
            return 1;
        } else {
            return 0;
        }
    }
}
