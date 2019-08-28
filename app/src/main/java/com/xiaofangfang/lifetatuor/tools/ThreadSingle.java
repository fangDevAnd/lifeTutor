package com.xiaofangfang.lifetatuor.tools;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadSingle {


    /**
     * 创建单一线程
     *
     * @return
     */

    public static ExecutorService getSingleThread() {

        ExecutorService service =
                Executors.newFixedThreadPool(1);
        return service;
    }


}
