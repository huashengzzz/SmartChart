package com.smart.smartchart.http.builder;


import com.smart.smartchart.http.OkHttpUtils;
import com.smart.smartchart.http.request.OtherRequest;
import com.smart.smartchart.http.request.RequestCall;

/**
 * Created by zhy on 16/3/2.
 */
public class HeadBuilder extends GetBuilder {
    @Override
    public RequestCall build() {
        return new OtherRequest(null, null, OkHttpUtils.METHOD.HEAD, url, tag, params, headers, id).build();
    }
}
