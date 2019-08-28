package com.xiaofangfang.opensourceframeworkdemo.RxAndroid.Simple5.downUtil;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import rx.Observable;
import rx.Subscriber;

public class DownloadUtil {


    public static byte[] downloadImage(String path) {

        //异步任务在这里编写,本身是一个内部类,有继承关系,所有返回结果很难
        return null;
    }

    interface Callback {
        void callBack(byte[] data);
    }


    /**
     * 上边是传统的开发手法
     * <p>
     * <p>
     * 下边是使用观察者模式的实现
     * 我倒是发现了一个很好的现象,这里我们和context进行了独立,函数在使用的过程中与context
     * 进行了解耦操作
     */


    public OkHttpClient client;

    public DownloadUtil() {
        client = new OkHttpClient();
    }


    public void setClient(OkHttpClient client) {
        this.client = client;
    }

    /**
     * 声明一个被观察者对象作为一个结果进行返回
     *
     * @param path
     * @return
     */
    public Observable<byte[]> downLoadImage(String path) {

        return Observable.create(new Observable.OnSubscribe<byte[]>() {
            @Override
            public void call(Subscriber<? super byte[]> subscriber) {
                if (!subscriber.isUnsubscribed()) {
                    Request request = new Request.Builder().url(path).build();
                    client.newCall(request).enqueue(new okhttp3.Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            subscriber.onError(e);
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            if (response.isSuccessful()) {
                                byte[] data = response.body().bytes();
                                if (data != null) {
                                    subscriber.onNext(data);
                                }
                            }
                        }
                    });
                    subscriber.onCompleted();
                }
            }
        });
    }
}
