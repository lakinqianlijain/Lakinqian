package com.qlj.lakinqiandemo.ndk;

public class DynamicNdkTools {
    static {
        System.loadLibrary("NdkTest-jni");
    }

    //加法
    public static native int  add(int a,int b);

    //减法
    public static native int sub(int a,int b);

    //乘法
    public static native int mul(int a,int b);

    //除法
    public static native int div(int a,int b);
}
