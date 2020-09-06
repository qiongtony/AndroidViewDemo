package com.example.annotiondemo.annotation;

import androidx.annotation.IdRes;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
// 作用的类型：属性
@Target(ElementType.FIELD)
public @interface BindView {
    // @IdRes 只能穿id资源
    @IdRes int value();
}
