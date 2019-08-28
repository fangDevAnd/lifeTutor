package com.example.componentasystemtest.canvas.View;

import android.content.Context;
import android.graphics.Paint;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by fang on 2018/6/10.
 */

public class SizeChange extends View {

    Paint paint;

    public SizeChange(Context context) {
        this(context, null);
    }

    public SizeChange(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SizeChange(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        Log.d("test", "onSizeChanged: 被调用");
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Log.d("test","从新测量");
    }


}
