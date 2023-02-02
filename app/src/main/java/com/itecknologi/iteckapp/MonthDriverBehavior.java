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

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;


public class MonthDriverBehavior extends Fragment {
    LineChart lineChartDownFill;
    ArrayList<String> yAxisVals;

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


        xAxis.setCenterAxisLabels(false);
        xAxis.setDrawGridLines(false);
        xAxis.setAxisMaximum(xVals.size());
        xAxis.setLabelCount(xVals.size() / 3);
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

//        yAxis.setValueFormatter(new YaxisValueFormatter(yVals));

    }

    private void lineChartDownFillWithData() {

        ArrayList<Entry> entryArrayList = new ArrayList<>();
        entryArrayList.add(new Entry(0, 60f));
        entryArrayList.add(new Entry(1, 70f));
        entryArrayList.add(new Entry(2, 60f));
        entryArrayList.add(new Entry(3, 60f));
        entryArrayList.add(new Entry(4, 60f));
        entryArrayList.add(new Entry(5, 70f));
        entryArrayList.add(new Entry(6, 70f));

        entryArrayList.add(new Entry(7, 60f));
        entryArrayList.add(new Entry(8, 80f));
        entryArrayList.add(new Entry(9, 60f));
        entryArrayList.add(new Entry(10, 80f));


        entryArrayList.add(new Entry(11, 80f));
        entryArrayList.add(new Entry(12, 90f));
        entryArrayList.add(new Entry(13, 80f));
        entryArrayList.add(new Entry(14, 90f));
        entryArrayList.add(new Entry(15, 60f));
        entryArrayList.add(new Entry(16, 70f));
        entryArrayList.add(new Entry(17, 60f));
        entryArrayList.add(new Entry(18, 80f));
        entryArrayList.add(new Entry(19, 60f));
        entryArrayList.add(new Entry(20, 70f));
        entryArrayList.add(new Entry(21, 80f));
        entryArrayList.add(new Entry(22, 60f));
        entryArrayList.add(new Entry(23, 70f));
        entryArrayList.add(new Entry(24, 60f));
        entryArrayList.add(new Entry(25, 90f));
        entryArrayList.add(new Entry(26, 60f));
        entryArrayList.add(new Entry(27, 60f));
        entryArrayList.add(new Entry(28, 60f));


        ArrayList<Entry> entryArrayList1 = new ArrayList<>();
        entryArrayList1.add(new Entry(0, 60f));
        entryArrayList1.add(new Entry(1, 90f));
        entryArrayList1.add(new Entry(2, 60f));
        entryArrayList1.add(new Entry(3, 60f));
        entryArrayList1.add(new Entry(4, 70f));
        entryArrayList1.add(new Entry(5, 90f));
        entryArrayList1.add(new Entry(6, 80f));

        entryArrayList1.add(new Entry(7, 60f));
        entryArrayList1.add(new Entry(8, 90f));
        entryArrayList1.add(new Entry(9, 60f));
        entryArrayList1.add(new Entry(10, 70f));


        entryArrayList1.add(new Entry(11, 80f));
        entryArrayList1.add(new Entry(12, 60f));
        entryArrayList1.add(new Entry(13, 90f));
        entryArrayList1.add(new Entry(14, 70f));
        entryArrayList1.add(new Entry(15, 60f));
        entryArrayList1.add(new Entry(16, 80f));
        entryArrayList1.add(new Entry(17, 90f));
        entryArrayList1.add(new Entry(18, 80f));
        entryArrayList1.add(new Entry(19, 60f));
        entryArrayList1.add(new Entry(20, 80f));
        entryArrayList1.add(new Entry(21, 70f));
        entryArrayList1.add(new Entry(22, 60f));
        entryArrayList1.add(new Entry(23, 80f));
        entryArrayList1.add(new Entry(24, 80f));
        entryArrayList1.add(new Entry(25, 60f));
        entryArrayList1.add(new Entry(26, 70f));
        entryArrayList1.add(new Entry(27, 80f));
        entryArrayList1.add(new Entry(28, 90f));


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