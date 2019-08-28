package com.rcs.nchumanity.entity;

public class StudyStatus {


    /**
     * 没有学习
     */
    public static final int STATUS_NOT_STUDY = 1;

    /**
     * 线上学习中
     */
    public static final int STATUS_ONLINE_STUDY = 2;

    /**
     * 允许线上考核
     */
    public static final int STATUS_ONLINE_ASSESS = 3;

    /**
     * 通过线上考核
     */
    public static final int STATUS_POST_ONLINE_ASSESS = 4;

    /**
     * 已选择培训班
     */
    public static final int STATUS_SELECTED_TRAIN_CLASS = 5;

    /**
     * 已完成线下培训
     */
    public static final int STATUS_COMPLETE_OFFLINE_TRAIN = 6;

    /**
     * 状态 完成线下培训
     */
    public static final int STATUS_COMPLETE_OFFLINE_ASSESS = 7;

    /**
     *领证
     */
    public static final int STATUS_COMPLETE_CERTIFICATION = 8;


}
