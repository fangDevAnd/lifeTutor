package com.lyc.hybird;


import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * @author Mohammed.Tell.Yes
 * @create time 2016.5.12
 * */
@SuppressLint("NewApi")
public class ExtWebView extends WebView {

    private OnScrollChangedListener mOnScrollChangedListener;

    private boolean isDefault;

    private Context mContext;

    public ExtWebView(Context context,boolean isDefault){
        super(context);
        mContext = context;
        if(isDefault) initDefault();
    }

    public ExtWebView(Context context, AttributeSet attrs){
        super(context,attrs);
        mContext = context;
        initDefault();
    }

    public ExtWebView(Context context, AttributeSet attrs, int defStyle,boolean isDefault) {
        super(context, attrs, defStyle);
        mContext = context;
        if(isDefault) initDefault();
    }

    //滚动事件
    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (mOnScrollChangedListener != null) {
            mOnScrollChangedListener.onScroll(l, t, oldl, oldt);
        }
    }

    public void setOnScrollChangedListener(final OnScrollChangedListener callback){
        this.mOnScrollChangedListener = callback;
    }

    //滚动事件接口
    public static interface OnScrollChangedListener{

        public void onScroll(int l, int t, int oldl, int oldt);
    }



    //初始化默认WebView配置
    private void initDefault() {

        //禁用长按默认事件,此处可暴露接口回调给长按事件
        this.setOnLongClickListener(new OnLongClickListener() {

            @Override
            public boolean onLongClick(View v) {

                return true;
            }
        });


        this.setWebChromeClient(new WebChromeClient(){

            @Override
            public void onProgressChanged(WebView view, int newProgress) {

                super.onProgressChanged(view, newProgress);
            }


        });
        this.setWebChromeClient(new ExtWebChromeClient());

        this.setWebViewClient(new WebViewClient(){

            //设置在webView点击打开的新网页在当前界面显示，而不是跳转到浏览器中
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            //设置页面加载结束时，保存cookie
            //@Override
/*			public void onPageFinished(WebView view, String url) {
				CookieManager cm = CookieManager.getInstance();
				String cookiestr = cm.getCookie(url);
				Toast.makeText(getContext(), cookiestr, Toast.LENGTH_LONG).show();

				super.onPageFinished(view, url);
			}*/

            @Override
            public void onPageFinished(WebView view, String url) {

                super.onPageFinished(view, url);
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {

                super.onReceivedError(view, errorCode, description, failingUrl);
            }




        });

        WebSettings setting = this.getSettings();
        //设置启动javascript
        setting.setJavaScriptEnabled(true);
        //设置启用viewport
        setting.setUseWideViewPort(true);
        //设置自适应屏幕
        setting.setLoadWithOverviewMode(true);
        //设置支持缩放
        setting.setSupportZoom(true);
        //设置不显示缩放控件
        setting.setDisplayZoomControls(false);
        //设置utf-8
        setting.setDefaultTextEncodingName("UTF-8");
        //设置允许跨域相关
        setting.setAllowFileAccess(true);
        setting.setAllowContentAccess(true);
        setting.setAllowFileAccessFromFileURLs(true);
        setting.setAllowUniversalAccessFromFileURLs(true);

        //设置关闭滚动条
        this.setHorizontalScrollBarEnabled(false);
        this.setVerticalScrollBarEnabled(false);

        //设置JS接口

        /**
         * nobj 是一个在 js代码中被保存在 window中的
         * native对 js暴露的顶级的对象接口
         *
         *
         *
         */

        this.addJavascriptInterface(new JSObject(mContext,this), "nobj");
    }

}
