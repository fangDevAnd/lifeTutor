package com.rcs.nchumanity.entity;


/**
 * 网络连接的接口实现
 * 提供的是一堆的常量
 */
public class NetConnectionUrl {



    /**
     * 快递查询的相关的接口信息常量
     */
    public static interface ExpressInter {

        //电商ID
        public static final String EBusinessID = "1339086";
        //电商加密私钥，快递鸟提供，注意保管，不要泄漏
        public static final String AppKey = "fb870cad-a9c8-4540-a3ed-4f0e6d5d252d";
        //请求url
        public static final String ReqURL = "http://api.kdniao.com/Ebusiness/EbusinessOrderHandle.aspx";
    }

    /**
     * 百度地图的相关的常量
     */
    public static interface BaiduMapInter {

    }

    /**
     * 极光推送的相关的常量
     */
    public static interface JGPullInter {

    }

    /**
     * mob
     * 短信验证登录相关的接口
     */
    public static interface SmsVerficaInter {

    }

    /**
     * 微信支付相关的接口
     */
    public static interface WeChatPayInter {

    }


    /**
     * 阿里支付接口
     */
    public static interface AliPayInter {

    }


    public static final String HOST = "https://www.tongkun5g.com/";

    /**
     * 服务器的主地址
     */
    public static final String ROOT_SERVER = HOST + "ncrd/";

    public static final String APP = ROOT_SERVER + "app/";


    public static final String system = ROOT_SERVER + "sys/";

    public static final String api = ROOT_SERVER + "api/";


    public static String getSignUpInfoByUser=APP+"signUp/getSignUpInfoByUser";
    /**
     * 获的邮寄状态
     */
    public static String getPostStatus=APP+"certification/getPostStatus";

    /**
     * 获得隐私政策
     */
    public static String getPrivacyPolicy = HOST + "/getPrivacyPolicy";


    /**
     * 查询选修的课程信息
     */
    public static String getNotRequiredCourseByCourseNo = APP + "onlineStudy/getNotRequiredCourseByCourseNo?courseNo=%s";

    /**
     * 取消选课
     */
    public static String cancelChooseClass = APP + "my/myCourse/cancelChosenClass?id=%d";

    /**
     * 帮助中心
     */
    public static String getHelpInfo = APP + "settings/info/getHelpInfo";

    /**
     * 根据密码进行修改密码
     */
    public static String changePassword = api + "updatePassword";


    /**
     * 提交用户邮寄信息
     */
    public static String doUnifiedOrder = APP + "certification/savePostInfo";


    /**
     * 获得用户状态
     */
    public static String getStatusByUser = APP + "certification/getStatusByUser";

    /**
     * 获得用户报名相关信息
     */
    public static String getPostInfoByUser = APP + "certification/getPostInfo";

    /**
     * 获得验证码
     */
    public static String getValidateCode = system + "admin/sendSms";


    /**
     * 关于我们
     */
    public static String getAboutUs = APP + "settings/info/getHelpInfo";

    /**
     * 短信验证登录
     */
    public static String smsLogin = system + "admin/smsLogin";


    /**
     * 修改头像
     */
    public static String updateUserPic = APP + "settings/updateUserPic";

    /**
     * 短信验证注册
     * post 方式
     * code
     * mobilephone
     * password
     */
    public static String register = api + "userRegister";


    /**
     * 添加救护的响应信息
     */
    public static String addResEmergency = ROOT_SERVER + "emergency/res/addResEmergency";


    /**
     * 我的电子证书
     */
    public static String myCertificate = APP + "settings/getElectronicCertificate";

    /**
     * 我的课程
     */
    public static String myCourse = APP + "my/myCourse/getMyClassList";

    /**
     * 取消选课
     */
    public static String getMyClassList = APP + "my/myCourse/cancelChosenClass";

    /**
     * 获取考核结果
     */
    public static String getMyScore = APP + "my/myCourse/getMyScore";


    /**
     * 发送求救
     */
    public static String addInfo = ROOT_SERVER + "emergency/ForHelp/addInfo";

    /**
     * 获得aed分布
     */
    public static final String getAEDList = ROOT_SERVER + "emergency/aed/getAEDList";


    /**
     * 获取线上必修课程列表（匿名）
     */
    public static String getOnlineClass = APP + "onlineStudy/getOnlineClass";


    /**
     * 获取线上必修课程及用户学习记录列表 （非匿名）
     */
    public static String getOnlineClassAndRecordByUser = APP + "onlineStudy/getOnlineClassAndRecordByUser";


