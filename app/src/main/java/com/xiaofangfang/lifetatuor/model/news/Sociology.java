package com.xiaofangfang.lifetatuor.model.news;

import com.xiaofangfang.lifetatuor.model.news.parent.CommonNews;
import com.xiaofangfang.lifetatuor.model.news.parent.Result;

/**
 * 社会相关的新闻信息封装
 */
public class Sociology extends CommonNews {

    public Sociology(Result result, int error_code, String reason) {
        super(result, error_code, reason);
    }

    public Sociology() {
    }

    public Sociology(CommonNews commonNews) {
        super(commonNews.getResult(), commonNews.getError_code(),
                commonNews.getReason());
        // Looger.d("数据结果的大小" + this.getResult().data.size());
    }

}
