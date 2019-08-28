package com.xiaofangfang.filterrice.InsertResponse;

import com.xiaofangfang.filterrice.ViewDataBean.DataResponse;
import com.xiaofangfang.filterrice.ViewDataBean.FunctionDataResponse;
import com.xiaofangfang.filterrice.consumeView.FunctionMode;

public class FunctionModeInsertResponse extends ViewInsertResponse {


    private FunctionDataResponse functionDataResponse;


    @Override
    public void setResponse(DataResponse dataResponse) {
        this.functionDataResponse = functionDataResponse;
    }

    @Override
    public DataResponse getResponse() {
        return functionDataResponse;
    }
}
