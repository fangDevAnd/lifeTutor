package com.rcs.nchumanity.entity.model;

public class OprateRecordDetail {
    private Integer id;

    private Integer oprateId;

    private String name;

    private String valueStr;

    private Boolean isDelete;

    private String remark;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOprateId() {
        return oprateId;
    }

    public void setOprateId(Integer oprateId) {
        this.oprateId = oprateId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getValueStr() {
        return valueStr;
    }

    public void setValueStr(String valueStr) {
        this.valueStr = valueStr == null ? null : valueStr.trim();
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