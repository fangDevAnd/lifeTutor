package com.xiaofangfang.rice2_verssion.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.annotation.Nullable;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.xiaofangfang.rice2_verssion.ParentActivity;
import com.xiaofangfang.rice2_verssion.R;
import com.xiaofangfang.rice2_verssion.view.CommandBar;

/**
 * 自己的详情信息的界面，提供了对自己头像的修改
 * 个人信息的修改等
 */
public class SelfDetailInfoActivity extends ParentActivity {


    private SharedPreferences userInfo;
    public static final String USER = "user";

    public static final String NIKE_NAME = "nikeName";
    public static final String AREA = "area";
    public static final String SEX = "sex";
    public static final String MARK = "mark";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.self_detail_info_sub_activity_layout);

        userInfo = getSharedPreferences(USER, MODE_PRIVATE);

        super.onCreate(savedInstanceState);
        if (!getLoginStatus(this)) {
            finish();
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            return;
        }
    }

    @Override
    public void initView() {

        CommandBar com = findViewById(R.id.commandBar);
        com.setTitle("个人信息");
        EditText nikeNameEdit = findViewById(R.id.nikeNameEdit);
        EditText areaEdit = findViewById(R.id.areaEdit);
        EditText sexEdit = findViewById(R.id.sexEdit);
        EditText mark = findViewById(R.id.mark);

        nikeNameEdit.setText(userInfo.getString(NIKE_NAME, ""));
        areaEdit.setText(userInfo.getString(AREA, ""));
        sexEdit.setText(userInfo.getString(SEX, ""));
        mark.setText(userInfo.getString(MARK, ""));


        TextView userId = findViewById(R.id.userId);
        userId.setText(getUserId(this));


        findViewById(R.id.submit).setOnClickListener((v) -> {

            SharedPreferences.Editor editor = userInfo.edit();
            editor.putString(NIKE_NAME, nikeNameEdit.getText().toString());
            editor.putString(AREA, areaEdit.getText().toString());
            editor.putString(SEX, sexEdit.getText().toString());
            editor.putString(MARK, mark.getText().toString());
            editor.commit();

            Toast.makeText(this, "提交成功", Toast.LENGTH_SHORT).show();

        });

    }
}
