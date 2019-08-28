package com.xiaofangfang.opensourceframeworkdemo.AspectJ;

import android.util.Log;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;

@Aspect
public class AspectTest {

    final String TAG = "test";

    /**
     * 执行所有类的名称后缀为MainActivity方法以on开头的方法
     *
     * @param joinPoint
     * @throws Throwable
     */
    @Before("execution(* *..MainActivity+.on**(..))")
    public void method(JoinPoint joinPoint) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        String className = joinPoint.getThis().getClass().getSimpleName();
        Log.e(TAG, "class:" + className);
        Log.e(TAG, "method:" + methodSignature.getName());
    }


}