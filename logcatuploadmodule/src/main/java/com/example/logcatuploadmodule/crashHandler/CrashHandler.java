package com.example.logcatuploadmodule.crashHandler;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.Log;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

public class CrashHandler implements Thread.UncaughtExceptionHandler {


    /**
     * CrashHandler实例
     */
    private static CrashHandler sCrashHandler;

    /**
     * 系统默认的UncaughtExceptionHandler处理类
     */
    private Thread.UncaughtExceptionHandler mDefaultHandler;

    /**
     * 用LinkedHashMap来保存设备信息和错误堆栈信息
     */
    private Map<String, String> mDeviceCrashInfo = new LinkedHashMap<>();

    private static final String VERSION_NAME = "versionName";
    private static final String VERSION_CODE = "versionCode";
    private static final String STACK_TRACE = "stackTrace";
    public static final String CRASH_REPORTER_EXTENSION = ".log";

    private Context mContext;


    private CrashHandler() {

    }

    public static CrashHandler getInstance() {
        if (sCrashHandler == null) {
            sCrashHandler = new CrashHandler();
        }
        return sCrashHandler;
    }


    /**
     * 初始化，设置CrashHandler为程序的默认处理类
     *
     * @param context
     */
    public void init(Context context) {
        mContext = context;
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    /**
     * 必须实现的方法，用来处理uncaughtException
     *
     * @param t
     * @param e
     */
    @Override
    public void uncaughtException(Thread t, Throwable e) {

        //如果没有自己处理则交给系统来处理
        if (!handleException(e) && mDefaultHandler != null) {
            mDefaultHandler.uncaughtException(t, e);
        }
    }

    /**
     * 错误处理，收集和存储
     *
     * @param ex
     * @return 处理成功返回true
     */
    private boolean handleException(Throwable ex) {

        if (ex == null) {
            Log.i("TAG", "ex is null (○´･д･)ﾉ");
            return true;
        }

        //收集设备信息
        collectCrashedDeviceInfo(mContext);

        //保存错误报告到文件
        saveCrashInfoToFile(ex);

        return true;
    }

    /**
     * 收集设备信息
     *
     * @param context
     */
    private void collectCrashedDeviceInfo(Context context) {

        PackageManager pm = context.getPackageManager();
        try {
            PackageInfo packageInfo = pm.getPackageInfo(context.getPackageName(), PackageManager.GET_ACTIVITIES);
            if (packageInfo != null) {

                mDeviceCrashInfo.put(VERSION_NAME, packageInfo.versionName);        //版本名
                mDeviceCrashInfo.put(VERSION_CODE, packageInfo.versionCode + "");   //版本号
                mDeviceCrashInfo.put("manufacturer", Build.MANUFACTURER);           //厂商名
                mDeviceCrashInfo.put("product", Build.PRODUCT);                     //产品名
                mDeviceCrashInfo.put("brand", Build.BRAND);                         //手机品牌
                mDeviceCrashInfo.put("model", Build.MODEL);                         //手机型号
                mDeviceCrashInfo.put("device", Build.DEVICE);                       //设备名
                mDeviceCrashInfo.put("sdkInt", Build.VERSION.SDK_INT + "");         //SDK版本
                mDeviceCrashInfo.put("release", Build.VERSION.RELEASE);             //Android版本
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 保存奔溃的错误信息到文件
     *
     * @param ex
     */
    private void saveCrashInfoToFile(Throwable ex) {

        Writer info = new StringWriter();
        PrintWriter printWriter = new PrintWriter(info);

        ex.printStackTrace(printWriter);
        Throwable cause = ex.getCause();
        while (cause != null) {
            cause.printStackTrace(printWriter);
            cause = cause.getCause();
        }

        String result = info.toString();
        printWriter.close();

        mDeviceCrashInfo.put("EXCEPTION", ex.getLocalizedMessage());
        mDeviceCrashInfo.put(STACK_TRACE, result);

        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String> entry : mDeviceCrashInfo.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            sb.append(key);
            sb.append("=");
            sb.append(value);
            sb.append("\n");
        }

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss", Locale.CHINA);
        Date date = new Date(System.currentTimeMillis());

        String fileName = "crash" + simpleDateFormat.format(date) + CRASH_REPORTER_EXTENSION;

        File file = null;
        File directory = new File(CrashReportedUtils.sCrashDirectory);
        if (CrashReportedUtils.sCrashDirectory != null && directory.isDirectory()) {
            file = new File(CrashReportedUtils.sCrashDirectory, fileName);
        } else {
            CrashReportedUtils.sCrashDirectory = mContext.getFilesDir().getAbsolutePath();
            file = new File(CrashReportedUtils.sCrashDirectory, fileName);//默认放在files目录下
        }

        try {
            FileOutputStream trace = new FileOutputStream(file);
            trace.write(sb.toString().getBytes());
            trace.close();
        } catch (IOException e) {
            Log.e("TAG", "an error occurred while write report file..", e);
            e.printStackTrace();
        }
    }


}
