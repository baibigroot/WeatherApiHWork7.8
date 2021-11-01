package com.example.weatherapihworknew.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.weatherapihworknew.R;
import com.example.weatherapihworknew.databinding.ActivityMapBinding;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback,
        GoogleMap.OnMapClickListener,
        GoogleMap.OnMarkerClickListener {

    private ActivityMapBinding binding;
    private GoogleMap map;
    private List<LatLng> coords = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMapBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    @Override
    public void onMapClick(@NonNull LatLng latLng) {

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.title("Android-3");
        markerOptions.alpha(0.1f);
        BitmapDescriptor bitmapDescriptor = BitmapDescriptorFactory.fromResource(R.drawable.common_full_open_on_phone);
        markerOptions.icon(bitmapDescriptor);
        markerOptions.draggable(true);
        markerOptions.anchor(0f, 0.7f);
        markerOptions.position(latLng);
        map.addMarker(markerOptions);

        coords.add(latLng);
    }

    @Override
    public boolean onMarkerClick(@NonNull Marker marker) {
        marker.remove();
        return false;
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {

        map = googleMap;
        LatLng latLng = new LatLng(42.8884469, 74.6896);
        CameraPosition cameraPosition = new CameraPosition(latLng, 1462f, 0f, 0f);
        CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition);
        map.animateCamera(cameraUpdate);
        map.setOnMapClickListener(this);
        map.setOnMarkerClickListener(this);
    }
}