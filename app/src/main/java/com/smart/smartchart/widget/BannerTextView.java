package com.smart.smartchart.widget;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Handler;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Gs on 2016/5/5.
 */
public class BannerTextView extends TextView {

    private ArrayList<String> textList;


    private FastOutSlowInInterpolator interpolator = new FastOutSlowInInterpolator();
    private OnItemClick onItemClick;
    private boolean switched = false;
    private Handler mHandler;
    private float animY = 0;
    private float animX = 0;
    private int currentPosition = 0;
    private int animType = 2;
    public final static int ANIM_TYPE_VERTICAL = 1;
    public final static int ANIM_TYPE_HORIZONTAL = 2;
    private int animDuration = 700;
    private int intervalTime = 5000;
    private ValueAnimator anim;

    public interface OnItemClick {
        void onClick(View view, int position, String text, Object extraData);
    }

    public void setOnItemClick(OnItemClick onItemClick) {
        this.onItemClick = onItemClick;
    }

    public BannerTextView(Context context) {
        super(context);
        init();
    }

    public BannerTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);

    }

    public BannerTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (animType == ANIM_TYPE_VERTICAL) {
            canvas.translate(getPaddingLeft(), animY);
        } else if (animType == ANIM_TYPE_HORIZONTAL) {
            canvas.translate(animX, 0);
        }

        super.onDraw(canvas);


    }


    public void addItem(String item) {
        if (textList == null) {
            textList = new ArrayList<>();
        }
        if (item != null && !"".equals(item)) {
            textList.add(item);
        }
        if (!textList.isEmpty()) {
            setText(textList.get(0));
        }
    }

    private void init() {
        mHandler = new Handler();
        setGravity(Gravity.CENTER_VERTICAL);
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClick != null) {
                    onItemClick.onClick(v, currentPosition, textList.get(currentPosition), null);
                }
            }
        });
    }

    public void clearData() {
        if (textList != null) {
            textList.clear();
        }

    }

    public void addItem(ArrayList<String> list) {
        if (textList == null) {
            textList = new ArrayList<>();
        }

        if (list != null && !list.isEmpty()) {
            textList.addAll(list);
        }
        if (!textList.isEmpty()) {
            setText(textList.get(0));
        }
    }

    private int getHeightS() {
        TextPaint textPaint = new TextPaint(TextPaint.ANTI_ALIAS_FLAG);
        textPaint.setTextSize(getTextSize());
        StaticLayout staticLayout = new StaticLayout(textList.get(currentPosition), textPaint, getWidth() - getPaddingLeft() - getPaddingRight(), Layout.Alignment.ALIGN_NORMAL, 1.0f, 0, false);
        int height = staticLayout.getHeight();
        if (staticLayout.getLineCount() > getMaxLines()) {
            int lineCount = staticLayout.getLineCount();
            height = staticLayout.getLineBottom(getMaxLines() - 1);
        }
        return height;
    }

    private void start() {
        if (animType == ANIM_TYPE_VERTICAL) {
            animY = getHeight() * .5F - getHeightS() * .5F;
            anim = ValueAnimator.ofFloat(animY, 0, -getHeight() * .5F - getHeightS() * 1.5F);
            anim.setInterpolator(interpolator);
            anim.setDuration(700);
            anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    float value = (Float) animation.getAnimatedValue();
                    if (value < -getHeightS()) {
                        if (!switched) {
                            setText(textList.get(currentPosition));
                            switched = true;
                        }
                        animY = value + getHeight() + getHeightS();
                    } else {
                        animY = value;
                    }
                    animY -= (int) ((0.5 * getHeight() - 0.5 * getHeightS()));
                    invalidate();
                }
            });

            anim.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {

                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    switched = false;
                    play();
                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });

            anim.start();
        } else if (animType == ANIM_TYPE_HORIZONTAL) {
            animX = 0;
            TextPaint p = new TextPaint(Paint.ANTI_ALIAS_FLAG);
            float textWidth = p.measureText(textList.get(currentPosition));
            final float end = Math.min(getWidth() - getPaddingRight(), textWidth + getPaddingLeft());
            final ValueAnimator anim = ValueAnimator.ofFloat(animX, 0, end + getWidth() - getPaddingRight());
            anim.setInterpolator(interpolator);
            anim.setDuration(animDuration);
            anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    float value = (Float) animation.getAnimatedValue();
                    if (value > end) {
                        if (!switched) {
                            setText(textList.get(currentPosition));
                            switched = true;
                        }
                        animX = getWidth() - getPaddingRight() - (value - end);
                    } else {
                        animX = -value;
                    }

                    invalidate();
                }
            });

            anim.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {

                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    switched = false;
                    play();
                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });

            anim.start();
        }

    }


    public void play() {
        if (textList == null || textList.size() < 2) {
            return;
        }
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (currentPosition + 1 < textList.size()) {
                    currentPosition++;
                } else {
                    currentPosition = 0;
                }
                start();
            }
        }, intervalTime);
    }

    @Override
    public void setGravity(int gravity) {
        super.setGravity(Gravity.CENTER_VERTICAL);
    }

    public void setIntervalTime(int intervalTime) {
        this.intervalTime = intervalTime;
    }

    public void setAnimDuration(int duration) {
        animDuration = duration;
    }

    public void setOrientationVertical(boolean flag) {
        if (flag)
            animType = 1;
        else
            animType = 2;

    }

    public void stopAnim() {
        if (anim != null)
            anim.cancel();
        if (mHandler != null)
            mHandler.removeCallbacksAndMessages(null);
        if (textList != null)
            textList.clear();
        currentPosition=0;
        switched = false;
        animY = 0;
        animX = 0;
        animType = 2;
    }

}
