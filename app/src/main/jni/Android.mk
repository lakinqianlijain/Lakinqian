LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

LOCAL_MODULE    := NdkTest-jni

LOCAL_SRC_FILES := NdkDynamic \
                   NdkTest.c

include $(BUILD_SHARED_LIBRARY)