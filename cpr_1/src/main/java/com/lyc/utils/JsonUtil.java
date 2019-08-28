package com.lyc.utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.HashMap;

public class JsonUtil {


    /**
     * 将一个对象序列化问一个Json
     * @param obj
     * @return
     */
    public static String ToJson(Object obj) {
        Gson gson = new Gson();
        return gson.toJson(obj);
    }


    /**
     * 将一个json数据反序列化为一个对象
     * @param json
     * @param t
     * @param <T>
     * @return
     */
    public static <T> T ToObject(String json, Class<T> t) {
        try {
            Gson gson = new Gson();

            return gson.fromJson(json, t);
        } catch (Exception e) {
            return null;
        }

    }

    /**
     * 将json数据序列化为一个HashMap
     * @param json
     * @return
     */
    public static HashMap<String, String> toMap(String json){
        Gson gson = new Gson();
        HashMap map=  (HashMap)gson.fromJson(json, new TypeToken<HashMap<String, String>>(){}.getType());
        return map;
    }

    /**
     * 返回指定类型的json反序列化对象
     * @param json
     * @param t
     * @param <T>
     * @return
     */
    public static <T> T ToObjectByType(String json, Type t) {
        try {
            Gson gson = new Gson();

            return gson.fromJson(json, t);
        } catch (Exception e) {
            LogUtil.WriteLog(e);
            return null;
        }

    }

}
