package com.rcs.nchumanity.entity;


/**
 * 当前的应用程序使用的系统参数设置的相关参数
 */
public class SystemSet {

    /**
     * 默认的AED搜索半径
     */
    public static final int DEF_AED_SEARCH_RADIUS = 1;

    /**
     * aed的搜索半径字段
     */
    public static final String AED_SEARCH_RADIUS = "aedSearchRadius";


    /**
     * 首页的参数设置
     */
    public static class PageMainParam {

        /**
         * 获得banner图片的默认大小
         */
        public static final int BANNER_IMG_SIZE = 3;

        /**
         * 获得新闻的默认大小
         */
        public static final int NEWS_SIZE = 3;
    }


}
