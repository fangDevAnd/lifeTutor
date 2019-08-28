package com.rcs.nchumanity.entity.model;

public class OperaRecord {
    private Integer oprateId;

    private Integer examId;

    private String title;

    private Integer type;

    private String pingyu;

    private Boolean isDelete;

    private String remark;

    public Integer getOprateId() {
        return oprateId;
    }

    public void setOprateId(Integer oprateId) {
        this.oprateId = oprateId;
    }

    public Integer getExamId() {
        return examId;
    }

    public void setExamId(Integer examId) {
        this.examId = examId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getPingyu() {
        return pingyu;
    }

    public void setPingyu(String pingyu) {
        this.pingyu = pingyu == null ? null : pingyu.trim();
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