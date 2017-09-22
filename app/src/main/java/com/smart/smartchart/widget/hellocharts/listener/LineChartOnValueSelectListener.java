package com.smart.smartchart.widget.hellocharts.listener;


import com.smart.smartchart.widget.hellocharts.model.PointValue;

public interface LineChartOnValueSelectListener extends OnValueDeselectListener {

    public void onValueSelected(int lineIndex, int pointIndex, PointValue value);

}
