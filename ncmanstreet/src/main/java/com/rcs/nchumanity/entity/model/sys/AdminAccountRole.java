package com.rcs.nchumanity.entity.model.sys;

/***
 * 管理员权限表
 * @auther laoxia
 */
public class AdminAccountRole {
    private Integer id;
    //管理员id
    private Integer adminId;
    //权限id
    private Integer roleId;
    //是否被删除
    private Boolean isDelete;
    private String remark;

}