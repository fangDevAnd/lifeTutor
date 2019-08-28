package com.rcs.nchumanity.ul;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.rcs.nchumanity.R;
import com.rcs.nchumanity.entity.BasicResponse;
import com.rcs.nchumanity.entity.NetConnectionUrl;
import com.rcs.nchumanity.entity.PersistenceData;
import com.rcs.nchumanity.service.thirdParty.ValidateCodeServler;
import com.rcs.nchumanity.tool.LoadProgress;
import com.rcs.nchumanity.tool.Tool;
import com.rcs.nchumanity.view.CommandBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.commons.codec.binary.StringUtils;

import java.io.IOException;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.smssdk.SMSSDK;
import okhttp3.Headers;
import okhttp3.Response;


public class ValidateCodeActivity extends BasicResponseProcessHandleActivity {


    private static final String TAG = "test";
    @BindViews({R.id.code1, R.id.code2, R.id.code3,
            R.id.code4, R.id.code5, R.id.code6})
    List<EditText> codes;

    @BindView(R.id.reSend)
    Button reSend;

    @BindView(R.id.toolbar)
    CommandBar toolbar;

    @BindView(R.id.phone)
    TextView phone;

    public static final String MOBILE_PHONE = "mobilePhone";

    public static final String ACTION = "action";

    public static String ACTION_VALIDE_CODE_LOGIN = "login";

    public static String ACTION_REGISTER = "register";

    public static String ACTION_RESET_PASSWORD = "resetPassword";


    @OnClick(R.id.reSend)
    public void onClick(View view) {
        //点击操作之后，我们禁用该控件
        //发送验证码到该手机上
        isSubmitSuccess = false;
        anim();

        sendmSms();
    }


    public void anim() {

        ValueAnimator va = ValueAnimator.ofInt(60, 0);
        va.setInterpolator(new LinearInterpolator());
        va.setDuration(60000);
        va.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                reSend.setClickable(true);
                reSend.setBackgroundResource(R.drawable.login_button_radius);
                reSend.setText("重新发送");
            }

