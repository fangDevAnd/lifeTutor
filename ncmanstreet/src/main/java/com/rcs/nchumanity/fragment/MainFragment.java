package com.rcs.nchumanity.fragment;

import android.Manifest;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.DrawableRes;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.rcs.nchumanity.R;
import com.rcs.nchumanity.entity.BasicResponse;
import com.rcs.nchumanity.entity.NetConnectionUrl;
import com.rcs.nchumanity.entity.PersistenceData;
import com.rcs.nchumanity.entity.complexModel.ComplexModelSet;
import com.rcs.nchumanity.entity.model.EmergencyInfo;
import com.rcs.nchumanity.entity.model.SpecificInfo;
import com.rcs.nchumanity.entity.model.SpecificInfoClassification;
import com.rcs.nchumanity.service.JG.MyReceiver;
import com.rcs.nchumanity.service.JG_server.JGServer_sendNotification;
import com.rcs.nchumanity.tool.DateProce;
import com.rcs.nchumanity.tool.DensityConvertUtil;
import com.rcs.nchumanity.tool.JsonDataParse;
import com.rcs.nchumanity.tool.LBSallocation;
import com.rcs.nchumanity.tool.StringTool;
import com.rcs.nchumanity.tool.Tool;
import com.rcs.nchumanity.ul.AmbulanceRescueActivity;
import com.rcs.nchumanity.ul.BasicResponseProcessHandleActivity;
import com.rcs.nchumanity.ul.MainActivity;
import com.rcs.nchumanity.ul.WebLoadActivity;
import com.rcs.nchumanity.ul.basicMap.BasicMapChangeActivity;
import com.rcs.nchumanity.ul.basicMap.ILocaPoint;
import com.rcs.nchumanity.ul.basicMap.LocalPoint;
import com.rcs.nchumanity.ul.detail.SpecificInfoComplexListDetailActivity;
import com.rcs.nchumanity.ul.list.ComplexListActivity;
import com.rcs.nchumanity.ul.list.SpecificInfoComplexListActivity;
import com.rcs.nchumanity.ul.TrainStepActivity;
import com.rcs.nchumanity.ul.basicMap.BasicMapActivity;
import com.rcs.nchumanity.view.BannerFlip;
import com.rcs.nchumanity.view.BasicItem;
import com.rcs.nchumanity.view.PercentLinearLayout;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Array;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Response;

/**
 * 首页的界面的显示
 */
public class MainFragment extends BasicResponseProcessHandleFragment {


    public static int requestPermissionCode_QJ = 34;

    @Override
    protected int getCreateView() {
        return R.layout.fragment_main;
    }


    @BindView(R.id.banner)
    BannerFlip banner;

    @BindView(R.id.funcArea)
    LinearLayout funcArea;


    @BindView(R.id.basicInfoArea)
    LinearLayout basicInfoArea;

    @BindView(R.id.total)
    public TextView total;


    private int defBannerHeight = 200;


    /**
     * url映射
     */
    private static Map<String, String> urlMap = new HashMap();

    static {
        urlMap.put("急救知识", NetConnectionUrl.getKnowledge);
        urlMap.put("养老护理知识", NetConnectionUrl.getOldCare);
        urlMap.put("无偿献血", NetConnectionUrl.getBloodDonation);
        urlMap.put("器官遗体捐献", NetConnectionUrl.getOrganBodyDonation);
        urlMap.put("爱心捐献", NetConnectionUrl.getLoveDonation);
        urlMap.put("造血干细胞捐献", NetConnectionUrl.getMarrowDonation);
    }


    /**
     * 定义的是每一行的列的数量
     */
    private static final int COLUMN_LENGTH = 4;

    private int delay = 3000;

    private int rowWidth;

    private static final int marginBottom = 5;

    private HelpCountBroadcastReceiver helpCountBroadcastReceiver;


