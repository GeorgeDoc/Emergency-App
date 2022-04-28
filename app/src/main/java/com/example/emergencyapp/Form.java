package com.example.emergencyapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;
import java.util.Locale;

public class Form extends AppCompatActivity implements LocationListener {                           // 1.

    private GoogleMap mMap;
    TextView tvLocation;
    Button btLocation;
    LocationManager locationManager;                                                                // 3. Initiate locationManager class to get lat, long, etc


    // NOTE: Possibly need to refresh map fragment after pressing the button
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        Intent in = getIntent();                                                                    //receive bundle
        Bundle bundle = in.getExtras();
        String type = bundle.getString("type");

        tvLocation = (TextView) findViewById(R.id.tvLocation);
        tvLocation.setText(type);
        btLocation = (Button) findViewById(R.id.form_button);

        // Runtime Permissions 26
        if (ContextCompat.checkSelfPermission(Form.this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(Form.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 100);
        }

        btLocation.setOnClickListener(new View.OnClickListener() {                                  // Button & listener to get location
            @Override
            public void onClick(View view) {
                getLocation();
            }
        });

        //05/11
        Fragment fragment = new MapsFragment();                                                     //initialize fragment

        getSupportFragmentManager()                                                                 //open fragment
                .beginTransaction()
                .replace(R.id.fragment_Map,fragment)
                .commit();
    }

    //Add permission check
    @SuppressLint("MissingPermission")
    private void getLocation() {
        try {
            locationManager = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 5, (LocationListener) Form.this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {                                     // 2.
        try {
            Toast.makeText(this, "" + location.getLatitude() + ", " + location.getLongitude(), Toast.LENGTH_LONG).show();

            Geocoder geocoder = new Geocoder(Form.this, Locale.getDefault());               //4. Add geocoder class for refers to transforming street address, or any address into lat & long
            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);  //5.
            String address = addresses.get(0).getAddressLine(0);

            tvLocation.setText(address);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


