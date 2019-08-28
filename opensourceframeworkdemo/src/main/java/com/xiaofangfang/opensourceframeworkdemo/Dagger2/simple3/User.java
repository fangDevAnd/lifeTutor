package com.xiaofangfang.opensourceframeworkdemo.Dagger2.simple3;

import javax.inject.Inject;

public class User {

    private String name;

    @Inject
    public User() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
