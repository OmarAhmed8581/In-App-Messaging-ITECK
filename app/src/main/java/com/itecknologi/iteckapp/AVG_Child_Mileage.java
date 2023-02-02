package com.itecknologi.iteckapp;

import static android.content.ContentValues.TAG;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.DefaultAxisValueFormatter;
import com.github.mikephil.charting.formatter.DefaultValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.LargeValueFormatter;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.material.card.MaterialCardView;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;


public class AVG_Child_Mileage extends Fragment {
    JSONObject jsonObject;
    ArrayList<PieEntry> HourlyData;
    PieChart pieChart;
    TextView txtBelongTo;
    MaterialCardView imgIndicator;
    int[] MY_COLORS = {
            Color.parseColor("#FBE34F"),
            Color.parseColor("#F9D040"),
            Color.parseColor("#F8BD30"),
            Color.parseColor("#F7AB22"),
            Color.parseColor("#F59611"),
            Color.parseColor("#F48200"),
    };

    ArrayList<PieEntry> entries;
    HashMap<String, Float> hourlyMap = new HashMap<>();

    public AVG_Child_Mileage() {

    }

    public AVG_Child_Mileage(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
    }


    public static AVG_Child_Amount newInstance(String param1, String param2) {
        AVG_Child_Amount fragment = new AVG_Child_Amount();
        Bundle args = new Bundle();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {


        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_a_v_g__child__mileage, container, false);


        pieChart = v.findViewById(R.id.pieChart);
        txtBelongTo = v.findViewById(R.id.txtBelongTo);
        imgIndicator = v.findViewById(R.id.imgIndicator);
        setUp();


        JSONObject todayData = null;
        try {

            String FuelCostSlabId = jsonObject.getString("DistanceSlabId");
            int TotalVehicle = Math.round(Float.parseFloat(jsonObject.getString("TotalVehicle").toString()));

            todayData = jsonObject.getJSONObject("Distance");
            Iterator<String> keys = todayData.keys();

            HourlyData = new ArrayList<>();
            int slabIdIndex = 0;
            String globalKey = null;
            int count = 0;
            while (keys.hasNext()) {
                String key = keys.next();
                if (key.equals(FuelCostSlabId)) {
                    slabIdIndex = count;
                    globalKey = key;
                }
                Object value = null;
                try {
                    value = todayData.get(key);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                hourlyMap.put(key, Float.parseFloat(value.toString()));
                String temp = null;
                if (key != null) {
                    switch (key) {
                        case "2":
                            temp = "=<25";
                            break;

                        case "3":
                            temp = ">25 and =<50";
                            break;

                        case "4":
                            temp = ">50 and <=75";
                            break;

                        case "5":
                            temp = ">75 and =<100";
                            break;

                        case "6":
                            temp = ">100 and =<125";
                            break;

                        case "7":
                            temp = ">125";
                            break;

                    }
                }
                int percentage = Math.round(Float.parseFloat(value.toString()) / TotalVehicle * 100);
                PieEntry b = new PieEntry(Float.parseFloat(value.toString()), percentage + "%");
                HourlyData.add(b);
                count++;
            }

            int cat = Integer.parseInt(todayData.get(globalKey).toString());
            int pp = cat / TotalVehicle * 100;
            float percentage = (((float) cat) / TotalVehicle) * 100;

            txtBelongTo.setText("You Ly in " + Math.round(percentage) + "%");
//            txtBelongTo.setText("You ly in " + p + "%");
            int color = MY_COLORS[slabIdIndex];
            imgIndicator.setCardBackgroundColor(color);

            loadData(HourlyData);


        } catch (JSONException e) {
            e.printStackTrace();
        }


        return v;
    }

    private void loadData(ArrayList<PieEntry> hourlyData) {
//        entries = new ArrayList<>();
//        entries.add(new PieEntry(0.2f, "A"));
//        entries.add(new PieEntry(0.15f, "B"));
//        entries.add(new PieEntry(0.25f, "C"));
//        entries.add(new PieEntry(0.12f, "D"));
//        entries.add(new PieEntry(0.27f, "B"));


        ArrayList<Integer> colors = new ArrayList<>();
        for (int c : MY_COLORS) {
            colors.add(Integer.valueOf(c));
        }
        PieDataSet dataSet = new PieDataSet(hourlyData, "categories");
        dataSet.setColors((List<Integer>) colors);

        PieData data2 = new PieData(dataSet);
        data2.setDrawValues(true);
        data2.setValueTextSize(0f);
        data2.setHighlightEnabled(true);
        data2.setValueTextColor(1);
        this.pieChart.setData(data2);
        this.pieChart.invalidate();
    }


    private void setUp() {
        this.pieChart.setDrawHoleEnabled(true);
        this.pieChart.setUsePercentValues(false);
        this.pieChart.setEntryLabelTextSize(11f);

        this.pieChart.setEntryLabelColor(R.color.black);
        this.pieChart.setHoleRadius(50.0f);
        this.pieChart.setCenterTextSize(20.0f);
        this.pieChart.getDescription().setEnabled(false);
        Legend l = this.pieChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);
        l.setEnabled(false);
    }
}