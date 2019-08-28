package com.rcs.nchumanity.ul;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.rcs.nchumanity.R;
import com.rcs.nchumanity.adapter.ListViewCommonsAdapter;
import com.rcs.nchumanity.dialog.DialogTool;
import com.rcs.nchumanity.entity.BasicResponse;
import com.rcs.nchumanity.entity.City;
import com.rcs.nchumanity.entity.NetConnectionUrl;
import com.rcs.nchumanity.entity.PersistenceData;
import com.rcs.nchumanity.entity.Province;
import com.rcs.nchumanity.entity.SystemParamSet;
import com.rcs.nchumanity.tool.GetJsonDataUtil;
import com.rcs.nchumanity.tool.RealPathFromUriUtils;
import com.rcs.nchumanity.tool.StringTool;
import com.rcs.nchumanity.view.CommandBar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Response;

import static com.rcs.nchumanity.fragment.MeFragment.REQUEST_CODE_PHOTO;

/**
 * 用于实现身份验证的界面
 */
public class IdentityInfoRecordActivity extends BasicResponseProcessHandleActivity {


    @BindView(R.id.name)
    EditText name;

    @BindView(R.id.phoneNumber)
    EditText phoneNumber;

    @BindView(R.id.identityCardNumber)
    EditText identityCarNumber;

    @BindView(R.id.workCompany)
    EditText workCompany;


    @BindView(R.id.six)
    Spinner sixSpinner;

    @BindView(R.id.degEduca)
    Spinner degEducaSpinner;

    @BindView(R.id.workLife)
    Spinner workLife;

    @BindView(R.id.officialCapacity)
    EditText officialCapacity;

    @BindView(R.id.homeAddress)
    EditText homeAddress;

    @BindView(R.id.homeAddressTag)
    TextView homeAddressTag;


    @BindView(R.id.capture)
    ImageView capture;


    private String fileName;

    private String province;

    private String city;

    private String district;

