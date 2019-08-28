package com.thread.futureTask;


import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class MyFutureTask {


    static MyFutureTask myFutureTask = new MyFutureTask();


    public static void main(String[] argc) {

        MyFutureTask.myFutureTask.start();
        new Thread(() -> {
            System.out.print(MyFutureTask.myFutureTask.get());
        }).start();
        System.out.print("结束了");
        /**
         *
         * 由于FutureTask.get();会阻塞主线程，所以使用了另一个线程进行异步
         */

    }

    private FutureTask<String> futureTask = new FutureTask<String>(() -> loadData());

    /**
     * 模拟加载数据
     *
     * @return
     */
    private String loadData() throws InterruptedException {
        Thread.sleep(3000);
        return "你好啊，没有产品了";
    }

    private final Thread thread = new Thread(futureTask);

    public void start() {
        thread.start();
    }

    public String get() {
        try {
            return futureTask.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }









}
