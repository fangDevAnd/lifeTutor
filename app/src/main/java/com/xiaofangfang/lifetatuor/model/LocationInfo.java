package com.xiaofangfang.lifetatuor.model;

/**
 * 得到的是位置的相关信息
 */
public class LocationInfo {

    private String latitude;//纬度
    private String altitude;//经度
    private String country;//国家
    private String province;//省份
    private String city;//城市
    private String district;//区
    private String street;//街道
    private String cityCode;//城市代码


    public LocationInfo() {
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getAltitude() {
        return altitude;
    }

    public void setAltitude(String altitude) {
        this.altitude = altitude;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public LocationInfo(String latitude, String altitude, String country, String province, String city, String district, String street, String cityCode) {
        this.latitude = latitude;
        this.altitude = altitude;
        this.country = country;
        this.province = province;
        this.city = city;
        this.district = district;
        this.street = street;
        this.cityCode = cityCode;
    }


    @Override
    public String toString() {
        return "LocationInfo{" +
                "latitude='" + latitude + '\'' +
                ", altitude='" + altitude + '\'' +
                ", country='" + country + '\'' +
                ", province='" + province + '\'' +
                ", city='" + city + '\'' +
                ", district='" + district + '\'' +
                ", street='" + street + '\'' +
                ", cityCode='" + cityCode + '\'' +
                '}';
    }
}
