package com.xiaofangfang.opensourceframeworkdemo.pullServer;


public class MessageEvent {

    private String sender;
    private String content;
    private String date;


    public MessageEvent(String sender, String content, String date) {
        this.sender = sender;
        this.content = content;
        this.date = date;
    }


    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
