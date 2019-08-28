package com.rcs.nchumanity.entity.model;

import java.io.Serializable;
import java.util.Date;

public class SpecificInfo implements Serializable {

    private Integer id;

    private Integer specificNo;

    private String title;

    private Date createTime;

    private String icon;

    private String imgUrl;

    private String videoId;

    private String videoUrl;

    private String editor;

    private Boolean checked;

    private Integer typeId;

    private Boolean isDelete;

    private String remark;

    private String content;

    public SpecificInfo(Integer id, Integer specificNo, String title, Date createTime, String icon, String imgUrl, String videoId, String videoUrl, String editor, Boolean checked, Integer typeId, Boolean isDelete, String remark, String content) {
        this.id = id;
        this.specificNo = specificNo;
        this.title = title;
        this.createTime = createTime;
        this.icon = icon;
        this.imgUrl = imgUrl;
        this.videoId = videoId;
        this.videoUrl = videoUrl;
        this.editor = editor;
        this.checked = checked;
        this.typeId = typeId;
        this.isDelete = isDelete;
        this.remark = remark;
        this.content = content;
    }

    public SpecificInfo() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSpecificNo() {
        return specificNo;
    }

    public void setSpecificNo(Integer specificNo) {
        this.specificNo = specificNo;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon == null ? null : icon.trim();
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl == null ? null : imgUrl.trim();
    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId == null ? null : videoId.trim();
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl == null ? null : videoUrl.trim();
    }

    public String getEditor() {
        return editor;
    }

    public void setEditor(String editor) {
        this.editor = editor == null ? null : editor.trim();
    }

    public Boolean getChecked() {
        return checked;
    }

    public void setChecked(Boolean checked) {
        this.checked = checked;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }
}