package com.itecknologi.iteckapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.mackhartley.roundedprogressbar.RoundedProgressBar;

public class AmountFragment1 extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    RoundedProgressBar progressBarToday, progressBarYesterday;
    float progress1 = 0;
    float progress2 = 0;


    private String mParam1;
    private String mParam2;

    private String AmountThisMonth;
    private String AmountLastMonth;


    private TextView txtThisMonth;
    private TextView txtLastMonth;

    private TextView txtLast28DaysSum;

    public AmountFragment1() {


    }

    public AmountFragment1(String amountThisMonth, String amountLastMonth) {
        AmountThisMonth = amountThisMonth;
        AmountLastMonth = amountLastMonth;
    }

    public static AmountFragment1 newInstance(String param1, String param2) {
        AmountFragment1 fragment = new AmountFragment1();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_amount1, container, false);

        txtThisMonth = view.findViewById(R.id.txtToday);
        txtLastMonth = view.findViewById(R.id.txtYesterday);

        progressBarToday = view.findViewById(R.id.barSpeed);
        progressBarYesterday = view.findViewById(R.id.horibarYesterday);


        txtLastMonth.setText(AmountLastMonth);
        txtThisMonth.setText(AmountThisMonth);

        txtLast28DaysSum = view.findViewById(R.id.txtRecent);
        if (Integer.parseInt(AmountLastMonth) > Integer.parseInt(AmountThisMonth)) {
            txtLast28DaysSum.setText("You have Spent "
                    .concat(Integer.parseInt(AmountLastMonth) - Integer.parseInt(AmountThisMonth) +
                            " Rs less than this month"));
        } else if (Integer.parseInt(AmountLastMonth) <= Integer.parseInt(AmountThisMonth)) {
            txtLast28DaysSum.setText("You have Spent "
                    .concat(Integer.parseInt(AmountThisMonth) - Integer.parseInt(AmountLastMonth) +
                            " Rs more than last month"));
        }


        if (Integer.parseInt(AmountThisMonth) > Integer.parseInt(AmountLastMonth)) {
            progress1 = Float.parseFloat(AmountLastMonth) / Float.parseFloat(AmountThisMonth) * 100;
            progress2 = 100 - progress1;
            progress1 = 100;
            progress2 = 100 - progress2;
        } else if (Integer.parseInt(AmountThisMonth) < Integer.parseInt(AmountLastMonth)) {
            progress2 = Float.parseFloat(AmountThisMonth) / Float.parseFloat(AmountLastMonth) * 100;
            progress1 = 100 - progress2;
            progress2 = 100;
            progress1 = 100 - progress1;
        }

        progressBar1(progress1, progressBarToday);
        progressBar1(progress2, progressBarYesterday);


        constraintLayouttt = view.findViewById(R.id.constraintLayouttt);
        frameLayout = view.findViewById(R.id.frameLayout);


        ///here to dao everything

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