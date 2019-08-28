package com.annotation.annotaionProcessor2;

import android.app.Activity;

import java.lang.reflect.Method;

/**
 * 创建注解工具类
 */
public class BindViewTool {

    public static void bind(Activity activity) {

        Class clz = activity.getClass();

        try {

            Class bindViewClass=Class.forName(clz.getName()+"_ViewBinding");

            Method method=bindViewClass.getMethod("bind",activity.getClass());

            method.invoke(bindViewClass.newInstance(),activity);

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

}
