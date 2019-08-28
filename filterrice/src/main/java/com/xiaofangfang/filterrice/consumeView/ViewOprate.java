package com.xiaofangfang.filterrice.consumeView;


import com.xiaofangfang.filterrice.ViewDataBean.DataResponse;

public interface ViewOprate {


    void setDataResponse(DataResponse dataResponse);

    void updateDataResponse(DataResponse dataResponse, int leavel);

    void defaultDataResponse();

}
