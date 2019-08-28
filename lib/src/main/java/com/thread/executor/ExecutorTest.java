package com.thread.executor;

import java.util.ArrayDeque;
import java.util.Date;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;


/**
 * 下面的代码取自#{AsyncTask} 源码的实现
 */
public class ExecutorTest {


    private static final int CPU_COUNT = Runtime.getRuntime().availableProcessors();
    // We want at least 2 threads and at most 4 threads in the core pool,
    // preferring to have 1 less than the CPU count to avoid saturating
    // the CPU with background work
    private static final int CORE_POOL_SIZE = Math.max(2, Math.min(CPU_COUNT - 1, 4));
    private static final int MAXIMUM_POOL_SIZE = CPU_COUNT * 2 + 1;
    private static final int KEEP_ALIVE_SECONDS = 30;

    private static final ThreadFactory sThreadFactory = new ThreadFactory() {
        private final AtomicInteger mCount = new AtomicInteger(1);

        public Thread newThread(Runnable r) {
            return new Thread(r, "AsyncTask #" + mCount.getAndIncrement());
        }
    };


    /**
     * An {@link Executor} that can be used to execute tasks in parallel.
     */
    public static final Executor THREAD_POOL_EXECUTOR;


    private static final BlockingQueue<Runnable> sPoolWorkQueue =
            new LinkedBlockingQueue<Runnable>(128);


    static {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
                CORE_POOL_SIZE, MAXIMUM_POOL_SIZE, KEEP_ALIVE_SECONDS, TimeUnit.SECONDS,
                sPoolWorkQueue, sThreadFactory);
        threadPoolExecutor.allowCoreThreadTimeOut(true);
        THREAD_POOL_EXECUTOR = threadPoolExecutor;
    }


    public static final Executor SERIAL_EXECUTOR = new SerialExecutor();


    private static class SerialExecutor implements Executor {
        final ArrayDeque<Runnable> mTasks = new ArrayDeque<Runnable>();
        Runnable mActive;


        /**
         * 这个mTasks是一个双端的队列，我们从尾部进行插入，插入的对象是Runnable
         * 对象，当第一次来的时候 mActive为空，执行下一个Runnable，调用的过程是
         * 从头部去取Runnable对象，然后通过THREAD_POOL_EXECUTOR进行调度
         * <p>
         * ArrayDeque用来实现的是队列的效果  ，但是ArrayDeque不是线程安全的，所以需要
         * 通过同步锁的方式进行插入数据和取出数据的同步，保证线程安全
         *
         * @param r
         */
        public synchronized void execute(final Runnable r) {
            mTasks.offer(new Runnable() {
                public void run() {
                    try {
                        r.run();
                    } finally {
                        scheduleNext();
                    }
                }
            });
            if (mActive == null) {
                scheduleNext();
            }
        }

        protected synchronized void scheduleNext() {
            if ((mActive = mTasks.poll()) != null) {
                THREAD_POOL_EXECUTOR.execute(mActive);
            }
        }
    }


    public static void main(String[] argc) {

        for (int i = 0; i < 100; i++) {
            int finalI = i;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    SERIAL_EXECUTOR.execute(new Runnable() {
                        @Override
                        public void run() {
                            System.out.println("传递的值" + finalI);
                        }
                    });
                }
            }).start();

        }
    }
}
