#include <jni.h>
#include <android/log.h>
#include <stdio.h>
#include <stdlib.h>


jint addNumber(JNIEnv *env,jclass clazz,jint a,jint b);

jint subNumber(JNIEnv *env,jclass clazz,jint a,jint b);

jint mulNumber(JNIEnv *env,jclass clazz,jint a,jint b);

jint divNumber(JNIEnv *env,jclass clazz,jint a,jint b);




JNIEXPORT jint JNI_OnLoad(JavaVM* vm, void* reserved){

    JNIEnv* env = NULL;
    jint result = -1;

    // 判断是否正确
    if((*vm)->GetEnv(vm,(void**)&env,JNI_VERSION_1_6)!= JNI_OK){
        return result;
    }

    //注册四个方法，注意签名
    const JNINativeMethod method[]={
            {"add","(II)I",(void*)addNumber},
            {"sub","(II)I",(void*)subNumber},
            {"mul","(II)I",(void*)mulNumber},
            {"div","(II)I",(void*)divNumber}
    };

    //找到对应的DynamicNdkTools类
    jclass jClassName=(*env)->FindClass(env,"com/qlj/lakinqiandemo/ndk/DynamicNdkTools");

    //开始注册
    jint ret = (*env)->RegisterNatives(env,jClassName,method, 4);

     //如果注册失败，打印日志
    if (ret != JNI_OK) {
        return -1;
    }

    return JNI_VERSION_1_6;
}


jint addNumber(JNIEnv *env,jclass clazz,jint a,jint b){
     return a+b;
}

jint subNumber(JNIEnv *env,jclass clazz,jint a,jint b){
     return a-b;
}

jint mulNumber(JNIEnv *env,jclass clazz,jint a,jint b){
     return a*b;
}

jint divNumber(JNIEnv *env,jclass clazz,jint a,jint b){
     return a/b;
}


