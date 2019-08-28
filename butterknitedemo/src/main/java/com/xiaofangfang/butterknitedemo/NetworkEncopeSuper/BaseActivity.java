package com.xiaofangfang.butterknitedemo.NetworkEncopeSuper;

import android.os.Handler;

import com.xiaofangfang.butterknitedemo.NetworkEncope.NetRequestUrl;

import java.io.IOException;

import androidx.appcompat.app.AppCompatActivity;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public abstract class BaseActivity extends AppCompatActivity {


    Handler handler = new Handler();

    public void openNetwork(String url, int what) {

        NetRequestUrl.requestUrl(url, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                //执行在子线程中

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        //我们通过这个handler.的post将请求转发给主线程
                        failth(e, what);
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                String value = response.body().string();

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        success(value, what);
                    }
                });
            }
        });
    }

    protected abstract void failth(Exception e, int what);


    protected abstract void success(String response, int what);


}
