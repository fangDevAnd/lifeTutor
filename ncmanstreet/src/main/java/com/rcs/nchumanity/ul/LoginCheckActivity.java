package com.rcs.nchumanity.ul;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.rcs.nchumanity.entity.PersistenceData;
import com.rcs.nchumanity.tool.Tool;

import static com.rcs.nchumanity.tool.Tool.LOGIN_REQUEST_CODE;

/**
 * 登录状态检测的Activity
 */
public class LoginCheckActivity extends ParentActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Tool.loginCheck(this);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == LOGIN_REQUEST_CODE
                && resultCode == RESULT_OK) {

        }
    }
}
