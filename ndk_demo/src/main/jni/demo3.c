

#include<jni.h>
#include<stdio.h>

JNIEXPORT jstring JNICALL_Java_com_example_ndk_JNITest_sayHello(JNIEnv *env,jobject obj){

    return (*env)->NewStringUTF(e,"i love this world");

}






