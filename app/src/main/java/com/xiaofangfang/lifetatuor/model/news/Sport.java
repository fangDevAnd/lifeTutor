package com.xiaofangfang.lifetatuor.model.news;

import com.xiaofangfang.lifetatuor.model.news.parent.CommonNews;
import com.xiaofangfang.lifetatuor.model.news.parent.Result;

/**
 * 运动相关的新闻信息
 */
public class Sport extends CommonNews {

    public Sport(Result result, int error_code, String reason) {
        super(result, error_code, reason);
    }

    public Sport() {
    }

    public Sport(CommonNews commonNews) {
        super(commonNews.getResult(), commonNews.getError_code(),
                commonNews.getReason());
        // Looger.d("数据结果的大小" + this.getResult().data.size());
    }
}

