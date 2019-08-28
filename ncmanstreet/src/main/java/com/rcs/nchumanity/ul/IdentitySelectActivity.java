package com.rcs.nchumanity.ul;


import android.app.Dialog;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.rcs.nchumanity.R;
import com.rcs.nchumanity.dialog.DialogTool;
import com.rcs.nchumanity.tool.Tool;

import butterknife.OnClick;

/**
 * 救护培训的身份验证界面的实现
 */
public class IdentitySelectActivity extends ParentActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_identity_select);
    }

    @OnClick({R.id.identity_1, R.id.identity_2})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.identity_1:

                Tool.startActivity(this, IdentityInfoRecordActivity.class);

                break;

            case R.id.identity_2:

                DialogTool<String> dialogTool = new DialogTool<String>() {
                    @Override
                    public void bindView(DialogTool<String> d, Dialog dialog, String... t) {

                    }
                };
                Dialog dialog = dialogTool.getDialog(this, R.layout.dialog_train_number_input);
                dialog.show();
                break;
        }
    }

}
