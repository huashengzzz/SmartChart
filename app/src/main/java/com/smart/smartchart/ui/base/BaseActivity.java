package com.smart.smartchart.ui.base;

import android.os.Bundle;
import android.support.annotation.Nullable;

import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * Created by Gs on 2016/5/23.
 */
public abstract class BaseActivity extends AbsBaseActivity {
    private Unbinder unbinder;
    protected boolean isActive = false;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentViewId());
        unbinder = ButterKnife.bind(this);
        isActive = true;
        handleIntent(getIntent());
        initViewsAndEvents(savedInstanceState);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        isActive = false;
        unbinder.unbind();
    }

}
