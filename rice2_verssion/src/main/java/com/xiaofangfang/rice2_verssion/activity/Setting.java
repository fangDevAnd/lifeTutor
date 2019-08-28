package com.xiaofangfang.rice2_verssion.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;

import android.widget.Toast;

import com.xiaofangfang.rice2_verssion.ParentActivity;
import com.xiaofangfang.rice2_verssion.R;
import com.xiaofangfang.rice2_verssion.tool.DataCleanManager;
import com.xiaofangfang.rice2_verssion.tool.DialogTool;
import com.xiaofangfang.rice2_verssion.view.CommandBar;

public class Setting extends ParentActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.system_setting_sub_activity_layout);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initView() {
        CommandBar com = findViewById(R.id.commandBar);
        com.setTitle("设置");

        findViewById(R.id.aboutRice).setOnClickListener((v) -> {
            Intent intent = new Intent(Setting.this, AboutSoftware.class);
            startActivity(intent);
        });

        findViewById(R.id.offline).setOnClickListener((v) -> {
            //检测登录状态
            if (getLoginStatus(this)) {//登录

                DialogTool<String> dialogTool = new DialogTool<String>() {

                    @Override
                    public void bindView(DialogTool<String> d, Dialog dialog, String... t) {
                        d.setClickListener(R.id.cancel, (v) -> {
                            dialog.cancel();
                        });
                        d.setClickListener(R.id.yes, (v) -> {
                            offline(Setting.this);
                            finish();
                        });
                    }
                };
                Dialog dialog = dialogTool.getDialog(Setting.this, R.layout.warn_dialog_template);

                dialog.show();

            } else {
                Toast.makeText(this, "暂不在线", Toast.LENGTH_SHORT).show();
            }


            //在线   -->退出登录
            //不在线  -->toast
        });

        findViewById(R.id.clearCache).setOnClickListener((v) -> {
            DataCleanManager.cleanApplicationData(Setting.this);
            Toast.makeText(this, "清理完成", Toast.LENGTH_SHORT).show();
        });

        findViewById(R.id.addressManager).setOnClickListener((v) -> {
            Intent intent = new Intent(Setting.this, AddressManager.class);
            startActivity(intent);
        });

        findViewById(R.id.notiOpen).setOnClickListener((v) -> {
            //打开热门推送
            //空实现,毕竟没有设么好推送的
        });


    }
}
