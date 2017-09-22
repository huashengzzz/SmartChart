package com.smart.smartchart.utils;

import android.support.v4.content.LocalBroadcastManager;

import com.smart.smartchart.Application;

/**
 * Created by gaosheng on 2016/12/21.
 */

public class LocalBroadcast {
    private static LocalBroadcastManager localBroadcastManager ;


    public static LocalBroadcastManager getLocalBroastManager(){
        if(localBroadcastManager == null){
            synchronized (LocalBroadcast.class){
                if(localBroadcastManager == null){
                    localBroadcastManager = LocalBroadcastManager.getInstance(Application.getInstance().getApplicationContext());
                }
            }
        }

        return localBroadcastManager;
    }
}
