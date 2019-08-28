package com.rcs.nchumanity.ul;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;


import com.google.gson.Gson;
import com.rcs.nchumanity.R;

import com.rcs.nchumanity.dialog.DialogTool;
import com.rcs.nchumanity.entity.BasicResponse;
import com.rcs.nchumanity.entity.NetConnectionUrl;
import com.rcs.nchumanity.entity.PersistenceData;
import com.rcs.nchumanity.net.NetRequest;
import com.rcs.nchumanity.tool.*;

import org.json.JSONObject;

import com.rcs.nchumanity.service.thirdParty.*;
import com.rcs.nchumanity.view.CommandBar;

import java.io.IOException;
import java.net.URI;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.smssdk.SMSSDK;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class RegisterUserActivity extends BasicResponseProcessHandleActivity {


    private static final String TAG = "test";
    @BindView(R.id.phoneNumber)
    EditText phoneNumber;

    @BindView(R.id.registerSubmit)
    Button registerSubmit;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_register);
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);

    }


    private String userPhone;


    @OnClick({R.id.registerSubmit, R.id.privacyClause, R.id.userProtocol})
    public void registerSubmit(View view) {
        switch (view.getId()) {
            case R.id.registerSubmit:


                /**
                 *
                 * 请求网络，查看当前的用户是否已经被注册，如果被注册，直接跳转到
                 * 输入密码的界面
                 *
                 */


                userPhone = phoneNumber.getText().toString();
                if (TextUtils.isEmpty(userPhone) || userPhone.length() != 11) {
                    Toast.makeText(this, "请输入合法的手机号码", Toast.LENGTH_SHORT).show();
                    return;
                }


                String param = String.format(NetConnectionUrl.REGISTER_STATUS, userPhone);

                loadDataGet(param, "registerStatus");

                break;

            case R.id.privacyClause:

                //弹出一个窗口 隐私政策

                Bundle bundle = new Bundle();
                bundle.putString(WebLoadActivity.TITLE, "隐私政策");
//                bundle.putString(WebLoadActivity.URL, NetConnectionUrl.getPrivacyPolicy);
                bundle.putString(WebLoadActivity.URL, "http://193.112.182.184:8888/ncrd/app/getPrivacyPolicy");
                Tool.startActivity(this, WebLoadActivity.class, bundle);
                break;

            case R.id.userProtocol:

                //弹出一个窗口用户协议


                break;
        }
    }


    @Override
    protected void responseWith1(String what, BasicResponse br) {
        super.responseWith1(what, br);

        if (what.equals("registerStatus")) {

            /**
             * 根据返回数据的结果，动态的跳转相应的界面
             */
            Bundle bundle = new Bundle();
            bundle.putString(ValidateCodeActivity.MOBILE_PHONE, userPhone);
            bundle.putString(ValidateCodeActivity.ACTION, ValidateCodeActivity.ACTION_REGISTER);
            Tool.startActivity(this, ValidateCodeActivity.class, bundle);
        }

    }


    @Override
    protected void responseWith2(String what, BasicResponse br) {
        super.responseWith2(what, br);
        if (what.equals("registerStatus")) {
            PersistenceData.setPhoneNumber(this, userPhone);
            Bundle bundle1 = new Bundle();
            //进行登录
            bundle1.putString(InputPasswordActivity.FUNC, InputPasswordActivity.FUNC_LOGIN);
            Tool.startActivity(this, InputPasswordActivity.class, bundle1);
        }
    }

//    @Override
//    public void onSucessful(Response response, String what, String... backData) throws IOException {
//        super.onSucessful(response, what, backData);
//
//        BasicResponse basicResponse = new Gson().fromJson(backData[0], BasicResponse.class);
//
//        Log.d(TAG, "onSucessful: " + basicResponse);
//
//        switch (what) {
//
//            case "registerStatus": {
//
//                switch (basicResponse.code) {
//
//                    case BasicResponse.NOT_REGISTER:
//
//                        //发送验证码到该手机上
////                        ValidateCodeServler.sendValidateCode("86", userPhone);
//
//                        /**
//                         * 根据返回数据的结果，动态的跳转相应的界面
//                         */
//                        Bundle bundle = new Bundle();
//                        bundle.putString(ValidateCodeActivity.MOBILE_PHONE, userPhone);
//                        bundle.putString(ValidateCodeActivity.ACTION, ValidateCodeActivity.ACTION_REGISTER);
//                        Tool.startActivity(this, ValidateCodeActivity.class, bundle);
//                        break;
//
//                    case BasicResponse.REGISTED:
//
//                        PersistenceData.setPhoneNumber(this, userPhone);
//                        Bundle bundle1 = new Bundle();
//                        //进行登录
//                        bundle1.putString(InputPasswordActivity.FUNC, InputPasswordActivity.FUNC_LOGIN);
//                        Tool.startActivity(this, InputPasswordActivity.class, bundle1);
//
//                        break;
//                }
//            }
//            break;
//        }
//    }
}