package com.xiaofangfang.filterrice.Fragment;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.xiaofangfang.filterrice.R;
import com.xiaofangfang.filterrice.model.MyResponseData;
import com.xiaofangfang.filterrice.service.CacheService;
import com.xiaofangfang.filterrice.tool.NetRequest;
import com.xiaofangfang.filterrice.tool.UiThread;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class FragmentPageFour extends Fragment {


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //onloadData();
    }

    private String url;

    private void onloadData() {

        url = "http://localhost:8080/TestTag?"
                + "action=" + this.getClass().getSimpleName();

        Log.d("test", "url=" + url);

        new Thread(new Runnable() {
            @Override
            public void run() {

                NetRequest.requestUrl(url, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        UiThread.getUiThread().post(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getContext(), "请求失败", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        final String data = response.body().string();

                        while (viewList.size() == 0) {

                        }

                        UiThread.getUiThread().post(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getContext(), "请求成功", Toast.LENGTH_SHORT).show();
                                responseBackData(data);
                            }
                        });
                    }
                });


            }
        }).start();

    }

    private void responseBackData(String data) {

        Gson gson = new Gson();
        MyResponseData responseData = gson.fromJson(data, MyResponseData.class);

        //在下面进行判断
        for (int i = 0; i < viewList.size(); i++) {
            if (responseData.getViewName().equals(viewList.get(i).getClass().getSimpleName())) {

                progressViewNameEqual(responseData, i);

            } else {//不相等
                progressViewNameNotEqual(responseData, i);
            }
        }

    }


    /**
     * 定义内部的版本号为1
     */
    int version = 1;


    /**
     * 处理view的名称相同的情况
     *
     * @param responseData
     * @param i
     */
    private void progressViewNameEqual(MyResponseData responseData, int i) {

        if (responseData.getVersion() == version) {//内部版本相同

            //不去更新数据，缓存数据

        } else {//内部版本不相同
            //
            cacheData(responseData, i);
            View view = viewList.get(i);
            view.getId();


        }


    }

    /**
     * 缓存数据
     * 在这里我门清楚接口的组成，也就代表者我门可以对内部的数据进行缓存处理
     * 单就对MyResponseData来说，imageAddress代表着图片地址，完全可以根据这点进行图片的缓存
     *
     * @param responseData
     * @param index
     */
    private void cacheData(MyResponseData responseData, int index) {

        Intent intent = new Intent(getContext(), CacheService.class);
        intent.putExtra(MyResponseData.class.getSimpleName(), responseData);
        intent.putExtra("index", index);
        getActivity().startService(intent);
    }

    /**
     * 处理view的名称不同的情况
     *
     * @param responseData
     * @param i
     */
    private void progressViewNameNotEqual(MyResponseData responseData, int i) {


    }


    ViewGroup view;

    List<View> viewList;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = (ViewGroup) inflater.inflate(R.layout.fragment_page_one, container, false);

        viewList = new ArrayList<>();
        for (int i = 0; i < view.getChildCount(); i++) {
            viewList.add(view.getChildAt(i));
        }

        initView((ViewGroup) view);

        return view;
    }

    private void initView(ViewGroup view) {


    }


}
