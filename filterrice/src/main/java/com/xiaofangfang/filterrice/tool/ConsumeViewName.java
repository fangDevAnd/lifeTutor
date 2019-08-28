package com.xiaofangfang.filterrice.tool;

/**
 * 自定义视图的名称
 * @author fang
 *
 */
public enum ConsumeViewName {

	BANNER_FLIP_CONTAINER("BannerFlipContainer"),
	CONSUME_TOOLBAR("ConsumeToolbar"),
	FUNCTION_MODE("FunctionMode"),
	LIANG_HAO_DISPLAY_VIEW("LiangHaoDisplayView"),
	MY_ACTION_HORIZONTAL_SCROLL_VIEW("MyActionHorizontalScrollView"),
	MY_INDEX_INFO_VIEW("MyIndexInfoView"),
	MY_MENU_FUNCTION_LIST("MyMenuFunctionList"),
	PHONE_DISPLAY_VIEW("PhoneDisplayView"),
	PHONE_SALE_VIEW("PhoneSaleView"),
	SETMEAL_VIEW("SetmealView");


	public String consumeViewName;

	ConsumeViewName(String consumeViewName){
		this.consumeViewName=consumeViewName;
	}

}
