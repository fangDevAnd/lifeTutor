package com.xiaofangfang.rice2_verssion;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.xiaofangfang.rice2_verssion.application.MyApplication;
import com.xiaofangfang.rice2_verssion.model.City;
import com.xiaofangfang.rice2_verssion.network.NetRequest;
import com.xiaofangfang.rice2_verssion.tool.LoadProgress;
import com.xiaofangfang.rice2_verssion.tool.UiThread;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

import static com.xiaofangfang.rice2_verssion.tool.SystemSet.DEFAULT_USER_ID;
import static com.xiaofangfang.rice2_verssion.tool.SystemSet.DEF_LOGIN_STATUS;
import static com.xiaofangfang.rice2_verssion.tool.SystemSet.LOGIN_STATUS;
import static com.xiaofangfang.rice2_verssion.tool.SystemSet.USER_ID;


/**
 * 这个类的作用是保存一些全局的共享的数据
 */
public abstract class ParentActivity extends AppCompatActivity {

    public static City city;


    protected String[] permissionas = {

            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.SEND_SMS

    };

    private final String TAG = "MPermissions";
    protected int REQUEST_CODE_PERMISSION = 0x00099;


    public static List<Activity> activities = new ArrayList();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activities.add(this);
        initView();

//        Looger.currentLevel = -1;//修改为正式版本，不会在打印log

    }


    @Override
    public Resources getResources() {
        Resources res = super.getResources();
        Configuration config = new Configuration();
        config.setToDefaults();
        res.updateConfiguration(config, res.getDisplayMetrics());
        return res;
    }


    public abstract void initView();


    /**
     * 请求权限
     *
     * @param permissions 请求的权限
     * @param requestCode 请求权限的请求码
     */
    public void requestPermission(String[] permissions, int requestCode) {
        this.REQUEST_CODE_PERMISSION = requestCode;
        if (checkPermissions(permissions)) {
            permissionSuccess(REQUEST_CODE_PERMISSION);
        } else {
            List<String> needPermissions = getDeniedPermissions(permissions);
            ActivityCompat.requestPermissions(this, needPermissions.toArray(new String[needPermissions.size()]), REQUEST_CODE_PERMISSION);
        }
    }


    /**
     * 获取权限成功
     * 我们只要从新覆写这个方法,就能实现当我们请求权限成功后执行的操作
     * 并不需要我门去重写
     * {@link #onRequestPermissionsResult(int, String[], int[])}的实现
     *
     * @param requestCode
     */
    public void permissionSuccess(int requestCode) {
        Log.e("------", "获取权限成功=" + requestCode);

    }


    /**
     * 检测所有的权限是否都已授权
     *
     * @param permissions
     * @return
     */
    private boolean checkPermissions(String[] permissions) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }

        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this, permission) !=
                    PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    /**
     * 获取权限集中需要申请权限的列表
     *
     * @param permissions
     * @return
     */
    private List<String> getDeniedPermissions(String[] permissions) {
        List<String> needRequestPermissionList = new ArrayList<>();
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this, permission) !=
                    PackageManager.PERMISSION_GRANTED ||
                    ActivityCompat.shouldShowRequestPermissionRationale(this, permission)) {
                needRequestPermissionList.add(permission);
            }
        }
        return needRequestPermissionList;
    }

    /**
     * 启动当前应用设置页面
     */
    private void startAppSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse("package:" + getPackageName()));
        startActivity(intent);
    }


    /**
     * 系统请求权限回调
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_PERMISSION) {
            if (verifyPermissions(grantResults)) {
                permissionSuccess(REQUEST_CODE_PERMISSION);
            } else {
                permissionFail(REQUEST_CODE_PERMISSION);
                showTipsDialog();
            }
        }
    }


    /**
     * 显示提示对话框
     */
    private void showTipsDialog() {
        new AlertDialog.Builder(this)
                .setTitle("提示信息")
                .setMessage("当前应用缺少必要权限，该功能暂时无法使用。如若需要，请单击【确定】按钮前往设置中心进行权限授权。")
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startAppSettings();
                    }
                }).show();
    }


    /**
     * 权限获取失败
     *
     * @param requestCode
     */
    public void permissionFail(int requestCode) {

    }


    /**
     * 确认所有的权限是否都已授权
     *
     * @param grantResults
     * @return
     */
    private boolean verifyPermissions(int[] grantResults) {
        for (int grantResult : grantResults) {
            if (grantResult != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }


    public <T extends MyApplication> T getMyApplication() {
        return (T) getApplication();
    }


    private ProgressBar progressBar;

    private MyCallHandler myCallHandler;


    /**
     * 这个方法是用来加载网络资源的
     *
     * @param url
     * @param what
     * @param <T>
     */
    public <T> void loadData(String url, String what) {
        progressBar = LoadProgress.loadProgress(this);
        new Thread(new Runnable() {
            @Override
            public void run() {
                myCallHandler = new MyCallHandler(what);
                NetRequest.requestUrl(url, myCallHandler);
            }
        }).start();
    }

    /**
     * 网络请求成功的回调函数
     * 该方法运行在Ui线程中,,我们需要通过重写这个方法实现
     *
     * @param response
     */
    public void onSucessful(final Response response, String what, String... backData) throws IOException {
        //在这里需要判断是否是你发出的请求,通过class进行辨别

    }

    /**
     * 当请求网络发生错误的时候,执行会掉函数,通过复写这个方法实现对应的 操作
     *
     * @param e
     */
    public void onError(IOException e, String what) {

    }


    public class MyCallHandler implements okhttp3.Callback {

        private String what;

        public MyCallHandler(String what) {
            this.what = what;
        }

        @Override
        public void onFailure(Call call, IOException e) {
            UiThread.getUiThread().post(new Runnable() {
                @Override
                public void run() {
                    LoadProgress.removeLoadProgress(ParentActivity.this, progressBar);
                    Toast.makeText(ParentActivity.this, "加载数据出错,请稍后再试", Toast.LENGTH_SHORT).show();
                    onError(e, what);
                }
            });
        }

        @Override
        public void onResponse(Call call, final Response response) throws IOException {
            String value = response.body().string();
            UiThread.getUiThread().post(new Runnable() {
                @Override
                public void run() {
                    LoadProgress.removeLoadProgress(ParentActivity.this, progressBar);
                    try {
                        //这里返回的数据比较复杂,需要解析
                        onSucessful(response, what, value);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }


    /**
     * 获得登录的状态
     *
     * @param t
     * @return
     */
    public static <T extends ParentActivity> boolean getLoginStatus(T t) {

        boolean status = t.getMyApplication().setting.getBoolean(LOGIN_STATUS, DEF_LOGIN_STATUS);
        return status;
    }


    /**
     * 获得用户的Id，也就是登录的账号
     *
     * @param t
     * @return
     */
    public static <T extends ParentActivity> String getUserId(T t) {
        String userId = null;
        if (getLoginStatus(t)) {//如果已经登录
            userId = t.getMyApplication().setting.getString(USER_ID, DEFAULT_USER_ID);
        } else {//没有登录。返回默认的用户id
            userId = DEFAULT_USER_ID;
        }
        return userId;
    }

    public static <T extends ParentActivity> void offline(T t) {
        SharedPreferences.Editor editor = t.getMyApplication().setting.edit();
        editor.putString(USER_ID, DEFAULT_USER_ID);
        editor.putBoolean(LOGIN_STATUS, DEF_LOGIN_STATUS);
        editor.commit();
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        activities.remove(this);
    }
}
