package com.smart.smartchart.ui.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.smart.smartchart.widget.swipeback.SwipeBackActivityBase;
import com.smart.smartchart.widget.swipeback.SwipeBackActivityHelper;
import com.smart.smartchart.widget.swipeback.SwipeBackLayout;
import com.smart.smartchart.widget.swipeback.SwipeBackUtils;


/**
 * Created by GS on 2016/5/23.
 */
public abstract class BaseSwipeBackActivity extends BaseActivity implements SwipeBackActivityBase {
    /**
     * swipeBack helper to using swipeBack
     */
    private SwipeBackActivityHelper mHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mHelper = new SwipeBackActivityHelper(this);
        mHelper.onActivityCreate();
    }


    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mHelper.onPostCreate();
    }

    @Override
    public View findViewById(int id) {
        View v = super.findViewById(id);
        if (v == null && mHelper != null)
            return mHelper.findViewById(id);
        return v;
    }

    @Override
    public SwipeBackLayout getSwipeBackLayout() {
        return mHelper.getSwipeBackLayout();
    }

    @Override
    public void setSwipeBackEnable(boolean enable) {
        getSwipeBackLayout().setEnableGesture(enable);
    }

    @Override
    public void scrollToFinishActivity() {
        SwipeBackUtils.convertActivityToTranslucent(this);
        getSwipeBackLayout().scrollToFinishActivity();
    }
}
