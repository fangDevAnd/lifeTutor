package com.xiaofangfang.filterrice.Activity;

import android.app.AlertDialog;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.xiaofangfang.filterrice.R;

public class DialogTestActivity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_test_activity);

        findViewById(R.id.start).setOnClickListener((v) -> {

            AlertDialog.Builder builder = new AlertDialog.Builder(DialogTestActivity.this);
            builder.setTitle("温馨提示")
                    .setMessage("关注信息").create().show();
        });


    }
}
