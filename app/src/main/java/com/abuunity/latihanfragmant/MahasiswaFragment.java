package com.abuunity.latihanfragmant;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.Toast;

import java.util.ArrayList;

public class MahasiswaFragment extends Fragment {

    private RecyclerView recyclerViewMahasiswa;
    private RecyclerAdapterMahasiswa recyclerAdapterMahasiswa;
    private ArrayList<Mahasiswa> mahasiswaArrayList;
    private DatabaseHandler databaseHandler;
    private SwipeRefreshLayout refreshLayout;

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

        refreshLayout = view.findViewById(R.id.swipe_refresh);
        refreshLayout.setColorSchemeResources(
                android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light
                );

        getMahasiswa();
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();
                refreshLayout.setRefreshing(false);
            }
        });
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_option, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.navigation_add:
                Toast.makeText(getContext(), "Edit", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), AddMahasiswaActivity.class);
                startActivity(intent);
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void openMenuEdit(View view, final int position) {
        PopupMenu popupMenu = new PopupMenu(view.getContext(), view);
        popupMenu.getMenuInflater().inflate(R.menu.menu_edit, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case R.id.navigation_edit:
                    Intent intent = new Intent(getActivity(), EditMahasiswaActivity.class);
                    intent.putExtra("ID",mahasiswaArrayList.get(position).getId());
                    intent.putExtra("NPM",mahasiswaArrayList.get(position).getNpm());
                    intent.putExtra("NAMA",mahasiswaArrayList.get(position).getNama());
                    intent.putExtra("PRODI",mahasiswaArrayList.get(position).getProdi());

                    // menambahkan intent untuk FAKUTAS
                    intent.putExtra("FAKULTAS",mahasiswaArrayList.get(position).getFakultas());
                    // akhir modifikasi

                    startActivity(intent);
                    break;
                case R.id.navigation_delete:
                    dialogShow(position);
                    break;
            }
            return true;
        });
        popupMenu.show();
    }


    @Override
    public void onResume() {
        super.onResume();
        refresh();
    }

    private void refresh() {
        refreshLayout.setRefreshing(true);
        getMahasiswa();
        try {
            Thread.sleep(100);

        } catch (InterruptedException e) {
            System.out.println("got interrupted");
        }
        refreshLayout.setRefreshing(false);
    }

    private void dialogShow(final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Yakin ingin menghapus?");
        builder.setCancelable(true);
        builder.setPositiveButton("Ya",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        databaseHandler.delete(String.valueOf(mahasiswaArrayList.get(position).getId()));
                        refresh();
                        dialog.cancel();
                    }
                });
        builder.setNegativeButton("Tidak",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void getMahasiswa() {
        mahasiswaArrayList= new ArrayList<Mahasiswa>();
        databaseHandler = new DatabaseHandler(getActivity());
        mahasiswaArrayList = (ArrayList<Mahasiswa>) databaseHandler.findAll();
        recyclerAdapterMahasiswa = new RecyclerAdapterMahasiswa(mahasiswaArrayList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerViewMahasiswa.setLayoutManager(layoutManager);
        recyclerViewMahasiswa.setAdapter(recyclerAdapterMahasiswa);
        
        recyclerAdapterMahasiswa.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                openMenuEdit(view,position);
            }
        });
    }
}