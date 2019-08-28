package com.xiaofangfang.filterrice.service;

import android.app.IntentService;
import android.content.Intent;
import android.os.Environment;

import androidx.annotation.Nullable;

import com.xiaofangfang.filterrice.model.MyResponseData;
import com.xiaofangfang.filterrice.tool.NetRequest;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;


/**
 * 用于实现对图片的等文本数据进行缓存的数据
 */
public class CacheService extends IntentService {


    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public CacheService(String name) {
        super(name);
    }


    private MyResponseData myResponseData;
    private int index;


    /**
     * 该方法运行在子线程中
     *
     * @param intent
     */
    @Override
    protected void onHandleIntent(@Nullable Intent intent) {


        myResponseData = (MyResponseData) intent.getSerializableExtra(MyResponseData.class.getSimpleName());
        index = intent.getIntExtra("index", -1);//这个对应的是view的索引


        String imageAddress = myResponseData.getImageAddress();

        NetRequest.requestUrl(imageAddress, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                InputStream inputStream = response.body().byteStream();
                writeFile(inputStream);
            }
        });


    }

    private void writeFile(InputStream inputStream) {

        String fileName = myResponseData.getImageAddress().substring(myResponseData.getImageAddress().lastIndexOf("/") + 1);

        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
                index + "_" + myResponseData.getViewName() + "_" + fileName);
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(file);
            byte[] b = new byte[1024];
            int len;
            while ((len = inputStream.read(b)) != -1) {
                fileOutputStream.write(b, 0, len);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fileOutputStream.close();
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
