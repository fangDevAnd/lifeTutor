package com.xiaofangfang.lifetatuor.model.news.parent;

import java.io.Serializable;

public class CommonNews  {
    //返回的结果集
    private Result result;
    //错误码
    private int error_code;
    //原因
    private String reason;

    public CommonNews(Result result, int error_code, String reason) {
        this.result = result;
        this.error_code = error_code;
        this.reason = reason;
    }

    public CommonNews() {
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
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
