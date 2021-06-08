package com.abuunity.latihanfragmant.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.abuunity.latihanfragmant.Interface.ItemClickListener;
import com.abuunity.latihanfragmant.R;

import java.util.List;

public class ToolsDetailAdapter extends RecyclerView.Adapter<ToolsDetailAdapter.ViewHolder> {

    private List<String> toolList;
    private ItemClickListener itemClickListener;

    public ToolsDetailAdapter(List<String> toolList) {
        this.toolList = toolList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tools_detail,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        int no = (position+1);
        String tools = toolList.get(position);
        holder.index.setText(String.valueOf(no));
        holder.steps.setText(tools);
        System.out.println(tools);

    }

    @Override
    public int getItemCount() {
        return toolList !=null ? toolList.size():0;
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener=itemClickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView index, steps;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            index = itemView.findViewById(R.id.index_no);
            steps = itemView.findViewById(R.id.detail_tools);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(itemClickListener!=null)
                itemClickListener.onClick(v,getAdapterPosition());
        }
    }
}
