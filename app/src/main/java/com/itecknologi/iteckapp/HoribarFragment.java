package com.itecknologi.iteckapp;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.mackhartley.roundedprogressbar.RoundedProgressBar;

public class HoribarFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    RoundedProgressBar progressBarToday, progressBarYesterday;
    float progress1 = 0;
    float progress2 = 0;

    private String MileageThisMonth;
    private String MileageLastMonth;


    private TextView txtThisMonth;
    private TextView txtLastMonth;

    private String mParam1;
    private String mParam2;
    private TextView txtLast28DaysSum;

    public HoribarFragment() {

    }


    public HoribarFragment(String mileageThisMonth, String mileageLastMonth) {
        MileageThisMonth = mileageThisMonth;
        MileageLastMonth = mileageLastMonth;
    }

    public static HoribarFragment newInstance(String param1, String param2) {
        HoribarFragment fragment = new HoribarFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
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

    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_horibar, container, false);


        txtThisMonth = view.findViewById(R.id.txtToday);
        txtLastMonth = view.findViewById(R.id.txtYesterday);
        progressBarToday = view.findViewById(R.id.barSpeed);
        progressBarYesterday = view.findViewById(R.id.horibarYesterday);

        txtLastMonth.setText(MileageLastMonth);
        txtThisMonth.setText(MileageThisMonth);

        txtLast28DaysSum = view.findViewById(R.id.txtRecent);
        if (Integer.parseInt(MileageLastMonth) > Integer.parseInt(MileageThisMonth)) {
            txtLast28DaysSum.setText("You have driven "
                    .concat(Integer.parseInt(MileageLastMonth) - Integer.parseInt(MileageThisMonth) +
                            " Km less than this month"));
        } else if (Integer.parseInt(MileageLastMonth) <= Integer.parseInt(MileageThisMonth)) {
            txtLast28DaysSum.setText("You have driven "
                    .concat(Integer.parseInt(MileageThisMonth) - Integer.parseInt(MileageLastMonth) +
                            " Km more than last month"));
        }

        if (Integer.parseInt(MileageThisMonth) > Integer.parseInt(MileageLastMonth)) {
            progress1 = Float.parseFloat(MileageLastMonth) / Float.parseFloat(MileageThisMonth) * 100;
            progress2 = 100 - progress1;
            progress1 = 100;
            progress2 = 100 - progress2;
        } else if (Integer.parseInt(MileageThisMonth) < Integer.parseInt(MileageLastMonth)) {
            progress2 = Float.parseFloat(MileageThisMonth) / Float.parseFloat(MileageLastMonth) * 100;
            progress1 = 100 - progress2;
            progress2 = 100;
            progress1 = 100 - progress1;
        }

        progressBar1(progress1, progressBarToday);
        progressBar1(progress2, progressBarYesterday);

        return view;
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

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }
}