package com.rcs.nchumanity.entity.model.train;


import java.util.Date;

public class UserBasicInfo {
    //用户id
    private Integer userId;

    //姓名

    private String name;

    //性别

    private String gender;

    //工作单位

    private String employer;

    //身份证号

    private String idNumber;

    //救护员证书编号
    private String licenseNo;

    //用户图片url

    private String photoUrl;

    //训练状态  0	未报名
    // 1	已报名
    // 2	培训合格
    // 3	已过期
    // 4	复训中
    private Integer trainStatus;

    //证书过期时间
    private Date overdueTime;

    //文化程度

    private String degreeOfEducation;

    //工作年限

    private String yearsOfWorking;

    //签发日期
    private String signDate;

    //职称职务
    private String duty;

    //家庭住址
    private String address;

    //所在地区
    private Integer areaId;

    //是否删除   0否1是
    private Boolean isDelete;

    //备注
    private String remark;


}