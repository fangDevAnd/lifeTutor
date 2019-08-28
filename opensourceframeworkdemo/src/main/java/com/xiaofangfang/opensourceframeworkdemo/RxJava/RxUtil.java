package com.xiaofangfang.opensourceframeworkdemo.RxJava;

import android.content.Context;
import android.widget.Toast;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

import rx.Observable;
import rx.functions.Action1;

public class RxUtil {


    public static  void demo1(Context context){

        Observable<String> observable=Observable.just("sgshgwewgweg");

        observable.subscribe(new Action1<String>() {
            @Override
            public void call(String s) {

                Toast.makeText(context, "接收到消息"+s, Toast.LENGTH_SHORT).show();

            }
        });
    }


    public static void demo2(Context context){

        try {
            Observable.just(value()).subscribe(new Action1<String>() {
                @Override
                public void call(String s) {

                    Toast.makeText(context, "得到运算结果"+s, Toast.LENGTH_SHORT).show();
                }
            });

        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public  static  String value() throws ExecutionException, InterruptedException {

        FutureTask<String> futureTask;

        new Thread(futureTask=new FutureTask<String>(new Callable<String>() {
            @Override
            public String call() throws Exception {
                int i=0;
                while (i<10000){
                    i++;
                }
                return "运算结束了"+i;
            }
        })).start();


        return futureTask.get();
    }


}
