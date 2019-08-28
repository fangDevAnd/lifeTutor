package com.xiaofangfang.butterknitedemo.dataBinding.model.data;

public class User {

    private String user;

    private String password;


    public User(String password, String user) {
        this.user = user;
        this.password = password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }
}
