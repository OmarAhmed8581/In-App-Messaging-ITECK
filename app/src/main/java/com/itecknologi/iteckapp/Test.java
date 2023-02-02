package com.itecknologi.iteckapp;

import android.location.Location;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;


public class Test extends AppCompatActivity implements OnMapReadyCallback {
    private GoogleMap mMap;
    private Location lastlocation;
    private Marker currentLocationMarker;

    int PROXIMITY_RADIUS = 10000;
    double latitude, longitude;


    public static final int REQUEST_LOCATION_CODE = 99;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

       /* binding = ActivityTestBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());*/

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.frag1);
        /*MapFragment mapFragment = (MapFragment) getFragmentManager() .findFragmentById(R.id.frag1);*/
        if (mapFragment == null) {
            Toast.makeText(this, "Null mapFragment", Toast.LENGTH_SHORT).show();
        }

        assert mapFragment != null;
        mapFragment.getMapAsync(this);


    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }
}