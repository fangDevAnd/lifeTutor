package com.rcs.nchumanity.ul;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.widget.TextViewCompat;

import com.google.gson.Gson;
import com.rcs.nchumanity.R;
import com.rcs.nchumanity.entity.BasicResponse;
import com.rcs.nchumanity.entity.NetConnectionUrl;
import com.rcs.nchumanity.tool.Tool;

import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Response;

/**
 * 考核结果的界面
 */
public class AssessResultActivity extends BasicResponseProcessHandleActivity {


    @BindView(R.id.totalScore)
    TextView totalScoreT;

    @BindView(R.id.one)
    TextView one;

    @BindView(R.id.two)
    TextView two;

    @BindView(R.id.three)
    TextView three;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assess_result);
        ButterKnife.bind(this);

        loadDataGetForForce(NetConnectionUrl.getMyScore, "myCourse");

    }


    @Override
    protected void responseDataSuccess(String what, String backData, Response response, BasicResponse... br) throws Exception {
        super.responseDataSuccess(what, backData, response, br);

        if (what.equals("myCourse")) {

            /**
             *      "theoryScore": null,
             *         "cprScore": null,
             *         "traumaScore": null,
             *         "totalScore": null
             */
            try {
                JSONObject brJ = new JSONObject(backData);
                JSONObject obj = brJ.getJSONObject("object");

                String theoryScore = obj.isNull("theoryScore")
                        ? "暂无成绩" : obj.getInt("theoryScore") + "分";

                String cprScore = obj.isNull("cprScore") ?
                        "暂无成绩" : obj.getInt("cprScore") + "分";

                String traumaScore = obj.isNull("traumaScore") ?
                        "暂无成绩" : obj.getInt("traumaScore") + "分";
                String totalScore = obj.isNull("totalScore")
                        ? "暂无成绩" : obj.getInt("totalScore") + "分";

                one.setText(theoryScore);
                two.setText(cprScore);
                three.setText(traumaScore);
                totalScoreT.setText(totalScore);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private int setScore(Integer score) {
        if (score != null) {
        } else {
            score = 0;
        }
        return score;
    }
}