    /**
     * 获取线上选修课程及用户学习记录列表 (非匿名)
     */
    public static String getNotRequiredOnlineClassAndRecordByUser = APP + "onlineStudy/getNotRequiredOnlineClassAndRecordByUser";

    /**
     * 获取线上选修课程及用户学习记录列表 (非匿名)
     */
    public static String getNotRequiredOnlineClass = APP + "onlineStudy/getNotRequiredOnlineClass";


    /**
     * 登录的接口
     * 登录传递的是
     * <p>
     * send 数据
     */
    public static String login = system + "admin/login";
    /**
     * 注册的接口
     */
//    public static String register = ROOT_SERVER + "/api/userRegister";


//    public static String smsLogin = system + "admin/smsLogin?mobilephone=%s";


    public static String resetPasswordWithMobilePhone = api + "resetPasswordWithMobilePhone?code=%s&mobilephone=%s&password=%s";


    /**
     * 查询指定的SpecificInfoClassfication的typeid对应的 SpecificInfo
     */
    public static final String getSpecificInfoForClassId = ROOT_SERVER + "getSpecificInfoForClassId?size=%s&page=?";


    /**
     * 通过id 查询 特定的 SpecificInfo
     */
    public static final String getSpecificInfoForId = ROOT_SERVER + "getSpecificInfoForId";


    /**
     * 用户报名状态的查询
     */
    public static String getSignInStatus = APP + "signUp/getStatusByUser";


    /**
     * post方式  提交用户报名信息
     */
    public static String submitSignInUserInfo = APP + "signUp/submitSignUpInfoByUser";


    /**
     * 获得用户已有报名信息
     */
    public static String getUserHasSignInInfo = "";


    /**
     * 获得获得线上课程内容详情
     */
    public static String getOnlineCourseContentForId = APP + "onlineStudy/getCourseByCourseNo?courseNo=%s";


    /**
     * 提交观看数据
     * post
     * <p>
     * courseNo
     * start_time
     * total_time
     */
    public static String submitWatchData = APP + "onlineStudy/recieveWatchData";

    /**
     * 获得选修以及对应的学习记录
     */
    public static String getSelectCourseAndStudyRecord = "";


    /**
     * 获得试卷的数据
     */
    public static String getExamSubject = APP + "onlineTest/getExamPaper";


    /**
     * 提交试卷
     * POST
     */
    public static String submitExamSubject = APP + "onlineTest/postExamPaper";


    /**
     * 获取创伤救护培训班列表
     */
    public static String getTraumaClassList = APP + "offlineTrain/getTraumaClassList";

    /**
     * 获取心肺复苏培训班列表
     */
    public static String getCPRClassList = APP + "offlineTrain/getCPRClassList";


    /**
     * 获得线下培训班课程详情
     */
    public static String getOfflineTrainClassDetail = APP + "offlineTrain/getClassInfo?classId=%s";


    /**
     * 报名线下培训班报名
     */
    public static String offlineTrainClassSignUp = APP + "offlineTrain/saveStudyRecord";


    /**
     * 心肺复苏培训班签到
     * POST
     */
    public static String signInCPRClass = APP + "offlineTrain/signInCPRClass";

    /**
     * 创伤救护培训班签到
     * POST
     */
    public static String signInTraumaClass = APP + "offlineTrain/signInTraumaClass";

    /**
     * 查询心肺复苏的成绩
     * GET
     */
    public static String queryXFScore = APP + "offlineTrain/saveCPRScore?bindNo=%s";


    /**
     * 查询创伤救护的成绩
     * GET
     */
    public static String queryCSJHScore = APP + "offlineTrain/saveTraumaScore?specialText=%s&name=%s";


    /**
     * 相关资讯
     */
    public static String getNews = APP + "info/getNews";

    /**
     * 关于红十字会
     */
    public static String getAboutRedCross = APP + "info/getAboutRedCross";

    /**
     * 获得无偿献血信息
     */
    public static String getBloodDonation = APP + "info/getBloodDonation";

    /**
     * 获得急救知识信息
     */
    public static String getKnowledge = APP + "info/getKnowledge";

    /**
     * 获得爱心捐献信息
     */
    public static String getLoveDonation = APP + "info/getLoveDonation";

    /**
     * 获得骨髓捐献信息
     */
    public static String getMarrowDonation = APP + "info/getMarrowDonation";


