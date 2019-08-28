

#include<stdio.h>
#include<android/log.h>

#include "com_example_ndk_NativeLogUtil.h"


#define TAG "ndknativelog"

/**
 *@param ANDROID_LOG_INFO android的在日志级别
 *@param TAG 我们定义的tag，用于过滤信息
 *
*/
//#define  LOGI(...) __android_log_print(ANDROID_LOG_INFO,TAG,__VA_ARGS__);
//#define  LOGD(...) __android_log_print(ANDROID_LOG_DEBUG,TAG,__VA_ARGS__);



/*
 * Class:     com_example_ndk_NativeLogUtil
 * Method:    logD
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_com_example_ndk_NativeLogUtil_logD
  (JNIEnv * env, jobject obj){

printf("傻逼东西");

  }

/*
 * Class:     com_example_ndk_NativeLogUtil
 * Method:    logV
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_com_example_ndk_NativeLogUtil_logV
  (JNIEnv *env, jobject obj){
printf("傻逼东西");
  }

/*
 * Class:     com_example_ndk_NativeLogUtil
 * Method:    logI
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_com_example_ndk_NativeLogUtil_logI
  (JNIEnv *env, jobject obj){

    printf("傻逼东西");
  }

/*
 * Class:     com_example_ndk_NativeLogUtil
 * Method:    logW
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_com_example_ndk_NativeLogUtil_logW
  (JNIEnv *env, jobject obj){
printf("傻逼东西");

  }

/*
 * Class:     com_example_ndk_NativeLogUtil
 * Method:    logE
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_com_example_ndk_NativeLogUtil_logE
  (JNIEnv *env, jobject obj){

printf("傻逼东西");
  }