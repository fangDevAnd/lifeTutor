package com.lyc.hybird;

import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Context;
import android.content.SharedPreferences;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.widget.DatePicker;

import com.db4o.query.Predicate;
import com.kangren.cpr.AppConfig;
import com.kangren.cpr.CprType;
import com.kangren.cpr.MainActivity;
import com.kangren.cpr.entity.Score;
import com.kangren.cpr.entity.ScoreReceiveMessage;
import com.kangren.cpr.entity.Setting;
import com.kangren.cpr.entity.query.QueryScoreRecord;
import com.kangren.cpr.viewModel.ReturnDataViewModel;
import com.kangren.language.Chinese;
import com.kangren.language.English;
import com.kangren.language.Language;
import com.kangren.services.PrintsService;
import com.lyc.db4o.Db;
import com.lyc.ui.DataPickerEx;
import com.lyc.utils.DateUtil;
import com.lyc.utils.FileUtil;
import com.lyc.utils.JsonUtil;
import com.lyc.utils.LogUtil;

import java.util.Collections;
import java.util.List;


/**
 * @author Mohammed.Tell.Yes
 * @create time 2016.5.14
 *
 *
 * 由于 在API>16的系统来说，为了解决webview的远程代码执行漏洞
 *
 * 通过使用 @JavascriptInterface来进行远程函数访问接口的设置
 *
 *
 *
 * */
public class JSObject {

    private Score lastScore;

    /**
     * 返回的数据模型
     */
    private ReturnDataViewModel lastRes;
    /**
     * 时间选择器
     */
    private DataPickerEx datepick;

    private Context mContext;
    private WebView webview;
    private MainActivity mc;
    private Language lang;
    public JSObject(Context context){
        this.mContext = context;
        mc=(MainActivity) context;
        Setting setting=AppConfig.getInstance().db.getFirst(new Setting());
        if(setting.language==0)
            lang=new Chinese();
        else
            lang=new English();

    }
    public JSObject(Context context,WebView webview){
        this.mContext = context;
        this.webview=webview;
        mc=(MainActivity) context;

    }


