package com.example.weatherapp.api;

import com.example.weatherapp.model.WeatherResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherApiService {
    // Endpoint untuk mendapatkan cuaca saat ini dan perkiraan
    // URL lengkap akan menjadi: http://api.weatherapi.com/v1/forecast.json?key=YOUR_API_KEY&q=LOCATION&days=DAYS
    @GET("v1/forecast.json")
    Call<WeatherResponse> getWeatherForecast(
            @Query("key") String apiKey, // Parameter untuk API Key
            @Query("q") String location, // Parameter untuk lokasi (kota, zip code, lat/lon)
            @Query("days") int days      // Parameter untuk jumlah hari perkiraan (max 3 untuk free plan)
    );
}