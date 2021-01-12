package com.example.weektwotest.ui.main;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weektwotest.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class FreeFragmentHAdapter extends RecyclerView.Adapter<FreeFragmentHAdapter.ViewHolder>{
    private Context context;
    private String poJsonString;
//    private ArrayList<Post> postList;

    public FreeFragmentHAdapter(Context context, String poJsonString){
        System.out.println("ffadpt constru");
        this.context = context;
        this.poJsonString = poJsonString;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // ViewHolder의 생성자에 layout을 inflate한거를 넘기면서 생성
        ViewHolder viewHolder = new ViewHolder(LayoutInflater.from(context).inflate(R.layout.post_recyclerview_layout, parent, false));
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        try {
            JSONObject jsonObject = new JSONObject(poJsonString);
            JSONArray poJsonArr = jsonObject.getJSONArray("posts");
            holder.postAuthor.setText(poJsonArr.getJSONObject(position).getString("Name"));
            System.out.println("onbindvidewhodsllerf");
            System.out.println(poJsonArr.getJSONObject(position).getString("Name"));
            holder.postTitle.setText(poJsonArr.getJSONObject(position).getString("Title"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return 10;
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView postAuthor;
        TextView postTitle;

        public ViewHolder(@NonNull View itemView){
            super(itemView);
            // itemView의 각 요소들을 설정
            postAuthor = itemView.findViewById(R.id.postAuthor);
            postTitle = itemView.findViewById(R.id.postTitle);
        }
    }
}