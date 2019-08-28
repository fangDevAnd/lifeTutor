package com.rcs.nchumanity.tool;


import org.apache.commons.codec.digest.DigestUtils;

import java.math.BigInteger;
import java.security.MessageDigest;

/**
 * 用于对系统的密码进行加密以及进行解密的操作
 */

@Deprecated
public class Crypto {


    /**
     * 进行加密运算
     *
     * @param password 返回加密后的字符串
     * @return
     */
    public static String encryption(String password) {
        byte[] bytes = DigestUtils.md5(password);
        String value = new String(bytes);
        return value;
    }


    public static String getMD5(String str) {
        try {
            // 生成一个MD5加密计算摘要
            MessageDigest md = MessageDigest.getInstance("MD5");
            // 计算md5函数
            md.update(str.getBytes("utf-8"));
            // digest()最后确定返回md5 hash值，返回值为8为字符串。因为md5 hash值是16位的hex值，实际上就是8位的字符
            // BigInteger函数则将8位的字符串转换成16位hex值，用字符串来表示；得到字符串形式的hash值
            String md5 = new BigInteger(1, md.digest()).toString(16);
            //BigInteger会把0省略掉，需补全至32位
            return md5.length() == 32 ? md5 : getMD5("0" + md5);
        } catch (Exception e) {
            throw new RuntimeException("MD5加密错误:" + e.getMessage(), e);
        }
    }


}
