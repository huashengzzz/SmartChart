package com.smart.smartchart.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;

import com.smart.smartchart.R;
import com.smart.smartchart.ui.base.BaseActivity;
import com.smart.smartchart.utils.SPManager;


public class SplashActivity extends BaseActivity {

    private static final int sleepTime = 1500;
    private String authCode;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initViewsAndEvents(Bundle savedInstanceState) {
        authCode = SPManager.getAuthCode(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        long start = System.currentTimeMillis();
        new Thread(new SplashRunnable(start)).start();
    }

    class SplashRunnable implements Runnable {

        private long start;

        public SplashRunnable(long start) {
            this.start = start;
        }

        @Override
        public void run() {

            long costTime = System.currentTimeMillis() - start;
            if (sleepTime - costTime > 0) {
                try {
                    Thread.sleep(sleepTime - costTime);
                } catch (Exception e) {
                }
            }
            startActivity(new Intent(SplashActivity.this, MainActivity.class));
            finish();
        }
    }

    /**
     * 屏蔽物理返回按钮
     *
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return keyCode == KeyEvent.KEYCODE_BACK || super.onKeyDown(keyCode, event);
    }
}