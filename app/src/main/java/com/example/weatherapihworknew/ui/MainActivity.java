package com.example.weatherapihworknew.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.weatherapihworknew.R;
import com.example.weatherapihworknew.data.curren_weather.Weather;
import com.example.weatherapihworknew.databinding.WeatherLayoutBinding;
import com.example.weatherapihworknew.internet.RetrofitBuilder;
import com.example.weatherapihworknew.utils.Constants;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private WeatherLayoutBinding ui;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ui = WeatherLayoutBinding.inflate(getLayoutInflater());
        setContentView(ui.getRoot());

        setupListeners();

    }

    private void setupListeners() {
        getCurrentWeather();
        ui.cityLayout.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, MapActivity.class);
            startActivityForResult(intent, 1);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && data != null && resultCode == RESULT_OK) {
            getWeatherByLatLng(toLatlang(data.getStringExtra(Constants.mapResultKey)));
        }
    }

    private LatLng toLatlang(String stringExtra) {
        return new Gson().fromJson(stringExtra, LatLng.class);
    }

    private void getWeatherByLatLng(LatLng latlng) {
        RetrofitBuilder.getInstance().getWeatherByLatLng(String.valueOf(latlng.latitude), String.valueOf(latlng.longitude), Constants.api_key).enqueue(new Callback<Weather>() {
            @Override
            public void onResponse(Call<Weather> call, Response<Weather> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.e("tag", "success :" + response.body());
                    setData(response.body());
                } else {
                    Log.e("tag", "Error! :" + response.code());

                }
            }

            @Override
            public void onFailure(Call<Weather> call, Throwable t) {
                Log.e("tag", "Failure! :" + t.getLocalizedMessage());
            }

        });
    }


    private void getCurrentWeather() {
        RetrofitBuilder.getInstance().getCurrentWeather(Constants.city_name, Constants.api_key).enqueue(new Callback<Weather>() {
            @Override
            public void onResponse(Call<Weather> call, Response<Weather> response) {

                if (response.isSuccessful() && response.body() != null) {
                    setData(response.body());
                } else {
                    Log.e("tag", "error: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<Weather> call, Throwable t) {
                Log.e("tag", "failure: " + t.getLocalizedMessage());
            }
        });
    }

    private void setData(Weather currentWeather) {
        String imageUri, pressure, humidity, speedWind;
        ui.dateTextView.setText(getDate(currentWeather.dt));
        ui.locationTextView.setText(currentWeather.name);
        imageUri = "http://openweathermap.org/img/wn/" + currentWeather.weather.get(0).icon + "@2x.png";
        Picasso.get().load(imageUri).resize(70, 70).into(ui.weatherImageView);
        ui.weatherTextView.setText(currentWeather.weather.get(0).main);
        ui.weatherImageView.setContentDescription(currentWeather.weather.get(0).description);
        ui.degTextView.setText(getCelsius(currentWeather.main.temp));
        ui.degUpTextView.setText(String.valueOf(getCelsius(currentWeather.main.tempMax) + getString(R.string.deg_up)));
        ui.degDownTextView.setText(String.valueOf(getCelsius(currentWeather.main.tempMin) + getString(R.string.deg_down)));
        humidity = currentWeather.main.humidity + getResources().getString(R.string.percentage);
        ui.humidityTextView.setText(humidity);
        pressure = currentWeather.main.pressure + getResources().getString(R.string.mbar);
        ui.pressureTextView.setText(pressure);
        speedWind = currentWeather.wind.speed + getResources().getString(R.string.m_s);
        ui.windTextView.setText(speedWind);

    }


    private String getCelsius(Double kelvin) {
        return new DecimalFormat("##").format(kelvin - 273.15);
    }

    private String getDate( Long dt) {
        return DateUtils.formatDateTime(MainActivity.this,dt*1000,DateUtils.FORMAT_SHOW_WEEKDAY|DateUtils.FORMAT_SHOW_TIME|DateUtils.FORMAT_SHOW_DATE|DateUtils.FORMAT_ABBREV_MONTH);
    }

}