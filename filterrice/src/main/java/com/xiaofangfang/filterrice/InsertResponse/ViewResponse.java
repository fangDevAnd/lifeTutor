package com.xiaofangfang.filterrice.InsertResponse;

import com.xiaofangfang.filterrice.ViewDataBean.DataResponse;

public abstract class ViewResponse {


    protected int index;

    protected int oprateCode;


    public abstract void setResponse(DataResponse dataResponse);

    public abstract  DataResponse getResponse();



}
