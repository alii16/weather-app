package com.example.weatherapp;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.weatherapp.adapter.ForecastAdapter;
import com.example.weatherapp.model.Current;
import com.example.weatherapp.model.Forecastday;
import com.example.weatherapp.model.Location;
import com.example.weatherapp.model.WeatherResponse;
import com.google.android.material.button.MaterialButton;
import com.google.gson.Gson;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class WeatherDetailActivity extends AppCompatActivity {

    private TextView tvLocationNameDetail;
    private ImageView ivCurrentWeatherIcon;
    private TextView tvCurrentTemp;
    private TextView tvCurrentCondition;
    private TextView tvHumidity;
    private TextView tvWindSpeed;
    private TextView tvFeelsLike; // Deklarasi baru
    private TextView tvUvIndex;   // Deklarasi baru
    private TextView tvPressure;  // Deklarasi baru
    private TextView tvVisibility; // Deklarasi baru
    private TextView tvPrecipitation; // Deklarasi baru
    private RecyclerView rvForecast;
    private ForecastAdapter forecastAdapter;
    private MaterialButton btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_detail);

        // Membuat status bar & navigation bar transparan
        Window window = getWindow();
        window.getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                        View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |
                        View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
        );
        window.setStatusBarColor(Color.TRANSPARENT);
        window.setNavigationBarColor(Color.TRANSPARENT);

        // Inisialisasi Views
        tvLocationNameDetail = findViewById(R.id.tv_location_name_detail);
        ivCurrentWeatherIcon = findViewById(R.id.iv_current_weather_icon);
        tvCurrentTemp = findViewById(R.id.tv_current_temp);
        tvCurrentCondition = findViewById(R.id.tv_current_condition);
        tvHumidity = findViewById(R.id.tv_humidity);
        tvWindSpeed = findViewById(R.id.tv_wind_speed);
        tvFeelsLike = findViewById(R.id.tv_feels_like); // Inisialisasi TextView
        tvUvIndex = findViewById(R.id.tv_uv_index);     // Inisialisasi TextView
        tvPressure = findViewById(R.id.tv_pressure);    // Inisialisasi TextView
        tvVisibility = findViewById(R.id.tv_visibility); // Inisialisasi TextView
        tvPrecipitation = findViewById(R.id.tv_precipitation); // Inisialisasi TextView
        rvForecast = findViewById(R.id.rv_forecast);
        btnBack = findViewById(R.id.btn_back);

        // Menyiapkan tombol kembali
        btnBack.setOnClickListener(v -> finish());

        // Menerima data dari Intent
        String weatherJson = getIntent().getStringExtra("WEATHER_DATA_JSON");
        String locationName = getIntent().getStringExtra("LOCATION_NAME"); // Anda mungkin tidak lagi membutuhkan ini jika nama lokasi diambil dari weatherJson

        if (weatherJson != null) {
            Gson gson = new Gson();
            WeatherResponse weatherResponse = gson.fromJson(weatherJson, WeatherResponse.class);
            displayWeatherData(weatherResponse);
        } else {
            Toast.makeText(this, getString(R.string.error_load_failed), Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void displayWeatherData(WeatherResponse weatherResponse) {
        if (weatherResponse == null) {
            Toast.makeText(this, getString(R.string.error_data_null), Toast.LENGTH_SHORT).show();
            return;
        }

        // Tampilkan Data Cuaca Saat Ini
        Location location = weatherResponse.getLocation();
        Current current = weatherResponse.getCurrent();

        if (location != null) {
            tvLocationNameDetail.setText(location.getName() + ", " + location.getCountry());
        }

        if (current != null) {
            tvCurrentTemp.setText(String.format(Locale.getDefault(), "%.0f°C", current.getTempC()));
            if (current.getCondition() != null) {
                String localizedCondition = getLocalizedWeatherCondition(current.getCondition().getText());
                tvCurrentCondition.setText(localizedCondition);

                Glide.with(this)
                        .load(current.getCondition().getIcon())
                        .placeholder(R.drawable.baseline_wb_cloudy_24) // Placeholder default
                        .error(R.drawable.broken) // Error icon
                        .into(ivCurrentWeatherIcon);
            }

            // Menggunakan string dari resources untuk Humidity
            tvHumidity.setText(String.format(Locale.getDefault(), getString(R.string.label_humidity), current.getHumidity()));
            // Menggunakan string dari resources untuk Wind Speed
            tvWindSpeed.setText(String.format(Locale.getDefault(), getString(R.string.label_wind_speed), current.getWindKph()));

            // NEW: Set additional weather details
            tvFeelsLike.setText(String.format(Locale.getDefault(), getString(R.string.label_feels_like), current.getFeelslikeC()));
            tvUvIndex.setText(String.format(Locale.getDefault(), getString(R.string.label_uv_index), current.getUv()));
            tvPressure.setText(String.format(Locale.getDefault(), getString(R.string.label_pressure), current.getPressureMb()));
            tvVisibility.setText(String.format(Locale.getDefault(), getString(R.string.label_visibility), current.getVisKm()));
            tvPrecipitation.setText(String.format(Locale.getDefault(), getString(R.string.label_precipitation), current.getPrecipMm()));

            // Tampilkan Perkiraan Cuaca 3 Hari
            if (weatherResponse.getForecast() != null && weatherResponse.getForecast().getForecastday() != null) {
                List<Forecastday> forecastdayList = weatherResponse.getForecast().getForecastday();
                // Menggunakan constructor dengan Context
                forecastAdapter = new ForecastAdapter(this, forecastdayList);
                rvForecast.setLayoutManager(new LinearLayoutManager(this));
                rvForecast.setAdapter(forecastAdapter);
            } else {
                Log.e("WeatherDetailActivity", "Forecast data is null or empty.");
                // Menggunakan string dari resources
                Toast.makeText(this, getString(R.string.error_forecast_not_available), Toast.LENGTH_SHORT).show();
            }
        }
    }

    // Metode untuk lokalisasi kondisi cuaca
    private String getLocalizedWeatherCondition(String englishCondition) {
        switch (englishCondition) {
            case "Clear":
                return getString(R.string.condition_clear);
            case "Partly cloudy":
                return getString(R.string.condition_partly_cloudy);
            case "Cloudy":
                return getString(R.string.condition_cloudy);
            case "Overcast":
                return getString(R.string.condition_overcast);
            case "Mist":
                return getString(R.string.condition_mist);
            case "Patchy rain possible":
                return getString(R.string.condition_patchy_rain_possible);
            case "Patchy snow possible":
                return getString(R.string.condition_patchy_snow_possible);
            case "Patchy sleet possible":
                return getString(R.string.condition_patchy_sleet_possible);
            case "Patchy freezing drizzle possible":
                return getString(R.string.condition_patchy_freezing_drizzle_possible);
            case "Thundery outbreaks possible":
                return getString(R.string.condition_thundery_outbreaks_possible);
            case "Blowing snow":
                return getString(R.string.condition_blowing_snow);
            case "Blizzard":
                return getString(R.string.condition_blizzard);
            case "Fog":
                return getString(R.string.condition_fog);
            case "Freezing fog":
                return getString(R.string.condition_freezing_fog);
            case "Patchy light drizzle":
                return getString(R.string.condition_patchy_light_drizzle);
            case "Light drizzle":
                return getString(R.string.condition_light_drizzle);
            case "Freezing drizzle":
                return getString(R.string.condition_freezing_drizzle);
            case "Heavy freezing drizzle":
                return getString(R.string.condition_heavy_freezing_drizzle);
            case "Patchy light rain":
                return getString(R.string.condition_patchy_light_rain);
            case "Light rain":
                return getString(R.string.condition_light_rain);
            case "Moderate rain at times":
                return getString(R.string.condition_moderate_rain_at_times);
            case "Moderate rain":
                return getString(R.string.condition_moderate_rain);
            case "Heavy rain at times":
                return getString(R.string.condition_heavy_rain_at_times);
            case "Heavy rain":
                return getString(R.string.condition_heavy_rain);
            case "Light freezing rain":
                return getString(R.string.condition_light_freezing_rain);
            case "Moderate or heavy freezing rain":
                return getString(R.string.condition_moderate_or_heavy_freezing_rain);
            case "Light sleet":
                return getString(R.string.condition_light_sleet);
            case "Moderate or heavy sleet":
                return getString(R.string.condition_moderate_or_heavy_sleet);
            case "Patchy light snow":
                return getString(R.string.condition_patchy_light_snow);
            case "Light snow":
                return getString(R.string.condition_light_snow);
            case "Patchy moderate snow":
                return getString(R.string.condition_patchy_moderate_snow);
            case "Moderate snow":
                return getString(R.string.condition_moderate_snow);
            case "Patchy heavy snow":
                return getString(R.string.condition_patchy_heavy_snow);
            case "Heavy snow":
                return getString(R.string.condition_heavy_snow);
            case "Ice pellets":
                return getString(R.string.condition_ice_pellets);
            case "Light rain shower":
                return getString(R.string.condition_light_rain_shower);
            case "Moderate or heavy rain shower":
                return getString(R.string.condition_moderate_or_heavy_rain_shower);
            case "Torrential rain shower":
                return getString(R.string.condition_torrential_rain_shower);
            case "Light sleet showers":
                return getString(R.string.condition_light_sleet_showers);
            case "Moderate or heavy sleet showers":
                return getString(R.string.condition_moderate_or_heavy_sleet_showers);
            case "Light snow showers":
                return getString(R.string.condition_light_snow_showers);
            case "Moderate or heavy snow showers":
                return getString(R.string.condition_moderate_or_heavy_snow_showers);
            case "Light showers of ice pellets":
                return getString(R.string.condition_light_showers_of_ice_pellets);
            case "Moderate or heavy showers of ice pellets":
                return getString(R.string.condition_moderate_or_heavy_showers_of_ice_pellets);
            case "Patchy light rain with thunder":
                return getString(R.string.condition_patchy_light_rain_with_thunder);
            case "Moderate or heavy rain with thunder":
                return getString(R.string.condition_moderate_or_heavy_rain_with_thunder);
            case "Patchy light snow with thunder":
                return getString(R.string.condition_patchy_light_snow_with_thunder);
            case "Moderate or heavy snow with thunder":
                return getString(R.string.condition_moderate_or_heavy_snow_with_thunder);
            default:
                return englishCondition;
        }
    }
}