package com.xiaofangfang.filterrice.UpdateResponse;

import com.xiaofangfang.filterrice.ViewDataBean.BannerDataRespnse;
import com.xiaofangfang.filterrice.consumeView.BannerFlipContainer;
import com.xiaofangfang.filterrice.ViewDataBean.DataResponse;

public class BannerFlipperUpdateResponse extends ViewUpdateResponse {


    private BannerDataRespnse bannerDataRespnse;

    public BannerFlipperUpdateResponse(BannerDataRespnse bannerDataRespnse) {
        this.bannerDataRespnse = bannerDataRespnse;
    }


    @Override
    public void setResponse(DataResponse dataResponse) {
        this.bannerDataRespnse = (BannerDataRespnse) dataResponse;
    }

    @Override
    public DataResponse getResponse() {
        return bannerDataRespnse;
    }
}
