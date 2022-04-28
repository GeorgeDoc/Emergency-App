package com.example.emergencyapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsFragment extends Fragment implements  OnMapReadyCallback {

    private GoogleMap mMap;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        //05/11
        //String location = getArguments().getString("marker");
        //LatLng coords = getArguments().getParcelable("coordinates");
        //return inflater.inflate(R.layout.fragment_maps, container, false);

        //Initialize fragment maps view
        View view = inflater.inflate(R.layout.fragment_maps, container, false);

        //initialize map fragment
        SupportMapFragment supportMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.fragment_Map);

        //async map
        supportMapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                //when map is loaded
                googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                    @Override
                    public void onMapClick(LatLng latLng) {
                        //initialize marker options
                        MarkerOptions markerOptions = new MarkerOptions();
                        //set marker position
                        markerOptions.position(latLng);
                        //set title of marker
                        markerOptions.title("Γεωγραφικό πλάτος & μήκος: " + latLng.latitude + " : " + latLng.longitude);
                        //remove all marker
                        googleMap.clear();
                        //animating to zoom the marker
                        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
                        //add marker on map
                        googleMap.addMarker(markerOptions);
                    }
                });
            }
        });
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.fragment_Map);
        if (mapFragment != null) {
            //23 mapFragment.getMapAsync(callback);
            mapFragment.getMapAsync(this);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        //String location = args.getString("marker");
        //String location = getArguments().getString("marker"); crash

        //LatLng coords = getArguments().getParcelable("coordinates");
        LatLng coords = new LatLng(35.159, 33.338);

        mMap.addMarker(new MarkerOptions().position(coords).title("Πατήστε στο χάρτη, εάν χρειάζεται προσαρμογή της θέσης"));
        mMap.moveCamera((CameraUpdateFactory.newLatLngZoom(coords, 15)));
        mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);                                                // types that seem ok" terrain, hybrid, normal
    }
}