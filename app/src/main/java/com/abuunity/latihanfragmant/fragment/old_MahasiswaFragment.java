package com.abuunity.latihanfragmant.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.abuunity.latihanfragmant.api.old_MahasiswaRepository;
import com.abuunity.latihanfragmant.pojo.old_Mahasiswa;
import com.abuunity.latihanfragmant.R;
import com.abuunity.latihanfragmant.activity.old_AddMahasiswaActivity;
import com.abuunity.latihanfragmant.activity.old_EditMahasiswaActivity;
import com.abuunity.latihanfragmant.adapter.old_RecyclerAdapterMahasiswa;
import com.abuunity.latihanfragmant.Interface.ItemClickListener;
import com.abuunity.latihanfragmant.ViewModel.old_MainViewModel;

import java.util.ArrayList;

public class old_MahasiswaFragment extends Fragment {

    private RecyclerView recyclerViewMahasiswa;
    private old_RecyclerAdapterMahasiswa oldRecyclerAdapterMahasiswa;
    private ArrayList<old_Mahasiswa> oldMahasiswaArrayList = new ArrayList<>();
    private SwipeRefreshLayout refreshLayout;

    private old_MainViewModel oldMainViewModel;

    public old_MahasiswaFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.old_fragment_mahasiswa, container, false);
        setHasOptionsMenu(true);
        recyclerViewMahasiswa=view.findViewById(R.id.rcv_mahasiswa);

        refreshLayout = view.findViewById(R.id.swipe_refresh);
        refreshLayout.setColorSchemeResources(
                android.R.color.holo_orange_dark,
                android.R.color.holo_green_light,
                android.R.color.holo_blue_bright,
                android.R.color.holo_red_light
                );

        getMahasiswa();

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshLayout.setRefreshing(false);
            }
        });
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.old_menu_option, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.navigation_add:
                Toast.makeText(getContext(), "Edit", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), old_AddMahasiswaActivity.class);
                startActivity(intent);
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void openMenuEdit(View view, final int position) {
        PopupMenu popupMenu = new PopupMenu(view.getContext(), view);
        popupMenu.getMenuInflater().inflate(R.menu.old_menu_edit, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case R.id.navigation_edit:
                    Intent intent = new Intent(getActivity(), old_EditMahasiswaActivity.class);
                    intent.putExtra("ID", oldMahasiswaArrayList.get(position).getId());
                    intent.putExtra("NIM", oldMahasiswaArrayList.get(position).getNim());
                    intent.putExtra("NAMA", oldMahasiswaArrayList.get(position).getNama());
                    intent.putExtra("PRODI", oldMahasiswaArrayList.get(position).getProdi());

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
        getMahasiswa();
    }


    private void dialogShow(final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Yakin ingin menghapus?");
        builder.setCancelable(true);
        builder.setPositiveButton("Ya",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        new old_MahasiswaRepository().deleteMahasiswa(oldMahasiswaArrayList.get(position).getId());
                        getMahasiswa();
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
        refreshLayout.setRefreshing(true);
        oldMainViewModel = new ViewModelProvider(this).get(old_MainViewModel.class);
        oldMainViewModel.getMahasiswa().observe(getViewLifecycleOwner(), new Observer<ArrayList<old_Mahasiswa>>() {
            @Override
            public void onChanged(ArrayList<old_Mahasiswa> oldMahasiswas) {
                refreshLayout.setRefreshing(false);
                oldMahasiswaArrayList.clear();
                oldMahasiswaArrayList.addAll(oldMahasiswas);
                loadsMahasiswa();
            }
        });
    }

    private void  loadsMahasiswa()
    {
        oldRecyclerAdapterMahasiswa = new old_RecyclerAdapterMahasiswa(oldMahasiswaArrayList);
        recyclerViewMahasiswa.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewMahasiswa.setAdapter(oldRecyclerAdapterMahasiswa);
        recyclerViewMahasiswa.setItemAnimator(new DefaultItemAnimator());

        oldRecyclerAdapterMahasiswa.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                openMenuEdit(view,position);
            }
        });
    }

}