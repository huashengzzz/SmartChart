package com.smart.smartchart.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.smart.smartchart.Application;
import com.smart.smartchart.R;
import com.smart.smartchart.ui.base.BaseActivity;
import com.smart.smartchart.ui.base.BaseFragment;
import com.smart.smartchart.ui.fragment.maintab.DataFragment;
import com.smart.smartchart.ui.fragment.maintab.EnergyFragment;
import com.smart.smartchart.ui.fragment.maintab.FormFragment;
import com.smart.smartchart.ui.fragment.maintab.HomeFragment;
import com.smart.smartchart.ui.fragment.maintab.MineFragment;
import com.smart.smartchart.utils.StatusBarUtil;
import com.smart.smartchart.utils.ToastHelper;

import butterknife.BindString;
import butterknife.BindView;

public class MainActivity extends BaseActivity {

    @BindView(R.id.main_tab1)
    View mTab1;
    @BindView(R.id.main_tab2)
    View mTab2;
    @BindView(R.id.main_tab3)
    View mTab3;
    @BindView(R.id.main_tab4)
    View mTab4;
    @BindView(R.id.main_tab5)
    View mTab5;
    @BindString(R.string.app_exit)
    String app_exit;
    private final int[] mTabIcons = new int[]{R.drawable.tab_main_home_selector,
            R.drawable.tab_main_data_selector, R.drawable.tab_main_energy_selector, R.drawable.tab_main_form_selector,
            R.drawable.tab_main_mine_selector};
    // 当前fragment的index
    private int currentTabIndex = 0;
    private View[] mTabs;
    private long mExitTime;
    private HomeFragment homeFragment;
    private DataFragment dataFragment;
    private EnergyFragment energyFragment;
    private FormFragment formFragment;
    private MineFragment mineFragment;
    protected BaseFragment currentFragment;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initViewsAndEvents(Bundle savedInstanceState) {
        StatusBarUtil.darkMode(this);

        homeFragment = new HomeFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.main_content, homeFragment).commitAllowingStateLoss();
        currentFragment = homeFragment;
        currentTabIndex = 0;
        initTabs();
        changeFragment(currentTabIndex);


    }
    /**
     * 初始化底部4个导航按钮
     */

    private void initTabs() {
        mTabs = new View[]{mTab1, mTab2,mTab3,mTab4,mTab5};
        String[] mTabTitles = getResources().getStringArray(R.array.main_tab_titles);
        mTabs[0].setSelected(true);
        for (int i = 0; i < mTabs.length; i++) {
            initTab(i, mTabTitles[i], mTabIcons[i]);
        }
    }

    /**
     * 初始化单个导航布局
     *
     * @param position  位置
     * @param mTabTitle 标题
     * @param mTabIcon  按钮Res
     */
    private void initTab(final int position, String mTabTitle, int mTabIcon) {
        View tab = mTabs[position];

        ImageView tab_icon_iv = (ImageView) tab.findViewById(R.id.tab_icon_iv);
        TextView tab_title_tv = (TextView) tab.findViewById(R.id.tab_title_tv);

        tab_icon_iv.setImageResource(mTabIcon);
        tab_title_tv.setText(mTabTitle);
        mTabs[position].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeFragment(position);
            }
        });

    }

    private void changeFragment(int position) {
        for (int i = 0; i < mTabs.length; i++) {
            mTabs[i].setSelected(i == position);
        }
        switch (position) {
            case 0:
                if (homeFragment == null) {
                    homeFragment = new HomeFragment();
                }
                addOrShowFragment(getSupportFragmentManager().beginTransaction(), homeFragment);
                break;
            case 1:
                if (dataFragment == null) {
                    dataFragment = new DataFragment();
                }
                addOrShowFragment(getSupportFragmentManager().beginTransaction(), dataFragment);
                break;
            case 2:
                if (energyFragment == null) {
                    energyFragment = new EnergyFragment();
                }
                addOrShowFragment(getSupportFragmentManager().beginTransaction(), energyFragment);
                break;
            case 3:
                if (formFragment == null) {
                    formFragment = new FormFragment();
                }
                addOrShowFragment(getSupportFragmentManager().beginTransaction(), formFragment);
                break;
            case 4:
                if (mineFragment == null) {
                    mineFragment = new MineFragment();
                }
                addOrShowFragment(getSupportFragmentManager().beginTransaction(), mineFragment);
                break;

        }
        currentTabIndex = position;
    }

    /**
     * 添加或者显示 fragment
     *
     * @param transaction
     * @param fragment
     */
    protected void addOrShowFragment(FragmentTransaction transaction, Fragment fragment) {
        if (currentFragment == fragment)
            return;

        if (!fragment.isAdded()) { // 如果当前fragment未被添加，则添加到Fragment管理器中
            transaction.hide(currentFragment).add(R.id.main_content, fragment).commitAllowingStateLoss();
        } else {
            transaction.hide(currentFragment).show(fragment).commitAllowingStateLoss();
        }
        currentFragment = (BaseFragment) fragment;
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {

            if (System.currentTimeMillis() - mExitTime > 2000) {
                ToastHelper.shortToast(this, app_exit);
                mExitTime = System.currentTimeMillis();
                return true;
            } else {
                Application.getInstance().exit();
                System.exit(0);
                //android.os.Process.killProcess(android.os.Process.myPid());
            }
        }
        return super.onKeyDown(keyCode, event);
    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
