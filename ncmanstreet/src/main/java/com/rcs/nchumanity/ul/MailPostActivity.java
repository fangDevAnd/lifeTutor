package com.rcs.nchumanity.ul;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import com.google.gson.Gson;
import com.rcs.nchumanity.R;
import com.rcs.nchumanity.entity.BasicResponse;
import com.rcs.nchumanity.entity.NetConnectionUrl;
import com.rcs.nchumanity.entity.PersistenceData;
import com.rcs.nchumanity.tool.Tool;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Response;

/**
 * 邮寄的证书
 */
public class MailPostActivity extends BasicResponseProcessHandleActivity {


    @BindView(R.id.realName)
    EditText realName;

    @BindView(R.id.mobilePhone)
    EditText mobilePhone;

    @BindView(R.id.address)
    EditText address;


    public static final String NAME = "name";

    public static final String MOBILE_PHONE = "mobilePhone";

    public static final String ADDRESS = "address";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mail_post);
        ButterKnife.bind(this);


//        Bundle obj = getIntent().getExtras();
//
//        String name = obj.getString("name");
        String mobilePhone = PersistenceData.getPhoneNumber(this);
//        String address = obj.getString("address");
//
//        realName.setText(name);
        this.mobilePhone.setText(mobilePhone);
//        this.address.setText(address);


    }

    private boolean isClick = false;

    @OnClick(R.id.mail)
    public void onClick(View view) {
        if (!isClick) {
            isClick=true;
            Map<String, String> map = new HashMap<>();
            map.put("address", address.getText().toString());
            map.put("mobilephone", mobilePhone.getText().toString());
            map.put("name", mobilePhone.getText().toString());
            loadDataPost(NetConnectionUrl.doUnifiedOrder, "savePostInfo", map);
        }
    }


    @Override
    protected void responseDataSuccess(String what, String backData, Response response, BasicResponse... br) throws Exception {
        super.responseDataSuccess(what, backData, response, br);
        if (what.equals("savePostInfo")) {
            isClick = false;
            new AlertDialog.Builder(this)
                    .setTitle("提示")
                    .setMessage(br[0].message)
                    .setPositiveButton("确定", (dialog, which) -> {
                        dialog.dismiss();
                        finish();
                    }).create().show();
        }
    }
}
