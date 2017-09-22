package com.smart.smartchart.utils;

import android.view.View;

/**
 * Created by gaosheng on 2017/3/22.
 */

public abstract class OnNoDoubleClickListener implements View.OnClickListener {
    public static final int DELAY = 500; //连击事件间隔
    private long lastClickTime = 0; //记录最后一次时间

    @Override
    public void onClick(View v) {
                long curTime = System.currentTimeMillis();
        if (curTime - lastClickTime > DELAY) {
            lastClickTime = curTime;
            onNoDoubleClick(v);
        }
    }

    public abstract void onNoDoubleClick(View v);
}
