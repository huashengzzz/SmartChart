package com.smart.smartchart.utils;


import android.content.Context;
import android.os.Handler;
import android.widget.TextView;

import com.smart.smartchart.R;

public class CountDownUtils {

    private Context context;
    private int iTime = 60;
    private TextView v;
    private Handler countdownHandler;

    public CountDownUtils(final Context context, final TextView v) {
        this.context = context;
        this.v = v;
        countdownHandler = new Handler(context.getMainLooper()) {
            public void handleMessage(android.os.Message msg) {
                String s = String.format(context.getString(R.string.code_txt), String.valueOf(iTime));
                v.setText(s);
                countdownHandler.postDelayed(countdownRunnable, 1000);
            }
        };
    }

    private Runnable countdownRunnable = new Runnable() {

        @Override
        public void run() {
            if (iTime == 60) {
                v.setEnabled(false);
                v.setTextColor(context.getResources().getColor(R.color.color_969696));
            }

            if (iTime > 0) {
                iTime--;
                countdownHandler.sendEmptyMessage(0);
            } else if (iTime == 0) {
                iTime = 60;
                v.setEnabled(true);
                countdownHandler.removeCallbacks(this);
                v.setText(context.getString(R.string.bind_mobile_get_code));
                v.setTextColor(context.getResources().getColor(R.color.color_2CA2F4));
            }

        }
    };

    public void countDown() {
        countdownHandler.post(countdownRunnable);
    }

    public void cancleCount() {
        countdownHandler.removeCallbacks(countdownRunnable);
    }

}
