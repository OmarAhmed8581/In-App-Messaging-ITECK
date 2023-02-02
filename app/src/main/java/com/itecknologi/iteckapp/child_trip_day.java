package com.itecknologi.iteckapp;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import java.util.ArrayList;


public class child_trip_day extends Fragment {
    BarChart mBarChart;
    ArrayList<BarEntry> arrayList;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.child_trip_day, container, false);

        mBarChart = v.findViewById(R.id.barchart);
        mBarChart.setVisibility(View.INVISIBLE);
        mBarChart.setScaleEnabled(false);

        barConfiguration();

        final Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mBarChart.animateY(1200);
                mBarChart.setVisibility(View.VISIBLE);
            }
        }, 500);

        return v;
    }

    private void barConfiguration() {

        mBarChart.setDrawGridBackground(false);
        mBarChart.setDrawBarShadow(false);
        mBarChart.setGridBackgroundColor(Color.WHITE);
        mBarChart.setTouchEnabled(true);
        mBarChart.setPinchZoom(true);
        mBarChart.setDoubleTapToZoomEnabled(true);
        mBarChart.highlightValue(null);
        mBarChart.setDoubleTapToZoomEnabled(false);
        mBarChart.getAxisLeft().setEnabled(false);
        mBarChart.getAxisRight().setEnabled(false);

        getData();
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
        barDataSet.setColor(Color.parseColor("#dc3c05"));

        mBarChart.getXAxis().setTextColor(R.color.dark_grey);
        barData.setBarWidth(0.6f);
        mBarChart.getXAxis().setSpaceMax(0.1f);

        final ArrayList<String> xVals = new ArrayList<>();
        xVals.add("12am");
        xVals.add("1");
        xVals.add("2");
        xVals.add("3");
        xVals.add("4");
        xVals.add("5");
        xVals.add("6");
        xVals.add("7");
        xVals.add("8");
        xVals.add("9");
        xVals.add("10");
        xVals.add("11");
        xVals.add("12pm");
        xVals.add("1");
        xVals.add("2");
        xVals.add("3");
        xVals.add("4");
        xVals.add("5");
        xVals.add("6");
        xVals.add("7");
        xVals.add("8");
        xVals.add("9");
        xVals.add("10");
        xVals.add("11");


        xAxis.setCenterAxisLabels(false);
        xAxis.setDrawGridLines(false);
        xAxis.setAxisMaximum(xVals.size());
//        xAxis.setLabelCount(6);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(xVals));

        CustomMarkerView mv = new CustomMarkerView(getContext(), R.layout.mileage_marker, "t");
        mBarChart.setMarkerView(mv);
    }

    private void getData() {
        arrayList = new ArrayList<BarEntry>();
        arrayList.add(new BarEntry(0, 110));
        arrayList.add(new BarEntry(1, 420));
        arrayList.add(new BarEntry(2, 310));
        arrayList.add(new BarEntry(3, 40));
        arrayList.add(new BarEntry(4, 590));
        arrayList.add(new BarEntry(5, 62));
        arrayList.add(new BarEntry(6, 700));
        arrayList.add(new BarEntry(7, 700));
        arrayList.add(new BarEntry(8, 70));
        arrayList.add(new BarEntry(9, 400));
        arrayList.add(new BarEntry(10, 700));
        arrayList.add(new BarEntry(11, 700));
        arrayList.add(new BarEntry(12, 100));
        arrayList.add(new BarEntry(13, 400));
        arrayList.add(new BarEntry(14, 300));
        arrayList.add(new BarEntry(15, 40));
        arrayList.add(new BarEntry(16, 500));
        arrayList.add(new BarEntry(17, 60));
        arrayList.add(new BarEntry(18, 700));
        arrayList.add(new BarEntry(19, 700));
        arrayList.add(new BarEntry(20, 70));
        arrayList.add(new BarEntry(21, 400));
        arrayList.add(new BarEntry(22, 700));
        arrayList.add(new BarEntry(23, 700));
//        arrayList.add(new BarEntry(24, 700));

    }

}