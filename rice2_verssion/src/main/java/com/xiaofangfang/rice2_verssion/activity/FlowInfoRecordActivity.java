package com.xiaofangfang.rice2_verssion.activity;


import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.xiaofangfang.rice2_verssion.ParentActivity;
import com.xiaofangfang.rice2_verssion.R;
import com.xiaofangfang.rice2_verssion.tool.SystemSet;

import java.util.ArrayList;
import java.util.List;

/***
 * 流量过滤的类
 * 该类界面需要重新编写
 *
 */
public class FlowInfoRecordActivity extends ParentActivity implements View.OnClickListener {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.statistic_flow_call);
        super.onCreate(savedInstanceState);
    }

    private Button buttonselect;
    /**
     * 其中seekbar1 是通话的seekbar   9月使用流量的seekbar   10是日使用短信的seekbar
     */
    private SeekBar seekBar1, seekBar9, seekBar10;
    /**
     * 同上面一样是显示
     */
    private TextView callDis1, flowDis1, messis1;

    private TextView queryInfoEntry;

    private List<SeekBar> seekBars;
    private List<TextView> disList;


    public static int flowCount;
    public static int messCount;
    public static int callCount;

    @Override
    public void initView() {


        seekBar1 = findViewById(R.id.seekbar1);
        seekBar9 = findViewById(R.id.seekbar9);
        seekBar10 = findViewById(R.id.seekbar10);

        seekBars = new ArrayList<>();
        seekBars.add(seekBar1);
        seekBars.add(seekBar9);
        seekBars.add(seekBar10);

        callDis1 = findViewById(R.id.callDis1);
        flowDis1 = findViewById(R.id.flowDis1);
        messis1 = findViewById(R.id.messDis1);

        disList = new ArrayList<>();
        disList.add(callDis1);
        disList.add(flowDis1);
        disList.add(messis1);

        for (int i = 0; i < seekBars.size(); i++) {
            final int finalI = i;
            seekBars.get(i).setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    TextView disView = disList.get(finalI);
                    String text = disView.getText().toString().split(" ")[1];
                    disView.setText(progress + " " + text);
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }
            });
        }
        buttonselect = findViewById(R.id.downStepDisPlayResult);
        buttonselect.setOnClickListener(this);


        queryInfoEntry = findViewById(R.id.queryInfoEntry);
        queryInfoEntry.setOnClickListener((view) -> {

            Intent intent = new Intent(this, QuerySetmealUseInfoEntry.class);
            startActivity(intent);
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //如果登录的情况下,我们进入系统的筛选界面
            //反之进入系统的登录注册界面
            case R.id.downStepDisPlayResult:
                progressDisplayResult();
                break;
        }
    }

    /**
     * 获得通话时长的数量
     *
     * @return
     */
    public int getCallCount() {
        int count = 0;
        count = seekBar1.getProgress();
        return count;
    }

    /**
     * 获得消息的数量
     *
     * @return
     */
    public int getMessCount() {
        return seekBars.get(seekBars.size() - 1).getProgress();
    }

    /**
     * 获得流量的使用
     *
     * @return
     */
    public int getFlowCount() {
        return seekBar9.getProgress();
    }

    /**
     * 处理显示结果
     */
    private void progressDisplayResult() {
        if (getCallCount() == 0 || getFlowCount() == 0 || getMessCount() == 0) {
            Toast.makeText(this, "请输入短信 通话 流量的使用情况", Toast.LENGTH_SHORT).show();
            return;
        }
        callCount = getCallCount();
        messCount = getMessCount();
        flowCount = getFlowCount();

        boolean status = getMyApplication().setting.getBoolean(SystemSet.LOGIN_STATUS, SystemSet.DEF_LOGIN_STATUS);
        //todo  测试使用的代码
        if (status) {

            //如果登录的状态是true,我们就去加载数据
            Intent intent = new Intent(this, ProductSalePageActivity.class);
            //代表的是自定义
            StringBuffer filter = new StringBuffer();
            //todo 这里的代码请不要随意变动,除非你自己知道后端是怎么编写的
            filter.append("&通话时长=").append(callCount).append("&上网流量=").append(flowCount).append("&短信=").append(messCount);
            intent.putExtra("filter", filter.toString());

            Log.d("test", "progressDisplayResult: =" + filter);

            startActivity(intent);


        } else {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }
    }

}
