package com.itecknologi.iteckapp;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ExpandableListAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.MediaController;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.itecknologi.iteckapp.chat.MessageActivity;
import com.mackhartley.roundedprogressbar.RoundedProgressBar;
import com.mikhaellopez.circularprogressbar.CircularProgressBar;
import com.ntt.customgaugeview.library.GaugeView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

//import org.kxml2.wap.Wbxml;


import io.github.douglasjunior.androidSimpleTooltip.SimpleTooltip;
import me.bastanfar.semicirclearcprogressbar.SemiCircleArcProgressBar;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import retrofit2.converter.scalars.ScalarsConverterFactory;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, OnMapReadyCallback, ClickListener {
    private static final int MENU_ABOUT = 2;
    private static final int MENU_LAST_ID = 3;
    public GoogleMap mMap;
    Handler handler = new Handler();
    Runnable runnable;
    LinearLayout btnDisc, btnCall;
    JSONObject freqPlacesG = null;
    RoundedProgressBar progressBarSpeed, progressBarMileage, progressBarAmount;
    CardView trackLive, trackNow, cardAd;

    ArrayList<BarEntry> mileageBar;

    Integer sumMileageG = 0;
    Integer sumFuelG = 0;
    Integer sumAmountG = 0;

    Integer avg_mileags = 0;
    Integer avg_fuel = 0;
    Integer avg_amount = 0;

    int delay = 30000;
    private ArrayList<ModelFreqPlaces> list;
    private static final int PERMISSION_REQUEST_CODE = 1;
    private ProgressDialog progressDialog;
    MarkerOptions markerOptions;
    Marker mMarker;
    String temp;
    TextView txt1, txt2, txt3, txt4, txt5, txt6, txt7;
    TextView txtBusHr, txtPerHr, txtDrvScore;
    FloatingActionButton btnFABMsg;
    Drawable drawable1;
    LatLng myCarLocation;

    float yesterdayFuelG = 0;
    float yesterdayAmountG = 0;
    float yesterdayMileageG = 0;

    String[] day2;
    public double locationX;
    public double locationY;
    HashMap<String, Float> mileageMap = new HashMap<>();
    HashMap<String, Float> fuelMap = new HashMap<>();
    HashMap<String, Float> amountMap = new HashMap<>();
    int angle;
    int vehicleColor = R.drawable.black_car;
    int bgDrawable = R.drawable.bg_red;

    String[] day;

    HashMap<String, String[]> vehicledetails = new HashMap<String, String[]>();
    private String Veh_Id, ObjectId;
    ArrayList<String> imgArray;
    public String updated_user_name, updated_car_plate;

    /* access modifiers changed from: private */

    private CardView Amount_card, Fuel_card, Mileage_card, DriverScore, YesterdayCard;

    /* renamed from: Ad */
    public VideoView Ad;
    private RelativeLayout Main_Layout;
    private ScrollView Mscroll;
    int TIME_CONSTANT = 5000;
    String[] action = {"1 June", "5 June", "6 June", "8 June"};
    private ImageView banks;
    private ImageView carmechanic;
    private TextView cars, user_name, car_selected;
    Context context;
    private ImageView img1, img2, img3, img4, img5, img6, img7, img8, img9;
    int count2 = 0;
    private CardView currentloccard;
    List<LinkedHashMap<String, String[]>> data = new ArrayList();
    ExpandableListAdapter expandableListAdapter;
    HashMap<String, List<String>> expandableListDetail;
    List<String> expandableListTitle;
    private FrameLayout frameLayout;

    private ImageView gas;
    //Handler handler = new Handler();
    private ImageView hospitals;
    private ImageView img;
    boolean isTrue = true;
    private LineChart lineChart;
    protected float mBearing;

    private LinearLayout mainLayout;
    private LinearLayout mainLayout2;
    private LinearLayout mainLayout3;


    private ImageView parking;
    private PieChart pieChart;
    private ImageView resturant;
    Runnable runnableCode;
    Runnable runnableCodeAd;
    List<List<String>> secondLevel = new ArrayList();
    private SemiCircleArcProgressBar semiCircleArcProgressBar;
    private SemiCircleArcProgressBar semiCircleArcProgressBar2;
    CircularProgressBar mileageP7;
    ListView simpleList;

    /**
     * Cluster Work
     **/
    //double lat=0.00,lng=0.00;
    double g, h;

    String object_ID, vehicle_ID, live_date, car_angel;
    public TextView live_location, live_speed;


    /* renamed from: t */
    TimerTask f1t;

    String[] thriller = {"55 July", "61 August", "66 May", "8 July"};
    Timer timer = new Timer();
    BarChart past4weekchart;
    ArrayList<BarEntry> arrayList;
    ArrayList<BarEntry> fuelBar;
    ArrayList<BarEntry> amountBar;

    ArrayList<BarEntry> arrayListHoribar1;
    private int MileageThisMonthG;
    private int MileageLastMonthG;

    private int tripThisMonthG;
    private int tripLastMonthG;

    private int AmountThisMonthG;
    private int AmountLastMonthG;
    private RecyclerView recyclerView;

    private int FuelThisMonthG;
    private int FuelLastMonthG;
    Statss s;

    TextView past7daysFuel, past7daysAmount, past7daysKm, yesterdayFuel, yesterDayAmount, yesterDayKm;
    private Float p7FuelG;
    private Float p7AmountG;
    private Float p7MileageG;
    String year, month, currentDay;
    private Dialog loadingDialogue;

    CircularProgressBar amountP7;

    CircularProgressBar fuelP7;
    private RatingBar ratingBar;
    CircularProgressBar thisMonthAmountCbar;
    private TextView txtThisMonthAmount, txtThisMonthFuel, txtThisMonthMileage;
    CircularProgressBar thisMonthFuelCbar;
    CircularProgressBar thisMonthMileageCbar;
    int daysInMonth;
    VPAdapter vpAdapter;
    private TabLayout tablayout;
    private ViewPager viewpager;
    private AdapterFreqPlaces adapter;
    private TextView txtLocationMain;
    private TextView txtSpeedMain;
    private TextView txtIgnition;
    private MaterialCardView imgIgnition;
    private String drvScoreG;
    private ImageView imgAd;
    private int adIndex = 0;
    private TextView txtAd;
    Handler handlerAd;
    private TextView txtTimeCurrent;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        vpAdapter = new VPAdapter(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);

        tablayout = findViewById(R.id.tablayout);
        viewpager = findViewById(R.id.pager);
        cardAd = findViewById(R.id.cardAd);
        btnFABMsg = findViewById(R.id.btnFABMsg);
        txtAd = findViewById(R.id.txtAd);
        txtTimeCurrent = findViewById(R.id.txtTimeCurrent);

        imgIgnition = findViewById(R.id.imgIgnition);
        trackLive = findViewById(R.id.trackLive);

        trackNow = findViewById(R.id.trackNow);

        txtIgnition = findViewById(R.id.txtIgnition);
        txtLocationMain = findViewById(R.id.txtLocationMain);
        progressBarAmount = findViewById(R.id.barAmount);
        progressBarSpeed = findViewById(R.id.barSpeed);
        progressBarMileage = findViewById(R.id.barMileage);
        txtSpeedMain = findViewById(R.id.txtSpeedMain);

        btnFABMsg.setOnClickListener(this);

        trackNow.setOnClickListener(this);
        trackLive.setOnClickListener(this);

        tablayout.setupWithViewPager(viewpager);


        thisMonthFuelCbar = (CircularProgressBar) findViewById(R.id.thisMonthFuelCbar);
        thisMonthAmountCbar = (CircularProgressBar) findViewById(R.id.thisMonthAmountCbar);
        thisMonthMileageCbar = (CircularProgressBar) findViewById(R.id.thisMonthMileageCbar);

        btnCall = findViewById(R.id.btnCall);
        btnCall.setOnClickListener(this);

        btnDisc = findViewById(R.id.btnDisclaimer);
        btnDisc.setOnClickListener(this);
        ratingBar = findViewById(R.id.ratingBar);

        txtThisMonthAmount = findViewById(R.id.txtThisMonthAmount);
        txtThisMonthFuel = findViewById(R.id.txtThisMonthFuel);
        txtThisMonthMileage = findViewById(R.id.txtThisMonthMileage);

        mileageP7 = (CircularProgressBar) findViewById(R.id.mileageP7);
        amountP7 = (CircularProgressBar) findViewById(R.id.amountP7);
        fuelP7 = (CircularProgressBar) findViewById(R.id.fuelP7);
        txtBusHr = findViewById(R.id.txtBusHr);
        txtPerHr = findViewById(R.id.txtPerHr);
        txtDrvScore = findViewById(R.id.txtDrvScore);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading");

        String[] mainDate = new String[0];
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            mainDate = getDate().split("-");
        }
        String month = getMonth(mainDate[1]);


        live_location = findViewById(R.id.live_location_tv);
        live_speed = findViewById(R.id.live_speed_tv);
        loadingItem();

        String[] parts = new String[0];
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            parts = getDate().split("-");
        }
        year = parts[0];
        month = parts[1];
        currentDay = parts[2];

        YearMonth yearMonthObject = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            yearMonthObject = YearMonth.of(Integer.parseInt(year), Integer.parseInt(month));
            daysInMonth = yearMonthObject.lengthOfMonth();
        }


        getStats("101837", "65566");
//        mBarChart = findViewById(R.id.barchart);
//        mBarChart.setScaleEnabled(false);

//        barConfiguration();

        this.semiCircleArcProgressBar = findViewById(R.id.progressBar23);
        this.semiCircleArcProgressBar2 = findViewById(R.id.progressBar24);
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();

        Amount_card = findViewById(R.id.Amount_card);
        Mileage_card = findViewById(R.id.Mileage_card);
        Fuel_card = findViewById(R.id.Fuel_card);
        DriverScore = findViewById(R.id.driverscorecard);
        YesterdayCard = findViewById(R.id.card4);


        yesterdayFuel = findViewById(R.id.yesterdayFuel);
        yesterDayAmount = findViewById(R.id.yesterdayAmount);
        yesterDayKm = findViewById(R.id.yesterdayKm);

        past7daysFuel = findViewById(R.id.past7daysFuel);
        past7daysAmount = findViewById(R.id.past7daysAmount);
        past7daysKm = findViewById(R.id.past7daysKm);


        this.cars = (TextView) findViewById(R.id.txtSumHourly);
        this.Mscroll = (ScrollView) findViewById(R.id.main_scroll);
        txt1 = findViewById(R.id.txt1);
        txt2 = findViewById(R.id.txt2);
        txt3 = findViewById(R.id.txt3);
        txt4 = findViewById(R.id.txt4);
        txt5 = findViewById(R.id.txt5);
        txt6 = findViewById(R.id.txt6);
        txt7 = findViewById(R.id.txt7);
        car_selected = findViewById(R.id.txtSumHourly);


        user_name = (TextView) findViewById(R.id.textView25_user_name);
        this.parking = findViewById(R.id.imageView9);
        this.gas = findViewById(R.id.imageView10);
        this.resturant = (ImageView) findViewById(R.id.imageView11);
        this.banks = (ImageView) findViewById(R.id.imageView12);
        this.hospitals = (ImageView) findViewById(R.id.imageView13);
        this.carmechanic = (ImageView) findViewById(R.id.imageView14);
        this.context = this;

        postDataUsingVolley("101837", "65566");
        rvInitialization();
        getAdImage();
        getAverageUser("101837");
