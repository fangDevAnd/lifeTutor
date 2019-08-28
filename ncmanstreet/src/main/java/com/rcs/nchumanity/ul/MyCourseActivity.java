package com.rcs.nchumanity.ul;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.google.gson.Gson;
import com.rcs.nchumanity.R;
import com.rcs.nchumanity.adapter.ListViewCommonsAdapter;
import com.rcs.nchumanity.dialog.DialogCollect;
import com.rcs.nchumanity.dialog.DialogTool;
import com.rcs.nchumanity.entity.BasicResponse;
import com.rcs.nchumanity.entity.NetConnectionUrl;
import com.rcs.nchumanity.entity.complexModel.ComplexModelSet;
import com.rcs.nchumanity.entity.model.OfflineTrainClass;
import com.rcs.nchumanity.tool.DateProce;
import com.rcs.nchumanity.tool.Tool;
import com.rcs.nchumanity.ul.detail.OfflineTrainClassDetailActivity;
import com.rcs.nchumanity.ul.list.ComplexListActivity;
import com.rcs.nchumanity.ul.list.OfflineTrainClassListActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Response;

/**
 * 我的课程界面的实现
 */
public class MyCourseActivity extends ComplexListActivity<ComplexModelSet.ClassDetail> {


    public static final int CODE_RE = 0;


    public static final String DATA = "data";


    public static final String FOR_RESULT = "forData";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /**
         * 在这里加载数据，然后将数据通过setDataList设置进去
         */

        loadDataGetForForce(NetConnectionUrl.myCourse, "myCourse");

        setTitle("我的课程");

    }

    @Override
    protected void bindViewValue(ListViewCommonsAdapter.ViewHolder holder, ComplexModelSet.ClassDetail obj) {

        holder.setText(R.id.trainOrg, obj.org);
        holder.setText(R.id.currentCount, obj.currentNum + "人");
        holder.setText(R.id.trainPosition, obj.position);
        holder.setText(R.id.dateTime, DateProce.formatDate(obj.startTime));
        View view = holder.getItemView().findViewById(R.id.cancel);
        view.setTag(obj);
        view.setOnClickListener((v) -> {

            DialogCollect.openTipDialog(this, (inter, index) -> {
                inter.dismiss();

                ComplexModelSet.ClassDetail classDetail = (ComplexModelSet.ClassDetail) v.getTag();

                int id = classDetail.classId;

                clickStep = R.id.cancel;

                String url = String.format(NetConnectionUrl.cancelChooseClass, id);

                loadDataGet(url, "cancelChooseClass");

            }, "提示", "你将在一周之内无法报名");
        });

        View reSelect = holder.getItemView().findViewById(R.id.reSelect);
        reSelect.setTag(obj);
        reSelect.setOnClickListener((v) ->

        {

            Bundle bundle2 = new Bundle();
            bundle2.putString(OfflineTrainClassListActivity.URL, NetConnectionUrl.getCPRClassList);
            bundle2.putBoolean(FOR_RESULT, true);
            Intent intent = new Intent(this, OfflineTrainClassListActivity.class);
            intent.putExtras(bundle2);
//            OfflineTrainClassDetailActivity.isReselect = true;
            startActivityForResult(intent, CODE_RE);

            /**
             *
             * 通过 courseNO调整我们调用的后端接口
             *
             */

//            ComplexModelSet.ClassDetail classDetail = (ComplexModelSet.ClassDetail) v.getTag();
//            int id = classDetail.classId;
//            clickStep = R.id.reSelect;
//            Map<String, String> param = new HashMap<>();
//            param.put("classId", id + "");
//            loadDataPost(NetConnectionUrl.offlineTrainClassSignUp, "offlineTrainClassSignUp", param);

        });
    }


    @Override
    protected void itemClick(AdapterView<?> parent, View view, int position, long id, ComplexModelSet.ClassDetail item) {

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CODE_RE) {
            if (resultCode == Activity.RESULT_OK) {
                //再次拉取数据
                loadDataGetForForce(NetConnectionUrl.myCourse, "myCourse");
            }
        }
    }

    @Override
    protected int getLayout() {
        return R.layout.item_me_course;
    }


    private int clickStep;

    @Override
    protected void responseDataSuccess(String what, String backData, Response response, BasicResponse... br) throws Exception {
        super.responseDataSuccess(what, backData, response, br);

        try {
            if (br[0] != null) {

                if (what.equals("myCourse")) {
                    List<ComplexModelSet.ClassDetail> classDetails = new ArrayList<>();

                    JSONObject brJ = new JSONObject(backData);
                    JSONArray objects = brJ.getJSONArray("object");
                    for (int i = 0; i < objects.length(); i++) {
                        JSONObject obj = objects.getJSONObject(i);

                        int id = obj.getInt("id");
                        String startTime = obj.getString("startTime");
                        String position = obj.getString("position");
                        int currentNum = obj.getInt("currentNum");
                        String org = obj.getString("org");
                        String trainer = obj.getString("trainer");

                        ComplexModelSet.ClassDetail classDetail =
                                new ComplexModelSet.ClassDetail();

                        classDetail.classId = id;
                        classDetail.startTime = DateProce.parseDate(startTime);
                        classDetail.position = position;
                        classDetail.currentNum = currentNum;
                        classDetail.org = org;
                        classDetail.trainer = trainer;
                        classDetails.add(classDetail);

                        setDataList(classDetails);
                    }
                } else if (what.equals("cancelChooseClass")) {

                    new AlertDialog.Builder(this).setTitle("提示")
                            .setMessage("已取消选课，七天内无法再选课")
                            .setPositiveButton("确定", (dialog, which) -> {
                                dialog.dismiss();
                                finish();
                            }).setCancelable(false).create().show();


                } else if (what.equals("offlineTrainClassSignUp")) {
                    new AlertDialog.Builder(this)
                            .setTitle("提示")
                            .setMessage("选课成功,请准时去参加课程")
                            .setPositiveButton("确定", (dialog, which) -> {
                                dialog.dismiss();
                                finish();
                            }).create().show();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
