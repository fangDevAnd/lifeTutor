package com.xiaofangfang.butterknitedemo.NetworkequestEncapsulation;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.util.logging.Logger;

import androidx.fragment.app.Fragment;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public abstract class BaseFragment extends Fragment {


    private static final String TAG = "test";

    public void sendNetwork(String url) {


        NetRequest.requestUrl(url, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {


                Handler handler = new Handler(Looper.getMainLooper());

                handler.post(new Runnable() {
                    @Override
                    public void run() {

                        Toast.makeText(getContext(), "发生错误", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "run: " + e.getMessage());
                        failure(e);
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String value = response.body().string();

                Handler handler = new Handler(Looper.getMainLooper());
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        success(value);
                    }
                });
            }
        });
    }


    public abstract void failure(Exception e);

    public abstract void success(String string);


}
