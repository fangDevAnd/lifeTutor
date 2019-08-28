package com.xiaofangfang.filterrice.ViewDataBean;


import java.util.List;

public class FunctionDataResponse implements DataResponse {

    private String title;
    private List<FunctionModeSingleBean> list;


    public FunctionDataResponse(String title, List<FunctionModeSingleBean> list) {
        this.title = title;
        this.list = list;
    }

    public FunctionDataResponse() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<FunctionModeSingleBean> getList() {
        return list;
    }

    public void setList(List<FunctionModeSingleBean> list) {
        this.list = list;
    }

    public static class FunctionModeSingleBean {

        private int imageAddress;
        private String title;
        private String tableName;
        private String clickStartActivity;

        public FunctionModeSingleBean(int imageAddress, String title, String tableName, String clickStartActivity) {
            this.imageAddress = imageAddress;
            this.title = title;
            this.tableName = tableName;
            this.clickStartActivity = clickStartActivity;
        }


        public FunctionModeSingleBean() {
        }

        public int getImageAddress() {
            return imageAddress;
        }

        public void setImageAddress(int imageAddress) {
            this.imageAddress = imageAddress;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }


        public String getTableName() {
            return tableName;
        }

        public void setTableName(String tableName) {
            this.tableName = tableName;
        }

        public String getClickStartActivity() {
            return clickStartActivity;
        }

        public void setClickStartActivity(String clickStartActivity) {
            this.clickStartActivity = clickStartActivity;
        }


    }

}