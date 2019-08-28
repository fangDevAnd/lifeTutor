package com.xiaofangfang.rice2_verssion.model;

public class Music {


    private String albumImgUrl;

    /**
     * 歌词文件路径
     */
    private String ircPath;

    /**
     * 播放文件的路径
     */
    private String dataUrl;

    private String name;

    private String singer;


    public Music(String albumImgUrl, String ircPath, String dataUrl, String name, String singer) {
        this.albumImgUrl = albumImgUrl;
        this.ircPath = ircPath;
        this.dataUrl = dataUrl;
        this.name = name;
        this.singer = singer;
    }

    public Music() {
    }


    public String getAlbumImgUrl() {
        return albumImgUrl;
    }

    public void setAlbumImgUrl(String albumImgUrl) {
        this.albumImgUrl = albumImgUrl;
    }

    public String getIrcPath() {
        return ircPath;
    }

    public void setIrcPath(String ircPath) {
        this.ircPath = ircPath;
    }

    public String getDataUrl() {
        return dataUrl;
    }

    public void setDataUrl(String dataUrl) {
        this.dataUrl = dataUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSinger() {
        return singer;
    }

    public void setSinger(String singer) {
        this.singer = singer;
    }
}
