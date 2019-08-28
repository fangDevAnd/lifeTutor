package com.example.momomusic.servie;

import android.content.Context;
import android.database.Cursor;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;

import com.example.momomusic.R;

import androidx.annotation.AnimRes;

/**
 * 动画控制服务
 */
public class AnimationControllService {

    /**
     * @param id      动画id
     * @param delay   延迟 时间
     * @param order   LayoutAnimationController的order常量
     * @param context
     */
    public static LayoutAnimationController setLayoutAnim(@AnimRes int id, float delay, int order, Context context) {

        Animation animation = AnimationUtils.loadAnimation(context, id);
        LayoutAnimationController controller = new LayoutAnimationController(animation);
        controller.setDelay(delay);
        controller.setOrder(order);
        return controller;
    }


}
