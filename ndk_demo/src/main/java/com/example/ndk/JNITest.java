package com.example.ndk;

public class JNITest {

    //创建一个 native 方法
    public native static String get();

    public native String hello();


    public native JNITest getInstance(String value);


    public native String sayHello();


    public native JNITest outPut(short a, int b, long c, char d, char[] value);


    public native int getDay();


//    static {
//        System.loadLibrary("JNITest");
//    }



}