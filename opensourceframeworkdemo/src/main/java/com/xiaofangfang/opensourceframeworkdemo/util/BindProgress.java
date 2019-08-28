package com.xiaofangfang.opensourceframeworkdemo.util;


import android.view.View;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import androidx.appcompat.app.AppCompatActivity;

/**
 * java注解的处理器
 */
public class BindProgress {


    public static <T extends AppCompatActivity> void bindContentView(T activity) {
        Class<T> c = (Class<T>) activity.getClass();
        if (c.isAnnotationPresent(Bind.class)) {
            Bind viewId = c.getAnnotation(Bind.class);
            int layoutId = viewId.bindContent();
            try {
                //调用指定类的指定的方法
                Method method = c.getMethod("setContentView", int.class);
                method.setAccessible(true);//允许访问
                method.invoke(activity, layoutId);//调用这个方法
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    public static <T extends AppCompatActivity> void bindView(T activity) {
        Class<T> c = (Class<T>) activity.getClass();
        Field[] fields = c.getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            if (fields[i].isAnnotationPresent(Bind.class)) {
                Bind bind = fields[i].getAnnotation(Bind.class);
                int viewId = bind.bindView();
                try {
                    Method method = c.getMethod("findViewById", int.class);
                    method.setAccessible(true);
                    Object obj = method.invoke(activity, viewId);
                    fields[i].setAccessible(true);
                    fields[i].set(activity, obj);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }


    public static <T extends AppCompatActivity> void bindClick(final T activity) {
        Class<?> objClass = activity.getClass();
        Method methodProgress = null;//需要处理的Method
        Bind bind = null;//得到使用的注解
        //找打设置了注解的click方法
        final Method[] method = objClass.getDeclaredMethods();
        for (int i = 0; i < method.length; i++) {
            if (method[i].isAnnotationPresent(Bind.class)) {
                methodProgress = method[i];
                bind = method[i].getAnnotation(Bind.class);
                break;
            }
        }
        if (methodProgress != null) {
            //获得注解的值
            int[] viewRes = bind.onCLick();
            for (int intRes : viewRes) {
                final View view = activity.findViewById(intRes);
                final Method methodProgress1 = methodProgress;
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        methodProgress1.setAccessible(true);
                        try {
                            methodProgress1.invoke(activity, view);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        }
    }


}


