package com.itecknologi.iteckapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

public class mapref extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapref);

        String uri = "http://maps.google.com/maps?daddr= (" + "parkings " + ")";

        //uri gmmIntentUri = Uri.parse("geo:37.7749,-122.4194?z=10&q=restaurants");
        //Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
        //intent.setPackage("com.google.android.apps.maps");
        Uri gmmIntentUri = Uri.parse("geo:24.8607,67.0011?q=parkings");
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        //startActivity(mapIntent);
        try {
            startActivity(mapIntent);
        } catch (ActivityNotFoundException ex) {
            try {
                Intent unrestrictedIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                startActivity(unrestrictedIntent);
            } catch (ActivityNotFoundException innerEx) {
                Toast.makeText(this, "Please install a maps application", Toast.LENGTH_LONG).show();
            }
        }


    }
}