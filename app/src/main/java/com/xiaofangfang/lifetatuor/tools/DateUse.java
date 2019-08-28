package com.xiaofangfang.lifetatuor.tools;

import java.util.Date;

/**
 * 提供了对时间的处理
 */
public class DateUse {

    /** * 获取精确到秒的时间戳 * @return */
    public static int getSecondTimestamp(Date date){
        if (null == date) {
            return 0;
        }
        String timestamp = String.valueOf(date.getTime());
        int length = timestamp.length();
        if (length > 3) {
            return Integer.valueOf(timestamp.substring(0,length-3));
        } else {
            return 0;
        }
    }

    public static int getDaySecond(){
        return 60*60*24;
    }



}
