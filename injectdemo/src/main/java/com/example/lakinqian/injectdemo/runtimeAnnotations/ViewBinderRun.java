package com.example.lakinqian.injectdemo.runtimeAnnotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD) // 标识改注解应用在成员变量上
@Retention(RetentionPolicy.RUNTIME) // 标识生命周期是运行时
public @interface ViewBinderRun {
    int id() default -1;
}
