package com.kangren.cpr;

import android.content.Context;
import android.os.Environment;

import com.kangren.cpr.analyzer.F2800Analyzer;
import com.lyc.db4o.Db;
import com.lyc.socket.client.Client;

import java.io.File;
import java.io.IOException;

public class AppConfig {


    private static AppConfig appConfig;

    public static Context context;


    public static AppConfig getInstance() {

        if (appConfig == null) {
            appConfig = new AppConfig();
        }
        return appConfig;
    }

    public Class<?> AnlyzerType = F2800Analyzer.class;
    //蓝牙数据包的长度
    public int BluetoothPackageLength = 20;
    /**
     * 默认连接的ip地址
     */
    public String ip = "192.168.0.12";
    /**
     * 默认的端口
     */
    public int port = 9002;

    public boolean IsInit;
    public String PrintName = "";
    public String ModelName = "";
    public String KnowledgePath = context.getCacheDir() + "/kangren/Knowledge/";
    public Client server;
    public String[] moduleInfo = new String[]{
            "����ѧϰ",
            "ѵ��ģʽ",
            "����ģʽ",
            "ʵսģʽ",
            "��ʷ�ɼ�",

    };


    /**
     * 模型人设备是否被连接
     */
    public boolean ModelConnected = false;

    /**
     * 打印机设置是否被连接
     */
    public boolean PrintConnected = false;
    public String BaseDir = context.getCacheDir() + "/kangren/";
    public String DbFileName = context.getCacheDir() + "/kangren/db.db4o";
    public Db db;

    private AppConfig() {


        File baseFile = new File(BaseDir);
        File knowledge = new File(KnowledgePath);
        if (!baseFile.exists()) {
            baseFile.mkdir();
        }
        if (!knowledge.exists()) {
            knowledge.mkdir();
        }

        File dbFileName = new File(DbFileName);
        if (!dbFileName.exists()) {
            try {
                dbFileName.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        db = new Db(DbFileName);
    }

}
