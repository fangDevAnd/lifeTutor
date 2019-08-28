package com.rcs.nchumanity.tool;

import android.text.TextUtils;
import android.view.View;

import androidx.annotation.IdRes;

import java.util.regex.Pattern;

/**
 * 提供的对View的绑定
 */
public class StringTool {


    /**
     * 图片地址存放的分隔符
     * 暂时定为 &nbsp;作为分隔
     */
    public static final String DELIMITER = "&nbsp;";


    /**
     * 首行缩进
     */
    public static final String TEXT_INDENT="\u3000\u3000";


    /**
     * 验证数据的是否为空，以及长度
     *
     * @param text
     * @param length
     * @return
     */
    public static boolean accessLength(String text, int length) {
        if (TextUtils.isEmpty(text) || text.length() < length) {
            return false;
        }
        return true;
    }


    /**
     * 身份验证
     *
     * @param text
     * @return
     */
    public static boolean identityAssess(String text) {
        String pattern = "^[1-9]\\d{5}[1-9]\\d{3}((0[1-9])|(1[0-2]))(([0|1|2][1-9])|3[0-1])((\\d{4})|\\d{3}X)$";
        boolean isMatch = Pattern.matches(pattern, text);
        return isMatch;
    }


}
