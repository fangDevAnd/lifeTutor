package com.xiaofangfang.opensourceframeworkdemo.Dagger2.simple6;

import javax.inject.Inject;

public class SellMoe {
    @Inject
    public SellMoe() {
    }

    public String sellMoe() {
        return "赶紧卖了个大萌";
    }
}