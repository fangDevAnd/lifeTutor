package com.rcs.nchumanity.ul;

import android.app.AlertDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.google.gson.Gson;
import com.rcs.nchumanity.R;
import com.rcs.nchumanity.entity.BasicResponse;
import com.rcs.nchumanity.entity.NetConnectionUrl;
import com.rcs.nchumanity.tool.Tool;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Response;

public class ChangePasswordActivity extends BasicResponseProcessHandleActivity {


    @OnClick({R.id.submit, R.id.forgetPassword})
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.submit:

                String oldPass = String.valueOf(oldPassword.getText());
                String rePass = rePassword.getText().toString();
                String pass = password.getText().toString();

                String butter = "";

                butter += TextUtils.isEmpty(oldPass) ? "请填写旧密码\n" : "";
                butter += TextUtils.isEmpty(pass) ? "密码不能为空" : "";
                butter += pass.equals(rePass) ? "" : "两次密码不一致";

                if (!butter.equals("")) {
                    Toast.makeText(this, butter, Toast.LENGTH_SHORT).show();
                    return;
                }
                Map<String, String> map = new HashMap<>();
                map.put("newFPassword", pass);
                map.put("newSPassword", rePass);
                map.put("oldPassowrd", oldPass);
                loadDataPost(NetConnectionUrl.changePassword, "changePassword", map);

                break;

            case R.id.forgetPassword:
                Tool.startActivity(this, RegisterUserActivity.class);
                break;
        }

    }

    @BindView(R.id.oldPassword)
    EditText oldPassword;

    @BindView(R.id.password)
    EditText password;

    @BindView(R.id.rePassword)
    EditText rePassword;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_password);
        ButterKnife.bind(this);
    }


    @Override
    protected void responseWith11(String what, BasicResponse br) {
        super.responseWith11(what, br);
        oldPassword.setText("");
        password.setText("");
        rePassword.setText("");
        new AlertDialog.Builder(this)
                .setTitle("提示")
                .setMessage("修改密码成功")
                .setPositiveButton("确定", (dialog, which) -> {
                    dialog.dismiss();
                    finish();
                }).create().show();
    }


}