    @Override
    protected void onViewCreated(View view, Bundle savedInstanceState, boolean isViewCreated) {
        banner.setBannerHeight(DensityConvertUtil.dpi2px(getContext(), defBannerHeight));
        rowWidth = (int) (Tool.getScreenDimension(getContext())[0] / 10 * 9.6);
        helpCountBroadcastReceiver = new HelpCountBroadcastReceiver();
        IntentFilter intentFilter = new IntentFilter(getActivity().getPackageName());
        getActivity().registerReceiver(helpCountBroadcastReceiver, intentFilter);

        /**
         * 加载求救的数量
         */

        loadDataGetForForce(NetConnectionUrl.selectInfo, "selectInfo");

    }


    private PercentLinearLayout cacheView() {
        return (PercentLinearLayout) LayoutInflater.from(getContext()).inflate(R.layout.include_funclist, null);
    }


    /**
     * 动态获得后端的功能列表
     * <p>
     * 需要传递的参数是图标，和对应的文字
     * 传递的是一组数据
     */
    public void daymicFuncList(List<SpecificInfo> iconList) {

        for (SpecificInfo specificInfo : iconList) {
            /**
             * 算法实现
             * 1.判断funcArea的child的个数，代表  的是每一行数据
             * 2.判断每一行child的个数，如果child<4 就去添加一个子节点，如果=4 就去创建新的行
             */


            LinearLayout lastIndexRow = (LinearLayout)
                    (funcArea.getChildCount() == 1 ?
                            funcArea.getChildAt(0) : funcArea.getChildAt(funcArea.getChildCount() - 1));


            //添加元素到ly
            /**
             * 1.PercentLinearLayout
             *    ---PercentFrameLayout
             *          ---ImageButton
             *    ---TextView
             *
             */
            PercentLinearLayout ply = addFuncItem(specificInfo.getIcon(), specificInfo.getTitle());

            ImageButton imageButton = ply.findViewById(R.id.menuIcon);
            imageButton.setTag(specificInfo);
            imageButton.setOnClickListener((v) -> {
                //对于新添加的view我们设置点击事件
                String title = ((SpecificInfo) v.getTag()).getTitle();

                if (title.equals("查看更多")) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext())
                            .setTitle("提示")
                            .setMessage("更多功能请期待！")
                            .setPositiveButton("确定", ((dialog, which) -> {
                                dialog.dismiss();
                            }));
                    builder.create().show();
                    return;
                }

                String url = urlMap.get(title);

                Log.d("test", "daymicFuncList:==== " + url);

                if (url == null) {
                    throw new RuntimeException("URL not match");
                }

                Bundle bundle = new Bundle();
                bundle.putString(SpecificInfoComplexListActivity.URL, url);
                bundle.putString(SpecificInfoComplexListActivity.CLASS_NAME, title);
                Tool.startActivity(getContext(), SpecificInfoComplexListActivity.class, bundle);
            });


            ply.setOnClickListener((v) -> {

            });


