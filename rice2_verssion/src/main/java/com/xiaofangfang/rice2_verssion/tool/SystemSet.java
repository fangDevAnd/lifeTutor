package com.xiaofangfang.rice2_verssion.tool;


/**
 * 提供了系统设置的常量
 */
public class SystemSet {

    /**
     * 配置的文件名
     */
    public final static String settingFileName = "Setting";

    /**
     * 系统的登录状态
     */
    public static final String LOGIN_STATUS = "loginStatus";

    /**
     * 用户的id.,代表的是用户的手机号码 .存放的是是一个String
     * 的值
     */
    public static final String USER_ID = "userid";

    /**
     * 默认的用户的id
     */
    public static final String DEFAULT_USER_ID = "未登录";

    /**
     * 默认的登录状态是false
     */
    public static boolean DEF_LOGIN_STATUS = false;


    /**
     * 系统默认配置地区的值,这个地区的设置是市级别的数据
     * 1代表的是北京的数据
     */
    public static final int DEF_LOC_PARAM_VAL = 147;

    /**
     * 默认是南昌南昌，的省份id是19，城市的id也是147
     */
    public static int DEF_PRO_PARAM_VAL = 19;

    public static String DEFAULT_PROVINCE_ID_NAME = "provinceId";

    /**
     * 系统的地区默认的配置名称
     */
    public static String DEFALUT_LOCATION_CITY_ID_NAME = "locationId";


    /**
     * 系统默认的的城市名称的配置参数
     */
    public static String DEFALUT_LOCATION_CITY_NAME = "locationName";


    /**
     * 系统默认的的城市名称的配置值
     */
    public static String DEF_LOC_NAME_VAL = "南昌";


}