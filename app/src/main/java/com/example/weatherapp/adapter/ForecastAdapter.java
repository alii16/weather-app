package com.example.weatherapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.weatherapp.R;
import com.example.weatherapp.model.Day;
import com.example.weatherapp.model.Forecastday;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ForecastAdapter extends RecyclerView.Adapter<ForecastAdapter.ForecastViewHolder> {

    private List<Forecastday> forecastList;
    private Context context; // Pastikan field context sudah ada

    // Perbarui konstruktor ini agar menerima Context
    public ForecastAdapter(Context context, List<Forecastday> forecastList) {
        this.context = context; // Inisialisasi context dari parameter
        this.forecastList = forecastList;
    }

    @NonNull
    @Override
    public ForecastViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Karena context sudah diinisialisasi di konstruktor,
        // Anda tidak perlu lagi mengambilnya dari parent di sini.
        View view = LayoutInflater.from(context).inflate(R.layout.item_forecast_card, parent, false);
        return new ForecastViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ForecastViewHolder holder, int position) {
        Forecastday forecastday = forecastList.get(position);
        Day day = forecastday.getDay(); // Dapatkan objek Day

        // Set Date
        holder.tvDate.setText(formatDate(forecastday.getDate()));

        // Set Min/Max Temp
        holder.tvMinMaxTemp.setText(String.format(Locale.getDefault(), "%.0f°C / %.0f°C", day.getMintempC(), day.getMaxtempC()));

        // Set Condition Text
        if (day.getCondition() != null) {
            String localizedCondition = getLocalizedWeatherCondition(day.getCondition().getText());
            holder.tvCondition.setText(localizedCondition);

            // Set Weather Icon
            Glide.with(context) // Gunakan 'context' yang telah diinisialisasi
                    .load(day.getCondition().getIcon())
                    .placeholder(R.drawable.baseline_wb_cloudy_24) // Placeholder default
                    .error(R.drawable.broken) // Error icon
                    .into(holder.ivForecastIcon);
        }
    }

    @Override
    public int getItemCount() {
        return forecastList != null ? forecastList.size() : 0;
    }

    public static class ForecastViewHolder extends RecyclerView.ViewHolder {
        TextView tvDate;
        TextView tvMinMaxTemp;
        TextView tvCondition;
        ImageView ivForecastIcon;

        public ForecastViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDate = itemView.findViewById(R.id.tv_forecast_date);
            tvMinMaxTemp = itemView.findViewById(R.id.tv_forecast_temp);
            tvCondition = itemView.findViewById(R.id.tv_forecast_condition);
            ivForecastIcon = itemView.findViewById(R.id.iv_forecast_icon);
        }
    }

    // Metode untuk lokalisasi kondisi cuaca (sama seperti di WeatherDetailActivity)
    private String getLocalizedWeatherCondition(String englishCondition) {
        switch (englishCondition) {
            case "Clear":
                return context.getString(R.string.condition_clear);
            case "Partly cloudy":
                return context.getString(R.string.condition_partly_cloudy);
            case "Cloudy":
                return context.getString(R.string.condition_cloudy);
            case "Overcast":
                return context.getString(R.string.condition_overcast);
            case "Mist":
                return context.getString(R.string.condition_mist);
            case "Patchy rain possible":
                return context.getString(R.string.condition_patchy_rain_possible);
            case "Patchy snow possible":
                return context.getString(R.string.condition_patchy_snow_possible);
            case "Patchy sleet possible":
                return context.getString(R.string.condition_patchy_sleet_possible);
            case "Patchy freezing drizzle possible":
                return context.getString(R.string.condition_patchy_freezing_drizzle_possible);
            case "Thundery outbreaks possible":
                return context.getString(R.string.condition_thundery_outbreaks_possible);
            case "Blowing snow":
                return context.getString(R.string.condition_blowing_snow);
            case "Blizzard":
                return context.getString(R.string.condition_blizzard);
            case "Fog":
                return context.getString(R.string.condition_fog);
            case "Freezing fog":
                return context.getString(R.string.condition_freezing_fog);
            case "Patchy light drizzle":
                return context.getString(R.string.condition_patchy_light_drizzle);
            case "Light drizzle":
                return context.getString(R.string.condition_light_drizzle);
            case "Freezing drizzle":
                return context.getString(R.string.condition_freezing_drizzle);
            case "Heavy freezing drizzle":
                return context.getString(R.string.condition_heavy_freezing_drizzle);
            case "Patchy light rain":
                return context.getString(R.string.condition_patchy_light_rain);
            case "Light rain":
                return context.getString(R.string.condition_light_rain);
            case "Moderate rain at times":
                return context.getString(R.string.condition_moderate_rain_at_times);
            case "Moderate rain":
                return context.getString(R.string.condition_moderate_rain);
            case "Heavy rain at times":
                return context.getString(R.string.condition_heavy_rain_at_times);
            case "Heavy rain":
                return context.getString(R.string.condition_heavy_rain);
            case "Light freezing rain":
                return context.getString(R.string.condition_light_freezing_rain);
            case "Moderate or heavy freezing rain":
                return context.getString(R.string.condition_moderate_or_heavy_freezing_rain);
            case "Light sleet":
                return context.getString(R.string.condition_light_sleet);
            case "Moderate or heavy sleet":
                return context.getString(R.string.condition_moderate_or_heavy_sleet);
            case "Patchy light snow":
                return context.getString(R.string.condition_patchy_light_snow);
            case "Light snow":
                return context.getString(R.string.condition_light_snow);
            case "Patchy moderate snow":
                return context.getString(R.string.condition_patchy_moderate_snow);
            case "Moderate snow":
                return context.getString(R.string.condition_moderate_snow);
            case "Patchy heavy snow":
                return context.getString(R.string.condition_patchy_heavy_snow);
            case "Heavy snow":
                return context.getString(R.string.condition_heavy_snow);
            case "Ice pellets":
                return context.getString(R.string.condition_ice_pellets);
            case "Light rain shower":
                return context.getString(R.string.condition_light_rain_shower);
            case "Moderate or heavy rain shower":
                return context.getString(R.string.condition_moderate_or_heavy_rain_shower);
            case "Torrential rain shower":
                return context.getString(R.string.condition_torrential_rain_shower);
            case "Light sleet showers":
                return context.getString(R.string.condition_light_sleet_showers);
            case "Moderate or heavy sleet showers":
                return context.getString(R.string.condition_moderate_or_heavy_sleet_showers);
            case "Light snow showers":
                return context.getString(R.string.condition_light_snow_showers);
            case "Moderate or heavy snow showers":
                return context.getString(R.string.condition_moderate_or_heavy_snow_showers);
            case "Light showers of ice pellets":
                return context.getString(R.string.condition_light_showers_of_ice_pellets);
            case "Moderate or heavy showers of ice pellets":
                return context.getString(R.string.condition_moderate_or_heavy_showers_of_ice_pellets);
            case "Patchy light rain with thunder":
                return context.getString(R.string.condition_patchy_light_rain_with_thunder);
            case "Moderate or heavy rain with thunder":
                return context.getString(R.string.condition_moderate_or_heavy_rain_with_thunder);
            case "Patchy light snow with thunder":
                return context.getString(R.string.condition_patchy_light_snow_with_thunder);
            case "Moderate or heavy snow with thunder":
                return context.getString(R.string.condition_moderate_or_heavy_snow_with_thunder);
            default:
                return englishCondition; // Mengembalikan teks asli jika tidak ada terjemahan
        }
    }


    // Metode untuk memformat tanggal
    private String formatDate(String dateString) {
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        SimpleDateFormat outputFormat = new SimpleDateFormat("EEEE, MMM dd", Locale.getDefault()); // Contoh: Monday, Oct 26
        try {
            Date date = inputFormat.parse(dateString);
            return outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return dateString; // Kembali ke string asli jika gagal parsing
        }
    }
}