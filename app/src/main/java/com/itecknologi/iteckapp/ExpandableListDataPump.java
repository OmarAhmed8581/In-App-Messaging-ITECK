package com.itecknologi.iteckapp;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.TreeMap;

public class ExpandableListDataPump {

    public static TreeMap<FreqDayModel, List<FreqListModel>> getData(JSONObject jsonObj) {

        List<String> days = new ArrayList<String>();
        List<FreqListModel> details = null;
        TreeMap<FreqDayModel, List<FreqListModel>> expandableListDetail = new TreeMap();
        ArrayList<FreqBlockData> list = new ArrayList<>();

        JSONObject freqPlaces = jsonObj;

        Iterator<String> keys = freqPlaces.keys();

        int count = 0;

        while (keys.hasNext()) {
            String key = keys.next();
            if (!key.equals("Success")) {
                days.add(count, key);
                JSONArray value = null;
                details = new ArrayList<FreqListModel>();
                try {
                    value = (JSONArray) freqPlaces.get(key);
                    for (int i = 0; i < value.length(); i++) {
                        JSONArray finalArray = (JSONArray) value.get(i);
                        String start = "";
                        String end = "";
                        ArrayList<String> finalArrayList = new ArrayList();
                        for (int j = 0; j < finalArray.length(); j++) {
                            finalArrayList.add(String.valueOf(finalArray.get(j)));
                        }
                        start = finalArrayList.get(0);
                        end = finalArrayList.get(1);

                        if (start != null && end != null)
                            details.add(i, new FreqListModel(start, end, getTime(start, end)));
                    }

                    expandableListDetail.put(
                            new FreqDayModel(days.get(count).toUpperCase(), String.valueOf(details.size())), details
                    );
                    count++;


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        //        DayOfWeek.valueOf()
        return expandableListDetail;
    }

    public static String getTime(String start, String end) {

        String dateStart = start;
        String dateStop = end;


        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long diffHours = 0;
        long diffMinutes = 0;
        long diffSeconds = 0;

        Date d1 = null;
        Date d2 = null;
        try {
            d1 = format.parse(dateStart);
            d2 = format.parse(dateStop);
            long diff = d2.getTime() - d1.getTime();

            diffSeconds = diff / 1000 % 60;
            diffMinutes = diff / (60 * 1000) % 60;
            diffHours = diff / (60 * 60 * 1000);


        } catch (ParseException e) {
            e.printStackTrace();
        }


        String stringTime = "";
        if (diffHours > 0) {

            stringTime =
                    String.valueOf(diffHours) + " hrs "
                            + String.valueOf(diffMinutes) + " min ";

        } else if (diffHours == 0 && diffMinutes > 0) {

            stringTime =
                    String.valueOf(diffMinutes) + " min "
                            + String.valueOf(diffSeconds)
                            + " sec ";

        } else if (diffMinutes == 0 && diffSeconds > 0) {

            stringTime =
                    String.valueOf(diffSeconds) + " sec ";
        }
        return stringTime;
    }
}