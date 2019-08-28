package com.kangren.base;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;
import android.os.Environment;
import android.os.Looper;
import android.os.SystemClock;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.Thread.UncaughtExceptionHandler;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 *
 * prorgram crash Handler
 *
 */
@SuppressLint("SimpleDateFormat")
public class CrashHandler implements UncaughtExceptionHandler {

    public static String TAG = "MyCrash";

    private UncaughtExceptionHandler mDefaultHandler;

    private static CrashHandler instance = new CrashHandler();
    private Context mContext;


    private Map<String, String> infos = new HashMap<String, String>();


    private DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");


    private CrashHandler() {
    }


    public static CrashHandler getInstance() {
        return instance;
    }


    public void init(Context context) {
        mContext = context;

        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();

        Thread.setDefaultUncaughtExceptionHandler(this);

    }


    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        if (!handleException(ex) && mDefaultHandler != null) {

            mDefaultHandler.uncaughtException(thread, ex);
        } else {
            SystemClock.sleep(3000);

            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(1);
        }
    }


    private boolean handleException(final Throwable ex) {
        if (ex == null)
            return false;

        try {

            new Thread() {

                @Override
                public void run() {
                    Looper.prepare();
                    Toast.makeText(mContext, "产生异常"+ex.getMessage(),
                            Toast.LENGTH_LONG).show();
                    Looper.loop();
                }
            }.start();

            collectDeviceInfo(mContext);

            saveCrashInfoFile(ex);
            SystemClock.sleep(3000);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return true;
    }


    public void collectDeviceInfo(Context ctx) {
        try {
            PackageManager pm = ctx.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(ctx.getPackageName(),
                    PackageManager.GET_ACTIVITIES);
            if (pi != null) {
                String versionName = pi.versionName + "";
                String versionCode = pi.versionCode + "";
                infos.put("versionName", versionName);
                infos.put("versionCode", versionCode);
            }
        } catch (NameNotFoundException e) {
            Log.e(TAG, "an error occured when collect package info", e);
        }
        Field[] fields = Build.class.getDeclaredFields();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                infos.put(field.getName(), field.get(null).toString());
            } catch (Exception e) {
                Log.e(TAG, "an error occured when collect crash info", e);
            }
        }
    }


    private String saveCrashInfoFile(Throwable ex) throws Exception {
        StringBuffer sb = new StringBuffer();
        try {
            SimpleDateFormat sDateFormat = new SimpleDateFormat(
                    "yyyy-MM-dd HH:mm:ss");
            String date = sDateFormat.format(new Date());
            sb.append("\r\n" + date + "\n");
            for (Map.Entry<String, String> entry : infos.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                sb.append(key + "=" + value + "\n");
            }

            Writer writer = new StringWriter();
            PrintWriter printWriter = new PrintWriter(writer);
            ex.printStackTrace(printWriter);
            Throwable cause = ex.getCause();
            while (cause != null) {
                cause.printStackTrace(printWriter);
                cause = cause.getCause();
            }
            printWriter.flush();
            printWriter.close();
            String result = writer.toString();
            sb.append(result);

            String fileName = writeFile(sb.toString());
            return fileName;
        } catch (Exception e) {
            Log.e(TAG, "an error occured while writing file...", e);
            sb.append("an error occured while writing file...\r\n");
            writeFile(sb.toString());
        }
        return null;
    }

    private String writeFile(String sb) throws Exception {
        String time = formatter.format(new Date());
        String fileName = "crash-" + time + ".txt";

        String path = getGlobalpath();
        File dir = new File(path);
        if (!dir.exists())
            dir.mkdirs();

        File crashFile = new File(path + fileName);

        if (!crashFile.exists()) {
            crashFile.createNewFile();
        }

        FileOutputStream fos = new FileOutputStream(crashFile, true);
        fos.write(sb.getBytes());
        fos.flush();
        fos.close();

        return fileName;
    }

    public static String getGlobalpath() {
        return Environment.getExternalStorageDirectory().getAbsolutePath()
                + File.separator + "crash" + File.separator;
    }

    public static void setTag(String tag) {
        TAG = tag;
    }


}