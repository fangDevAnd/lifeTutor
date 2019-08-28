package com.rcs.nchumanity.ul;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.google.gson.Gson;
import com.rcs.nchumanity.R;
import com.rcs.nchumanity.entity.BasicResponse;
import com.rcs.nchumanity.entity.NetConnectionUrl;
import com.rcs.nchumanity.entity.PersistenceData;
import com.rcs.nchumanity.entity.complexModel.ComplexModelSet;
import com.rcs.nchumanity.tool.Tool;

import org.json.JSONObject;

import java.io.IOException;

import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Response;

/**
 * 实体证书
 */
public class EntityCertificateActivity extends BasicResponseProcessHandleActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entity_certificate);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.ziqu, R.id.youji})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ziqu:
                Intent intent = new Intent(this, SelfTakeActivity.class);
                startActivity(intent);
                break;
            case R.id.youji:

                /**
                 * 确定用户考核状态，必须是已完成线下考核
                 */

                loadDataGetForForce(NetConnectionUrl.getStatusByUser, "getStatusByUser");


                break;
        }
    }


    @Override
    protected void responseDataSuccess(String what, String backData, Response response, BasicResponse... br) throws Exception {
        super.responseDataSuccess(what, backData, response, br);


        if (what.equals("getStatusByUser")) {
            try {
                JSONObject brJ = new JSONObject(backData);
                JSONObject obj = brJ.getJSONObject("object");
                int studyStatus = obj.getInt("studyStatus");

                if (studyStatus == 7) {

                    /**
                     * 当状态码为7 的时候 ，我们先去获取邮寄状态
                     * 1
                     *
                     */
                    loadDataGetForForce(NetConnectionUrl.getPostInfoByUser, "getPostInfoByUser");

                } else {
                    new AlertDialog.Builder(this)
                            .setTitle("提示")
                            .setMessage("暂未达到要求，请完成相关培训后重试")
                            .setPositiveButton("确定", (dialog, which) -> {

                            }).create().show();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (what.equals("getPostInfoByUser")) {

            if (br[0].object == null) {
                //代表没有邮寄信息
                Tool.startActivity(this, MailPostActivity.class);

            } else {
                try {
                    JSONObject brJ = new JSONObject(backData);
                    JSONObject obj = brJ.getJSONObject("object");

                    String name = obj.getString("name");
                    String mobilePhone = obj.getString("mobilephone");
                    String address = obj.getString("address");
                    String expressNo = obj.getString("expressNo");
                    String expressCompany = obj.getString("expressCompany");
                    String expressStatus = obj.getInt("expressStatus") == 0 ? "没有发出" : "已发出";

                    ComplexModelSet.PostInfo postInfo
                            = new ComplexModelSet.PostInfo(name, mobilePhone, address, expressNo, expressCompany, expressStatus);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(MailPostStatusActivity.DATA, postInfo);
                    Tool.startActivity(this, MailPostStatusActivity.class, bundle);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }

//        else if (what.equals("getSignUpInfoByUser")) {
//
//            try {
//                JSONObject brJ = new JSONObject(backData);
//                JSONObject obj = brJ.getJSONObject("object");
//
//                String name = obj.getString("name");
//                String mobilePhone = obj.getString("mobilephone");
//                String address = obj.getString("address");
//
//
//                Bundle bundle = new Bundle();
//                bundle.putString(MailPostActivity.NAME, name);
//                bundle.putString(MailPostActivity.MOBILE_PHONE, mobilePhone);
//                bundle.putString(MailPostActivity.ADDRESS, address);
//
//                Tool.startActivity(this, MailPostActivity.class, bundle);
//
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        } else if (what.equals("getPostStatus")) {
//
//            /**
//             * 代表的是没有填写邮寄信息
//             * 加载用户报名信息
//             */
//            loadDataGet(NetConnectionUrl.getSignUpInfoByUser, "getSignUpInfoByUser");
//
//        } else if (what.equals("getPostInfoByUser")) {
//
//            try {
//                JSONObject brJ = new JSONObject(backData);
//                JSONObject obj = brJ.getJSONObject("object");
//
//                String name = obj.getString("name");
//                String mobilePhone = obj.getString("mobilephone");
//                String address = obj.getString("address");
//
//
//                Bundle bundle = new Bundle();
//                bundle.putString(MailPostActivity.NAME, name);
//                bundle.putString(MailPostActivity.MOBILE_PHONE, mobilePhone);
//                bundle.putString(MailPostActivity.ADDRESS, address);
//
//                Tool.startActivity(this, MailPostActivity.class, bundle);
//
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//
//        }
    }

//    @Override
//    protected void responseWith302(String what, BasicResponse br) {
//        if (what.equals("getPostStatus")) {
//            /**
//             * 代表的是已发了证书
//             * ,进入订单详情界面
//             */
//            ComplexModelSet.PostInfo postInfo = new ComplexModelSet.PostInfo();
//
//
//        }
//    }
//
//
//    @Override
//    protected void responseWith301(String what, BasicResponse br) {
//        if (what.equals("getPostStatus")) {
//            /**
//             * 代表的是没有发出
//             */
//            loadDataGet(NetConnectionUrl.getPostInfoByUser, "getPostInfoByUser");
//        }
//    }


}
