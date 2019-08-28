package com.xiaofangfang.butterknitedemo.NetworkEncopeDemo;

import android.os.Handler;

import java.io.IOException;

import androidx.fragment.app.Fragment;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public abstract class BaseFragment extends Fragment {  //loginFragment   musicFragment


    private Handler handler = new Handler();


    public void sendRequest(String url) {

        NetRequestUtil.sendRequest(url, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        failth(e);
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                String value = response.body().string();

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        success(value);
                    }
                });
            }
        });
    }


    public abstract void failth(Exception e);

    public abstract void success(String response);


}
