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

public class StepsDetailAdapter extends RecyclerView.Adapter<StepsDetailAdapter.ViewHolder> {

    private List<String> stepList;
    private ItemClickListener itemClickListener;

    public StepsDetailAdapter(List<String> stepList) {
        this.stepList = stepList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_steps_detail,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        int no = (position+1);
        String steps = stepList.get(position);
        holder.index.setText(String.valueOf(no));
        holder.steps.setText(steps);
        System.out.println(steps);

    }

    @Override
    public int getItemCount() {
        return stepList !=null ? stepList.size():0;
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener=itemClickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView index, steps;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            index = itemView.findViewById(R.id.index_no);
            steps = itemView.findViewById(R.id.detail_steps);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(itemClickListener!=null)
                itemClickListener.onClick(v,getAdapterPosition());
        }
    }
}
