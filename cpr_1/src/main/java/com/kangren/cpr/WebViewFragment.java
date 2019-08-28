package com.kangren.cpr;


import android.annotation.SuppressLint;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.kanren.cpr.R;
import com.lyc.hybird.ExtWebView;

public class WebViewFragment extends Fragment {


    private String lastTitle, lastTitle2;
    private ExtWebView webView;
    private String url;
    private MainActivity mc;

    @SuppressLint("ValidFragment")
    public WebViewFragment(String url) {
        this.url = url;
    }


    public WebViewFragment(){

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_webview, container, false);
        mc = (MainActivity) getActivity();
        webView = (ExtWebView) view.findViewById(R.id.cprView);
        webView.loadUrl("file:///android_asset/cpr.html#/cpr/" + this.url);
        lastTitle = mc.getCurrentTitle();
        lastTitle2 = mc.getCurrentTitle2();
        mc.updateTitle("");

        mc.updateTitle2("");
        return view;
    }

    @Override
    public void onDestroyView() {
        // TODO Auto-generated method stub
        mc.updateTitle(lastTitle);
        mc.updateElapsedTimeTitle(lastTitle2);
        super.onDestroyView();
    }

    public boolean canGoBack() {
        return webView.canGoBack();
    }

    public void goBack() {
        webView.loadUrl("javascript:onBackEvent()");
    }

}
