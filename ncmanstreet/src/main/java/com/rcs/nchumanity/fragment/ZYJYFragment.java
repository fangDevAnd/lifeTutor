package com.rcs.nchumanity.fragment;

import android.Manifest;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.google.gson.Gson;
import com.rcs.nchumanity.R;
import com.rcs.nchumanity.entity.BasicResponse;
import com.rcs.nchumanity.entity.NetConnectionUrl;
import com.rcs.nchumanity.entity.PersistenceData;
import com.rcs.nchumanity.entity.model.EmergencyInfo;
import com.rcs.nchumanity.service.JG.MyReceiver;
import com.rcs.nchumanity.service.JG_server.JGServer_sendNotification;
import com.rcs.nchumanity.tool.ActivityStackManager;
import com.rcs.nchumanity.tool.DateProce;
import com.rcs.nchumanity.tool.JsonDataParse;
import com.rcs.nchumanity.tool.LBSallocation;
import com.rcs.nchumanity.tool.Tool;
import com.rcs.nchumanity.ul.MainActivity;
import com.rcs.nchumanity.ul.basicMap.BasicMapChangeActivity;
import com.rcs.nchumanity.ul.basicMap.ILocaPoint;
import com.rcs.nchumanity.ul.basicMap.LocalPoint;
import com.rcs.nchumanity.ul.list.EmergencyComplexListActivity;
import com.rcs.nchumanity.view.CommandBar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.OnClick;
import okhttp3.Response;

/**
 * 志愿救援的信息的显示
 */
public class ZYJYFragment extends BasicResponseProcessHandleFragment {


    private static final String TAG = "test";
    @BindView(R.id.toolbar)
    public CommandBar commandBar;

    private String title = "救护员救援";

    @BindView(R.id.sendHelp)
    ImageButton sendHelp;

    @BindView(R.id.rootHelpView)
    LinearLayout rootHelpView;


    public static final String ACTION_HELP_DATA = "com.fang.getHelpData.ACTION";


    @Override
    protected int getCreateView() {
        return R.layout.zyjy;
    }


    private HelpDataBroadcastReceiver helpDataBroadcastReceiver;

    @Override
    protected void onViewCreated(View view, Bundle savedInstanceState, boolean isViewCreated) {
        super.onViewCreated(view, savedInstanceState, isViewCreated);
        commandBar.setTitle(title);
        helpDataBroadcastReceiver = new HelpDataBroadcastReceiver();

        IntentFilter intentFilter = new IntentFilter(getActivity().getPackageName());

        getActivity().registerReceiver(helpDataBroadcastReceiver, intentFilter);


        ActivityStackManager asm = ActivityStackManager.getInstance(getContext());

        String packName = getContext().getPackageName() + File.separator + MainActivity.class.getName();

        Log.d(TAG, "onViewCreated: " + packName);

        loadDataGet(NetConnectionUrl.selectInfo, "selectInfo");


        if (getMyActivity() instanceof MainActivity) {
            commandBar.hiddenBack();
        }

        commandBar.setBackGroundColor1(android.R.color.transparent);
        commandBar.setMenu(R.drawable.ic_30, (view1 -> {

            Tool.startActivity(getActivity(), EmergencyComplexListActivity.class);


        }));
    }

    public void setToolbarOffsetY(int dimensionPixel) {
        commandBar.setTranslationY(dimensionPixel);
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
    }

    private EmergencyInfo currentClickEmergencyInfo;
    private LocalPoint currentHelpLocation;


