package com.xiaofangfang.butterknitedemo.NetworkEncope;

import android.os.Handler;

import java.io.IOException;

import androidx.fragment.app.Fragment;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public abstract class BaseFragment extends Fragment {


    Handler handler = new Handler();


    public void openNetwork(String url) {

        NetRequestUrl.requestUrl(url, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                //执行在子线程中

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        //我们通过这个handler.的post将请求转发给主线程
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
