package com.abuunity.latihanfragmant.fragment;

import android.content.Intent;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.abuunity.latihanfragmant.activity.old_DescriptionActivity;
import com.abuunity.latihanfragmant.R;
import com.abuunity.latihanfragmant.pojo.old_Team;
import com.abuunity.latihanfragmant.adapter.old_RecyclerAdapterTeam;
import com.abuunity.latihanfragmant.Interface.ItemClickListener;

import java.util.ArrayList;


public class old_HomeFragment extends Fragment {

    private RecyclerView recyclerViewTeam;
    private old_RecyclerAdapterTeam oldRecyclerAdapterTeam;
    private ArrayList<old_Team> oldTeamArrayList;
    private String name, desc, logo;
    old_Team teams = new old_Team("", "", "");

    public old_HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.old_fragment_home, container,
                false);
        recyclerViewTeam = view.findViewById(R.id.rcv_team);
        getTeam();
        oldRecyclerAdapterTeam = new old_RecyclerAdapterTeam(getContext(), oldTeamArrayList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerViewTeam.setLayoutManager(layoutManager);
        recyclerViewTeam.setAdapter(oldRecyclerAdapterTeam);

        oldRecyclerAdapterTeam.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                String nama = oldTeamArrayList.get(position).getNama();
                String logo = oldTeamArrayList.get(position).getLogo();
                String desc = oldTeamArrayList.get(position).getDescription();

                teams.setNama(nama);
                teams.setLogo(logo);
                teams.setDescription(desc);

                Intent intent = new Intent(getActivity(), old_DescriptionActivity.class);
                intent.putExtra("TEAMS", teams);
                startActivity(intent);
            }
        });
        return view;
    }

    private void getTeam() {
        if(oldTeamArrayList ==null) {
            Resources resources = getResources();
            String[] teamName = resources.getStringArray(R.array.club_name);
            final TypedArray teamLogo = resources.obtainTypedArray(R.array.club_image);
            String[] teamDesc = resources.getStringArray(R.array.club_detail);
            oldTeamArrayList =new ArrayList<old_Team>();
            for (int i=0; i<teamName.length; i++) {
                oldTeamArrayList.add(new old_Team(teamName[i],String.valueOf(teamLogo.getResourceId(i,-1)),teamDesc[i]));
            }
        }
    }
}