package com.xiaofangfang.rice2_verssion.activity;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.xiaofangfang.rice2_verssion.ParentActivity;
import com.xiaofangfang.rice2_verssion.R;
import com.xiaofangfang.rice2_verssion.network.NetRequest;
import com.xiaofangfang.rice2_verssion.view.CommandBar;

public class ServerTatour extends ParentActivity {


    WebView webView;
    private long exitTime = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.server_tatour);
        super.onCreate(savedInstanceState);

    }

    @Override
    public void initView() {
        webView = findViewById(R.id.webView);
        webView.setWebViewClient(new WebViewClient() {
            //设置在webView点击打开的新网页在当前界面显示,而不跳转到新的浏览器中
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        webView.getSettings().setJavaScriptEnabled(true); //设置WebView属性,运行执行js脚本
        webView.loadUrl(NetRequest.serverMain + "/html/tatour1.html");//调用loadView方法为WebView加入链接

        CommandBar com = findViewById(R.id.commandBar);
        com.setTitle("指南");
    }

}

