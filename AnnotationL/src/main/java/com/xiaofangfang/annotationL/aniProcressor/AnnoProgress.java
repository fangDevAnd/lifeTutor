package com.xiaofangfang.annotationL.aniProcressor;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * @author fang
 * @version 1.0
 * @func 定义的一个注解处理类，实现的是对注解的处理
 * 应该extends AbstractProcessor
 * @Date 2019 4.2 16：37
 */
public class AnnoProgress {

    //extends AbstractProcessor

    public static void main(String[] argc) throws Exception {

        Class className = Class.forName("com.xiaofangfang.annotationL.aniProcressor.AnnoProgress");

        Constructor constructor = className.getConstructor();
        constructor.setAccessible(true);
        AnnoProgress annoProgress = (AnnoProgress) constructor.newInstance();
        annoProgress.print();

        annoProgress = (AnnoProgress) className.newInstance();
        annoProgress.print();


    }

    private void print() {
        System.out.println("agaeggewg");
    }


}
