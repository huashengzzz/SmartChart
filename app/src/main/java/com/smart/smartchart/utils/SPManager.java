package com.smart.smartchart.utils;

import android.content.Context;
import android.text.TextUtils;

import com.smart.smartchart.utils.constant.SharedPreferencesKey;


/**
 * Created by Gs on 2016/6/1.
 * <p>
 * 获取 以及 设置 sharedpreferences存贮的值
 */
public class SPManager {

    public static void clearUserInfo(Context context) {
        setAuthCode(context, "");
        setUserName(context, "");
        setRealName(context,"");
    }

    public static boolean isLogin(Context context) {
        return !TextUtils.isEmpty(SPManager.getAuthCode(context));
    }

    public static String getAuthCode(Context context) {
        return (String) SPUtils.get(context, SharedPreferencesKey.SP_KEY_TOKEN, "");
    }


    public static String getUsername(Context context) {
        return (String) SPUtils.get(context, SharedPreferencesKey.SP_KEY_USERNAME, "");
    }

    public static String getRealName(Context context) {
        return (String) SPUtils.get(context, SharedPreferencesKey.SP_KEY_REALNAME, "");
    }

    public static boolean getExit(Context context) {
        return (boolean) SPUtils.get(context, SharedPreferencesKey.SP_KEY_EXIT, false);
    }


//=============================华丽的分割线=========================================================


    public static void setAuthCode(Context context, String authCode) {
        SPUtils.put(context, SharedPreferencesKey.SP_KEY_TOKEN, authCode, false);
    }

    public static void setUserName(Context context, String name) {
        SPUtils.put(context, SharedPreferencesKey.SP_KEY_USERNAME, name, false);
    }

    public static void setRealName(Context context, String name) {
        SPUtils.put(context, SharedPreferencesKey.SP_KEY_REALNAME, name, false);
    }

    public static void setExit(Context context, boolean flag) {
        SPUtils.put(context, SharedPreferencesKey.SP_KEY_EXIT, flag, false);
    }


}
