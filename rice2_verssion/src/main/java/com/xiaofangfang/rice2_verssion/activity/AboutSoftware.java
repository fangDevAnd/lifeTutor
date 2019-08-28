package com.xiaofangfang.rice2_verssion.activity;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.xiaofangfang.rice2_verssion.ParentActivity;
import com.xiaofangfang.rice2_verssion.R;
import com.xiaofangfang.rice2_verssion.model.Update;
import com.xiaofangfang.rice2_verssion.network.NetRequest;
import com.xiaofangfang.rice2_verssion.service.DownloadService;
import com.xiaofangfang.rice2_verssion.tool.DialogTool;
import com.xiaofangfang.rice2_verssion.tool.Tools;
import com.xiaofangfang.rice2_verssion.view.CommandBar;

import java.io.IOException;

import okhttp3.Response;

public class AboutSoftware extends ParentActivity {


    public static String weiboAddress = "https://weibo.com/p/1005057042935673?is_all=1";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.about_software);
        super.onCreate(savedInstanceState);

    }


    DialogTool<String> dialogTool;

    @Override
    public void initView() {
        CommandBar com = findViewById(R.id.commandBar);
        com.setTitle("关于");
        String url = NetRequest.productFilterList + "?class=" + getClass().getSimpleName() + "&version=" + Tools.getSoftWareVersion(this);

        findViewById(R.id.checkUpdate).setOnClickListener((v) -> {
            //发送网络请求
//            loadData(url, "10");//暂时不去处理
            //弹出窗口进行更新检测
            //已经是最新版本  弹出窗口
            DialogTool<String> dialogTool = new DialogTool<String>() {
                @Override
                public void bindView(DialogTool<String> d, Dialog dialog, String... t) {

                    d.setClickListener(R.id.cancel, (v) -> {
                        dialog.cancel();
                    });
                    d.setText(R.id.title, t[0]);
                    d.setText(R.id.content, t[1]);
                    d.setClickListener(R.id.yes, (v) -> {
                        dialog.cancel();
                    });
                }
            };
            Dialog dialog = dialogTool.getDialog(AboutSoftware.this,
                    R.layout.warn_dialog_template, "软件更新", "当前是已经是最新版本");
            dialog.show();
        });

        findViewById(R.id.weibo).setOnClickListener((v) -> {
            Uri uri = Uri.parse(weiboAddress);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        });

        findViewById(R.id.wechat).setOnClickListener((v) -> {
            Intent intent = new Intent(this, WeChatAQQCode.class);
            intent.putExtra(WeChatAQQCode.IMG_DATA, R.drawable.weichat_code);
            startActivity(intent);
        });

        findViewById(R.id.qq).setOnClickListener((v) -> {
            Intent intent = new Intent(this, WeChatAQQCode.class);
            intent.putExtra(WeChatAQQCode.IMG_DATA, R.drawable.qq_code);
            startActivity(intent);
        });

    }


    @Override
    public void onSucessful(Response response, String what, String... backData) throws IOException {
        //这里我们规定返回的数据的组成结构    update  {version,url}
        super.onSucessful(response, what);
        if (what.equals("10")) {
            Log.d("test", "当前的软件版本=" + Tools.getSoftWareVersion(this));

            Gson gson = new Gson();
            Update up = gson.fromJson(backData[0], Update.class);

            if (up.getVersion() == Tools.getSoftWareVersion(this)) {
                //已经是最新版本  弹出窗口
                DialogTool<String> dialogTool = new DialogTool<String>() {
                    @Override
                    public void bindView(DialogTool<String> d, Dialog dialog, String... t) {

                        d.setClickListener(R.id.cancel, (v) -> {
                            dialog.cancel();
                        });
                        d.setText(R.id.title, t[0]);
                        d.setText(R.id.content, t[1]);
                        d.setClickListener(R.id.yes, (v) -> {
                            dialog.cancel();
                        });
                    }
                };
                Dialog dialog = dialogTool.getDialog(AboutSoftware.this,
                        R.layout.warn_dialog_template, "软件更新", "当前是已经是最新版本");
                dialog.show();
            } else {
                //弹出窗口询问是否需要更新
                DialogTool<String> dialogTool = new DialogTool<String>() {
                    @Override
                    public void bindView(DialogTool<String> d, Dialog dialog, String... t) {

                        d.setClickListener(R.id.cancel, (v) -> {
                            dialog.cancel();
                        });
                        d.setText(R.id.title, t[0]);
                        d.setText(R.id.content, t[1]);
                        d.setClickListener(R.id.yes, (v) -> {
                            //确定更新,开启服务

                            Intent intent = new Intent(AboutSoftware.this, DownloadService.class);
                            intent.putExtra(DownloadService.URL, up.getUrl());
                            startService(intent);
                        });
                    }
                };
                Dialog dialog = dialogTool.getDialog(AboutSoftware.this,
                        R.layout.warn_dialog_template, "软件更新", "存在新的版本,你是否需要进行更新?");
                dialog.show();
            }
        }
    }

    @Override
    public void onError(IOException e, String what) {
        super.onError(e, what);
        Toast.makeText(this, "发生网络错误", Toast.LENGTH_SHORT).show();
    }
}
