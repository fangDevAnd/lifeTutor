package com.xiaofangfang.lifetatuor.model.news;


import com.xiaofangfang.lifetatuor.model.news.parent.CommonNews;
import com.xiaofangfang.lifetatuor.model.news.parent.Result;

/**
 * 娱乐新闻的数据封装类
 */
public class Entertainment extends CommonNews {
    public Entertainment(Result result, int error_code, String reason) {
        super(result, error_code, reason);
    }

    public Entertainment() {
    }

    public Entertainment(CommonNews commonNews) {
        super(commonNews.getResult(), commonNews.getError_code(),
                commonNews.getReason());
    }
}






