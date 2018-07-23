package com.yisingle.amapview.lib.utils;


import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author jikun
 * Created by jikun on 2018/6/6.
 */
public class CustomAnimator {

    private ExecutorService mThreadPools;

    private AtomicBoolean isrunning = new AtomicBoolean(false);


    private long startTime;

    private int duration = 1000;


    private CustomRunnable customRunnable;


    private OnTimeListener onTimeListener;


    private int i = 0;

    public CustomAnimator() {
        mThreadPools = new ThreadPoolExecutor(1, 1, 200L, TimeUnit.SECONDS, new LinkedBlockingDeque<Runnable>(), new CustomThreadFactory());
    }


    public void start() {

        start(false);


    }

    private void start(boolean isRepeat) {
        startTime = System.currentTimeMillis();
        end();
        customRunnable = new CustomRunnable(i);
        customRunnable.exitFlag.set(false);
        customRunnable.repeatFlag.set(true);
        if (null != onTimeListener) {
            if (isRepeat) {
                onTimeListener.onRepeatStart();
            } else {
                onTimeListener.onStart();
            }

        }
        mThreadPools.submit(customRunnable);
        i = i + 1;
    }


    public void end() {

        if (null != customRunnable) {
            customRunnable.exitFlag.set(true);
            customRunnable.repeatFlag.set(false);

        }


        //----cancale

    }

    public class CustomRunnable implements Runnable {
        private int index;


        AtomicBoolean exitFlag = new AtomicBoolean(false);

        AtomicBoolean repeatFlag = new AtomicBoolean(false);

        private CustomRunnable(int index) {
            this.index = index;

        }


        @Override
        public void run() {
            setRunning(true);
            while (!exitFlag.get()) {

                try {
                    Thread.sleep(20L);
                } catch (InterruptedException e) {
                    e.printStackTrace();

                }
                if (!exitFlag.get()) {
                    long currentTime = System.currentTimeMillis();
                    long time = currentTime - startTime;
                    BigDecimal t = new BigDecimal(time).divide(new BigDecimal(duration), 6, RoundingMode.HALF_DOWN);

                    if (t.floatValue() >= 1) {
                        exitFlag.set(true);
                        t = new BigDecimal(1);
                    }

                    if (null != onTimeListener) {
                        onTimeListener.onUpdate(t.floatValue());
                    }
                }

            }

            if (repeatFlag.get()) {

                start(true);

                //-----重新开启线程

            } else {
                setRunning(false);
                //--线程结束
            }

            //--线程结束


        }
    }

    public CustomAnimator setDuration(int duration) {
        if (duration <= 0) {
            duration = 10;
        }
        this.duration = duration;
        return this;
    }


    public CustomAnimator setOnTimeListener(OnTimeListener onTimeListener) {
        this.onTimeListener = onTimeListener;
        return this;
    }

    public interface OnTimeListener {


        /**
         * 重新开始
         */
        void onRepeatStart();

        /**
         * 开始
         */
        void onStart();

        /**
         * 更新
         *
         * @param t float
         */
        void onUpdate(float t);


    }

    private class CustomThreadFactory implements ThreadFactory {
        private CustomThreadFactory() {
        }

        @Override
        public final Thread newThread(Runnable runnable) {
            return new Thread(runnable);
        }
    }


    public void destory() {
        mThreadPools.shutdownNow();
    }

    public boolean isRunning() {
        return isrunning.get();
    }

    public void setRunning(boolean isrun) {
        isrunning.set(isrun);
    }
}
