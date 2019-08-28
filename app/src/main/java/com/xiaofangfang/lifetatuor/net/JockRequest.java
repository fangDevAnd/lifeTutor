package com.xiaofangfang.lifetatuor.net;

import com.xiaofangfang.lifetatuor.net.progress.NetRequest;
import com.xiaofangfang.lifetatuor.net.requestModel.JockParamValue;
import com.xiaofangfang.lifetatuor.set.SettingStandard;
import com.xiaofangfang.lifetatuor.tools.Looger;

import okhttp3.Callback;
import okhttp3.OkHttpClient;

/**
 * 笑话的请求类
 * 该请求类提供了四中不同的请求,用于实现不同的数据服务
 */
public class JockRequest {

    /***
     * 根据时间来查询笑话
     */
    public static void queryJockWithTime(JockParamValue jpv, Callback callback) {

        String url = getParameterQueryJockWithTime(jpv);
        Looger.d("请求的url"+url);
        NetRequest.request(url, callback);

    }


    /**
     * 查询最新的笑话
     *
     * @param jpv
     * @param callback
     */
    public static void queryNowJock(JockParamValue jpv, Callback callback) {
        String url = getParameterQueryNowJock(jpv);
        Looger.d("请求的url"+url);
        NetRequest.request(url, callback);
    }


    /**
     * @param jpv
     * @param callback 请求动态趣图通过传入的事件来获得数据
     */
    public static void queryImageByTime(JockParamValue jpv, Callback callback) {
        String url = getParameterQueryImageByTime(jpv);
        Looger.d("请求的url"+url);
        NetRequest.request(url, callback);
    }


    /**
     * 查询最新的动态图片
     *
     * @param jpv
     * @param callback
     */
    public static void queryNewImage(JockParamValue jpv, Callback callback) {
        String url = getParameterQueryNewImage(jpv);
        Looger.d("请求的url"+url);
        NetRequest.request(url, callback);
    }

    private static String getParameterQueryNewImage(JockParamValue jpv) {
        StringBuilder sb = new StringBuilder();
        sb.append(SettingStandard.Joke.NEW_IMAGE)
                .append(SettingStandard.Joke.JokeParam.page)
                .append(jpv.getPage())
                .append(SettingStandard.Joke.JokeParam.rows)
                .append(jpv.getRows());
        return sb.toString();
    }


    private static String getParameterQueryImageByTime(JockParamValue jpv) {

        StringBuilder sb = new StringBuilder();
        sb.append(SettingStandard.Joke.IMAGE_BY_TIME)
                .append(SettingStandard.Joke.JokeParam.page)
                .append(jpv.getPage())
                .append(SettingStandard.Joke.JokeParam.rows)
                .append(jpv.getRows())
                .append(SettingStandard.Joke.JokeParam.sort)
                .append(jpv.getSort())
                .append(SettingStandard.Joke.JokeParam.time)
                .append(jpv.getTime());
        return sb.toString();

    }


    private static String getParameterQueryNowJock(JockParamValue jpv) {
        StringBuilder sb = new StringBuilder();
        sb.append(SettingStandard.Joke.NEW_JOCK)
                .append(SettingStandard.Joke.JokeParam.page)
                .append(jpv.getPage())
                .append(SettingStandard.Joke.JokeParam.rows)
                .append(jpv.getRows());
        return sb.toString();
    }


    /**
     * 通过请求将参数进行拼接
     *
     * @param jpv
     * @return
     */
    private static String getParameterQueryJockWithTime(JockParamValue jpv) {

        StringBuilder sb = new StringBuilder();
        sb.append(SettingStandard.Joke.JOCK_URL_BY_TIME)
                .append(SettingStandard.Joke.JokeParam.page)
                .append(jpv.getPage())
                .append(SettingStandard.Joke.JokeParam.rows)
                .append(jpv.getRows())
                .append(SettingStandard.Joke.JokeParam.sort)
                .append(jpv.getSort())
                .append(SettingStandard.Joke.JokeParam.time)
                .append(jpv.getTime());
        return sb.toString();
    }


}
