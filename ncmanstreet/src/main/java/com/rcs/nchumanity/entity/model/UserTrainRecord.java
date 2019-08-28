package com.rcs.nchumanity.entity.model;

import java.util.Date;

public class UserTrainRecord {
    private Integer trainId;

    private Integer userId;

    private Date startTime;

    private Date allowTime;

    private Integer totalScore;

    private Integer studyStatus;

    private Integer type;

    private Boolean checked;

    private String checkMan;

    private Boolean isDelete;

    private String remark;

    public Integer getTrainId() {
        return trainId;
    }

    public void setTrainId(Integer trainId) {
        this.trainId = trainId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getAllowTime() {
        return allowTime;
    }

    public void setAllowTime(Date allowTime) {
        this.allowTime = allowTime;
    }

    public Integer getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(Integer totalScore) {
        this.totalScore = totalScore;
    }

    public Integer getStudyStatus() {
        return studyStatus;
    }

    public void setStudyStatus(Integer studyStatus) {
        this.studyStatus = studyStatus;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Boolean getChecked() {
        return checked;
    }

    public void setChecked(Boolean checked) {
        this.checked = checked;
    }

    public String getCheckMan() {
        return checkMan;
    }

    public void setCheckMan(String checkMan) {
        this.checkMan = checkMan == null ? null : checkMan.trim();
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