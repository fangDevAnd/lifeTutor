package com.xiaofangfang.lifetatuor.model.news;

import com.xiaofangfang.lifetatuor.model.news.parent.CommonNews;
import com.xiaofangfang.lifetatuor.model.news.parent.Result;
import com.xiaofangfang.lifetatuor.tools.Looger;

/**
 * 国内新闻的相关信息的封装对象
 */
public class Domestic extends CommonNews {


    public Domestic(Result result, int error_code, String reason) {
        super(result, error_code, reason);

    }

    public Domestic() {

    }

    public Domestic(CommonNews commonNews) {
        super(commonNews.getResult(), commonNews.getError_code(),
                commonNews.getReason());
       // Looger.d("数据结果的大小" + this.getResult().data.size());
    }
}
