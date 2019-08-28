package com.rcs.nchumanity.ul;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;

import com.google.gson.Gson;
import com.rcs.nchumanity.R;
import com.rcs.nchumanity.adapter.ListViewCommonsAdapter;
import com.rcs.nchumanity.entity.BasicResponse;
import com.rcs.nchumanity.entity.NetConnectionUrl;
import com.rcs.nchumanity.entity.PersistenceData;
import com.rcs.nchumanity.entity.complexModel.ComplexModelSet;
import com.rcs.nchumanity.tool.Tool;
import com.rcs.nchumanity.view.CommandBar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Response;

import static com.rcs.nchumanity.ul.detail.ObligatoryCourseInfoComplexDetailActivity.Division;
import static com.rcs.nchumanity.ul.detail.ObligatoryCourseInfoComplexDetailActivity.OPTION_SIZE;

public class OnlineAssessmentActivity extends BasicResponseProcessHandleActivity {


    CommandBar toolbar;

    LinearLayout listRoot;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_online_assessment);

        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("在线考核");

        listRoot = findViewById(R.id.listRoot);

        findViewById(R.id.submit).setOnClickListener((v) -> {

            boolean post = true;
            //提交试卷
            for (int i = 0; i < exams.length(); i++) {

                try {
                    JSONObject exam = exams.getJSONObject(i);
                    if (!exam.has("answer")) {
                        post = false;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            if (post) {
                try {
                    for (int i = 0; i < exams.length(); i++) {
                        exams.getJSONObject(i).remove("question");
                        exams.getJSONObject(i).remove("options");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                String jsonData = exams.toString();
                //进行提交
                Log.d("test", jsonData);

                Map<String, String> param = new HashMap<>();
                param.put("answerList", jsonData);
                loadDataPost(NetConnectionUrl.submitExamSubject, "submitExamSubject", param);

            } else {
                //没有通过，不能提交
                Toast.makeText(this, "请将试卷填写完整", Toast.LENGTH_SHORT).show();
            }
        });

        loadDataGet(NetConnectionUrl.getExamSubject, "examSubject");

    }


    @Override
    protected void responseDataSuccess(String what, String backData, Response response, BasicResponse... br) throws Exception {
        super.responseDataSuccess(what, backData, response, br);

        if (what.equals("examSubject")) {

            try {
                paramData(backData);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        } else if (what.equals("submitExamSubject")) {

            new AlertDialog.Builder(this)
                    .setTitle("提示")
                    .setMessage("考试数据提交成功,审核通过后可以在我的成绩里查看")
                    .setPositiveButton("确定", (dialog, which) -> {
                        dialog.dismiss();
                        finish();
                    }).create().show();
        }


    }

    
//    @Override
//    public void onSucessful(Response response, String what, String... backData) throws IOException {
//        super.onSucessful(response, what, backData);
//
//        BasicResponse br = new Gson().fromJson(backData[0], BasicResponse.class);
//
//        if (what.equals("examSubject")) {
//
//            if (br.code == BasicResponse.RESPONSE_SUCCESS) {
//
//                try {
//                    paramData(backData[0]);
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            } else if (br.code == BasicResponse.NOT_LOGIN) {
//
//                PersistenceData.clear(this);
//                Tool.loginCheck(this);
//
//            } else {
//                Toast.makeText(this, "发生错误", Toast.LENGTH_SHORT).show();
//            }
//        } else if (what.equals("submitExamSubject")) {
//
//            if (br.code == BasicResponse.RESPONSE_SUCCESS) {
//
//                new AlertDialog.Builder(this)
//                        .setTitle("提示")
//                        .setMessage("考试数据提交成功,审核通过后可以在我的成绩里查看")
//                        .setPositiveButton("确定", (dialog, which) -> {
//                            dialog.dismiss();
//                        }).create().show();
//            }
//
//        }
//
//    }


    /**
     * 测试数据
     */
    private String value = "{\"code\":200,\"message\":null,\"object\":[{\"id\":9,\"question\":\"dsd\",\"options\":\"sss\"},{\"id\":12,\"question\":\"131\",\"options\":\"123\"}]}";


    private JSONArray exams;

    private void paramData(String data) throws JSONException {
        JSONObject brJ = new JSONObject(data);
        exams = brJ.getJSONArray("object");

        for (int i = 0; i < exams.length(); i++) {
            JSONObject exam = exams.getJSONObject(i);
//                        int id = exam.getInt("id");
            String question = exam.getString("question");
            String option = exam.getString("options");

            String[] options = option.split(Division);

            int optionSize = options.length;

            String[] optionsBackup = null;
            optionsBackup = Arrays.copyOf(options, OPTION_SIZE);

            for (int j = optionSize; j < OPTION_SIZE; j++) {
                optionsBackup[j] = "暂无选项";
            }

            View view = LayoutInflater.from(OnlineAssessmentActivity.this).inflate(R.layout.item_exam, null);

            ((TextView) view.findViewById(R.id.number)).setText((i + 1) + "、");
            ((TextView) view.findViewById(R.id.question)).setText(question + "( )");

            RadioButton a = view.findViewById(R.id.a);
            RadioButton b = view.findViewById(R.id.b);
            RadioButton c = view.findViewById(R.id.c);
            RadioButton d = view.findViewById(R.id.d);

            a.setText(optionsBackup[0]);
            b.setText(optionsBackup[1]);
            c.setText(optionsBackup[2]);
            d.setText(optionsBackup[3]);

            view.setTag(exam);

            RadioGroup groupO = view.findViewById(R.id.options);
            groupO.setTag(i);
            groupO.setOnCheckedChangeListener((group, checkedId) -> {
                RadioButton radbtn = (RadioButton) group.findViewById(checkedId);
                try {
                    int index = (int) group.getTag();
                    exams.getJSONObject(index).put("answer", radbtn.getTag());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            });
            listRoot.addView(view);
        }
    }


}
