package com.itecknologi.iteckapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class DriverActivity extends AppCompatActivity {

    Handler handler = new Handler();
    Runnable runnableCode;
    int count2 = 0;
    public TextView x, y;
    TextView score;
    TextView scoreHis;
    int colorCount = 60;
    ConstraintLayout constraintLayout;
    private TabLayout tablayout;
    private ViewPager viewpager;

    private RecyclerView recyclerView;
    DriverAdapter adapter;
    private List<DriverListModel> list;
    Context contextInstance;
    int txtScore;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @SuppressLint({"ResourceAsColor", "UseCompatLoadingForColorStateLists"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver);

        score = findViewById(R.id.txtScore);
        scoreHis = findViewById(R.id.txtCar);
        constraintLayout = findViewById(R.id.constraintLayout9);
        x = findViewById(R.id.Header_name);
        y = findViewById(R.id.txtCar);
        /** Fetch variable value from Main **/


        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            x.setText(extras.getString("user_namee"));
            y.setText(extras.getString("car_platee"));
            score.setText(extras.getString("score"));
            txtScore = Integer.parseInt(extras.getString("score"));
        }


//        score.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(DriverActivity.this, TripDetailActivity.class);
//                startActivity(intent);
//            }
//        });

        switchColor(txtScore);

        scoreHis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (colorCount >= 100) {
                    colorCount = 60;
                } else {
                    colorCount = colorCount + 10;
                    switchColor(colorCount);
                }
            }
        });


        tablayout = findViewById(R.id.tablayout);
        viewpager = findViewById(R.id.pager);

        tablayout.setupWithViewPager(viewpager);
        VPAdapter vpAdapter = new VPAdapter(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        vpAdapter.addFragment(new WeekDriverBehavior(), "WEEK");
        vpAdapter.addFragment(new MonthDriverBehavior(), "MONTH");
        viewpager.setAdapter(vpAdapter);

        rvInitialization();
        listData();


    }


    private void rvInitialization() {
        recyclerView = findViewById(R.id.driver_rv);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        list = new ArrayList<>();
        adapter = new DriverAdapter(list);
        recyclerView.setAdapter(adapter);
    }

    private void listData() {
        list.add(new DriverListModel("Over Speeding", "Rough and sudden braking", "24"));
        list.add(new DriverListModel("Idling", "Rough and sudden braking", "2"));
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @SuppressLint({"ResourceAsColor", "UseCompatLoadingForDrawables"})
    private void switchColor(int scoreStatus) {
        Drawable drawable1 = null;
        if (scoreStatus >= 90) {
            drawable1 = getDrawable(R.drawable.driverac_bg_red);

        } else if (scoreStatus >= 80) {
            drawable1 = getDrawable(R.drawable.driverac_bg_orange);

        } else if (scoreStatus >= 70) {
            drawable1 = getDrawable(R.drawable.driverac_bg_yellow);

        } else if (scoreStatus >= 60) {
            drawable1 = getDrawable(R.drawable.driverac_bg_green);
        }
        constraintLayout.setBackground(drawable1);
    }


}