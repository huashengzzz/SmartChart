package com.smart.smartchart.ui.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Gs on 2016/5/23.
 */
public abstract class AbsBaseActivity extends AppCompatActivity {
    /**
     * log tag
     */
    protected static String TAG_LOG = null;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TAG_LOG = this.getClass().getSimpleName();

    }

    /**
     * 获取布局文件ID
     *
     * @return
     */
    protected abstract int getContentViewId();

    /**
     * 获取Intent
     *
     * @param intent
     */
    protected void handleIntent(Intent intent) {
    }

    /**
     * initialize views and events here
     *
     * @param savedInstanceState
     */
    protected abstract void initViewsAndEvents(Bundle savedInstanceState);
}
