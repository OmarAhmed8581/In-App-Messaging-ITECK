package com.itecknologi.iteckapp;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TimerTask;
import java.util.TreeMap;

import io.github.douglasjunior.androidSimpleTooltip.SimpleTooltip;

public class FrequentLocationActivity extends AppCompatActivity implements View.OnClickListener {
    LinearLayout btnDisc, btnCall;
    TextView txtLoc;
    private String Veh_Id, ObjectId, Location;
    ExpandableListView expandableListView;
    ExpandableListAdapter expandableListAdapter;
    List<FreqDayModel> expandableListTitle;
    TreeMap<FreqDayModel, List<FreqListModel>> expandableListDetail;
    private Dialog loadingDialogue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frequent_location);

        expandableListView = findViewById(R.id.expendableList);
        txtLoc = findViewById(R.id.txtLocation);
        btnCall = findViewById(R.id.btnCall);
        btnCall.setOnClickListener(this);

        btnDisc = findViewById(R.id.btnDisclaimer);
        btnDisc.setOnClickListener(this);
        loadingItem();

        Intent intent = getIntent();
        String f = intent.getStringExtra("freqObj");
        Veh_Id = intent.getStringExtra("vId");
        ObjectId = intent.getStringExtra("ObjId");
        Location = intent.getStringExtra("loc");
        txtLoc.setText(intent.getStringExtra("loc"));

        getDataFromAPI(Veh_Id, ObjectId, Location);


        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                Toast.makeText(
                        getApplicationContext(),
                        expandableListTitle.get(groupPosition)
                                + " -> "
                                + expandableListDetail.get(
                                expandableListTitle.get(groupPosition)).get(
                                childPosition), Toast.LENGTH_SHORT
                ).show();
                return false;
            }
        });
    }

    private void loadingItem() {
        loadingDialogue = new Dialog(this);
        loadingDialogue.setContentView(R.layout.loading);
        loadingDialogue.getWindow().setBackgroundDrawable(getDrawable(R.drawable.rounded_corner_main_activity));
        loadingDialogue.getWindow().setLayout(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        loadingDialogue.setCancelable(false);
    }

    private void getDataFromAPI(String vId, String oId, String location) {

        if (checkConnection()) {
            loadingDialogue.show();
            String url = "https://api.itecknologi.com/mobile/get_frequent_location_detail.php";
            RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
            StringRequest request = new StringRequest(Request.Method.POST, url, new com.android.volley.Response.Listener<String>() {

                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        Log.d(TAG, "onResponse: " + vId + oId + location);
                        expandableListDetail = ExpandableListDataPump.getData(jsonObject);
                        expandableListTitle = new ArrayList<FreqDayModel>(expandableListDetail.keySet());
                        expandableListAdapter = new CustomExpandableListAdapter(getApplicationContext(), expandableListTitle, expandableListDetail);
                        expandableListView.setAdapter(expandableListAdapter);

                        loadingDialogue.dismiss();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }, new com.android.volley.Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    Toast.makeText(getApplicationContext(), "Fail to get response = " + error, Toast.LENGTH_SHORT).show();
                    loadingDialogue.dismiss();

                }
            }) {
                @Override
                protected Map<String, String> getParams() {

                    Map<String, String> params = new HashMap<String, String>();

                    params.put("Veh_Id", vId);
                    params.put("ObjectId", oId);
                    params.put("Location", location);
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

    private boolean checkConnection() {
        ConnectivityManager manager = (ConnectivityManager) getApplicationContext()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        @SuppressLint("MissingPermission") NetworkInfo networkInfo = manager.getActiveNetworkInfo();

        if (null != networkInfo)
            return true;
        else
            return false;

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.btnCall:
                String phone = "+9221111148325";
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null));
                startActivity(intent);
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
}