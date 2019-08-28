package com.rcs.nchumanity.tool;

import com.rcs.nchumanity.entity.model.EmergencyInfo;
import com.rcs.nchumanity.entity.model.SpecificInfo;
import com.rcs.nchumanity.ul.basicMap.ILocaPoint;
import com.rcs.nchumanity.ul.basicMap.LocalPoint;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 用于解析json数据
 */
public class JsonDataParse {


    public static ArrayList<ILocaPoint> getAEDLocations(String jsonData) throws JSONException {

        JSONObject brJ = new JSONObject(jsonData);

        JSONObject listObj = brJ.getJSONObject("data");

        JSONArray aedList = listObj.getJSONArray("list");

        ArrayList<ILocaPoint> locaPoints = new ArrayList<>();

        for (int i = 0; i < aedList.length(); i++) {

            JSONObject aedDetail = aedList.getJSONObject(i);

            double longitude = aedDetail.getDouble("longitude");
            double latitude = aedDetail.getDouble("latitude");
            String detail = aedDetail.getString("detail");


            int startIndex = detail.indexOf("（") >= detail.length() ? detail.indexOf("(") : detail.indexOf("（");
            int endIndex = detail.indexOf("）") >= detail.length() ? detail.indexOf(")") : detail.indexOf("）");

            String position = detail.substring(startIndex, endIndex);

            String location = detail.replace(position, "");
            locaPoints.add(new LocalPoint(longitude, latitude, location, "", position));
        }

        return locaPoints;
    }


    /**
     * 解析出当前的求救的数据
     *
     * @param backDatum
     * @return
     */
    public static ArrayList<EmergencyInfo> parseEmergencyData(String backDatum) throws JSONException {
        JSONObject brJ = new JSONObject(backDatum);
        JSONObject data = brJ.getJSONObject("data");
        JSONArray totalList = data.getJSONArray("list");

        ArrayList<EmergencyInfo> emergencyInfos = new ArrayList<>();

        for (int i = 0; i < totalList.length(); i++) {
            EmergencyInfo emergencyInfo = new EmergencyInfo();
            JSONObject emerO = totalList.getJSONObject(i);
            int emerId = emerO.getInt("emerId");
            String createTime = emerO.getString("createTime");
            String title = emerO.getString("title");
            String content = emerO.getString("content");
            double longitude = emerO.getDouble("longitude");
            double latitude = emerO.getDouble("latitude");
            int readCount = emerO.getInt("readCount");
            String mobilePhone = emerO.getString("mobilePhone");
            int userId = emerO.getInt("userId");
            emergencyInfo.setContent(content);
            emergencyInfo.setCreateTime(DateProce.parseDate(createTime));
            emergencyInfo.setEmerId(emerId);
            emergencyInfo.setTitle(title);
            emergencyInfo.setLatitude(latitude);
            emergencyInfo.setLongitude(longitude);
            emergencyInfo.setUserId(userId);
            emergencyInfo.setReadCount(readCount);
            emergencyInfo.setMobilePhone(mobilePhone);
            emergencyInfos.add(emergencyInfo);
        }
        return emergencyInfos;
    }

    /**
     * "id": 36,
     * "specificNo": 1,
     * "title": "新闻1",
     * "createTime": null,
     * "icon": null,
     * "imgUrl": "https://ncrd2019.oss-cn-shenzhen.aliyuncs.com/specificInfo/pic/xinwen1.jpg?Expires=1565599747&OSSAccessKeyId=TMP.hVJ9P4c7YcK26gN4zuafY8AYm8JQP9nrV4taHn9JTUBXf2RiZq4dmwEgKNwCfbwBqmbdYJtbofpKEasyZ5zzAYc27TtfAKJ2kboQcedCUapWvjjQbTFMHGteGauZ9C.tmp&Signature=oiLDGHoP9hOUmtTvJ2KnkUjJ%2FVU%3D",
     * "videoId": null,
     * "videoUrl": null,
     * "editor": null,
     * "checked": null,
     * "typeId": 2,
     * "isDelete": false,
     * "remark": null,
     * "content": null
     *
     * @param news
     * @return
     * @throws JSONException
     */
    public static List<SpecificInfo> parseNewItem(JSONArray news) throws JSONException {

        List<SpecificInfo> specificInfos = new ArrayList<>();

        for (int i = 0; i < news.length(); i++) {
            JSONObject new1 = news.getJSONObject(i);
            String title = new1.getString("title");
            String createTime = new1.getString("createTime");
            String icon = new1.getString("icon");
            String videoId = new1.getString("videoId");
            String videoUrl = new1.getString("videoUrl");
            String editor = new1.getString("editor");
            String content = new1.getString("content");
            String imageUrl = new1.getString("imgUrl");


            SpecificInfo specificInfo = new SpecificInfo();
            specificInfo.setTitle(title);
            specificInfo.setIcon(icon);
            specificInfo.setVideoId(videoId);
            specificInfo.setVideoUrl(videoUrl);
            specificInfo.setEditor(editor);
            specificInfo.setContent(content);
            specificInfo.setImgUrl(imageUrl);

            specificInfos.add(specificInfo);

        }
        return specificInfos;
    }


    /**
     * 解析图标
     * "typeId": 2,
     * "title": "国内国际要闻",
     * "icon": null,
     * "parent": "首页数据",
     * "isDelete": false,
     * "remark": null
     * <p>
     * 解析 拿到 捐款捐献 和 培训相关
     *
     * @param iconList
     */
    public static List<SpecificInfo> parseIconListData(JSONArray iconList) throws JSONException {

        List<SpecificInfo> specificInfos = new ArrayList<>();

        for (int i = 0; i < iconList.length(); i++) {
            JSONObject new1 = iconList.getJSONObject(i);
            String parentView = new1.getString("parent");
            if (parentView.equals("捐款捐献") || parentView.equals("培训相关")) {
                String title = new1.getString("title");
                String icon = new1.getString("icon");
                SpecificInfo specificInfo = new SpecificInfo();
                specificInfo.setTitle(title);
                specificInfo.setIcon(icon);
                specificInfos.add(specificInfo);
            }
        }
        return specificInfos;
    }


}
