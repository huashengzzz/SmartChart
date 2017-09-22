package com.smart.smartchart.widget.hellocharts.animation;


import com.smart.smartchart.widget.hellocharts.model.Viewport;

public interface ChartViewportAnimator {

    public static final int FAST_ANIMATION_DURATION = 300;

    public void startAnimation(Viewport startViewport, Viewport targetViewport);

    public void startAnimation(Viewport startViewport, Viewport targetViewport, long duration);

    public void cancelAnimation();

    public boolean isAnimationStarted();

    public void setChartAnimationListener(ChartAnimationListener animationListener);

}
