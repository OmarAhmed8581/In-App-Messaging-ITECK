package com.itecknologi.iteckapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.github.mikephil.charting.data.BarEntry;
import com.google.android.material.tabs.TabLayout;
import com.mackhartley.roundedprogressbar.RoundedProgressBar;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import io.github.douglasjunior.androidSimpleTooltip.SimpleTooltip;

public class ParentMileage extends AppCompatActivity implements View.OnClickListener {

    private TabLayout tablayout;
    private ViewPager viewpager;
    private TextView x, y;
    private TextView txtThisMonth, txtLastMonth, txtThisWeek, txtLastWeek, txtToday, txtYesterday;
    private LinearLayout btnDisclaimer, btnCall;

    RoundedProgressBar chartToday, chartYesterday, chart2LastWeek, chartThisWeek, horibarThisMonth, horibarLastMonth;


    private Float valYesterdayG;
    private Float valTodayG;
    private Float valThisWeekG;
    private Float valLastWeekG;
    private Float valThisMonthG;
    private Float valLastMonthG;
    VPAdapter vpAdapter;
    private float progress1, progress2, progress3, progress4, progress5, progress6 = 0;

    ArrayList<BarEntry> past7Days;
    ArrayList<BarEntry> HourlyData;
    ArrayList<BarEntry> MonthlyData;
    HashMap<String, Float> p7daysMap = new HashMap<>();
    HashMap<String, Float> hourlyMap = new HashMap<>();
    HashMap<String, Float> monthlyMap = new HashMap<>();
    String[] day;
    private int sumMileageP7;
    private int sumMileageHourly;
    private int sumMileageMonthly;
    private Float p7FuelG;
    private Float p7FuelH;
    private Float p7FuelM;
    private Dialog loadingDialogue;

    TextView txtRecent, txtThisWeekM, txtMonthM;
    private String[] day2;
    private ArrayList<String> keyArrayList = new ArrayList<>();
    private ArrayList<String> dateArrayListWeek = new ArrayList<>();

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @SuppressLint("CutPasteId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mileage_tab);

        vpAdapter = new VPAdapter(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);

        tablayout = findViewById(R.id.tablayout);
        viewpager = findViewById(R.id.pager);
        x = findViewById(R.id.Header_name);
        y = findViewById(R.id.Header_plate);
        txtThisMonth = findViewById(R.id.txtThisMonth);
        txtLastMonth = findViewById(R.id.txtLastMonth);
        txtThisWeek = findViewById(R.id.txtThisWeek);
        txtLastWeek = findViewById(R.id.txtLastWeek);
        txtToday = findViewById(R.id.txtToday);
        txtYesterday = findViewById(R.id.txtYesterday);
        horibarThisMonth = findViewById(R.id.horibarThisMonth);
        horibarLastMonth = findViewById(R.id.horibarLastMonth);
        chartThisWeek = findViewById(R.id.chartThisWeek);
        chart2LastWeek = findViewById(R.id.chartLastWeek);
        chartToday = findViewById(R.id.barSpeed);
        chartYesterday = findViewById(R.id.horibarYesterday);
        chartYesterday = findViewById(R.id.horibarYesterday);
        tablayout.setupWithViewPager(viewpager);
        txtRecent = findViewById(R.id.txtRecent);
        btnDisclaimer = findViewById(R.id.btnDisclaimerr);
        btnCall = findViewById(R.id.btnCall);
        txtThisWeekM = findViewById(R.id.txtThisWeekM);
        txtMonthM = findViewById(R.id.txtMonthM);

        clickListeners();
        Bundle extras = getIntent().getExtras();
        loadingItem();
        if (extras != null) {
            x.setText(extras.getString("user_namee"));
            y.setText(extras.getString("car_platee"));
        }

        setDayText();
        getDataFromAPI("101837", "65566");

    }

    private void clickListeners() {
        btnCall.setOnClickListener(this);
        btnDisclaimer.setOnClickListener(this);
    }

