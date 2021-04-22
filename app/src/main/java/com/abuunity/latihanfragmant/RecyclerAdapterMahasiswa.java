package com.abuunity.latihanfragmant;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerAdapterMahasiswa extends RecyclerView.Adapter<RecyclerAdapterMahasiswa.ViewHolder> {

    private ArrayList<Mahasiswa> mahasiswaArrayList;
    private ItemClickListener itemClickListener;

    public RecyclerAdapterMahasiswa(ArrayList<Mahasiswa> mahasiswaArrayList) {
        this.mahasiswaArrayList = mahasiswaArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_mahasiswa,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        int no = (position+1);
        holder.textNo.setText(String.valueOf(no));
        holder.textNpm.setText(mahasiswaArrayList.get(position).getNim());
        holder.textNama.setText(mahasiswaArrayList.get(position).getNama());
        holder.textProdi.setText(mahasiswaArrayList.get(position).getProdi());
    }

    @Override
    public int getItemCount() {
        return mahasiswaArrayList !=null ? mahasiswaArrayList.size():0;
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener=itemClickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView textNo, textNpm, textNama, textProdi;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textNo = itemView.findViewById(R.id.text_no);
            textNpm = itemView.findViewById(R.id.text_nim);
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
