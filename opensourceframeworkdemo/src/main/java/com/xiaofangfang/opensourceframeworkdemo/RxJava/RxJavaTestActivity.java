package com.xiaofangfang.opensourceframeworkdemo.RxJava;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.xiaofangfang.opensourceframeworkdemo.R;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.functions.Action1;
import rx.functions.Func1;

public class RxJavaTestActivity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rxjava);



        findViewById(R.id.button4).setOnClickListener((v)->{

            RxUtil.demo1(this);

        });


        /**
         * 使用了RxJava实现了一个异步的操作
         */
        findViewById(R.id.button5).setOnClickListener((v)->{

            RxUtil.demo2(this);
        });






        findViewById(R.id.button6).setOnClickListener((v)->{

            try {
                Observable.just(RxUtil.value()).map(new Func1<String,  String>() {

                    /**
                     * map函数的作用是用来进行数据的转换  ，我们使用map对我们just里面返回的数据进行了在包装
                     *
                     *
                     * @param s
                     * @return
                     */
                    @Override
                    public String call(String s) {
                        return s+"  你好";
                    }
                }).subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        Toast.makeText(RxJavaTestActivity.this, "接收到消息"+s, Toast.LENGTH_SHORT).show();
                    }
                });


            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        });


        findViewById(R.id.button7).setOnClickListener((v)->{
            Observable.from(new FutureTask<String>(new Callable<String>() {
                @Override
                public String call() throws Exception {

                /*8
                用于执行异步方法
                 */
                    int i=0;
                    while (i<10000){
                        i++;
                    }
                    return "计算结束"+i;
                }
            })).all(new Func1<String, Boolean>() {
                @Override
                public Boolean call(String s) {

                    return true;
                }
            }).subscribe(new Observer<Boolean>() {
                @Override
                public void onCompleted() {
                    Log.d("test","计算完成================onCompleted");
                }

                @Override
                public void onError(Throwable e) {
                    Log.d("test","计算完成================onError");
                }

                @Override
                public void onNext(Boolean aBoolean) {
                    Log.d("test","计算完成================onNext");
                }
            });
        });



        findViewById(R.id.button8).setOnClickListener((v)->{



            Observable.create(new Observable.OnSubscribe<String> () {
                @Override
                public void call(Subscriber<? super String> subscriber) {
                          if(!subscriber.isUnsubscribed()){

                              for(int i=0;i<10;i++) {
                                  subscriber.onNext("消息"+i);
                              }
                      }
                }
            }).subscribe(new Action1<String>() {
                @Override
                public void call(String s) {
                    Toast.makeText(RxJavaTestActivity.this, "收到消息"+s, Toast.LENGTH_SHORT).show();
                }
            });



        });

    }







}
