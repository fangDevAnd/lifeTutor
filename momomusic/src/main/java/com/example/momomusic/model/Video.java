package com.example.momomusic.model;

public class Video {

    private String videoId;

    private String des;

    private String videoUrl;

    private String thubeImage;

    private String timeLong;

    private int lookHuman;

    private User user;

    private int zanCount;

    private int infoCount;


    public Video(String videoId, String des, String videoUrl, String thubeImage, String timeLong, int lookHuman, User user, int zanCount, int infoCount) {
        this.videoId = videoId;
        this.des = des;
        this.videoUrl = videoUrl;
        this.thubeImage = thubeImage;
        this.timeLong = timeLong;
        this.lookHuman = lookHuman;
        this.user = user;
        this.zanCount = zanCount;
        this.infoCount = infoCount;
    }


    public Video() {
    }


    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getThubeImage() {
        return thubeImage;
    }

    public void setThubeImage(String thubeImage) {
        this.thubeImage = thubeImage;
    }

    public String getTimeLong() {
        return timeLong;
    }

    public void setTimeLong(String timeLong) {
        this.timeLong = timeLong;
    }

    public int getLookHuman() {
        return lookHuman;
    }

    public void setLookHuman(int lookHuman) {
        this.lookHuman = lookHuman;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getZanCount() {
        return zanCount;
    }

    public void setZanCount(int zanCount) {
        this.zanCount = zanCount;
    }

    public int getInfoCount() {
        return infoCount;
    }

    public void setInfoCount(int infoCount) {
        this.infoCount = infoCount;
    }
}
