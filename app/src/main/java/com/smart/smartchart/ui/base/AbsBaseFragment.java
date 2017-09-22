package com.smart.smartchart.ui.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;

/**
 * Created by Gs on 2016/5/23.
 */
public abstract class AbsBaseFragment extends Fragment {
    /**
     * Log tag
     */
    protected static String TAG_LOG = null;

    /**
     * activity context of fragment
     */
    protected Context mContext;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TAG_LOG = this.getClass().getSimpleName();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mContext = null;
    }

    /**
     * 获取布局ID
     *
     * @return
     */
    protected abstract int getContentViewID();

    /**
     * override this method to do operation in the fragment
     */
    protected abstract void initViewsAndEvents(View rootView, Bundle savedInstanceState);

}
