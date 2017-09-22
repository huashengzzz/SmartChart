package com.smart.smartchart.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.smart.smartchart.R;
import com.smart.smartchart.ui.base.BaseSwipeBackActivity;

import butterknife.BindView;


/**
 * Created by gs on 2017/9/21.
 */

public class SetDataActivity extends BaseSwipeBackActivity {
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.tv_title_right)
    TextView tvTitleRight;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.linear_select_root)
    LinearLayout linearSelectRoot;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_set_data;
    }

    @Override
    protected void initViewsAndEvents(Bundle savedInstanceState) {
        initToolbar();
    }
    private void initToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setNavigationIcon(R.drawable.icon_btn_back);
        title.setText("设置实时数据指标");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tvTitleRight.setVisibility(View.VISIBLE);
        tvTitleRight.setText("保存");
        tvTitleRight.setTextColor(getResources().getColor(R.color.color_6CABFA));
        tvTitleRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
