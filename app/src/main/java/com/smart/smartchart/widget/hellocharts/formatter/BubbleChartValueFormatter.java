package com.smart.smartchart.widget.hellocharts.formatter;


import com.smart.smartchart.widget.hellocharts.model.BubbleValue;

public interface BubbleChartValueFormatter {

    public int formatChartValue(char[] formattedValue, BubbleValue value);
}
