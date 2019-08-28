


//笔记

1.日志的输出

  #define LOGI(...) __android_log_print(ANDROID_LOG_INFO,TAG,__VA_ARGS__)

//下面是实现的代码


#include<android/log.h>

#define TAG="ndknativelog"


/**
 *@param ANDROID_LOG_INFO android的在日志级别
 *@param TAG 我们定义的tag，用于过滤信息
 *
*/
#define  LOGI(...) __android_log_print(ANDROID_LOG_INFO,TAG,__VA_ARGS__);

JNIEXPORT void JNICALL Java_com_example_ndk_NativeLogUtil_log
  (JNIEnv * env, jclass obj){

        LOGI("I LOVE THIS WORLD");
  }


下面是添加到build.gradle的代码
添加到 android节点下面:

sourceSets.main{
    jniLibs.srcDir="src/main/libs"
    jni.srcDirs=[]
}

task ndkBuild(type: Exec){
    //ndk的目录
    commandLine "/home/fang/Android/Sdk/ndk-bundle",'-C',file('src')
}

task.withType(JavaCompile){
    compileTask -> compileTask.dependsOn ndkBuild
}




2.直接编写native文件

    一般格式







3.jni的api

jstring NewStringUTF(JNIEnv *env,const char *bytes);
//利用UTF-8字符数组构造新的java.lang.String的对象



4.数组访问

void (* GetIntArrayRegion)(JNIEnv *,jintArray,jsize a,jsize b,jint *buf);
//将jintArray数组中从a～b中的数据复制到buf中

jint *(*GetIntArrayElements)(JNIEnv *,jintArray,jboolean *);
//将java数组转换成c的数组，如果isCopy不是NULL,*isCopy在复制完成后即将被设为JNI_TRUE;如果
//未复制，则设为JNI_FALSE ，返回指向c数组的指针

jarray NewObjectArray(JNIEnv *env ,jsize length,jclass elementClass,jobject initialElement);
//构造新的数组，他将保存类elementClass中的对象 所有 元素初始值设为initialElement

jsize (*GetArrayLength)(JNIEnv *,jarray);
//获得数组的长度

jclass (*FindClass)(JNIEnv *,const char *);
//查找类 参数二是标识符  使用javap可以查看函数的标识符

jobjectArray (*NewObjectArray)(JNIEnv *,jsize,jclass,jobject);
//创建一个对象数组


下面编写一些代码

jsize size=10;

jobjectArray result;
jclass jintClass=(*env)->FindClass(env,"LI");
result=(*env)->NewObjectArray(env,jsize,jintClass,NULL);

int i=0;
for(i=0;i<size;i++){
    int temp[256];

    jintArray jArr=(* env)->NewIntArray(env,size);

   int j;
   for(j=0;j<size;j++){

        temp[j]=20+j;

   }

   (* env)->SetIntArrayRegion(env,jArr,0,size,temp);

   (*env)->SetObjectArrayElement(env,result,i,jArr);

}
//垃圾回收
(*env).DeleteLocalRef(env,jArr);
return result;



//对象字段的操作
jclass GetObjectClass(JNIEnv * env,jobject obj);
//通过对象获取这个类，该函数比较简单，唯一注意的地方是对象不能为NULL，否则获取的class肯定返回也为Null

jfieldID GetFieldID(JNIEnv *env,jclass clazz,const char *name,const char *sig);
//返回java类(非静态)域的属性id

jobject GetObjectField(jobject obj,jfieldID fieldID);
//根据field获取相应的field

jclass (*GetObjectClass)(JNIENV *,jobject);
//找到对象实例所对应的class对象

void SetObjectField(jobject obj,jfieldID fieldID,jobject value);
//设置成员变量的值





//设置java对象成员变量的值


//java代码
public void name;

public native void changeName();


//c语言实现代码
#include<jni.h>

代码实现

JNIEXPORT void JNICALL Java_text_hjd_com_fang_demo_changeName(JNIENV *env,jobject obj){
    jclass clsobj=(*env)->GetObjectClass(env,obj);
    //类对象     成员变量的名称      标识符 
    jfieldID fid=(*env)->GetFieldID(env,clsobj,"name","Ljava/lang/String;");
    //对象      成员变量id
    jobject jstr=(*env)->GetObjectField(env,obj,fid);

    jstr=(*env)->NewStringUTF(env,"allen");

    (*env)->SetObjectField(env,obj,fid,jstr);;
}


//静态字段的操作
jfieldID GetStaticFieldID(JNIEnv *env ,jclass clazz,const char *name,const char *sig);
//获得类的静态域ID方法


void SetStaticIntField(jclass clazz,jfieldID fieldID,jint value);
//设置static field的值




