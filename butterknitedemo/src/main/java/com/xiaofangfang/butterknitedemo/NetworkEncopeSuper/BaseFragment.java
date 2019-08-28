package com.xiaofangfang.butterknitedemo.NetworkEncopeSuper;

import android.content.Context;
import android.os.Handler;

import com.xiaofangfang.butterknitedemo.NetworkEncope.NetRequestUrl;

import java.io.IOException;

import androidx.fragment.app.Fragment;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public abstract class BaseFragment extends Fragment {

    protected BaseActivity context;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = (BaseActivity) context;
    }


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

    public abstract void failth(Exception e, int what);

    public abstract void success(String response, int what);


    public <T extends BaseActivity> T getMyActivity() {

        return (T) getActivity();
    }


}
