package com.abuunity.latihanfragmant.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.abuunity.latihanfragmant.pojo.old_Mahasiswa;
import com.abuunity.latihanfragmant.R;
import com.abuunity.latihanfragmant.Interface.ItemClickListener;

import java.util.ArrayList;

public class old_RecyclerAdapterMahasiswa extends RecyclerView.Adapter<old_RecyclerAdapterMahasiswa.ViewHolder> {

    private ArrayList<old_Mahasiswa> oldMahasiswaArrayList;
    private ItemClickListener itemClickListener;

    public old_RecyclerAdapterMahasiswa(ArrayList<old_Mahasiswa> oldMahasiswaArrayList) {
        this.oldMahasiswaArrayList = oldMahasiswaArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.old_list_mahasiswa,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        int no = (position+1);
        holder.textNo.setText(String.valueOf(no));
        holder.textNim.setText(oldMahasiswaArrayList.get(position).getNim());
        holder.textNama.setText(oldMahasiswaArrayList.get(position).getNama());
        holder.textProdi.setText(oldMahasiswaArrayList.get(position).getProdi());

    }

    @Override
    public int getItemCount() {
        return oldMahasiswaArrayList !=null ? oldMahasiswaArrayList.size():0;
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener=itemClickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView textNo, textNim, textNama, textProdi;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textNo = itemView.findViewById(R.id.text_no);
            textNim = itemView.findViewById(R.id.text_nim);
            textNama = itemView.findViewById(R.id.text_nama);

            textProdi = itemView.findViewById(R.id.text_prodi);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(itemClickListener!=null)
                itemClickListener.onClick(v,getAdapterPosition());
        }
    }
}
