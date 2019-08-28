package com.example.componentasystemtest.canvas.Shader;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.PorterDuffXfermode;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by fang on 2018/6/9.
 */

public class MyView1 extends View {

    private Paint mPaint;
    private PorterDuffXfermode porterDuffXfermode;

    public MyView1(Context context) {
        this(context, null);
    }

    public MyView1(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyView1(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


}
