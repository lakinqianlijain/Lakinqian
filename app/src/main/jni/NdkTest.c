//
// Created by lakinqian on 2020/3/10.
//

#include "com_qlj_lakinqiandemo_ndk_NdkTools.h"

JNIEXPORT jstring JNICALL Java_com_qlj_lakinqiandemo_ndk_NdkTools_getStringFromNDK
  (JNIEnv *env, jobject obj){
     return (*env)->NewStringUTF(env,"你获取到了你的ndk代码");
  }

