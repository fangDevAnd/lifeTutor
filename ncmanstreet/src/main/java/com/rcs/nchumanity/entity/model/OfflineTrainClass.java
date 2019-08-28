package com.rcs.nchumanity.entity.model;

import java.util.Date;

public class OfflineTrainClass {
    private Integer classId;

    private Integer courseNo;

    private String className;

    private Date startTime;

    private Date endTime;

    private Integer trainerId;

    private String intro;

    private String imgUrl;

    private Integer pointId;

    private Boolean isVr;

    private Integer currentNum;

    private Integer maxNum;

    private String specialText;

    private String backupText;

    private Boolean isDelete;

    private String remark;

    public Integer getClassId() {
        return classId;
    }

    public void setClassId(Integer classId) {
        this.classId = classId;
    }

    public Integer getCourseNo() {
        return courseNo;
    }

    public void setCourseNo(Integer courseNo) {
        this.courseNo = courseNo;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className == null ? null : className.trim();
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Integer getTrainerId() {
        return trainerId;
    }

    public void setTrainerId(Integer trainerId) {
        this.trainerId = trainerId;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro == null ? null : intro.trim();
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl == null ? null : imgUrl.trim();
    }

    public Integer getPointId() {
        return pointId;
    }

    public void setPointId(Integer pointId) {
        this.pointId = pointId;
    }

    public Boolean getIsVr() {
        return isVr;
    }

    public void setIsVr(Boolean isVr) {
        this.isVr = isVr;
    }

    public Integer getCurrentNum() {
        return currentNum;
    }

    public void setCurrentNum(Integer currentNum) {
        this.currentNum = currentNum;
    }

    public Integer getMaxNum() {
        return maxNum;
    }

    public void setMaxNum(Integer maxNum) {
        this.maxNum = maxNum;
    }

    public String getSpecialText() {
        return specialText;
    }

    public void setSpecialText(String specialText) {
        this.specialText = specialText == null ? null : specialText.trim();
    }

    public String getBackupText() {
        return backupText;
    }

    public void setBackupText(String backupText) {
        this.backupText = backupText == null ? null : backupText.trim();
    }

    public Boolean getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Boolean isDelete) {
        this.isDelete = isDelete;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}