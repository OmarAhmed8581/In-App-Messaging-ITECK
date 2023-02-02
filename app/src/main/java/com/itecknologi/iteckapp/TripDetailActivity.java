package com.itecknologi.iteckapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Marker;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


import java.util.ArrayList;
import java.util.List;


public class TripDetailActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {

    private GoogleMap mMap;


    private BottomSheetBehavior bottomSheetBehavior;
    LayoutInflater inflater;
    ViewGroup container;
    private Location lastlocation;
    private Marker currentLocationMarker;

    int PROXIMITY_RADIUS = 10000;
    double latitude, longitude;
    //private GoogleApiClient client;
    private RecyclerView recyclerView;
    TripAdapter adapter;
    private List<TripModel> list;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_detail);


        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.gmap);

        if (mapFragment == null) {
            Toast.makeText(this, "Null mapFragment", Toast.LENGTH_SHORT).show();
        }

        assert mapFragment != null;
        mapFragment.getMapAsync(this);

        ConstraintLayout bottomSheetLayout = findViewById(R.id.bottom_sheet);
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheetLayout);
        bottomSheetBehavior.setPeekHeight(420);

        bottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {

            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                switch (newState) {
                    case BottomSheetBehavior.STATE_HIDDEN:
//                        Toast.makeText(TripDetailActivity.this, "STATE HIDDEN", Toast.LENGTH_SHORT).show();
                        break;
                    case BottomSheetBehavior.STATE_EXPANDED:
//                        Toast.makeText(TripDetailActivity.this, "STATE Exp", Toast.LENGTH_SHORT).show();

                        break;
                    case BottomSheetBehavior.STATE_COLLAPSED:
//                        Toast.makeText(TripDetailActivity.this, "STATE Collapsed", Toast.LENGTH_SHORT).show();
                        break;

                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });


        rvInitialization();

        listData();


    }

    private void rvInitialization() {
        recyclerView = findViewById(R.id.RecyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        list = new ArrayList<>();
        adapter = new TripAdapter(list);
        recyclerView.setAdapter(adapter);
    }

    private void listData() {
        list.add(new TripModel("2:54pm", "9:10pm", "iTecknologi Group of Companies"
                , "Department of Computer Science UBIT", "21 KM", "3:01pm"));

        list.add(new TripModel("1:22pm", "10:01pm", "Clifton BLock 7 Mai Kolachi Bypass"
                , "University Of Karachi", "20 KM", "1:01am"));

        list.add(new TripModel("2:54pm", "9:10pm", "Nandos Clifton Opp. Shaheed Benazir Bhutto Park"
                , "Department of Computer Science UBIT", "21 KM", "3:01pm"));

        list.add(new TripModel("2:54pm", "9:10pm", "iTecknologi Group of Companies"
                , "Department of Computer Science UBIT", "21 KM", "3:01pm"));

        list.add(new TripModel("2:54pm", "9:10pm", "iTecknologi Group of Companies"
                , "Department of Computer Science UBIT", "21 KM", "3:01pm"));

        list.add(new TripModel("2:54pm", "9:10pm", "iTecknologi Group of Companies"
                , "Department of Computer Science UBIT", "21 KM", "3:01pm"));

    }

    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            //Initialize Google Play Services
            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (ContextCompat.checkSelfPermission(this,
                        Manifest.permission.ACCESS_FINE_LOCATION)
                        == PackageManager.PERMISSION_GRANTED) {
                    buildGoogleApiClient();
                    mMap.setMyLocationEnabled(true);
                }
            } else {
                buildGoogleApiClient();
                mMap.setMyLocationEnabled(true);
            }

        }


    }

    protected synchronized void buildGoogleApiClient() {
        //client=new GoogleApiClient.Builder(this).addConnectionCallbacks(this).addOnConnectionFailedListener(this).addApi(LocationServices.API).build();
        //client.connect();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override

    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {
        latitude = location.getLatitude();
        longitude = location.getLongitude();

        lastlocation = location;
        if (currentLocationMarker != null) {
            currentLocationMarker.remove();
        }
        //new entry
        Log.d("lat=", "" + latitude);

        LatLng latlng = new LatLng(location.getLatitude(), location.getLongitude());
        MarkerOptions markerOptions = new MarkerOptions();

        markerOptions.position(latlng);
        markerOptions.title("Current Location");
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));

        currentLocationMarker = mMap.addMarker(markerOptions);

        mMap.moveCamera(CameraUpdateFactory.newLatLng(latlng));
        mMap.animateCamera(CameraUpdateFactory.zoomBy(10));
        /*if (client != null)
        {
            LocationServices.FusedLocationApi.removeLocationUpdates(client, (com.google.android.gms.location.LocationListener) this);
        }*/


    }


}