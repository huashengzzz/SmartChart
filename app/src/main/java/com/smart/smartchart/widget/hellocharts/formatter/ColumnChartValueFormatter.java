package com.smart.smartchart.widget.hellocharts.formatter;


import com.smart.smartchart.widget.hellocharts.model.SubcolumnValue;

public interface ColumnChartValueFormatter {

    public int formatChartValue(char[] formattedValue, SubcolumnValue value);

}
