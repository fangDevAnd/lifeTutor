package com.xiaofangfang.lifetatuor.model.weather;

import java.util.List;
/*
HeWeather5 	和风标识
status 	状态码
daily_forecast 	天气预报
vis 	能见度
wind 	风力情况
deg 	风向（360度）
sc 	风力等级
spd 	风速
dir 	风向
pres 	气压
astro 	天文指数
mr 	月升时间
sr 	日出时间
ss 	日落时间
ms 	月落时间
cond 	天气状况
code_n 	夜间天气状况代码
code_d 	白天天气状况代码
txt_n 	夜间天气状况描述
txt_d 	白天天气状况描述
tmp 	温度
max 	最高温度
min 	最低温度
pop 	降水概率
date 	日期
pcpn 	降水量
hum 	相对湿度
aqi 	AQI
city 	城市名
no2 	NO2
pm10 	PM10
o3 	O3
qlty 	空气质量
pm25 	PM2.5
so2 	SO2
co 	CO
hourly_forecast 	每小时预报
code 	天气状况代码
txt 	数据详情
alarms 	灾害预警
type 	预警种类
stat 	预警状态
title 	预警标题
level 	预警级别
suggestion 	生活指数
cw 	洗车指数
brf 	简介
drsg 	穿衣指数
comf 	舒适度指数
uv 	紫外线指数
flu 	感冒指数
trav 	旅游指数
sport 	运动指数
basic 	基本信息
lat 	纬度
cnty 	国家
update 	更新时间
utc 	UTC时间
loc 	当地时间
id 	城市ID
lon 	经度
now 	实况天气
fl 	体感温度




 */


public class Root
{
    private List<HeWeather5> HeWeather5;

    public void setHeWeather5(List<HeWeather5> HeWeather5){
        this.HeWeather5 = HeWeather5;
    }
    public List<HeWeather5> getHeWeather5(){
        return this.HeWeather5;
    }


    @Override
    public String toString() {
        return "Root{" +
                "HeWeather5=" + HeWeather5 +
                '}';
    }
}
