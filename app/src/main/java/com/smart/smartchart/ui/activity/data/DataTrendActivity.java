package com.smart.smartchart.ui.activity.data;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;

import com.smart.smartchart.R;
import com.smart.smartchart.ui.base.BaseSwipeBackActivity;
import com.smart.smartchart.utils.CommonUtils;
import com.smart.smartchart.widget.hellocharts.gesture.ContainerScrollType;
import com.smart.smartchart.widget.hellocharts.model.Axis;
import com.smart.smartchart.widget.hellocharts.model.AxisValue;
import com.smart.smartchart.widget.hellocharts.model.Column;
import com.smart.smartchart.widget.hellocharts.model.ColumnChartData;
import com.smart.smartchart.widget.hellocharts.model.Line;
import com.smart.smartchart.widget.hellocharts.model.LineChartData;
import com.smart.smartchart.widget.hellocharts.model.PointValue;
import com.smart.smartchart.widget.hellocharts.model.SubcolumnValue;
import com.smart.smartchart.widget.hellocharts.model.ValueShape;
import com.smart.smartchart.widget.hellocharts.model.Viewport;
import com.smart.smartchart.widget.hellocharts.view.ColumnChartView;
import com.smart.smartchart.widget.hellocharts.view.LineChartView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static com.smart.smartchart.R.id.chart;

/**
 * Created by gs on 2017/9/19.
 */

public class DataTrendActivity extends BaseSwipeBackActivity {
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(chart)
    ColumnChartView columnChartView;
    @BindView(R.id.lineView)
    LineChartView lineChartView;
    @BindView(R.id.scroll_view)
    ScrollView scrollView;
    @Override
    protected int getContentViewId() {
        return R.layout.activity_data_trend;
    }

    @Override
    protected void initViewsAndEvents(Bundle savedInstanceState) {
        initToobar();
        drawLine();
        drawChart();

    }

    private void initToobar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        title.setText("近3小时趋势图");
        toolbar.setNavigationIcon(R.drawable.icon_btn_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void drawLine() {
        CommonUtils.solveScrollConflict(lineChartView,scrollView);
        String[] lineLabels = {"16:00", "15:45", "15:30", "15:15", "15:00", "14:45", "14:30","14:00","13:45"};
        int maxNumberOfLines = 1;
        int numberOfPoints = lineLabels.length;
        ValueShape shape = ValueShape.CIRCLE;
        float[][] randomNumbersTab = new float[maxNumberOfLines][numberOfPoints];
        for (int i = 0; i < maxNumberOfLines; ++i) {
            for (int j = 0; j < numberOfPoints; ++j) {
                randomNumbersTab[i][j] = (float) Math.random() * 100f;
            }
        }



        List<Line> lines = new ArrayList<Line>();
        List<AxisValue> axisValues = new ArrayList<AxisValue>();
        for (int i = 0; i < maxNumberOfLines; ++i) {
            List<PointValue> values = new ArrayList<PointValue>();
            for (int j = 0; j < numberOfPoints; ++j) {
                values.add(new PointValue(j, randomNumbersTab[i][j]));
            }
            Line line = new Line(values);
            line.setColor(getResources().getColor(R.color.color_67DCFF));

            line.setShape(shape);
            line.setPointRadius(3);
            line.setStrokeWidth(1);
            line.setCubic(false);
            line.setFilled(false);
            line.setHasLabels(false);
            line.setHasLabelsOnlyForSelected(true);
            line.setHasLines(true);
            line.setHasPoints(true);
            //line.setPointColor(R.color.transparent);
            line.setHasGradientToTransparent(true);
//            if (pointsHaveDifferentColor){
//                line.setPointColor(ChartUtils.COLORS[(i + 1) % ChartUtils.COLORS.length]);
//            }
            lines.add(line);

        }

        LineChartData data = new LineChartData(lines);
        for (int i = 0; i < numberOfPoints; i++) {
            axisValues.add(new AxisValue(i).setLabel(lineLabels[i]));
        }
        Axis axisX = new Axis(axisValues).setMaxLabelChars(5);
        axisX.setTextColor(getResources().getColor(R.color.color_969696))
        .setTextSize(10).setHasSeparationLineColor(getResources().getColor(R.color.color_e6e6e6))
        .setHasTiltedLabels(true);
        data.setAxisXBottom(axisX);
        Axis axisY = new Axis().setHasSeparationLine(false).setMaxLabelChars(3)
        .setTextColor(getResources().getColor(R.color.color_969696))
        .setTextSize(10).setHasLines(true).setLineColor(getResources().getColor(R.color.color_e6e6e6));
        data.setAxisYLeft(axisY);
        data.setBaseValue(Float.NEGATIVE_INFINITY);
        lineChartView.setLineChartData(data);

        lineChartView.setZoomEnabled(false);
        lineChartView.setScrollEnabled(true);
        lineChartView.setContainerScrollEnabled(true, ContainerScrollType.HORIZONTAL);
        lineChartView.setValueSelectionEnabled(true);//点击折线点可以显示label
        // Reset viewport height range to (0,100)
        lineChartView.setViewportCalculationEnabled(false);

        //让布局能够水平滑动要设置setCurrentViewport比setMaximumViewport小
        final Viewport v = new Viewport(lineChartView.getMaximumViewport());
        v.bottom = 0;
        v.top = 125;
        v.left=0;
        v.right=numberOfPoints-1+0.5f;
        lineChartView.setMaximumViewport(v);
        v.left = 0;
        v.right = Math.min(6,numberOfPoints-1+0.5f);
        lineChartView.setCurrentViewport(v);
    }

    private void drawChart() {
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
        v.left=-0.5f;
        v.right=numColumns-1+0.5f;
        columnChartView.setMaximumViewport(v);
        v.left = -0.5f;
        v.right = Math.min(6,numColumns-1+0.5f);
        columnChartView.setCurrentViewport(v);

    }

}
