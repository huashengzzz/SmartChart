package com.smart.smartchart.ui.activity.energy;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;

import com.smart.smartchart.R;
import com.smart.smartchart.ui.base.BaseSwipeBackActivity;
import com.smart.smartchart.ui.dialog.CommSigleSelectDialog;
import com.smart.smartchart.ui.dialog.SelectDateDialog;
import com.smart.smartchart.utils.CommonUtils;
import com.smart.smartchart.utils.ToastHelper;
import com.smart.smartchart.widget.hellocharts.gesture.ContainerScrollType;
import com.smart.smartchart.widget.hellocharts.model.Axis;
import com.smart.smartchart.widget.hellocharts.model.AxisValue;
import com.smart.smartchart.widget.hellocharts.model.Column;
import com.smart.smartchart.widget.hellocharts.model.ColumnChartData;
import com.smart.smartchart.widget.hellocharts.model.SubcolumnValue;
import com.smart.smartchart.widget.hellocharts.model.Viewport;
import com.smart.smartchart.widget.hellocharts.view.ColumnChartView;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by gs on 2017/9/22.
 */

public class BranchEnergyActivity extends BaseSwipeBackActivity {
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_start_time)
    TextView tvStartTime;
    @BindView(R.id.tv_end_time)
    TextView tvEndTime;
    @BindView(R.id.chart_one)
    ColumnChartView chartOne;
    @BindView(R.id.chart_two)
    ColumnChartView chartTwo;
    @BindView(R.id.tv_energy_type)
    TextView tvEnergyType;
    @BindView(R.id.scroll_view)
    ScrollView scrollView;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_branch_energy;
    }

    @Override
    protected void initViewsAndEvents(Bundle savedInstanceState) {
        initToobar();
        drawChart(chartOne);
        drawChart(chartTwo);
    }

    private void initToobar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        title.setText("分公司一热耗");
        toolbar.setNavigationIcon(R.drawable.icon_btn_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @OnClick({R.id.tv_start_time, R.id.tv_end_time, R.id.tv_energy_type})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_start_time:
                selectDate(tvStartTime);
                break;
            case R.id.tv_end_time:
                selectDate(tvEndTime);
                break;
            case R.id.tv_energy_type:
                singleSelectDialog(this,tvEnergyType,new String[]{"热耗","水耗","电耗"});
                break;

        }

    }

    private void drawChart(ColumnChartView columnChartView) {
        CommonUtils.solveScrollConflict(columnChartView,scrollView);
        String[] chartLabels = {"16:00", "15:45", "15:30", "15:15", "15:00", "14:45", "14:30"};
        int numColumns = chartLabels.length;

        // Column can have many subcolumns, here by default I use 1 subcolumn in each of 8 columns.
        List<Column> columns = new ArrayList<Column>();
        List<SubcolumnValue> values;
        List<AxisValue> axisValues = new ArrayList<AxisValue>();
        for (int i = 0; i < numColumns; ++i) {
            values = new ArrayList<SubcolumnValue>();
            float height = (float) (Math.random() * 115) + 5;
            values.add(new SubcolumnValue(height, getResources().getColor(R.color.color_67DCFF)).setLabel(height + "")
                    .setLabelColor(getResources().getColor(R.color.color_FE5E63)).setLabelTextsize(CommonUtils.dp2px(this, 15)));

            Column column = new Column(values);
            column.setHasLabels(false).setHasLabelsOnlyForSelected(true);
            columns.add(column);
            axisValues.add(new AxisValue(i).setLabel(chartLabels[i]));
        }


        ColumnChartData data = new ColumnChartData(columns);
        // value.
        Axis axisx = new Axis(axisValues)
                .setTextColor(getResources().getColor(R.color.color_969696))
                .setTextSize(11).setHasLines(false).setHasSeparationLine(true).
                        setLineColor(getResources().getColor(R.color.color_e6e6e6));
        data.setAxisXBottom(axisx);
        data.setFillRatio(0.5f);
        Axis axisY = new Axis().setHasLines(true).setHasSeparationLine(false).setMaxLabelChars(3);
        axisY.setTextColor(getResources().getColor(R.color.color_969696))
                .setTextSize(11).setLineColor(getResources().getColor(R.color.color_e6e6e6))
                .setHasSeparationLineColor(getResources().getColor(R.color.color_e6e6e6));
        data.setAxisYLeft(axisY);
        columnChartView.setColumnChartData(data);


        columnChartView.setZoomEnabled(false);
        columnChartView.setScrollEnabled(true);
        columnChartView.setInteractive(true);
        columnChartView.setContainerScrollEnabled(true, ContainerScrollType.HORIZONTAL);
        columnChartView.setValueSelectionEnabled(true);//点击折线点可以显示label
        columnChartView.setViewportCalculationEnabled(false);
        final Viewport v = new Viewport(columnChartView.getMaximumViewport());
        v.bottom = 0;
        v.top = 135;
        v.left = -0.5f;
        v.right = numColumns - 1 + 0.5f;
        columnChartView.setMaximumViewport(v);
        v.left = -0.5f;
        v.right = Math.min(6, numColumns - 1 + 0.5f);
        columnChartView.setCurrentViewport(v);

    }

    private void selectDate(final TextView tv) {
        SelectDateDialog selectDateDialog = new SelectDateDialog(this);
        selectDateDialog.setOnSelectedDateListener(new SelectDateDialog.OnSelectedDateListener() {
            @Override
            public void selectedDate(int year, int month, int day) {
                String mon, da, birthday;
                mon = month < 10 ? "0" + month : String.valueOf(month);
                da = day < 10 ? "0" + day : String.valueOf(day);
                birthday = year + "-" + mon + "-" + da;
                switch (tv.getId()) {
                    case R.id.tv_start_time:
                        try {
                            if (!TextUtils.isEmpty(tvEndTime.getText().toString())
                                    && (CommonUtils.dateToStamp(tvEndTime.getText().toString(), "yyyy-MM-dd") <
                                    CommonUtils.dateToStamp(birthday, "yyyy-MM-dd"))) {
                                ToastHelper.shortToast(BranchEnergyActivity.this, "开始时间要不于结束时间");
                                return;
                            } else {
                                tvStartTime.setText(birthday);
                            }

                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        break;
                    case R.id.tv_end_time:
                        try {
                            if (!TextUtils.isEmpty(tvStartTime.getText().toString())
                                    && (CommonUtils.dateToStamp(tvStartTime.getText().toString(), "yyyy-MM-dd") >
                                    CommonUtils.dateToStamp(birthday, "yyyy-MM-dd"))) {
                                ToastHelper.shortToast(BranchEnergyActivity.this, "结束时间要不小于开始时间");
                                return;
                            } else {
                                tvEndTime.setText(birthday);
                            }

                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        break;

                }


            }
        });

    }
    private void singleSelectDialog(Activity activity, final TextView tv, String[] strs) {
        CommSigleSelectDialog commSigleSelectDialog = new CommSigleSelectDialog(activity);
        commSigleSelectDialog.setValue(strs);
        commSigleSelectDialog.setShowCount(3);
        commSigleSelectDialog.setWrap(true);
        commSigleSelectDialog.setOnSelectListener(new CommSigleSelectDialog.OnSelectListener() {
            @Override
            public void onSelect(String str, int value) {
                tv.setText(str);
            }
        });
        commSigleSelectDialog.show();
    }

}
