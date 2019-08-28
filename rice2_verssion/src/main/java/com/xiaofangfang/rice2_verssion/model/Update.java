package com.xiaofangfang.rice2_verssion.model;


/**
 * 软件更新检测返回的接口类型
 */
public class Update {

    private long version;

    private String url;

    public Update(long version, String url) {
        this.version = version;
        this.url = url;

    }


    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
