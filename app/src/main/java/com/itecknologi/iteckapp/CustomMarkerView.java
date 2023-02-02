package com.itecknologi.iteckapp;

import android.content.Context;
import android.widget.TextView;


import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.utils.MPPointF;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;

public class CustomMarkerView extends MarkerView {
    private TextView tvContent;
    private TextView tvDate;
    private TextView tvEnv;
    private String env;


    public CustomMarkerView(Context context, int layoutResource, ArrayList<String> keys, String env) {
        super(context, layoutResource);
        tvContent = (TextView) findViewById(R.id.txtVisits);
        tvDate = (TextView) findViewById(R.id.date_tv);
        tvEnv = (TextView) findViewById(R.id.txtEnv);

        this.env = env;


    }

    public CustomMarkerView(Context context, int layoutResource, String env) {
        super(context, layoutResource);
        tvContent = (TextView) findViewById(R.id.txtVisits);
        tvDate = (TextView) findViewById(R.id.date_tv);
        tvEnv = (TextView) findViewById(R.id.txtEnv);
        this.env = env;

    }


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

        tvEnv.setText(environment);

        Float ltr1 = (float) Math.round(highlight.getY() * 100) / 100;
        Float temp = ltr1;

        int x = (int) highlight.getX();
        String yAxis = String.valueOf(temp).trim();

        int temp2 = (int) e.getX();

        tvContent.setText(yAxis);
        tvDate.setText(convertTime(temp2));
        super.refreshContent(e, highlight);

    }

    private String convertTime(int i) {
        switch (i) {
            case 0:
                return "12am-1am";
            case 1:
                return "1am-2am";
            case 2:
                return "2am-3am";
            case 3:
                return "3am-4am";
            case 4:
                return "4am-5am";
            case 5:
                return "5am-6am";
            case 6:
                return "6am-7am";
            case 7:
                return "7am-8am";
            case 8:
                return "8am-9am";
            case 9:
                return "9am-10am";
            case 10:
                return "10am-11am";
            case 11:
                return "11am-12pm";
            case 12:
                return "12pm-1pm";
            case 13:
                return "1pm-2pm";
            case 14:
                return "2pm-3pm";
            case 15:
                return "3pm-4pm";
            case 16:
                return "4pm-5pm";
            case 17:
                return "5pm-6pm";
            case 18:
                return "6pm-7pm";
            case 19:
                return "7pm-8pm";
            case 20:
                return "8pm-9pm";
            case 21:
                return "9pm-10pm";
            case 22:
                return "10pm-11pm";
            case 23:
                return "11pm-12am";

        }
        return "";

    }


    @Override
    public MPPointF getOffset() {
        return new MPPointF(-(getWidth() / 2), -getHeight());
    }
}
