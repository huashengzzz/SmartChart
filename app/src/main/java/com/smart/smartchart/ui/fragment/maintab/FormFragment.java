package com.smart.smartchart.ui.fragment.maintab;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.smart.smartchart.R;
import com.smart.smartchart.ui.adapter.CommonViewPagerAdapter;
import com.smart.smartchart.ui.base.BaseFragment;
import com.smart.smartchart.ui.fragment.other.FormMainFragment;
import com.smart.smartchart.widget.smarttablelayout.SmartTabLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by gs on 2017/9/15.
 */

public class FormFragment extends BaseFragment{
    @BindView(R.id.viewpager)
    ViewPager viewPager;
    @BindView(R.id.tab)
    SmartTabLayout smartTabLayout;
    @Override
    protected int getContentViewID() {
        return R.layout.fragment_form;
    }

    @Override
    protected void initViewsAndEvents(View rootView, Bundle savedInstanceState) {
        CommonViewPagerAdapter adapter = new CommonViewPagerAdapter(getChildFragmentManager());
        List<Fragment> fragments=new ArrayList<>();
        fragments.add(FormMainFragment.newInstance("0"));
        fragments.add(FormMainFragment.newInstance("1"));
        fragments.add(FormMainFragment.newInstance("2"));
        fragments.add(FormMainFragment.newInstance("3"));
        fragments.add(FormMainFragment.newInstance("4"));
        fragments.add(FormMainFragment.newInstance("5"));
        fragments.add(FormMainFragment.newInstance("6"));
        adapter.setData(getResources().getStringArray(R.array.main_tab_form),fragments);
        viewPager.setOffscreenPageLimit(fragments.size()-1);
        viewPager.setAdapter(adapter);
        smartTabLayout.setViewPager(viewPager);
    }
}
