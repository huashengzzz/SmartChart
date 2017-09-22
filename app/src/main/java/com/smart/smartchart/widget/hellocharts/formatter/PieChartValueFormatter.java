package com.smart.smartchart.widget.hellocharts.formatter;


import com.smart.smartchart.widget.hellocharts.model.SliceValue;

public interface PieChartValueFormatter {

    public int formatChartValue(char[] formattedValue, SliceValue value);
}
