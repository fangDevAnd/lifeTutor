package com.xiaofangfang.opensourceframeworkdemo.pullServer;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.Toast;

import com.tencent.android.tpush.XGIOperateCallback;
import com.tencent.android.tpush.XGPushManager;
import com.xiaofangfang.opensourceframeworkdemo.R;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
    }

    private void initView() {


        EditText editText = findViewById(R.id.editText);

        findViewById(R.id.button2).setOnClickListener((v) -> {
            String id = editText.getText().toString();
            if (TextUtils.isEmpty(id)) {
                Toast.makeText(this, "ID不能为空", Toast.LENGTH_SHORT).show();
                return;
            }
            setAccound(id);
        });
    }

    private void setAccound(String id) {

        XGPushManager.bindAccount(this, id, new XGIOperateCallback() {
            @Override
            public void onSuccess(Object o, int i) {
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                intent.putExtra("id", id);
                startActivity(intent);
                finish();
            }

            @Override
            public void onFail(Object o, int i, String s) {
                Toast.makeText(LoginActivity.this, "失败" + s, Toast.LENGTH_SHORT).show();
            }
        });

    }
}
