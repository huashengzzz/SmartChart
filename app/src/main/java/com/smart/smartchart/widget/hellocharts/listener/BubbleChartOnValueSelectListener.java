package com.smart.smartchart.widget.hellocharts.listener;


import com.smart.smartchart.widget.hellocharts.model.BubbleValue;

public interface BubbleChartOnValueSelectListener extends OnValueDeselectListener {

    public void onValueSelected(int bubbleIndex, BubbleValue value);

}
