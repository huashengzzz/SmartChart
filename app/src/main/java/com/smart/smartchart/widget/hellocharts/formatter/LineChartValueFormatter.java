package com.smart.smartchart.widget.hellocharts.formatter;


import com.smart.smartchart.widget.hellocharts.model.PointValue;

public interface LineChartValueFormatter {

    public int formatChartValue(char[] formattedValue, PointValue value);
}
