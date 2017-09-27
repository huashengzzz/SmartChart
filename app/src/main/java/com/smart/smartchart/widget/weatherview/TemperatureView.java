package com.smart.smartchart.widget.weatherview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;


public class TemperatureView extends View {

    private int maxTemp;
    private int minTemp;

    private int temperatureDay;
    private int temperatureNight;

    private Paint pointPaint;
    private Paint linePaint;
    private Paint textPaint;
    private int lineColor;
    private int pointColor;
    private int textColor;

    private int radius = 5;
    private int textSize = 40;

    private int xPointDay;
    private int yPointDay;
    private int xPointNight;
    private int yPointNight;
    private int mWidth;

    public TemperatureView(Context context) {
        this(context, null);
    }

    public TemperatureView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public TemperatureView(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        initAttrs(context, attrs);

        initPaint(context, attrs);
    }

    private void initAttrs(Context context, AttributeSet attrs) {
        lineColor = 0xff93a122;
        textColor = 0xffffffff;
        pointColor = 0xffffffff;
    }

    private void initPaint(Context context, AttributeSet attrs) {

        pointPaint = new Paint();
        linePaint = new Paint();
        textPaint = new Paint();

        linePaint.setColor(lineColor);
        pointPaint.setColor(pointColor);
        pointPaint.setAntiAlias(true);
        textPaint.setColor(textColor);
        textPaint.setTextSize(textSize);
        textPaint.setAntiAlias(true);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        drawPoint(canvas);

        drawText(canvas);

    }

    private void drawPoint(Canvas canvas) {
        int height = getHeight() - textSize * 4;
        int x = getWidth() / 2;
        int y = (int) (height - height * (temperatureDay - minTemp) * 1.0f / (maxTemp - minTemp)) + textSize * 2;
        int x2 = getWidth() / 2;
        int y2 = (int) (height - height * (temperatureNight - minTemp) * 1.0f / (maxTemp - minTemp)) + textSize * 2;
        xPointDay = x;
        yPointDay = y;
        xPointNight = x2;
        yPointNight = y2;
        mWidth = getWidth();
        canvas.drawCircle(x, y, radius, pointPaint);
        canvas.drawCircle(x2, y2, radius, pointPaint);
    }

    private void drawText(Canvas canvas) {
        int height = getHeight() - textSize * 4;
        int yDay = (int) (height - height * (temperatureDay - minTemp) * 1.0f / (maxTemp - minTemp)) + textSize * 2;
        int yNight = (int) (height - height * (temperatureNight - minTemp) * 1.0f / (maxTemp - minTemp)) + textSize * 2;
        String dayTemp = temperatureDay + "°";
        String nightTemp = temperatureNight + "°";
        float widDay = textPaint.measureText(dayTemp);
        float widNight = textPaint.measureText(nightTemp);
        float hei = textPaint.descent() - textPaint.ascent();
        canvas.drawText(dayTemp, getWidth() / 2 - widDay / 2, yDay - radius - hei / 2, textPaint);
        canvas.drawText(nightTemp, getWidth() / 2 - widNight / 2, yNight + radius + hei, textPaint);
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
        invalidate();
    }

    public int getMaxTemp() {
        return maxTemp;
    }

    public void setMaxTemp(int maxTemp) {
        this.maxTemp = maxTemp;
    }

    public int getMinTemp() {
        return minTemp;
    }

    public void setMinTemp(int minTemp) {
        this.minTemp = minTemp;
    }

    public int getxPointDay() {
        return xPointDay;
    }

    public int getyPointDay() {
        return yPointDay;
    }

    public void setxPointDay(int xPointDay) {
        this.xPointDay = xPointDay;
    }

    public void setyPointDay(int yPointDay) {
        this.yPointDay = yPointDay;
    }

    public int getxPointNight() {
        return xPointNight;
    }

    public void setxPointNight(int xPointNight) {
        this.xPointNight = xPointNight;
    }

    public int getyPointNight() {
        return yPointNight;
    }

    public void setyPointNight(int yPointNight) {
        this.yPointNight = yPointNight;
    }

    public int getmWidth() {
        return mWidth;
    }

    public int getTemperatureDay() {
        return temperatureDay;
    }

    public void setTemperatureDay(int temperatureDay) {
        this.temperatureDay = temperatureDay;
    }

    public int getLineColor() {
        return lineColor;
    }

    public void setLineColor(int lineColor) {
        this.lineColor = lineColor;
    }

    public int getPointColor() {
        return pointColor;
    }

    public void setPointColor(int pointColor) {
        this.pointColor = pointColor;
    }

    public int getTextColor() {
        return textColor;
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
    }

    public int getTemperatureNight() {
        return temperatureNight;
    }

    public void setTemperatureNight(int temperatureNight) {
        this.temperatureNight = temperatureNight;
    }
}
