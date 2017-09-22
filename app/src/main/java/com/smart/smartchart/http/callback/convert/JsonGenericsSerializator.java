package com.smart.smartchart.http.callback.convert;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Created by JimGong on 2016/6/23.
 */
public class JsonGenericsSerializator implements IGenericsSerializator {
    @Override
    public <T> T transform(String jsonResult, Class<T> clazz) {
        try {
            Gson gson = new GsonBuilder().create();
            return gson.fromJson(jsonResult, clazz);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
