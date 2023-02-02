package com.itecknologi.iteckapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.widget.TextView;

import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.utils.MPPointF;

import java.util.ArrayList;

public class CustomMarkerView2 extends MarkerView {
    private TextView tvContent;
    private TextView tvDate;
    private TextView txtEnv;
    private ArrayList<String> keyArrayList;
    private String env;

    public CustomMarkerView2(Context context, int layoutResource, ArrayList<String> keys, String env) {
        super(context, layoutResource);
        this.keyArrayList = keys;
        this.env = env;

        tvContent = (TextView) findViewById(R.id.txtVisits);
        tvDate = (TextView) findViewById(R.id.date_tv);
        txtEnv = findViewById(R.id.txtEnv);
    }


    @SuppressLint("SetTextI18n")
    @Override
    public void refreshContent(Entry e, Highlight highlight) {
        String environment = null;
        if (env.equals("a")) {
            environment = "PKR";
        } else if (env.equals("f")) {
            environment = "Ltr";
        } else if (env.equals("t")) {
            environment = "Trips";
        } else if (env.equals("m")) {
            environment = "Km";
        }

        txtEnv.setText(environment);

        Float ltr1 = (float) Math.round(highlight.getY() * 100) / 100;

        Float temp = ltr1;
        int x = (int) highlight.getX();
        String yAxis = String.valueOf(temp).trim();
        int temp2 = (int) e.getX();

        tvContent.setText(yAxis);
        tvDate.setText(keyArrayList.get(Math.round(highlight.getX())).toString());
        super.refreshContent(e, highlight);


    }


    @Override
    public MPPointF getOffset() {
        return new MPPointF(-(getWidth() / 2), -getHeight());
    }
}