//操作代码
public static int salary;
public native int changeSalary();


//c语言的实现
#include<jni.h>
#include<stdio.h>

JNIEXPORT jnit JNICALL Java_com_example_demo_changeSalary(JNIEnv *env,jobject obj){

       jclass clsobj=(*env)->GetObjectClass(env,obj);
       jfieldID fid=(*env)->GetStaticFieldID(env,clsobj,"salary","I");
       int salary=(*env)->GetStaticIntField(env,clsobj,fid);
       (*env)->SetStaticIntField(env,clsobj,fld,35335);
}


//对象方法的调用

jmethordID GetMethordID(JNIEnv *env,jclass clazz,const char *name,const char *sig);
//返回类或者接口实例(非静态)方法的方法的ID

void CallVoidMethod(jobject obj,jmethordID methordID,...);
//用于从本地方法调用java实例方法

//操作代码
public class MehtodAccess{
    public void callBack(){
            Sysstem.out.println("hello i am java");
    }
}
public native void callJavaMethod();


//c语言的实现

#include <jni.h>
#include <stdio.h>

JNIEXPORT jnit JNICALL Java_com_example_demo_MethodAccess_callJavaMethod(JNIEnv * env,jboject obj){

    jclass cl=(*env)->GetObjectClass(env,obj);
    jmethodID mid=(*env)->GetMethordID(env,cl,"callBack","()V");
    (*env)->CallVoidMethod(env,obj,mid);
}



//静态方法的调用
void (*CallStaticVoidMethod)(JNIEnv *,jclass,jmethodID,...);
//调用静态的void方法


//操作代码
public class StaticMethodAccess(){
    public static void callBack(){
            System.out.println("i am java code");
    }
}

public native void callJavaStaticMethod();

//c语言的实现

#include <jni.h>
#include <stdio.h>

JNIEXPORT void JNICALL Java_com_example_demo_MethodAccess_callJavaStaticMethod(JNIEnv * env,jboject obj){
    
      jclass cls=(*env)->GetObjectClass(env,obj);
      if(cls==NULL){
          return;
      }
      jmethodID mid=(*env)->GeetStaticMethodID(env,cls,"callBack","()V");
      if(mdi==NULL){
        return;
      }
      (*env)->CallStaticVoidMethod(env,cls,mid);

}


//构造函数的调用、
void GetCharArrayRegion(JNIEnv *env,ArrayType array,jsize start,jsize len,NativeType *buf);
//将基本类型数组某一区域复制到缓冲区中的一组函数
void SetCharArrayRegion(JNIEnv *env,ArrayType array,jsize start,jsize len,NativeType *buf);
//将基本类型数组的某一个区域从缓冲区中复制回来一组函数
jobject NewObject(JNIEnv *env,jclass clazz,jmethodID methodID,...);
//构造新java对象


//操作代码
public class Animal{
    public String name;
    public Animal(String name){
        this.name=name;
    }
}

public class ConstructorMethodAccess{
    public native void callConstructorMethod();
}

//c语言实现代码
#include<stdio.h>
#include<jni.h>
JNIEXPORT void JNICALL Java_com_example_demo_MethodAccess_ConstructorMethodAccess(JNIEnv * env,jboject obj){

    //找到Animal的class
    jclass cls=(*env)->FindClass(env,"com/example/demo/Animal");

    jmethodID mid=(*env)->GetMethodID(env,cls,"<init>","(Ljava/lang/String;)V");

    jstring name=(*env)->NewStringUTF(env,"allen is a handsome boy");

    (*env)->NewObject(env,cls,mid,name);

}


//使用时缓存
/**
 *字段ID和方法ID可以在字段的值被访问或者方法被回调的时候缓存起来 
 * 
 * 缓存字段ID
 * 
 * 缓存方法ID
 */

//操作代码
public class AccessCache{
    public native void callJavaMethod();
    public void sayHello(){
        System.out.println("----------------------->被调用")
    }
}

//c语言实现
#include<stdio.h>
#include<jni.h>

JNIEXPORT void JNICALL Java_com_example_demo_cache_AccessCache_callJavaMethod(JNIEnv * env,jobject obj){
    jclass cls=(*env)->GetObjectClass(env,obj);
    static jmethodID mid=NULL;

    if(mid==NULL){
        mid=(*env)->GetMethodID(env,cls,"sayHello","()V");
    }
    (*env)->CallVoidMethod(env,obj,mid);


}



//静态初始化的过程中缓存

//操作代码
piblic class StaticAccessCache{

    public static native void initIds();
    public static native void callJavaMethod();
    public void sayHello(){
        System.out.println("--------------->sayHello");
    }

    static{
        System.out.println("---------------execute");
        initIds(); 
    }
}

