package com.example.momomusic.model;

import java.io.Serializable;

public class Comment extends User implements Serializable {


    private String commentContent;

    private String date;


    public Comment(String accoundId, String name, String password, String image, String commentContent, String date) {
        super(accoundId, name, password, image);
        this.commentContent = commentContent;
        this.date = date;
    }

    public Comment(String accoundId, String name, String password, String commentContent, String date) {
        super(accoundId, name, password);
        this.commentContent = commentContent;
        this.date = date;
    }

    public Comment(String commentContent, String date) {
        this.commentContent = commentContent;
        this.date = date;
    }

    public Comment() {
    }

    public String getCommentContent() {
        return commentContent;
    }

    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }


}
