package com.rcs.nchumanity.entity;

import org.litepal.crud.DataSupport;

public class Province extends DataSupport {

    private int id;
    private String name;
    private int provinceId;

    public Province(int id, String name, int provinceId) {
        this.id = id;
        this.name = name;
        this.provinceId = provinceId;
    }


    public int getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(int provinceId) {
        this.provinceId = provinceId;
    }

    public Province() {

    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    @Override
    public String toString() {
        return "Province{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", provinceId=" + provinceId +
                '}';
    }
}
