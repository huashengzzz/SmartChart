package com.smart.smartchart.widget.weatherview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.smart.smartchart.R;


public class WeatherItemView extends LinearLayout {

    private View rootView;
    private TextView tvWeek;
    private TextView tvDate;
    private TextView tvDayWeather;
    private TextView tvNightWeather;
    private TemperatureView ttvTemp;
    private TextView tvWindOri;
    private TextView tvWindLevel;
    private ImageView ivDayWeather;
    private ImageView ivNightWeather;

    public WeatherItemView(Context context) {
        this(context, null);
    }

    public WeatherItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public WeatherItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        rootView = LayoutInflater.from(context).inflate(R.layout.weather_item, null);
        tvWeek = (TextView) rootView.findViewById(R.id.tv_week);
        tvDate = (TextView) rootView.findViewById(R.id.tv_date);
        tvDayWeather = (TextView) rootView.findViewById(R.id.tv_day_weather);
        tvNightWeather = (TextView) rootView.findViewById(R.id.tv_night_weather);
        ttvTemp = (TemperatureView) rootView.findViewById(R.id.ttv_day);
        tvWindOri = (TextView) rootView.findViewById(R.id.tv_wind_ori);
        tvWindLevel = (TextView) rootView.findViewById(R.id.tv_wind_level);
        ivDayWeather = (ImageView) rootView.findViewById(R.id.iv_day_weather);
        ivNightWeather = (ImageView) rootView.findViewById(R.id.iv_night_weather);
        //tvAirLevel = (TextView) rootView.findViewById(R.id.tv_air_level);
        rootView.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        addView(rootView);
    }

    public void setWeek(String week) {
        if (tvWeek != null)
            tvWeek.setText(week);
    }

    public void setDate(String date) {
        if (tvDate != null)
            tvDate.setText(date);
    }

    public int getTempX() {
        if (ttvTemp != null)
            return (int) ttvTemp.getX();
        return 0;
    }

    public int getTempY() {
        if (ttvTemp != null)
            return (int) ttvTemp.getY();
        return 0;
    }

    public void setDayWeather(String dayWeather) {
        if (tvDayWeather != null)
            tvDayWeather.setText(dayWeather);
    }

    public void setNightWeather(String nightWeather) {
        if (tvNightWeather != null)
            tvNightWeather.setText(nightWeather);
    }

    public void setWindOri(String windOri) {
        if (tvWindOri != null)
            tvWindOri.setText(windOri);
    }

    public void setWindLevel(String windLevel) {
        if (tvWindLevel != null)
            tvWindLevel.setText(windLevel);
    }

//    public void setAirLevel(AirLevel airLevel) {
//        if (tvAirLevel != null) {
//            switch (airLevel) {
//                case EXCELLENT:
//                    tvAirLevel.setBackgroundResource(R.drawable.best_level_shape);
//                    tvAirLevel.setText("优");
//                    break;
//                case GOOD:
//                    tvAirLevel.setBackgroundResource(R.drawable.good_level_shape);
//                    tvAirLevel.setText("良好");
//                    break;
//                case LIGHT:
//                    tvAirLevel.setText("轻度");
//                    tvAirLevel.setBackgroundResource(R.drawable.small_level_shape);
//                    break;
//                case MIDDLE:
//                    tvAirLevel.setBackgroundResource(R.drawable.mid_level_shape);
//                    tvAirLevel.setText("中度");
//                    break;
//                case HIGH:
//                    tvAirLevel.setBackgroundResource(R.drawable.big_level_shape);
//                    tvAirLevel.setText("重度");
//                    break;
//                case POISONOUS:
//                    tvAirLevel.setBackgroundResource(R.drawable.poison_level_shape);
//                    tvAirLevel.setText("有毒");
//                    break;
//            }
//        }
//    }

    public void setDayTemp(int dayTemp) {
        if (ttvTemp != null)
            ttvTemp.setTemperatureDay(dayTemp);
    }

    public void setNightTemp(int nightTemp) {
        if (ttvTemp != null)
            ttvTemp.setTemperatureNight(nightTemp);
    }

    public void setDayImg(int resId) {
        if (ivDayWeather != null)
            ivDayWeather.setImageResource(resId);
    }

    public void setNightImg(int resId) {
        if (ivNightWeather != null)
            ivNightWeather.setImageResource(resId);
    }

    public void setMaxTemp(int max) {
        if (ttvTemp != null)
            ttvTemp.setMaxTemp(max);
    }

    public void setMinTemp(int min) {
        if (ttvTemp != null) {
            ttvTemp.setMinTemp(min);
        }
    }
}
