package com.rcs.nchumanity.entity.model.sys;

import java.util.Date;




public class AdminAccount {

	// 管理用户id
	private Integer adminId;

	// 管理账号名
	private String adminName;

	// 密码
	private String password;

	// 手机号
	private String phone;

	// 是否可用
	private Boolean available;

	// 管理用户名
	private String name;

	// 管理员账户类型
	// 1 信息录入员
	// 2 培训员
	// 3 培训机构管理员
	// 4 最高等级管理员
	private Integer type;

	//所属组织id
	private Integer orgId;

	//创建时间
	private Date createTime;

	//是否隐藏
	private Boolean isDelete;

	//备注
	private String remark;

}