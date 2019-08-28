package com.rcs.nchumanity.entity.model;

import java.util.Date;

public class OfflineExamRecord {
    private Integer examId;

    private String bindNo;

    private Date examTime;

    private String vrdataId;

    private Integer score;

    private Float passRate;

    private Boolean isDelete;

    private String remark;

    public Integer getExamId() {
        return examId;
    }

    public void setExamId(Integer examId) {
        this.examId = examId;
    }

    public String getBindNo() {
        return bindNo;
    }

    public void setBindNo(String bindNo) {
        this.bindNo = bindNo == null ? null : bindNo.trim();
    }

    public Date getExamTime() {
        return examTime;
    }

    public void setExamTime(Date examTime) {
        this.examTime = examTime;
    }

    public String getVrdataId() {
        return vrdataId;
    }

    public void setVrdataId(String vrdataId) {
        this.vrdataId = vrdataId == null ? null : vrdataId.trim();
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Float getPassRate() {
        return passRate;
    }

    public void setPassRate(Float passRate) {
        this.passRate = passRate;
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