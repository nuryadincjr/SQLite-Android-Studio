package com.abuunity.latihanfragmant;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.Toast;

import java.util.ArrayList;

public class MahasiswaFragment extends Fragment {

    private RecyclerView recyclerViewMahasiswa;
    private RecyclerAdapterMahasiswa recyclerAdapterMahasiswa;
    private ArrayList<Mahasiswa> mahasiswaArrayList;

    public MahasiswaFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_mahasiswa, container, false);
        setHasOptionsMenu(true);
        recyclerViewMahasiswa=view.findViewById(R.id.rcv_mahasiswa);
        getMahasiswa();
        recyclerAdapterMahasiswa= new RecyclerAdapterMahasiswa(mahasiswaArrayList);
        RecyclerView.LayoutManager layoutManager = new
                LinearLayoutManager(getContext());
        recyclerViewMahasiswa.setLayoutManager(layoutManager);
        recyclerViewMahasiswa.setAdapter(recyclerAdapterMahasiswa);
        recyclerAdapterMahasiswa.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                openMenuEdit(view,position);
            }
        });
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_option, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    public void openMenuEdit(View view, final int position) {
        PopupMenu popupMenu = new PopupMenu(view.getContext(), view);
        popupMenu.getMenuInflater().inflate(R.menu.menu_edit, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_edit:
                        Toast.makeText(getContext(),"Edit", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.navigation_delete:
                        Toast.makeText(getContext(),"Delete",Toast.LENGTH_SHORT).show();
                        break;
                }
                return true;
            }
        });
        popupMenu.show();
    }

    private void getMahasiswa() {
        mahasiswaArrayList= new ArrayList<Mahasiswa>();
        mahasiswaArrayList.add(new Mahasiswa(1,"170010200","Putu Ciko","SI"));
        mahasiswaArrayList.add(new Mahasiswa(2,"170010212","Made Cetar","TI"));
        mahasiswaArrayList.add(new Mahasiswa(3,"19552011182","Nuryadin Abutani","TI"));
    }
}