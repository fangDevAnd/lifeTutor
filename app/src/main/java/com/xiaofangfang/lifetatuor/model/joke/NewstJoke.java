package com.xiaofangfang.lifetatuor.model.joke;

import com.xiaofangfang.lifetatuor.model.joke.parent.JokeAndImg;
import com.xiaofangfang.lifetatuor.model.joke.parent.Result;

import java.util.List;

/**
 * 获得最新的笑话的数据封装类
 */
public class NewstJoke extends JokeAndImg {

    /**
     * 这里实际存放的是ResultImg
     *
     * @param result
     * @param error_code
     * @param reason
     */
    public NewstJoke(List<Result> result, int error_code, String reason) {
        super(result, error_code, reason);
    }

    public NewstJoke() {

    }

    public NewstJoke(JokeAndImg jokeByImg) {
        this(jokeByImg.getResult(), jokeByImg.getError_code(),
                jokeByImg.getReason());
    }


}
