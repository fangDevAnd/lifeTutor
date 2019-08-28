package com.xiaofangfang.bootstrap;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.xiaofangfang.juerymobiledemo.R;

public class WebActivityTest_demo2 extends AppCompatActivity {


    private static final String TAG = "test";
    WebView webView;
    WebSettings webSettings;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_test);
        webView = findViewById(R.id.webView);
        webView.loadUrl("file:///android_asset/js/test1.html");
        webSettings = webView.getSettings();
        //设置js可用
        webSettings.setJavaScriptEnabled(true);
        //设置可以弹窗
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        //添加js对java代码的调用
        webView.addJavascriptInterface(new JavaObject(), "demo1");


        //下面就是两种java调用js代码的方式，前者会刷新数据，效率低
        findViewById(R.id.click).setOnClickListener((v) -> {

            webView.loadUrl("javascript:demo1()");


        });


        findViewById(R.id.click).setOnClickListener((v) -> {

            // 只需要将第一种方法的loadUrl()换成下面该方法即可
            webView.evaluateJavascript("javascript:demo1()", new ValueCallback<String>() {
                @Override
                public void onReceiveValue(String value) { //此处为 js 返回的结果

                    Log.d(TAG, "onReceiveValue: 返回的结果是" + value);
                }
            });
        });

        //处理兼容问题的实现代码
        // Android版本变量
        final int version = Build.VERSION.SDK_INT;
        // 因为该方法在 Android 4.4 版本才可使用，所以使用时需进行版本判断
        if (version < 18) {
            webView.loadUrl("javascript:demo1()");
        } else {
            webView.evaluateJavascript("javascript:demo1()", new ValueCallback<String>() {
                @Override
                public void onReceiveValue(String value) { //此处为 js 返回的结果
                }
            });
        }


        webView.setWebViewClient(new WebViewClient() {


            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {

                Log.d(TAG, "加载的url====" + url);
                Uri uri = Uri.parse(url);

                if (uri.getScheme().equals("js")) {

                    if (uri.getAuthority().equals("webview")) {

                        //代表的这个请求是我们发送的请求，这个请求被拦截
                        Log.d(TAG, "执行拦截代码 ");
                        Toast.makeText(WebActivityTest_demo2.this, "js调用了android", Toast.LENGTH_SHORT).show();
                    }

                    String result = "你好";

                    //接下来向js传递返回值
                    webView.loadUrl("javascript:returnResult(" + result + ")");


                    return true;//代表的是自己进行处理
                }

                return super.shouldOverrideUrlLoading(view, url);
            }
        });


        // 由于设置了弹窗检验调用结果,所以需要支持js对话框
        // webview只是载体，内容的渲染需要使用webviewChromClient类去实现
        // 通过设置WebChromeClient对象处理JavaScript的对话框
        // 设置响应js 的Alert()函数

        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onJsAlert(WebView view, String url, String message, final JsResult result) {
                AlertDialog.Builder b = new AlertDialog.Builder(WebActivityTest_demo2.this);
                b.setTitle("Alert");
                b.setMessage(message);
                b.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        result.confirm();
                    }
                });
                b.setCancelable(false);
                b.create().show();

                //返回true代表是当前的弹窗会被处理
                return true;
            }


            /**
             * 处理确认框
             * @param view
             * @param url
             * @param message
             * @param result
             * @return
             */
            @Override
            public boolean onJsConfirm(WebView view, String url, String message, JsResult result) {

                Log.d(TAG, "onJsPrompt:   url=" + url + " " + message + " " + result.toString());
                return true;
            }

            /**
             * 处理提示输入框
             * @param view
             * @param url   是web.loadUrl()里面加载的url
             * @param message   执行的消息，也就是js中prompt里面的内容
             * @param defaultValue
             * @param result  执行结果
             * @return
             */
            @Override
            public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, JsPromptResult result) {

                Log.d(TAG, "onJsPrompt:   url=" + url + "\nmessage=" + message + "\ndefaultValue=" + defaultValue + "\nresult=" + result.toString());

                Uri uri = Uri.parse(url);

                if (uri.getScheme().equals("js")) {

                    if (uri.getAuthority().equals("demo")) {

                        //代表的这个请求是我们发送的请求，这个请求被拦截
                        Log.d(TAG, "执行拦截代码 ");
                        Toast.makeText(WebActivityTest_demo2.this, "android传递参数给js" +
                                "或者通过该接口调用js代码", Toast.LENGTH_SHORT).show();
                    }


                    return true;//代表的是自己进行处理
                }


                return true;
            }
        });


    }


    public class JavaObject {


        @JavascriptInterface
        public void printLog(String message) {
            Log.d("test", "printLog: =====调用了");
        }

        @JavascriptInterface
        public void showToast(String message) {
            Toast.makeText(WebActivityTest_demo2.this, message, Toast.LENGTH_SHORT).show();
        }
    }

}
