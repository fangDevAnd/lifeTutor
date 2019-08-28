package com.xiaofangfang.consumeview.ClippingView;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Outline;
import android.view.View;
import android.view.ViewOutlineProvider;

public class ClipViewUtil {


    /**
     * 设置view圆角
     *
     * @param radio
     * @return
     */
    public static ViewOutlineProvider getRoundRect(final int radio) {

        ViewOutlineProvider vop = new ViewOutlineProvider() {
            @Override
            public void getOutline(View view, Outline outline) {
                outline.setRoundRect(0, 0, view.getWidth(), view.getHeight(), radio);
            }
        };

        return vop;
    }


    public static ViewOutlineProvider getCircle() {

        ViewOutlineProvider vop = new ViewOutlineProvider() {
            @Override
            public void getOutline(View view, Outline outline) {
                outline.setOval(0, 0, view.getWidth(), view.getHeight());
            }
        };
        return vop;
    }


}
