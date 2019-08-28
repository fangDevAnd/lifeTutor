package com.xiaofangfang.rice2_verssion.model;

import org.litepal.crud.DataSupport;

public class County extends DataSupport {

    private int id;
    private int countyId;
    private int cityId;
    private String name;

    public County(int id, int countyId, int cityId, String name) {
        this.id = id;
        this.countyId = countyId;
        this.cityId = cityId;
        this.name = name;
    }

    public County(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCountyId() {
        return countyId;
    }

    public void setCountyId(int countyId) {
        this.countyId = countyId;
    }

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
