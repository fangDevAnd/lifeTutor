package com.rcs.nchumanity.ul.detail;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.rcs.nchumanity.R;
import com.rcs.nchumanity.entity.BasicResponse;
import com.rcs.nchumanity.entity.NetConnectionUrl;
import com.rcs.nchumanity.entity.complexModel.ComplexModelSet;
import com.rcs.nchumanity.entity.model.OnlineCourseInfo;
import com.rcs.nchumanity.tool.StringTool;
import com.rcs.nchumanity.view.CommandBar;

import org.json.JSONObject;

import java.io.IOException;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Response;

/**
 * 课程信息详情Activity
 * 需要传递
 * #{{@link ComplexModelSet.M_speinf_speinfCla}}过来
 * 这样我们就能拿到 对应的课程想关的信息
 * <p>
 * 子类继承通过传递网络加载不同的数据  选修
 * <p>
 * 这个类可以用于基本的选修的实现
 */
public class ElectiveCourseInfoComplexDetailActivity extends ComplexDetailActivity<OnlineCourseInfo> {


    public static final String COURSE_NO = "courseNo";

    @Override
    protected int getViewLayoutId() {
        return R.layout.activity_complex_detail;
    }


    @Override
    protected void bindView(View view, OnlineCourseInfo obj) {

        if (isError) {
            view.findViewById(R.id.contentArea).setVisibility(View.INVISIBLE);
        } else {

            /**
             * 设置标题
             */
            TextView barTitle = view.findViewById(R.id.barTitle);

            barTitle.setText(obj.getTitle());


            if (obj == null) {
                return;
            }

            /**
             * 设置图片的显示，因为图片的显示
             *
             *   <ImageView
             *                     android:layout_width="match_parent"
             *                     android:layout_height="wrap_content"
             *                     android:scaleType="centerCrop"
             *                     android:src="@drawable/banner1" />
             *
             *                     被添加到R.id.imgArea 下面
             *
             */

            ViewGroup imgArea = view.findViewById(R.id.imgArea);

            for (String url : obj.getImgUrl().split(StringTool.DELIMITER)) {

                ImageView imageView = new ImageView(this);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(-1, -2);
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                Glide.with(this).load(url).into(imageView);
                imgArea.addView(imageView, lp);
            }


            /**
             * 设置视频的播放地址
             */
            if (!TextUtils.isEmpty(obj.getVideoUrl()) && (!"null".equals(obj.getVideoUrl()))) {
                videoPlayFragment.setUrl(obj.getVideoUrl());
            } else {
                videoPlayFragment.setVisiblity(View.GONE);
            }

            if (!TextUtils.isEmpty(obj.getWriting()) && (!"null".equals(obj.getWriting()))) {
                TextView content = view.findViewById(R.id.content);
                content.setText(StringTool.TEXT_INDENT + obj.getWriting());
            }

            if (!TextUtils.isEmpty(obj.getTitle()) && (!"null".equals(obj.getTitle()))) {
                TextView title = view.findViewById(R.id.title);
                title.setText(obj.getTitle());
            }


            //正确加载的情况下，开启计时
            thread.start();

        }

    }

    private void postWatchData(OnlineCourseInfo obj) {
        if (obj != null && isError == false) {
            Map<String, String> param = new HashMap<>();
            param.put("courseNo", courseNo + "");
//            param.put("startTime", System.currentTimeMillis() + "");
            param.put("totalTime", totalTime + "");
            loadDataPostSilence(NetConnectionUrl.submitWatchData, "postWatchData", param);
        }
    }


    private Thread thread;

    private boolean isStartTime = false;

    private int totalTime = 0;

    @BindView(R.id.toolbar)
    CommandBar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        thread = new Thread(() -> {
            while (isStartTime) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                totalTime++;
                Log.d("test", "当前停留时长" + totalTime);
            }
        });

        isStartTime = true;

        courseNo = getIntent().getExtras().getString(COURSE_NO);
        ButterKnife.bind(this);

        if (courseNo == null) {
            throw new InvalidParameterException("invalid paramter is COURSE_NO");
        }

        String param = String.format(NetConnectionUrl.getNotRequiredCourseByCourseNo, courseNo);
        loadDataGet(param, "courseDetail");
    }

    private String courseNo;


    @Override
    public void onSucessful(Response response, String what, String... backData) throws IOException {

        BasicResponse br = new Gson().fromJson(backData[0], BasicResponse.class);
        String message = null;
        JSONObject br1 = null;
        try {
            br1 = new JSONObject(backData[0]);
            message = br1.has("msg") ? br1.getString("msg") : br1.getString("message");
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (what.equals("courseDetail")) {

            if (br.code == BasicResponse.RESPONSE_SUCCESS) {

                try {
                    JSONObject courseDetail = null;
                    if (br1.getJSONObject("object") != null) {
                        courseDetail = br1.getJSONObject("object");

                        int courseNo = courseDetail.getInt("courseNo");

                        String title = courseDetail.getString("title");

                        String videoUrl = null;
                        videoUrl = courseDetail.getString("videoUrl");
//                        videoUrl = "http://172.27.35.12:8080/GF/video.mp4";

                        String imgUrl = courseDetail.getString("imgUrl");

                        String writing = courseDetail.getString("writing");

                        String remark = courseDetail.getString("remark");

                        info = new OnlineCourseInfo();

                        info.setCourseNo(courseNo);
                        info.setTitle(title);
                        info.setVideoUrl(videoUrl);
                        info.setImgUrl(imgUrl);
                        info.setWriting(writing);
                        info.setRemark(remark);
                        bundleData();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    isError = true;
                    Toast.makeText(this, "暂无数据", Toast.LENGTH_SHORT).show();
                    info = new OnlineCourseInfo();
                    bundleData();
                }
            } else {
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
                info = new OnlineCourseInfo();
                isError = true;
                bundleData();
            }
        }
    }

    private boolean isError = false;


    private void stopTime() {
        isStartTime = false;
        thread.interrupt();
    }


    @Override
    protected void onDestroy() {
        stopTime();
        postWatchData(info);
        super.onDestroy();
    }

}