//        getResponse("101837");


        setDayText();
        setFragment(new MainFragment(day2));

        //looper(this.TIME_CONSTANT);
        looper2(5000, isTrue);
        initializeIDs();
        slider();

        clickListeners();
        this.Mscroll.smoothScrollTo(0, 0);
//        getResponse("231921");


        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.frag1);
        /*MapFragment mapFragment = (MapFragment) getFragmentManager() .findFragmentById(R.id.frag1);*/
        if (mapFragment == null) {
            Toast.makeText(this, "Null mapFragment", Toast.LENGTH_SHORT).show();
        }

        assert mapFragment != null;
        mapFragment.getMapAsync(this);


/** child screeen working start **/
        txt1.setOnClickListener(v -> {
            // TODO

            temp = txt1.getText().toString();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                showAlert(temp, "1");
            }


        });

        txt2.setOnClickListener(v -> {
            // TODO

            temp = txt2.getText().toString();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                showAlert(temp, "2");
            }


        });

        txt3.setOnClickListener(v -> {
            // TODO

            temp = txt3.getText().toString();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                showAlert(temp, "3");
            }


        });


        txt4.setOnClickListener(v -> {
            // TODO

            temp = txt4.getText().toString();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                showAlert(temp, "4");
            }


        });

        txt5.setOnClickListener(v -> {
            // TODO

            temp = txt5.getText().toString();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                showAlert(temp, "5");
            }


        });

        txt6.setOnClickListener(v -> {
            // TODO

            temp = txt6.getText().toString();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                showAlert(temp, "6");
            }


        });

        txt7.setOnClickListener(v -> {
            temp = txt7.getText().toString();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                showAlert(temp, "7");
            }


        });


        Amount_card.setOnClickListener(v -> {
            Intent process = new Intent(MainActivity.this, ParentAmount.class);
            process.putExtra("user_namee", updated_user_name);
            process.putExtra("car_platee", updated_car_plate);
            startActivity(process);
        });

        Fuel_card.setOnClickListener(v -> {
            Intent process = new Intent(MainActivity.this, ParentFuel.class);
            process.putExtra("user_namee", updated_user_name);
            process.putExtra("car_platee", updated_car_plate);
            startActivity(process);

        });

        Mileage_card.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent process = new Intent(MainActivity.this, ParentMileage.class);
                process.putExtra("user_namee", updated_user_name);
                process.putExtra("car_platee", updated_car_plate);
                startActivity(process);
            }
        });

        DriverScore.setOnClickListener(v -> {

            Intent process = new Intent(MainActivity.this, DriverActivity.class);
            process.putExtra("user_namee", updated_user_name);
            process.putExtra("car_platee", updated_car_plate);
            process.putExtra("score", drvScoreG);
            startActivity(process);

        });

        YesterdayCard.setOnClickListener(v -> {
            // TODO
            Intent intent = new Intent(MainActivity.this, TripDetailActivity.class);
            startActivity(intent);

        });


        final GaugeView gaugeView = (GaugeView) findViewById(R.id.gauge_view);
        gaugeView.setShowRangeValues(true);
        gaugeView.setTargetValue(0);
        final Random random = new Random();
        final CountDownTimer timer = new CountDownTimer(10000, 2) {
            @Override
            public void onTick(long millisUntilFinished) {
                gaugeView.setTargetValue(random.nextInt(5000));

            }

            @Override
            public void onFinish() {
                gaugeView.setTargetValue(0);
            }
        };
        timer.start();


        final GaugeView gaugeView2 = (GaugeView) findViewById(R.id.meter2);
        gaugeView2.setShowRangeValues(true);
        gaugeView2.setTargetValue(0);
        final Random random2 = new Random();
        final CountDownTimer timer2 = new CountDownTimer(10000, 2) {
            @Override
            public void onTick(long millisUntilFinished) {
                gaugeView2.setTargetValue(random2.nextInt(5000));

            }

            @Override
            public void onFinish() {
                gaugeView.setTargetValue(0);
            }
        };
        timer2.start();


        /** gauges Working End **/
        SharedPreferences sh = getSharedPreferences("MySharedPref", MODE_PRIVATE);

        String fetchphone = sh.getString("Phone", "");
        String pphone = fetchphone;

        postContactForVehicleONLOAD(pphone);


        this.cars.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                progressDialog.setMessage("Requesting Data...");
                progressDialog.show();
                SharedPreferences sh = getSharedPreferences("MySharedPref", MODE_PRIVATE);

                String fetchphone = sh.getString("Phone", "");
                String pphone = fetchphone;

                postContactForVehicle(pphone);

                fill_cluster(vehicle_ID, object_ID);

            }
        });

        this.semiCircleArcProgressBar.setPercentWithAnimation(100);
        this.semiCircleArcProgressBar2.setPercentWithAnimation(100);
        this.parking.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                MainActivity.this.startActivity(new Intent(MainActivity.this.getApplicationContext(), mapref.class));
            }
        });
        this.gas.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                MainActivity.this.gassearch();
            }
        });
        this.resturant.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                MainActivity.this.resturantsearch();
            }
        });
        this.banks.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                MainActivity.this.banksearch();
            }
        });
        this.hospitals.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                MainActivity.this.hospitalssearch();
            }
        });
        this.carmechanic.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                MainActivity.this.carmechanicssearch();
            }
        });
        Ad = (VideoView) findViewById(R.id.Ad);


        thisMonthAmountCbar.setProgressWithAnimation(0f, 4000L);
        thisMonthAmountCbar.setProgressMax(200.0f);
        thisMonthAmountCbar.setProgressBarColor(ViewCompat.MEASURED_STATE_MASK);
        thisMonthAmountCbar.setProgressBarColorStart(Integer.valueOf(Color.rgb(238, 214, 0)));
        thisMonthAmountCbar.setProgressBarColorEnd(Integer.valueOf(Color.rgb(238, 214, 0)));
        thisMonthAmountCbar.setProgressBarColorDirection(CircularProgressBar.GradientDirection.TOP_TO_BOTTOM);
        thisMonthAmountCbar.setBackgroundProgressBarColor(Color.rgb(224, 224, 224));
        thisMonthAmountCbar.setBackgroundProgressBarColorStart(Integer.valueOf(Color.rgb(224, 224, 224)));
        thisMonthAmountCbar.setBackgroundProgressBarColorEnd(Integer.valueOf(Color.rgb(224, 224, 224)));
        thisMonthAmountCbar.setBackgroundProgressBarColorDirection(CircularProgressBar.GradientDirection.TOP_TO_BOTTOM);
        thisMonthAmountCbar.setProgressBarWidth(3.0f);
        thisMonthAmountCbar.setBackgroundProgressBarWidth(3.0f);
        thisMonthAmountCbar.setRoundBorder(true);
        thisMonthAmountCbar.setStartAngle(365.0f);
        thisMonthAmountCbar.setProgressDirection(CircularProgressBar.ProgressDirection.TO_RIGHT);


        thisMonthMileageCbar.setProgressWithAnimation(0f, 4000L);
        thisMonthMileageCbar.setProgressMax(200.0f);
        thisMonthMileageCbar.setProgressBarColor(ViewCompat.MEASURED_STATE_MASK);
        thisMonthMileageCbar.setProgressBarColorStart(Integer.valueOf(Color.rgb(147, 229, 48)));
        thisMonthMileageCbar.setProgressBarColorEnd(Integer.valueOf(Color.rgb(147, 229, 48)));
        thisMonthMileageCbar.setProgressBarColorDirection(CircularProgressBar.GradientDirection.TOP_TO_BOTTOM);
        thisMonthMileageCbar.setBackgroundProgressBarColor(Color.rgb(224, 224, 224));
        thisMonthMileageCbar.setBackgroundProgressBarColorStart(Integer.valueOf(Color.rgb(224, 224, 224)));
        thisMonthMileageCbar.setBackgroundProgressBarColorEnd(Integer.valueOf(Color.rgb(224, 224, 224)));
        thisMonthMileageCbar.setBackgroundProgressBarColorDirection(CircularProgressBar.GradientDirection.TOP_TO_BOTTOM);
        thisMonthMileageCbar.setProgressBarWidth(3.0f);
        thisMonthMileageCbar.setBackgroundProgressBarWidth(3.0f);
        thisMonthMileageCbar.setRoundBorder(true);
        thisMonthMileageCbar.setStartAngle(365.0f);
        thisMonthMileageCbar.setProgressDirection(CircularProgressBar.ProgressDirection.TO_RIGHT);


        thisMonthFuelCbar.setProgressWithAnimation(0f, 4000L);
        thisMonthFuelCbar.setProgressMax(200.0f);
        thisMonthFuelCbar.setProgressBarColor(ViewCompat.MEASURED_STATE_MASK);
        thisMonthFuelCbar.setProgressBarColorStart(Integer.valueOf(Color.rgb(104, 183, 205)));
        thisMonthFuelCbar.setProgressBarColorEnd(Integer.valueOf(Color.rgb(104, 183, 205)));
        thisMonthFuelCbar.setProgressBarColorDirection(CircularProgressBar.GradientDirection.TOP_TO_BOTTOM);
        thisMonthFuelCbar.setBackgroundProgressBarColor(Color.rgb(224, 224, 224));
        thisMonthFuelCbar.setBackgroundProgressBarColorStart(Integer.valueOf(Color.rgb(224, 224, 224)));
        thisMonthFuelCbar.setBackgroundProgressBarColorEnd(Integer.valueOf(Color.rgb(224, 224, 224)));
        thisMonthFuelCbar.setBackgroundProgressBarColorDirection(CircularProgressBar.GradientDirection.TOP_TO_BOTTOM);
        thisMonthFuelCbar.setProgressBarWidth(3.0f);
        thisMonthFuelCbar.setBackgroundProgressBarWidth(3.0f);
        thisMonthFuelCbar.setRoundBorder(true);
        thisMonthFuelCbar.setStartAngle(365.0f);
        thisMonthFuelCbar.setProgressDirection(CircularProgressBar.ProgressDirection.TO_RIGHT);


        past4weekchart = findViewById(R.id.barchartpast);
        past4weekchart.setScaleEnabled(false);

        barConfiguration();


        new MediaController(this).setAnchorView(this.Ad);
        this.Ad.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.iteck));
        this.Ad.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            public void onPrepared(MediaPlayer mp) {
                mp.setLooping(true);
                int videoWidth = mp.getVideoWidth();
                int videoHeight = mp.getVideoHeight();
                int width = MainActivity.this.Ad.getWidth();
                int height = MainActivity.this.Ad.getHeight();
                float scale = Math.min(10.0f, 0.4f);

                ViewGroup.LayoutParams layoutParams = MainActivity.this.Ad.getLayoutParams();

                layoutParams.height = height;
                layoutParams.width = width;

                MainActivity.this.Ad.setLayoutParams(layoutParams);
                MainActivity.this.Ad.start();
            }
        });


        amountP7.setProgressWithAnimation(/*p7AmountG*/0, 1000L);
        amountP7.setProgressMax(/*setYAmountMax(Math.round(p7AmountG)) * 7*/2000);
        amountP7.setProgressBarColor(ViewCompat.MEASURED_STATE_MASK);