            if (lastIndexRow.getChildCount() == COLUMN_LENGTH) {
                //创建新的行

                LinearLayout ly = new PercentLinearLayout(getContext());
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(rowWidth, -2);
                lp.bottomMargin = DensityConvertUtil.dp2px(getContext(), marginBottom);
                ly.setBackgroundColor(Color.parseColor("#ffffff"));
                lp.gravity = Gravity.CENTER_HORIZONTAL;
                ly.setLayoutParams(lp);

                ly.addView(ply);
                //将行添加到funcArea中
                funcArea.addView(ly);

            } else {
                //添加元素到lastIndexRow
                lastIndexRow.addView(ply);
            }

        }
    }


    private PercentLinearLayout addFuncItem(String iconUrl, String text) {
        PercentLinearLayout ply = cacheView();
        ply.setLayoutParams(new LinearLayout.LayoutParams(rowWidth / 4, -2));

        Glide.with(getContext()).load(iconUrl).into((ImageButton) ply.findViewById(R.id.menuIcon));

        ((TextView) ply.findViewById(R.id.iconText)).setText(text);
        return ply;
    }


    /**
     * 功能区的其他3个按钮
     */
    @OnClick({R.id.jhypx, R.id.aedfb, R.id.jhyjy, R.id.hjjhy})
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.jhypx:

                Tool.startActivity(getContext(), TrainStepActivity.class);

                break;

            case R.id.aedfb:

                loadDataGet(NetConnectionUrl.getAEDList, "AEDList");

                break;

            case R.id.jhyjy:

                Intent intent = new Intent();
                intent.setAction(getActivity().getPackageName());
                intent.putExtra(MyReceiver.FUNC, MyReceiver.FUN_CLICK_TIP);
                getActivity().sendBroadcast(intent);

                Tool.startActivity(getContext(),
                        AmbulanceRescueActivity.class
                );

                break;

            case R.id.hjjhy:

                helpMe();


                //直接发送激光推送
                break;
        }
    }


    public void helpMe() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext())
                .setTitle("警告")
                .setMessage("确定发出求救？")
                .setPositiveButton("确定", ((dialog, which) -> {

                    LBSallocation.startLocationWithFragment(this, requestPermissionCode_QJ);

                    dialog.dismiss();
                })).setNegativeButton("按错了", ((dialog, which) -> {
                    dialog.cancel();
                }));
        builder.create().show();

    }


    /**
     * 对  返回为500
     * 进行重写  不要在里面调用父级的实现
     *
     * @param what
     * @param br
     */
    @Override
    protected void responseWith500(String what, BasicResponse br) {
        /**
         * 如果 求解信息加载失败 ，依旧去加载 首页的数据
         */
        if (what.equals("selectInfo")) {
            if (!broadCallback) {
                /**
                 * 加载首页的显示
                 */
                loadDataGetForForce(NetConnectionUrl.getIndexInfo, "pageIndex");
            }
        }
    }

    @Override
    protected void responseDataSuccess(String what, String backData, Response response, BasicResponse... br) throws Exception {
        super.responseDataSuccess(what, backData, response, br);
        if (what.equals("AEDList")) {
            ArrayList<ILocaPoint> locaPoints = JsonDataParse.getAEDLocations(backData);
            Bundle bundle = new Bundle();
            bundle.putSerializable(BasicMapChangeActivity.DATA, locaPoints);
            Tool.startActivity(getContext(), BasicMapChangeActivity.class, bundle);
        } else if (what.equals("pageIndex")) {
            JSONObject brJ = new JSONObject(backData);
            JSONObject dataObj = brJ.getJSONObject("object");

            JSONArray bannerList = dataObj.getJSONArray("specificPictureList");

            parseBannerData(bannerList);

            JSONArray iconList = dataObj.getJSONArray("specificInfoClassificationList");

            parseIconListData(iconList);

            JSONArray hszNews = dataObj.getJSONArray("aboutRedCrossList");

            JSONArray zxNews = dataObj.getJSONArray("newsList");

            parseNews(hszNews, zxNews);

        } else if (what.equals("selectInfo")) {

            /**
             *
             */
            if (!broadCallback) {
                /**
                 * 加载首页的显示
                 */
                loadDataGetForForce(NetConnectionUrl.getIndexInfo, "pageIndex");
            }

            /**
             * "code": "200",
             *   "msg": "查询成功",
             *   "data": {
             *     "total": 3,
             */

            JSONObject object = new JSONObject(backData);

            totalNoti = object.getJSONObject("data").getInt("total");

            if (totalNoti > 0) {
                int len = 0;
                len = PersistenceData.getHelpNumber(getActivity());
                if (totalNoti - len > 0) {
                    total.setVisibility(View.VISIBLE);
                    total.setText((totalNoti - len) + "");
                }
            }
        } else if (what.equals("addInfo")) {


            /**
             * 添加addInfo 成功后，才去发送 推送信息
             *
             * addINfo 很可能返回的 401状态吗 代表的用户没有登录
             *
             *  如果没有登录使用父级的默认实现  清除缓存 ---->进行登录操作
             *
             */
            JGServer_sendNotification.sendPushNoti(content);

            /**
             * 清空消息体
             */
            content = null;


            /**
             * 添加求救信息的回调的实现
             */
            Log.d("test", "onSucess:" + backData);
        }
    }

    private boolean broadCallback;

    private int totalNoti;

    /**
     * 解析图标
     * "typeId": 2,
     * "title": "国内国际要闻",
     * "icon": null,
     * "parent": "首页数据",
     * "isDelete": false,
     * "remark": null
     * <p>
     * 解析 拿到 捐款捐献 和 培训相关
     *
     * @param iconList
     */
    private void parseIconListData(JSONArray iconList) throws JSONException {

        List<SpecificInfo> specificInfos = JsonDataParse.parseIconListData(iconList);
        daymicFuncList(specificInfos);
    }

    /**
     * "id": 10,
     * "specificNo": null,
     * "title": "banner",
     * "url": "https://ncrd2019.oss-cn-shenzhen.aliyuncs.com/specificPic/1-130513125246400.jpg?Expires=1565597306&OSSAccessKeyId=TMP.hVJ9P4c7YcK26gN4zuafY8AYm8JQP9nrV4taHn9JTUBXf2RiZq4dmwEgKNwCfbwBqmbdYJtbofpKEasyZ5zzAYc27TtfAKJ2kboQcedCUapWvjjQbTFMHGteGauZ9C.tmp&Signature=E6Gzj%2FrH9tDz7GP3od1KpMNHVAY%3D",
     * "isDelete": false,
     * "remark": null
     *
     * @param bannerList
     */
    private void parseBannerData(JSONArray bannerList) throws JSONException {

        String[] bannerS = new String[bannerList.length()];
        for (int i = 0; i < bannerList.length(); i++) {
            bannerS[i] = bannerList.getJSONObject(i).getString("url");
        }
        banner.setImageUrl(Arrays.asList(bannerS));
        banner.startAutoRoll(delay);
    }


    private void parseNews(JSONArray hszNews, JSONArray zxNews) throws JSONException {


        List<SpecificInfo> one1 = JsonDataParse.parseNewItem(hszNews);
        List<SpecificInfo> two1 = JsonDataParse.parseNewItem(zxNews);
        ComplexModelSet.M_speinf_speinfCla mSpeinfSpeinfCla = new ComplexModelSet.M_speinf_speinfCla("关于红十字", one1);

        BasicItem basicItem = new BasicItem(getContext());
        basicItem.setAllSet((v) -> {

            if (v.getId() == R.id.more) {

                String title = (String) v.getTag();

                Bundle bundle = new Bundle();
                bundle.putString(SpecificInfoComplexListActivity.URL, NetConnectionUrl.getAboutRedCross);
                bundle.putString(SpecificInfoComplexListActivity.CLASS_NAME, title);
                Tool.startActivity(getContext(), SpecificInfoComplexListActivity.class, bundle);
            } else {

                //列表数据的点击
                Bundle bundle = new Bundle();
                SpecificInfo specificInfo = (SpecificInfo) v.getTag();

                if (Tool.isUrl(specificInfo.getContent())) {


                    bundle.putString(WebLoadActivity.TITLE,specificInfo.getTitle());
                    bundle.putString(WebLoadActivity.URL,specificInfo.getContent());
                    Tool.startActivity(getContext(),WebLoadActivity.class,bundle);

                } else {
                    bundle.putSerializable(SpecificInfoComplexListDetailActivity.DATA, specificInfo);
                    Tool.startActivity(getContext(), SpecificInfoComplexListDetailActivity.class, bundle);
                }
            }
        }, mSpeinfSpeinfCla);

        basicInfoArea.addView(basicItem);


        ComplexModelSet.M_speinf_speinfCla mSpeinfSpeinfCla1 = new ComplexModelSet.M_speinf_speinfCla("资讯", two1);
        BasicItem basicItem1 = new BasicItem(getContext());
        basicItem1.setAllSet((v) -> {

            if (v.getId() == R.id.more) {

                String title = (String) v.getTag();

                Bundle bundle = new Bundle();
                bundle.putString(SpecificInfoComplexListActivity.CLASS_NAME, title);
                bundle.putString(SpecificInfoComplexListActivity.URL, NetConnectionUrl.getNews);
                Tool.startActivity(getContext(), SpecificInfoComplexListActivity.class, bundle);
            } else {

                //列表数据的点击
                Bundle bundle = new Bundle();
                SpecificInfo specificInfo = (SpecificInfo) v.getTag();

                if (Tool.isUrl(specificInfo.getContent())) {

                    bundle.putString(WebLoadActivity.TITLE, specificInfo.getTitle());
                    bundle.putString(WebLoadActivity.URL, specificInfo.getContent());
                    Tool.startActivity(getContext(), WebLoadActivity.class, bundle);

                } else {
                    bundle.putSerializable(SpecificInfoComplexListDetailActivity.DATA, specificInfo);
                    Tool.startActivity(getContext(), SpecificInfoComplexListDetailActivity.class, bundle);
                }
            }

        }, mSpeinfSpeinfCla1);

        basicInfoArea.addView(basicItem1);


    }


    /**
     * 注册救护的广播接受者
     */
    public class HelpCountBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {

            Log.d("test", "onReceive: ====拉去数据");

            String func = intent.getStringExtra(MyReceiver.FUNC);

            if (func.equals(MyReceiver.FUN_NOTIFICATION)) {
                broadCallback = true;
                /**
                 * 再次拉取数据
                 */
                loadDataGet(NetConnectionUrl.selectInfo, "selectInfo");
            } else if (func.equals(MyReceiver.FUN_CLICK_TIP)) {
                //用来是实现对 total的处理
                total.setVisibility(View.INVISIBLE);
                PersistenceData.setClicked(getContext(), true);
                PersistenceData.setHelpNumber(getActivity(), totalNoti);
            } else if (func.equals(MyReceiver.FUN_SEND_HELP)) {
                String main = intent.getStringExtra(MyReceiver.MAIN);

                /**
                 * 代表使用的是主页面的回调
                 */
                if (main.equals(MainActivity.class.getSimpleName())) {
                    helpMe();
                }
            }
        }
    }


    @Override
    public void onDestroy() {
        if (helpCountBroadcastReceiver != null) {
            getActivity().unregisterReceiver(helpCountBroadcastReceiver);
        }
        /**
         * 重置广播回调的值
         */
        broadCallback = false;
        super.onDestroy();
    }


    /**
     * 代表的是发送极光推送的
     * 内容
     */
    private String content;

    @Override
    public void permissionSuccess(int requestCode) {
        super.permissionSuccess(requestCode);
        if (requestCode == requestPermissionCode_QJ) {


            LBSallocation.getCurrentLocation(getContext(), new BDAbstractLocationListener() {
                @Override
                public void onReceiveLocation(BDLocation bdLocation) {
                    String latitute = bdLocation.getLatitude() + "";
                    String longitute = bdLocation.getLongitude() + "";
                    Map<String, String> param = new HashMap<>();

                    String phone = PersistenceData.getPhoneNumber(getContext());

                    String city = bdLocation.getCity();
                    String district = bdLocation.getDistrict();
                    String street = bdLocation.getStreet();
                    content = "用户 " + phone + " 在" + city +
                            district + street + "求救";

                    Log.d("test", "onReceiveLocation: " + latitute + longitute + city + "  \t" + district + " " + street);
                    String content1 = "用户在" + city +
                            district + street + "求救";
                    param.put("content", content1);
                    param.put("latitude", latitute);
                    param.put("longitude", longitute);
                    param.put("mobilePhone", phone);
                    param.put("readCount", "0");
                    param.put("title", district + " " + phone + " 求救");
                    param.put("userId", PersistenceData.getUserId(getActivity()));

                    loadDataPostForce(NetConnectionUrl.addInfo, "addInfo", param);
                }
            });

        }
    }


}
