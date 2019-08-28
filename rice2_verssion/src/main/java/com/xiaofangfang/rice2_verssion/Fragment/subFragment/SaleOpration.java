package com.xiaofangfang.rice2_verssion.Fragment.subFragment;

import android.content.Intent;

/**
 * 销售的操作的集合，用来定义了销售界面fragment的基本的操作的集合
 */
public interface SaleOpration {
    //设置tableName的名称，代表的加载的数据源，同时也是定义了加载的界面格式
    void setTableName(String tableName);

    void loadData();

    /**
     * 获得筛选条件
     * 这个筛选的条件是fragment上边的用于排序的条形
     *
     * @return
     */
    String getFilterConditionParamString();

    /**
     * 设置赛选条件
     */

    /**
     * 获得请求的url
     *
     * @return
     */
    String getUrl();

    /**
     * 设置过滤界面传递的过滤条件
     *
     * @param value
     * @return
     */
    void setFilterFragmentCondition(String value);

    /**
     * 初始化请求的参数
     */
    void initParam();

    /**
     * list点击启动的activity
     */
    Intent getStartIntent();


}
