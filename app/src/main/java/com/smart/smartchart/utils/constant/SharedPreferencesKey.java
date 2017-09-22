package com.smart.smartchart.utils.constant;

/**
 * Created by linhe on 2016/8/23.
 */
public interface SharedPreferencesKey {

    /**
     * user authCode
     */
    String SP_KEY_TOKEN = "token";

    /**
     * 用户手机号
     */
    String SP_KEY_STAFF_PHONE = "staff_phone";

    /**
     * 用户登录时输入的用户名
     */
    String SP_KEY_USERNAME = "username";
    /**
     * 用户真实姓名
     */
    String SP_KEY_REALNAME="realname";


    /**
     * 移动设备唯一ID（deviceId  + mac）
     */
    String SP_KEY_UMID = "sp_key_umid";
    /**
     * 成功登录的身份证json串
     */
    String SP_KEY_CARD="sp_key_card";
    /**
     * 用户是否退出应用程序
     */
    String SP_KEY_EXIT="sp_key_exit";

}
