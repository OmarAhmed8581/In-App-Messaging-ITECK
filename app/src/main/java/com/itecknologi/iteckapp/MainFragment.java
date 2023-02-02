package com.itecknologi.iteckapp;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.IMarker;
import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import java.util.ArrayList;

public class MainFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    String[] day2;

    public MainFragment() {


    }

    public MainFragment(String[] day2) {
        this.day2 = day2;
    }

    public static MainFragment newInstance(String param1, String param2) {
        MainFragment fragment = new MainFragment();
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

    BarChart mBarChart;
    ArrayList<BarEntry> arrayList;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_main, container, false);

        mBarChart = (BarChart) view.findViewById(R.id.barchart);

        mBarChart.setVisibility(View.INVISIBLE);
        mBarChart.setScaleEnabled(false);
        if (arrayList == null) {
            getData();
        }
        barConfiguration();


        final Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mBarChart.animateY(2000);
                mBarChart.setVisibility(View.VISIBLE);
            }
        }, 500);

        return view;
    }


    private void barConfiguration() {

        mBarChart.setDrawGridBackground(false);
        mBarChart.setDrawBarShadow(false);
        mBarChart.setGridBackgroundColor(Color.WHITE);
        mBarChart.setTouchEnabled(true);
        mBarChart.setPinchZoom(true);
        mBarChart.highlightValue(null);
        mBarChart.setDoubleTapToZoomEnabled(false);
        mBarChart.getAxisLeft().setEnabled(false);
        mBarChart.getAxisRight().setEnabled(false);


        BarDataSet barDataSet = new BarDataSet(arrayList, "Test");
        barDataSet.setDrawValues(false);

        mBarChart.getAxisLeft().setDrawGridLines(false);
        mBarChart.getXAxis().setDrawGridLines(false);
        XAxis xAxis = mBarChart.getXAxis();

        xAxis.setAxisLineColor(getResources().getColor(R.color.transparent));

        mBarChart.setDescription(null);    // Hide the description
        mBarChart.getAxisLeft().setDrawLabels(true);
        mBarChart.getAxisRight().setDrawLabels(true);
        mBarChart.getLegend().setEnabled(false);


        BarData barData = new BarData(barDataSet);
        mBarChart.setData(barData);
        barDataSet.setValueTextColor(Color.parseColor("#ebecf0"));
        barDataSet.setColor(Color.parseColor("#ebecf0"));

        mBarChart.getXAxis().setTextColor(R.color.dark_grey);
        barData.setBarWidth(0.5f);


        final ArrayList<String> xVals = new ArrayList<>();
        xVals.add(day2[0]);
        for (int i = 0; i < day2.length; i++) {
            xVals.add(day2[i]);
        }

//        final ArrayList<String> xVals = new ArrayList<>();
//        xVals.add("M");
//        xVals.add("T");
//        xVals.add("W");
//        xVals.add("T");
//        xVals.add("F");
//        xVals.add("S");
//        xVals.add("S");

        xAxis.setCenterAxisLabels(false);
        xAxis.setDrawGridLines(false);
        xAxis.setAxisMaximum(8);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(xVals));

        IMarker marker = new MarkerView(getContext(), R.layout.mileage_marker);
        mBarChart.setMarker(marker);
    }

    private void getData() {
        arrayList = new ArrayList<BarEntry>();
//        arrayList.add(new BarEntry(0, 0));
        arrayList.add(new BarEntry(1, 0));
        arrayList.add(new BarEntry(2, 0));
        arrayList.add(new BarEntry(3, 0));
        arrayList.add(new BarEntry(4, 0));
        arrayList.add(new BarEntry(5, 0));
        arrayList.add(new BarEntry(6, 0));
        arrayList.add(new BarEntry(7, 0));


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }
}