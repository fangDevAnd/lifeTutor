package com.rcs.nchumanity.ul;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.rcs.nchumanity.R;
import com.rcs.nchumanity.entity.BasicResponse;
import com.rcs.nchumanity.entity.NetConnectionUrl;
import com.rcs.nchumanity.entity.PersistenceData;
import com.rcs.nchumanity.fragment.MainFragment;
import com.rcs.nchumanity.fragment.ZYJYFragment;
import com.rcs.nchumanity.service.JG.MyReceiver;
import com.rcs.nchumanity.service.JG_server.JGServer_sendNotification;
import com.rcs.nchumanity.tool.LBSallocation;

import java.util.HashMap;
import java.util.Map;

import okhttp3.Response;

public class AmbulanceRescueActivity extends BasicResponseProcessHandleActivity {

    private static final String TAG = "test";

    int statuBarHeight = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        WindowManager.LayoutParams mParams;

        mParams = new WindowManager.LayoutParams();
        mParams.format = PixelFormat.TRANSLUCENT;
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            mParams.flags = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;  //设置状态栏透明

            int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
            if (resourceId > 0) {
                statuBarHeight = getResources().getDimensionPixelSize(resourceId);
            }
        }
        getWindow().setAttributes(mParams);

        /**
         * 上面实现的是透明的状态栏，实现了很多的方案，发现上面的实现
         * 是实现的最好的，之前自己实现的都有问题
         */

        setContentView(R.layout.activity_ambulance_rescue);

        IntentFilter intentFilter = new IntentFilter(getPackageName());

        registerReceiver(new MyBroadcastReceiver(), intentFilter);

        ZYJYFragment fragment =
                (ZYJYFragment) getSupportFragmentManager()
                        .findFragmentById(R.id.zyjyFragment);
        fragment.setToolbarOffsetY(statuBarHeight);
    }


    /**
     * 广播接受者
     */
    public class MyBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {

            String func = intent.getStringExtra(MyReceiver.FUNC);

            if (func.equals(MyReceiver.FUN_SEND_HELP)) {
                String main = intent.getStringExtra(MyReceiver.MAIN);
                if (main.equals(AmbulanceRescueActivity.class.getSimpleName())) {
                    helpMe();
                }
            }
        }
    }


    public void helpMe() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle("警告")
                .setMessage("确定发出求救？")
                .setPositiveButton("确定", ((dialog, which) -> {

                    LBSallocation.startLocationWithActivity(this, MainFragment.requestPermissionCode_QJ);

                    dialog.dismiss();
                })).setNegativeButton("按错了", ((dialog, which) -> {
                    dialog.cancel();
                }));
        builder.create().show();

    }


    private String content;

    @Override
    public void permissionSuccess(int requestCode) {
        super.permissionSuccess(requestCode);
        if (requestCode == MainFragment.requestPermissionCode_QJ) {


            LBSallocation.getCurrentLocation(this, new BDAbstractLocationListener() {
                @Override
                public void onReceiveLocation(BDLocation bdLocation) {
                    String latitute = bdLocation.getLatitude() + "";
                    String longitute = bdLocation.getLongitude() + "";
                    Map<String, String> param = new HashMap<>();

                    String phone = PersistenceData.getPhoneNumber(AmbulanceRescueActivity.this);

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
                    param.put("userId", PersistenceData.getUserId(AmbulanceRescueActivity.this));
                    loadDataPostForce(NetConnectionUrl.addInfo, "addInfo", param);
                }
            });
        }
    }


    @Override
    protected void responseDataSuccess(String what, String backData, Response response, BasicResponse... br) throws Exception {
        super.responseDataSuccess(what, backData, response, br);

        if (what.equals("addInfo")) {

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
}
