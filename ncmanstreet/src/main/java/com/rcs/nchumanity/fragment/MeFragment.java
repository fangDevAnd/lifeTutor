package com.rcs.nchumanity.fragment;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.rcs.nchumanity.R;
import com.rcs.nchumanity.entity.BasicResponse;
import com.rcs.nchumanity.entity.NetConnectionUrl;
import com.rcs.nchumanity.entity.PersistenceData;
import com.rcs.nchumanity.net.NetRequest;
import com.rcs.nchumanity.tool.RealPathFromUriUtils;
import com.rcs.nchumanity.tool.Tool;
import com.rcs.nchumanity.ul.AssessResultActivity;
import com.rcs.nchumanity.ul.ElectronicCertificateActivity;
import com.rcs.nchumanity.ul.EntityCertificateActivity;
import com.rcs.nchumanity.ul.MyCourseActivity;
import com.rcs.nchumanity.ul.RegisterUserActivity;
import com.rcs.nchumanity.ul.SettingActivity;


import org.apache.commons.codec.binary.StringUtils;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Response;

import static android.app.Activity.RESULT_OK;

/**
 * 我的的界面的显示
 */
public class MeFragment extends BasicResponseProcessHandleFragment {


    private static final String TAG = "test";
    @BindView(R.id.picture)
    ImageView photo;//用户头像

    @BindView(R.id.userName)
    TextView userName;


    public static final int REQUEST_CODE_PHOTO = 1;


    @Override
    protected int getCreateView() {
        return R.layout.fragment_me;
    }


    public void updateUserData() {
        String userNameS = PersistenceData.getNickName(getContext());

        if (!PersistenceData.DEF_USER.equals(PersistenceData.getNickName(getContext()))) {
            userName.setText(userNameS);
        } else {
            userName.setText("注册登录");
        }

        String pictureUri = (String) PersistenceData.getPicture(getContext());
        if (!PersistenceData.DEF_VAL.equals(pictureUri)) {
            if (pictureUri.startsWith("https:") || pictureUri.startsWith("http")) {
                Glide.with(getContext()).load(pictureUri).into(photo);
            } else {
                //加载本地数据
                Glide.with(getContext()).load(new File(pictureUri)).into(photo);
            }
        } else {
            Glide.with(getContext()).load(R.drawable.ic_logo).into(photo);
        }
    }

    @Override
    protected void onLoadData() {
        super.onLoadData();
        updateUserData();
    }

    private int clickStep;

    @OnClick({R.id.meCourse, R.id.picture, R.id.assessResult,
            R.id.elecCertificate, R.id.entityCertificate,
            R.id.setting, R.id.userName})
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.meCourse:

                String param = NetConnectionUrl.getSignInStatus;
                clickStep = R.id.meCourse;
                loadDataGet(param, "getSignInStatus");

                break;

            case R.id.picture:

//                if (!Tool.loginCheck(getActivity())) {
//                    return;
//                }

                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, REQUEST_CODE_PHOTO);


                break;

            case R.id.assessResult:

//                if (!Tool.loginCheck(getActivity())) {
//                    return;
//                }

                clickStep = R.id.assessResult;
                loadDataGet(NetConnectionUrl.getSignInStatus, "getSignInStatus");



                break;

            case R.id.elecCertificate:
//                if (!Tool.loginCheck(getActivity())) {
//                    return;
//                }

                clickStep = R.id.elecCertificate;
                loadDataGet(NetConnectionUrl.getSignInStatus, "getSignInStatus");


                break;

            case R.id.entityCertificate:

                Tool.startActivity(getContext(), EntityCertificateActivity.class);


                break;

            case R.id.setting:

                Tool.startActivity(getActivity(), SettingActivity.class);

                break;

            case R.id.userName:
                if (!Tool.loginCheck(getActivity())) {
                    return;
                }
                break;
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.d(TAG, "onActivityResult: " + resultCode);

        switch (requestCode) {
            case REQUEST_CODE_PHOTO:
                if (resultCode == RESULT_OK) {

                    doChangePicture(data);
                } else {
                    Toast.makeText(getActivity(), "修改头像失败", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }


    private String realPathFromUri;

    /**
     * @param data
     */
    private void doChangePicture(Intent data) {

        Uri uri = data.getData();
        try {
            InputStream is = getActivity().getContentResolver().openInputStream(uri);

            Bitmap bitmap = BitmapFactory.decodeStream(is);

            photo.setImageBitmap(bitmap);

            realPathFromUri = RealPathFromUriUtils.getRealPathFromUri(getContext()
                    , data.getData());

            Log.d(TAG, "doChangePicture: " + realPathFromUri);

            NetRequest.setImageName("userPic");
            loadDataPostImg(
                    NetConnectionUrl.updateUserPic,
                    "updateUserPic",
                    realPathFromUri, new HashMap<>());


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
    }



    @Override
    protected void responseDataSuccess(String what, String backData, Response response, BasicResponse... br) throws Exception {
        super.responseDataSuccess(what, backData, response, br);

        if (what.equals("updateUserPic")) {
            new AlertDialog.Builder(getContext())
                    .setTitle("提示")
                    .setMessage("修改头像成功")
                    .setPositiveButton("确定", (dialog, which) -> {
                        dialog.dismiss();
                    });

            PersistenceData.setUserPicture(getContext(), realPathFromUri);

            updateUserData();
            /**
             * 修改头像之后，成功后需要将数据重新返回
             */
        } else if (what.equals("getSignInStatus")) {

            try {
                JSONObject jsonObject = new JSONObject(backData);
                JSONObject data = null;
                if (jsonObject.has("object")) {
                    data = jsonObject.getJSONObject("object");
                } else if (jsonObject.has("data")) {
                    data = jsonObject.getJSONObject("data");
                }
                int studyStatus = data.getInt("studyStatus");

                if(clickStep==R.id.meCourse) {
                    if (studyStatus > 1) {
                        Tool.startActivity(getContext(), MyCourseActivity.class);
                    }else {
                        super.responseWith201_202(what,new BasicResponse(201,"先报名",null));
                    }
                }


                if(clickStep==R.id.assessResult){

                    if(studyStatus>=4){
                        Intent intent1 = new Intent(getActivity(), AssessResultActivity.class);
                        startActivity(intent1);
                    }else {
                        super.responseWith201_202(what,new BasicResponse(201,BasicResponse.NOT_REQUIRED_MESSAGE,null));
                    }
                }

                if(clickStep==R.id.elecCertificate){
                    if(studyStatus>=7){
                        Intent intent2 = new Intent(getActivity(), ElectronicCertificateActivity.class);
                        startActivity(intent2);
                    }else {
                        super.responseWith201_202(what,new BasicResponse(201,BasicResponse.NOT_REQUIRED_MESSAGE,null));
                    }
                }


            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

}
