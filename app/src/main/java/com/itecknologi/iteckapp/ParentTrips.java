package com.itecknologi.iteckapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.graphics.Color;
import android.os.Bundle;

import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class ParentTrips extends AppCompatActivity {

    private TabLayout tablayout;
    private ViewPager viewpager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trips_tab);

        tablayout = findViewById(R.id.tablayout);
        viewpager = findViewById(R.id.pager);

        tablayout.setupWithViewPager(viewpager);
        VPAdapter vpAdapter = new VPAdapter(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        vpAdapter.addFragment(new child_trip_day(), "D");
        vpAdapter.addFragment(new child_trip_week(), "W");
        vpAdapter.addFragment(new child_trip_month(), "M");
        viewpager.setAdapter(vpAdapter);

        chart1();
        chart2();
        chart3();

    }

    private void chart1() {
        HorizontalBarChart chart = findViewById(R.id.barSpeed);
        HorizontalBarChart chart2 = findViewById(R.id.horibarYesterday);
        BarData data = new BarData(getDataSet());

        chart.setData(data);
        chart.animateXY(2000, 2000);
        chart.invalidate();
        chart.getXAxis().setDrawGridLines(false);
        chart.getAxisLeft().setDrawGridLines(false);
        chart.setTouchEnabled(false);
        data.setDrawValues(false);
        Description des = chart.getDescription();
        des.setEnabled(false);
        Legend leg = chart.getLegend();
        leg.setEnabled(false);
        YAxis rightAxis = chart.getAxisRight();
        rightAxis.setDrawZeroLine(true);
        rightAxis.setEnabled(false);
        rightAxis.setTextColor(Color.WHITE);
        YAxis leftAxis = chart.getAxisLeft();
        leftAxis.setEnabled(false);
        leftAxis.setDrawZeroLine(true);
        XAxis xAxis = chart.getXAxis();
        xAxis.setEnabled(false);

        //chart2 start

        BarData data2 = new BarData(getDataSet2());
        chart2.setData(data2);
        chart2.animateXY(2000, 2000);
        chart2.invalidate();
        chart2.getXAxis().setDrawGridLines(false);
        chart2.getAxisLeft().setDrawGridLines(false);
        chart2.setTouchEnabled(false);

        data2.setDrawValues(false);
        Description des2 = chart2.getDescription();
        des2.setEnabled(false);
        Legend leg2 = chart2.getLegend();
        leg2.setEnabled(false);
        YAxis rightAxis2 = chart2.getAxisRight();
        rightAxis2.setDrawZeroLine(true);
        rightAxis2.setEnabled(false);
        rightAxis2.setTextColor(Color.WHITE);
        YAxis leftAxis2 = chart2.getAxisLeft();
        leftAxis2.setEnabled(false);
        leftAxis2.setDrawZeroLine(true);
        XAxis xAxis2 = chart2.getXAxis();
        xAxis2.setEnabled(false);
    }

    private void chart2() {
        HorizontalBarChart chart = findViewById(R.id.chartThisWeek);
        HorizontalBarChart chart2 = findViewById(R.id.chartLastWeek);
        BarData data = new BarData(getDataSet());

        chart.setData(data);
        chart.animateXY(2000, 2000);
        chart.invalidate();
        chart.getXAxis().setDrawGridLines(false);
        chart.getAxisLeft().setDrawGridLines(false);
        chart.setTouchEnabled(false);
        data.setDrawValues(false);
        Description des = chart.getDescription();
        des.setEnabled(false);
        Legend leg = chart.getLegend();
        leg.setEnabled(false);
        YAxis rightAxis = chart.getAxisRight();
        rightAxis.setDrawZeroLine(true);
        rightAxis.setEnabled(false);
        rightAxis.setTextColor(Color.WHITE);
        YAxis leftAxis = chart.getAxisLeft();
        leftAxis.setEnabled(false);
        leftAxis.setDrawZeroLine(true);
        XAxis xAxis = chart.getXAxis();
        xAxis.setEnabled(false);

        //chart2 start

        BarData data2 = new BarData(getDataSet2());
        chart2.setData(data2);
        chart2.animateXY(2000, 2000);
        chart2.invalidate();
        chart2.getXAxis().setDrawGridLines(false);
        chart2.getAxisLeft().setDrawGridLines(false);
        chart2.setTouchEnabled(false);

        data2.setDrawValues(false);
        Description des2 = chart2.getDescription();
        des2.setEnabled(false);
        Legend leg2 = chart2.getLegend();
        leg2.setEnabled(false);
        YAxis rightAxis2 = chart2.getAxisRight();
        rightAxis2.setDrawZeroLine(true);
        rightAxis2.setEnabled(false);
        rightAxis2.setTextColor(Color.WHITE);
        YAxis leftAxis2 = chart2.getAxisLeft();
        leftAxis2.setEnabled(false);
        leftAxis2.setDrawZeroLine(true);
        XAxis xAxis2 = chart2.getXAxis();
        xAxis2.setEnabled(false);
    }

    private void chart3() {
        HorizontalBarChart chart = findViewById(R.id.horibarThisMonth);
        HorizontalBarChart chart2 = findViewById(R.id.horibarLastMonth);
        BarData data = new BarData(getDataSet());
        chart.setData(data);
        chart.animateXY(2000, 2000);
        chart.invalidate();
        chart.getXAxis().setDrawGridLines(false);
        chart.getAxisLeft().setDrawGridLines(false);
        chart.setTouchEnabled(false);
        data.setDrawValues(false);
        Description des = chart.getDescription();
        des.setEnabled(false);
        Legend leg = chart.getLegend();
        leg.setEnabled(false);
        YAxis rightAxis = chart.getAxisRight();
        rightAxis.setDrawZeroLine(true);
        rightAxis.setEnabled(false);
        rightAxis.setTextColor(Color.WHITE);
        YAxis leftAxis = chart.getAxisLeft();
        leftAxis.setEnabled(false);
        leftAxis.setDrawZeroLine(true);
        XAxis xAxis = chart.getXAxis();
        xAxis.setEnabled(false);

        //chart2 start

        BarData data2 = new BarData(getDataSet2());
        chart2.setData(data2);
        chart2.animateXY(2000, 2000);
        chart2.invalidate();
        chart2.getXAxis().setDrawGridLines(false);
        chart2.getAxisLeft().setDrawGridLines(false);
        chart2.setTouchEnabled(false);

        data2.setDrawValues(false);
        Description des2 = chart2.getDescription();
        des2.setEnabled(false);
        Legend leg2 = chart2.getLegend();
        leg2.setEnabled(false);
        YAxis rightAxis2 = chart2.getAxisRight();
        rightAxis2.setDrawZeroLine(true);
        rightAxis2.setEnabled(false);
        rightAxis2.setTextColor(Color.WHITE);
        YAxis leftAxis2 = chart2.getAxisLeft();
        leftAxis2.setEnabled(false);
        leftAxis2.setDrawZeroLine(true);
        XAxis xAxis2 = chart2.getXAxis();
        xAxis2.setEnabled(false);
    }

    private BarDataSet getDataSet() {

        ArrayList<BarEntry> entries = new ArrayList();
        entries.add(new BarEntry(4f, 7f));
        entries.add(new BarEntry(4f, 2f));

        //entries.add(new BarEntry(3f,1.8f));


        BarDataSet dataset = new BarDataSet(entries, "hamza");
        dataset.setColor(Color.rgb(220, 60, 5));
        dataset.setDrawValues(false);

        return dataset;


    }

    private BarDataSet getDataSet2() {

        ArrayList<BarEntry> entries2 = new ArrayList();
        entries2.add(new BarEntry(2f, 4.5f));
        entries2.add(new BarEntry(2f, 2.25f));
        //entries.add(new BarEntry(3f,1.8f));

        BarDataSet dataset2 = new BarDataSet(entries2, "hamza");
        dataset2.setColor(Color.rgb(224, 224, 224));
        dataset2.setDrawValues(false);
        return dataset2;


    }

    private ArrayList<String> getXAxisValues() {
        ArrayList<String> labels = new ArrayList();
        labels.add("A");

        return labels;


    }
}