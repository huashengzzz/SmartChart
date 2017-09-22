package com.smart.smartchart.utils;

import android.content.Context;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.TextView;

import static android.view.View.GONE;
import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

/**
 * 操作 {@link View}的工具类， Utilities for working with the {@link View} class
 */
public class ViewUtil {
    ViewUtil() {
    }

    public static boolean isVisibile(View v) {
        return v.getVisibility() == View.VISIBLE;
    }

    public static boolean isGone(View v) {
        return v.getVisibility() == View.GONE;
    }

    public static boolean isInvisible(View v) {
        return v.getVisibility() == View.INVISIBLE;
    }

    /**
     * 设置选中
     *
     * @param view
     * @param isSelected
     */
    public static void setSelected(View view, boolean isSelected) {
        view.setSelected(isSelected);
    }

    public static void setSelected(boolean isSelected, View... views) {
        for (View view : views) {
            setSelected(view, isSelected);
        }
    }

    /**
     * 设置多个控件 的状态为非选中
     *
     * @param views
     */
    public static void setUnSelected(View... views) {
        for (View view : views) {
            setSelected(view, false);
        }
    }

    /**
     * 是否隐藏view
     *
     * @param v
     * @param gone :是否GONE
     */
    public static void gone(View v, final boolean gone) {
        if (gone) {
            if (GONE != v.getVisibility())
                v.setVisibility(View.GONE);
        } else {
            if (VISIBLE != v.getVisibility())
                v.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 隐藏view
     *
     * @param v
     */
    public static void gone(View v) {
        gone(v, true);
    }

    public static void gone(View... views) {
        for (View view : views) {
            gone(view, true);
        }
    }

    /**
     * 显示view
     *
     * @param v
     */
    public static void visible(View v) {
        gone(v, false);
    }

    public static void visible(View... views) {
        for (View view : views) {
            gone(view, false);
        }
    }

    /**
     * 是否invisibile
     *
     * @param v
     * @param invisible
     */
    public static void invisible(View v, final boolean invisible) {
        if (invisible) {
            if (INVISIBLE != v.getVisibility())
                v.setVisibility(View.INVISIBLE);
        } else {
            if (VISIBLE != v.getVisibility())
                v.setVisibility(View.VISIBLE);
        }
    }

    public static void invisible(View v) {
        invisible(v, true);
    }

    public static void invisible(View... views) {
        for (View view : views) {
            invisible(view, true);
        }
    }

    /**
     * 设置TextView 的Color
     *
     * @param resId
     * @param views
     */
    public static void setTextViewColor(Context context, int resId, TextView... views) {
        for (TextView view : views) {
            setTextViewColor(context, resId, view);
        }
    }

    /**
     * 设置TextView 的Color
     *
     * @param resId
     * @param textView
     */
    public static void setTextViewColor(Context context, int resId, TextView textView) {
        textView.setTextColor(context.getResources().getColor(resId));
    }


    /**
     * 使用{@link Animation}隐藏view
     *
     * @param v
     * @param isGoneType :true 使用{@link #gone(View, boolean)},false 使用
     *                   {@link #invisible(View, boolean)}
     * @param gone
     * @param animation
     */
    public static void animHidden(final View v, final boolean isGoneType, final boolean gone, Animation animation) {
        if (isGoneType) {
            if (gone && View.GONE == v.getVisibility()) {
                return;
            }

        } else {
            if (gone && View.INVISIBLE == v.getVisibility()) {
                return;
            }
        }
        if (!gone && View.VISIBLE == v.getVisibility()) {
            return;
        }
        animation.setAnimationListener(new IViewAnimationListener(v, isGoneType, gone));
        v.startAnimation(animation);
    }

    /**
     * 使用{@link Animation}隐藏(GONE)view
     *
     * @param v
     * @param gone
     * @param animation
     */
    public static void goneAnim(final View v, final boolean gone, Animation animation) {
        animHidden(v, true, gone, animation);
    }

    /**
     * 使用{@link Animation}隐藏(INVISIBILE)view
     *
     * @param v
     * @param invisibile
     * @param animation
     */
    public static void invisibleAnim(final View v, final boolean invisibile, Animation animation) {
        animHidden(v, false, invisibile, animation);
    }

    /**
     * 使用默认AlphaAnimation动画、默认500ms隐藏view
     *
     * @param v
     * @param gone
     */
    public static void alphaAnimHidden(final View v, final boolean isGoneType, final boolean gone) {
        alphaAnimHidden(v, isGoneType, gone, 500);
    }

    /**
     * 使用默认AlphaAnimation动画隐藏view
     *
     * @param v
     * @param gone
     * @param duration :animation duration
     */
    public static void alphaAnimHidden(final View v, final boolean isGoneType, final boolean gone, long duration) {
        Animation animation = new AlphaAnimation(1, 0);
        if (!gone) {
            animation = new AlphaAnimation(0, 1);
        }
        animation.setDuration(duration);
        animHidden(v, isGoneType, gone, animation);
    }

    /**
     * 使用默认AlphaAnimation动画、默认500ms隐藏(GONE)view
     *
     * @param v
     * @param gone
     */
    public static void alphaAnimGone(final View v, final boolean gone) {
        alphaAnimHidden(v, true, gone);
    }

    /**
     * 使用默认AlphaAnimation动画隐藏(GONE)view
     *
     * @param v
     * @param gone
     * @param duration
     */
    public static void alphaAnimGone(final View v, final boolean gone, long duration) {
        alphaAnimHidden(v, true, gone, duration);
    }

    /**
     * 使用默认AlphaAnimation动画、默认500ms隐藏(GONE)view
     *
     * @param v
     * @param gone
     */
    public static void alphaAnimInvisible(final View v, final boolean invisible) {
        alphaAnimHidden(v, false, invisible);
    }

    /**
     * 使用默认AlphaAnimation动画隐藏(GONE)view
     *
     * @param v
     * @param gone
     * @param duration
     */
    public static void alphaAnimInvisible(final View v, final boolean invisible, long duration) {
        alphaAnimHidden(v, false, invisible, duration);
    }

    /**
     * 使用{@link Animation}时候的监听，在动画结束之后完成隐藏
     */
    private static class IViewAnimationListener implements AnimationListener {
        private View v;
        private boolean check;
        private boolean isGoneType;

        public IViewAnimationListener(View v, boolean isGoneType, boolean gone) {
            super();
            this.v = v;
            this.isGoneType = isGoneType;
            this.check = gone;
        }

        @Override
        public void onAnimationStart(Animation animation) {
        }

        @Override
        public void onAnimationEnd(Animation animation) {
            if (isGoneType) {
                gone(v, check);
            } else {
                invisible(v, check);
            }
        }

        @Override
        public void onAnimationRepeat(Animation animation) {
        }

    }


}
