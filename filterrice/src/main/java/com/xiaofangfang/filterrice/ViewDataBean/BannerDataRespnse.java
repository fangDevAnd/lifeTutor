package com.xiaofangfang.filterrice.ViewDataBean;

import java.util.List;

public  class BannerDataRespnse implements DataResponse {

    private List<SingleBannerDataResponse> singleBannerDataResponses;

    public BannerDataRespnse(List<SingleBannerDataResponse> singleBannerDataResponses) {
        this.singleBannerDataResponses = singleBannerDataResponses;
    }


    public BannerDataRespnse() {

    }

    public List<SingleBannerDataResponse> getSingleBannerDataResponses() {
        return singleBannerDataResponses;
    }

    public void setSingleBannerDataResponses(List<SingleBannerDataResponse> singleBannerDataResponses) {
        this.singleBannerDataResponses = singleBannerDataResponses;
    }


    public static class SingleBannerDataResponse {

        private int defaultImageAddress;
        private String updateImageAddress;
        private String title;
        private String clickStartActivityName;
        private String tableName;


        public SingleBannerDataResponse(int defaultImageAddress, String updateImageAddress, String title, String clickStartActivityName, String tableName) {
            this.defaultImageAddress = defaultImageAddress;
            this.updateImageAddress = updateImageAddress;
            this.title = title;
            this.clickStartActivityName = clickStartActivityName;
            this.tableName = tableName;
        }


        public SingleBannerDataResponse() {

        }

        public int getDefaultImageAddress() {
            return defaultImageAddress;
        }

        public void setDefaultImageAddress(int defaultImageAddress) {
            this.defaultImageAddress = defaultImageAddress;
        }

        public String getUpdateImageAddress() {
            return updateImageAddress;
        }

        public void setUpdateImageAddress(String updateImageAddress) {
            this.updateImageAddress = updateImageAddress;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getClickStartActivityName() {
            return clickStartActivityName;
        }

        public void setClickStartActivityName(String clickStartActivityName) {
            this.clickStartActivityName = clickStartActivityName;
        }

        public String getTableName() {
            return tableName;
        }

        public void setTableName(String tableName) {
            this.tableName = tableName;
        }
    }


}
