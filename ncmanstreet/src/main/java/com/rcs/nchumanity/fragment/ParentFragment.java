package com.rcs.nchumanity.fragment;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.IntDef;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;


import com.rcs.nchumanity.R;
import com.rcs.nchumanity.dialog.DialogCollect;
import com.rcs.nchumanity.net.NetRequest;
import com.rcs.nchumanity.tool.LoadProgress;
import com.rcs.nchumanity.tool.UiThread;
import com.rcs.nchumanity.ul.ParentActivity;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Response;

import static com.rcs.nchumanity.net.NetRequest.MODE_FORCE;
import static com.rcs.nchumanity.net.NetRequest.MODE_SILENCE;
import static com.rcs.nchumanity.net.NetRequest.MODE_SOFT;


/**
 * fragment的父集，用来实现一些通用的操作
 * <p>
 * 我们在lazyLoad()函数里面实现数据加载---->调用 loadData()加载数据，然后将街而过返回到success和error其中，然后根据烦恼返回的数据进行数据的处理
 */
public abstract class ParentFragment extends Fragment implements FramgentOprate {


    protected int REQUEST_CODE_PERMISSION = 0x00099;

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
            ActivityCompat.requestPermissions(getActivity(), needPermissions.toArray(new String[needPermissions.size()]), REQUEST_CODE_PERMISSION);
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
            if (ContextCompat.checkSelfPermission(getContext(), permission) !=
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
            if (ContextCompat.checkSelfPermission(getContext(), permission) !=
                    PackageManager.PERMISSION_GRANTED ||
                    ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), permission)) {
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
        intent.setData(Uri.parse("package:" + getActivity().getPackageName()));
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
//                showTipsDialog();
            }
        }
    }


    /**
     * 显示提示对话框
     */
    private void showTipsDialog() {
        new AlertDialog.Builder(getContext())
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


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getCreateView(), null,false);
        ButterKnife.bind(this, view);
        return view;
    }

    /**
     * 创建view
     *
     * @return
     */
    protected abstract int getCreateView();

    /**
     * 当view创建成功后的回调
     *
     * @param view
     * @param savedInstanceState
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        isViewCreated = true;
        onViewCreated(view, savedInstanceState, isViewCreated);
        lazyLoad();
    }

    protected void onViewCreated(View view, Bundle savedInstanceState, boolean isViewCreated) {

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
        Log.d("test", "loadData: 请求的url：" + url);
        final Dialog dialog = (AlertDialog) DialogCollect.openLoadDialog(getContext());
        if (loadCase != MODE_SILENCE) {
            dialog.show();
        }
        if (loadCase == MODE_FORCE) {
            dialog.setCancelable(false);
        } else if (loadCase == MODE_SOFT) {
            dialog.setCancelable(true);
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
            Log.d("test", "onDismiss: ");
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


    public <T extends ParentActivity> T getMyActivity() {
        return (T) getActivity();
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
                    Toast.makeText(getMyActivity(), "加载数据出错,请稍后再试", Toast.LENGTH_SHORT).show();
                    onError(e, what);
                }
            });
        }

        @Override
        public void onResponse(Call call, final Response response) throws IOException {
            final String value = response.body().string();
            UiThread.getUiThread().post(new Runnable() {
                @Override
                public void run() {
                    if (dialog.isShowing()) {
                        dialog.dismiss();
                    }
                    try {
                        //这里返回的数据比较复杂,需要解析
                        onSucess(response, what, value);
                        Log.d("test", "run: " + value);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }


    /**
     * 下面的变量是来控制fragment的加载数据的
     */
    //Fragment的View加载完毕的标记
    private boolean isViewCreated;

    //Fragment对用户可见的标记
    private boolean isUIVisible;


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        Log.d("test", "setUserVisibleHint: 当前页面可见" + getClass().getSimpleName());
        //isVisibleToUser这个boolean值表示:该Fragment的UI 用户是否可见
        if (isVisibleToUser) {
            isUIVisible = true;
            lazyLoad();
        } else {
            isUIVisible = false;
        }
    }


    private void lazyLoad() {
        Log.d(
                "test", "lazyLoad: ");
        //这里进行双重标记判断,是因为setUserVisibleHint会多次回调,并且会在onCreateView执行前回调,必须确保onCreateView加载完毕且页面可见,才加载数据
        if (isViewCreated && isUIVisible) {
            onLoadData();
            //数据加载完毕,恢复标记,防止重复加载
            isViewCreated = false;
            isUIVisible = false;
        }
    }

    /**
     * 在这里面进行数据的加载
     * 是一个空的实现，可以在这里实现数据的加载
     * 例如加载网络数据
     */
    protected void onLoadData() {
    }

    /**
     * 请求网络加载，加载数据出错的情况的回调
     *
     * @param e
     * @param what
     */
    public void onError(IOException e, String what) {
    }

    /**
     * 请求网络加载，加载数据加载成功的回调
     *
     * @param response
     * @param what
     * @param backData
     * @throws IOException
     */
    public void onSucess(Response response, String what, String... backData) throws IOException {

    }


}
