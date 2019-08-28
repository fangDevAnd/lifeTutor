package com.rcs.nchumanity.ul.basicMap;

public class LocalPoint implements ILocaPoint {


    public LocalPoint(Double longitude, Double latitude, String locationName, String distance, String position) {
        this.longitude = longitude;
        this.latitude = latitude;
        this.locationName = locationName;
        this.distance = distance;
        this.position = position;
    }

    /**
     * 精度
     */
    public Double longitude = null;

    /**
     * 维度
     */
    public Double latitude = null;

    /**
     * 定位点的位置
     */
    public String locationName = null;

    /**
     * 当前定位的距离
     */
    public String distance = null;

    /**
     * 当前定位点的位置
     * 详细位置
     */
    public String position = null;


    public boolean isHelp;


    @Override
    public Double getLongitude() {
        return longitude;
    }

    @Override
    public Double getLatitude() {
        return latitude;
    }

    @Override
    public String getLocationName() {
        return locationName;
    }

    @Override
    public String getDistance() {
        return distance;
    }

    @Override
    public String getPosition() {
        return position;
    }

    @Override
    public void setDistance(String distance) {
        this.distance = distance;
    }

    @Override
    public boolean isHelp() {
        return isHelp;
    }
}
