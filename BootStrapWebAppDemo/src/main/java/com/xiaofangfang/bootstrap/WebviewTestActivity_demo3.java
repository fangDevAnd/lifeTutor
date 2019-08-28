package com.xiaofangfang.bootstrap;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.webkit.ConsoleMessage;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Toast;

import com.xiaofangfang.juerymobiledemo.R;

import java.lang.reflect.Array;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class WebviewTestActivity_demo3 extends AppCompatActivity implements View.OnClickListener {


    private static final String TAG = "test";
    WebView webView;

    Button jsCalljava, jsCallJavaToRes, promptUse;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_webview_test);

        webView = findViewById(R.id.webView);
        //android_asset
        webView.loadUrl("file:///android_asset/js/webView.html");

        webView.getSettings().setJavaScriptEnabled(true);


        jsCalljava = findViewById(R.id.btn1);
        jsCallJavaToRes = findViewById(R.id.btn2);
        promptUse = findViewById(R.id.btn3);

        jsCalljava.setOnClickListener(this);
        jsCallJavaToRes.setOnClickListener(this);
        promptUse.setOnClickListener(this);


        webView.setWebViewClient(new WebViewClient() {

            /**
             * 如果不复写这个方法，点击里面的url，会加载错误
             * 返回false，不会加载
             * @param view
             * @param request
             * @return
             */
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {

                //返回true，代表的是交给宿主程序执行,加载web资源会被停止掉，因为交给了宿主程序

                Log.d(TAG, "shouldOverrideUrlLoading: +url加载====" + request.getUrl());
                //这个方法居然没有打印log????,

                Uri uri = request.getUrl();
                if (uri.getScheme().equals("js") && uri.getAuthority().equals("webview")) {

                    Set<String> set = uri.getQueryParameterNames();

                    List<String> list = new ArrayList<>(set);

                    StringBuilder stringBuilder = new StringBuilder();

                    for (String key : list) {
                        String value = uri.getQueryParameter(key);
                    }

                    String resultStr = uri.toString().substring(uri.toString().lastIndexOf("?"));

                    resultStr = URLDecoder.decode(resultStr);


                    Log.d(TAG, "shouldOverrideUrlLoading: 接收到js的消息" + resultStr);


                    return true;

                }
                return false;
            }


            /**
             * 页面加载完成后执行
             *
             * @param view
             * @param url
             */
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                //页面加载完成
                Log.d(TAG, "onPageFinished: ===web页面加载完成" + url);
            }

            @Deprecated
            public WebResourceResponse shouldInterceptRequest(WebView view,
                                                              String url) {
                return null;
            }


            /**
             * HTTP的body标签加载前调用，仅在主frame调用。
             * @param view
             * @param url
             */
            public void onPageCommitVisible(WebView view, String url) {

            }


            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                //开始加载页面的时候调用
                Log.d(TAG, "onPageStarted: ===web页面开始加载" + url);
            }

            @Override
            public void onLoadResource(WebView view, String url) {
                super.onLoadResource(view, url);
                //在加载资源的时候会一直调用
                Log.d(TAG, "onLoadResource: ===web页面加载资源" + url);
            }
        });


        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
                // Call the old version of this function for backwards compatability.
                onConsoleMessage(consoleMessage.message(), consoleMessage.lineNumber(),
                        consoleMessage.sourceId());

                Log.d(TAG, "webBrowser Console---->" + consoleMessage);

                return false;
            }

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                //页面加载进度发生的时候调用
                Log.d(TAG, "onProgressChanged: ===页面加载进度" + newProgress);
            }


            @Override
            public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, JsPromptResult result) {

                Log.d(TAG, "onJsPrompt: 输入的内容" + message);

                result.confirm("android接收到了js的数据");
                //交给宿主程序进行处理，否者会弹出两个窗口

                return false;
            }


            @Override
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {

                Log.d(TAG, "onJsAlert: 弹出窗口的消息==" + message);

                return super.onJsAlert(view, url, message, result);
            }
        });


    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.btn1:


                if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {

                    webView.evaluateJavascript("javascript:demo1_1('你好js代码')", new ValueCallback<String>() {
                        @Override
                        public void onReceiveValue(String value) {
                            Log.d(TAG, "onReceiveValue: 调用js代码的返回值是" + value);
                        }
                    });

                } else {

                    /**
                     * 调用了js代码
                     */
                    webView.loadUrl("javascript:demo1('你好js代码')");
                }
                break;
            case R.id.btn2:

                webView.loadUrl("javascript:demo2('你好，你是谁?')");

                break;
            case R.id.btn3:
                webView.loadUrl("javascript:demo3()");
                break;
        }
    }


    long endTime = 0;

    @Override
    public void onBackPressed() {
        long startTime = System.currentTimeMillis() - endTime;

        if (endTime == 0) {
            Toast.makeText(this, "再点击一次退出", Toast.LENGTH_SHORT).show();
        }
        if (startTime < 2000) {
            finish();
        } else {
            if (webView.canGoBack()) {
                webView.goBack();
            }
        }
        endTime = System.currentTimeMillis();
    }

}
