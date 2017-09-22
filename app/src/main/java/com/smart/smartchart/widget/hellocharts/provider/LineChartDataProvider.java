package com.smart.smartchart.widget.hellocharts.provider;


import com.smart.smartchart.widget.hellocharts.model.LineChartData;

public interface LineChartDataProvider {

    public LineChartData getLineChartData();

    public void setLineChartData(LineChartData data);

}
