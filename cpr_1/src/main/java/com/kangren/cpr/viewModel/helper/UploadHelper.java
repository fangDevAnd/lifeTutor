package com.kangren.cpr.viewModel.helper;

import android.graphics.Bitmap;

import com.lyc.utils.ByteUtil;

import java.io.UnsupportedEncodingException;


public class UploadHelper {

    public static byte JPG_TYPE = 2;
    public static byte JSON_TYPE = 1;
    public static byte NAME_TYPE = 3;

    private static byte[] getHead(int len) {
        byte[] size = new byte[4];
        byte[] head = new byte[5];


        System.arraycopy(ByteUtil.intToBytes(len), 0, head, 1, 4);
        return head;
    }

    public static byte[] imageToBytes(Bitmap bitmap, byte type) {
        byte[] res = null;
        byte[] content = null;
        byte[] head = null;

        content = ByteUtil.ImageToBytes(bitmap);
        head = getHead(content.length);
        head[0] = type;
        res = ByteUtil.combine(head, content);


        return res;
    }

    public static byte[] stringToBytes(String msg, byte type) {
        byte[] res = null;
        byte[] content = null;
        byte[] head = null;
        try {
            content = msg.getBytes("UTF-8");
            head = getHead(content.length);
            head[0] = type;
            res = ByteUtil.combine(head, content);
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return res;

    }
}
