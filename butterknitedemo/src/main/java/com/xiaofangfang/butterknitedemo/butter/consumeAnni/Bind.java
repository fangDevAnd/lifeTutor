package com.xiaofangfang.butterknitedemo.butter.consumeAnni;


import androidx.annotation.IdRes;
import androidx.annotation.LayoutRes;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(value = RetentionPolicy.RUNTIME)
public @interface Bind {
    @LayoutRes
    int bindContent() default -1;

    @IdRes
    int bindView() default -1;

    @IdRes
    int[] onCLick() default -1;

}
