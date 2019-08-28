package com.annotation.annotaion3;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.CLASS)
public @interface BindView {

    /**
     * 返回的是bindView的值
     * 也就是view的id值
     * @return
     */
    int value();

}
