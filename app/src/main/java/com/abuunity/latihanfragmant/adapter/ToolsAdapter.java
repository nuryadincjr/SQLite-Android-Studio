package com.abuunity.latihanfragmant.adapter;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.abuunity.latihanfragmant.Interface.ItemClickListener;
import com.abuunity.latihanfragmant.R;
import com.abuunity.latihanfragmant.pojo.Tools;

import java.util.List;

public class ToolsAdapter extends RecyclerView.Adapter<ToolsAdapter.ViewHolder>{

    private List<Tools> toolsList;
    private ItemClickListener itemClickListener;


    public ToolsAdapter(List<Tools> toolsList) {
        this.toolsList = toolsList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_tools,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public int getItemCount() {
        return toolsList !=null ? toolsList.size():0;
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Tools tools= toolsList.get(position);
        holder.index.setText(String.valueOf(position+1));
        holder.inputTools.setHint("Add your Tools");
        holder.inputTools.setText(tools.getToolsList());

        holder.inputTools.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(v.hasFocus()){
                    holder.inputCounter.setVisibility(View.VISIBLE);
                    holder.inputTools.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                            if(s.length() != 0)
                                holder.inputCounter.setText(s.length()+"/64");
                        }


                        @Override
                        public void afterTextChanged(Editable s) {

                        }
                    });
                } else {
                    holder.inputCounter.setVisibility(View.GONE);
                }
            }
        });
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements
            View.OnClickListener {

        public TextView index;
        public ImageView imageMore;
        public EditText inputTools;
        public TextView inputCounter;

        public ViewHolder(View itemView) {
            super(itemView);
            index = itemView.findViewById(R.id.index_no);
            imageMore = itemView.findViewById(R.id.image_more);
            inputTools = itemView.findViewById(R.id.input_tools);
            inputCounter = itemView.findViewById(R.id.input_counter);

            imageMore.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(itemClickListener != null){
                itemClickListener.onClick(v,getAdapterPosition());
            }
        }
    }
}