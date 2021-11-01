package com.example.weatherapihworknew.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.weatherapihworknew.R;
import com.example.weatherapihworknew.databinding.ActivityMapBinding;
import com.example.weatherapihworknew.utils.Constants;
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
import com.google.gson.Gson;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback,
        GoogleMap.OnMapClickListener,
        GoogleMap.OnMarkerClickListener {

    private GoogleMap map;
    private LatLng coords = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        setupListeners();
    }

    private void setupListeners() {
        findViewById(R.id.backTo_btn).setOnClickListener(v -> {
            if (coords!=null) {
                Intent intent = new Intent(this, MainActivity.class);
                intent.putExtra(Constants.mapResultKey, new Gson().toJson(coords));
                setResult(RESULT_OK, intent);
                finish();
            }
            else Toast.makeText(this,"Click to the map!",Toast.LENGTH_SHORT).show();
        });

    }

    @Override
    public void onMapClick(@NonNull LatLng latLng) {

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.title("Android-3");
        markerOptions.draggable(true);
        markerOptions.anchor(0f, 0.7f);
        markerOptions.position(latLng);
        map.addMarker(markerOptions);

        coords = latLng;
        Log.e("TAG", "onMapClick: "+coords );
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