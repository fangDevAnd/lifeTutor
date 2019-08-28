package com.xiaofangfang.lifetatuor.model.joke;

import com.xiaofangfang.lifetatuor.model.joke.parent.JokeAndImg;
import com.xiaofangfang.lifetatuor.model.joke.parent.Result;

import java.util.List;

/**
 * 根据时间加载动态图的封装类
 */
public class ImgByTime  {

    private List<ResultImg> result;
    private int error_code;
    private String reason;


    public List<ResultImg> getResult() {
        return result;
    }

    public void setResult(List<ResultImg> result) {
        this.result = result;
    }

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }


    public ImgByTime(List<ResultImg> result, int error_code, String reason) {
        this.result = result;
        this.error_code = error_code;
        this.reason = reason;
    }

    public ImgByTime() {
    }

    public ImgByTime(ImgByTime imgByTime) {
        this(imgByTime.getResult(), imgByTime.getError_code(),
                imgByTime.getReason());
    }


}
