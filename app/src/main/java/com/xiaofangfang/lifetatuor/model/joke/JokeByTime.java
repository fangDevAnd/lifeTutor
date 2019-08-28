package com.xiaofangfang.lifetatuor.model.joke;

import com.xiaofangfang.lifetatuor.model.joke.parent.JokeAndImg;
import com.xiaofangfang.lifetatuor.model.joke.parent.Result;

import java.util.List;

/**
 * 根据时间获得笑话的数据封装类
 */
public class JokeByTime extends JokeAndImg {

    public JokeByTime(List<Result> result, int error_code, String reason) {
        super(result, error_code, reason);
    }

    public JokeByTime() {
    }


    public JokeByTime(JokeAndImg jokeAndImg) {
        this(jokeAndImg.getResult(),jokeAndImg.getError_code(),
                jokeAndImg.getReason());
    }
}
