

#include"com_example_ndk_ArraySum.h"

#include<stdio.h>

#include <android/log.h>




#define LOG "test"


/**
    这个被宏定义是被andrid/log.h里面的一个函数 其中使用了可变参数，可变参数使用的是argc...
    请注意这个和定义函数里面使用的可变参数有一些不一样，毕竟普通的函数对于可变参数使用...

*/


/**
为了解决导入这个方法找不到的情况，在android.mk文件下面添加
1、在android.mk 文件中找到

include $(CLEAR_VARS)  这一行，

在下面增加一行：
LOCAL_LDLIBS    := -lm -llog
*/

#define LOGI(fmt,argc...) (__android_log_print(ANDROID_LOG_INFO,LOG,fmt,argc))



JNIEXPORT jint JNICALL Java_com_example_ndk_ArraySum_getSum
  (JNIEnv *env, jclass jclass, jintArray arr){

    int buf[10];

    int result=0;
    int i;
     (*env)->GetIntArrayRegion(env,arr,0,10,buf);


    for(i=0;i<10;i++){

       result+=buf[i];
    }
    return result;
  }







JNIEXPORT jint JNICALL Java_com_example_ndk_ArraySum_getSum2
  (JNIEnv *env, jclass jclass, jintArray arr){

  int result=0;

  int *p=(*env)->GetIntArrayElements(env,arr,NULL);

  int len=(*env)->GetArrayLength(env,arr);



   int i=0;
   for(;i<len;i++){
        result+=*(p+i);
   }

    return result;

  }



JNIEXPORT jobjectArray JNICALL Java_com_example_ndk_ArraySum_get2DArraySum
  (JNIEnv * env, jclass jcc , jint size){

  jobjectArray result;
  jclass jintClass=(*env)->FindClass(env,"I");//这个方法的签名可能存在问题

//  jclass jStringClass=(*env)->FindClass(env,"Ljava/lang/String");


  result=(*env)->NewObjectArray(env,size,jintClass,NULL);//创建一个jobjectArray对象

  int i=0;
  for(;i<size;i++){

        int temp[256];
        jintArray jArr=(*env)->NewIntArray(env,size);//创建一个java的整型数组

        int j=0;
        for(;j<size;j++){
           temp[j]=20+j;
        }

        (*env)->SetIntArrayRegion(env,jArr,0,size,temp);//将temp上面的数据copy一共size大小到jArr

        (*env)->SetObjectArrayElement(env,result,i,jArr);

          (*env)->DeleteLocalRef(env,jArr);
  }
  return result;

  }


JNIEXPORT jobjectArray JNICALL Java_com_example_ndk_ArraySum_get2DArraySum2
  (JNIEnv * env, jclass jcc , jint size){

    jobjectArray result;
    jclass jintClass=(*env)->FindClass(env,"I");
    result=(*env)->NewObjectArray(env,size,jintClass,NULL);

    int i=0;
    for(;i<size;i++){

        int temp[256];
        jintArray jArr=(*env)->NewIntArray(env,size);
        int j=0;
        for(;j<size;j++){
            temp[j]=20+j;
        }

        (*env)->SetIntArrayRegion(env,jArr,0,size,temp);

        jint * intj=(*env)->GetIntArrayElements(env,jArr,NULL);
       int z=0;
       for(;z<size;z++){
            printf("%d",*(intj+i));
       }
       (*env)->SetObjectArrayElement(env,result,i,jArr);
    }
       return result;
  }



JNIEXPORT jobjectArray JNICALL Java_com_example_ndk_ArraySum_demo3(JNIEnv *env,jobject obj){

//        jmethodID mid=(*env)->FromReflectedMethod(env,jobject);
//
//        jobject jobj=(*env)->CallObjectMethod(env,jobject,mid);




//        jclass str=(*env)->FindClass(env,"Ljava/lang/String;");




        jsize size=10;
//
//       jintArray jintArr=(*env)->NewIntArray(env,20);

        int temp[256];
        jintArray jArr=(*env)->NewIntArray(env,size);
        int j=0;
        for(;j<size;j++){
            temp[j]=20+j;
        }




       (*env)->SetIntArrayRegion(env,jArr,0,size,temp);


       jsize length=(*env)->GetArrayLength(env,jArr);



       LOGI("当前的jsize的长度是%d",length);



//       (*env)->SetIntArrayRegion(env,jArr,0,20,temp);
//
//        jobjectArray jArr1=(*env)->NewObjectArray(env,0,length,jArr);

    return NULL;

}




JNIEXPORT jobjectArray JNICALL Java_com_example_ndk_ArraySum_demo4
(JNIEnv *env,jobject obj,jsize size){


         jclass jintClass=(*env)->FindClass(env,"I");


        jobjectArray result=(*env)->NewObjectArray(env,size,jintClass,NULL);








}













