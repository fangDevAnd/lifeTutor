package com.xiaofangfang.filterrice.tool;


/**
 * 这个是关于tableName的与具体操作之间的映射关系
 */
public class TableNameMappingOpration {


/**
 *
 * 根据服务器返回的数据来说，现在需要定义该接口数据的有
 * 1.BannerFlipperActivityTableNameMapping  多个BannerFlip隐射的都是同意个界面，那么就需要对不同的banner进行区分 banner1,banner2,banner3....
 * 2.ProductSalePageActivityTableNameMapping  这个是产品的销售界面，那么对应的 card ，phone ，以及二手机都会对应该界面  card phone secondHandPhone
 * 3.ActionHorizontalActivityTableMapping     这个是活动的界面，内部的多个Action都对应该界面，所以需要进行映射    action1 action2 action3....
 * 4.
 *
 *
 *
 *
 *
 *
 */

    /**
     * 水平滚动视图的点击启动的水平视图的activity对应的tableName
     */
    public enum ActionHorizontalActivityTableNameMapping {

        /**
         * 最新的活动
         */
        newAction("newAction");

        public String tableName;

        ActionHorizontalActivityTableNameMapping(String tableName) {
            this.tableName = tableName;
        }
    }


    /**
     * 申请账户的界面的对应的tableName
     */
    public enum ApplyAccountActivityTableNameMapping {
        /**
         * 申请账户的activity
         */
        applyAccount("applyAccount");

        public String tableName;

        ApplyAccountActivityTableNameMapping(String tableName) {
            this.tableName = tableName;
        }
    }


    /**
     * 轮播图的点击界面tableName对应的操作的映射关系
     */
    public enum BannerFlipperActivityTableNameMapping {

        /**
         * 最新的手机的
         */
        newPhone("newPhone"),
        /**
         * 最新的活动
         */
        newAction("newAction");

        public String tableName;

        BannerFlipperActivityTableNameMapping(String tableName) {
            this.tableName = tableName;
        }

    }


    /**
     * 宽带服务的tableName对应的映射
     */
    public enum BroadbandServiceActivityTableNameMapping {


    }

    /**
     * 套餐卡的详细信息的映射
     */
    public enum CardDetailActivityTableNameMapping {


    }

    /**
     * 携号转网界面的操作
     */
    public enum CarryingNumberToNetWorkActivityTableMapping {


    }

    /**
     * 通讯指导的界面的tableName数据操作
     */
    public enum ConmunicationGuidanceActivityTableMapping {


    }


    /**
     * 企业服务的tableMapping
     */
    public enum EnterpriceServiceActivityTableMapping {

    }


    /**
     * 固话业务的tableMappiing
     */
    public enum FixedLineServiceActivityTableNameMapping {


    }

    /**
     * 流量办理的tableMapping
     */
    public enum FlowHandleActivityTableNameMapping {

    }


    /**
     * 靓号的tableMapping
     */
    public enum LiangHaoActivityTableNameMapping {


    }

    /*
    靓号详细界面的tableMapping
     */
    public enum LiangHaoDetailPageActivityTableNameMapping {


    }

    /**
     * 物流查询的tableMapping
     */
    public enum LogisticsQueryActivityTableNameMapping {

    }


    /**
     * 手机详细信息界面的tableNameMapping
     */
    public enum PhoneDetailPageActivityTableNameMapping {


    }

    /**
     * 产品销售界面的mapping
     */
    public enum ProductSalePageActivityTableNameMapping {

        card("card"),
        phone("phone"),
        secondHandPhone("secondHandPhone");
        public String tableName;
        ProductSalePageActivityTableNameMapping(String tableName) {
            this.tableName = tableName;
        }

    }

    /**
     * 发布二手机的操作
     */
    public enum PublishSecondHandPhoneActivityTableNameMapping {

    }

    /**
     * 已购宝贝的mapping
     */
    public enum PurchasedBabyActivityTableNameMapping {

    }


    public enum QueryBillActivityTableNameMapping {


    }


    /**
     * 最近优惠的Mapping
     */
    public enum RecentDiscountActivityTableNameMapping {


    }

    /**
     * 注册会员的Mapping
     */
    public enum RegisterVIPActivityTableNameMapping {

    }

    /**
     * 自己详细界面的Mapping
     */
    public enum SelfDetailInfoActivityTableNameMapping {

    }


    /**
     * 服务评价的Mapping
     */
    public enum SeriviceEvaluationActivityTableNameMapping {

    }


    /**
     * 套餐变更的具体的操作细节
     */
    public enum SetmealChangeActivityTableNameMapping {

    }

    /**
     * 系统设置的Mapping
     */
    public enum SystemSettingActivityTableNameMapping {

    }


    /**
     * 电话咨询的Mapping
     */
    public enum TelephoneConsultationActivityTableNameMapping {


    }


}
