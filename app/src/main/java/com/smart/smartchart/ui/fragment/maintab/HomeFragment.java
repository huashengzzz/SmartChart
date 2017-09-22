package com.smart.smartchart.ui.fragment.maintab;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;
import com.smart.smartchart.R;
import com.smart.smartchart.ui.activity.data.DataTrendActivity;
import com.smart.smartchart.ui.activity.home.MessageActivity;
import com.smart.smartchart.ui.base.BaseFragment;
import com.smart.smartchart.utils.CommonUtils;
import com.smart.smartchart.widget.BannerTextView;
import com.smart.smartchart.widget.CustomIndicatorView;
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
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.smart.smartchart.R.id.chart;


/**
 * Created by Gs on 2017/4/12.
 */

public class HomeFragment extends BaseFragment implements BannerTextView.OnItemClick, ViewPager.OnPageChangeListener {

    @BindView(chart)
    ColumnChartView columnChartView;
    @BindView(R.id.lineView)
    LineChartView lineChartView;
    @BindView(R.id.banner)
    BannerTextView bannerTextView;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.img_message)
    ImageView imgMessage;
    @BindView(R.id.banner_top)
    ConvenientBanner banner;
    @BindView(R.id.ic_preference)
    CustomIndicatorView ic_preference;
    @BindView(R.id.scroll_view)
    ScrollView scrollView;
    private List<List<String>> banners = new ArrayList<>();

    @Override
    protected int getContentViewID() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initViewsAndEvents(View rootView, Bundle savedInstanceState) {
        CommonUtils.solveScrollConflict(lineChartView,scrollView);
        drawChart();
        drawLine();
        if (bannerTextView == null) {
            bannerTextView = (BannerTextView) rootView.findViewById(R.id.banner);
        }
        ArrayList ad = new ArrayList();
        ad.add("公告来了，活动来了");
        ad.add("什么来了，呵呵呵呵哈");
        bannerTextView.addItem(ad);
        bannerTextView.setOrientationVertical(true);
        bannerTextView.setIntervalTime(5000);
        bannerTextView.setOnItemClick(HomeFragment.this);
        bannerTextView.play();
        String[] str = {"一次供水：103bar", "一次供水：103bar", "一次供水：103bar", "一次供水：103bar", "一次供水：103bar", "一次供水：103bar"};
        for (int i = 0; i < 6; i++) {
            banners.add(Arrays.asList(str));
        }
        setUpView(banners);
        banner.startTurning(5000);

    }

    @OnClick({R.id.img_message})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_message:
                startActivity(new Intent(getActivity(), MessageActivity.class));
                break;
        }
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

    private void setUpView(List<List<String>> adEntities) {
        if (adEntities != null || adEntities.size() > 0) {
            banner.setPages(new CBViewHolderCreator<NetHolderView>() {
                @Override
                public NetHolderView createHolder() {
                    return new NetHolderView();
                }
            }, adEntities)
                    .setOnPageChangeListener(this);

            if (adEntities.size() == 1) {
                banner.setCanLoop(false);
                ic_preference.setVisibility(View.INVISIBLE);
            } else {
                ic_preference.setVisibility(View.VISIBLE);
            }

            ic_preference.setIndicatorNum(adEntities.size());
            ic_preference.setIndicatorSelected(0);
        }
    }

    public class NetHolderView implements Holder<List<String>> {
        private TextView[] views=new TextView[6];

        @Override
        public View createView(Context context) {
            //你可以通过layout文件来创建，也可以像我一样用代码创建，不一定是Image，任何控件都可以进行翻页
            View view = View.inflate(getContext(), R.layout.item_main_banner, null);
            views[0] = (TextView) view.findViewById(R.id.tv_one);
            views[1] = (TextView) view.findViewById(R.id.tv_two);
            views[2] = (TextView) view.findViewById(R.id.tv_three);
            views[3] = (TextView) view.findViewById(R.id.tv_four);
            views[4] = (TextView) view.findViewById(R.id.tv_five);
            views[5] = (TextView) view.findViewById(R.id.tv_six);

            return view;
        }

        @Override
        public void UpdateUI(Context context, int position, final List<String> data) {
            for (int i = 0; i < views.length; i++) {
                views[i].setText(data.get(i) == null ? "" : data.get(i));
            }

        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        ic_preference.setIndicatorSelected(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }


    @Override
    public void onClick(View view, int position, String text, Object extraData) {
        //CommWebActivity.launchCommWebActivity(getActivity(),"活动","www.baidu.com");
        startActivity(new Intent(getActivity(), DataTrendActivity.class));
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (bannerTextView != null) {
            bannerTextView.stopAnim();
        }
        if (banner != null)
            banner.stopTurning();
    }


}
