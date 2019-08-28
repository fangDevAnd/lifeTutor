package com.example.jsontosql;

import android.content.Context;
import android.content.res.AssetManager;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class GetJsonDataUtil {

    private Context context;

    private String fileName = "city.json";


    public String getJson(Context context, String fileName) {
        this.context = context;
        StringBuilder stringBuilder = new StringBuilder();
        try {
            AssetManager assetManager = context.getAssets();
            BufferedReader bf = new BufferedReader(new InputStreamReader(assetManager.open(fileName)));
            String line;
            while ((line = bf.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }


    public void getProvinceList(final Context context, final Callback callback)  {

        new Thread(new Runnable() {
            @Override
            public void run() {
                String json = getJson(context, fileName);
                JSONArray jsonArray = null;
                try {
                    jsonArray = new JSONArray(json);
                    List<JSONObject> objects=new ArrayList<>();
                    for(int i=0;i<jsonArray.length();i++){
                        objects.add(jsonArray.getJSONObject(i));
                    }
                    callback.getData(objects);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

    public static  interface  Callback{
        void getData(List<? extends JSONObject> data) throws JSONException;
    }





    public List<JSONObject> getCityList(JSONObject jsonObject) throws JSONException {


        List<JSONObject> objects=new ArrayList<>();

        JSONArray jsonArray=jsonObject.getJSONArray("cityList");
        for(int i=0;i<jsonArray.length();i++){
            objects.add(jsonArray.getJSONObject(i));
        }
        return objects;
    }


    public List<JSONObject> getAreaList(JSONObject jsonObject) throws JSONException {

        List<JSONObject> objects=new ArrayList<>();

        JSONArray jsonArray=jsonObject.getJSONArray("areaList");
        for(int i=0;i<jsonArray.length();i++){
            objects.add(jsonArray.getJSONObject(i));
        }
        return objects;
    }


}
