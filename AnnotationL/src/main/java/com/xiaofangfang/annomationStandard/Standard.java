package com.xiaofangfang.annomationStandard;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class Standard {




    public static final int MODE_PRIVATE = 0x0000;

    public static  final  int MODE_WRITEABLE=0x111;

    public static final int MODE_DB=0X333;


        @IntDef(flag = true,value = {

                MODE_PRIVATE,
                MODE_WRITEABLE,

        })
    @Retention(RetentionPolicy.SOURCE)
    public @interface   OprateMode{

    }


    /**
     * 通过这个方案我们就能实现对接口的统一
     * @param mode
     */
    public void setOPrateMode(@OprateMode int mode){

    }

    public static void main(String[] argc){


        Standard standard=new Standard();

        /**
         * 如果设置的不是我们指定的值，就会报错
         */
//        standard.setOPrateMode(MODE_DB);

        standard.setOPrateMode(MODE_PRIVATE);



    }




}
