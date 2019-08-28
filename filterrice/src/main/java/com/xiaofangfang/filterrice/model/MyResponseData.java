package com.xiaofangfang.filterrice.model;

import java.io.Serializable;

/**
 * 响应的数据
 */
public class MyResponseData implements Serializable {

    private String viewName;
    private int version;
    private String imageAddress;
    private String text;


    public MyResponseData(String viewName, int version, String imageAddress, String text) {
        this.viewName = viewName;
        this.version = version;
        this.imageAddress = imageAddress;
        this.text = text;
    }

    public MyResponseData() {
    }

    public String getViewName() {
        return viewName;
    }

    public void setViewName(String viewName) {
        this.viewName = viewName;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public String getImageAddress() {
        return imageAddress;
    }

    public void setImageAddress(String imageAddress) {
        this.imageAddress = imageAddress;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}