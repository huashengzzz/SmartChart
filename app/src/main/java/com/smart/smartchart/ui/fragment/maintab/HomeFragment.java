package com.smart.smartchart.ui.fragment.maintab;

import android.os.Bundle;
import android.view.View;
import android.widget.ScrollView;

import com.smart.smartchart.R;
import com.smart.smartchart.ui.base.BaseFragment;
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
 * Created by Gs on 2017/4/12.
 */

public class HomeFragment extends BaseFragment  {

    @BindView(chart)
    ColumnChartView columnChartView;
    @BindView(R.id.lineView)
    LineChartView lineChartView;
    @BindView(R.id.scroll_view)
    ScrollView scrollView;

    @Override
    protected int getContentViewID() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initViewsAndEvents(View rootView, Bundle savedInstanceState) {
        CommonUtils.solveScrollConflict(lineChartView,scrollView);
        drawChart();
        drawLine();
    }

    private void drawLine() {
        String[] lineLabels = {"09-12", "09-11", "09-10", "09-09", "09-08", "09-07", "09-06", "09-09", "09-08", "09-07", "09-06"};
        int[] chartColors = new int[]{getResources().getColor(R.color.color_FE5E63), getResources().getColor(R.color.color_6CABFA),
                getResources().getColor(R.color.color_FBE382)};
        int maxNumberOfLines = 3;
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
                values.add(new PointValue(j, randomNumbersTab[i][j]).setLabelColor(getResources().getColor(R.color.color_FE5E63))
                        .setLabelTextsize(CommonUtils.dp2px(getActivity(), 15)));
            }
            Line line = new Line(values);
            line.setColor(chartColors[i]);
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
                .setTextSize(10).setLineColor(getResources().getColor(R.color.color_e6e6e6))
                .setHasSeparationLineColor(getResources().getColor(R.color.color_e6e6e6)).setHasTiltedLabels(true);
        data.setAxisXBottom(axisX);
        Axis axisY = new Axis().setHasLines(true).setHasSeparationLine(false).setMaxLabelChars(3);
        axisY.setTextColor(getResources().getColor(R.color.color_969696));
        axisY.setTextSize(10);
        data.setAxisYLeft(axisY);
        data.setBaseValue(Float.NEGATIVE_INFINITY);


        lineChartView.setZoomEnabled(false);
        lineChartView.setScrollEnabled(true);
        lineChartView.setContainerScrollEnabled(true, ContainerScrollType.HORIZONTAL);
        lineChartView.setValueSelectionEnabled(true);//点击折线点可以显示label
        lineChartView.setLineChartData(data);
        // Reset viewport height range to (0,100)
        lineChartView.setViewportCalculationEnabled(false);
        //让布局能够水平滑动要设置setCurrentViewport比setMaximumViewport小
        final Viewport v = new Viewport(lineChartView.getMaximumViewport());
        v.bottom = 0;
        v.top = 105;
        v.left = 0;
        v.right = numberOfPoints - 1 + 0.5f;
        lineChartView.setMaximumViewport(v);
        v.left = 0;
        v.right = Math.min(6, numberOfPoints - 1 + 0.5f);
        lineChartView.setCurrentViewport(v);

    }

    private void drawChart() {

        String[] chartLabels = {"热能", "水耗", "电耗"};
        int[] chartColors = new int[]{getResources().getColor(R.color.color_FE5E63), getResources().getColor(R.color.color_6CABFA),
                getResources().getColor(R.color.color_FBE382)};

        int numColumns = chartLabels.length;
        //columnChartView.setZoomEnabled(false);

        // Column can have many subcolumns, here by default I use 1 subcolumn in each of 8 columns.
        List<Column> columns = new ArrayList<Column>();
        List<SubcolumnValue> values;
        List<AxisValue> axisValues = new ArrayList<AxisValue>();
        for (int i = 0; i < numColumns; ++i) {
            values = new ArrayList<SubcolumnValue>();
            int height = (int) (Math.random() * 50) + 5;
            values.add(new SubcolumnValue(height, chartColors[i]).setLabel(height +
                    "GJ/平方米")
                    .setLabelColor(getResources().getColor(R.color.color_969696)).setLabelTextsize(CommonUtils.dp2px(getActivity(), 12)));

            Column column = new Column(values);
            column.setHasLabels(true);
            // column.setHasLabelsOnlyForSelected(hasLabelForSelected);
            columns.add(column);
            axisValues.add(new AxisValue(i).setLabel(chartLabels[i]));
        }


        ColumnChartData data = new ColumnChartData(columns);

        // value.
        Axis axisx = new Axis(axisValues);
        axisx.setTextColor(getResources().getColor(R.color.color_323232));
        axisx.setTextSize(13);
        axisx.setHasLines(false);
        axisx.setHasSeparationLine(false);
        data.setAxisXBottom(axisx);
        data.setFillRatio(0.5f);
//        Axis axisY = new Axis().setHasLines(true);
//        data.setAxisYLeft(axisY);
        //data.setAxisXBottom(null);
        columnChartView.setInteractive(false);
        columnChartView.setColumnChartData(data);
        columnChartView.setViewportCalculationEnabled(false);
        final Viewport v = new Viewport(columnChartView.getMaximumViewport());
        v.bottom = 0;
        v.top = 65;
        v.left = -0.5f;
        v.right = numColumns - 1 + 0.5f;
        columnChartView.setMaximumViewport(v);
        columnChartView.setCurrentViewport(v);
    }




}
