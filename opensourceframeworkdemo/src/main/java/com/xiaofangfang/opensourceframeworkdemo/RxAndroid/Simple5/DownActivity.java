package com.xiaofangfang.opensourceframeworkdemo.RxAndroid.Simple5;

import androidx.appcompat.app.AppCompatActivity;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import com.orhanobut.logger.Logger;
import com.xiaofangfang.opensourceframeworkdemo.R;
import com.xiaofangfang.opensourceframeworkdemo.RxAndroid.Simple5.downUtil.DownloadUtil;

/**
 * 在实际的开发activity充当的角色太多了
 * 1.UI主线程负责绘制UI
 * 2.开启子线程获取网络数据
 * 3.赋值到控件
 * 4.逻辑判断
 */
public class DownActivity extends AppCompatActivity {


    /**
     * 下载的实现
     * <p>
     * 1.Handler的实现   结合了activity
     * <p>
     * 2.
     */


    private DownloadUtil util;
    private String PATH = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_down);


        util = new DownloadUtil();

        findViewById(R.id.button).setOnClickListener((v) -> {
            /**
             * 使用io操作,同时使用主线程
             */
            util.downLoadImage(PATH)
                    .observeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<byte[]>() {
                        @Override
                        public void onCompleted() {
                            Logger.d("COMPLETE");
                        }

                        @Override
                        public void onError(Throwable e) {
                            Logger.e(e.getMessage());
                        }

                        @Override
                        public void onNext(byte[] bytes) {
                            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                            //获取到图片

                        }
                    });
        });

    }
}
