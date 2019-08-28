package com.xiaofangfang.filterrice.tool;

import android.content.Context;

import com.xiaofangfang.filterrice.consumeView.FunctionMode;
import com.xiaofangfang.filterrice.consumeView.ViewOprate;

public class ConsumeViewMapping {


    public static ViewOprate getView(String consumeView, Context context) {

        ViewOprate viewOprate=null;

        if (consumeView.equals(ConsumeViewName.FUNCTION_MODE.consumeViewName)) {
            viewOprate = new FunctionMode(context);
        }

        return viewOprate;
    }


}
