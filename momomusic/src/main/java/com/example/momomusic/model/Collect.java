package com.example.momomusic.model;


import org.litepal.crud.DataSupport;

/***
 * 收藏表，用来显示收藏的歌曲
 */
public class Collect extends DataSupport {
    private String dataUrl;

    public Collect(String dataUrl) {
        this.dataUrl = dataUrl;
    }
}
