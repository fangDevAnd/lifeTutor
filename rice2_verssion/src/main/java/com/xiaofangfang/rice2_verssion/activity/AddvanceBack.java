package com.xiaofangfang.rice2_verssion.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.Toast;

import com.xiaofangfang.rice2_verssion.ParentActivity;
import com.xiaofangfang.rice2_verssion.R;
import com.xiaofangfang.rice2_verssion.network.NetRequest;
import com.xiaofangfang.rice2_verssion.tool.DialogTool;
import com.xiaofangfang.rice2_verssion.tool.Looger;
import com.xiaofangfang.rice2_verssion.view.CommandBar;

import java.io.IOException;

import okhttp3.Response;

public class AddvanceBack extends ParentActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.addvance_back);
        super.onCreate(savedInstanceState);
    }


    EditText addv_eidt;

    @Override
    public void initView() {

        CommandBar com = findViewById(R.id.commandBar);
        com.setTitle("反馈");

        addv_eidt = findViewById(R.id.addv_eidt);

        findViewById(R.id.submit).setOnClickListener((v) -> {

            if (!getLoginStatus(AddvanceBack.this)) {
                Intent intent = new Intent(AddvanceBack.this, LoginActivity.class);
                startActivity(intent);
            }

            if (TextUtils.isEmpty(addv_eidt.getText())) {
                Toast.makeText(this, "请输入内容!", Toast.LENGTH_SHORT).show();
            } else {
                String url = NetRequest.productSalePageAction + "?class=" + getClass().getSimpleName()
                        + "&user=" + getUserId(AddvanceBack.this) + "&content=" + addv_eidt.getText();
                Looger.D("请求的url=" + url);
                loadData(url, "23");
            }
        });
    }


    @Override
    public void onSucessful(Response response, String what, String... backData) throws IOException {
        super.onSucessful(response, what);

        addv_eidt.setText("");

        if (what.equals("23")) {
            DialogTool<String> dialogTool = new DialogTool<String>() {
                @Override
                public void bindView(DialogTool<String> d, Dialog dialog, String... t) {
                    d.setClickListener(R.id.close, (v) -> {
                        dialog.cancel();
                    });

                    d.setText(R.id.oprateTip, t[0]);
                    d.setClickListener(R.id.button, (v) -> {
                        dialog.cancel();
                        finish();
                    });
                }
            };

            Dialog dialog = dialogTool.getDialog(AddvanceBack.this, R.layout.oprate_facenack, "提交成功");
            dialog.show();
        }


    }


    @Override
    public void onError(IOException e, String what) {
        super.onError(e, what);

    }
}
