package com.xiaofangfang.lifetatuor.model.joke.parent;


public class Result {

    private String content;
    private String hashId;
    private String unixtime;
    private String updatetime;

    public Result(String content, String hashId,
                  String unixtime, String updatetime) {
        this.content = content;
        this.hashId = hashId;
        this.unixtime = unixtime;
        this.updatetime = updatetime;
    }

    public Result() {
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getHashId() {
        return hashId;
    }

    public void setHashId(String hashId) {
        this.hashId = hashId;
    }

    public String getUnixtime() {
        return unixtime;
    }

    public void setUnixtime(String unixtime) {
        this.unixtime = unixtime;
    }

    public String getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(String updatetime) {
        this.updatetime = updatetime;
    }
}