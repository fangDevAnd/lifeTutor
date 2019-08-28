package com.example.componentasystemtest.musicPlay.simple2;

import org.litepal.crud.DataSupport;

public class Music extends DataSupport {


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
}
