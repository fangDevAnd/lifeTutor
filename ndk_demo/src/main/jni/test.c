#include<jni.h>
#include<stdio.h>
//导入我们创建的头文件
#include "com_example_ndk_JNITest.h"


/*
 * Class:     com_example_ndk_JNITest
 * Method:    get
 * Signature: ()Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_com_example_ndk_JNITest_get
  (JNIEnv *env, jclass cla){

  //返回一个字符串
      return (*env)->NewStringUTF(env,"This is my first NDK Application");
  }

/*
 * Class:     com_example_ndk_JNITest
 * Method:    hello
 * Signature: ()Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_com_example_ndk_JNITest_hello
  (JNIEnv *env, jobject obj){

return (*env)->NewStringUTF(env,"i am native layer code hello application!");

  }

/*
 * Class:     com_example_ndk_JNITest
 * Method:    getInstance
 * Signature: ()Lcom/example/ndk/JNITest;
 */
JNIEXPORT jobject JNICALL Java_com_example_ndk_JNITest_getInstance
  (JNIEnv *env, jobject obj){


    return NULL;
  }

