//        CircularProgressBar circularProgressBar3 = yesMileageBar;
        amountP7.setProgressBarColorStart(Integer.valueOf(Color.rgb(255, 153, 51)));
        amountP7.setProgressBarColorEnd(Integer.valueOf(Color.rgb(255, 153, 51)));
        amountP7.setProgressBarColorDirection(CircularProgressBar.GradientDirection.TOP_TO_BOTTOM);
        amountP7.setBackgroundProgressBarColor(Color.rgb(224, 224, 224));
        amountP7.setBackgroundProgressBarColorStart(Integer.valueOf(Color.rgb(224, 224, 224)));
        amountP7.setBackgroundProgressBarColorEnd(Integer.valueOf(Color.rgb(224, 224, 224)));
        amountP7.setBackgroundProgressBarColorDirection(CircularProgressBar.GradientDirection.TOP_TO_BOTTOM);
        amountP7.setProgressBarWidth(7.2f);
        amountP7.setBackgroundProgressBarWidth(7.2f);
        amountP7.setRoundBorder(true);
        amountP7.setStartAngle(365.0f);
        amountP7.setProgressDirection(CircularProgressBar.ProgressDirection.TO_RIGHT);


        fuelP7.setProgressWithAnimation(/*p7AmountG*/0, 1000L);
        fuelP7.setProgressMax(/*setYAmountMax(Math.round(p7AmountG)) * 7*/2000);

        fuelP7.setProgressBarColor(ViewCompat.MEASURED_STATE_MASK);
        CircularProgressBar circularProgressBar4 = amountP7;
        fuelP7.setProgressBarColorStart(Integer.valueOf(Color.rgb(102, 204, 0)));
        fuelP7.setProgressBarColorEnd(Integer.valueOf(Color.rgb(102, 204, 0)));
        fuelP7.setProgressBarColorDirection(CircularProgressBar.GradientDirection.TOP_TO_BOTTOM);
        fuelP7.setBackgroundProgressBarColor(Color.rgb(224, 224, 224));
        fuelP7.setBackgroundProgressBarColorStart(Integer.valueOf(Color.rgb(224, 224, 224)));
        fuelP7.setBackgroundProgressBarColorEnd(Integer.valueOf(Color.rgb(224, 224, 224)));
        fuelP7.setBackgroundProgressBarColorDirection(CircularProgressBar.GradientDirection.TOP_TO_BOTTOM);
        fuelP7.setProgressBarWidth(7.2f);
        fuelP7.setBackgroundProgressBarWidth(7.2f);
        fuelP7.setRoundBorder(true);
        fuelP7.setStartAngle(365.0f);
        fuelP7.setProgressDirection(CircularProgressBar.ProgressDirection.TO_RIGHT);


        mileageP7.setProgressWithAnimation(/*p7AmountG*/0, 1000L);
        mileageP7.setProgressMax(/*setYAmountMax(Math.round(p7AmountG)) * 7*/2000);
