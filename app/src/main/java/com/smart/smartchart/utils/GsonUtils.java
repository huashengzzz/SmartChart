package com.smart.smartchart.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GsonUtils {

    public static <T> T parser(String jsonResult, Class<T> clazz) {
        try {
            Gson gson = new GsonBuilder().create();
            return gson.fromJson(jsonResult, clazz);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String object2Json(Object obj) {
        Gson gson = new GsonBuilder().create();
        return gson.toJson(obj);
    }
}