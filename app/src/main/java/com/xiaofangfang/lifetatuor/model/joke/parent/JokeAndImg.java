package com.xiaofangfang.lifetatuor.model.joke.parent;

import java.util.List;

/**
 * 笑话或动态趣图的数据项封装
 */
public class JokeAndImg {

    private List<Result> result;
    private int error_code;
    private String reason;

    public JokeAndImg(List<Result> result, int error_code, String reason) {
        this.result = result;
        this.error_code = error_code;
        this.reason = reason;
    }




    public JokeAndImg() {
    }

    public List<Result> getResult() {
        return result;
    }

    public void setResult(List<Result> result) {
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
}
