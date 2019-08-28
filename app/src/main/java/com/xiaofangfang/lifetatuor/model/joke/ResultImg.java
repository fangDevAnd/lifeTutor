package com.xiaofangfang.lifetatuor.model.joke;

import com.xiaofangfang.lifetatuor.model.joke.parent.Result;

public class ResultImg extends Result {
    private String url;

    public ResultImg(String content, String hashId, String unixtime, String updatetime, String url) {
        super(content, hashId, unixtime, updatetime);
        this.url = url;
    }

    public ResultImg() {

    }


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
