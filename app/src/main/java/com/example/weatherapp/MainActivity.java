package com.example.weatherapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import android.graphics.Color;
import android.view.Window;


import com.airbnb.lottie.LottieAnimationView;
import com.example.weatherapp.api.WeatherApiService;
import com.example.weatherapp.model.WeatherResponse;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    // Deklarasi view
    private TextInputEditText etLocation;
    private MaterialButton btnGetWeather;
    private TextInputLayout tilLocationInput;
    private LottieAnimationView lottieLoadingAnimation;

    // --- API KEY LANGSUNG DI SINI ---
    private static final String API_KEY = "YOUR_API_KEY";

    // Base URL untuk WeatherAPI.com
    private static final String BASE_URL = "https://api.weatherapi.com/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Membuat status bar & navigation bar transparan
        Window window = getWindow();
        window.getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                        View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |
                        View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
        );
        window.setStatusBarColor(Color.TRANSPARENT);
        window.setNavigationBarColor(Color.TRANSPARENT);

        setContentView(R.layout.activity_main);
        setContentView(R.layout.activity_main);

         // Inisialisasi
        etLocation = findViewById(R.id.et_location);
        btnGetWeather = findViewById(R.id.btn_get_weather);  // Inisialisasi button
        tilLocationInput = findViewById(R.id.til_location_input); // Inisialisasi TextInputLayout
        lottieLoadingAnimation = findViewById(R.id.lottie_loading_animation); // Inisialisasi LottieAnimationView

        btnGetWeather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String location = etLocation.getText().toString().trim();
                if (TextUtils.isEmpty(location)) {
                    Toast.makeText(MainActivity.this, "Please enter a city or location.", Toast.LENGTH_SHORT).show();
                    return;
                }
                getWeatherData(location);
            }
        });
    }

    private void showLoading() {
        lottieLoadingAnimation.setVisibility(View.VISIBLE);
        lottieLoadingAnimation.playAnimation(); // Mulai animasi Lottie
        btnGetWeather.setEnabled(false); // Nonaktifkan tombol
        tilLocationInput.setEnabled(false); // Nonaktifkan input
        etLocation.setEnabled(false); // Nonaktifkan EditText
    }

    private void hideLoading() {
        lottieLoadingAnimation.setVisibility(View.GONE);
        lottieLoadingAnimation.pauseAnimation(); // Hentikan animasi Lottie
        btnGetWeather.setEnabled(true); // Aktifkan kembali tombol
        tilLocationInput.setEnabled(true); // Aktifkan kembali input
        etLocation.setEnabled(true); // Aktifkan kembali EditText
    }

    private void getWeatherData(String location) {
        showLoading();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        WeatherApiService service = retrofit.create(WeatherApiService.class);

        service.getWeatherForecast(API_KEY, location, 3).enqueue(new Callback<WeatherResponse>() {
            @Override
            public void onResponse(Call<WeatherResponse> call, Response<WeatherResponse> response) {
                hideLoading();
                if (response.isSuccessful() && response.body() != null) {
                    WeatherResponse weatherResponse = response.body();
                    Log.d("WeatherApp", "API Response: " + new Gson().toJson(weatherResponse));

                    Intent intent = new Intent(MainActivity.this, WeatherDetailActivity.class);
                    String weatherJson = new Gson().toJson(weatherResponse);
                    intent.putExtra("WEATHER_DATA_JSON", weatherJson);
                    intent.putExtra("LOCATION_NAME", weatherResponse.getLocation().getName());
                    startActivity(intent);

                } else {
                    String errorMsg = "Failed to get weather data.";
                    if (response.errorBody() != null) {
                        try {
                            String rawError = response.errorBody().string();
                            Log.e("WeatherApp", "API Error Raw: " + rawError);
                        
                            if (rawError.contains("No matching location found")) {
                                errorMsg = "Location not found. Please try again.";
                            } else {
                                errorMsg = "Error: " + rawError;
                            }
                        } catch (Exception e) {
                            Log.e("WeatherApp", "Error reading error body: " + e.getMessage());
                            errorMsg += " (Unknown error body)";
                        }
                    } else {
                        errorMsg += " HTTP Code: " + response.code();
                    }
                    Toast.makeText(MainActivity.this, errorMsg, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<WeatherResponse> call, Throwable t) {
                hideLoading();
                Log.e("WeatherApp", "API Call Failed: " + t.getMessage(), t);
                Toast.makeText(MainActivity.this, "Network error. Please check your internet connection.", Toast.LENGTH_LONG).show();
            }
        });
    }
}
