package com.example.componentasystemtest.net;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.componentasystemtest.R;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class NetRequestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_net);

      load();
    }

    public void load(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String url1 = "http://193.112.182.184:8888/ncrd/sys/admin/smsLogin?mobilephone=13077995907";
                    URL url = new URL(url1);
                    //得到connection对象。
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    //设置请求方式
                    connection.setRequestMethod("GET");
                    //连接
                    connection.connect();

                    //得到响应码
                    int responseCode = connection.getResponseCode();
                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        //得到响应流
                        InputStream inputStream = connection.getInputStream();
                        //将响应流转换成字符串
                        String result = is2String(inputStream);//将流转换为字符串。
                        Log.d("test","result=============" + result);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }


    private static String is2String(InputStream inputStream) throws IOException {

        StringBuilder builder = new StringBuilder();

        InputStreamReader ir = new InputStreamReader(inputStream);

        char[] c = new char[1024];
        int len;
        while ((len = ir.read(c, 0, c.length)) != -1) {
            builder.append(c,0,len);
        }
        return builder.toString();
    }



}
