package com.annotation.annotationProcessor4;

import android.app.Activity;

import java.lang.reflect.Method;

public class BindClickTool {

    public static void bind(Activity activity) {

        Class clz = activity.getClass();

        try {

            Class bindViewClass=Class.forName(clz.getName()+"_ClickBind");

            Method method=bindViewClass.getMethod("bind",activity.getClass());

            method.invoke(bindViewClass.newInstance(),activity);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
