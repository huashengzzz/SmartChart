package com.smart.smartchart.widget.hellocharts.listener;


import com.smart.smartchart.widget.hellocharts.model.SliceValue;

public interface PieChartOnValueSelectListener extends OnValueDeselectListener {

    public void onValueSelected(int arcIndex, SliceValue value);

}
