package com.xiaofangfang.lifetatuor.model.news;

import com.xiaofangfang.lifetatuor.model.news.parent.CommonNews;
import com.xiaofangfang.lifetatuor.model.news.parent.Result;

/**
 * 封装了经济相关的信息
 */
public class Economics extends CommonNews {

    public Economics(Result result, int error_code, String reason) {
        super(result, error_code, reason);
    }

    public Economics() {
    }


    public Economics(CommonNews commonNews) {
        super(commonNews.getResult(), commonNews.getError_code(),
                commonNews.getReason());

    }
}
