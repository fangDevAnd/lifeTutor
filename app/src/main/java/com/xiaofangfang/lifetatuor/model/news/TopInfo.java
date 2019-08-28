package com.xiaofangfang.lifetatuor.model.news;


import com.xiaofangfang.lifetatuor.model.news.parent.CommonNews;
import com.xiaofangfang.lifetatuor.model.news.parent.Result;

/**
 * 封装的是新闻头条相关的信息
 */
public class TopInfo extends CommonNews {

    public TopInfo(Result result, int error_code, String reason) {
        super(result, error_code, reason);
    }

    public TopInfo() {
    }

    public TopInfo(CommonNews commonNews) {

        super(commonNews.getResult(), commonNews.getError_code(),
                commonNews.getReason());
        // Looger.d("数据结果的大小" + this.getResult().data.size());
    }
}
