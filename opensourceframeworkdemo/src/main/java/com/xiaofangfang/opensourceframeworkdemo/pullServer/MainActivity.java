package com.xiaofangfang.opensourceframeworkdemo.pullServer;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.xiaofangfang.opensourceframeworkdemo.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import rx.internal.schedulers.NewThreadWorker;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pullserver_main);
        initView();
        EventBus.getDefault().register(this);
    }

    private void initView() {


        accountId = getIntent().getStringExtra("id");

        id = findViewById(R.id.textInputEditText);
        content = findViewById(R.id.textInputEditText1);
        message = findViewById(R.id.textView3);

        findViewById(R.id.button3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userId = id.getText().toString();
                String contents = content.getText().toString();
                if (TextUtils.isEmpty(userId) || TextUtils.isEmpty(contents)) {
                    Toast.makeText(MainActivity.this, "输入完整的信息", Toast.LENGTH_SHORT).show();
                    return;
                }

                delivery(accountId, userId, contents);

            }
        });


    }

    private void delivery(String sender, String receiver, String contents) {

        FormBody formBody = new FormBody.Builder()
                .add("sender", sender)
                .add("receiver", receiver)
                .add("content", contents).build();


        OkHttpClient okHttpClient = new OkHttpClient();

        Request request = new Request.Builder()
                .url(url)
                .post(formBody).build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MainActivity.this, "发生错误" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                String respData = response.body().string();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MainActivity.this, respData, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }

    //服务器地址
    private final static String url = "http://192.168.42.165:8080/XinGePullServer_war_exploded/pull";

    private TextInputEditText id;
    private TextInputEditText content;

    private TextView message;

    private String accountId;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }



    @Subscribe(threadMode = ThreadMode.POSTING)
    public void onEvent(MessageEvent event) {
        String text = event.getSender() + "\n" +
                "留言内容\n" +
                event.getContent() + "\n" +
                "留言时间" + event.getDate();
        message.setText(text);
    }


}

