package com.xiaofangfang.rice2_verssion.Fragment;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.xiaofangfang.rice2_verssion.ParentActivity;
import com.xiaofangfang.rice2_verssion.R;
import com.xiaofangfang.rice2_verssion.activity.AboutSoftware;
import com.xiaofangfang.rice2_verssion.activity.AddvanceBack;
import com.xiaofangfang.rice2_verssion.activity.SelfDetailInfoActivity;
import com.xiaofangfang.rice2_verssion.activity.Setting;
import com.xiaofangfang.rice2_verssion.model.Update;
import com.xiaofangfang.rice2_verssion.network.NetRequest;
import com.xiaofangfang.rice2_verssion.service.DownloadService;
import com.xiaofangfang.rice2_verssion.tool.DialogTool;
import com.xiaofangfang.rice2_verssion.tool.Tools;

import java.io.IOException;

import okhttp3.Response;

public class MeFragment extends ParentFragment {


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.me_fragment, null, false);
    }

    Class[] tClass = {
            SelfDetailInfoActivity.class,
            Setting.class,
            AboutSoftware.class,
            AddvanceBack.class,
    };


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        View[] funcs = {
                view.findViewById(R.id.userCenter),
                view.findViewById(R.id.setting),
                view.findViewById(R.id.about),
                view.findViewById(R.id.addva),
        };

        for (int i = 0; i < funcs.length; i++) {
            int finalI = i;
            funcs[i].setOnClickListener((v) -> {
                Intent intent = new Intent(getActivity(), tClass[finalI]);
                startActivity(intent);
            });
        }

        view.findViewById(R.id.verUpg).setOnClickListener((v) -> {
            String url = NetRequest.APP_LAYOUT_SERVER_URL + "?class=" + getClass().getSimpleName() + "&version=" + Tools.getSoftWareVersion(getContext());
//            loadData(url, "10");
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
            Dialog dialog = dialogTool.getDialog(getActivity(),
                    R.layout.warn_dialog_template, "软件更新", "当前是已经是最新版本");
            dialog.show();
        });

        //进行登录的检测
        user = view.findViewById(R.id.user);
        user.setText(ParentActivity.getUserId(getMyActivity()));

    }

    @Override
    public void onError(IOException e, String what) {

    }

    @Override
    public void onSucess(Response response, String what, String... backData) throws IOException {
        //这里我们规定返回的数据的组成结构    update  {version,url}
        if (what.equals("10")) {
            Log.d("test", "当前的软件版本=" + Tools.getSoftWareVersion(getContext()));

            Gson gson = new Gson();
            Update up = gson.fromJson(backData[0], Update.class);

            if (up.getVersion() == Tools.getSoftWareVersion(getContext())) {
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
                Dialog dialog = dialogTool.getDialog(getContext(),
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

                            Intent intent = new Intent(getContext(), DownloadService.class);
                            intent.putExtra(DownloadService.URL, up.getUrl());
                            getMyActivity().startService(intent);
                        });
                    }
                };
                Dialog dialog = dialogTool.getDialog(getContext(),
                        R.layout.warn_dialog_template, "软件更新", "存在新的版本,你是否需要进行更新?");
                dialog.show();
            }
        }
    }

}