    /**
     * 加载特定的JS
     * @param js
     */
    private void exceJs(final String js){
        mc.runOnUiThread(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                String es="javascript:"+js;
                webview.loadUrl(es);
            }
        });

    }
    /**
     * 工具类
     * */

    //弹出提示
    @JavascriptInterface
    public void showTip(String tip){
        mc.showMsg(tip);

    }

    /**
     * 进入训练界面
     */
    @JavascriptInterface
    public void goXunlianCpr(){
        mc.runOnUiThread(new Runnable() {

            @Override
            public void run() {
                mc.goCpr(CprType.xunlian);
            }
        });


    }

    /**
     *进入 实战 CPR环节
     */
    @JavascriptInterface
    public void goShiZhanCpr(){
        mc.runOnUiThread(new Runnable() {

            @Override
            public void run() {
                mc.goCpr(CprType.shizhan);
            }
        });


    }

    /**
     * 进入考核CPR环节
     */
    @JavascriptInterface
    public void goKaoHeCpr(){
        mc.runOnUiThread(new Runnable() {

            @Override
            public void run() {
                mc.goCpr(CprType.kaohe);
            }
        });


    }

    /**
     * 删除分数
     * @param id
     */
    @JavascriptInterface
    public void removeScore(String id){
        Db db=AppConfig.getInstance().db;
        Score score=new Score();
        score.id=id;
        db.del(score);
    }

    /**
     * 查询学院的分数记录
     * @param json
     */
    //查询学员评分列表页
    //queryScoreRecord
    @JavascriptInterface
    public void queryScoreRecord(String json){

        long tempendTime=-1;
        long tempstartTime=-1;
        final QueryScoreRecord  queryScoreRecord =JsonUtil.ToObject(json, QueryScoreRecord.class);
        if(queryScoreRecord.begin_date.indexOf("-")==-1){
            tempstartTime=0;
        }
        if(queryScoreRecord.end_date.indexOf("-")==-1){

            tempendTime=System.currentTimeMillis();
        }

        if(tempstartTime==-1)
            tempstartTime=DateUtil.parse(queryScoreRecord.begin_date+" 00:00:00", "yyyy-MM-dd hh:mm:ss").getTime();
        if(tempendTime==-1)
            tempendTime=DateUtil.parse(queryScoreRecord.end_date+" 23:59:59", "yyyy-MM-dd hh:mm:ss").getTime();
        final long startTime=tempstartTime;
        final long endTime=tempendTime;
        Db db=AppConfig.getInstance().db;
        lastRes=new ReturnDataViewModel();

        List<Score> datas=db.getByNQ(new Predicate<Score>() {

            @Override
            public boolean match(Score score) {
                // TODO Auto-generated method stub
                if(score.exam_data_long<startTime)
                    return false;
                if(score.exam_data_long>endTime)
                    return false;

                if(queryScoreRecord.exam_type!=""&&!score.exam_type.contains(queryScoreRecord.exam_type))
                    return false;

                if(queryScoreRecord.stu_class!=""&&!score.stu_class.contains(queryScoreRecord.stu_class))
                    return false;

                if(queryScoreRecord.stu_name!=""&&!score.stu_name.contains(queryScoreRecord.stu_name))
                    return false;
                return true;
            }
        });
        Collections.reverse(datas);

        lastRes.data=datas;
        String js="NativeCaller.endCallBack('queryScoreRecordCallbck')";
        exceJs(js);
    }

    /**
     * 查询分数记录的接口回调
     * @return
     */
    @JavascriptInterface
    public String queryScoreRecordCallbck(){

        String js=JsonUtil.ToJson(lastRes);
        return JsonUtil.ToJson(lastRes);
    }

    /**
     * 获得系统设置的相关信息
     * @return
     */
    @JavascriptInterface
    public String getSetting(){
        Setting setting=AppConfig.getInstance().db.getFirst(new Setting());
        if(setting==null)
            setting=new Setting();
//        String j=JsonUtil.ToJson(setting);
        return JsonUtil.ToJson(setting);
    }

    /**
     * 保存设置
     * @param json
     */
    @JavascriptInterface
    public void saveSetting(String json){
        Setting newSet=JsonUtil.ToObject(json, Setting.class);
        Db db=AppConfig.getInstance().db;
        Setting old=db.getFirst(new Setting());
        db.del(old);
        db.add(newSet);
    }

    /**
     * 获得系统的语言
     * @return
     */
    @JavascriptInterface
    public int getLanguage(){

        Db db=AppConfig.getInstance().db;
        Setting old=db.getFirst(new Setting());
        if(old.language==0)
            lang=new Chinese();
        else
            lang=new English();
        return old.language;
    }

    /**
     * 保存密码 ，其实就是设置新的密码
     * @param oldPwd
     * @param newPwd
     * @return
     */
    @JavascriptInterface
    public String savePwd(String oldPwd,String newPwd){

        Setting setting=AppConfig.getInstance().db.getFirst(new Setting());
        if(!setting.pwd.equals(oldPwd))
            return lang.yuanmimacuowu;
        setting.pwd=newPwd;
        AppConfig.getInstance().db.del(setting);
        AppConfig.getInstance().db.add(setting);
        return lang.xiugaichenggong;

    }
    //queryOprateRecord查询学员操作记录

    //详情页

    /**
     *
     * 查询操作记录
     *
     * @param json
     */
    @JavascriptInterface
    public void queryOprateRecord(String json){

        String id=JsonUtil.toMap(json).get("id");
        if(id!=null&&!id.isEmpty()){

            Score score=new Score();
            score.id=id;
            lastScore=AppConfig.getInstance().db.get(score);

        }
        else{
            lastScore=mc.lastScore;
        }

        String js="NativeCaller.endCallBack('queryOprateRecordCallback')";
        exceJs(js);
    }

    /**
     * 查询操作记录的回调
     * @return
     */
    @JavascriptInterface
    public String queryOprateRecordCallback(){
		/*ReturnDataViewModel res=new ReturnDataViewModel();
		Score or=new Score();
		or.name="lyc";
		or.className="22帮";
		OprateRecordTable opr1=new OprateRecordTable();
		OprateRecordTable opr2=new OprateRecordTable();
		OprateRecordTable opr3=new OprateRecordTable();
	    opr2.type=2;
	    opr1.type=1;
	    opr3.type=0;
	    opr3.pingyu="sdfsdfsd左的很好";
	    OprateRecordGroup group1=new OprateRecordGroup("","");
	    group1.name="测试1";
	    group1.count="0";
	    OprateRecordGroup group2=new OprateRecordGroup("","");
	    group2.name="测试1";
	    group2.count="5";
	    opr1.records=new OprateRecordGroup[]{group1};

	    opr2.records=new OprateRecordGroup[]{group2};
	    or.tables=new OprateRecordTable[]{
	    		opr1,
	    		opr2,
	    		opr3
	    };
	    res.data=or;
	    */
        String j= JsonUtil.ToJson(lastScore);
        return JsonUtil.ToJson(lastScore);

    }

    /**
     * 返回到主页面的操作
     *
     * @param id
     */
    @JavascriptInterface
    public void playBack(String id){
        Score score=new Score();
        score.id=id;
        ScoreReceiveMessage srm;
        if(mc.lastReceiveMessage!=null&& mc.lastReceiveMessage.id.equals(id)){
            srm=mc.lastReceiveMessage;
            score=mc.lastScore;
        }
        else{
            srm=new ScoreReceiveMessage(id);
            srm=AppConfig.getInstance().db.get(srm);
            score=AppConfig.getInstance().db.get(score);
        }
        LogUtil.WriteLog("idididid"+ srm.id);
        mc.playBack(score,srm);
    }

    /**
     * 保存 cpr的信息数据
     * @param json
     */
    @JavascriptInterface
    public void saveCpr(String json){
        Score score=JsonUtil.ToObject(json, Score.class);
        ScoreReceiveMessage srm=mc.lastReceiveMessage;

        //上传数据

        //	Client client=Client.getInstance();
        //if(!client.isConnected()){
        //	client.reconnect();

        //}

        //if(client.send(UploadHelper.stringToBytes(json,(byte)1))){
        //	score.isUpload=true;
        //}
        //Bitmap bitmap=BitmapFactory.decodeFile(score.path);
        //bitmap=BitmapUtil.rotate(bitmap, -90);
        //	client.send(UploadHelper.imageToBytes(bitmap, (byte)2));

        AppConfig.getInstance().db.add(score);

        AppConfig.getInstance().db.add(srm);
        //	LogUtil.WriteLog("idididid"+ mc.lastReceiveMessage.id);

    }

    /**
     * 打印CPR的数据
     * @param json
     */
    @JavascriptInterface
    public void printCpr(String json){
        if(!AppConfig.getInstance().PrintConnected){
            showTip("打印机未连接");
            return;
        }

        Score score=JsonUtil.ToObject(json, Score.class);
        PrintsService.getInstance().printScore(score);
        //	String js="NativeCaller.endCallBack('getModuleInfoCallback')";
        //exceJs(js);

    }

    /**
     * 打印CPR的数据
     * @param json
     */
    @JavascriptInterface
    public void printCprPic(String json){
        if(!AppConfig.getInstance().PrintConnected){
            showTip("打印机未连接");
            return;
        }

        Score score=JsonUtil.ToObject(json, Score.class);
        PrintsService.getInstance().printScorePic(score);
        //	String js="NativeCaller.endCallBack('getModuleInfoCallback')";
        //exceJs(js);

    }
    //获取模块介绍
    @JavascriptInterface
    public void getModuleInfo(){
        String js="NativeCaller.endCallBack('getModuleInfoCallback')";
        exceJs(js);
    }
    @JavascriptInterface
    public String getModuleInfoCallback(){
        return JsonUtil.ToJson(AppConfig.getInstance().moduleInfo);
    }
    @JavascriptInterface
    public String getIPandPort(){
        SharedPreferences sp = mContext.getSharedPreferences("config", mContext.MODE_PRIVATE);
        String ip = sp.getString("ip", "");
        String port = sp.getString("port", "");
        String serverUrl = "http://"+ ip +":"+ port;
        return serverUrl;

    }
    @JavascriptInterface
    public String getBaseFilePath(){
        return AppConfig.getInstance().KnowledgePath;
    }
    @JavascriptInterface
    public String getFiles(String path){
        String json=JsonUtil.ToJson(FileUtil.list(path));

        return json;
    }

    @JavascriptInterface
    public void openFile(String path){
        FileUtil.openFileBySystem(mc, path);
    }

    /**
     * 工具类 开启日期选择器
     * */

    //从主界面开启日期选择器
    @JavascriptInterface
    public void showDatePicker(){
        if(datepick==null)
            datepick=new DataPickerEx(this.mc,new OnDateSetListener() {

                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear,
                                      int dayOfMonth) {
                    // TODO Auto-generated method stub
                    datepick.selected_date=year+"-"+String.format("%02d",monthOfYear+1)+"-"+String.format("%02d",dayOfMonth);
                    String js="NativeCaller.endStIngCallBack('showDatePickerCallback')";
                    exceJs(js);
                }
            });

        datepick.show();
    }

    /**
     * 显示 日期选定的回调
     * @return
     */
    //得到日期选择器选定的日期字符串
    @JavascriptInterface
    public String showDatePickerCallback(){
        return datepick.selected_date;
    }

    /**
     * 业务逻辑类
     * */

    //获取操作结果对象JSON
    @JavascriptInterface
    public String getOprateResult(){
        return ((MainActivity)mContext).oprate_obj;
    }

}
