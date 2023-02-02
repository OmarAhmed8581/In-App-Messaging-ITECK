package com.itecknologi.iteckapp;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;


public class WeekDriverBehavior extends Fragment {
    LineChart lineChartDownFill;
    String[] yAxisVals;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_month_driver_behavior, container, false);
        lineChartDownFill = v.findViewById(R.id.lineChart);

        final Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
//                mBarChart.animateY(1200);
//                mBarChart.setVisibility(View.VISIBLE);
            }
        }, 500);

        initLineChartDownFill(v);
        return v;
    }

    private void initLineChartDownFill(View v) {

        lineChartDownFill = v.findViewById(R.id.lineChart);
        lineChartDownFill.setTouchEnabled(false);
        lineChartDownFill.setScaleEnabled(true);
        lineChartDownFill.setPinchZoom(false);

        lineChartDownFill.getXAxis().setSpaceMax(1f);
        XAxis xAxis = lineChartDownFill.getXAxis();
        YAxis yAxis = lineChartDownFill.getAxisLeft();

        xAxis.setCenterAxisLabels(false);
        xAxis.setDrawGridLines(false);

        final ArrayList<String> xVals = new ArrayList<>();

        xVals.add("M");
        xVals.add("T");
        xVals.add("W");
        xVals.add("T");
        xVals.add("F");
        xVals.add("S");
        xVals.add("S");
        xAxis.setAxisMaximum(xVals.size());
        xAxis.setLabelCount(xVals.size());

        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

        xAxis.setValueFormatter(new IndexAxisValueFormatter(xVals));
        final ArrayList<String> yVals = new ArrayList<>();


        yVals.add("Very Good");
        yVals.add("Good");
        yVals.add("Average");
        yVals.add("Poor");
        yVals.add("Very Poor");
        yVals.add("Worst");


        yAxis.setEnabled(true);
//        yAxis.setAxisMaximum(yVals.size());
        yAxis.setLabelCount(yVals.size());

        lineChartDownFill.getAxisRight().setEnabled(false);

        lineChartDownFillWithData();
        lineChartDownFill.setDrawGridBackground(false);
        lineChartDownFill.setGridBackgroundColor(Color.WHITE);

        lineChartDownFill.getLegend().setEnabled(false);
        lineChartDownFill.setDescription(null);

//        yAxis.setValueFormatter(new YaxisValueFormatter(yAxisVals));

    }

    private void lineChartDownFillWithData() {

        ArrayList<Entry> entryArrayList = new ArrayList<>();
        entryArrayList.add(new Entry(0, 6f));
        entryArrayList.add(new Entry(1, 5f));
        entryArrayList.add(new Entry(2, 50f));
        entryArrayList.add(new Entry(3, 4f));
        entryArrayList.add(new Entry(4, 45f));
        entryArrayList.add(new Entry(5, 30f));
        entryArrayList.add(new Entry(6, 10f));

        ArrayList<Entry> entryArrayList1 = new ArrayList<>();
        entryArrayList1.add(new Entry(0, 65f));
        entryArrayList1.add(new Entry(1, 50f));
        entryArrayList1.add(new Entry(2, 60f));
        entryArrayList1.add(new Entry(3, 45f));
        entryArrayList1.add(new Entry(4, 30f));
        entryArrayList1.add(new Entry(5, 30f));
        entryArrayList1.add(new Entry(6, 35f));


        LineDataSet lineDataSet = new LineDataSet(entryArrayList, "This is y bill");
        LineDataSet lineDataSet1 = new LineDataSet(entryArrayList1, "This is y bill");

        lineDataSet.setLineWidth(2.2f);
        lineDataSet.setColor(Color.parseColor("#03DA64"));
        lineDataSet.setDrawValues(false);
        lineDataSet.setCircleRadius(10f);
        lineDataSet.setCircleColor(Color.YELLOW);

        lineDataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        lineDataSet.setCubicIntensity(0.2f);
        lineDataSet.setDrawFilled(false);

        lineDataSet.setDrawCircles(false);


        lineDataSet1.setLineWidth(2.2f);
        lineDataSet1.setColor(Color.parseColor("#FF6F00"));
        lineDataSet1.setDrawValues(false);
        lineDataSet1.setCircleRadius(10f);
        lineDataSet1.setCircleColor(Color.YELLOW);

        lineDataSet1.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        lineDataSet1.setCubicIntensity(0.2f);
        lineDataSet1.setDrawFilled(false);

        lineDataSet1.setDrawCircles(false);

        ArrayList<ILineDataSet> iLineDataSetArrayList = new ArrayList<>();
        iLineDataSetArrayList.add(lineDataSet);
        iLineDataSetArrayList.add(lineDataSet1);

        LineData lineData = new LineData(iLineDataSetArrayList);
        lineData.setValueTextSize(10f);
        lineData.setValueTextColor(Color.GRAY);

        lineChartDownFill.setData(lineData);
        lineChartDownFill.invalidate();


//        lineChartDownFill.getAxisLeft().setDrawGridLines(false);
        lineChartDownFill.getXAxis().setDrawGridLines(false);

    }

}