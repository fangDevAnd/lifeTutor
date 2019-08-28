//配置类，cpr

/**


配置了 菜单的栏目
      服务器的基本地址
      加载中的图片
      中英文

*/

var Config = (function(){

    var unique;

    function Construct(){

        //左侧三个不同模块的菜单
        this.cprMenus = [];
        //服务器的地址
        this.serverBasePath="http://localhost:60896/";
        //实验菜单
        this.cprMenus[0] = new Menu("理论学习", "模块一", "cpr/knowledge");
        this.cprMenus[1] = new Menu("训练模式", "模块二", "");
        this.cprMenus[2] = new Menu("考核模式", "模块三", "");
        this.cprMenus[3] = new Menu("实战模式", "模块四", "");
        this.cprMenus[4] = new Menu("历史成绩", "模块五", "cpr/scoreRecord");

        //进度条图片
        this.loadPicPath = "css/image/loading.gif";

        //是否使用伪数据
        this.fakeData = false;
        //使用伪数据情况下，伪数据个数
        this.fakeDataCount = 10;

    }

    //菜单类
    function Menu(title, description, url) {
        this.title = title;
        this.description = description;
        this.url = url;
        this.selected = false;
    }

    unique = new Construct();
    return unique;

})();

function English(){
    this.lilunxuexi="Knowledge";
    this.xunlianmoshi = "Exercise";
    this.kaohemoshi = "Exam";
    this.shizhanmoshi = "Live Exam";
    this.lishichengji = "History";
    this.qingshurupingyu = "Please Enter The Password";
    this.anyapinlv = "Pressing Rate";
    this.kaishi = "Start";
    this.tingzhi = "Stop";
    this.fanhui = "Back";
    this.cprXiangQing = "CPR Info";
    this.baocunchengji = "Save Score";
    this.pingugxiangqing = "Assessment";
    this.qingchuyiwu = "Clear Foreign Matter";
    this.maibojiancha = "Sphygmoscopy Check";
    this.jijiuhujiao = "Phone";
    this.huxijiancha = "Breath Chck";
    this.yishipanduan = "Live Check";
    this.hejicaozuo = "T<br>o<br>t<br>a<br>l";
    this.chuiqi = "B<br>l<br>o<br>w";
    this.anya = "P<br>r<br>e<br>s<br>s";
    this.zongshu = "Total";
    this.zhengque = "Right";
    this.cuowu = "Error";
    this.dangqiancaozuo = "C<br>u<br>r<br>r<br>e<br>n<br>t";
    this.guoda = "Large";
    this.guoxiao = "Small";
    this.duochui = "Blow Hyp+";
    this.shaochui = "Blow Hyp-";
    this.chuiqijinwei = "Into<br>Stomach";
    this.qidaoweikai = "Airway <br>NotOpen";
    this.duoan = "Press Hyp+";
    this.shaoan = "Press Hyp-";
    this.pinlvcuowu = "Rate Error";
    this.huitanbuzu = "Insufficient Springback";
    this.anyaweizhi = "Press Point";
    this.pianshang = "Above";
    this.pianxia = "Below";
    this.pianzuo = "Left";
    this.pianyou = "Right";
    this.qingshuruguanliyuanmima = "Password";
    this.queren = "Submit";
    this.mimacuowu = "Password Error";
    this.caozuoshijian = "Operating Time";
    this.anyahegelv = "Press Qualified";
    this.anyacishu = "Press Times";
    this.anyashendu = "Press Depth";
    this.chuiqishunxu = "Order";
    this.chuiqihegelv = "Blow Qualified";
    this.chuiqicishu = "Blow Times";
    this.chuiqiliang = "Blow Amount";
    this.xunhuancishu = "Cycle Times";
    this.CPR = "CPR";
    this.fen = "Score";
    this.heji = "Total";
    this.qingshurudangqianmima = "Please Enter The Current Password";
    this.qingshuruxinmima = "Please Enter The Original Password";
    this.qingzaicishuruxinmima = "Please Enter A New Password Again";
    this.kangrenCpr = "KangRen CPRV1.0";
    this.baocun = "Save";
    this.moxingweilianjie = "Model Not Connected";
    this.chenggongjiuhuo = "Success";
    this.weijiuhuo = "Faild";
    this.kaoshizhong = "Examing";
    this.huifangzhong = "Playback";
    this.daojishi = "Countdown";
    this.shifoujiuhuo = "Living";
    this.CprDefen = "Cpr Score";
    this.shi = "Yes";
    this.fou = "No";
    this.zongfen = "Score";
    this.chengjixiangqing = "Info";
    this.zhengquelv = "Right Qualified";
    this.pingyu = "Comment";
    this.xunhuan = "Cycle";
    this.jinru = "Enter";
    this.miao = "S";
    this.mimabunengweikong = "Password Cannot Be Empty";
    this.shanghaikangren = "ShangHai Kang Ren";
    this.weicaozuo = "Not Operating";
    this.hege = "Checked";
    this.xiananhouchui = "First Press";
    this.xianchuihouan = "First Blow";
    this.mimabuyizhi = "Password Inconsistency";
    this.chaoguo100 = "Total not more than 100 points";
    this.baocunchenggong = "Save Success";
    this.xiugaichenggong = "Password Modify Success";
    this.mima = "Password";
    this.bianji = "Edit";
    this.ci = "Times";
    this.fenzhong = "M";

    this.kaishiriqi = "Begin Date";
    this.jieshuriqi = "End Date";
    this.qingshuruxingming = "Please Enter Name";
    this.qingshurubanji = "Please Enter Class";
    this.kaoheleixing = "Exam Type";
    this.soushou = "Search";
    this.xingming = "Name";
    this.banji = "Class";
    this.fenshu = "Score";
    this.kaoheriqi = "Time";
    this.jinrushanchumoshi = "Del Mode";
    this.tuichushanchumoshi = "Exit Del Mode";
    this.shanchuchenggong = "Del Success";

    this.xiangmu = "Item";
    this.dayinchengji = "Print";
    this.huifangchengji = "Replay";
    this.banjibunengweikong = "Class Can not Be Empty";
    this.xingmingbunengweikong = "Name Can not Be Empty";
    this.xinfeifushucaozuojilu = "CPR Record";
    this.defen = "Score";
    this.chuiqibiaozhun = "Blow";
    this.anyabiaozhun = "Press";

    this.xianshi = "Max Time";
    this.keguanjieguo = "Objective";
    this.anyajieguo = "   Press";
    this.chuiqijieguo = "    Blow";
    this.haoshi="Take";
    this.zanting="Pause";
    this.jixu="Resume";
    this.zhunbeihuifangzhong="Ready to play... Please wait";

    this.dayinboxingtu="Oscillograph";
    this.chongqiApp="Please Restart App";
}
function Chinese(){
    this.lilunxuexi="理论学习";
    this.xunlianmoshi = "训练模式";
    this.kaohemoshi = "考核模式";
    this.shizhanmoshi = "实战模式";
    this.lishichengji = "历史成绩";
    this.qingshurupingyu = "请输入评语";
    this.anyapinlv = "按压频率";
    this.kaishi = "开始";
    this.tingzhi = "停止";
    this.fanhui = "返回";
    this.cprXiangQing = "CPR详情";
    this.baocunchengji = "保存成绩";
    this.pingugxiangqing = "评估详情";
    this.qingchuyiwu = "清除异物";
    this.maibojiancha = "脉搏检查";
    this.jijiuhujiao = "急救呼叫";
    this.huxijiancha = "呼吸检查";
    this.yishipanduan = "意识判别";
    this.hejicaozuo = "合计操作";
    this.chuiqi = "吹气";
    this.anya = "按压";
    this.zongshu = "总数";
    this.zhengque = "正确";
    this.cuowu = "错误";
    this.dangqiancaozuo = "当前操作";
    this.guoda = "过大";
    this.guoxiao = "过小";
    this.duochui = "多吹";
    this.shaochui = "少吹";
    this.chuiqijinwei = "吹气<br>进胃";
    this.qidaoweikai = "气道<br>未开";
    this.duoan = "多按";
    this.shaoan = "少按";
    this.pinlvcuowu = "频率<br>错误";
    this.huitanbuzu = "回弹<br>不足";
    this.anyaweizhi = "按压位置";
    this.pianshang = "偏上";
    this.pianxia = "偏下";
    this.pianzuo = "偏左";
    this.pianyou = "偏右";
    this.qingshuruguanliyuanmima = "管理员密码";
    this.queren = "确认";
    this.mimacuowu = "密码错误";
    this.caozuoshijian = "操作时间";
    this.anyahegelv = "按压合格率";
    this.anyacishu = "按压次数";
    this.anyashendu = "按压深度";
    this.chuiqishunxu = "吹气顺序";
    this.chuiqihegelv = "吹气合格率";
    this.chuiqicishu = "吹气次数";
    this.chuiqiliang = "吹气量";
    this.xunhuancishu = "循环次数";
    this.CPR = "CPR";
    this.fen = "分";
    this.heji = "合计";
    this.qingshurudangqianmima = "请输入当前密码";
    this.qingshuruxinmima = "请输入原密码";
    this.qingzaicishuruxinmima = "请再次输入新密码";
    this.kangrenCpr = "康人CPRV1.0";
    this.baocun = "保存";
    this.moxingweilianjie = "模型未连接";
    this.chenggongjiuhuo = "成功救活";
    this.weijiuhuo = "未救活";
    this.kaoshizhong = "考试中";
    this.huifangzhong = "回放中";
    this.daojishi = "倒计时";
    this.CprDefen = "Cpr得分";
    this.shifoujiuhuo = "是否救活";
    this.shi = "是";
    this.fou = "否";
    this.zongfen = "总分";
    this.chengjixiangqing = "成绩详情";
    this.zhengquelv = "正确率";
    this.pingyu = "评语";
    this.xunhuan = "循环";
    this.jinru = "进入";
    this.miao = "秒";
    this.mimabunengweikong = "密码不能为空";
    this.shanghaikangren = "上海康人";
    this.weicaozuo = "未操作";
    this.hege = "合格";
    this.xiananhouchui = "先按后吹";
    this.xianchuihouan = "先吹后按";
    this.mimabuyizhi = "密码不一致";

    this.chaoguo100 = "合计不能超过100分";
    this.baocunchenggong = "保存成功";
    this.xiugaichenggong = "密码修改成功";
    this.mima = "密码";
    this.bianji = "编辑";
    this.ci = "次";
    this.fenzhong = "分";


    this. kaishiriqi="开始日期";
    this. jieshuriqi = "结束日期";
    this. qingshuruxingming="请输入姓名";
    this. qingshurubanji="请输入班级";
    this. kaoheleixing="考核类型";
    this. soushou="搜索";
    this. xingming="姓名";
    this. banji="班级";
    this. fenshu="分数";
    this.kaoheriqi = "考核日期";
    this.jinrushanchumoshi = "进入删除模式";
    this.tuichushanchumoshi = "退出删除模式";
    this.shanchuchenggong = "删除成功";

    this.xiangmu = "项目";
    this.dayinboxingtu = "打印波形图";
    this.dayinchengji = "打印成绩";
    this.huifangchengji = "回放成绩";
    this.banjibunengweikong = "班级不能为空";
    this.xingmingbunengweikong = "姓名不能为空";
    this.xinfeifushucaozuojilu = "心肺复苏操作记录";
    this.defen = "得分";
    this.chuiqibiaozhun = "吹气标准";
    this.anyabiaozhun = "按压标准";
    this.xianshi = "限时";
    this.keguanjieguo = "客观成绩";
    this.anyajieguo = "按压结果";
    this.chuiqijieguo = "吹气结果";
    this.haoshi="耗时";
    this.zanting="暂停";
    this.jixu="继续";
    this.zhunbeihuifangzhong="准备回放中...请稍候";
    this.chongqiApp="请重启App";
}

