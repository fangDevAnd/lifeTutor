package com.xiaofangfang.lifetatuor.server.interfaces;

import com.xiaofangfang.lifetatuor.model.LocationInfo;

/**
 * 获得到的位置信息的回调
 */
public interface LocationCallback {

    void locationInfo(LocationInfo info);
}
