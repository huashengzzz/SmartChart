package com.smart.smartchart.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.smart.smartchart.R;


/**
 * Created by linhe on 2015/12/15.
 */
public class CustomIndicatorView extends LinearLayout {

    private static final int DEFAULT_INDICATOR_NORMAL_RESID = R.drawable.ic_page_indicator;
    private static final int DEFAULT_INDICATOR_SELECTED_RESID = R.drawable.ic_page_indicator_focused;

    private static final int DEFAULT_INDICATOR_SPACE = 28;
    private static final int DEFAULT_INDICATOR_WIDTH = -1;
    private static final int DEFAULT_INDICATOR_HEIGHT = -1;

    private int indicatorSpace;
    private int indicatorWidth;
    private int indicatorHeight;

    private Drawable indicatorNormalDrawable = null;
    private Drawable indicatorSelectedDrawable = null;

    public CustomIndicatorView(Context context) {
        this(context, null);
    }

    public CustomIndicatorView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomIndicatorView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOrientation(HORIZONTAL);
        setGravity(Gravity.CENTER_HORIZONTAL);

        /**
         * 获得我们所定义的自定义样式属性
         */
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.CustomIndicatorView, defStyleAttr, 0);

        int n = a.getIndexCount();
        for (int i = 0; i < n; i++) {
            int attr = a.getIndex(i);
            switch (attr) {
                case R.styleable.CustomIndicatorView_u_indicatorWidth:
//                    indicatorWidth = a.getDimensionPixelSize(attr, CommonUtils.sp2px(context, DEFAULT_INDICATOR_WIDTH));
                    indicatorWidth = a.getDimensionPixelSize(attr, (int) TypedValue.applyDimension(
                            TypedValue.COMPLEX_UNIT_DIP, DEFAULT_INDICATOR_WIDTH, getResources().getDisplayMetrics()));
                    break;
                case R.styleable.CustomIndicatorView_u_indicatorHeight:
//                    indicatorHeight = a.getDimensionPixelSize(attr, CommonUtils.dp2px(context, DEFAULT_INDICATOR_HEIGHT));
                    indicatorHeight = a.getDimensionPixelSize(attr, (int) TypedValue.applyDimension(
                            TypedValue.COMPLEX_UNIT_DIP, DEFAULT_INDICATOR_HEIGHT, getResources().getDisplayMetrics()));
                    break;
                case R.styleable.CustomIndicatorView_u_indicatorSpace:
                    indicatorSpace = a.getDimensionPixelSize(attr, (int) TypedValue.applyDimension(
                            TypedValue.COMPLEX_UNIT_DIP, DEFAULT_INDICATOR_SPACE, getResources().getDisplayMetrics()));
                    break;
                case R.styleable.CustomIndicatorView_u_indicatorDrawableNormal:
                    indicatorNormalDrawable = a.getDrawable(attr);
                    break;
                case R.styleable.CustomIndicatorView_u_indicatorDrawableSelected:
                    indicatorSelectedDrawable = a.getDrawable(attr);
                    break;
            }

        }
        a.recycle();
    }

    public void setIndicatorNum(int num) {
        removeAllViews();
        for (int i = 0; i < num; i++) {
            addView(createIndicator());
        }
        requestLayout();
    }


    /**
     * 创建一个指示器
     *
     * @return
     */
    private ImageView createIndicator() {
        ImageView imageView = new ImageView(getContext());
        LayoutParams params = new LayoutParams(
                indicatorWidth == DEFAULT_INDICATOR_WIDTH ? LayoutParams.WRAP_CONTENT : indicatorWidth,
                indicatorHeight == DEFAULT_INDICATOR_HEIGHT ? LayoutParams.WRAP_CONTENT : indicatorHeight);

        if (indicatorSpace < 0) {
            indicatorSpace = DEFAULT_INDICATOR_SPACE;
        }
        params.leftMargin = indicatorSpace;

        if (indicatorNormalDrawable == null) {
            imageView.setImageResource(DEFAULT_INDICATOR_NORMAL_RESID);
        } else {
            imageView.setImageDrawable(indicatorNormalDrawable);
        }
        imageView.setLayoutParams(params);
        return imageView;
    }


    public void setIndicatorSelected(int selectItem) {
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            ImageView imageView = (ImageView) getChildAt(i);
            if (i == selectItem) {
                if (indicatorSelectedDrawable == null) {
                    imageView.setImageResource(DEFAULT_INDICATOR_SELECTED_RESID);
                } else {
                    imageView.setImageDrawable(indicatorSelectedDrawable);
                }
            } else {
                if (indicatorNormalDrawable == null) {
                    imageView.setImageResource(DEFAULT_INDICATOR_NORMAL_RESID);
                } else {
                    imageView.setImageDrawable(indicatorNormalDrawable);
                }
            }
        }
    }
}
