package com.xiaofangfang.filterrice.tool;


/**
 * 客户端的Activity的映射
 * @author fang
 *
 */
public enum ClientActivityName {
	
	ApplyAccountActivity("ApplyAccountActivity"),
	BannerFlipperActivity("ApplyAccountActivity"),
	BroadbandServiceActivity("ApplyAccountActivity"),
	CarryingNumberToNetworkActivity("ApplyAccountActivity"),
	CommunicationGuidanceActivity("ApplyAccountActivity"),
	EnterpriseServiceActivity("ApplyAccountActivity"),
	FixedLineServiceActivity("ApplyAccountActivity"),
	FlowHandleActivity("ApplyAccountActivity"),
	LiangHaoActivity("ApplyAccountActivity"),
	LogisticsQueryActivity("ApplyAccountActivity"),
	ProductSalePageActivity("ApplyAccountActivity"),
	PublishSecondPageActivity("ApplyAccountActivity"),
	PurchasedBabyActivity("ApplyAccountActivity"),
	QueryBillActivity("ApplyAccountActivity"),
	RecentDiscountActivity("ApplyAccountActivity"),
	RegisterVipActivity("ApplyAccountActivity"),
	ServiceEvaluationActivity("ApplyAccountActivity"),
	SetmealChangeActivity("ApplyAccountActivity"),
	ActionHorizontalActivity("ActionHorizontalActivity"),
	PhoneDetainPageActivity("PhoneDetainPageActivity"),
	LiangHaoDetailPageActivity("LiangHaoDetailPageActivity"),
	CardDetailActivity("CardDetailActivity"),
	TelephoneConsultationActivity("ApplyAccountActivity");
	
	public String activityName;
	
	private ClientActivityName(String activityName) {
		this.activityName=activityName;
	}
}
