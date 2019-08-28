package com.xiaofangfang.opensourceframeworkdemo.Dagger2.simple2;

import android.widget.Toast;

import javax.inject.Inject;

public class SellMoe {

    @Inject
    public SellMoe() {

    }

    public String sellMoe() {
        return "赶紧起来卖个萌";
    }

    public void toastInfo(String message) {

    }


}
