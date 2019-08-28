package com.xiaofangfang.lifetatuor.model.joke;

import com.xiaofangfang.lifetatuor.model.joke.parent.JokeAndImg;
import com.xiaofangfang.lifetatuor.model.joke.parent.Result;

import java.util.List;

/**
 * 最新的动态图的封装类
 */
public class NewstImg extends ImgByTime {

    public NewstImg(List<ResultImg> result, int error_code,
                    String reason) {
        super(result, error_code, reason);
    }

    public NewstImg() {
    }

    public NewstImg(ImgByTime imgByTime) {
        super(imgByTime.getResult(), imgByTime.getError_code(),
                imgByTime.getReason());
    }

}