//        mileageP7.setProgressWithAnimation(p7MileageG, 1000L);
//        mileageP7.setProgressMax(setMileageMax(Math.round(p7MileageG)) * 7);
        mileageP7.setProgressBarColor(ViewCompat.MEASURED_STATE_MASK);
        mileageP7.setProgressBarColorStart(Integer.valueOf(Color.rgb(0, 128, 255)));
        mileageP7.setProgressBarColorEnd(Integer.valueOf(Color.rgb(0, 128, 255)));
        mileageP7.setProgressBarColorDirection(CircularProgressBar.GradientDirection.TOP_TO_BOTTOM);
        mileageP7.setBackgroundProgressBarColor(Color.rgb(224, 224, 224));
        mileageP7.setBackgroundProgressBarColorStart(Integer.valueOf(Color.rgb(224, 224, 224)));
        mileageP7.setBackgroundProgressBarColorEnd(Integer.valueOf(Color.rgb(224, 224, 224)));
        mileageP7.setBackgroundProgressBarColorDirection(CircularProgressBar.GradientDirection.TOP_TO_BOTTOM);
        mileageP7.setProgressBarWidth(7.2f);
        mileageP7.setBackgroundProgressBarWidth(7.2f);
        mileageP7.setRoundBorder(true);
        mileageP7.setStartAngle(365.0f);
        mileageP7.setProgressDirection(CircularProgressBar.ProgressDirection.TO_RIGHT);


        CircularProgressBar circularProgressBar5 = fuelP7;
        progressDialog.dismiss();


    }

    private void getAverageUser(String vehicle_ID) {
        if (checkConnection()) {
            loadingDialogue.show();
            String url = "https://api.itecknologi.com/mobile/get_avg_user.php";
            RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
            StringRequest request = new StringRequest(Request.Method.POST, url, new com.android.volley.Response.Listener<String>() {

                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        vpAdapter.addFragment(new AVG_Child_Amount(jsonObject), "Amount");
                        vpAdapter.addFragment(new AVG_Child_Fuel(jsonObject), "Fuel");
                        vpAdapter.addFragment(new AVG_Child_Mileage(jsonObject), "Mileage");
                        viewpager.setAdapter(vpAdapter);

//                        jsonObject.getString()
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }, new com.android.volley.Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    Toast.makeText(MainActivity.this, "Fail to get response = " + error, Toast.LENGTH_SHORT).show();
                }
            }) {
                @Override
                protected Map<String, String> getParams() {

                    Map<String, String> params = new HashMap<String, String>();

                    params.put("Veh_Id", vehicle_ID);
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

    private int setYAmountMax(Integer integer) {
        if (integer < 500) {
            return 500;
        } else if (integer > 500 && integer < 1000) {
            return 1000;
        } else if (integer > 1000 && integer < 1500) {
            return 1500;
        } else if (integer > 1500 && integer < 2000) {
            return 2000;
        } else {
            return integer + 1000;
        }
    }

    private int setMileageMax(Integer integer) {
        if (integer < 25) {
            return 25;
        } else if (integer > 25 && integer < 50) {
            return 50;
        } else if (integer > 50 && integer < 75) {
            return 75;
        } else if (integer > 75 && integer < 100) {
            return 100;
        } else {
            return integer + 50;
        }
    }

    private int setYFuelMax(Integer integer) {
        if (integer < 5) {
            return 5;
        } else if (integer > 5 && integer < 10) {
            return 10;
        } else if (integer > 10 && integer < 15) {
            return 15;
        } else if (integer > 15 && integer < 20) {
            return 20;
        } else {
            return integer + 25;
        }

    }

    private void getAdImage() {
        if (checkConnection()) {
            String url = "https://discountworld.net/test/api/getTestBanners?token=AAGxgVnN1MF7OHO7pfBCs2tG0kUK3qrnNfDxgPvR";
            RequestQueue queue = Volley.newRequestQueue(this);
            StringRequest request = new StringRequest(Request.Method.GET, url, new com.android.volley.Response.Listener<String>() {

                @Override
                public void onResponse(String response) {
                    Log.d(TAG, "AdResponse: " + response);
                    JSONObject respObj = null;
                    try {
                        respObj = new JSONObject(response);
                        boolean success = Boolean.parseBoolean(respObj.getString("success"));
                        if (success) {
                            txtAd.setVisibility(View.GONE);
                            JSONArray data = respObj.getJSONArray("data");
                            imgArray = new ArrayList<>();
                            for (int i = 0; i < data.length(); i++) {
                                JSONObject obj = data.getJSONObject(i);
                                imgArray.add(obj.getString("banner_image_url"));
                            }
                            loadImage();

                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new com.android.volley.Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                }
            });
            queue.add(request);
        } else {
            showAlertDialogue2("No Internet",
                    "Make sure your internet is connected and try again", R.drawable.ic_wifi_off_fill);

        }
    }

    private void loadImage() {

        Toast.makeText(context, "ImageUrl" + imgArray.get(adIndex), Toast.LENGTH_SHORT).show();

        handlerAd = new Handler();
        runnableCodeAd = new Runnable() {
            @Override
            public void run() {
                adIndex++;
                switch (adIndex) {
                    case 0:
                        Glide.with(getApplicationContext())
                                .load(imgArray.get(adIndex))
                                .centerCrop()
                                .into(imgAd);
                        Animation rotate = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_from_right);
                        imgAd.startAnimation(rotate);
                        break;
                    case 1:
                        Glide.with(getApplicationContext())
                                .load(imgArray.get(adIndex))
                                .centerCrop()
                                .into(imgAd);
                        Animation rotate2 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_from_right);
                        imgAd.startAnimation(rotate2);
                        adIndex = 0;
                        break;
                }
                handlerAd.postDelayed(runnableCodeAd, 5000);
            }
        };
        handlerAd.post(runnableCodeAd);


    }

    private void postDataUsingVolley(String id_vehicle, String ObjectId) {

        if (checkConnection()) {
            loadingDialogue.show();
            String url = "https://api.itecknologi.com/mobile/get_stats.php";
            RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
            StringRequest request = new StringRequest(Request.Method.POST, url, new com.android.volley.Response.Listener<String>() {
                @RequiresApi(api = Build.VERSION_CODES.O)
                @SuppressLint({"SimpleDateFormat", "SetTextI18n"})
                @Override
                public void onResponse(String response) {
                    try {

                        JSONObject respObj = new JSONObject(response);
                        txtDrvScore.setText(respObj.getString("DrivingScrore"));
                        drvScoreG = respObj.getString("DrivingScrore");
                        frequentPlaces(respObj);


                        txtPerHr.setText(respObj.getJSONObject("HoursDriving").getString("Personal"));
                        txtBusHr.setText(respObj.getJSONObject("HoursDriving").getString("Business"));
                        ratingbar(Integer.parseInt(respObj.getString("DrivingScrore")));

                        setThisMonthFuel(respObj);
                        setThisMonthMileage(respObj);
                        setThisMonthAmount(respObj);

                        Float pkr1 = Float.valueOf(respObj.getJSONObject("FuelCost").getString("Yesterday").toString());
                        pkr1 = (float) Math.round(pkr1 * 100) / 100;
                        yesterdayAmountG = pkr1;

                        Float ltr1 = Float.valueOf(respObj.getJSONObject("Fuel").get("Yesterday").toString());
                        ltr1 = (float) Math.round(ltr1 * 100) / 100;
                        yesterdayFuelG = ltr1;

                        Float km1 = Float.valueOf(respObj.getJSONObject("Mileage").get("Yesterday").toString());
                        km1 = (float) Math.round(km1 * 100) / 100;
                        yesterdayMileageG = km1;


                        yesterdayFuel.setText(String.valueOf(ltr1)
                                .concat(" ltr"));
                        yesterDayAmount.setText(String.valueOf(pkr1).concat(" pkr"));
                        yesterDayKm.setText(String.valueOf(km1)
                                .concat(" km"));

                        CircularProgressBar yesFuelBar = (CircularProgressBar) findViewById(R.id.yesFuelBar);
                        yesFuelBar.setProgressWithAnimation(ltr1, 1000L);
                        yesFuelBar.setProgressMax(setYFuelMax(Math.round(ltr1)));
                        yesFuelBar.setProgressBarColor(ViewCompat.MEASURED_STATE_MASK);
                        yesFuelBar.setProgressBarColorStart(Integer.valueOf(Color.rgb(102, 204, 0)));
                        yesFuelBar.setProgressBarColorEnd(Integer.valueOf(Color.rgb(102, 204, 0)));
                        yesFuelBar.setProgressBarColorDirection(CircularProgressBar.GradientDirection.TOP_TO_BOTTOM);
                        yesFuelBar.setBackgroundProgressBarColor(Color.rgb(224, 224, 224));
                        yesFuelBar.setBackgroundProgressBarColorStart(Integer.valueOf(Color.rgb(224, 224, 224)));
                        yesFuelBar.setBackgroundProgressBarColorEnd(Integer.valueOf(Color.rgb(224, 224, 224)));
                        yesFuelBar.setBackgroundProgressBarColorDirection(CircularProgressBar.GradientDirection.TOP_TO_BOTTOM);
                        yesFuelBar.setProgressBarWidth(7.2f);
                        yesFuelBar.setBackgroundProgressBarWidth(7.2f);
                        yesFuelBar.setRoundBorder(true);
                        yesFuelBar.setStartAngle(365.0f);
                        yesFuelBar.setProgressDirection(CircularProgressBar.ProgressDirection.TO_RIGHT);

                        CircularProgressBar yesAmountBar = (CircularProgressBar) findViewById(R.id.yesAmountBar);
                        yesAmountBar.setProgressWithAnimation(pkr1, 1000L);
                        yesAmountBar.setProgressMax(setYAmountMax(Math.round(pkr1)));
                        yesAmountBar.setProgressBarColor(ViewCompat.MEASURED_STATE_MASK);
                        yesAmountBar.setProgressBarColorStart(Integer.valueOf(Color.rgb(255, 153, 51)));
                        yesAmountBar.setProgressBarColorEnd(Integer.valueOf(Color.rgb(255, 153, 51)));
                        yesAmountBar.setProgressBarColorDirection(CircularProgressBar.GradientDirection.TOP_TO_BOTTOM);
                        yesAmountBar.setBackgroundProgressBarColor(Color.rgb(224, 224, 224));
                        yesAmountBar.setBackgroundProgressBarColorStart(Integer.valueOf(Color.rgb(224, 224, 224)));
                        yesAmountBar.setBackgroundProgressBarColorEnd(Integer.valueOf(Color.rgb(224, 224, 224)));
                        yesAmountBar.setBackgroundProgressBarColorDirection(CircularProgressBar.GradientDirection.TOP_TO_BOTTOM);
                        yesAmountBar.setProgressBarWidth(7.2f);
                        yesAmountBar.setBackgroundProgressBarWidth(7.2f);
                        yesAmountBar.setRoundBorder(true);
                        yesAmountBar.setStartAngle(365.0f);
                        yesAmountBar.setProgressDirection(CircularProgressBar.ProgressDirection.TO_RIGHT);

                        CircularProgressBar yesMileageBar = (CircularProgressBar) findViewById(R.id.yesMileageBar);
                        yesMileageBar.setProgressWithAnimation(km1, 1000L);
                        yesMileageBar.setProgressMax(setMileageMax(Math.round(km1)));
                        yesMileageBar.setProgressBarColor(ViewCompat.MEASURED_STATE_MASK);
                        CircularProgressBar circularProgressBar2 = yesAmountBar;
                        yesMileageBar.setProgressBarColorStart(Integer.valueOf(Color.rgb(0, 128, 255)));
                        yesMileageBar.setProgressBarColorEnd(Integer.valueOf(Color.rgb(0, 128, 255)));
                        yesMileageBar.setProgressBarColorDirection(CircularProgressBar.GradientDirection.TOP_TO_BOTTOM);
                        yesMileageBar.setBackgroundProgressBarColor(Color.rgb(224, 224, 224));
                        yesMileageBar.setBackgroundProgressBarColorStart(Integer.valueOf(Color.rgb(224, 224, 224)));
                        yesMileageBar.setBackgroundProgressBarColorEnd(Integer.valueOf(Color.rgb(224, 224, 224)));
                        yesMileageBar.setBackgroundProgressBarColorDirection(CircularProgressBar.GradientDirection.TOP_TO_BOTTOM);
                        yesMileageBar.setProgressBarWidth(7.2f);
                        yesMileageBar.setBackgroundProgressBarWidth(7.2f);
                        yesMileageBar.setRoundBorder(true);
                        yesMileageBar.setStartAngle(365.0f);
                        yesMileageBar.setProgressDirection(CircularProgressBar.ProgressDirection.TO_RIGHT);


                        getMileagePast7DaysData(respObj);
                        getFuelPast7DaysData(respObj);
                        getAmountPast7DaysData(respObj);

                        getMileageThisMonthData(respObj);
                        getTripThisMonthData(respObj);
                        getFuelThisMonthData(respObj);
                        getAmountThisMonthData(respObj);

                        loadingDialogue.dismiss();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new com.android.volley.Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    Toast.makeText(MainActivity.this, "Fail to get response = " + error, Toast.LENGTH_SHORT).show();
                }
            }) {
                @Override
                protected Map<String, String> getParams() {

                    Map<String, String> params = new HashMap<String, String>();

                    params.put("veh_id", id_vehicle);
                    params.put("ObjectId", ObjectId);
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

    @SuppressLint("NotifyDataSetChanged")
    private void frequentPlaces(JSONObject respObj) {
        JSONObject freqPlaces = null;
        try {
            freqPlaces = respObj.getJSONObject("FrequentPlaces");
            freqPlacesG = respObj.getJSONObject("FrequentPlaces");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Iterator<String> keys = freqPlaces.keys();

        int count = 0;

        while (keys.hasNext()) {
            String key = keys.next();

            Object value = null;
            try {
                value = freqPlaces.get(key);

            } catch (JSONException e) {
                e.printStackTrace();
            }

            list.add(new ModelFreqPlaces(key, value.toString()));
            count++;
        }
        adapter.notifyDataSetChanged();

    }

    @SuppressLint("SetTextI18n")
    private void setThisMonthAmount(JSONObject respObj) {

        Float thisMonthMileage = null;
        try {
            thisMonthMileage = Float.valueOf(respObj.getJSONObject("FuelCost").getString("ThisMonth").toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        thisMonthMileage = thisMonthMileage / Float.parseFloat(currentDay);
        thisMonthMileage = (float) Math.round(thisMonthMileage * 100) / 100;

        thisMonthAmountCbar.setProgressWithAnimation(Math.round(thisMonthMileage) / Integer.parseInt(currentDay), 4000L);
        thisMonthAmountCbar.setProgressMax(setYAmountMax(Math.round(thisMonthMileage)) / daysInMonth);
        thisMonthMileage = (float) Math.round(thisMonthMileage * 100) / 100;
        txtThisMonthAmount.setText(thisMonthMileage.toString());

    }

    @SuppressLint("SetTextI18n")
    private void setThisMonthMileage(JSONObject respObj) {
        Float thisMonthAmount = null;
        try {
            thisMonthAmount = Float.valueOf(respObj.getJSONObject("Mileage").getString("ThisMonth").toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        thisMonthAmount = thisMonthAmount / Float.parseFloat(currentDay);
        thisMonthAmount = (float) Math.round(thisMonthAmount * 100) / 100;
        thisMonthMileageCbar.setProgressWithAnimation(Math.round(thisMonthAmount) / Integer.parseInt(currentDay), 4000L);
        thisMonthMileageCbar.setProgressMax(setMileageMax(Math.round(thisMonthAmount)) / daysInMonth);
        thisMonthAmount = (float) Math.round(thisMonthAmount * 100) / 100;
        txtThisMonthMileage.setText(thisMonthAmount.toString());
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @SuppressLint("SetTextI18n")
    private void setThisMonthFuel(JSONObject respObj) {
        Float thisMonthFuel = null;
        try {
            thisMonthFuel = Float.valueOf(respObj.getJSONObject("Fuel").getString("ThisMonth").toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        thisMonthFuel = thisMonthFuel / Float.parseFloat(currentDay);
        thisMonthFuel = (float) Math.round(thisMonthFuel * 100) / 100;

        thisMonthFuelCbar.setProgressWithAnimation(Math.round(thisMonthFuel) / Integer.parseInt(currentDay), 4000L);
        thisMonthFuelCbar.setProgressMax(setYFuelMax(Math.round(thisMonthFuel)) / daysInMonth);
        thisMonthFuel = (float) Math.round(thisMonthFuel * 100) / 100;
        txtThisMonthFuel.setText(thisMonthFuel.toString());
    }

    private void ratingbar(int score) {

        if (score > 0 && score <= 10) {
            ratingBar.setRating(0.5F);
        } else if (score >= 11 && score <= 20) {
            ratingBar.setRating(1.0F);
        } else if (score >= 21 && score <= 30) {
            ratingBar.setRating(1.5F);
        } else if (score >= 31 && score <= 40) {
            ratingBar.setRating(2.0F);
        } else if (score >= 41 && score <= 50) {
            ratingBar.setRating(2.5F);
        } else if (score >= 51 && score <= 60) {
            ratingBar.setRating(3.0F);
        } else if (score >= 61 && score <= 70) {
            ratingBar.setRating(3.5F);
        } else if (score >= 71 && score <= 80) {
            ratingBar.setRating(4.0F);
        } else if (score >= 81 && score <= 90) {
            ratingBar.setRating(4.5F);
        } else if (score >= 91 && score <= 100) {
            ratingBar.setRating(5.0F);
        } else {
            ratingBar.setRating(0.0F);
        }

    }

    public String getMonth(String month) {
        String mon = "";
        switch (month) {
            case "01":
                mon = "JAN";
                break;
            case "02":
                mon = "FEB";
                break;
            case "03":
                mon = "MAR";
                break;
            case "04":
                mon = "APR";
                break;
            case "05":
                mon = "MAY";
                break;
            case "06":
                mon = "JUN";
                break;
            case "07":
                mon = "JUL";
                break;
            case "08":
                mon = "AUG";
                break;
            case "09":
                mon = "SEP";
                break;
            case "10":
                mon = "OCT";
                break;
            case "11":
                mon = "NOV";
                break;
            case "12":
                mon = "DEC";
                break;
        }
        return mon;
    }

    private void getStats(String selectedCarVid, String selectedCarObjId) {


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.itecknologi.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);

        Call<Statss> call = retrofitAPI.getStats(selectedCarVid, selectedCarObjId);
        call.enqueue(new Callback<Statss>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(@NonNull Call<Statss> call, @NonNull Response<Statss> response) {
                s = response.body();
                assert response.body() != null;
                Log.d(TAG, "Stats: " + response.body().toString());
                assert s != null;
                if (s.isSuccess()) {


                }

            }

            @Override
            public void onFailure(Call<Statss> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });


    }


    private void getAmountPast7DaysData(JSONObject respObj) {
        JSONObject amountPast7DaysData = null;
        try {
            amountPast7DaysData = respObj.getJSONObject("FuelCost").getJSONObject("PastSeven");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Iterator<String> keys = amountPast7DaysData.keys();
        amountBar = new ArrayList<>();
        double sum = 0.0;
        int count = 0;

        while (keys.hasNext()) {

            String key = keys.next();
            Object value = null;
            try {
                value = amountPast7DaysData.get(key);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            if (Float.parseFloat(value.toString()) >= 1.0) {
                count++;
            }

            amountMap.put(key, Float.parseFloat(value.toString()));
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
                    BarEntry b = new BarEntry(i + 1, Float.parseFloat(value.toString()));
                    amountBar.add(b);
                }
            }
            System.out.println("Day of week in text:" + dayWeekText);
        }
        Float d2 = new Float(sum);
        sumAmountG = d2.intValue();
        Float pkr1 = (float) Math.round(d2 * 100) / 100;
        past7daysAmount.setText(pkr1.toString().concat(" PKR"));

        p7AmountG = pkr1;

        amountP7.setProgressWithAnimation(p7AmountG, 1000L);
        amountP7.setProgressMax(setYAmountMax(Math.round(p7AmountG)) * 7);
        double a1 = sum / count;
        Double d = new Double(a1);
        avg_amount = d.intValue();

    }

    private void getFuelPast7DaysData(JSONObject respObj) {

        JSONObject fuelPast7DaysData = null;
        try {
            fuelPast7DaysData = respObj.getJSONObject("Fuel").getJSONObject("PastSeven");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Iterator<String> keys = fuelPast7DaysData.keys();
        fuelBar = new ArrayList<>();
        double sum = 0.0;
        int count = 0;

        while (keys.hasNext()) {

            String key = keys.next();
            Object value = null;
            try {
                value = fuelPast7DaysData.get(key);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            if (Float.parseFloat(value.toString()) >= 1.0) {
                count++;
            }

            fuelMap.put(key, Float.parseFloat(value.toString()));
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
                    BarEntry b = new BarEntry(i + 1, Float.parseFloat(value.toString()));
                    fuelBar.add(b);
                }
            }
            System.out.println("Day of week in text:" + dayWeekText);
        }
        Float d2 = new Float(sum);
        sumFuelG = d2.intValue();
        Float ltr1 = (float) Math.round(d2 * 100) / 100;
        past7daysFuel.setText(ltr1.toString().concat(" LTR"));


        p7FuelG = ltr1;
        fuelP7.setProgressWithAnimation(p7FuelG, 1000L);
        fuelP7.setProgressMax(setYFuelMax(Math.round(p7FuelG)) * 7);


        double a1 = sum / count;
        Double d = new Double(a1);
        avg_fuel = d.intValue();
    }

    private void getMileageThisMonthData(JSONObject respObj) {

        String thisMonth = null;
        String lastMonth = null;
        try {
            thisMonth = respObj.getJSONObject("Mileage").getString("ThisMonth");
            lastMonth = respObj.getJSONObject("Mileage").getString("LastMonth");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        MileageThisMonthG = (int) Math.round(Double.parseDouble(thisMonth));
        MileageLastMonthG = (int) Math.round(Double.parseDouble(lastMonth));

    }

    private void getTripThisMonthData(JSONObject respObj) {

        String thisMonth = null;
        String lastMonth = null;
        try {
            thisMonth = respObj.getJSONObject("Trips").getString("ThisMonth");
            lastMonth = respObj.getJSONObject("Trips").getString("LastMonth");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        tripThisMonthG = (int) Math.round(Double.parseDouble(thisMonth));
        tripLastMonthG = (int) Math.round(Double.parseDouble(lastMonth));

    }

    private void getFuelThisMonthData(JSONObject respObj) {

        String thisMonth = null;
        String lastMonth = null;
        try {
            thisMonth = respObj.getJSONObject("Fuel").getString("ThisMonth");
            lastMonth = respObj.getJSONObject("Fuel").getString("LastMonth");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        FuelThisMonthG = (int) Math.round(Double.parseDouble(thisMonth));
        FuelLastMonthG = (int) Math.round(Double.parseDouble(lastMonth));

    }

    private void getAmountThisMonthData(JSONObject respObj) {

        String thisMonth = null;
        String lastMonth = null;
        try {
            thisMonth = respObj.getJSONObject("FuelCost").getString("ThisMonth");
            lastMonth = respObj.getJSONObject("FuelCost").getString("LastMonth");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        AmountThisMonthG = (int) Math.round(Double.parseDouble(thisMonth));
        AmountLastMonthG = (int) Math.round(Double.parseDouble(lastMonth));

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


    @SuppressLint("SimpleDateFormat")
    private void getMileagePast7DaysData(JSONObject respObj) {

        JSONObject mileagePast7DaysData = null;
        try {
            mileagePast7DaysData = respObj.getJSONObject("Mileage").getJSONObject("PastSeven");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Iterator<String> keys = mileagePast7DaysData.keys();
        mileageBar = new ArrayList<>();
        double sum = 0.0;
        int count = 0;


        while (keys.hasNext()) {

            String key = keys.next();
            Object value = null;
            try {
                value = mileagePast7DaysData.get(key);
            } catch (JSONException e) {
                e.printStackTrace();
            }

//            if (value != "0.0") {
//                count++;
//            }

            if (Float.parseFloat(value.toString()) >= 1.0) {
                count++;
            }
            mileageMap.put(key, Float.parseFloat(value.toString()));
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
                    BarEntry b = new BarEntry(i + 1, Float.parseFloat(value.toString()));
                    mileageBar.add(b);
                }
            }
            System.out.println("Day of week in text:" + dayWeekText);
        }
        Float d2 = new Float(sum);
        sumMileageG = d2.intValue();

        Float km1 = (float) Math.round(d2 * 100) / 100;

        past7daysKm.setText(km1.toString()
                .concat(" KM"));
        p7MileageG = km1;

        mileageP7.setProgressWithAnimation(p7MileageG, 1000L);
        mileageP7.setProgressMax(setMileageMax(Math.round(p7MileageG)) * 7);

        double a1 = sum / count;
        Double d = new Double(a1);
        avg_mileags = d.intValue();

    }


    //yahan sy uthaya hai
    public void gassearch() {
        Intent mapIntent = new Intent("android.intent.action.VIEW", Uri.parse("geo:24.8607,67.0011?q=gas station"));
        mapIntent.setPackage("com.google.android.apps.maps");
        try {
            startActivity(mapIntent);
        } catch (ActivityNotFoundException e) {
            try {
                startActivity(new Intent("android.intent.action.VIEW", Uri.parse("http://maps.google.com/maps?daddr= (gas station )")));
            } catch (ActivityNotFoundException e2) {
                Toast.makeText(this, "Please install a maps application", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void resturantsearch() {
        Intent mapIntent = new Intent("android.intent.action.VIEW", Uri.parse("geo:24.8607,67.0011?q=Restaurants"));
        mapIntent.setPackage("com.google.android.apps.maps");
        try {
            startActivity(mapIntent);
        } catch (ActivityNotFoundException e) {
            try {
                startActivity(new Intent("android.intent.action.VIEW", Uri.parse("http://maps.google.com/maps?daddr= (Restaurants )")));
            } catch (ActivityNotFoundException e2) {
                Toast.makeText(this, "Please install a maps application", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void banksearch() {
        Intent mapIntent = new Intent("android.intent.action.VIEW", Uri.parse("geo:24.8607,67.0011?q=Banks"));
        mapIntent.setPackage("com.google.android.apps.maps");
        try {
            startActivity(mapIntent);
        } catch (ActivityNotFoundException e) {
            try {
                startActivity(new Intent("android.intent.action.VIEW", Uri.parse("http://maps.google.com/maps?daddr= (Banks )")));
            } catch (ActivityNotFoundException e2) {
                Toast.makeText(this, "Please install a maps application", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void hospitalssearch() {
        Intent mapIntent = new Intent("android.intent.action.VIEW", Uri.parse("geo:24.8607,67.0011?q=Hospitals"));
        mapIntent.setPackage("com.google.android.apps.maps");
        try {
            startActivity(mapIntent);
        } catch (ActivityNotFoundException e) {
            try {
                startActivity(new Intent("android.intent.action.VIEW", Uri.parse("http://maps.google.com/maps?daddr= (Hospitals )")));
            } catch (ActivityNotFoundException e2) {
                Toast.makeText(this, "Please install a maps application", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void carmechanicssearch() {
        Intent mapIntent = new Intent("android.intent.action.VIEW", Uri.parse("geo:24.8607,67.0011?q=car mechanic"));
        mapIntent.setPackage("com.google.android.apps.maps");
        try {
            startActivity(mapIntent);
        } catch (ActivityNotFoundException e) {
            try {
                startActivity(new Intent("android.intent.action.VIEW", Uri.parse("http://maps.google.com/maps?daddr= (car mechanic )")));
            } catch (ActivityNotFoundException e2) {
                Toast.makeText(this, "Please install a maps application", Toast.LENGTH_SHORT).show();
            }
        }
    }


    @SuppressLint("ClickableViewAccessibility")
    private void slider() {
        mainLayout3.setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction()) {
                    case MotionEvent.ACTION_MOVE:
                        isTrue = false;
                        handler.removeCallbacks(runnableCode);
                        handler.removeCallbacksAndMessages(null);
                        event.setAction(MotionEvent.ACTION_DOWN);
                        break;

                    default:
                        isTrue = true;
                        handler.postDelayed(runnableCode, 1200);
                        break;

                }
                return false;
            }
        });
    }

    private void initializeIDs() {
        frameLayout = findViewById(R.id.frameLayoutMain);
        mainLayout = findViewById(R.id.mainID);
        mainLayout2 = findViewById(R.id.mainID2);
        mainLayout3 = findViewById(R.id.mainID3);
        Main_Layout = findViewById(R.id.MAIN_LAYOUT);
        imgAd = findViewById(R.id.imgAd);
        img1 = findViewById(R.id.img11);
        img2 = findViewById(R.id.img22);
        img3 = findViewById(R.id.img33);
        img4 = findViewById(R.id.img44);
        img5 = findViewById(R.id.img55);
        img6 = findViewById(R.id.img66);
        img7 = findViewById(R.id.img77);
        img8 = findViewById(R.id.img88);
        img9 = findViewById(R.id.img99);

    }

    private void looper2(int TIME_CONSTANT, boolean status) {
        runnableCode = new Runnable() {
            @Override
            public void run() {
                if (status) {
                    switch (count2 + 1) {
                        case 1:
                            setDefaultFragment(new MainFragment(day2));
                            updateIndicator(count2);
                            count2 = count2 + 1;
                            break;

                        case 2:
                            setDefaultFragment(new BlankFragment());
                            setFragment(new MainFragment1(String.valueOf(tripThisMonthG), String.valueOf(tripLastMonthG)));
                            updateIndicator(count2);
                            count2 = count2 + 1;
                            break;

                        case 3:
                            setDefaultFragment(new BlankFragment());
                            setFragment(new Horibar1Fragment(mileageBar, avg_mileags, sumMileageG, day2));
                            updateIndicator(count2);
                            count2 = count2 + 1;
                            break;

                        case 4:
                            setDefaultFragment(new BlankFragment());
                            setFragment(new HoribarFragment(String.valueOf(MileageThisMonthG), String.valueOf(MileageLastMonthG)));
                            updateIndicator(count2);
                            count2 = count2 + 1;
                            break;

                        case 5:
                            setDefaultFragment(new BlankFragment());
                            setFragment(new FuelFragment1(fuelBar, avg_fuel, sumFuelG, day2));
                            updateIndicator(count2);
                            count2 = count2 + 1;
                            break;

                        case 6:
                            setDefaultFragment(new BlankFragment());
                            setFragment(new FuelFragment(String.valueOf(FuelThisMonthG), String.valueOf(FuelLastMonthG)));
                            updateIndicator(count2);
                            count2 = count2 + 1;
                            break;

                        case 7:
                            setDefaultFragment(new BlankFragment());
                            setFragment(new AmountFragment(amountBar, avg_amount, sumAmountG, day2));
                            updateIndicator(count2);
                            count2 = count2 + 1;
                            break;

                        case 8:
                            setDefaultFragment(new BlankFragment());
                            setFragment(new AmountFragment1(String.valueOf(AmountThisMonthG), String.valueOf(AmountLastMonthG)));
                            updateIndicator(count2);
                            count2 = count2 + 1;
                            break;

                        case 9:
                            setDefaultFragment(new BlankFragment());
                            setFragment(new SpeedLimitFragment());
                            updateIndicator(count2);
                            count2 = count2 + 1;
                            break;

                        case 10:
                            setDefaultFragment(new BlankFragment());
                            setFragment(new p7FuelF());
                            updateIndicator(count2);
                            count2 = count2 + 1;
                            break;

                        case 11:
                            setDefaultFragment(new BlankFragment());
                            setFragment(new ParkFragment());
                            updateIndicator(count2);
                            count2 = count2 + 1;
                            break;

                        case 12:
                            setDefaultFragment(new BlankFragment());
                            setFragment(new RunningFragment());
                            updateIndicator(count2);
                            count2 = count2 + 1;
                            break;

                        case 13:
                            setDefaultFragment(new BlankFragment());
                            setFragment(new IdlingFragment());
                            updateIndicator(count2);
                            count2 = count2 + 1;
                            break;

                        case 14:
                            setDefaultFragment(new BlankFragment());
                            setFragment(new StatsFragment());
                            updateIndicator(count2);
                            count2 = count2 + 1;
                            break;

                        default:
                            setDefaultFragment(new BlankFragment());
                            setFragment(new MainFragment(day2));
                            updateIndicator(0);
                            count2 = 1;
                            break;
                    }
                    handler.postDelayed(runnableCode, TIME_CONSTANT);
                } else {
                    return;
                }
            }
        };
        handler.post(runnableCode);
    }

    @SuppressLint("ResourceAsColor")
    private void updateIndicator(int count2) {
        switch (count2) {

            case 0:
                img8.setImageResource(R.drawable.ic_baseline_circle_24);
                img1.setImageResource(R.drawable.ic_baseline_circle_dark);
                img2.setImageResource(R.drawable.ic_baseline_circle_24);
                break;

            case 1:
                img1.setImageResource(R.drawable.ic_baseline_circle_24);
                img2.setImageResource(R.drawable.ic_baseline_circle_dark);
                img3.setImageResource(R.drawable.ic_baseline_circle_24);
                break;

            case 2:
                img2.setImageResource(R.drawable.ic_baseline_circle_24);
                img3.setImageResource(R.drawable.ic_baseline_circle_dark);
                img4.setImageResource(R.drawable.ic_baseline_circle_24);
                break;

            case 3:
                img4.setImageResource(R.drawable.ic_baseline_circle_dark);
                img3.setImageResource(R.drawable.ic_baseline_circle_24);
                img5.setImageResource(R.drawable.ic_baseline_circle_24);
                break;

            case 4:
                img5.setImageResource(R.drawable.ic_baseline_circle_dark);
                img4.setImageResource(R.drawable.ic_baseline_circle_24);
                img6.setImageResource(R.drawable.ic_baseline_circle_24);
                break;

            case 5:
                img6.setImageResource(R.drawable.ic_baseline_circle_dark);
                img5.setImageResource(R.drawable.ic_baseline_circle_24);
                img7.setImageResource(R.drawable.ic_baseline_circle_24);
                break;
            case 6:
                img7.setImageResource(R.drawable.ic_baseline_circle_dark);
                img6.setImageResource(R.drawable.ic_baseline_circle_24);
                img8.setImageResource(R.drawable.ic_baseline_circle_24);
                break;
            case 7:
                img8.setImageResource(R.drawable.ic_baseline_circle_dark);
                img7.setImageResource(R.drawable.ic_baseline_circle_24);
                img1.setImageResource(R.drawable.ic_baseline_circle_24);
                break;
            case 8:
                img8.setImageResource(R.drawable.ic_baseline_circle_24);
                img9.setImageResource(R.drawable.ic_baseline_circle_dark);
                img2.setImageResource(R.drawable.ic_baseline_circle_24);
                break;

            case 9:
                img1.setImageResource(R.drawable.ic_baseline_circle_dark);
                img9.setImageResource(R.drawable.ic_baseline_circle_24);
                img2.setImageResource(R.drawable.ic_baseline_circle_24);
                break;
//
            default:
                img8.setImageResource(R.drawable.ic_baseline_circle_24);
                img1.setImageResource(R.drawable.ic_baseline_circle_dark);
                img9.setImageResource(R.drawable.ic_baseline_circle_24);
                break;

        }

    }


    private void clickListeners() {
        Main_Layout.setOnClickListener(this);
        imgAd.setOnClickListener(this);
        frameLayout.setOnClickListener(this);
        mainLayout.setOnClickListener(this);
        mainLayout2.setOnClickListener(this);
        mainLayout3.setOnClickListener(this);
    }

    private void rvInitialization() {
        recyclerView = findViewById(R.id.rv_freq);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        list = new ArrayList<>();
        adapter = new AdapterFreqPlaces(list, this, getApplicationContext());
        recyclerView.setAdapter(adapter);

    }

    private void setFragment(Fragment fragment) {
        if (!getSupportFragmentManager().isDestroyed()) {

            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.setCustomAnimations(R.anim.slide_from_right, R.anim.slide_from_right);
            fragmentTransaction.replace(R.id.frameLayoutMain, fragment);
            fragmentTransaction.commitAllowingStateLoss();
        }
    }

    private void setDefaultFragment(Fragment fragment) {
        if (!getSupportFragmentManager().isDestroyed()) {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frameLayoutMain, fragment);
            fragmentTransaction.commitAllowingStateLoss();
        }
    }

    private void setFragment2(Fragment fragment) {
        if (!getSupportFragmentManager().isDestroyed()) {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.setCustomAnimations(R.anim.slide_from_left, R.anim.slide_from_left);
            fragmentTransaction.replace(R.id.frameLayoutMain, fragment);
            fragmentTransaction.commitAllowingStateLoss();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.btnFABMsg:
                Intent intent2 = new Intent(getApplicationContext(), MessageActivity.class);
                startActivity(intent2);
                break;

            case R.id.imgAd:
                Intent webIntent = new Intent(getApplicationContext(), WebActivity.class);
                startActivity(webIntent);
                break;


            case R.id.trackNow:
                Toast.makeText(context, "TrackNowClicked", Toast.LENGTH_SHORT).show();
                //trackNow
//                Intent trackNow = new Intent(getApplicationContext(), MessageActivity.class);
//                startActivity(trackNow);
                break;
            case R.id.trackLive:
                Toast.makeText(context, "TrackLiveClicked", Toast.LENGTH_SHORT).show();
                //trackNow
//                Intent trackNow = new Intent(getApplicationContext(), MessageActivity.class);
//                startActivity(trackNow);


                break;

            case R.id.mainID2:
                rightClick();
                break;

            case R.id.mainID:
                leftClick();
                break;


            case R.id.btnCall:
                String phone = "+9221111148325";
                Intent intentto = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null));
                startActivity(intentto);
                break;

            case R.id.btnDisclaimer:
                new SimpleTooltip.Builder(this)
                        .anchorView(v)
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


    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;

        mMap.clear();
        myCarLocation = new LatLng(locationX, locationY);    //new LatLng(locationY, locationX);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myCarLocation, 16f));
        mMap.setBuildingsEnabled(true);


        markerOptions = new MarkerOptions().position(myCarLocation).title("Your Car")
                .icon(bitmapDescriptorFromVector(this, vehicleColor)).rotation(angle);
        mMarker = mMap.addMarker(markerOptions);

    }

    private void leftClick() {
        setFragment(new BlankFragment());
        handler.removeCallbacks(runnableCode);
        handler.removeCallbacksAndMessages(null);
        switch (count2 - 1) {
            case 1:
                setFragment2(new MainFragment(day2));
                updateIndicator(0);
                count2 = count2 - 1;
                break;
            case 2:
                setFragment2(new MainFragment1(String.valueOf(tripThisMonthG), String.valueOf(tripLastMonthG)));
                updateIndicator(count2 - 2);
                count2 = count2 - 1;
                break;

            case 3:
                setFragment2(new Horibar1Fragment(mileageBar, avg_mileags, sumMileageG, day2));
                updateIndicator(count2 - 2);
                count2 = count2 - 1;
                break;

            case 4:
                setFragment2(new HoribarFragment(String.valueOf(MileageThisMonthG), String.valueOf(MileageLastMonthG)));
                updateIndicator(count2 - 2);
                count2 = count2 - 1;
                break;

            case 5:
                setFragment2(new FuelFragment1(fuelBar, avg_fuel, sumFuelG, day2));
                updateIndicator(count2 - 2);
                count2 = count2 - 1;
                break;

            case 6:
                setFragment2(new FuelFragment(String.valueOf(FuelThisMonthG), String.valueOf(FuelLastMonthG)));
                updateIndicator(count2 - 2);
                count2 = count2 - 1;
                break;

            case 7:
                setFragment2(new AmountFragment(amountBar, avg_amount, sumAmountG, day2));
                updateIndicator(count2 - 2);
                count2 = count2 - 1;
                break;

            case 8:
                setFragment2(new AmountFragment1(String.valueOf(AmountThisMonthG), String.valueOf(AmountLastMonthG)));
                updateIndicator(count2);
                count2 = count2 - 1;
                break;

            case 9:
                setFragment2(new SpeedLimitFragment());
                updateIndicator(count2);
                count2 = count2 - 1;
                break;

            default:
                setFragment2(new AmountFragment1(String.valueOf(AmountThisMonthG), String.valueOf(AmountLastMonthG)));
                updateIndicator(7);
                count2 = 9;
                break;
        }
        handler.postDelayed(runnableCode, 5000);

    }

    private void rightClick() {
        setFragment(new BlankFragment());
        handler.removeCallbacks(runnableCode);
        handler.removeCallbacksAndMessages(null);
        switch (count2 + 1) {
            case 1:
                setFragment(new MainFragment(day2));
                updateIndicator(count2);
                count2 = count2 + 1;
                break;
            case 2:
                setFragment(new MainFragment1(String.valueOf(tripThisMonthG), String.valueOf(tripLastMonthG)));
                updateIndicator(count2);
                count2 = count2 + 1;
                break;

            case 3:
                setFragment(new Horibar1Fragment(mileageBar, avg_mileags, sumMileageG, day2));
                updateIndicator(count2);
                count2 = count2 + 1;
                break;

            case 4:
                setFragment(new HoribarFragment(String.valueOf(MileageThisMonthG), String.valueOf(MileageLastMonthG)));
                updateIndicator(count2);
                count2 = count2 + 1;
                break;

            case 5:
                setFragment(new FuelFragment1(fuelBar, avg_fuel, sumFuelG, day2));
                updateIndicator(count2);
                count2 = count2 + 1;
                break;

            case 6:
                setFragment(new FuelFragment(String.valueOf(FuelThisMonthG), String.valueOf(FuelLastMonthG)));
                updateIndicator(count2);
                count2 = count2 + 1;
                break;

            case 7:
                setFragment(new AmountFragment(amountBar, avg_amount, sumAmountG, day2));
                updateIndicator(count2);
                count2 = count2 + 1;
                break;

            case 8:
                setFragment(new AmountFragment1(String.valueOf(AmountThisMonthG), String.valueOf(AmountLastMonthG)));
                updateIndicator(count2);
                count2 = count2 + 1;
                break;

            case 9:
                setFragment(new SpeedLimitFragment());
                updateIndicator(count2);
                count2 = count2 + 1;
                break;

            default:
                setFragment(new MainFragment(day2));
                updateIndicator(0);
                count2 = 1;
                break;
        }
        handler.postDelayed(runnableCode, 5000);
    }

    private void postContactForVehicle(String a) {
        if (checkConnection()) {
            String vehicle_list;
            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://api.itecknologi.com/mobile/loadcustomerdata.php/")
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();

            get_vehicle_reg retrofitAPI = retrofit.create(get_vehicle_reg.class);

            get_vehicle_data_model modal = new get_vehicle_data_model(a);

            if (modal.equals(null) && modal.equals("")) {
                Toast.makeText(MainActivity.this, "Modal Is Empty", Toast.LENGTH_LONG).show();

            } else {

                HashMap<String, String> fields = new HashMap<>();
                String c = a;
                fields.put("contact", c);
                Call<get_vehicle_data_model> call = retrofitAPI.createComment(fields);

                call.enqueue(new Callback<get_vehicle_data_model>() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onResponse(Call<get_vehicle_data_model> call, retrofit2.Response<get_vehicle_data_model> response) {
                        loadingDialogue.dismiss();

                        get_vehicle_data_model responseFromAPI = response.body();

                        List<get_vehicle_data_model.VehicleValue> a = responseFromAPI.getVehicle();
                        List<String> temp = new ArrayList<>();

                        int count = 0;
                        for (int i = 0; i < a.size(); i++) {
                            count++;

                        }
                        final String[] listItems = new String[count];

                        for (int i = 0; i < a.size(); i++) {
                            get_vehicle_data_model.VehicleValue a1 = a.get(i);

                            listItems[i] = a1.getVehReg();
                            final String[] vehicle_det = new String[2];
                            vehicle_det[0] = a1.getObjectId();
                            vehicle_det[1] = a1.getVehicleId();
                            Veh_Id = a1.getVehicleId();
                            ObjectId = a1.getObjectId();

                            vehicledetails.put(a1.getVehReg(), vehicle_det);

                        }

                        String t = String.join(",", temp);
                        String responseString = "Response Code:" + response.code() + "\n" + "Response:" + responseFromAPI.getSuccess() + "\n" + "Name:" + responseFromAPI.getName() + "\n" + "Car:" + t;//

                        String cust_name = responseFromAPI.getName();
                        user_name.setText(cust_name);
                        updated_user_name = cust_name;
                        String vehicle_list = t;

                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
                        alertDialog.setTitle("Choose vehicle");

                        alertDialog.setSingleChoiceItems(listItems, 0, new DialogInterface.OnClickListener() {
                            @SuppressLint("SetTextI18n")
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                //vehicle_id or obj_id utha rahy hain **

                                car_selected.setText(listItems[which]);
                                updated_car_plate = car_selected.getText().toString();
                                String obj_id = vehicledetails.get(listItems[which])[0];
                                String veh_id = vehicledetails.get(listItems[which])[1];
//                                Toast.makeText(MainActivity.this, "Veh_id:" + veh_id + "Obj_id:" + obj_id, Toast.LENGTH_SHORT).show();
                                //saving values to global variable.
                                vehicle_ID = veh_id;
                                object_ID = obj_id;
                            }
                        });


                        alertDialog.setPositiveButton((CharSequence) "OK", (DialogInterface.OnClickListener) new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                //onClick ki working aygi idhr.
                                dialog.dismiss();
                            }
                        });


                        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });


                        AlertDialog customAlertDialog = alertDialog.create();
                        customAlertDialog.show();

                        if (responseFromAPI.getSuccess().equals("true")) {
//                            Toast.makeText(MainActivity.this, "Done", Toast.LENGTH_SHORT).show();
                        } else
                            Toast.makeText(MainActivity.this, "Something went wrong.", Toast.LENGTH_SHORT).show();
                        return;
                    }


                    @Override
                    public void onFailure(Call<get_vehicle_data_model> call, Throwable t) {
                        loadingDialogue.dismiss();
                        Toast.makeText(MainActivity.this, "Error Found:" + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        } else {
            showAlertDialogue2("No Internet",
                    "Make sure your internet is connected and try again", R.drawable.ic_wifi_off_fill);
        }


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

    private void fill_cluster(String a, String b) {
        if (checkConnection()) {
            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://api.itecknologi.com/mobile/get_vehicle_latest_info.php/")
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();

            get_cluster_data retrofitAPI = retrofit.create(get_cluster_data.class);

            get_cluster_data_model modal = new get_cluster_data_model(a, b);


            if (modal.equals(null) && modal.equals("")) {
                Toast.makeText(MainActivity.this, "Modal Is Empty", Toast.LENGTH_LONG).show();

            } else {


                HashMap<String, String> fields = new HashMap<>();
                String c = a;
                String d = b;


                fields.put("veh_id", c);
                fields.put("object_id", d);

                //Toast.makeText(MainActivity.this, "Active", Toast.LENGTH_LONG).show();
                Call<get_cluster_data_model> call = retrofitAPI.createComment(fields);

                call.enqueue(new Callback<get_cluster_data_model>() {
                    @SuppressLint("ResourceAsColor")
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onResponse(Call<get_cluster_data_model> call, retrofit2.Response<get_cluster_data_model> response) {
                        loadingDialogue.dismiss();
                        String x, y, ignition;

                        get_cluster_data_model responseFromAPI = response.body();
                        System.out.println("HEHE" + responseFromAPI.getGpsTime());
                        // output.setText(response.body().toString()+"\n");


                        HashMap<String, String> GpsTime = responseFromAPI.getGpsTime();

                        live_date = GpsTime.get("date");


                        //////////////////////////fromHereToStart

                        Float Speed = Float.parseFloat(responseFromAPI.getSpeed().toString());
                        Float Speed1 = Speed / 200;
                        float Speed3 = Speed1 * 100;

                        txtLocationMain.setText(responseFromAPI.getLocation());
                        String[] live = live_date.split(":");
                        txtTimeCurrent.setText("at: " + live[0] + ":" + live[1]);

                        live_location.setText(responseFromAPI.getLocation().toString());
                        live_speed.setText(responseFromAPI.getSpeed() + "\n" + "KM/h");
                        updated_user_name = responseFromAPI.getCustName();

                        //////////////////////////fromHereToStart
                        ignition = responseFromAPI.getIgnition().toString();
                        if (ignition.equals("1")) {
                            vehicleColor = R.drawable.green_car;
                            car_selected.setBackgroundResource(R.drawable.backk);
                            txtIgnition.setText("Moving - Ignition On");
                            txtSpeedMain.setText(responseFromAPI.getSpeed());
                            progressBar1(Math.round(Speed3), progressBarSpeed);
                            imgIgnition.setCardBackgroundColor(Color.parseColor("#198754"));
                        } else if (ignition.equals("0")) {
                            vehicleColor = R.drawable.red_car;
                            progressBar1(0, progressBarSpeed);
                            car_selected.setBackgroundResource(R.drawable.backred);
                            txtIgnition.setText("Stopped - Ignition Off");
                            txtSpeedMain.setText("0");
                            imgIgnition.setCardBackgroundColor(Color.parseColor("#B10101"));
                        } else {
                            vehicleColor = R.drawable.black_car;
                        }

                        user_name.setText(updated_user_name);
                        car_angel = responseFromAPI.getAng();
                        angle = Integer.valueOf(car_angel);
                        x = responseFromAPI.getX();
                        y = responseFromAPI.getY();
                        locationX = Double.parseDouble(y); //yeh ulta hai lat lng.
                        locationY = Double.parseDouble(x);

                        onMapReady(mMap);


                        // Storing data into SharedPreferences

                        //output.setText(responseString);


                        if (responseFromAPI != null) {
                            //Toast.makeText(MainActivity.this, "Done", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                            loadingDialogue.dismiss();
                        } else
                            Toast.makeText(MainActivity.this, "Something went wrong.", Toast.LENGTH_SHORT).show();
                        return;

                    }


                    @Override
                    public void onFailure(Call<get_cluster_data_model> call, Throwable t) {
                        loadingDialogue.dismiss();

                        Toast.makeText(MainActivity.this, "Error Found:" + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });


            }
        } else {
            loadingDialogue.dismiss();
            showAlertDialogue2("No Internet",
                    "Make sure your internet is connected and try again", R.drawable.ic_wifi_off_fill);
        }


    }

    private void postContactForVehicleONLOAD(String a) {

        if (checkConnection()) {
            String vehicle_list;
            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://api.itecknologi.com/mobile/loadcustomerdata.php/")
                    .addConverterFactory(ScalarsConverterFactory.create())

                    .addConverterFactory(GsonConverterFactory.create(gson))

                    .build();

            get_vehicle_reg retrofitAPI = retrofit.create(get_vehicle_reg.class);
            get_vehicle_data_model modal = new get_vehicle_data_model(a);

            if (modal.equals(null) && modal.equals("")) {
                Toast.makeText(MainActivity.this, "Modal Is Empty", Toast.LENGTH_LONG).show();
            } else {
                HashMap<String, String> fields = new HashMap<>();
                String c = a;


                fields.put("contact", c);
                Call<get_vehicle_data_model> call = retrofitAPI.createComment(fields);

                call.enqueue(new Callback<get_vehicle_data_model>() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onResponse(Call<get_vehicle_data_model> call, retrofit2.Response<get_vehicle_data_model> response) {

                        loadingDialogue.dismiss();
                        get_vehicle_data_model responseFromAPI = response.body();

                        List<get_vehicle_data_model.VehicleValue> a = responseFromAPI.getVehicle();
                        List<String> temp = new ArrayList<>();

                        int count = 0;
                        for (int i = 0; i < a.size(); i++) {

                            count++;

                        }
                        final String[] listItems = new String[count];

                        for (int i = 0; i < a.size(); i++) {
                            get_vehicle_data_model.VehicleValue a1 = a.get(i);

                            listItems[i] = a1.getVehReg();
                            final String[] vehicle_det = new String[2];
                            vehicle_det[0] = a1.getObjectId();
                            vehicle_det[1] = a1.getVehicleId();
                            Veh_Id = a1.getVehicleId();
                            ObjectId = a1.getObjectId();

                            vehicledetails.put(a1.getVehReg(), vehicle_det);

                        }

                        String t = String.join(",", temp);
                        String responseString = "Response Code:" + response.code() + "\n" + "Response:" + responseFromAPI.getSuccess() + "\n" + "Name:" + responseFromAPI.getName() + "\n" + "Car:" + t;//

                        String cust_name = responseFromAPI.getName();
                        user_name.setText(cust_name);
                        updated_user_name = cust_name;
                        String vehicle_list = t;


                        car_selected.setText(listItems[0]);
                        updated_car_plate = car_selected.getText().toString();
                        String obj_id = vehicledetails.get(listItems[0])[0];
                        String veh_id = vehicledetails.get(listItems[0])[1];
//                        Toast.makeText(MainActivity.this, "Veh_id:" + veh_id + "Obj_id:" + obj_id, Toast.LENGTH_SHORT).show();
                        //saving values to global variable.
                        vehicle_ID = veh_id;
                        object_ID = obj_id;
                        fill_cluster(vehicle_ID, object_ID);

                        if (responseFromAPI.getSuccess().equals("true")) {
//                            Toast.makeText(MainActivity.this, "Done", Toast.LENGTH_SHORT).show();
                        } else
                            Toast.makeText(MainActivity.this, "Something went wrong.", Toast.LENGTH_SHORT).show();
                        return;
                    }


                    @Override
                    public void onFailure(Call<get_vehicle_data_model> call, Throwable t) {
                        loadingDialogue.dismiss();
                        Toast.makeText(MainActivity.this, "Error Found:" + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });


            }
        } else {
            showAlertDialogue2("No Internet",
                    "Make sure your internet is connected and try again", R.drawable.ic_wifi_off_fill);
        }


    }

    private void barConfiguration() {

        past4weekchart.setDrawGridBackground(false);
        past4weekchart.setDrawBarShadow(false);
        past4weekchart.setGridBackgroundColor(Color.WHITE);
        past4weekchart.setTouchEnabled(true);
        past4weekchart.setPinchZoom(true);
        past4weekchart.setDoubleTapToZoomEnabled(true);
        past4weekchart.highlightValue(null);
        past4weekchart.setDoubleTapToZoomEnabled(false);
        past4weekchart.getAxisLeft().setEnabled(false);
        past4weekchart.getAxisRight().setEnabled(false);

        getData_Of_Past4week();
        BarDataSet barDataSet = new BarDataSet(arrayList, "Test");
        barDataSet.setDrawValues(false);

        past4weekchart.getAxisLeft().setDrawGridLines(false);
        past4weekchart.getXAxis().setDrawGridLines(false);
        XAxis xAxis = past4weekchart.getXAxis();

        xAxis.setAxisLineColor(getResources().getColor(R.color.transparent));

        past4weekchart.setDescription(null);    // Hide the description
        past4weekchart.getAxisLeft().setDrawLabels(true);
        past4weekchart.getAxisRight().setDrawLabels(true);
        past4weekchart.getLegend().setEnabled(false);
        past4weekchart.setTouchEnabled(false);


        BarData barData = new BarData(barDataSet);
        past4weekchart.setData(barData);
        barDataSet.setValueTextColor(Color.parseColor("#ebecf0"));
        barDataSet.setColor(Color.parseColor("#018FFB"));

        past4weekchart.getXAxis().setTextColor(R.color.dark_grey);
        past4weekchart.getXAxis().setTextSize(8f);
        barData.setBarWidth(0.75f);
        past4weekchart.getXAxis().setSpaceMax(0.2f);

        final ArrayList<String> xVals = new ArrayList<>();
        xVals.add("26 Dec,2 Jan");
        xVals.add("26 Dec,2 Jan");
        xVals.add("26 Dec,2 Jan");
        xVals.add("26 Dec,2 Jan");

        xAxis.setCenterAxisLabels(false);
        xAxis.setDrawGridLines(false);
        xAxis.setAxisMaximum(xVals.size());
        xAxis.setLabelCount(xVals.size());
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(xVals));

    }

    private void getData_Of_Past4week() {
        arrayList = new ArrayList<BarEntry>();
        arrayList.add(new BarEntry(0, 100));
        arrayList.add(new BarEntry(1, 400));
        arrayList.add(new BarEntry(2, 300));
        arrayList.add(new BarEntry(3, 40));

    }

    /**
     * New Work
     **/


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

        setTextOnButtons(days);

    }

    private void setTextOnButtons(String[] days) {
        txt1.setText(days[0]);
        txt2.setText(days[6]);
        txt3.setText(days[5]);
        txt4.setText(days[4]);
        txt5.setText(days[3]);
        txt6.setText(days[2]);
        txt7.setText(days[1]);

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void showAlert(String weekday, String tag) {

        String titleTextDialog = "";

        switch (tag.toString()) {
            case "1":
                titleTextDialog = getDate().toString();
                break;
            default:
                titleTextDialog = getPreviousDates(tag).toString();
                break;

        }

        AlertDialog.Builder alt_bld = new AlertDialog.Builder(this);
        alt_bld.setIcon(R.drawable.ic_baseline_arrow_drop_down_24);
        alt_bld.setTitle(titleTextDialog);
        String[] grpName = {"12AM - 6AM", "6AM - 12PM", "12PM - 6PM", "6PM - 12AM"};
        String finalTitleTextDialog = titleTextDialog;
        alt_bld.setSingleChoiceItems(grpName, -1, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                show2ndAlertDialog(grpName[item], finalTitleTextDialog);
                dialog.dismiss();
            }
        });

        AlertDialog alert = alt_bld.create();
        alert.show();

    }


    private void show2ndAlertDialog(String name, String finalTitleTextDialog) {

        AlertDialog.Builder alt_bld = new AlertDialog.Builder(this);
        alt_bld.setIcon(R.drawable.ic_baseline_arrow_drop_down_24);
        alt_bld.setTitle("Select any One: ");
        String[] nullArray = {};

        String[] grpName = {
                "12AM - 1AM",
                "1AM - 2AM",
                "2AM - 3AM",
                "3AM - 4AM",
                "4AM - 5AM",
                "5AM - 6AM"
        };

        String[] grpName2 = {
                "6AM - 7AM",
                "7AM - 8AM",
                "8AM - 9AM",
                "9AM - 10AM",
                "10AM - 11AM",
                "11AM - 12PM"
        };

        String[] grpName3 = {
                "12PM - 1PM",
                "1PM - 2PM",
                "2PM - 3PM",
                "3PM - 4PM",
                "4PM - 5PM",
                "5PM - 6PM"
        };

        String[] grpName4 = {
                "6PM - 7PM",
                "7PM - 8PM",
                "8PM - 9PM",
                "9PM - 10PM",
                "10PM - 11PM",
                "11PM - 12AM"
        };

        String[] grpA = {
                "00:00:00 - 01:00:00",
                "01:00:00 - 02:00:00",
                "02:00:00 - 03:00:00",
                "03:00:00 - 04:00:00",
                "04:00:00 - 05:00:00",
                "05:00:00 - 06:00:00"
        };

        String[] grpB = {
                "06:00:00 - 07:00:00",
                "07:00:00 - 08:00:00",
                "08:00:00 - 09:00:00",
                "09:00:00 - 10:00:00",
                "10:00:00 - 11:00:00",
                "11:00:00 - 12:00:00"
        };
        String[] grpC = {
                "12:00:00 - 13:00:00",
                "13:00:00 - 14:00:00",
                "14:00:00 - 15:00:00",
                "15:00:00 - 16:00:00",
                "16:00:00 - 17:00:00",
                "17:00:00 - 18:00:00"
        };

        String[] grpD = {
                "18:00:00 - 19:00:00",
                "19:00:00 - 20:00:00",
                "20:00:00 - 21:00:00",
                "21:00:00 - 22:00:00",
                "22:00:00 - 23:00:00",
                "23:00:00 - 24:00:00"
        };

        switch (name) {

            case "12AM - 6AM":
                grpName = grpName;
                nullArray = grpA;
                break;

            case "6AM - 12PM":
                grpName = grpName2;
                nullArray = grpB;
                break;

            case "12PM - 6PM":
                grpName = grpName3;
                nullArray = grpC;
                break;

            case "6PM - 12AM":
                grpName = grpName4;
                nullArray = grpD;
                break;

        }

        String[] finalNullArray = nullArray;
        String[] finalGrpName = grpName;

        alt_bld.setSingleChoiceItems(finalGrpName, -1, new DialogInterface.OnClickListener() {

            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(DialogInterface dialog, int item) {
                String toastVariable = finalNullArray[item];
                String msg = (finalGrpName[item]);

                String[] delimatedArray = toastVariable.split("-", 8);
                String delimited = delimatedArray[0];
                String delimited2 = delimatedArray[1];

                String finalToast = finalTitleTextDialog + " " + delimited + " - " + finalTitleTextDialog + " " + delimited2;
                Toast.makeText(MainActivity.this, finalToast, Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });

        alt_bld.show();

    }


    private String getPreviousDates(String tag) {

        Calendar calendar = Calendar.getInstance();

        int day = calendar.get(Calendar.DAY_OF_WEEK);

        @SuppressLint("SimpleDateFormat")
        DateFormat format4Date = new SimpleDateFormat("yyyy-MM-dd");

        calendar.setFirstDayOfWeek(day);

        //CAN be --> calendar[Calendar.DAY_OF_WEEK] = day
        calendar.set(Calendar.DAY_OF_WEEK, day);

        String days[] = new String[7];
        for (int i = 0; i < 7; i++) {
            days[i] = format4Date.format(calendar.getTime());
            calendar.add(Calendar.DAY_OF_MONTH, -1);
        }
        int temp = Integer.parseInt(tag);
        return days[temp - 1];

    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    private String getDate() {//checked

        DayOfWeek currentDay = LocalDate.now().getDayOfWeek();
        LocalDate now = LocalDate.now();
        LocalDate startOfCurrentWeek = now.with(TemporalAdjusters.previousOrSame(currentDay));

        DayOfWeek lastDayOfWeek = currentDay.minus(6);

        LocalDate printDate = startOfCurrentWeek;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        LocalDate printDate1 = printDate.plusDays(-1);
        return printDate.format(formatter);

    }


    /**
     * OLD Work
     **/
    private BitmapDescriptor bitmapDescriptorFromVector(Context context, int vector) {

        Drawable drawable = ContextCompat.getDrawable(context, vector);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }

    @Override
    protected void onResume() {
        handler.postDelayed(runnable = new Runnable() {
            public void run() {
                handler.postDelayed(runnable, delay);
                fill_cluster(vehicle_ID, object_ID);
            }
        }, delay);
        super.onResume();
    }

    @SuppressLint("MissingSuperCall")
    @Override
    protected void onPause() {
        super.onPause();
        handler.removeCallbacks(runnable); //stop handler when activity not visible
    }

    @Override
    public void onItemClicked(int position) {
        Intent intent = new Intent(getApplicationContext(), FrequentLocationActivity.class);
        intent.putExtra("loc", list.get(position).getTxtLocation());
        intent.putExtra("vId", vehicle_ID);
        intent.putExtra("ObjId", object_ID);
        intent.putExtra("freqObj", freqPlacesG.toString());
        intent.putExtra("position", 0);
        startActivity(intent);
    }
}