//c语言实现代码

#include<jni.h>
#include<stdio.h>

jmethodID mid=NULL;

JNIEXPORT void JNICALL Java_com_example_demo_cache_StaticAccessCache_initIds(JNIEnv * env,jclass cls){
    mid=(*env)->GetMethodID(env,cls,"sayHello","()V");
}
JNIEXPORT void JNICALL Java_com_example_demo_cache_StaticAccessCache_callJavaMethod(JNIEnv * env,jobject obj){
    (*env)->CallVoidMethod(env,obj,mid);
}



//异常处理

jthrowable ExceptionOccurred(JNIEnv *env);
//确定某个异常是否正在被抛出

void ExceptionDescribe(JNIEnv *env);
//将异常以及堆栈的回溯输出到标准输出

void ExceptionClear(JNIEnv *env);
//清除当前抛出的任何异常，如果当前无异常，则不产生任何效果

jint ThrowNew(JNIEnv *env,jclass clazz,const char *message);
//利用指定类的消息(由message指定)构造异常对象并且抛出该异常


//操作代码
public class CatchThrow{
    public void throwJavaException(){
        throw new NullPointerException("------------>抛出空指针异常");
    }

    public native void sayHello() throws IllegalArgumentException;   
}

//activity的调用
CatchThrow ct=new CatchThrow();
try{
    ct.sayHello();
}catch(Exception e){
    e.printStackTrace();
}




//c语言实现
#include<jni.h>
#include<stdio.h>

JNIEXPORT void JNICALL Java_com_example_demo_CatchThrow_sayHello(JNIEnv *env,jobject obj){

    jclacc clsObj=(*env)->GetObjectClass(env,obj);
    jmethodID mdi=(*env)->GetMethodID(env,clsObj,"throwJavaException","()V");
    (*env)->CallVoidMethod(env,obj,mid);

    jthrowable ex=(*env)->ExceptionOccurred(env);
    if(ex!=NULL){
         (*env)->ExceptionDescribe(env);

         (*env)->ExceptionClear(env);

        jclass cls=(*env)->FindClass(env,"java/lang/IllegalArgumentException");
        (*env)->ThrowNew(env,cls,"this is exception from c");

    }
}



//jni性能测试

使用jni是比较消耗系统性能的，同样的操作，jni更加耗时间

//操作代码
public class Performance{

    public native void emptyJNIMethod();
    public void emptyJavaMethod(){

    }
}

//activity代码 ,自己通过系统时间动态的计算两种的消耗。
Performance p=new Performance();
p.emptyJavaMethod();
p.emptyJNIMethod();


//c代码实现
#include<stdio.h>

JNIEXPORT void JNICALL Java_com_example_demo_Performance_emptyJNIMethod(JNIEnv *env,jobject obj){

}


//加密算法的实现

punlic class PasswordUtils{
    public static native String encodePassword(String password,int length);
    public static native String decodePassword(String password,int length);
}



char * jstring2Cstr(JNIEnv *env,jstring jstr){
    jclass clsstr=(*env)->FindClass(env,"java/lang/String");
    jstring strencode=(*env)->NewStringUTF(env,"utf8");
    jmethodID mid=(*env)->GetMethodID(env,clsstr,"getBytes","(Ljava/lang/String;)[B");

    jbyteArray barr=(jbyteArray)(*ENV)->CallObjectMethod(env,jstr,mid,strencode);
    jbyte *ba =(*env)->GetByteArrayElements(env,barr,JNI_FALSE);
    jsize alen=(*env)->GetArrayLength(env,barr);

    char * result=NULL;
    if(alen>0){
        result=(char *)malloc(alen+1);
        memcpy(result,ba,alen);
        result[alen]=0;
    }

    (*env)->ReleaseByteArrayElements(env,barr,ba,0);

    return result;
}


JNIEXPORT void JNICALL Java_com_example_demo_PasswordUtils_encodePassword(JNIEnv *env,jclass cls,jstring password,jint length){
    char * cstr=jstring2Cstr(env,password);
    int i;
    for(i=0;i<length;i++){
        *(cstr+i)+=i;
    }
    return (*env)->NewStringUTF(env,cstr);
}

JNIEXPORT void JNICALL Java_com_example_demo_PasswordUtils_decodePassword(JNIEnv *env,jclass cls,jstring password,jint length){
     char * cstr=jstring2Cstr(env,password);
    int i;
    for(i=0;i<length;i++){
        *(cstr+i)-=i;
    }
    return (*env)->NewStringUTF(env,cstr);
}












































