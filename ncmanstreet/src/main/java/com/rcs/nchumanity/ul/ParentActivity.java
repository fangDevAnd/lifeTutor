package com.rcs.nchumanity.ul;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.IntDef;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.rcs.nchumanity.application.MyApplication;
import com.rcs.nchumanity.dialog.DialogCollect;
import com.rcs.nchumanity.net.NetRequest;
import com.rcs.nchumanity.tool.LoadProgress;
import com.rcs.nchumanity.tool.UiThread;
import com.orhanobut.logger.Logger;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Response;

import static com.rcs.nchumanity.net.NetRequest.MODE_FORCE;
import static com.rcs.nchumanity.net.NetRequest.MODE_SILENCE;
import static com.rcs.nchumanity.net.NetRequest.MODE_SOFT;


/**
 * 这个类的作用是保存一些全局的共享的数据
 */
public abstract class ParentActivity extends AppCompatActivity {


    protected String[] permissions = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.SEND_SMS,
            Manifest.permission.FOREGROUND_SERVICE
    };

    private final String TAG = "test";
    protected int REQUEST_CODE_PERMISSION = 0x00099;


    public static List<Activity> activities = new ArrayList();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activities.add(this);

        Logger.init("test");//初始化
    }

    /**
     * 清除当前所有的Activity
     */
    public void clearActivityStack() {
        for (Activity activity : activities) {
            activity.finish();
        }
        activities.clear();
    }

    /**
     * 回退到栈的底部
     */
    public void backStackLower() {
        for (int i = activities.size() - 1; i > 0; i--) {
            Activity activity = activities.get(i);
            activities.remove(activity);
            activity.finish();
        }
    }


    @Override
    public Resources getResources() {
        Resources res = super.getResources();
        Configuration config = new Configuration();
        config.setToDefaults();
        res.updateConfiguration(config, res.getDisplayMetrics());
        return res;
    }


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
                //  showTipsDialog();
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


    /**
     * @param url       请求的url
     * @param what      请求的标示
     * @param method    请求的方法
     * @param params    POST请求使用的参数map
     * @param loadCase  加载方案
     * @param postImg   是否是提交图片  post方式
     * @param imagePath 图片的路径
     * @param postJson  是否是json格式的提交数据
     * @param json      json格式数据
     */
    public void loadData(final String url, final String what, String method, Map<String, String> params, @NetRequest.LoadCase int loadCase, boolean postImg, String imagePath, boolean postJson, String json) {
        Log.d(TAG, "loadData: 请求的url：" + url);
        final Dialog dialog = (AlertDialog) DialogCollect.openLoadDialog(this);
        if(!this.isDestroyed()) {
            if (loadCase == MODE_FORCE) {
                dialog.setCancelable(false);
            } else if (loadCase == MODE_SOFT) {
                dialog.setCancelable(true);
            }
            if (loadCase != MODE_SILENCE) {
                dialog.show();
            }
        }else if(this.isDestroyed()) {
            dialog.dismiss();
        }

        Thread t = new Thread(() -> {
            MyCallHandler myCallHandler = new MyCallHandler(what, dialog);
            if (method.equalsIgnoreCase("GET")) {
                NetRequest.requestUrl(url, myCallHandler);
            } else if (method.equalsIgnoreCase("POST")) {
                if (postImg) {
                    NetRequest.postImage(url, imagePath, params, myCallHandler);
                } else if (postJson) {
                    NetRequest.requestPostJson(url, json, myCallHandler);
                } else {
                    NetRequest.requestPost(url, params, myCallHandler);
                }
            }
        });
        t.start();

        dialog.setOnDismissListener((dialog1) -> {
            Log.d(TAG, "onDismiss: ");
            if (t.getState() != Thread.State.TERMINATED) {
                t.interrupt();
                onError(new InterruptedIOException("自己取消异常"), what);
            }
        });
    }


    public void loadDataGetSilence(final String url, final String what) {
        loadData(url, what, "GET", null, MODE_SILENCE, false, null, false, null);
    }


    /**
     * 软加载
     *
     * @param url
     * @param what
     */
    public void loadDataGet(final String url, final String what) {
        loadData(url, what, "GET", null, MODE_SOFT, false, null, false, null);
    }

    /**
     * 代表的是强制加载 。无法取消
     *
     * @param url
     * @param what
     */
    public void loadDataGetForForce(String url, final String what) {
        loadData(url, what, "GET", null, MODE_FORCE, false, null, false, null);
    }


    public void loadDataPostSilence(final String url, final String what, Map<String, String> params) {
        loadData(url, what, "POST", params, MODE_SILENCE, false, null, false, null);
    }

    /**
     * 软加载提交
     *
     * @param url
     * @param what
     * @param params
     */
    public void loadDataPost(final String url, final String what, Map<String, String> params) {
        loadData(url, what, "POST", params, MODE_SOFT, false, null, false, null);
    }


    /**
     * 强制提交
     *
     * @param url
     * @param what
     * @param params
     */
    public void loadDataPostForce(final String url, final String what, Map<String, String> params) {
        loadData(url, what, "POST", params, MODE_FORCE, false, null, false, null);
    }


    /**
     * 提交json数据
     *
     * @param url
     * @param what
     * @param json
     */
    public void loadDataPostJson(final String url, final String what, String json) {
        loadData(url, what, "POST", null, MODE_SOFT, false, null, true, json);
    }

    /**
     * 提交json数据 强制
     *
     * @param url
     * @param what
     * @param json
     */
    public void loadDataPostJsonForce(final String url, final String what, String json) {
        loadData(url, what, "POST", null, MODE_FORCE, false, null, true, json);
    }


    /**
     * 提交图片
     *
     * @param url
     * @param what
     * @param imagePath
     */
    public void loadDataPostImg(final String url, final String what, String imagePath, Map<String, String> params) {
        loadData(url, what, "POST", params, MODE_SOFT, true, imagePath, false, null);
    }

    /**
     * 强制提交
     *
     * @param url
     * @param what
     * @param imagePath
     */
    public void loadDataPostImgForce(final String url, final String what, String imagePath, Map<String, String> params) {
        loadData(url, what, "POST", params, MODE_FORCE, true, imagePath, false, null);
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


        private Dialog dialog;

        public MyCallHandler(String what, Dialog dialog) {
            this.what = what;
            this.dialog = dialog;
        }

        @Override
        public void onFailure(Call call, final IOException e) {
            UiThread.getUiThread().post(new Runnable() {
                @Override
                public void run() {
                    if (dialog.isShowing()) {
                        dialog.dismiss();
                    }
                    Toast.makeText(ParentActivity.this, "加载数据出错,请稍后再试", Toast.LENGTH_SHORT).show();
                    onError(e, what);
                }
            });
        }

        @Override
        public void onResponse(Call call, final Response response) throws IOException {

            Log.d(TAG, "onResponse:响应头 " + response.headers());

            final String value = response.body().string();
            Log.d(TAG, "basicResponse : " + value);
            UiThread.getUiThread().post(new Runnable() {
                @Override
                public void run() {
                    if (dialog.isShowing()) {
                        dialog.dismiss();
                    }
                    try {
                        //这里返回的数据比较复杂,需要解析
                        Log.d(TAG, "basicResponse : " + value);
                        onSucessful(response, what, value);
                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.d(TAG, "发生错误" + e);
                    }
                }
            });
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


    public ServiceConnection conn;

    @Override
    protected void onDestroy() {


        activities.remove(this);
        super.onDestroy();
        if (conn != null) {
            unbindService(conn);//进行解绑 conn的具体实现是由子类实现的
        }
    }


}
