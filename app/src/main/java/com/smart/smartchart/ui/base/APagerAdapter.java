package com.smart.smartchart.ui.base;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by Gs on 2017/1/11.
 */

public abstract class APagerAdapter<T> extends PagerAdapter {
    protected LayoutInflater mInflater;
    protected List<T> mDataList;
    protected SparseArray<View> mViewSparseArray;
    protected Context mContext;

    public APagerAdapter(Context context, List<T> dataList) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mDataList = dataList;
        mViewSparseArray = new SparseArray<>(dataList.size());
    }

    @Override
    public int getCount() {
        if (mDataList == null) return 0;
        return mDataList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = mViewSparseArray.get(position);
        if (view == null) {
            view = getView(position);
            mViewSparseArray.put(position, view);
        }
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(mViewSparseArray.get(position));
    }

    public abstract View getView(int position);

}
