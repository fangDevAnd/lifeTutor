package com.xiaofangfang.lifetatuor.model.news.parent;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Result  {

    public List<Data> data;

    public Result(List<Data> data) {
        this.data = data;
    }

    public Result() {

    }

    public List<Data> getData() {
        return data;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }
}
