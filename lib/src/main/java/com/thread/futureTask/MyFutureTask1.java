package com.thread.futureTask;

import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class MyFutureTask1 {

    static MyFutureTask1 myFutureTask1 = new MyFutureTask1();


    public static void main(String[] argc) {

//        myFutureTask1.demo1();
//        myFutureTask1.demo2();
        myFutureTask1.demo3();

    }


    void demo1() {
        //step2:创建计算任务，作为参数，传入FutureTask
        Task task = new Task();
        FutureTask futureTask = new FutureTask(task);
//step3:将FutureTask提交给Thread执行
        Thread thread1 = new Thread(futureTask);
        thread1.setName("task thread 1");
        thread1.start();


        //step4:获取执行结果，由于get()方法可能会阻塞当前调用线程，如果子任务执行时间不确定，最好在子线程中获取执行结果
        try {
            // boolean result = (boolean) futureTask.get();
            boolean result = (boolean) futureTask.get(5, TimeUnit.SECONDS);
            System.out.print("result:" + result);
        } catch (InterruptedException e) {
            System.out.print("守护线程阻塞被打断...");
            e.printStackTrace();
        } catch (ExecutionException e) {
            System.out.print("执行任务时出错...");
            e.printStackTrace();
        } catch (TimeoutException e) {
            System.out.print("执行超时...");
            futureTask.cancel(true);
            e.printStackTrace();
        } catch (CancellationException e) {
            //如果线程已经cancel了，再执行get操作会抛出这个异常
            System.out.print("future已经cancel了...");
            e.printStackTrace();
        }
    }

    void demo2() {

        Task task = new Task();
        ExecutorService executorService = Executors.newCachedThreadPool();
        final Future<Boolean> future = executorService.submit(task);
        //step4：通过future获取执行结果
        boolean result = false;
        try {
            result = (boolean) future.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        System.out.print("结果" + result);

    }

    void demo3() {

        //step2:创建计算任务，作为参数，传入FutureTask
        Task task = new Task();
        FutureTask futureTask = new FutureTask(task);
        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.execute(futureTask);
    }


    static class Task implements Callable<Boolean> {


        @Override
        public Boolean call() throws Exception {

            try {
                for (int i = 0; i < 10; i++) {
                    System.out.print("task...");
                    //模拟耗时操作
                    Thread.sleep(100);
                }
            } catch (Exception e) {
                System.out.print("is interrupted when calculating, will stop...");
                return false;
            }
            return true;
        }
    }
}


