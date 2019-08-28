package com.lyc.utils;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * 日期格式化
 */
public class DateUtil {
    public static Date parse(String strDate,String format) {
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        /**
         *
         * The method attempts to parse text starting at the index given by pos.
         * 就是类似先把给的字符串从ParsePosition对象的index开始截取，然后对截取的字符串parse
         */
        ParsePosition pos = new ParsePosition(0);
        Date strtodate = formatter.parse(strDate, pos);
        return strtodate;
    }
}