            @Override
            public void onAnimationStart(Animator animation) {
                reSend.setClickable(false);
                reSend.setBackgroundResource(R.drawable.login_button_radius_enable);
            }
        });

        va.addUpdateListener(animation -> {
            int value = (int) animation.getAnimatedValue();
            reSend.setText("重新发送\t" + value + "s");
        });

        va.start();

    }

    private void sendmSms() {
        reSet();
        Map<String, String> param = new HashMap<>();
        param.put("mobilephone", userPhone);

        loadDataPostForce(NetConnectionUrl.getValidateCode, "getValidateCode", param);

    }


    private String userPhone;

    private String action;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_validate_code);
        ButterKnife.bind(this);

        for (int i = 0; i < codes.size(); i++) {
            codes.get(i).addTextChangedListener(tw);
        }

        toolbar.setTitle("验证码输入");
        anim();

        Bundle bundle = getIntent().getExtras();
        userPhone = bundle.getString(MOBILE_PHONE);

        action = bundle.getString(ACTION);


        if (userPhone == null || action == null) {
            throw new InvalidParameterException("please bundle params to this");
        }

        phone.setText(userPhone);

        sendmSms();

        init();

    }


    public void init() {
        for (int i = 0; i < codes.size(); i++) {
            codes.get(i).setFocusableInTouchMode(false);
            codes.get(i).setText("");
        }
        codes.get(currentFocusIndex).setFocusableInTouchMode(true);
        codes.get(currentFocusIndex).requestFocus();
    }


    private TextWatcher tw = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {

            Log.d(TAG, "afterTextChanged: " + s.toString().trim());

            if (s.toString().trim().length() == 1) {

                codes.get(currentFocusIndex).clearFocus();
                codes.get(currentFocusIndex).setFocusableInTouchMode(false);

                codeLink[currentFocusIndex] = codes.get(currentFocusIndex).getText().toString();

                if (currentFocusIndex < codes.size()) {//3
                    //代表的最后一个
                    if (currentFocusIndex == codes.size() - 1) {
                        for (String s1 : codeLink) {
                            code += s1;
                        }
                        //在这里进行提交 ，最后一个edittext
                        /**
                         * 不在使用当前的验证方案，使用正式服务器的验证请求
                         */
//                        ValidateCodeServler.submitValidateCode("86", userPhone, code);

                        oprateLogic();

                    } else {
                        currentFocusIndex++;
                    }
                    codes.get(currentFocusIndex).setFocusableInTouchMode(true);
                    codes.get(currentFocusIndex).requestFocus();
                }
                Log.d(TAG, "当前索引: " + currentFocusIndex);//3

            }
        }
    };


    /**
     * 实现短信验证的处理类
     * ,该类执行在主线程,可以操作ui
     */
    class MyHandlerCallback implements Handler.Callback {

        @Override
        public boolean handleMessage(Message msg) {

            int event = msg.arg1;
            int result = msg.arg2;
            Object data = msg.obj;
            if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                if (result == SMSSDK.RESULT_COMPLETE) {
                    // TODO 处理成功得到验证码的结果
                    // 请注意，此时只是完成了发送验证码的请求，验证码短信还需要几秒钟之后才送达
                    sucessfulSendVerification();

                } else {
                    // TODO 处理错误的结果
                    ((Throwable) data).printStackTrace();
                }
            } else if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                if (result == SMSSDK.RESULT_COMPLETE) {
                    // TODO 处理验证码验证通过的结果
//                    submitVerificationComplete();
                } else {
                    submitVerficationError();
                    // TODO 处理错误的结果
                    ((Throwable) data).printStackTrace();
                }
            }
            // TODO 其他接口的返回结果也类似，根据event判断当前数据属于哪个接口
            return false;
        }
    }


    private boolean isSubmitSuccess = false;


    public void oprateLogic() {

        if (action.equals(ACTION_REGISTER)) {


            //持久化数据 手机号码
            PersistenceData.setPhoneNumber(this, userPhone);
            Bundle bundle = new Bundle();
            bundle.putString(InputPasswordActivity.CODE, code);
            bundle.putString(InputPasswordActivity.FUNC, InputPasswordActivity.FUNC_REGISTER);
            //代表成功
            Tool.startActivity(this, InputPasswordActivity.class, bundle);

        } else if (action.equals(ACTION_RESET_PASSWORD)) {
            Bundle bundle1 = new Bundle();
            bundle1.putString(InputPasswordActivity.CODE, code);
            bundle1.putString(InputPasswordActivity.FUNC, InputPasswordActivity.FUNC_SET_PASSWORD);
            Tool.startActivity(this, InputPasswordActivity.class, bundle1);

        } else if (action.equals(ACTION_VALIDE_CODE_LOGIN)) {
            Map<String, String> param = new HashMap();
            param.put("mobilephone", userPhone);
            param.put("code", code);
            loadDataPost(NetConnectionUrl.smsLogin, "validateCodeLogin", param);
        }

    }


    /**
     * 成功发送了验证码
     * 不做处理
     */
    private void sucessfulSendVerification() {

        reSet();
    }

    /**
     * 提交验证码出错
     */
    private void submitVerficationError() {
        Toast.makeText(this, "验证码不正确", Toast.LENGTH_SHORT).show();
    }


    private int currentFocusIndex = 0;

    private String code = "";

    private String[] codeLink = new String[6];

    {
        for (String c : codeLink) {
            c = "";
        }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        Log.d(TAG, "onKeyDown: currentIndex" + currentFocusIndex);

        if (keyCode == KeyEvent.KEYCODE_DEL) {
            for (int i = 0; i < codes.size(); i++) {
                if (codes.get(i).isFocused()) {
                    currentFocusIndex = i;
                    codes.get(i).clearFocus();
                    codes.get(i).setFocusableInTouchMode(false);
                }
            }
        }

        if (currentFocusIndex > 0) {
            currentFocusIndex--;
            codes.get(currentFocusIndex).setFocusableInTouchMode(true);
            codes.get(currentFocusIndex).requestFocus();
            codes.get(currentFocusIndex).setText("");
            codeLink[currentFocusIndex] = "";
        }

        if (currentFocusIndex == 0) {
            codes.get(currentFocusIndex).setFocusableInTouchMode(true);
            codes.get(currentFocusIndex).requestFocus();
        }


        return super.onKeyDown(keyCode, event);
    }


    @Override
    protected void onRestart() {
        super.onRestart();

        Log.d(TAG, "onRestart: ");

        reSet();
    }

    public void reSet() {
        code = "";
        currentFocusIndex = 0;
        init();
        for (String c : codeLink) {
            c = "";
        }
        isSubmitSuccess = false;
    }


    @Override
    protected void responseDataSuccess(String what, String backData, Response response, BasicResponse... br) throws Exception {
        super.responseDataSuccess(what, backData, response, br);
        if (what.equals("validateCodeLogin")) {
            String sessionId = response.header("Set-Cookie");//JSESSIONID=0879B42A28FEEB113E883D6FC295C7CA; Path=/ncrd; HttpOnly
            Log.d("test", "onSucessful:当前的sessionId " + sessionId);
            sessionId = sessionId.substring(0, sessionId.indexOf(";"));
            Tool.loginResponse(this, backData, sessionId);
        } else if (what.equals("getValidateCode")) {
            reSet();
        }

    }
}
