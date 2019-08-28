package com.xiaofangfang.mylibrary;


import android.os.Environment;
import android.util.Log;
import android.widget.LinearLayout;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.logging.Logger;

public class demo6 {

    public static void main(String[] argc) throws JSONException {

        String json = "{\"name\":\"fang\",\"age\":\"22\",\"address\":\"fangrwtweg\"}";

//        String shlfJson = {
//
//        }
//        json = "{\"age\":\"23\"}";
//        JSONObject jsonObject = new JSONObject(json);

//        Gson gson = new Gson();
//        if (jsonObject.getString("name") != null) {
//            //代表的是User数据
//            Person person = gson.fromJson(json, User.class);
//            System.out.print(person.address);
//        }


        File file = Environment.getExternalStorageDirectory();
        Log.d("test",file.getAbsolutePath());

    }


    abstract class Person {

        private String address;

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }
    }


    class User extends Person {

        private String name;
        private String age;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAge() {
            return age;
        }

        public void setAge(String age) {
            this.age = age;
        }

        public User(String name, String age, String address) {
            this.setAddress(address);
            this.name = name;
            this.age = age;
        }

        public User() {

        }
    }


    class MySelf {
        private String birthday;

        public String getBirthday() {
            return birthday;
        }

        public void setBirthday(String birthday) {
            this.birthday = birthday;
        }
    }


    //-----------------------------

//    @Override
//    public void responseConsumeToolbar(DataResponse dataResponse) {
//        ConsumeToolbarDataResponse consumeToolbarDataResponse = (ConsumeToolbarDataResponse) dataResponse;
//
//        //如果当前视图还没有创建完成，就进行等待
//        while (!createSucessful) {
//
//        }
//
//        if (consumeToolbarDataResponse.getParentViewName().equals(SystemParam.VIEW_GROUP)) {
//
//            consumeToobar = ViewCreateHelp.createConsumeToolbar(viewContainer, consumeToolbarDataResponse);
//        } else if (consumeToolbarDataResponse.getParentViewName().equals(SystemParam.VIEW_ROOT)) {
//            consumeToobar = ViewCreateHelp.createConsumeToolbar((LinearLayout) viewRoot, consumeToolbarDataResponse);
//        }
//    }
//
//    @Override
//    public void responseBannerFlipContainer(DataResponse dataResponse) {
//        BannerFlipperContainerDataResponse bannerFlipperContainerDataResponse = (BannerFlipperContainerDataResponse) dataResponse;
//
//        //如果当前视图还没有创建完成，就进行等待
//        while (!createSucessful) {
//
//        }
//
//        if (bannerFlipperContainerDataResponse.getParentViewName().equals(SystemParam.VIEW_GROUP)) {
//
//            bannerFlipContainer=ViewCreateHelp.createBannerFlipContainer(bannerFlipperContainerDataResponse, viewContainer);
//        } else if (bannerFlipperContainerDataResponse.getParentViewName().equals(SystemParam.VIEW_ROOT)) {
//            bannerFlipContainer=ViewCreateHelp.createBannerFlipContainer(bannerFlipperContainerDataResponse, viewRoot);
//        }
//    }
//
//    @Override
//    public void responseFunctionMode(DataResponse dataResponse) {
//
//        FunctionModeViewDataResponse functionModeViewDataResponse= (FunctionModeViewDataResponse) dataResponse;
//        while(!createSucessful){
//
//        }
//        if(functionModeViewDataResponse.getParentViewName().equals(SystemParam.VIEW_GROUP)){
//            functionMode=ViewCreateHelp.createFunctionMode(viewContainer,functionModeViewDataResponse);
//        }
//
//    }
//
//    @Override
//    public void responseLiangHaoDisplayView(DataResponse dataResponse) {
//        LiangHaoDisplayViewDataResponse liangHaoDisplayViewDataResponse= (LiangHaoDisplayViewDataResponse) dataResponse;
//        while (!createSucessful){
//
//        }
//        if(liangHaoDisplayViewDataResponse.getParentViewName().equals(SystemParam.VIEW_GROUP)){
//            liangHaoDisplayView=ViewCreateHelp.createLiangHaoDisplayView(viewContainer,liangHaoDisplayViewDataResponse);
//        }else if(liangHaoDisplayViewDataResponse.getParentViewName().equals(SystemParam.VIEW_ROOT)){
//            liangHaoDisplayView=ViewCreateHelp.createLiangHaoDisplayView((LinearLayout) viewRoot,liangHaoDisplayViewDataResponse);
//        }
//    }
//
//    @Override
//    public void responseMyActionHorizontalScrollView(DataResponse dataResponse) {
//        ActionHorizontalScrollViewDataResponse actionHorizontalScrollViewDataResponse= (ActionHorizontalScrollViewDataResponse) dataResponse;
//        while (!createSucessful){
//
//        }
//        if(actionHorizontalScrollViewDataResponse.getParentViewName().equals(SystemParam.VIEW_GROUP)){
//
//        }else if(actionHorizontalScrollViewDataResponse.getParentViewName().equals(SystemParam.VIEW_ROOT)){
//            myActionHorizontalScrollView=ViewCreateHelp.createActionHorizontalScrollView(actionHorizontalScrollViewDataResponse, (LinearLayout) viewRoot);
//        }
//    }
//
//    @Override
//    public void responseMyIndexInfoView(DataResponse dataResponse) {
//
//        MyIndexInfoViewDataResponse myIndexInfoViewDataResponse= (MyIndexInfoViewDataResponse) dataResponse;
//        while (!createSucessful){
//
//        }
//        if(myIndexInfoViewDataResponse.getParentViewName().equals(SystemParam.VIEW_GROUP)){
//
//            myIndexInfoView=ViewCreateHelp.createMyIndexInfoView(viewContainer,myIndexInfoViewDataResponse);
//
//        }else if(myIndexInfoViewDataResponse.getParentViewName().equals(SystemParam.VIEW_ROOT)){
//            myIndexInfoView=ViewCreateHelp.createMyIndexInfoView(viewRoot,myIndexInfoViewDataResponse);
//        }
//    }
//
//    @Override
//    public void responseMyMenuFunctionList(DataResponse dataResponse) {
//
//        MyMenuFunctionViewDataResponse myMenuFunctionViewDataResponse= (MyMenuFunctionViewDataResponse) dataResponse;
//        while (!createSucessful){
//
//        }
//        if(myMenuFunctionViewDataResponse.getParentViewName().equals(SystemParam.VIEW_GROUP)){
//
//            myMenuFunctionList=ViewCreateHelp.createMenuList(viewContainer,myMenuFunctionViewDataResponse);
//
//        }else if(myMenuFunctionViewDataResponse.getParentViewName().equals(SystemParam.VIEW_ROOT)){
//            myMenuFunctionList=ViewCreateHelp.createMyIndexInfoView(viewRoot,myMenuFunctionViewDataResponse);
//        }
//
//    }
//
//    @Override
//    public void responsePhoneDisplayView(DataResponse dataResponse) {
//
//    }
//
//    @Override
//    public void responsePhoneSaleView(DataResponse dataResponse) {
//
//    }


}
