package com.xiaofangfang.rice2_verssion.model;

import org.litepal.crud.DataSupport;

public class City extends DataSupport {


    private int id;
    private int provinceId;
    private String name;
    private int cityId;

    public City(int id, int provinceId, String name, int cityId) {
        this.id = id;
        this.provinceId = provinceId;
        this.name = name;
        this.cityId = cityId;
    }


    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public City() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(int provinceId) {
        this.provinceId = provinceId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    @Override
    public String toString() {
        return "City{" +
                "id=" + id +
                ", provinceId=" + provinceId +
                ", name='" + name + '\'' +
                ", cityId=" + cityId +
                '}';
    }
}
