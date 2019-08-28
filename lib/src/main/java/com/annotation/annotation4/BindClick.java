package com.annotation.annotation4;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.CLASS)
public @interface BindClick {

    /**
     * 这个的作用是用来获得绑定元素的id号
     * @return
     */
    int value();

}
