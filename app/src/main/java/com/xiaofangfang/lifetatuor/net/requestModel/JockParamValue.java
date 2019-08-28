package com.xiaofangfang.lifetatuor.net.requestModel;

/**
 * 笑话请求参数的值
 * 封装的是请求时传递的值
 */
public class JockParamValue {

    /**
     * 当前的页数,默认为1,
     */
    private   String page;
    /***
     *  	返回记录条数，默认rows=20,最大50
     */
    private  String rows;
    /**
     * 类型，desc:指定时间之前发布的，asc:指定时间之后发布的
     */
    private  String sort;
    /**
     * 时间戳（10位），如：1418816972
     * 对于我们想要获得最新的数据,可以构建时间戳,然后sort设置desc
     */
    private  String time;

    public JockParamValue() {
    }

    public JockParamValue(String page, String rows, String sort, String time) {
        this.page = page;
        this.rows = rows;
        this.sort = sort;
        this.time = time;
    }


    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getRows() {
        return rows;
    }

    public void setRows(String rows) {
        this.rows = rows;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
