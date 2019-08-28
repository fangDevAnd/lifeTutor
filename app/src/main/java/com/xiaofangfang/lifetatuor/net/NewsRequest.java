package com.xiaofangfang.lifetatuor.net;

import com.xiaofangfang.lifetatuor.net.progress.NetRequest;
import com.xiaofangfang.lifetatuor.set.SettingStandard;

import okhttp3.Call;
import okhttp3.Callback;


/**
 * 新闻的请求类
 */
public class NewsRequest {

    /***
     * 根据请求的类型获得数据
     * @param type
     */
    public static void getNewsInfo(SettingStandard.News.NewsType type, Callback callback) {
        String url = SettingStandard.News.NEWS_URL + type.type;
        NetRequest.request(url, callback);
    }


}
