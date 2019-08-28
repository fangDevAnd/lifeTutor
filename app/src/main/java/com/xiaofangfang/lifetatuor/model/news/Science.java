package com.xiaofangfang.lifetatuor.model.news;

import com.xiaofangfang.lifetatuor.model.news.parent.CommonNews;
import com.xiaofangfang.lifetatuor.model.news.parent.Result;

/**
 * 科技相关的封装信息
 */
public class Science extends CommonNews {
    public Science(Result result, int error_code, String reason) {
        super(result, error_code, reason);
    }

    public Science() {
    }
    public Science(CommonNews commonNews) {
        super(commonNews.getResult(), commonNews.getError_code(),
                commonNews.getReason());
        // Looger.d("数据结果的大小" + this.getResult().data.size());
    }
}
