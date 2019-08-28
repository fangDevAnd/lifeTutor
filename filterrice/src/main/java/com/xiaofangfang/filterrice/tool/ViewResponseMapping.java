package com.xiaofangfang.filterrice.tool;

import com.xiaofangfang.filterrice.InsertResponse.FunctionModeInsertResponse;
import com.xiaofangfang.filterrice.InsertResponse.ViewInsertResponse;
import com.xiaofangfang.filterrice.UpdateResponse.BannerFlipperUpdateResponse;

public class ViewResponseMapping {


    public static Class getUpdateResponseClass(String viewName) {

        if (viewName.equals(ConsumeViewName.BANNER_FLIP_CONTAINER.consumeViewName)) {

            return BannerFlipperUpdateResponse.class;
        }
        return null;
    }


    public static Class getInsertResponseClass(String viewName) {

        if (viewName.equals(ConsumeViewName.FUNCTION_MODE.consumeViewName)) {

            return FunctionModeInsertResponse.class;
        }

        return null;
    }


}
