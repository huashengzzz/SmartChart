package com.smart.smartchart.http;

import android.text.TextUtils;

import com.smart.smartchart.http.builder.PostFormBuilder;
import com.smart.smartchart.http.callback.Callback;
import com.smart.smartchart.http.request.RequestCall;

import java.util.Map;


/**
 * Created by Gs on 2016/8/24.
 */
public class OkHttpManager {

    public static void get(String url, Callback callback) {
        OkHttpUtils.get().url(url).build().execute(callback);
    }




    public static void post(String url, Map<String, String> params, Callback callback) {
        post(url, params, null, "", callback);
    }

    public static void post(String url, Map<String, String> params, String head, Callback callback) {
        post(url, params, null, head, callback);
    }


    public static void post(String url, Map<String, String> params, Object tag, String head, Callback callback) {


        //构建请求
        PostFormBuilder postFormBuilder = OkHttpUtils.post()
                .url(url)
                .params(params).addHeader("client-token", "token=");
        if (tag != null) {
            postFormBuilder.tag(tag);
        }
        if (!TextUtils.isEmpty(head)) {
            postFormBuilder.addHeader("client-token", head);
        }
        RequestCall build = postFormBuilder.build();
        build.execute(callback);
    }

}