    @Override
    protected void responseDataSuccess(String what, String backData, Response response, BasicResponse... br) throws Exception {
        super.responseDataSuccess(what, backData, response, br);

        if (what.equals("selectInfo")) {
            try {
                List<EmergencyInfo> emergencyInfos1 = new ArrayList<>();
                List<EmergencyInfo> emergencyInfos = JsonDataParse.parseEmergencyData(backData);

                for (EmergencyInfo emergencyInfo : emergencyInfos) {

                    if (emergencyInfo.getCreateTime().after(getBeforeDay())) {
                        emergencyInfos1.add(emergencyInfo);
                    }
                }


                rootHelpView.removeAllViews();

                for (EmergencyInfo emergencyInfo : emergencyInfos1) {

                    String title = emergencyInfo.getTitle();
                    String content = emergencyInfo.getContent();
                    double lantitute = emergencyInfo.getLatitude();
                    Date createDate = emergencyInfo.getCreateTime();
                    int emerId = emergencyInfo.getEmerId();
                    double longitude = emergencyInfo.getLongitude();
                    String mobile = emergencyInfo.getMobilePhone();

                    View view = LayoutInflater.from(getActivity()).inflate(R.layout.item_help_emergy, null);
                    TextView titleS = view.findViewById(R.id.title);
                    titleS.setText(title);
                    TextView des = view.findViewById(R.id.des);
                    des.setText(DateProce.formatDate(createDate) + "\t\t" + content);

                    Button join = view.findViewById(R.id.join);
                    join.setTag(emergencyInfo);
                    view.findViewById(R.id.join).setOnClickListener((v) -> {
                        /**
                         * 重置aed数据
                         */
                        resetInfo();
                        //进入地图显示界面
                        currentClickEmergencyInfo = (EmergencyInfo) v.getTag();
                        currentHelpLocation =
                                new LocalPoint(currentClickEmergencyInfo.getLongitude(),
                                        currentClickEmergencyInfo.getLatitude(),
                                        "用户" + mobile, "", content);

                        if (mobile.equals(PersistenceData.getPhoneNumber(getContext()))) {

                            new AlertDialog.Builder(getContext())
                                    .setTitle("提示")
                                    .setMessage("自己不能响应自己")
                                    .setPositiveButton("确定", (dialog, which) -> {
                                        dialog.dismiss();
                                    }).create().show();
                            resetInfo();
                            return;
                        }


                        currentHelpLocation.isHelp = true;

                        LBSallocation.startLocationWithFragment(this, JYPXFragment.requestPermissionCode_DW);

                    });
                    rootHelpView.addView(view);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (what.equals("aedInfo")) {
            try {
                ArrayList<ILocaPoint> locaPoints = JsonDataParse.getAEDLocations(backData);
                locaPoints.add(0, currentHelpLocation);
                Bundle bundle = new Bundle();
                bundle.putSerializable(BasicMapChangeActivity.DATA, locaPoints);
                Tool.startActivity(getContext(), BasicMapChangeActivity.class, bundle);
                //提交响应数据到数据库

            } catch (Exception e) {
                e.printStackTrace();
            }

        } else if (what.equals("addResEmergency")) {
            loadDataGet(NetConnectionUrl.getAEDList, "aedInfo");
        }

    }


    /**
     * 重置aed的数据
     */
    private void resetInfo() {
        currentClickEmergencyInfo = null;
        currentHelpLocation = null;
    }


    @OnClick(R.id.sendHelp)
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.sendHelp:

                Intent intent = new Intent();
                intent.setAction(getActivity().getPackageName());
                intent.putExtra(MyReceiver.FUNC, MyReceiver.FUN_SEND_HELP);
                intent.putExtra(MyReceiver.MAIN, getActivity().getClass().getSimpleName());
                getActivity().sendBroadcast(intent);

                break;
        }
    }


    /**
     * 接收到极光推送返回的广播的回调、
     * ，实现对界面数据的刷新
     */
    public class HelpDataBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {

            Log.d("test", "onReceive: -----------接受到广播通知");

            String func = intent.getStringExtra(MyReceiver.FUNC);

            if (func.equals(MyReceiver.FUN_NOTIFICATION)) {
                loadDataGet(NetConnectionUrl.selectInfo, "selectInfo");
            }
        }
    }


    public static Date getBeforeDay() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -1); //得到前一天
        Date date = calendar.getTime();
        return date;
    }


    @Override
    public void permissionSuccess(int requestCode) {
        super.permissionSuccess(requestCode);
        if (requestCode == JYPXFragment.requestPermissionCode_DW) {
            LBSallocation.getCurrentLocation(getContext(), new BDAbstractLocationListener() {
                @Override
                public void onReceiveLocation(BDLocation bdLocation) {
                    //aed分布的实现

                    /**
                     * 提交响应数据到数据库
                     */
                    Map<String, String> param = new HashMap<>();
                    param.put("emerId", currentClickEmergencyInfo.getEmerId() + "");
                    param.put("latitude", bdLocation.getLatitude() + "");
                    param.put("longitude", bdLocation.getLongitude() + "");
                    param.put("resMobilePhone", currentClickEmergencyInfo.getMobilePhone());
                    param.put("mobilephone", PersistenceData.getPhoneNumber(getContext()));
                    param.put("userId", PersistenceData.getUserId(getContext()));

                    loadDataPost(NetConnectionUrl.addResEmergency, "addResEmergency", param);

                }
            });
        }
    }

    @Override
    public void onDestroy() {
        if (helpDataBroadcastReceiver != null) {
            getActivity().unregisterReceiver(helpDataBroadcastReceiver);
        }
        super.onDestroy();
    }
}
