package com.rcs.nchumanity.ul;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.google.gson.Gson;
import com.rcs.nchumanity.R;
import com.rcs.nchumanity.dialog.DialogCollect;
import com.rcs.nchumanity.entity.BasicResponse;
import com.rcs.nchumanity.entity.NetConnectionUrl;
import com.rcs.nchumanity.entity.PersistenceData;
import com.rcs.nchumanity.entity.model.FeedbackRecord;
import com.rcs.nchumanity.tool.DateProce;
import com.rcs.nchumanity.tool.Tool;
import com.rcs.nchumanity.ul.list.SpecificInfoComplexListActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Response;


/**
 * @author fangzhiyue
 * @version 1.0
 * @Date 20190-8-20
 * @function 设置页面的基本实现 ，提供了以下几种功能的实现
 * <p>
 * 1.退出登录
 * 账号的退出，注销当前的会话
 * <p>
 * 2.软件更新
 * 软件更新的相关实现
 * 3.光于我们
 * 关于该公司相关的情况，以及当前甲方相关的基本信息 相关简介，核心业务 ，工作范围 等
 * 4.取消报名
 * 取消当前的报名，当前app的主业务前提
 * 5.帮助中心
 * 提了对当前软件的详细使用说明，以及相关操作的介绍
 * 6.反馈
 * 7.修改密码
 */

/**
 * 系統設置的相关的显示
 */
public class SettingActivity extends BasicResponseProcessHandleActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set);
        ButterKnife.bind(this);
    }


    @OnClick({R.id.backLogin, R.id.softwareUpdate,
            R.id.aboutWe, R.id.cancelSignup, R.id.helpCenter,
            R.id.feedback, R.id.changePassword})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.backLogin:

                AlertDialog.Builder builder = new AlertDialog.Builder(this)
                        .setTitle("退出登录")
                        .setMessage("确认退出登录吗?")
                        .setNegativeButton("取消", ((dialog, which) -> {

                            dialog.dismiss();

                        })).setPositiveButton("确定", (dialog, which) -> {
                            PersistenceData.clear(this);
                            dialog.dismiss();
//                            Tool.startActivity(this, MainActivity.class);
                            backStackLower();
                        });

                builder.create().show();

                break;
            case R.id.softwareUpdate:

                AlertDialog.Builder builder1 = new AlertDialog.Builder(this)
                        .setTitle("更新提醒")
                        .setMessage("确定检验更新吗?")
                        .setNegativeButton("取消", ((dialog, which) -> {

                            dialog.dismiss();

                        })).setPositiveButton("确定", (dialog, which) -> {

                            /**
                             * 使用网络加载数据
                             */
                            dialog.dismiss();

                            final Dialog dialog1 = DialogCollect.openLoadDialog(this);
                            dialog1.setCancelable(false);
                            dialog1.show();
                            new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    dialog1.dismiss();
                                    new AlertDialog.Builder(SettingActivity.this)
                                            .setTitle("提示")
                                            .setMessage(R.string.message_update)
                                            .setPositiveButton("确定", (dialog, which) -> {
                                                dialog.dismiss();
                                            }).create().show();
                                }
                            }, 2000);
                        });
                builder1.create().show();

                break;

            case R.id.aboutWe:

                Bundle bundle3 = new Bundle();
                bundle3.putString(SpecificInfoComplexListActivity.CLASS_NAME, "关于我们");
                bundle3.putString(SpecificInfoComplexListActivity.URL, NetConnectionUrl.getAboutUs);
                Tool.startActivity(this, SpecificInfoComplexListActivity.class, bundle3);

                break;

            case R.id.cancelSignup:

                AlertDialog.Builder builder2 = new AlertDialog.Builder(this)
                        .setTitle("取消报名")
                        .setMessage(R.string.cancel_signup)
                        .setNegativeButton("取消", ((dialog, which) -> {
                            dialog.dismiss();

                        })).setPositiveButton("确定", (dialog, which) -> {

                            dialog.dismiss();
                            /**
                             * 使用网络加载数据
                             */
                            loadDataPost(NetConnectionUrl.cancelSignUp, "cancelSignUp", new HashMap<>());

                        });
                builder2.create().show();

                break;

            case R.id.helpCenter:

                Bundle bundle = new Bundle();
                bundle.putString(SpecificInfoComplexListActivity.CLASS_NAME, "帮助中心");
                bundle.putString(SpecificInfoComplexListActivity.URL, NetConnectionUrl.getHelpInfo);
                Tool.startActivity(this, SpecificInfoComplexListActivity.class, bundle);

                break;

            case R.id.feedback:

                loadDataPost(NetConnectionUrl.getFeedback, "getFeedback", new HashMap<>());

                break;


            case R.id.changePassword:

                Tool.startActivity(this, ChangePasswordActivity.class);

                break;
        }
    }


    /**
     * 响应成功的回调实现
     *
     * @param what
     * @param backData
     * @param response
     * @param br
     * @throws Exception
     */
    @Override
    protected void responseDataSuccess(String what, String backData, Response response, BasicResponse... br) throws Exception {
        super.responseDataSuccess(what, backData, response, br);

        if (what.equals("cancelSignUp")) {

            AlertDialog.Builder builder = new AlertDialog.Builder(this)
                    .setTitle("提示")
                    .setMessage("取消报名成功")
                    .setPositiveButton("确定", (dialog, which) -> {
                        dialog.dismiss();
                    });
            builder.create().show();

        } else if (what.equals("getFeedback")) {

            try {
                JSONObject brJ = new JSONObject(backData);
                JSONArray objS = brJ.getJSONArray("object");

                ArrayList<FeedbackRecord> feedbackRecords = new ArrayList<>();

                for (int i = 0; i < objS.length(); i++) {
                    JSONObject obj = objS.getJSONObject(i);
                    String createTime = obj.getString("createTime");
                    String message = obj.getString("message");
                    String replay = obj.getString("reply");

                    FeedbackRecord feedbackRecord = new FeedbackRecord();
                    feedbackRecord.setCreateTime(DateProce.parseDate(createTime));
                    feedbackRecord.setMessage(message);
                    feedbackRecord.setReply(replay);
                    feedbackRecords.add(feedbackRecord);
                }
                Bundle bundle = new Bundle();
                bundle.putSerializable(FeedbackActivity.DATA, feedbackRecords);
                Tool.startActivity(this, FeedbackActivity.class, bundle);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * 响应吗500的回调实现
     *
     * @param what 请求标示
     * @param br   基本响应类型的构建体，包装了对 响应数据
     */
    @Override
    protected void responseWith500(String what, BasicResponse br) {
        super.responseWith500(what, br);
        new AlertDialog.Builder(this)
                .setTitle("提示")
                .setMessage(br.message)
                .setPositiveButton("确定", (dialog, which) -> {
                    dialog.dismiss();
                }).create().show();
    }


    /**
     * 响应码 501 的回调实现
     *
     * @param what 请求标示
     * @param br   基本响应类型的构建体 包装了对 响应数据
     */
    @Override
    protected void responseWith501(String what, BasicResponse br) {
        super.responseWith501(what, br);
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle("提示")
                .setMessage(br.message)
                .setPositiveButton("确定", (dialog, which) -> {
                    dialog.dismiss();
                });
        builder.create().show();
    }

}
