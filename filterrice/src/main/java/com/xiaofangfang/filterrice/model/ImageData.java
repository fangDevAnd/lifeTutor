package com.xiaofangfang.filterrice.model;

/**
 * 这个类的功能是实现对图片数据的封装
 */
public class ImageData {

    private int url;
    private int height;
    private int width;

    public ImageData(int url, int height, int width) {
        this.url = url;
        this.height = height;
        this.width = width;
    }

    public int getUrl() {
        return url;
    }

    public void setUrl(int url) {
        this.url = url;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }
}
