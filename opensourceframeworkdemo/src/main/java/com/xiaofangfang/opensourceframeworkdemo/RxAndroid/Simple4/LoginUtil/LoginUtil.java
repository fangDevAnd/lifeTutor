package com.xiaofangfang.opensourceframeworkdemo.RxAndroid.Simple4.LoginUtil;


import java.io.IOException;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;

/**
 * 使用的是okhttp访问网络
 */
public class LoginUtil {

    private OkHttpClient client;

    public LoginUtil() {
        client = new OkHttpClient();
    }

    /**
     * 定义了login的操作,使用了Observable的设计模式
     *
     * @param url
     * @param params
     * @return
     */
    public Observable<String> login(String url, Map<String, String> params) {

        return Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                if (!subscriber.isUnsubscribed()) {
                    FormBody.Builder builder = new FormBody.Builder();

                    if (params != null && !params.isEmpty()) {

                        for (Map.Entry<String, String> entry : params.entrySet()) {

                            builder.add(entry.getKey(), entry.getValue());
                        }
                    }

                    RequestBody requestBody = builder.build();

                    Request request = new Request.Builder().url(url).post(requestBody).build();

                    client.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            subscriber.onError(e);
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            if (response.isSuccessful()) {
                                subscriber.onNext(response.body().string());
                            }
                            subscriber.onCompleted();//访问结束
                        }
                    });


                }
            }
        });
    }


}
