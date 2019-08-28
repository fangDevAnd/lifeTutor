package com.kangren.cpr.entity;

import java.util.UUID;

/**
 * 分数成绩的封装类
 *
 *
 */
public class Score {

    /**
     * 操作记录表
     * 封装两个数据
     * 1.
     */
    public OprateRecordTable[] tables;
    public String id;
    public String stu_name;
    public String stu_class;
    public String exam_date;

    public long exam_data_long;
    public String exam_type;
    public int score;
    public boolean isUpload;

    public String path;

    public Score() {
        id = UUID.randomUUID().toString();
    }

}
