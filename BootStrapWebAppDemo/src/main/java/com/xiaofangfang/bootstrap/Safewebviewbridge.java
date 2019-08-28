package com.xiaofangfang.bootstrap;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import cn.pedant.SafeWebViewBridge.InjectedChromeClient;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.ConsoleMessage;
import android.webkit.ValueCallback;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import com.xiaofangfang.bootstrap.bridge.HostJsScope;
import com.xiaofangfang.juerymobiledemo.R;

public class Safewebviewbridge extends AppCompatActivity implements View.OnClickListener {


    private static final String TAG = "test";
    Button bt1, btn2, btn3;
    WebView webView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_safewebviewbridge);

        bt1 = findViewById(R.id.btn1);
        btn2 = findViewById(R.id.btn2);
        btn3 = findViewById(R.id.btn3);

        bt1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);


        webView = findViewById(R.id.webView);

        webView.getSettings().setJavaScriptEnabled(true);

        webView.loadUrl("file:///android_asset/js/webView.html");
        webView.setWebChromeClient(new InjectedChromeClient("HostApp", HostJsScope.class) {


            /**
             * 控制台的输出信息
             * @param consoleMessage
             * @return
             */
            @Override
            public boolean onConsoleMessage(ConsoleMessage consoleMessage) {


                Log.d(TAG, "onConsoleMessage: 接收到控制台消息====>>>>" + consoleMessage.message());


                return super.onConsoleMessage(consoleMessage);
            }
        });

        webView.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {

                /**
                 * 当加载新的网页的时候进行回调
                 */
                Log.d(TAG, "加载的url====>>>>" + url);
                view.loadUrl(url);

                /**
                 * 返回true 代表的将当前请求的url交给自己进行处理
                 */
                return true;
            }
        });


    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.btn1:


                break;
            case R.id.btn2:


                break;
            case R.id.btn3:


                break;
        }
    }
}
