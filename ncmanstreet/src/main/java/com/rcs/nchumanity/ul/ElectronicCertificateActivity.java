package com.rcs.nchumanity.ul;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.rcs.nchumanity.R;
import com.rcs.nchumanity.entity.BasicResponse;
import com.rcs.nchumanity.entity.NetConnectionUrl;
import com.rcs.nchumanity.entity.PersistenceData;
import com.rcs.nchumanity.tool.Tool;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import okhttp3.Response;

public class ElectronicCertificateActivity extends BasicResponseProcessHandleActivity {


    @BindViews({R.id.z, R.id.f, R.id.capter})
    List<ImageView> zs;

    @BindView(R.id.name)
    TextView nameT;

    @BindView(R.id.date)
    TextView date;

    @BindView(R.id.code)
    TextView code;

    @BindView(R.id.idNumber)
    TextView idNumber;

    @BindView(R.id.sex)
    TextView sex;

    @BindView(R.id.zj)
    ImageView zj;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_electronic_certificate);
        ButterKnife.bind(this);
        loadDataGetForForce(NetConnectionUrl.myCertificate, "myCertificate");
    }


    @Override
    protected void responseDataSuccess(String what, String backData, Response response, BasicResponse... br) throws Exception {
        super.responseDataSuccess(what, backData, response, br);

        if (what.equals("myCertificate")) {

            try {
                JSONObject brJ = new JSONObject(backData);

                JSONObject obj = brJ.getJSONObject("object");
                List<String> imgs = parseImgs(obj);
                String name = parseName(obj);
                String sex = parseSex(obj);
                String idNumber = parseIdNumber(obj);
                String licenseNo = parseLicenseNo(obj);
                String dateOfIssue = parseDateOfIssue(obj);
                String zj = parsePicUrl(obj);

                if (zj != null) {
                    Glide.with(this).load(zj).into(this.zj);
                }

                margeData(imgs, name, sex, idNumber, licenseNo, dateOfIssue);


            } catch (Exception e) {
                e.printStackTrace();
            }


        }


    }


    private String parsePicUrl(JSONObject obj) throws JSONException {
        JSONObject obj1 = obj.getJSONObject("UserAccount");
        if (obj1.has("picUrl")) {
            return obj1.getString("picUrl");
        }
        return null;
    }


    /**
     * 拼接数据形成
     *
     * @param imgs
     * @param name
     * @param sex
     * @param idNumber
     * @param licenseNo
     * @param dateOfIssue
     */
    private void margeData(List<String> imgs, String name, String sex, String idNumber, String licenseNo, String dateOfIssue) {

        for (int i = 0; i < imgs.size(); i++) {
            Glide.with(this).load(imgs.get(i)).into(zs.get(i));
        }
        nameT.setText(name);
        this.sex.setText(sex);
        this.idNumber.setText(idNumber);
        this.code.setText(licenseNo);
        this.date.setText(dateOfIssue);

    }

    private String parseDateOfIssue(JSONObject obj) throws JSONException {
        JSONObject obj1 = obj.getJSONObject("UserFirstAiderCertificate");
        return obj1.getString("dateOfIssue");
    }

    private String parseLicenseNo(JSONObject obj) throws JSONException {
        JSONObject obj1 = obj.getJSONObject("UserBasicInfo");
        return obj1.getString("licenseNo");
    }

    private String parseIdNumber(JSONObject obj) throws JSONException {
        JSONObject obj1 = obj.getJSONObject("UserBasicInfo");
        return obj1.getString("idNumber");
    }

    private String parseSex(JSONObject obj) throws JSONException {
        JSONObject obj1 = obj.getJSONObject("UserBasicInfo");
        return obj1.getString("gender");
    }

    private String parseName(JSONObject obj) throws JSONException {
        JSONObject obj1 = obj.getJSONObject("UserBasicInfo");
        return obj1.getString("name");
    }

    private List<String> parseImgs(JSONObject obj) throws JSONException {

        JSONArray imgsJ = obj.getJSONArray("specificPictureList");
        List<String> imgs = new ArrayList<>();
        for (int i = 0; i < imgsJ.length(); i++) {
            JSONObject imgJ = imgsJ.getJSONObject(i);
            String imgUrl = imgJ.getString("url");
            imgs.add(imgUrl);
        }
        return imgs;
    }


}
