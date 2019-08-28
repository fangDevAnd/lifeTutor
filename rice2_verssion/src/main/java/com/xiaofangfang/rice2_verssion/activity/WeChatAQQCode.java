package com.xiaofangfang.rice2_verssion.activity;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.widget.ImageView;

import com.xiaofangfang.rice2_verssion.ParentActivity;
import com.xiaofangfang.rice2_verssion.R;

public class WeChatAQQCode extends ParentActivity {


    public static final String IMG_DATA = "imgResId";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.wechat_qq_code_layout);
        super.onCreate(savedInstanceState);
        try {
            initData();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Intent intent;

    private void initData() throws Exception {
        intent = getIntent();
        int imgResId = intent.getIntExtra(IMG_DATA, -1);
        if (imgResId == -1) {
            throw new ImgResNotFoundException();
        }
        imageView.setImageResource(imgResId);


    }

    private ImageView imageView;

    @Override
    public void initView() {

        imageView = findViewById(R.id.code_image);
    }


    public class ImgResNotFoundException extends Exception {

        @Override
        public void printStackTrace() {
            System.err.println("image address not found");
        }
    }

}
