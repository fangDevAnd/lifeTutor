package com.xiaofangfang.rice2_verssion.model;

import java.io.Serializable;

public class AddressMode implements Serializable {

    private long id; //主键,这里不使用

    private String name;

    private String tel;

    private String address;

    //是不是默认的地址
    private boolean isDefault;

    public AddressMode(String name, String tel, String address, boolean isDefault) {
        this.name = name;
        this.tel = tel;
        this.address = address;
        this.isDefault = isDefault;
    }


    public AddressMode(long id, String name, String tel, String address, boolean isDefault) {
        this.id = id;
        this.name = name;
        this.tel = tel;
        this.address = address;
        this.isDefault = isDefault;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public AddressMode() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean isDefault() {
        return isDefault;
    }

    public void setDefault(boolean aDefault) {
        isDefault = aDefault;
    }


    @Override
    public String toString() {
        return "AddressMode{" +
                "name='" + name + '\'' +
                ", tel='" + tel + '\'' +
                ", address='" + address + '\'' +
                ", isDefault=" + isDefault +
                '}';
    }
}
