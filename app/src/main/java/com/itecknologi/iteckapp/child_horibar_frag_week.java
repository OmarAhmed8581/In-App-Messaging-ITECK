package com.itecknologi.iteckapp;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;


import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;


public class child_horibar_frag_week extends Fragment {
    BarChart mBarChart;
    ArrayList<BarEntry> arrayList;
    ArrayList<String> dateArrayList;
    String[] day2;
    Float sum;
    TextView txtSumWeek;

    public child_horibar_frag_week(ArrayList<BarEntry> arrayList, String[] day2, Float sum, ArrayList<String> dateArrayList) {
        this.arrayList = arrayList;
        this.day2 = day2;
        this.sum = sum;
        this.dateArrayList = dateArrayList;
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View v = inflater.inflate(R.layout.child_horibar_week, container, false);

        txtSumWeek = v.findViewById(R.id.txtSumWeek);
        mBarChart = v.findViewById(R.id.barchart_week);
        mBarChart.setVisibility(View.INVISIBLE);
        mBarChart.setScaleEnabled(false);
        txtSumWeek.setText(sum.toString());

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


    @RequiresApi(api = Build.VERSION_CODES.O)
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

        if (arrayList == null) {
            getData();
        }


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
        barDataSet.setColor(Color.parseColor("#a1ca35"));

        mBarChart.getXAxis().setTextColor(R.color.dark_grey);
        barData.setBarWidth(0.6f);
        mBarChart.getXAxis().setSpaceMax(0.1f);

//        final ArrayList<String> xVals = new ArrayList<>();
//        xVals.add("M");
//        xVals.add("T");
//        xVals.add("W");
//        xVals.add("T");
//        xVals.add("F");
//        xVals.add("S");
//        xVals.add("S");


        final ArrayList<String> xVals = new ArrayList<>();
        for (int i = 0; i < day2.length; i++) {
            xVals.add(day2[i]);
        }


        xAxis.setCenterAxisLabels(false);
        xAxis.setDrawGridLines(false);
        xAxis.setAxisMaximum(xVals.size());
        xAxis.setLabelCount(xVals.size());
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(xVals));

        CustomMarkerViewWeek mv = new CustomMarkerViewWeek(getContext(), R.layout.mileage_marker, dateArrayList, "m");
        mBarChart.setMarkerView(mv);
    }


    private void getData() {
        arrayList = new ArrayList<BarEntry>();
        arrayList.add(new BarEntry(0, 0));
        arrayList.add(new BarEntry(1, 0));
        arrayList.add(new BarEntry(2, 0));
        arrayList.add(new BarEntry(3, 0));
        arrayList.add(new BarEntry(4, 0));
        arrayList.add(new BarEntry(5, 0));
        arrayList.add(new BarEntry(6, 0));

    }

}