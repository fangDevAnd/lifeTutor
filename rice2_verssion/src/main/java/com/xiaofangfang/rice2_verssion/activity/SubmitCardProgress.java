package com.xiaofangfang.rice2_verssion.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.xiaofangfang.rice2_verssion.ParentActivity;
import com.xiaofangfang.rice2_verssion.R;
import com.xiaofangfang.rice2_verssion.dao.OprateDb;
import com.xiaofangfang.rice2_verssion.model.AddressMode;
import com.xiaofangfang.rice2_verssion.model.Card;
import com.xiaofangfang.rice2_verssion.network.NetRequest;
import com.xiaofangfang.rice2_verssion.tool.DialogTool;
import com.xiaofangfang.rice2_verssion.tool.Looger;
import com.xiaofangfang.rice2_verssion.tool.Tools;
import com.xiaofangfang.rice2_verssion.view.CommandBar;

import java.io.IOException;
import java.util.List;

import okhttp3.Response;

public class SubmitCardProgress extends ParentActivity {


    OprateDb oprateDb;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.submit_card);
        oprateDb = OprateDb.getInstance(this);
        super.onCreate(savedInstanceState);
        initData();
    }


    private Card card;
    private AddressMode addressMode;
    private TextView user, tel, positionText,
            oprators, cardName, card_des, callCount, flow, actionT, xiaofeiT;
    private ImageView cardImg;

    private void initData() {
        card = (Card) getIntent().getSerializableExtra(Card.class.getSimpleName());

        Looger.D("card===" + card);


        oprators.setText(card.getOprator());
        if ("中国移动".equals(card.getOprator())) {
            Tools.drawableChange(R.drawable.ic_oprator2, this, oprators);
        }
        if ("中国电信".equals(card.getOprator())) {
            Tools.drawableChange(R.drawable.ic_oprator1, this, oprators);
        }
        if ("中国联通".equals(card.getOprator())) {
            Tools.drawableChange(R.drawable.ic_oprator3, this, oprators);
        }
        cardName.setText(card.getCardName());
        card_des.setText(card.getDetribute());
        callCount.setText(card.getTalkTime() + "分钟");
        flow.setText(card.getNetFlowCount());
        actionT.setText(card.getAction());
        xiaofeiT.setText(card.getMonthFee());
        Glide.with(this).load(NetRequest.serverMain + card.getRoughImageAddress()).into(cardImg);

        setData();

    }

    private void setAddressInfo(boolean has) {
        if (has) {
            user.setText(addressMode.getName());
            tel.setText(addressMode.getTel());
            positionText.setText(addressMode.getAddress());
        } else {
            user.setText("");
            tel.setText("");
            positionText.setText(positionText.getTag() + "");
        }
    }

    @Override
    public void initView() {

        CommandBar commandBar = findViewById(R.id.commandBar);
        commandBar.setTitle("提交订单");
        findViewById(R.id.positionSelect).setOnClickListener((v) -> {
            Intent intent = new Intent(SubmitCardProgress.this, AddressManager.class);
            intent.putExtra(SubmitCardProgress.class.getSimpleName(), SubmitCardProgress.class.getSimpleName());
            startActivityForResult(intent, 45);
        });

        user = findViewById(R.id.user);
        tel = findViewById(R.id.tel);
        positionText = findViewById(R.id.positionText);
        oprators = findViewById(R.id.oprators);
        cardName = findViewById(R.id.cardName);
        card_des = findViewById(R.id.callCount);
        callCount = findViewById(R.id.callCount);
        flow = findViewById(R.id.flow);
        actionT = findViewById(R.id.actionT);
        xiaofeiT = findViewById(R.id.xiaofeiT);
        cardImg = findViewById(R.id.card);


        findViewById(R.id.submit).setOnClickListener((v) -> {

            if (addressMode == null) {

                Toast.makeText(this, "请填写地址信息", Toast.LENGTH_SHORT).show();
                return;
            }

            DialogTool<String> dialogTool = new DialogTool<String>() {
                @Override
                public void bindView(DialogTool<String> d, Dialog dialog, String... t) {

                    d.setText(R.id.title, t[0]);
                    d.setText(R.id.content, t[1]);
                    d.setClickListener(R.id.cancel, (v) -> {
                        dialog.cancel();
                    });
                    d.setClickListener(R.id.yes, (v) -> {
                        String url = NetRequest.productSalePageAction + "?class=" + SubmitCardProgress.class.getSimpleName()
                                + "&tel=" + addressMode.getTel()
                                + "&name=" + addressMode.getName()
                                + "&address=" + addressMode.getAddress()
                                + "&cardId=" + card.getCardId();
                        Looger.D("加载url\t" + url);
                        loadData(url, "034");
                        dialog.cancel();
                    });
                }
            };
            Dialog dialog = dialogTool.getDialog(SubmitCardProgress.this, R.layout.warn_dialog_template, "提交提醒", "进行提交订单，是否继续");
            dialog.show();
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 45 && resultCode == RESULT_OK) {
            //代表的是请求数据成功
            addressMode = (AddressMode) data.getSerializableExtra(AddressMode.class.getSimpleName());
            //重新复制给当前的显示
            setAddressInfo(true);
        }
    }


    @Override
    public void onSucessful(Response response, String what, String... backdata) throws IOException {
        super.onSucessful(response, what);
        DialogTool<String> dialogTool = new DialogTool<String>() {
            @Override
            public void bindView(DialogTool<String> d, Dialog dialog, String... t) {

                dialog.setCancelable(false);
                d.setText(R.id.oprateTip, t[0]);
                d.setTextSize(R.id.oprateTip, 6);
                d.setClickListener(R.id.close, (v) -> {
                    dialog.cancel();
                });
                d.setVisibility(R.id.close, View.INVISIBLE);
                d.setClickListener(R.id.button, (v) -> {
                    dialog.cancel();
                    //返回首页
                    for (int i = activities.size() - 1; i >= 1; i--) {
                        Activity activity = activities.get(i);
                        activities.remove(activity);
                        activity.finish();
                    }
                });
            }
        };

        Dialog dialog = dialogTool.getDialog(SubmitCardProgress.this, R.layout.oprate_facenack, "提交订单成功,稍后会有客服和你取得联系，请稍后！");
        dialog.show();
    }


    @Override
    public void onError(IOException e, String what) {
        super.onError(e, what);
        Toast.makeText(this, "提交失败", Toast.LENGTH_SHORT).show();
    }


    @Override
    protected void onRestart() {
        super.onRestart();
        Looger.D("重新启动了程序");
        setData();
    }

    public void setData() {

        addressMode = null;//清楚数据，方便拿到最新的数据

        List<AddressMode> addressModes = oprateDb.getAllData();

        Looger.D("数据的长度==" + addressModes.size());

        if (addressModes.size() == 0) {
            //没有数据

        } else if (addressModes.size() >= 1) {

            for (int i = 0; i < addressModes.size(); i++) {
                if (addressModes.get(i).isDefault()) {
                    addressMode = addressModes.get(i);
                    break;
                }
            }
            addressMode = addressModes.get(0);
        }
        if (addressMode != null) {
            setAddressInfo(true);
        } else {
            setAddressInfo(false);
        }
    }
}
