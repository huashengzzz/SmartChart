package com.smart.smartchart.http.callback;


import android.app.Activity;
import android.app.ProgressDialog;

import com.orhanobut.logger.Logger;
import com.smart.smartchart.R;
import com.smart.smartchart.http.callback.convert.IGenericsSerializator;
import com.smart.smartchart.http.callback.convert.JsonGenericsSerializator;
import com.smart.smartchart.utils.ToastHelper;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;

import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

public abstract class GenericsCallback<T> extends Callback<T> {
    private IGenericsSerializator mGenericsSerializator;
    private Activity activity;
    private ProgressDialog progressDialog;

    public GenericsCallback(Activity activity, IGenericsSerializator serializator) {
        mGenericsSerializator = serializator;
        this.activity = activity;
    }

    public GenericsCallback(Activity activity) {
        mGenericsSerializator = new JsonGenericsSerializator();
        this.activity = activity;
    }

    @Override
    public void onBefore(Request request, int id) {
        super.onBefore(request, id);
        progressDialog = new ProgressDialog(activity);
        progressDialog.setMessage("数据加载中...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
    }


    @Override
    public T parseNetworkResponse(Response response, int id) throws IOException {
        String string = response.body().string();
        Class<T> entityClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        if (entityClass == String.class) {
            return (T) string;
        }
        Logger.json(string);
        T bean = mGenericsSerializator.transform(string, entityClass);
        return bean;
    }

//    @Override
//    public void onResponse(T response, int id) {
//
//        if (response instanceof BaseData) {
//            int code = Integer.parseInt(((BaseData) response).getCode());
//            if (code < 100 && code > 0) {
//                SPManager.clearUserInfo(activity_message);
//                ToastHelper.longToast(activity_message, ((BaseData) response).getMsg());
//                Intent intent = new Intent(activity_message, LoginActivity.class);
//                activity_message.startActivity(intent);
//                return;
//            }
//        }
//    }

    @Override
    public void onError(Call call, Exception e, int id) {
        ToastHelper.shortToast(activity, activity.getString(R.string.net_error));
    }

    @Override
    public void onAfter(int id) {
        super.onAfter(id);
        if (progressDialog != null) {
            progressDialog.dismiss();
            progressDialog = null;
        }

    }
}
