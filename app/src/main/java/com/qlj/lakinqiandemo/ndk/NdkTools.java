package com.qlj.lakinqiandemo.ndk;

public class NdkTools {

    static {
        System.loadLibrary("NdkTest-jni");
    }

    public static native String getStringFromNDK();
}
