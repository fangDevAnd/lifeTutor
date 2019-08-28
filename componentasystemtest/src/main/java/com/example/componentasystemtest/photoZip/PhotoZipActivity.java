package com.example.componentasystemtest.photoZip;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.collection.LruCache;

import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

import com.example.componentasystemtest.R;

import java.lang.ref.WeakReference;


/**
 * 图片压缩的实现
 * <p>
 */

public class PhotoZipActivity extends AppCompatActivity {


    private static final String TAG = "test";
    private String url = "http://192.168.42.165:8080/GF/app.jpg";
    private ImageView imageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_zip);
        imageView = findViewById(R.id.image);


    }


    private int height;
    private int width;
    Bitmap bitmap;

    public void onClick(View view) {
        Log.d(TAG, "点击了");

        if (view.getId() == R.id.loadImg) {

            /**
             * 居然不能用,比较郁闷
             */
            imageView.getViewTreeObserver().addOnGlobalLayoutListener(() -> {//通过view节点树观察者,获得宽高,然后进行设置
                if (height <= 0 || width <= 0) {
                    height = imageView.getMeasuredHeight();
                    width = imageView.getMeasuredWidth();
                    Log.d(TAG, "onGlobalLayout: height=" + height + "\twidth=" + height);
                    bitmap = NativeImageLoader.getInstance().loadNativeImage(url, new Point(width, width), new NativeImageLoader.NativeImageCallBack() {
                        @Override
                        public void onImageLoader(Bitmap bitmap, String path) {
                            Log.d(TAG, "onImageLoader: " + bitmap);
                            imageView.setImageBitmap(bitmap);
                        }
                    });
                    imageView.setImageBitmap(bitmap);
                }
            });
        }


        /**
         *
         *
         * 这个加载图片的代码存在一些问题
         * 如果当前的应用退出，NativeImageLoader还在进行加载，导致imageView不能被回收，，这时候我们最好的方法是使用WeakReference
         * 来取代在post方法里面对imageView的引用
         *
         *
         */
        if (view.getId() == R.id.loadImg1) {
            imageView.post(new Runnable() {
                @Override
                public void run() {
                    height = imageView.getMeasuredHeight();
                    width = imageView.getMeasuredWidth();
                    Log.d(TAG, "onGlobalLayout: height=" + height + "\twidth=" + height);
                    bitmap = NativeImageLoader.getInstance().loadNativeImage(url, new Point(width, width), new NativeImageLoader.NativeImageCallBack() {
                        @Override
                        public void onImageLoader(Bitmap bitmap, String path) {
                            Log.d(TAG, "onImageLoader: " + bitmap);
                            imageView.setImageBitmap(bitmap);
                        }
                    });
                    imageView.setImageBitmap(bitmap);
                }
            });
        }

        if (view.getId() == R.id.loadImg2) {

            imageView.post(new Runnable() {//加载本地图片
                @Override
                public void run() {
                    height = imageView.getMeasuredHeight();
                    width = imageView.getMeasuredWidth();
                    Bitmap bitmap = CompressUtil.decodeSampledBitmapFromResource(getResources(), R.drawable.meizi, width, height);
                    imageView.setImageBitmap(bitmap);
                }
            });
        }
    }


    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

    }


}
