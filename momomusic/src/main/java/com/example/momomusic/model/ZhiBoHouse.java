package com.example.momomusic.model;


/**
 * 直播房间
 */
public class ZhiBoHouse {

    private int houseId;
    private int houseName;
    /**
     * 正在演唱的歌曲
     */
    private int music;

    private int lookHuman;

    private String imgUrl;

    public ZhiBoHouse() {
    }

    public ZhiBoHouse(int houseId, int houseName, int music, int lookHuman) {
        this.houseId = houseId;
        this.houseName = houseName;
        this.music = music;
        this.lookHuman = lookHuman;
    }

    public ZhiBoHouse(int houseId, int houseName, int music, int lookHuman, String imgUrl) {
        this.houseId = houseId;
        this.houseName = houseName;
        this.music = music;
        this.lookHuman = lookHuman;
        this.imgUrl = imgUrl;
    }


    public int getHouseId() {
        return houseId;
    }

    public void setHouseId(int houseId) {
        this.houseId = houseId;
    }

    public int getHouseName() {
        return houseName;
    }

    public void setHouseName(int houseName) {
        this.houseName = houseName;
    }

    public int getMusic() {
        return music;
    }

    public void setMusic(int music) {
        this.music = music;
    }

    public int getLookHuman() {
        return lookHuman;
    }

    public void setLookHuman(int lookHuman) {
        this.lookHuman = lookHuman;
    }
}
