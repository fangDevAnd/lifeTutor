package com.rcs.nchumanity.ul.list;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import androidx.annotation.Nullable;

import com.google.gson.Gson;
import com.rcs.nchumanity.R;
import com.rcs.nchumanity.adapter.ListViewCommonsAdapter;
import com.rcs.nchumanity.entity.BasicResponse;
import com.rcs.nchumanity.entity.PersistenceData;
import com.rcs.nchumanity.entity.StudyStatus;
import com.rcs.nchumanity.entity.complexModel.ComplexModelSet;
import com.rcs.nchumanity.entity.model.UserOnlineStudyRecord;
import com.rcs.nchumanity.tool.Tool;
import com.rcs.nchumanity.ul.detail.ElectiveCourseInfoComplexDetailActivity;
import com.rcs.nchumanity.ul.detail.ObligatoryCourseInfoComplexDetailActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Response;

import static com.rcs.nchumanity.ul.detail.ObligatoryCourseInfoComplexDetailActivity.COURSE_NO;

/**
 * 课程列表的实现的Activity
 * 实现的是必修和选修的课程的实现列表
 */
public class CourseComplexListActivity extends ComplexListActivity<ComplexModelSet.M_couClas_usOnStuRec> {


    /**
     * 传递学习状态
     */
    public static final String STUDY_STATUS = "studyStatus";
    public String url;

    public static final String NET_URL = "url";

    /**
     * 必修完成的时间的时长为 300秒
     */
    public static final int BIXIU_COMPLETE_TIME_LENGTH = 5 * 60;

    /**
     * 选修时长设置为完成的时间为10秒
     */
    public static final int XUANXIU_COMPLETE_TIME_LENGTH = 10;


    public static final String FUN = "fun";


    public static final String FUN_SELECT = "select";

    public static final String FUN_NEED = "need";

    /**
     * 备份的url
     */
    public static final String BACKUP_URL = "backUrl";


    public static final String BACKUP_FUN = "backUrl";

    private String backFun;

    private String backUrl;


    private String func;

    private int studyStatus;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        toolbar.setTitle("课程列表");
        Bundle bundle = getIntent().getExtras();

        url = bundle.getString(NET_URL);
        func = bundle.getString(FUN);
        backUrl = bundle.getString(BACKUP_URL);
        studyStatus = bundle.getInt(STUDY_STATUS);

        if (TextUtils.isEmpty(url) || TextUtils.isEmpty(func)
                || TextUtils.isEmpty(backUrl) || studyStatus == 0) {
            throw new RuntimeException("please transport right data param!!!");
        }
        /**
         * 加载课程数据
         */
        loadDataGetForForce(url, "courseList");
        IntentFilter intentFilter = new IntentFilter(ObligatoryCourseInfoComplexDetailActivity.ACTION);
        registerReceiver(new MyWatchDataReceiver(),intentFilter);


    }

    @Override
    protected void bindViewValue(ListViewCommonsAdapter.ViewHolder holder, ComplexModelSet.M_couClas_usOnStuRec obj) {

        holder.setText(R.id.title, obj.courseName);

        if (func.equals(FUN_NEED)) {

            if (url.equals(backUrl)) {
                //代表掉线或者用户未登录

            } else {
                //代表在线
                if (obj.isStudied) {
                    holder.setVisibility(R.id.notComplete, View.INVISIBLE);
                    holder.setVisibility(R.id.complete, View.VISIBLE);
                } else {
                    holder.setVisibility(R.id.notComplete, View.VISIBLE);
                    holder.setVisibility(R.id.complete, View.INVISIBLE);
                }
            }

        } else if (func.equals(FUN_SELECT)) {
            /**
             * 1.判断当前的状态 是登陆还是没登录
             *  2.根据学习的状态调整显示
             */
            if (url.equals(backUrl)) {
                //代表掉线或者用户未登录


            } else {
                //代表可能在线可能不

                //代表在线
                if (obj.isStudied) {
                    holder.setVisibility(R.id.notComplete, View.INVISIBLE);
                    holder.setVisibility(R.id.complete, View.VISIBLE);
                } else {
                    holder.setVisibility(R.id.notComplete, View.VISIBLE);
                    holder.setVisibility(R.id.complete, View.INVISIBLE);
                }
            }


        }
    }

    @Override
    protected void itemClick(AdapterView<?> parent, View view, int position, long id, ComplexModelSet.M_couClas_usOnStuRec item) {
        /**
         * 进入详情界面
         * 根据
         */
        Log.d("test", "itemClick: ==");

        Bundle bundle = new Bundle();
        bundle.putString(COURSE_NO, item.courseNo + "");
        if (func.equals(FUN_NEED)) {
            bundle.putBoolean(ObligatoryCourseInfoComplexDetailActivity.IS_STUDY, item.isStudied);
            bundle.putInt(ObligatoryCourseInfoComplexDetailActivity.STUDY_STATUS, studyStatus);
            Tool.startActivity(this, ObligatoryCourseInfoComplexDetailActivity.class, bundle);
        } else if (func.equals(FUN_SELECT)) {
            bundle.putInt(ObligatoryCourseInfoComplexDetailActivity.STUDY_STATUS, studyStatus);
            Tool.startActivity(this, ElectiveCourseInfoComplexDetailActivity.class, bundle);
        }

    }

    @Override
    protected int getLayout() {
        return R.layout.item_course_list;
    }


    @Override
    public void onSucessful(Response response, String what, String... backData) throws IOException {
        BasicResponse br = new Gson().fromJson(backData[0], BasicResponse.class);
        switch (what) {

            case "courseList": {

                if (br.code == BasicResponse.NOT_LOGIN) {

                    PersistenceData.clear(this);

                    url = backUrl;

                    loadDataGetForForce(url, "courseList");
                } else if (br.code == BasicResponse.RESPONSE_SUCCESS) {
                    try {
                        List<ComplexModelSet.M_couClas_usOnStuRec> studyList = parseCourse(backData[0]);

                        setDataList(studyList);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
            break;
        }
    }

    private List<ComplexModelSet.M_couClas_usOnStuRec> parseCourse(String backDatum) throws JSONException {

        JSONObject br = new JSONObject(backDatum);

        List list = new ArrayList<>();
        JSONArray courseList = br.getJSONArray("object");

        for (int i = 0; i < courseList.length(); i++) {
            JSONObject course = courseList.getJSONObject(i);

            int courseNo = course.getInt("courseNo");
            String courseName = course.getString("courseName");
            boolean isStudied = course.getBoolean("isStudied");

            ComplexModelSet.M_couClas_usOnStuRec onStuRec
                    = new ComplexModelSet.M_couClas_usOnStuRec(courseNo, courseName, isStudied);
            list.add(onStuRec);
        }
        return list;
    }


    @Override
    protected void onRestart() {
        super.onRestart();

        /**
         * 当为登录用户的时候，我们需要重新拉取数据
         * ，进行列表数据的刷新
         *
         *  因为我们不知道什么时候才能提交成功 ，所以我们在提交成功的时候 发送一个广播
         *
         *  这边注册一个广播接受者 进行处理
         */

        if (studyStatus == StudyStatus.STATUS_ONLINE_STUDY) {
//            loadDataGetForForce(url, "courseList");
        }
    }


    class MyWatchDataReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (studyStatus == StudyStatus.STATUS_ONLINE_STUDY) {
              loadDataGetForForce(url, "courseList");
            }
        }
    }


}
