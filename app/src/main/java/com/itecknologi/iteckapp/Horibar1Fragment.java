package com.itecknologi.iteckapp;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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


public class Horibar1Fragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    BarChart mBarChart;


    ArrayList<BarEntry> arrayList;
    Integer averagemileage;
    Integer sum;
    String[] day2;


    TextView textview;
    TextView txtMileageSum;

    public Horibar1Fragment(ArrayList<BarEntry> arrayList, Integer averagemileage, Integer sum, String[] day2) {
        this.arrayList = arrayList;
        this.averagemileage = averagemileage;
        this.sum = sum;
        this.day2 = day2;
    }

    public Horibar1Fragment() {

    }

    public static Horibar1Fragment newInstance(String param1, String param2) {
        Horibar1Fragment fragment = new Horibar1Fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_horibar1, container, false);

        mBarChart = (BarChart) view.findViewById(R.id.barchart);
        mBarChart.highlightValues(null);
        mBarChart.setVisibility(View.INVISIBLE);
        mBarChart.setScaleEnabled(false);
        if (arrayList == null) {
            getData();
        }
        barConfiguration();

        //////////

        textview = view.findViewById(R.id.valuee);
        txtMileageSum = view.findViewById(R.id.txtRecent);
        txtMileageSum.setText("You have driven "
                .concat(sum.toString())
                .concat(" Km in past 7 Days"));

        textview.setText(averagemileage.toString());

        //////////


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

//        getData();
        BarDataSet barDataSet = new BarDataSet(arrayList, "Test");
        barDataSet.setDrawValues(false);


        mBarChart.getAxisLeft().setDrawGridLines(false);
        mBarChart.getXAxis().setDrawGridLines(false);
        mBarChart.getXAxis().setSpaceMax(6f);
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
        if (day2 != null) {
            xVals.add(day2[0]);
            for (int i = 0; i < day2.length; i++) {
                xVals.add(day2[i]);
            }
        } else {
            xVals.add("M");
            xVals.add("T");
            xVals.add("W");
            xVals.add("T");
            xVals.add("F");
            xVals.add("S");
            xVals.add("S");
        }


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