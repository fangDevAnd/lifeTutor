package com.xiaofangfang.lifetatuor.dao;

import android.content.Context;

import com.xiaofangfang.lifetatuor.server.FileVisitServer;
import com.xiaofangfang.lifetatuor.tools.Looger;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 应用程序的持久层,实现的是对数据的持久化
 * 操作,使用的技术是序列化到文件的技术
 */
public class DbOpener {

    public static String fileName = null;

    public static final String[] classNameSet = {
            "JokeAndImg", "ImgByTime", "JokeByTime", "NewstImg", "NewstJoke"
            , "CommonNews", "Domestic", "Economics", "Entertainment", "Fashion"
            , "International", "Military", "Science", "Sociology", "Sport"
            , "TopInfo", "Root"
    };


    /**
     * 存放的信息是json的形式保存的
     * 保存天气信息到文件
     */
    public static void saveInfo(Class className,
                                Context context, String jsonData) {

        String classn = className.getSimpleName();
        File fileDir = null;
        for (int i = 0; i < classNameSet.length; i++) {
            if (classn.equals(classNameSet[i])) {
                //在这里会在本程序的文件夹下面创建与类名称相对应的文件
                fileName = classNameSet[i];
                fileDir = FileVisitServer.getDir(fileName,
                        Context.MODE_PRIVATE, context);
                break;
            } else {
                Looger.d("请求的类型没有找到");
            }
        }

        File file = new File(fileDir, fileName + ".txt");

        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
            ByteArrayInputStream bai =
                    new ByteArrayInputStream(jsonData.getBytes());
            DataInputStream dis = new DataInputStream(bai);
            byte[] b = new byte[1024];
            int len;
            while ((len = dis.read(b)) != -1) {
                fos.write(b, 0, len);
            }
            fos.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 读取天气的文件信息
     */
    public static String readInfo(Context context, String className) {

        File fileDir = null;
        for (int i = 0; i < classNameSet.length; i++) {
            if (className.equals(classNameSet[i])) {
                //在这里会在本程序的文件夹下面创建与类名称相对应的文件
                fileName = classNameSet[i];
                fileDir = FileVisitServer.getDir(fileName,
                        Context.MODE_PRIVATE, context);
                break;
            }
        }
        BufferedReader reader = null;
        File file = new File(fileDir, fileName + ".txt");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        StringBuffer sb = new StringBuffer();
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(file);
            String line;
            reader = new BufferedReader(new InputStreamReader(fis));
            line = reader.readLine(); // 读取第一行
            while (line != null) { // 如果 line 为空说明读完了
                sb.append(line); // 将读到的内容添加到 buffer 中
                line = reader.readLine(); // 读取下一行
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
                if (fis != null) {
                    fis.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }


}