    @BindView(R.id.toolbar)
    CommandBar toolbar;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_identity_record);
        ButterKnife.bind(this);

        toolbar.setTitle("报名考试");

        phoneNumber.setText(PersistenceData.getPhoneNumber(this));

    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @OnClick({R.id.submitSignUp, R.id.capture, R.id.homeAddressTag})
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.submitSignUp:
                /**
                 * 进行验证
                 *1.验证必填数据的合法性
                 *2.获得下拉列表的数据
                 *3.验证选填数据的合法性
                 *4.提交数据
                 */
                String value = "";

                value += StringTool.accessLength(String.valueOf(name.getText()), 2) == false ? "请输入合法的姓名\n" : "";

                value += StringTool.accessLength(String.valueOf(phoneNumber.getText()), 11) == false ? "请输入合法的手机号\n" : "";

                value += StringTool.accessLength(String.valueOf(identityCarNumber.getText()), 18) == false
                        ? "身份证位数不足18位\n" : StringTool.identityAssess(String.valueOf(identityCarNumber.getText())) == false ? "身份证不合法" : "";

                value += StringTool.accessLength(String.valueOf(workCompany.getText()), 2) == false ? "请输入合法的工作单位\n" : "";


                if (value.length() != 0) {
                    Toast.makeText(this, value, Toast.LENGTH_SHORT).show();
                    return;
                }


                String sex = (String) sixSpinner.getSelectedItem();

                String educa = (String) degEducaSpinner.getSelectedItem();

                String workLifeI = String.valueOf(workLife.getSelectedItem());

                String officialCapacityS = officialCapacity.getText().toString();

                String homeAddressS = String.valueOf(homeAddress.getText());

                String nameS = name.getText().toString();
                String mobilePhoneS = phoneNumber.getText().toString();
                String identityCarNumberS = identityCarNumber.getText().toString();
                String workCompanyS = workCompany.getText().toString();


                Map<String, String> maps = new HashMap<>();
                maps.put("name", nameS);
                maps.put("mobilephone", mobilePhoneS);
                maps.put("gender", sex);
                maps.put("idNumber", identityCarNumberS);
                maps.put("employer", workCompanyS);
                maps.put("yearsOfWorking", workLifeI);
                maps.put("duty", officialCapacityS);
                if (position == null) {
                    Toast.makeText(this, "请选择位置", Toast.LENGTH_SHORT).show();
                    return;
                }
                maps.put("address", position + homeAddressS);
                maps.put("areaId", currentAreaId + "");
                maps.put("degreeOfEducation", educa);


                if (TextUtils.isEmpty(imageFilePath) || (!new File(imageFilePath).isFile())) {
                    Toast.makeText(this, "请选择正确的证件照", Toast.LENGTH_SHORT).show();
                    return;
                }


                new AlertDialog.Builder(this).setTitle("提示")
                        .setMessage("确定报名吗？")
                        .setPositiveButton("确定", ((dialog, which) -> {
                            /**
                             * 下面直接提交数据
                             */
                            loadDataPostImg(NetConnectionUrl.submitSignInUserInfo,
                                    "trainSignIn", imageFilePath, maps);

                        })).setNegativeButton("取消", (dialog, which) -> {

                }).create().show();

                break;

            case R.id.capture:

                //选证件照

                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, REQUEST_CODE_PHOTO);

                break;

            case R.id.homeAddressTag:
                //家庭地址的选择

                View view1 = LayoutInflater.from(this).inflate(R.layout.dialog_list, null);

                Log.d("test", "onClick: ");

                init();

                AlertDialog.Builder builder = new AlertDialog.Builder(this)
                        .setView(view1);

                AlertDialog alertDialog = builder.create();

                alertDialog.show();

                alertDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        if (successSelect) {
                            position = currentProvince + currentCity + currentArea;
                            homeAddressTag.setText(position);
                        } else {

                        }
                    }
                });

                ListView listView = view1.findViewById(R.id.list);

                ArrayList<JSONObject> citList = new ArrayList<>();

                adapter = new ListViewCommonsAdapter<JSONObject>(citList, R.layout.item_text) {
                    @Override
                    public void bindView(ViewHolder holder, JSONObject obj) {
                        try {
                            String name = obj.getString("name");
                            holder.setText(R.id.item, name);
                            Log.d("test", "bindView: " + name);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public int getCount() {
                        return citList.size();
                    }
                };

                listView.setAdapter(adapter);


                GetJsonDataUtil util = new GetJsonDataUtil();
                util.getProvinceList(this, data -> {

                    runOnUiThread(() -> {
                        citList.clear();
                        citList.addAll(data);
                        adapter.notifyDataSetChanged();
                    });
                });

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        if (level == -1) {//加载的是省份的数据
                            JSONObject jsonObject = adapter.getItem(position);
                            try {
                                currentProvince = jsonObject.getString("name");
                                List<JSONObject> citys = util.getCityList(jsonObject);
                                citList.clear();
                                citList.addAll(citys);
                                adapter.notifyDataSetChanged();
                                level++;
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        } else if (level == 0) {
                            //加载的是城市的数据
                            JSONObject jsonObject = adapter.getItem(position);
                            try {
                                currentCity = jsonObject.getString("name");
                                List<JSONObject> areaList = util.getAreaList(jsonObject);
                                citList.clear();
                                citList.addAll(areaList);
                                adapter.notifyDataSetChanged();
                                level++;
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else if (level == 1) {
                            //代表的是区的点击
                            JSONObject areaObj = adapter.getItem(position);
                            try {
                                currentAreaId = areaObj.getInt("code");
                                currentArea = areaObj.getString("name");
                                successSelect = true;
                                alertDialog.dismiss();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
                break;
        }
    }


    private int level = -1;

    /**
     * 成功选择
     */
    private boolean successSelect = false;

    private String currentProvince = "";

    private String currentCity = "";

    private String currentArea = "";

    private int currentAreaId;

    /**
     * 当前的位置==省 市 区
     */
    private String position = "";


    private void init() {
        level = -1;
        successSelect = false;
        currentArea = "";
        currentProvince = "";
        currentCity = "";
        position = "";
    }


    private ListViewCommonsAdapter<JSONObject> adapter;


    @Override
    protected void responseWith4(String what, BasicResponse br, Response response, String resData) {
        super.responseWith4(what, br, response, resData);
        if (what.equals("trainSignIn")) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this)
                    .setTitle("温馨提示")
                    .setMessage("报名成功")
                    .setPositiveButton("确定", (dialog, which) -> {
                        dialog.dismiss();
                        finish();
                    });
            builder.setCancelable(false);
            builder.create().show();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("this", "onActivityResult: ");
        switch (requestCode) {
            case REQUEST_CODE_PHOTO:
                if (resultCode == RESULT_OK) {

                    doChangePicture(data);
                } else {
                    Toast.makeText(this, "修改头像失败", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }


    private String imageFilePath;

    @BindView(R.id.userImg)
    ImageView userImg;

    /**
     * @param data
     */
    private void doChangePicture(Intent data) {

        Uri uri = data.getData();

        if (data != null) {
            String realPathFromUri = RealPathFromUriUtils.getRealPathFromUri(this, data.getData());

//            Log.d("test", "doChangePicture: " + realPathFromUri);// /storage/emulated/0/DCIM/Camera/IMG_20190809_051538.jpg


            try {
                InputStream is = getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(is);

//                userImg.setImageBitmap(bitmap);


                File file = new File(getCacheDir(), realPathFromUri.substring(realPathFromUri.lastIndexOf("/")));
                if (!file.exists()) {
                    file.createNewFile();
                }
                FileOutputStream fos = new FileOutputStream(file);
                bitmap.compress(Bitmap.CompressFormat.JPEG, SystemParamSet.IMAGE_QUALITY, fos);
                /**
                 * 使用Glide加载 ，避免 出现图片翻转问题
                 *
                 * 这个代码依旧会出现这种情况
                 */
//                Glide.with(this).load(bos.toByteArray()).into(userImg);


                Log.d("test", "doChangePicture: " + file.getAbsolutePath() + "\t" + file.length());

//                Executors.newSingleThreadExecutor().submit(new Runnable() {
//                    @Override
//                    public void run() {
//
//                        try {
//                            FileOutputStream fileOutputStream = new FileOutputStream(file);
//
//                            bos.writeTo(fileOutputStream);
//
//
//
//
//
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                    }
//                });


                imageFilePath = file.getAbsolutePath();


                /**
                 * 不会出现
                 */
                Glide.with(this).load(new File(realPathFromUri)).into(userImg);

            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            Toast.makeText(this, "图片损坏，请重新选择", Toast.LENGTH_SHORT).show();
        }

    }

}
