package com.rcs.nchumanity.ul;

import android.os.Bundle;
import android.webkit.JsPromptResult;
import android.webkit.WebBackForwardList;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.Nullable;

import com.rcs.nchumanity.R;
import com.rcs.nchumanity.view.CommandBar;
import com.rcs.nchumanity.webViewScope.WebNativeBridge;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WebLoadActivity extends ParentActivity {


    public static final String URL = "url";

    private String url;

    public static final String TITLE = "title";

    private WebView webView;

    private String title;

    @BindView(R.id.toolbar)
    CommandBar toolbar;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        ButterKnife.bind(this);

        url = getIntent().getExtras().getString(URL);
        title = getIntent().getExtras().getString(TITLE);
        if (url == null || title == null) {
            throw new IllegalArgumentException("please transport url to load");
        }

        toolbar.setTitle(title);
        
        webView = findViewById(R.id.webView);

        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });

        webView.loadUrl(url);

    }
}
