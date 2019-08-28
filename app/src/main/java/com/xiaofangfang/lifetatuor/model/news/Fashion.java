package com.xiaofangfang.lifetatuor.model.news;

import com.xiaofangfang.lifetatuor.model.news.parent.CommonNews;
import com.xiaofangfang.lifetatuor.model.news.parent.Result;

/**
 * 时尚新闻相关的信息
 */
public class Fashion extends CommonNews {

    public Fashion(Result result, int error_code, String reason) {
        super(result, error_code, reason);
    }

    public Fashion() {
    }

    public Fashion(CommonNews commonNews) {
        super(commonNews.getResult(), commonNews.getError_code(),
                commonNews.getReason());
        // Looger.d("数据结果的大小" + this.getResult().data.size());
    }


}
