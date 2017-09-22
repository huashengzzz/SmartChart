package com.smart.smartchart.ui.activity.data;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.smart.smartchart.R;
import com.smart.smartchart.ui.base.BaseSwipeBackActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by gs on 2017/9/19.
 */

public class DataDetailsActivity extends BaseSwipeBackActivity {
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_one_support_temp)
    TextView tvOneSupportTemp;
    @BindView(R.id.tv_two_support_temp)
    TextView tvTwoSupportTemp;
    @BindView(R.id.tv_one_return_temp)
    TextView tvOneReturnTemp;
    @BindView(R.id.tv_two_return_temp)
    TextView tvTwoReturnTemp;
    @BindView(R.id.tv_one_support_pressure)
    TextView tvOneSupportPressure;
    @BindView(R.id.tv_two_support_pressure)
    TextView tvTwoSupportPressure;
    @BindView(R.id.tv_water_level)
    TextView tvWaterLevel;
    @BindView(R.id.tv_two_pressure_diff)
    TextView tvTwoPressureDiff;
    @BindView(R.id.tv_instantaneous_flow)
    TextView tvInstantaneousFlow;
    @BindView(R.id.tv_tired_flow)
    TextView tvTiredFlow;
    @BindView(R.id.tv_instantaneous_heat)
    TextView tvInstantaneousHeat;
    @BindView(R.id.tv_tired_heat)
    TextView tvTiredHeat;
    @BindView(R.id.tv_water_instantaneous_flow)
    TextView tvWaterInstantaneousFlow;
    @BindView(R.id.tv_water_tired_flow)
    TextView tvWaterTiredFlow;
    @BindView(R.id.tv_power)
    TextView tvPower;
    @BindView(R.id.tv_electricity)
    TextView tvElectricity;
    @BindView(R.id.tv_one_regulating)
    TextView tvOneRegulating;
    @BindView(R.id.tv_two_regulating)
    TextView tvTwoRegulating;
    @BindView(R.id.tv_one_circulating_pump)
    TextView tvOneCirculatingPump;
    @BindView(R.id.tv_two_circulating_pump)
    TextView tvTwoCirculatingPump;
    @BindView(R.id.tv_one_water_pump)
    TextView tvOneWaterPump;
    @BindView(R.id.tv_two_water_pump)
    TextView tvTwoWaterPump;
    @BindView(R.id.tv_drain_solenoid)
    TextView tvDrainSolenoid;
    @BindView(R.id.tv_undrain_solenoid)
    TextView tvUndrainSolenoid;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_heat_details;
    }

    @Override
    protected void initViewsAndEvents(Bundle savedInstanceState) {
        initToobar();
    }
    private void initToobar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        title.setText("换热机组一");
        toolbar.setNavigationIcon(R.drawable.icon_btn_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    @OnClick({R.id.tv_one_support_temp})
    public void onClick(View v){
        switch (v.getId()){
            case R.id.tv_one_support_temp:
                startActivity(new Intent(this,DataTrendActivity.class));
                break;
        }
    }

}
