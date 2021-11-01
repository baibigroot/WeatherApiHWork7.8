package com.example.weatherapihworknew.internet;

import com.example.weatherapihworknew.data.WeatherModel;
import com.example.weatherapihworknew.data.curren_weather.Weather;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherApi {

    @GET("/data/2.5/weather")
    Call<Weather> getCurrentWeather(
            @Query("q") String name,
            @Query("appid") String key);

    @GET("/data/2.5/weather?&appid=b211aae5545e8d3de75404d096930c95")
    Call<WeatherModel> getWeather(
            @Query("q") String name);

    @GET("/data/2.5/weather")
    Call<Weather> getWeatherByLatLng(
            @Query("lat") String lat,
            @Query("lon") String lon,
            @Query("appid") String apiId
    );

}
