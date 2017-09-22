package com.smart.smartchart.ui.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Gs on 2016/5/23.
 */
public abstract class BaseFragment extends AbsBaseFragment {
    protected Unbinder unbinder;
    protected boolean isActive;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (getContentViewID() != 0) {
            return inflater.inflate(getContentViewID(), null);
        } else {
            return super.onCreateView(inflater, container, savedInstanceState);
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        unbinder = ButterKnife.bind(this, view);
        isActive = true;
        initViewsAndEvents(view, savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        isActive = false;
        unbinder.unbind();
    }

}
