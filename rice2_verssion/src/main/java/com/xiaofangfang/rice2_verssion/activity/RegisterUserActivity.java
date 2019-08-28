package com.xiaofangfang.rice2_verssion.activity;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import androidx.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import com.xiaofangfang.rice2_verssion.ParentActivity;
import com.xiaofangfang.rice2_verssion.R;
import com.xiaofangfang.rice2_verssion.network.NetRequest;
import com.xiaofangfang.rice2_verssion.tool.Crypto;
import com.xiaofangfang.rice2_verssion.tool.DialogTool;
import com.xiaofangfang.rice2_verssion.tool.LoadProgress;
import com.xiaofangfang.rice2_verssion.tool.Looger;
import com.xiaofangfang.rice2_verssion.tool.UiThread;
import com.xiaofangfang.rice2_verssion.tool.ValidateCodeServler;
import com.xiaofangfang.rice2_verssion.view.CommandBar;

import org.json.JSONObject;

import java.io.IOException;

import cn.smssdk.SMSSDK;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class RegisterUserActivity extends ParentActivity implements View.OnClickListener {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.register);
        super.onCreate(savedInstanceState);
        registerValidateServer();

    }

    /**
     * 注册验证服务
     */
    private void registerValidateServer() {
        ValidateCodeServler.registerProgress(new MyHandlerCallback());
    }


    private Button obtainValidateCode;
    private EditText userNumber, validatecode, userPassword, reuserPassword;
    private TextView useclause, registerButton;

    /**
     * 用户初始化一个视图
     */
    public void initView() {
        obtainValidateCode = findViewById(R.id.obtainValidateCode);

        //下面是编辑框的
        userNumber = findViewById(R.id.userNumber);
        userNumber.setOnFocusChangeListener(new MyOnFocusListener(userNumber.getId()));
        validatecode = findViewById(R.id.validatecode);
        userPassword = findViewById(R.id.userPassword);
        userPassword.setOnFocusChangeListener(new MyOnFocusListener(userPassword.getId()));
        reuserPassword = findViewById(R.id.reuserPassword);

        //下面是文本
        useclause = findViewById(R.id.useclause);
        registerButton = findViewById(R.id.registerButton);

        obtainValidateCode.setOnClickListener(this);
        useclause.setOnClickListener(this);
        registerButton.setOnClickListener(this);
        //添加焦点事件
        CommandBar com = findViewById(R.id.commandBar);
        com.setTitle("注册");

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.obtainValidateCode:
                progressObtainValudateCode();
                break;

            case R.id.useclause:
                progressUseclause();
                break;

            case R.id.registerButton:
                progressRegisterButton();
                break;
        }

    }

    ProgressBar progressBar;

    /**
     * 处理注册按钮的点击
     */
    private void progressRegisterButton() {

        String userPhone = userNumber.getText().toString();
        String code = validatecode.getText().toString();
        String password = userPassword.getText().toString();
        String repassword = reuserPassword.getText().toString();
        if (password.length() < 6) {
            Toast.makeText(this, "请输入6-16位密码", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!password.equals(repassword)) {
            Toast.makeText(this, "两次密码不一致", Toast.LENGTH_SHORT).show();
            return;
        }

        //在这里开启一个加载的框,用来实现加载
        progressBar = LoadProgress.loadProgress(this);


        ValidateCodeServler.submitValidateCode("86", userPhone, code);

    }

    /**
     * 处理点击用户协议的操作
     */
    private void progressUseclause() {

    }

    /**
     * 处理获得验证码的操作
     */
    private void progressObtainValudateCode() {


        String userPhone = userNumber.getText().toString();
        if (TextUtils.isEmpty(userPhone) || userPhone.length() != 11) {
            Toast.makeText(this, "请输入合法的手机号码", Toast.LENGTH_SHORT).show();
            return;
        }
        //点击过后启动定时器,进行动态的改变时间
        ValueAnimator valueAnimator = ValueAnimator.ofInt(60, 0);
        valueAnimator.setDuration(60000);
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.addListener(new MyAnimatorListener(obtainValidateCode));
        valueAnimator.addUpdateListener(new MyAnimatorUpdateListener(obtainValidateCode));
        valueAnimator.start();
        //下面是发送一个验证码过来
        ValidateCodeServler.sendValidateCode("86", userPhone);

    }

    /**
     * 返回登录按钮的处理
     */
    private void progressLoginButton() {


    }

    /**
     * 处理返回上一个界面的点击事件的处理
     */
    private void progressBackupPage() {

    }


    /**
     * 动画更新的实现类
     */
    class MyAnimatorListener implements Animator.AnimatorListener {

        TextView oprationView;

        MyAnimatorListener(TextView view) {
            this.oprationView = view;
        }


        @Override
        public void onAnimationStart(Animator animation) {
            oprationView.setClickable(false);
        }

        @Override
        public void onAnimationEnd(Animator animation) {
            oprationView.setClickable(true);
            oprationView.setText("获取验证码");
        }

        @Override
        public void onAnimationCancel(Animator animation) {

        }

        @Override
        public void onAnimationRepeat(Animator animation) {

        }
    }

    class MyAnimatorUpdateListener implements ValueAnimator.AnimatorUpdateListener {


        TextView oprationView;

        MyAnimatorUpdateListener(TextView view) {
            this.oprationView = view;
        }


        @Override
        public void onAnimationUpdate(ValueAnimator animation) {
            int value = (int) animation.getAnimatedValue();
            oprationView.setText(value + "s");
        }
    }


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
                    submitVerificationComplete();
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

    /**
     * 提交验证码出错
     */
    private void submitVerficationError() {
        Toast.makeText(this, "验证码不正确", Toast.LENGTH_SHORT).show();
        LoadProgress.removeLoadProgress(RegisterUserActivity.this, progressBar);
    }


    boolean submitVerificationPass = false;

    /**
     * 提交验证码码通过
     */
    private void submitVerificationComplete() {
        submitVerificationPass = true;
        String userPhone = userNumber.getText().toString();
        String password = userPassword.getText().toString();

        String encyptPassword = Crypto.getMD5(password);
        //加密密码
        //发送网络请求,进行注册
        String param = "name=" + userPhone + "&password=" + encyptPassword;
        String url = NetRequest.REGISTER_SERVER_URL + "?" + param;
        Looger.D("注册的url=" + url);
        NetRequest.requestUrl(url, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Looger.D(e.getMessage());
                UiThread.getUiThread().post(new Runnable() {
                    @Override
                    public void run() {
                        LoadProgress.removeLoadProgress(RegisterUserActivity.this, progressBar);
                        Toast.makeText(RegisterUserActivity.this, "注册失败,请稍后再试",
                                Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                UiThread.getUiThread().post(new Runnable() {
                    @Override
                    public void run() {
                        LoadProgress.removeLoadProgress(RegisterUserActivity.this, progressBar);
                        String jsonData = null;
                        JSONObject jsonObject;
                        int responseCode = 0;
                        String responseContent = null;
                        try {
                            jsonData = response.body().string();
                            jsonObject = new JSONObject(jsonData);
                            responseCode = jsonObject.getInt("responseCode");
                            responseContent = jsonObject.getString("responseContent");
                            Looger.D("响应内容" + responseContent);
                            Toast.makeText(RegisterUserActivity.this, "响应:" + responseContent,
                                    Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        if (responseCode == 0) {//代表响应成功

                            DialogTool<String> dialogTool = new DialogTool<String>() {
                                @Override
                                public void bindView(DialogTool<String> d, Dialog dialog, String... t) {
                                    d.setClickListener(R.id.close, (v) -> {
                                        dialog.cancel();
                                    });
                                    d.setText(R.id.button, t[1]);
                                    d.setText(R.id.oprateTip, t[0]);
                                    d.setClickListener(R.id.button, (v) -> {
                                        dialog.cancel();
                                        finish();
                                    });
                                }
                            };
                            Dialog dialog = dialogTool.getDialog(RegisterUserActivity.this, R.layout.oprate_facenack,
                                    "注册成功,返回登录?", "返回登录");
                            dialog.show();


                        }
                    }
                });
            }
        });

    }

    /**
     * 成功发送验证码的回调
     */
    private void sucessfulSendVerification() {

    }


    /**
     * 焦点改变的监听
     */
    private class MyOnFocusListener implements View.OnFocusChangeListener {

        int viewId;

        public MyOnFocusListener(int id) {
            this.viewId = id;
        }

        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            switch (viewId) {
                case R.id.userNumber:
                    progressUserNumber(hasFocus);
                    break;
                case R.id.userPassword:
                    progressUserPassword(hasFocus);
                    break;
            }
        }
    }

    private void progressUserPassword(boolean hasFocus) {

    }

    private void progressUserNumber(boolean hasFocus) {
        if (!hasFocus) {
            if (userPassword.getText().length() < 6) {
//                Toast.makeText(this, "请输入6-16位密码", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
