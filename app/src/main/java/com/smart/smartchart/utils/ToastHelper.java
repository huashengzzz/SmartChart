package com.smart.smartchart.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Gs on 2016/5/25.
 */
public class ToastHelper {

    /**
     * 短时间显示Toast
     *
     * @param mContext
     * @param msg
     */
    public static void shortToast(Context mContext, String msg) {
        showToast(mContext, msg, Toast.LENGTH_SHORT);
    }

    /**
     * 短时间显示Toast
     *
     * @param mContext
     * @param msgResId
     */
    public static void shortToast(Context mContext, int msgResId) {
        showToast(mContext, msgResId, Toast.LENGTH_SHORT);
    }

    /**
     * 长时间显示Toast
     *
     * @param mContext
     * @param msg
     */
    public static void longToast(Context mContext, String msg) {
        showToast(mContext, msg, Toast.LENGTH_LONG);
    }

    /**
     * 长时间显示Toast
     *
     * @param mContext
     * @param msgResId
     */
    public static void longToast(Context mContext, int msgResId) {
        showToast(mContext, msgResId, Toast.LENGTH_LONG);
    }

    /**
     * 自定义显示Toast时间
     *
     * @param mContext
     * @param msg
     * @param duration
     */
    public static void showToast(Context mContext, String msg, int duration) {
        Toast.makeText(mContext, msg, duration).show();
    }

    /**
     * 自定义显示Toast时间
     *
     * @param mContext
     * @param msgResId
     * @param duration
     */
    public static void showToast(Context mContext, int msgResId, int duration) {
        Toast.makeText(mContext, msgResId, duration).show();
    }

    //*****************WITH GRAVITY************************//

    public static void showToastWithGravity(Context ctx, int resID, int gravity) {
        showToastWithGravity(ctx, Toast.LENGTH_SHORT, resID, gravity);
    }

    public static void showToastWithGravity(Context ctx, String text, int gravity) {
        showToastWithGravity(ctx, Toast.LENGTH_SHORT, text, gravity);
    }

    public static void showToastWithGravity(Context ctx, int duration, int resID, int gravity) {
        showToastWithGravity(ctx, duration, ctx.getString(resID), gravity);
    }

    public static void showToastWithGravity(Context ctx, int duration, String text, int gravity) {
        Toast toast = Toast.makeText(ctx, text, duration);
        toast.setGravity(gravity, 0, 250);
        toast.show();
    }
}
