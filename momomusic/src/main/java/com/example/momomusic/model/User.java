package com.example.momomusic.model;

import java.security.PrivilegedAction;


/**
 * 用户的mode类
 */
public class User {

    private String accoundId;

    private String name;

    private String password;


    /**
     * 用户的头像
     */
    private String image;


    public User(String accoundId, String name, String password, String image) {
        this.accoundId = accoundId;
        this.name = name;
        this.password = password;
        this.image = image;
    }


    public User(String accoundId, String name, String password) {
        this.accoundId = accoundId;
        this.name = name;
        this.password = password;
    }

    public User() {
    }


    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getAccoundId() {
        return accoundId;
    }

    public void setAccoundId(String accoundId) {
        this.accoundId = accoundId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
