package com.rcs.nchumanity.crashHandler;

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class CrashReportedUtils {

    /**
     * 日志文件的缓存目录
     */
    public static String sCrashDirectory;

    /**
     * 上传的服务器地址
     */
    private static final String SERVER_UPLOAD = "https://www.baidu.com/";

    /**
     * 初始化
     */
    public static void init(Context context, String directoryPath) {

        setStoredDirectory(directoryPath, context);
        CrashHandler crashHandler = CrashHandler.getInstance();
        crashHandler.init(context);
    }

    /**
     * 设置日志文件保存的目录
     *
     * @param directoryPath
     */
    private static void setStoredDirectory(String directoryPath, Context context) {

        if (directoryPath == null) {
            return;
        }

        File dir = new File(directoryPath);

        if (!dir.exists()) {
            dir.mkdirs();
        }

        if (dir.isDirectory()) {
            sCrashDirectory = dir.getAbsolutePath();
        } else {
            sCrashDirectory = context.getFilesDir().getAbsolutePath();//传入的路径无效就用这个默认的路径
            try {
                throw new Exception("自定义的保存路径格式无效，已使用默认路径");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 获得已经保存的全部日志文件
     */
    public static File[] getCrashReportedFiles() {

        File filesDir = new File(sCrashDirectory);
        FilenameFilter filter = new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.endsWith(CrashHandler.CRASH_REPORTER_EXTENSION);
            }
        };

        return filesDir.listFiles(filter);
    }

    /**
     * 删除日志文件或这个目录下的所有日志文件
     *
     * @param file
     */
    public static void deleteFile(File file) {

        if (file == null) {
            throw new NullPointerException("file is null");
        }

        if (!file.exists()) {
            return;
        }

        if (file.isFile()) {
            file.delete();
        }

        if (!file.isDirectory()) {
            return;
        }

        File[] files = file.listFiles();
        for (File crashFile : files
        ) {
            crashFile.delete();
        }

    }

    /**
     * 获取最新修改的日志文件
     *
     * @return
     */
    public static File getLatestCrashReportFile() {

        if (CrashReportedUtils.getCrashReportedFiles().length == 0) {
            return null;
        }

        File directory = new File(sCrashDirectory);

        File[] files = directory.listFiles();

        Arrays.sort(files, new Comparator<File>() {
            @Override
            public int compare(File file1, File file2) {
                return (int) (file2.lastModified() - file1.lastModified());
            }
        });

        return files[0];
    }


    /**
     * 发送最新的错误报告到服务器
     */
    public static void sendCrashedReportsToServer() {

        final File file = CrashReportedUtils.getLatestCrashReportFile();

        if (file == null) {
            Log.i("TAG", "奔溃日志文件并不存在,发送失败(；′⌒`)");
            return;
        }

        OkHttpClient client = new OkHttpClient();
        MediaType type = MediaType.parse("File/*");
        RequestBody requestBody = RequestBody.create(type, file);
        final Request request = new Request.Builder()
                .post(requestBody)
                .url(SERVER_UPLOAD)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

            }
        });
    }

}