    private void getDataFromAPI(String id_vehicle, String ObjectId) {

        loadingDialogue.show();
        if (checkConnection()) {
            String url = "https://api.itecknologi.com/mobile/get_consolidated_distance_report.php";
            RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
            StringRequest request = new StringRequest(Request.Method.POST, url, new com.android.volley.Response.Listener<String>() {
                @RequiresApi(api = Build.VERSION_CODES.O)
                @SuppressLint({"SimpleDateFormat", "SetTextI18n"})
                @Override
                public void onResponse(String response) {
                    try {

                        JSONObject respObj = new JSONObject(response);
                        if (respObj.getString("Success").equals("true")) {


                            getToday(respObj);
                            getP7Data(respObj);
                            getMonthData(respObj);

                            setTextOnTextIds(respObj);

                            viewpager.setAdapter(vpAdapter);
                            loadingDialogue.dismiss();
                        }


                    } catch (JSONException e) {

                        e.printStackTrace();
                    }
                }
            }, new com.android.volley.Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    loadingDialogue.dismiss();
                    Toast.makeText(getApplicationContext(), "Fail to get response = " + error, Toast.LENGTH_SHORT).show();
                }
            }) {
                @Override
                protected Map<String, String> getParams() {

                    Map<String, String> params = new HashMap<String, String>();
                    params.put("ObjectId", ObjectId);
                    params.put("Veh_Id", id_vehicle);
                    return params;
                }
            };
            queue.add(request);
        } else {
            loadingDialogue.dismiss();
            showAlertDialogue2("No Internet",
                    "Make sure your internet is connected and try again", R.drawable.ic_wifi_off_fill);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void loadingItem() {
        loadingDialogue = new Dialog(this);
        loadingDialogue.setContentView(R.layout.loading);
        loadingDialogue.getWindow().setBackgroundDrawable(getDrawable(R.drawable.rounded_corner_main_activity));
        loadingDialogue.getWindow().setLayout(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        loadingDialogue.setCancelable(false);
    }


    private void getMonthData(JSONObject respObj) {
        JSONObject todayData = null;
        try {
            todayData = respObj.getJSONObject("Past28Days");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Iterator<String> keys = todayData.keys();
        MonthlyData = new ArrayList<>();
        double sum = 0.0;
        int count = 0;

        while (keys.hasNext()) {
            String key = keys.next();
            Object value = null;
            Object key1 = null;
            try {
                value = todayData.get(key);
                key1 = key;

            } catch (JSONException e) {
                e.printStackTrace();
            }
            keyArrayList.add(key1.toString());
            monthlyMap.put(key, Float.parseFloat(value.toString()));
            sum += Double.parseDouble(value.toString());
            BarEntry b = new BarEntry(count, Float.parseFloat(value.toString()));
            MonthlyData.add(b);
            count++;

        }
        Float d2 = new Float(sum);
        sumMileageMonthly = d2.intValue();
        Float ltr1 = (float) Math.round(d2 * 100) / 100;

        vpAdapter.addFragment(new child_horibar_frag_month(MonthlyData, ltr1, keyArrayList, "m"), "M");

        p7FuelH = ltr1;

        double a1 = sum / count;
        Double d = new Double(a1);
    }

    private void setDayText() {

        Calendar calendar = Calendar.getInstance();
        int day1 = calendar.get(Calendar.DAY_OF_WEEK);
        @SuppressLint("SimpleDateFormat") DateFormat format = new SimpleDateFormat("EEEE");
        @SuppressLint("SimpleDateFormat") DateFormat date4Format = new SimpleDateFormat("yyyy/MM/dd");
        calendar.setFirstDayOfWeek(day1);

        calendar.set(Calendar.DAY_OF_WEEK, day1);

        String[] days = new String[7];

        for (int i = 0; i < 7; i++) {
            days[i] = format.format(calendar.getTime());
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }

        day2 = new String[7];
        day = new String[7];
        String temp = days[0];
        int count = 0;
        for (int i = 6; i > 0; i--) {
            day[count] = days[i].toString();
            day2[count] = String.valueOf(days[i].toString().charAt(0));
            count++;
        }
        day2[6] = String.valueOf(days[0].toString().charAt(0));
        day[6] = days[0].toString();

    }


    private void getP7Data(JSONObject respObj) {

        JSONObject P7Data = null;
        try {
            P7Data = respObj.getJSONObject("PastSevenDays");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Iterator<String> keys = P7Data.keys();
        past7Days = new ArrayList<>();
        double sum = 0.0;
        int count = 0;

        while (keys.hasNext()) {

            String key = keys.next();
            Object value = null;
            Object key1 = null;
            try {
                value = P7Data.get(key);
                key1 = key;

            } catch (JSONException e) {
                e.printStackTrace();
            }
            dateArrayListWeek.add(key1.toString());
            if (Float.parseFloat(value.toString()) >= 1.0) {
                count++;
            }

            p7daysMap.put(key, Float.parseFloat(value.toString()));
            sum += Double.parseDouble(value.toString());
            Calendar c = Calendar.getInstance();
            Date date1 = null;
            try {
                date1 = new SimpleDateFormat("yyyy-MM-dd").parse(key);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            c.setTime(date1);
            int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
            System.out.println("Day of week in number:" + dayOfWeek);
            String dayWeekText = new SimpleDateFormat("EEEE").format(date1);
            for (int i = 0; i < day.length; i++) {
                if (day[i].equals(dayWeekText)) {
                    BarEntry b = new BarEntry(i, Float.parseFloat(value.toString()));
                    past7Days.add(b);
                }
            }
            System.out.println("Day of week in text:" + dayWeekText);
        }
        Float d2 = new Float(sum);
        sumMileageP7 = d2.intValue();
        Float ltr1 = (float) Math.round(d2 * 100) / 100;

        vpAdapter.addFragment(new child_horibar_frag_week(past7Days, day2, ltr1, dateArrayListWeek), "W");
        p7FuelG = ltr1;

        double a1 = sum / count;
        Double d = new Double(a1);
    }

    private void getToday(JSONObject respObj) {

        JSONObject todayData = null;
        try {
            todayData = respObj.getJSONObject("HourlyData");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Iterator<String> keys = todayData.keys();
        HourlyData = new ArrayList<>();
        double sum = 0.0;
        int count = 0;

        while (keys.hasNext()) {

            String key = keys.next();
            Object value = null;
            try {
                value = todayData.get(key);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            hourlyMap.put(key, Float.parseFloat(value.toString()));
            sum += Double.parseDouble(value.toString());
            BarEntry b = new BarEntry(count, Float.parseFloat(value.toString()));
            HourlyData.add(b);
            count++;

        }
        Float d2 = new Float(sum);
        sumMileageHourly = d2.intValue();
        Float ltr1 = (float) Math.round(d2 * 100) / 100;

        vpAdapter.addFragment(new child_horibar_frag_day(HourlyData, ltr1), "D");

        p7FuelH = ltr1;

        double a1 = sum / count;
        Double d = new Double(a1);
    }


    @SuppressLint("SetTextI18n")
    private void setTextOnTextIds(JSONObject respObj) {
        try {
            Float todayVal = Float.valueOf(respObj.getString("Today").toString());
            todayVal = (float) Math.round(todayVal * 100) / 100;
            txtToday.setText(String.valueOf(todayVal));
            valTodayG = todayVal;

            Float valYesterday = Float.valueOf(respObj.getString("Yesterday").toString());
            valYesterday = (float) Math.round(valYesterday * 100) / 100;
            txtYesterday.setText(String.valueOf(valYesterday));
            valYesterdayG = valYesterday;


            if (valYesterday > todayVal) {
                txtRecent.setText("You have driven "
                        .concat(Math.round(valYesterday - todayVal) +
                                " Km less than this today"));
            } else if (valYesterday <= todayVal) {
                txtRecent.setText("You have driven "
                        .concat(Math.round(todayVal - valYesterday) +
                                " Km more than yesterday"));
            }

            if (todayVal > valYesterday) {
                progress1 = valYesterday / todayVal * 100;
                progress2 = 100 - progress1;
                progress1 = 100;
                progress2 = 100 - progress2;
            } else if (todayVal < valYesterday) {
                progress2 = todayVal / valYesterday * 100;
                progress1 = 100 - progress2;
                progress2 = 100;
                progress1 = 100 - progress1;
            }
            progressBar1(progress1, chartToday);
            progressBar1(progress2, chartYesterday);

            Float thisWeek = Float.valueOf(respObj.getString("CurrentWeek").toString());
            thisWeek = (float) Math.round(thisWeek * 100) / 100;
            txtThisWeek.setText(String.valueOf(thisWeek));
            valThisWeekG = thisWeek;

            Float lastWeek = Float.valueOf(respObj.getString("LastWeek").toString());
            lastWeek = (float) Math.round(lastWeek * 100) / 100;
            txtLastWeek.setText(String.valueOf(lastWeek));
            valLastWeekG = lastWeek;

            if (lastWeek > thisWeek) {
                txtThisWeekM.setText("You have driven "
                        .concat(Math.round(lastWeek - thisWeek) +
                                " Km less than this Week"));
            } else if (lastWeek <= thisWeek) {
                txtThisWeekM.setText("You have driven "
                        .concat(Math.round(thisWeek - lastWeek) +
                                " Km more than Last Week"));
            }
            if (thisWeek > lastWeek) {
                progress3 = lastWeek / thisWeek * 100;
                progress4 = 100 - progress3;
                progress3 = 100;
                progress4 = 100 - progress4;
            } else if (thisWeek < lastWeek) {
                progress4 = thisWeek / lastWeek * 100;
                progress3 = 100 - progress4;
                progress4 = 100;
                progress3 = 100 - progress3;
            }
            progressBar1(progress3, chartThisWeek);
            progressBar1(progress4, chart2LastWeek);

            Float thisMonth = Float.valueOf(respObj.getString("CurrentMonth").toString());
            thisMonth = (float) Math.round(thisMonth * 100) / 100;
            txtThisMonth.setText(String.valueOf(thisMonth));
            valThisMonthG = thisMonth;


            Float lastMonth = Float.valueOf(respObj.getString("LastMonth").toString());
            lastMonth = (float) Math.round(lastMonth * 100) / 100;
            txtLastMonth.setText(String.valueOf(lastMonth));
            valLastMonthG = lastMonth;


            if (lastMonth > thisMonth) {
                txtMonthM.setText("You have driven "
                        .concat(Math.round(lastMonth - thisMonth) +
                                " Km less than this Month"));
            } else if (lastMonth <= thisMonth) {
                txtMonthM.setText("You have driven "
                        .concat(Math.round(thisMonth - lastMonth) +
                                " Km more than Last Month"));
            }

            if (thisMonth > lastMonth) {
                progress5 = lastMonth / thisMonth * 100;
                progress6 = 100 - progress5;
                progress5 = 100;
                progress6 = 100 - progress6;
            } else if (thisMonth < lastMonth) {
                progress6 = thisMonth / lastMonth * 100;
                progress5 = 100 - progress6;
                progress6 = 100;
                progress5 = 100 - progress5;
            }
            progressBar1(progress5, horibarThisMonth);
            progressBar1(progress6, horibarLastMonth);

        } catch (Exception e) {
            Toast.makeText(this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
        }

    }

    private void progressBar1(float progress, RoundedProgressBar progressBar) {
        progressBar.setProgressPercentage(progress, true);

        progressBar.showProgressText(false);
        progressBar.setAnimationLength(3500);
        Animation animation = new Animation() {
            @Override
            public void start() {
                super.start();
            }
        };
        progressBar.setAnimation(animation);
        progressBar.animate();
    }

    private boolean checkConnection() {
        ConnectivityManager manager = (ConnectivityManager) getApplicationContext()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        @SuppressLint("MissingPermission") NetworkInfo networkInfo = manager.getActiveNetworkInfo();

        if (null != networkInfo)
            return true;
        else
            return false;

    }

    private void showAlertDialogue2(String title, String message, int icon) {

        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setTitle(title);
        builder1.setMessage(message);
        builder1.setIcon(icon);
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });


        AlertDialog alert11 = builder1.create();
        alert11.show();

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnCall:
                String phone = "+9221111148325";
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null));
                startActivity(intent);
                break;

            case R.id.btnDisclaimerr:
                new SimpleTooltip.Builder(this)
                        .anchorView(view)
                        .text(R.string.disclaimer)
                        .gravity(Gravity.TOP)
                        .arrowColor(Color.parseColor("#ffffff"))
                        .backgroundColor(Color.parseColor("#ffffff"))
                        .animated(false)
                        .build()
                        .show();
                break;
        }
    }
}