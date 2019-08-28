package com.xiaofangfang.lifetatuor.set;


import android.Manifest;

import com.xiaofangfang.lifetatuor.tools.DateUse;

import java.util.Date;

/**
 * 系统设置的常量以及默认设置以及设置的常量名称
 */
public class SettingStandard {

    public static final String dbName = "life";

    /**
     * 需要申请的危险权限的列表
     */
    public static final String[] REQUEST_PERMISSION = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.ACCESS_FINE_LOCATION

    };


    public static class Weather {

        static String key = "5eeab85bf9e44b4da9a1d35fc3ce0d9c";

        /**
         * 请求的url,注意,请求的参数已经准备好了,但是需要传递城市名称  例如查询武汉的天气
         * WEATHER_URL+"武汉"    WEATHER_URL+"合肥"
         */
        public static final String WEATHER_URL = "https://free-api.heweather.com/v5/weather?key=" + key + "&city=";

        public static  final String UPDATE_TIME="updateTime";
        //更新时间的字段
        public static final String time="time";


        /**
         * 天气配置的xml的名称
         */
        public static final String WEATHER = "weather";




        /**
         * 天气的位置信息集合
         */
        public static String LOCATION_SET = "locationSet";


    }


    /**
     * 新闻相关的设置
     */
    public static class News {

        /**
         * 新闻的url地址,同天气的使用一样,只要直接+就能使用
         * NEWS_URL+Top.type
         */
        public static final String NEWS_URL = " http://api.avatardata.cn/TouTiao/Query?key=1b37e12b12324ed0879b0b255770a291&type=";

        /**
         * 这个枚举类型对应的是新闻的类型
         */
        public enum NewsType {
            TOP("top"),
            SHEHUI("shehui"),
            GUONEI("guonei"),
            GUOJI("guoji"),
            YULE("yule"),
            TIYU("tiyu"),
            JUNSHI("junshi"),
            KEJI("keji"),
            CAIJING("caijing"),
            SHISHANG("shishang");

            public String type;

            private NewsType(String type) {
                this.type = type;
            }

        }

    }


    public static class Joke {


        /**
         * 请求的关键字key
         */
        public static final String key = "1f23b23279f84b93a30706f637291361";

        /**
         * 请求的url,这个url的拼装显得有些复杂,下面是一个例子
         * 获得的是按更新时间查询的笑话
         * 参数是 page   rows   sort  time
         */
        public static final String JOCK_URL_BY_TIME =
                " http://api.avatardata.cn/Joke/QueryJokeByTime?key=" + key;
        /**
         * 请求的获得最新的笑话的url
         * 参数是page  rows
         */
        public static final String NEW_JOCK =
                "http://api.avatardata.cn/Joke/NewstJoke?key=" + key;

        /**
         * 按更新事件查询趣图
         * 参数有 page rows sort time
         */
        public static final String IMAGE_BY_TIME =
                "http://api.avatardata.cn/Joke/QueryImgByTime?key=" + key;
        /**
         * 获得最新的动态趣图
         * 参数有  page  rows
         */
        public static final String NEW_IMAGE =
                " http://api.avatardata.cn/Joke/NewstImg?key=" + key;

        /**
         * 配置笑话的请求参数
         */
        public static class JokeParam {
            /**
             * 当前的页数,默认为1,
             */
            public static String page = "&page=";
            /***
             *  	返回记录条数，默认rows=20,最大50
             */
            public static String rows = "&rows=";
            /**
             * 类型，desc:指定时间之前发布的，asc:指定时间之后发布的
             */
            public static String sort = "&sort=";
            /**
             * 时间戳（10位），如：1418816972
             * 对于我们想要获得最新的数据,可以构建时间戳,然后sort设置desc
             */
            public static String time = "&time=";


            public static String rowDefault = "20";
            public static String rowDefault2 = "50";
            public static String pageDefault = "1";
            public static String sortDefault = "desc";
            public static String sortDefault2 = "asc";
            public static String timeDefault = "1418816972";
            //动态的时间,获得的是事件挫的动态变化值
            public static String timeDaymic =
                    (DateUse.getSecondTimestamp(new Date(System.currentTimeMillis())) - DateUse.getDaySecond()) + "";

        }


        /**
         * 定义的类型
         */
        public enum Type {
            IMG_BY_TIME("ImgByTime"),
            JOKE_BY_TIME("JokeByTime"),
            NEWST_IMG("NewstImg"),
            NEWST_JOKE("NewstJoke");

            Type(String type) {
                this.type = type;
            }

            private String type;
        }


    }


}
