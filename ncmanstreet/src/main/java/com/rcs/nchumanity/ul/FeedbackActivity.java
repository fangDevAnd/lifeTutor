package com.rcs.nchumanity.ul;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.google.gson.Gson;
import com.rcs.nchumanity.R;
import com.rcs.nchumanity.adapter.ListViewCommonsAdapter;
import com.rcs.nchumanity.entity.BasicResponse;
import com.rcs.nchumanity.entity.NetConnectionUrl;
import com.rcs.nchumanity.entity.model.FeedbackRecord;
import com.rcs.nchumanity.tool.DateProce;
import com.rcs.nchumanity.tool.Tool;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Response;

public class FeedbackActivity extends BasicResponseProcessHandleActivity {


    public static final String DATA = "data";

    private ArrayList<FeedbackRecord> feedbackRecords;


    @BindView(R.id.content)
    EditText content;


    @BindView(R.id.feedList)
    ListView feedList;


    ListViewCommonsAdapter<FeedbackRecord> commonsAdapter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.feedback);
        ButterKnife.bind(this);

        feedbackRecords = (ArrayList<FeedbackRecord>) getIntent().getExtras().getSerializable(DATA);

        if (feedbackRecords == null) {
            throw new IllegalArgumentException("param exception");
        }


        commonsAdapter = new ListViewCommonsAdapter<FeedbackRecord>(feedbackRecords, R.layout.item_feedback) {
            @Override
            public void bindView(ViewHolder holder, FeedbackRecord obj) {
                if (obj.getCreateTime() != null) {
                    holder.setText(R.id.createTime, DateProce.formatDate(obj.getCreateTime()));
                }
                if (obj.getReply() != null) {
                    holder.setText(R.id.reply, obj.getReply());
                }
                if (obj.getMessage() != null) {
                    holder.setText(R.id.message, obj.getMessage());
                }
            }

            @Override
            public int getCount() {
                return feedbackRecords.size();
            }
        };
        feedList.setAdapter(commonsAdapter);
    }


    @OnClick(R.id.submit)
    public void onClick(View view) {
        loadDataPost(NetConnectionUrl.commitFeedback, "commitFeedback", new HashMap<>());
    }


    @Override
    protected void responseDataSuccess(String what, String backData, Response response, BasicResponse... br) throws Exception {
        super.responseDataSuccess(what, backData, response, br);
        if (what.equals("commitFeedback")) {

            AlertDialog.Builder builder = new AlertDialog.Builder(this)
                    .setTitle("提示")
                    .setMessage("提交留言成功")
                    .setPositiveButton("确定", (dialog, which) -> {
                        dialog.dismiss();
                    });
            builder.create().show();
        }
    }
}
