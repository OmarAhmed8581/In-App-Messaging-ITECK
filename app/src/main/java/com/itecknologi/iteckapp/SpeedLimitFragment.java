package com.itecknologi.iteckapp;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class SpeedLimitFragment extends Fragment {

    public SpeedLimitFragment() {
    }

    public static SpeedLimitFragment newInstance(String param1, String param2) {
        SpeedLimitFragment fragment = new SpeedLimitFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    RecyclerView recyclerView;
    private List<SpeedLimitModel> list;
    SpeedLimitAdapter adapter;

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fregment_speed_limit, container, false);
        recyclerView = view.findViewById(R.id.rv_speed_limit);

        list = new ArrayList<>();
        adapter = new SpeedLimitAdapter(list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        listData();
        adapter.notifyDataSetChanged();


        return view;
    }

    private void listData() {

        list.add(new SpeedLimitModel("56 August", "Nahi Clifton nh", "2"));
        list.add(new SpeedLimitModel("24 Sep", "Clifton", "24"));
        list.add(new SpeedLimitModel("24 Sep", "Nahi Clifton nh", "2"));
        list.add(new SpeedLimitModel("24 Sep", "Clifton", "24"));
        list.add(new SpeedLimitModel("24 Sep", "Clifton", "24"));

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }
}