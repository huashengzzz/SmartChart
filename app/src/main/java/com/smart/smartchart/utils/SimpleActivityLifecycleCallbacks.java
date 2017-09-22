package com.smart.smartchart.utils;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

/**
 * Created by linhe on 2016/12/12.
 */

public abstract class SimpleActivityLifecycleCallbacks implements Application.ActivityLifecycleCallbacks {

    @Override
    public void onActivityStarted(Activity activity) {

    }

    @Override
    public void onActivityResumed(Activity activity) {

    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }
}