    /**
     * 获得养老救护信息
     */
    public static String getOldCare = APP + "info/getOldCare";

    /**
     * 获得遗体捐献的信息
     */
    public static String getOrganBodyDonation = APP + "info/getOrganBodyDonation";

    /**
     * 取消报名
     */
    public static String cancelSignUp = APP + "settings/cancelSignUp";

    /**
     * 提交流言反馈
     */
    public static String commitFeedback = APP + "settings/commitFeedback";

    /**
     * 获得流言反馈
     */
    public static String getFeedback = APP + "settings/getFeedback";


    /**
     * 获得首页数据
     */
    public static String getIndexInfo = APP + "index/getInfo";


    /**
     * 救护信息列表
     */
    public static String selectInfo = ROOT_SERVER + "emergency/ForHelp/selectInfo?pageSize=60";


    /**
     * 救护信息列表
     */
    public static String selectInfoSplitPage = ROOT_SERVER + "emergency/ForHelp/selectInfo?pageSize=%d&pageNum=%d";


    /**
     * 主页查询分类信息对应的数据接口
     */
    public static String MAIN_CLASS = "";

    /**
     * 主页的banner的接口
     */
    public static String MAIN_BANNER = "";

    /**
     * 响应求救信息 响应求救信息之后跳转到百度地图的定位界面
     */
    public static String CRY_HELPER_RESP = "";

    /**
     * 查看求救信息的响应
     */
    public static String CRY_HELPER_QUERY = "";

    /**
     * 我的课程
     */
    public static String ME_COURSE = "";
    /**
     * 我的考核结果的接口
     */
    public static String ME_ASSESS_RESULT = "";

    /**
     * 我的证书的接口
     */
    public static String ME_CERTIFICATE = "";

    /**
     * 提交留言反馈的接口
     */
    public static String ME_FEEDBACK_SUBMIT = "";

    /**
     * 获得留言反馈的接口
     * 获得的是我们提交的留言系统后台恢复的反馈
     */

    public static String ME_FEEDBACK_GET = "";

    /**
     * 基础培训报名
     * 使用的是post请求，需要涉及到文件的上传
     * 同时传递对应的详细信息
     */
    public static String TRAIN_SIGN_UP = "";

    /**
     * 复训的接口
     * 同上面一样，同样涉及到文件上传的,传递的是用户证件照
     */
    public static String TRAIN_RETRAIN = "";

    /**
     * 线上课程的数据，传递的是视频的基本的信息，
     * 通过点击对应的列表，进入视频的详细数据的显示
     * 包括必修课程和选修课程
     */
    public static String ONLINE_TRAIN_VIDEO_LIST = "";


    /**
     * 获得在线视频的详细信息
     * 包括视频的url，视频的相关信息，以及对应视频可能存在的习题信息
     */
    public static String ONLINE_TRAIN_VIDEO_DETAIL = "";


    /**
     * 线上考核的接口，该条存在歧义，明天会议讨论
     */
    public static String ONLINE_ASSESS = "";

    /**
     * 心肺复苏课程列表
     * 获得的是课程的相关的数据，以及对应地区
     */
    public static String CPR_COURSE_LIST = "";

    /**
     * 心肺复苏课程的详情数据
     */
    public static String CPR_COURSE_DETAIL = "";

    /**
     * 课程签到接口
     * 因为签到需要实现的是
     * 心肺复苏的签到和
     * 创伤救护课程的签到，所以需要课程的id和用户的id
     */
    public static String CPR_COURSE_SIGN_IN = "";

    /**
     * aed的分布的列表，当前的aed分布的列表依赖于当前用户的位置
     */
    public static String AED_DISTRIBUTE_LIST;

    /**
     * 急救知识列表
     */
    public static String CRY_HELPER_KNOWLEDGE_LIST;

    /**
     * 急救知识的详情信息
     * 急救知识的详情信息和培训视频的相关信息是差不多的，一定情况可以接口复用
     */
    public static String CRY_HELPER_KNOWLEDGE_LIST_DETAIL;

    /**
     * 献血位置列表，用来对百度地图进行标记
     */
    public static String BLOOD_DONATION_POSITION_LIST;

    /**
     * 获得当前用户的注册状态、
     * 传递的参数是用户的phoneNumber
     * 返回的参数是json格式的数据 格式是BasicResponse类的实现
     * <p>
     * 参数
     * mobilephone=
     */
    public static final String REGISTER_STATUS = api + "getUserRegisterStatus?mobilephone=%s";

    static {


    }


}
