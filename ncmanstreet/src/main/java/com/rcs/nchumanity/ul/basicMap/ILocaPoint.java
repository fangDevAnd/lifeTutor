package com.rcs.nchumanity.ul.basicMap;

import java.io.Serializable;

/**
 * 不同的界面需要对起进行扩展
 * 位置点的接口
 */
public interface ILocaPoint extends Serializable {


    /**
     * 获得精度度的方法
     *
     * @return
     */
    public Double getLongitude();

    /**
     * 获得维度的方法
     *
     * @return
     */
    public Double getLatitude();


    public String getLocationName();

    /**
     * @return
     */
    public String getDistance();

    /**
     * 当前定位的位置
     *
     * @return
     */
    public String getPosition();


    /**
     * 设置距离
     */
    public void setDistance(String distance);


    public boolean isHelp();

}
