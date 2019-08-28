package com.example.momomusic.servie;


import android.content.Context;
import android.content.SharedPreferences;

/**
 * 系统设置服务
 * <p>
 * 提供的功能有
 * 1.最近播放列表最大保存量
 * 2.默认本地播放顺序  顺序播放 or  随机播放
 * 3.自动关闭  设置多久时间自动停止当前的播放
 * 4.自动进入播放页面，当我们的后台在播放的时候，点击应用程序应该进入播放界面而不是主页面
 * 5.桌面歌词的实现    当然难点是我们本地音乐没有歌词
 * 6.个性化推荐，根据我们听的内容，推送差不多内容的歌单 ，我们集成极光推送
 * 7.智能词图优化   我们先去插叙本地是够存在歌词 ，因为系统一般安装第三方的音乐软件，我们可以借助实现歌词  如果没有就去服务器检索，都没有的情况下，就凉凉
 * 8.通知设置  通知提醒（开关）   评论被回复 （开关）  评论被点赞(开关)
 * 9.边听边下载（开关）
 * 10.单曲收藏下载（开关）
 * 11.仅仅在WIFI下自动下载---对收藏来说的（开关）
 * 12.使用2g/3g/4g流量播放
 * 13.使用2g/3g/4g流量播放视频
 * 14文件过滤  大小过滤（开关） 滑动条   时长过滤（开关）  滑动条   按文件过滤  --->打开一个activity，选择文件夹
 * 15.隐私政策
 * <p>
 * <p>
 * <p>
 * 系统的所有的设置信息都是通过读取sp里面的内容进行呈现的
 * <p>
 * 所以我们在之前需要设置一些字段，同时对这些字段设置默认值
 */
public class SystemSettingService {

    private static SharedPreferences sharedPreferences;

    private Context context;

    /**
     * 自动进入播放界面
     * 默认false
     */
    public static final String AUTO_ENTRY_PLAY_PAGE = "autoEntryPlayPage";
    /**
     * 桌面歌词
     * 默认false
     */
    public static final String DESKTOP_RLC = "desktopRLC";

    /**
     * 个性化推荐
     * 默认false
     */
    public static final String PERSONAL_RECOMMEND = "personalRecommend";

    /**
     * 智能词图优化
     * 默认false
     */
    public static final String INTELLIGENCE_OPTIMIZATION = "intelligenceOptimization";

    /**
     * 通知提醒
     * 默认 false
     */
    public static final String NOTIFICATION_TIP = "notificationTip";

    /**
     * 评论被回复
     * 默认false
     */
    public static final String COMMENT_REPLY = "commentReply";

    /**
     * 评论被赞
     * 默认false
     */
    public static final String COMMENT_ADMIRE = "commentAdmire";

    /**
     * 自动关闭
     * 这个在倒计时结束之后自动关闭
     * 默认false
     */
    public static final String AUTO_CLOSE = "autoClose";

    /**
     * 边听边下载
     * 默认 false
     */
    public static final String LISTENER_DOWNLOAD = "listenerDownload";

    /**
     * 单曲收藏就下载  默认false  尽管 如果开启该选项，同样还是需要检测当前的网络环境，如果设置了wifi才自动下载，打开同样不会下载，
     * 只会弹出窗口提示，是否需要关闭wifi下面自动下载
     */
    public static final String SINGLE_MUSIC_COLLECT_DOWNLOAD = "singleMusicCollectDownload";

    /**
     * 只有在wifi下面才自动下载  默认true 这个下载应对的收藏自动下载
     */
    public static final String WIFI_AUTO_DOWNLOAD = "wifiAutoDownload";

    /**
     * 流量播放音乐  默认false
     */
    public static final String FLOW_PLAY_MUSIC = "flowPlayMusic";

    /**
     * 使用流量播放视频
     * boolean 默认false
     */
    public static final String FLOW_PLAY_VIDEO = "flowPlayVideo";

    /**
     * 使用大小进行过滤
     * boolean 默认false
     */
    public static final String BIG_OR_SMALL_FILETER = "bigOrSmallFilter";

    /**
     * 使用时长进行过滤
     * boolean  默认false
     */
    public static final String TIME_LENGTH_FILTER = "timeLengthFilter";

    /**
     * 文件夹过滤
     * 在这里保存的是过滤的文件夹，是一个stringset
     */
    public static final String FOLDER_FILTER = "folderFilter";


    public static final String NAME = "sysSet";

    /**
     * 默认的bool值
     */
    public static final boolean DEFAULT_BOOL = false;


    private SystemSettingService(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
    }


    private static SystemSettingService systemSettingService;


    public static SystemSettingService getInstace(Context context) {
        if (systemSettingService == null) {
            systemSettingService = new SystemSettingService(context);
        }
        return systemSettingService;
    }

    public SharedPreferences getSharedPreferences() {
        return sharedPreferences;
    }
}
