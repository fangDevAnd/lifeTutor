package com.xiaofangfang.lifetatuor.model.news;

import com.xiaofangfang.lifetatuor.model.news.parent.CommonNews;
import com.xiaofangfang.lifetatuor.model.news.parent.Result;

/**
 * 国际新闻相关的信息
 */
public class International extends CommonNews {

    public International(Result result, int error_code, String reason) {
        super(result, error_code, reason);
    }

    public International() {
    }

    public International(CommonNews commonNews) {
        super(commonNews.getResult(), commonNews.getError_code(),
                commonNews.getReason());
        // Looger.d("数据结果的大小" + this.getResult().data.size());
    }

}
