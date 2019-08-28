package com.xiaofangfang.rice2_verssion.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.xiaofangfang.rice2_verssion.ParentActivity;
import com.xiaofangfang.rice2_verssion.R;
import com.xiaofangfang.rice2_verssion.network.NetRequest;
import com.xiaofangfang.rice2_verssion.tool.Crypto;
import com.xiaofangfang.rice2_verssion.tool.LoadProgress;
import com.xiaofangfang.rice2_verssion.tool.Looger;
import com.xiaofangfang.rice2_verssion.tool.SystemSet;
import com.xiaofangfang.rice2_verssion.tool.UiThread;
import com.xiaofangfang.rice2_verssion.view.CommandBar;

import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class LoginActivity extends ParentActivity implements View.OnClickListener, View.OnFocusChangeListener {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.login);
        super.onCreate(savedInstanceState);
    }


    private Button loginButton;
    private EditText userNumber, userPassword;
    private TextView useclause, forgetpassword;

    @Override
    public void initView() {

        loginButton = findViewById(R.id.loginButton);

        userNumber = findViewById(R.id.userNumber);
        userPassword = findViewById(R.id.userPassword);

        useclause = findViewById(R.id.useclause);
        forgetpassword = findViewById(R.id.forgetpassword);

        //添加点击事件
        loginButton.setOnClickListener(this);
        useclause.setOnClickListener(this);
        forgetpassword.setOnClickListener(this);

        userNumber.setOnFocusChangeListener(this);
        userPassword.setOnFocusChangeListener(this);

        CommandBar com = findViewById(R.id.commandBar);
        com.setTitle("登录");
        com.setMenu(R.drawable.ic_user, new CommandBar.MenuClickListener() {
            @Override
            public void click(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterUserActivity.class);
                startActivity(intent);
            }
        });


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.loginButton:
                progressLoginButton();
                break;
            case R.id.useclause:
                progressUseclause();
                break;
            case R.id.forgetpassword:
                progressForgetpassword();
                break;
        }
    }

    private void progressForgetpassword() {

    }

    private void progressUseclause() {

    }


    ProgressBar progressBar;

    /**
     * 进行登录界面
     */
    private void progressLoginButton() {
        loginButton.setClickable(false);

        //进行登录,登录成功后进入流量通话时间过滤的界面
        final String userPhone = userNumber.getText().toString();
        String password = userPassword.getText().toString();
        if (TextUtils.isEmpty(userPhone) || userPhone.length() != 11) {
            Toast.makeText(this, "请输入合法的手机号码", Toast.LENGTH_SHORT).show();
            return;
        }
        //在这里开启一个加载的框,用来实现加载
        progressBar = LoadProgress.loadProgress(this);

        String passwd = Crypto.getMD5(password);
        final String param = "name=" + userPhone + "&password=" + passwd;
        //请求登录

        new Thread(new Runnable() {
            @Override
            public void run() {
                NetRequest.requestUrl(NetRequest.LOGIN_SERVER_URL + "?" + param, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        UiThread.getUiThread().post(new Runnable() {
                            @Override
                            public void run() {
                                loginButton.setClickable(true);
                                Toast.makeText(LoginActivity.this, "请求失败", Toast.LENGTH_SHORT).show();
                                LoadProgress.removeLoadProgress(LoginActivity.this, progressBar);
                            }
                        });

                    }

                    @Override
                    public void onResponse(Call call, final Response response) throws IOException {
                        final String jsonData = response.body().string();
                        UiThread.getUiThread().post(new Runnable() {
                            @Override
                            public void run() {
                                loginButton.setClickable(true);
                                LoadProgress.removeLoadProgress(LoginActivity.this, progressBar);
                                JSONObject jsonObject;
                                int responseCode = 0;
                                String responseContent;
                                try {
                                    jsonObject = new JSONObject(jsonData);
                                    responseCode = jsonObject.getInt("responseCode");
                                    responseContent = jsonObject.getString("responseContent");
                                    Toast.makeText(LoginActivity.this, "" + responseContent, Toast.LENGTH_SHORT).show();
                                    Looger.D(responseContent);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                if (responseCode == 3) {
                                    //代表的是登录成功,结束当前的界面,然后跳转到下一界面
                                    //写入默认登录状态的值
                                    changeDefaultLoginState(userPhone);

                                    //跳转到上一界面
                                    finish();
                                }

                            }
                        });
                    }

                });
            }
        }).start();

    }

    /**
     * 对系统的默认登录状态进行调整
     */
    private void changeDefaultLoginState(String userPhone) {
        SharedPreferences.Editor editor = getMyApplication().setting.edit();
        editor.putBoolean(SystemSet.LOGIN_STATUS, true);
        editor.putString(SystemSet.USER_ID, userPhone);
        editor.commit();

    }

    private void progressRegisterButton() {
        Intent intent = new Intent(this, RegisterUserActivity.class);
        startActivity(intent);
    }

    private void progressBackPage() {

    }


    /**
     * 焦点事件的处理
     *
     * @param v
     * @param hasFocus
     */
    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        switch (v.getId()) {
            case R.id.userNumber:
                if (!hasFocus) {
                    String userPhone = userNumber.getText().toString();
                    if (TextUtils.isEmpty(userPhone) || userPhone.length() != 11) {
                        Toast.makeText(this, "请输入合法的手机号码", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                break;

            case R.id.userPassword:
                if (!hasFocus) {
                    if (userPassword.getText().length() < 6) {
                        Toast.makeText(this, "请输入6-16位密码", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
        }
    }


    /**
     * 由于我们退出登录的时候也会跳转到当前的界面,但是这时候我们点击返回需要退到主页面,需要通过parentActivity进行activity的收集
     * 判断内部是否有setting的实例,如果有,需要进行移出
     */
    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }
}
