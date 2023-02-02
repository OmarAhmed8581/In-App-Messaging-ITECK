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


public class child_amount_month extends Fragment {
    private String env;
    BarChart mBarChart;
    ArrayList<BarEntry> arrayList;
    ArrayList<String> keyArrayList;
    private Float sum;
    TextView txtEnv;
    TextView txtSumMonthly;

    public child_amount_month(ArrayList<BarEntry> arrayList, Float sum, ArrayList<String> keyArrayList, String env) {
        this.arrayList = arrayList;
        this.sum = sum;
        this.keyArrayList = keyArrayList;
        this.env = env;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.child_amount_month, container, false);

        mBarChart = v.findViewById(R.id.barchart_month);
        txtSumMonthly = v.findViewById(R.id.txtSumMonthly);

        txtSumMonthly.setText(sum.toString());

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
        barDataSet.setColor(Color.parseColor("#f9bd3b"));

        mBarChart.getXAxis().setTextColor(R.color.dark_grey);
        barData.setBarWidth(0.4f);
        mBarChart.getXAxis().setSpaceMax(0.1f);


        final ArrayList<String> xVals = new ArrayList<>();
        if (keyArrayList != null) {
            for (int i = 0; i < keyArrayList.size(); i++) {
                String[] date = keyArrayList.get(i).split("-");
                xVals.add(date[2]);
            }

        } else {
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
            xVals.add("12");
            xVals.add("13");
            xVals.add("14");
            xVals.add("15");
            xVals.add("16");
            xVals.add("17");
            xVals.add("18");
            xVals.add("19");
            xVals.add("20");

            xVals.add("21");
            xVals.add("22");
            xVals.add("23");
            xVals.add("24");
            xVals.add("25");
            xVals.add("26");
            xVals.add("27");
            xVals.add("28");
        }

        xAxis.setCenterAxisLabels(false);
        xAxis.setDrawGridLines(false);
        xAxis.setAxisMaximum(xVals.size());
        xAxis.setLabelCount(xVals.size() / 2);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setValueFormatter(new
                IndexAxisValueFormatter(xVals));
        CustomMarkerView2 mv = new CustomMarkerView2(getContext(), R.layout.mileage_marker, keyArrayList, "a");
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


        arrayList.add(new BarEntry(7, 0));
        arrayList.add(new BarEntry(8, 0));
        arrayList.add(new BarEntry(9, 0));
        arrayList.add(new BarEntry(10, 0));
        arrayList.add(new BarEntry(11, 0));
        arrayList.add(new BarEntry(12, 0));
        arrayList.add(new BarEntry(13, 0));


        arrayList.add(new BarEntry(14, 0));
        arrayList.add(new BarEntry(15, 0));
        arrayList.add(new BarEntry(16, 0));
        arrayList.add(new BarEntry(17, 0));
        arrayList.add(new BarEntry(18, 0));
        arrayList.add(new BarEntry(19, 0));
        arrayList.add(new BarEntry(20, 0));


        arrayList.add(new BarEntry(21, 0));
        arrayList.add(new BarEntry(22, 0));
        arrayList.add(new BarEntry(23, 0));
        arrayList.add(new BarEntry(24, 0));
        arrayList.add(new BarEntry(25, 0));
        arrayList.add(new BarEntry(26, 0));


        arrayList.add(new BarEntry(27, 0));

    }
}