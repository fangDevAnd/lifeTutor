package com.example.componentasystemtest.appProtectedAlive.onePixelActivity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import com.example.componentasystemtest.R;

import java.lang.ref.WeakReference;

import androidx.appcompat.app.AppCompatActivity;

public class SinglePixelActivity extends AppCompatActivity {

    public static final String TAG = SinglePixelActivity.class.getSimpleName();

    public static void actionToSinglePixelActivity(Context pContext) {
        Intent intent = new Intent(pContext, SinglePixelActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        pContext.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");
        setContentView(R.layout.activity_singlepixel);
        Window window = getWindow();
        //放在左上角
        window.setGravity(Gravity.START | Gravity.TOP);
        WindowManager.LayoutParams attributes = window.getAttributes();
        //宽高设计为1个像素
        attributes.width = 1;
        attributes.height = 1;
        //起始坐标
        attributes.x = 0;
        attributes.y = 0;
        window.setAttributes(attributes);

        ScreenManager.getInstance(this).setActivity(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}

//在屏幕关闭的时候把SinglePixelActivity启动起来，在开屏的时候把SinglePixelActivity 关闭掉，所以要监听系统锁屏广播，以接口的形式通知MainActivity启动或者关闭SinglePixActivity。

class ScreenBroadcastListener {

    private Context mContext;

    private ScreenBroadcastReceiver mScreenReceiver;

    private ScreenStateListener mListener;

    public ScreenBroadcastListener(Context context) {
        mContext = context.getApplicationContext();
        mScreenReceiver = new ScreenBroadcastReceiver();
    }

    interface ScreenStateListener {

        void onScreenOn();

        void onScreenOff();
    }

    /**
     * screen状态广播接收者
     */
    private class ScreenBroadcastReceiver extends BroadcastReceiver {
        private String action = null;

        @Override
        public void onReceive(Context context, Intent intent) {
            action = intent.getAction();
            if (Intent.ACTION_SCREEN_ON.equals(action)) { // 开屏
                mListener.onScreenOn();
            } else if (Intent.ACTION_SCREEN_OFF.equals(action)) { // 锁屏
                mListener.onScreenOff();
            }
        }
    }

    public void registerListener(ScreenStateListener listener) {
        mListener = listener;
        registerListener();
    }

    private void registerListener() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_SCREEN_ON);
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        mContext.registerReceiver(mScreenReceiver, filter);
    }
}

class ScreenManager {

    private Context mContext;

    private WeakReference<Activity> mActivityWref;

    public static ScreenManager gDefualt;

    public static ScreenManager getInstance(Context pContext) {
        if (gDefualt == null) {
            gDefualt = new ScreenManager(pContext.getApplicationContext());
        }
        return gDefualt;
    }

    private ScreenManager(Context pContext) {
        this.mContext = pContext;
    }

    public void setActivity(Activity pActivity) {
        /**
         //当你想引用一个对象，但是这个对象有自己的生命周期，你不想介入这个对象的生命周期，这时候你就是用弱引用。
         //WeakReference是弱引用，弱引用持有的对象实例是可以被垃圾回收器回收的。通常用于保存保存对象的引用，却又不会影响对象被GC回收
         */
        mActivityWref = new WeakReference<Activity>(pActivity);
    }

    public void startActivity() {
        SinglePixelActivity.actionToSinglePixelActivity(mContext);
    }

    public void finishActivity() {
        //结束掉SinglePixelActivity
        if (mActivityWref != null) {
            Activity activity = mActivityWref.get();
            if (activity != null) {
                activity.finish();
            }
        }
    }
}