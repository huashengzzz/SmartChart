package com.smart.smartchart.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by gaosheng on 2016/7/27.
 */
public class CommonViewPagerAdapter<T extends Fragment> extends FragmentPagerAdapter {
    private String[] arrs;
    private List<T> fragments;

    public CommonViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return fragments == null ? null : fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments == null ? 0 : fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return arrs == null ? null : arrs[position];
    }

    public void setData(String[] arrs, List<T> fragments) {
        this.arrs = arrs;
        this.fragments = fragments;
    }
}
