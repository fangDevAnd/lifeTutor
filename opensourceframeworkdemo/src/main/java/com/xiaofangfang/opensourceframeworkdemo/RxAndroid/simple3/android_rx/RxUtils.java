package com.xiaofangfang.opensourceframeworkdemo.RxAndroid.simple3.android_rx;

//我们使用的是rx包里面的观察者

import android.util.Log;

import com.orhanobut.logger.Logger;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class RxUtils {

    private static final String TAG = RxUtils.class.getSimpleName();


    /**
     * 创建一个被观察者(第一种方式)
     */
    public static void createObservable() {

        Observable<String> observable = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                if (!subscriber.isUnsubscribed()) {//双重否定代表的是肯定
                    subscriber.onNext("傻逼玩意");
                    subscriber.onNext(downloadJson());
                    subscriber.onNext("world");
                    subscriber.onCompleted();
                }
            }
        });

        Subscriber<String> subscriber = new Subscriber<String>() {
            @Override
            public void onCompleted() {//正常终止
                Logger.d("complete==========>>>");
            }

            //出错
            @Override
            public void onError(Throwable e) {
                Logger.d("error==========>>>");
            }

            /**
             * 调用了下一步
             * @param s
             */
            @Override
            public void onNext(String s) {
                Logger.d("result==========>>>" + s);
            }
        };


        observable.subscribe(subscriber);//关联被观察者


    }


    public static String downloadJson() {
        return "json data";
    }


    /**
     * create的第二种方式
     */
    public static void createPrint() {
        Observable.create(new Observable.OnSubscribe<Integer>() {


            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                if (!subscriber.isUnsubscribed()) {
                    for (int i = 0; i < 10; i++) {
                        subscriber.onNext(i);
                    }
                    subscriber.onCompleted();

                }
            }
        }).subscribe(new Subscriber<Integer>() {
            @Override
            public void onCompleted() {
                Logger.d("complete==========>>>");
            }

            @Override
            public void onError(Throwable e) {
                Logger.d("error==========>>>");
            }

            @Override
            public void onNext(Integer integer) {
                Logger.d("next==========>>>" + integer);
            }
        });
    }


    /**
     * from操作
     * 将其他种类的对象和数据类型转换为Observable
     * <p>
     * 当你使用Obserable时,如果你要处理的数据都可以转换成展示为Observables
     * ,而不是需要混合使用Observables和其他类型的数据
     */
    public static void from() {

        Integer[] items = {1, 2, 3, 4, 5, 6, 7, 8, 9};

        Observable observable = Observable.from(items);
        observable.subscribe(new Action1() {
            @Override
            public void call(Object o) {
                Log.d(TAG, "call: ======" + o.toString());
            }
        });






    }

    /**
     * Z指定一个时刻进行发送
     */
    public static void interval() {
        Integer[] integer = {1, 2, 3, 4, 5, 6, 7};

        Observable observable = Observable.interval(2, TimeUnit.SECONDS);
        observable.subscribe(new Action1() {
            @Override
            public void call(Object o) {
                Logger.d("==============" + o.toString());
            }
        });

    }


    /**
     * 处理数组集合
     */
    public static void just() {
        Integer[] integers = {1, 2, 3, 4, 5, 6, 7, 8};
        Integer[] integers1 = {1, 2, 3, 5, 6, 7, 8, 8};

        Observable observable = Observable.just(integers1, integers);
        observable.subscribe(new Subscriber<Integer[]>() {
            @Override
            public void onCompleted() {
                Log.d(TAG, "onCompleted: ");
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "onError: ===" + e.getMessage());
            }

            @Override
            public void onNext(Integer[] o) {
                for (int i : o) {
                    Log.d(TAG, "onNext: " + i);
                }
            }
        });
    }


    /**
     * 使用范围数据,指定输出数据的范围
     */
    public static void range() {

        Observable observable = Observable.range(1, 40);
        observable.subscribe(new Subscriber<Integer>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Integer o) {
                Log.d(TAG, "onNext: " + o);
            }
        });
    }


    /**
     * 过滤条件.过滤之后再去触发订阅
     */
    public static void filter() {

        Observable observable = Observable.just(1, 2, 3, 4, 5, 6, 6);

        observable.filter(new Func1<Integer, Boolean>() {
            @Override
            public Boolean call(Integer o) {
                return o < 5;//返回的结果必须小于5
            }
        }).observeOn(Schedulers.io()).subscribe(new Subscriber<Integer>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Integer o) {
                Log.d(TAG, "onNext: =====" + o.toString());
            }
        });
    }


}
