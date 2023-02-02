package com.itecknologi.iteckapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.mackhartley.roundedprogressbar.RoundedProgressBar;

public class MainFragment1 extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    RoundedProgressBar progressBarToday, progressBarYesterday;
    float progress1 = 0;
    float progress2 = 0;
    String FuelThisMonth;
    String FuelLastMonth;

    private String mParam1;
    private String mParam2;

    public MainFragment1() {
    }

    public MainFragment1(String fuelThisMonth, String fuelLastMonth) {
        FuelThisMonth = fuelThisMonth;
        FuelLastMonth = fuelLastMonth;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    ConstraintLayout constraintLayouttt;
    FrameLayout frameLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_main1, container, false);

        progressBarToday = view.findViewById(R.id.barSpeed);
        progressBarYesterday = view.findViewById(R.id.horibarYesterday);

        if (Integer.parseInt(FuelThisMonth) > Integer.parseInt(FuelLastMonth)) {
            progress1 = Float.parseFloat(FuelLastMonth) / Float.parseFloat(FuelThisMonth) * 100;
            progress2 = 100 - progress1;
            progress1 = 100;
            progress2 = 100 - progress2;
        } else if (Integer.parseInt(FuelThisMonth) < Integer.parseInt(FuelLastMonth)) {
            progress2 = Float.parseFloat(FuelThisMonth) / Float.parseFloat(FuelLastMonth) * 100;
            progress1 = 100 - progress2;
            progress2 = 100;
            progress1 = 100 - progress1;
        }

        progressBar1(progress1, progressBarToday);
        progressBar1(progress1, progressBarToday);


        constraintLayouttt = view.findViewById(R.id.constraintLayouttt);
        frameLayout = view.findViewById(R.id.frameLayout);


        //chart2

        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }

    private void progressBar1(float progress, RoundedProgressBar progressBar) {
        progressBar.setProgressPercentage(progress, true);

        progressBar.showProgressText(false);
        progressBar.setAnimationLength(3500);
        Animation animation = new Animation() {
            @Override
            public void start() {
                super.start();
            }
        };
        progressBar.setAnimation(animation);
        progressBar.animate();
    }
}