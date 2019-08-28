

ndk开发的经验

1.我们看下jni里面对象的最初类型

typedef void*           jobject;
typedef jobject         jclass;
typedef jobject         jstring;
typedef jobject         jarray;
typedef jarray          jobjectArray;
typedef jarray          jbooleanArray;
typedef jarray          jbyteArray;
typedef jarray          jcharArray;
typedef jarray          jshortArray;
typedef jarray          jintArray;
typedef jarray          jlongArray;
typedef jarray          jfloatArray;
typedef jarray          jdoubleArray;
typedef jobject         jthrowable;
typedef jobject         jweak;


上面的代码可以看到我们定义的所有类型其实都是void*,也就是万能指针，对于这个void*
请不要对其设置基本类型，会出错，尽管是可以这样进行赋值的，但是可能与函数的内部实现有关，程序会crach掉


下面是一个封装了函数的宏定义
里面封装的函数是android/jni.h里面的函数.下面我们看看函数的原型

int __android_log_print(int prio, const char* tag, const char* fmt, ...)


#define LOGI(fmt,argc...) (__android_log_print(ANDROID_LOG_INFO,LOG,fmt,argc))


这里还有需要注意的地方，宏定义的可变参数的变量需要指定，不能直接使用...会报错的


下面是一些api



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

(*env).DeleteLocalRef(env,jArr);
//垃圾回收


5.对象字段的操作
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


6.操作静态字段
jfieldID GetStaticFieldID(JNIEnv *env ,jclass clazz,const char *name,const char *sig);
//获得类的静态域ID方法

void SetStaticIntField(jclass clazz,jfieldID fieldID,jint value);
//设置static field的值


7.对象方法的调用

jmethordID GetMethordID(JNIEnv *env,jclass clazz,const char *name,const char *sig);
//返回类或者接口实例(非静态)方法的方法的ID

void CallVoidMethod(jobject obj,jmethordID methordID,...);
//用于从本地方法调用java实例方法


8.静态方法的调用
void (*CallStaticVoidMethod)(JNIEnv *,jclass,jmethodID,...);


9.构造函数的调用
void GetCharArrayRegion(JNIEnv *env,ArrayType array,jsize start,jsize len,NativeType *buf);
//将基本类型数组某一区域复制到缓冲区中的一组函数

void SetCharArrayRegion(JNIEnv *env,ArrayType array,jsize start,jsize len,NativeType *buf);
//将基本类型数组的某一个区域从缓冲区中复制回来一组函数

jobject NewObject(JNIEnv *env,jclass clazz,jmethodID methodID,...);
//构造新java对象

8.获得对象的类
jclass      (*FindClass)(JNIEnv*, const char*);
//根据一个对象的标识符，生成与之对应的类





