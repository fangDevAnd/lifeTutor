package com.xiaofangfang.rice2_verssion.model;


/**
 * 封装的是dialog 的mode,用来实现的是dialog 的三种状态的模式
 * 1.操作反馈
 * 2.提示
 * 3.警告
 */
public class DialogMode {


    public static class feedback {
        String name;
    }


    public static class outLine {
        String content;
    }


    public static class Tip {
        String value;
    }


}
