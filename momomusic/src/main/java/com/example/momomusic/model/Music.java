package com.example.momomusic.model;

import android.os.Parcel;
import android.os.Parcelable;

import org.litepal.crud.DataSupport;

public class Music extends DataSupport implements Parcelable {





    /**
     * MediaStore.Audio.Media.DURATION,
     * MediaStore.Audio.Media.TRACK,
     * MediaStore.Audio.Media.ARTIST_ID,
     * MediaStore.Audio.Media.ALBUM_ID,
     * MediaStore.Audio.Media.ALBUM_KEY,
     * MediaStore.Audio.Media.BOOKMARK,
     * MediaStore.Audio.Media.DATE_ADDED,
     * MediaStore.Audio.Media.COMPOSER,
     * <p>
     * long duration = cursor.getLong(5);
     * int track = cursor.getInt(7);
     * int artist_id = cursor.getInt(8);
     * int albumId = cursor.getInt(9);
     * String albumKey = cursor.getString(10);
     * int bookMark = cursor.getInt(11);
     * int dateAdded = cursor.getInt(12);
     * String composer = cursor.getString(13);
     * String title = cursor.getString(14);
     */



    private int duration;

    private int track;

    private int artist_id;

    private int albumId;

    private String albumKey;

    private int bookMark;

    private int dateAdded;

    private String composer;

    private String title;

    private String dataUrl;


    private String displayName;

    private String albumName;

    private String artist;

    private float size;

    private boolean isCollect;

    /**
     * 这个域的作用是用来进行专辑对应的数量的
     */
    private int count;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }


    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getTrack() {
        return track;
    }

    public void setTrack(int track) {
        this.track = track;
    }

    public int getArtist_id() {
        return artist_id;
    }

    public void setArtist_id(int artist_id) {
        this.artist_id = artist_id;
    }

    public int getAlbumId() {
        return albumId;
    }

    public void setAlbumId(int albumId) {
        this.albumId = albumId;
    }

    public String getAlbumKey() {
        return albumKey;
    }

    public void setAlbumKey(String albumKey) {
        this.albumKey = albumKey;
    }

    public int getBookMark() {
        return bookMark;
    }

    public void setBookMark(int bookMark) {
        this.bookMark = bookMark;
    }

    public int getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(int dateAdded) {
        this.dateAdded = dateAdded;
    }

    public String getComposer() {
        return composer;
    }

    public void setComposer(String composer) {
        this.composer = composer;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getAlbumName() {
        return albumName;
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public float getSize() {
        return size;
    }

    public void setSize(float size) {
        this.size = size;
    }


    public boolean isCollect() {
        return isCollect;
    }

    public void setCollect(boolean collect) {
        isCollect = collect;
    }

    @Override
    public String toString() {
        return "Music{" +
                "duration=" + duration +
                ", track=" + track +
                ", artist_id=" + artist_id +
                ", albumId=" + albumId +
                ", albumKey='" + albumKey + '\'' +
                ", bookMark=" + bookMark +
                ", dateAdded=" + dateAdded +
                ", composer='" + composer + '\'' +
                ", title='" + title + '\'' +
                ", dataUrl='" + dataUrl + '\'' +
                ", displayName='" + displayName + '\'' +
                ", albumName='" + albumName + '\'' +
                ", artist='" + artist + '\'' +
                ", size=" + size +
                ", pinyin='" + pinyin + '\'' +
                '}';
    }

    public Music(String title, String dataUrl, String displayName, String albumName, String artist, float size) {
        this.title = title;
        this.dataUrl = dataUrl;
        this.displayName = displayName;
        this.albumName = albumName;
        this.artist = artist;
        this.size = size;
    }

    public Music(int duration, int track, int artist_id, int albumId, String albumKey,
                 int bookMark, int dateAdded, String composer, String title,
                 String dataUrl, String displayName, String albumName, String artist, float size) {
        this.duration = duration;
        this.track = track;
        this.artist_id = artist_id;
        this.albumId = albumId;
        this.albumKey = albumKey;
        this.bookMark = bookMark;
        this.dateAdded = dateAdded;
        this.composer = composer;
        this.title = title;
        this.dataUrl = dataUrl;
        this.displayName = displayName;
        this.albumName = albumName;
        this.artist = artist;
        this.size = size;
    }

    public Music(String title, String dataUrl) {
        this.title = title;
        this.dataUrl = dataUrl;
    }


    /**
     * 参数3 是一个标志参数,没有 实际意义
     *
     * @param albumName
     * @param artist
     * @param a
     */
    public Music(String albumName, String artist, int a) {
        this.albumName = albumName;
        this.artist = artist;
    }


    private String pinyin;

    public Music(String pinyin) {
        this.pinyin = pinyin;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }

    public String getPinyin() {
        return pinyin;
    }

    public Music() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDataUrl() {
        return dataUrl;
    }

    public void setDataUrl(String dataUrl) {
        this.dataUrl = dataUrl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.duration);
        dest.writeInt(this.track);
        dest.writeInt(this.artist_id);
        dest.writeInt(this.albumId);
        dest.writeString(this.albumKey);
        dest.writeInt(this.bookMark);
        dest.writeInt(this.dateAdded);
        dest.writeString(this.composer);
        dest.writeString(this.title);
        dest.writeString(this.dataUrl);
        dest.writeString(this.displayName);
        dest.writeString(this.albumName);
        dest.writeString(this.artist);
        dest.writeFloat(this.size);
        dest.writeByte(this.isCollect ? (byte) 1 : (byte) 0);
        dest.writeInt(this.count);
        dest.writeString(this.pinyin);
    }

    protected Music(Parcel in) {
        this.duration = in.readInt();
        this.track = in.readInt();
        this.artist_id = in.readInt();
        this.albumId = in.readInt();
        this.albumKey = in.readString();
        this.bookMark = in.readInt();
        this.dateAdded = in.readInt();
        this.composer = in.readString();
        this.title = in.readString();
        this.dataUrl = in.readString();
        this.displayName = in.readString();
        this.albumName = in.readString();
        this.artist = in.readString();
        this.size = in.readFloat();
        this.isCollect = in.readByte() != 0;
        this.count = in.readInt();
        this.pinyin = in.readString();
    }

    public static final Parcelable.Creator<Music> CREATOR = new Parcelable.Creator<Music>() {
        @Override
        public Music createFromParcel(Parcel source) {
            return new Music(source);
        }

        @Override
        public Music[] newArray(int size) {
            return new Music[size];
        }
    };
}
