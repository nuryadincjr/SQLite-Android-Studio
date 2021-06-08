package com.abuunity.latihanfragmant.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.abuunity.latihanfragmant.Interface.ItemClickListener;
import com.abuunity.latihanfragmant.R;
import com.abuunity.latihanfragmant.pojo.Hashtags;

import java.util.ArrayList;
import java.util.List;

public class HashtagAdapter extends RecyclerView.Adapter<HashtagAdapter.ViewHolder>{

    private List<Hashtags> hashtagsList;
    private ItemClickListener itemClickListener;

    public HashtagAdapter(ArrayList<Hashtags> hashtagsList) {
        this.hashtagsList = hashtagsList;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tags,parent,false);
        return new HashtagAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tag.setText("#"+hashtagsList.get(position).getTag());
        holder.noOfPost.setText(hashtagsList.get(position).getCounter() + " posts");

    }

    @Override
    public int getItemCount() {
        return hashtagsList !=null ? hashtagsList.size():0;
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView tag;
        public TextView noOfPost;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tag = itemView.findViewById(R.id.hash_tag);
            noOfPost = itemView.findViewById(R.id.no_of_posts);

            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            if (itemClickListener != null)
                itemClickListener.onClick(v, getAdapterPosition());
        }
    }
}
