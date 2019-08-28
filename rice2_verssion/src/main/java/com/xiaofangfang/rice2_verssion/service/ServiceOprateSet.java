package com.xiaofangfang.rice2_verssion.service;


import java.io.IOException;

import okhttp3.Response;

/**
 * 服务要做的事的集合
 */
public interface ServiceOprateSet {

    public void loadData(String url, int what);

    public void onDown(String url, int what) throws IOException;

    public void onSuccess(Response response, int what) throws IOException;

    public void onError(Exception e);

}
