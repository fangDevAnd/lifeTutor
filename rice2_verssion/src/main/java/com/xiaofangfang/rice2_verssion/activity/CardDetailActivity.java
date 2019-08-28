package com.xiaofangfang.rice2_verssion.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.os.Bundle;
import androidx.annotation.Nullable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.xiaofangfang.rice2_verssion.ParentActivity;
import com.xiaofangfang.rice2_verssion.R;
import com.xiaofangfang.rice2_verssion.model.Card;
import com.xiaofangfang.rice2_verssion.network.NetRequest;
import com.xiaofangfang.rice2_verssion.tool.LoadProgress;
import com.xiaofangfang.rice2_verssion.tool.Looger;
import com.xiaofangfang.rice2_verssion.tool.UiThread;
import com.xiaofangfang.rice2_verssion.view.CommandBar;
import com.xiaofangfang.rice2_verssion.view.ProductDetailBanner;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * card的详细信息的界面的显示
 */
public class CardDetailActivity extends ParentActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.card_detail_sub_activity_layout);
        super.onCreate(savedInstanceState);
        initData();
        loadData();
    }


    /**
     * 初始化视图
     */
    public void initView() {

        productDetailPage = findViewById(R.id.productDetailPage);
        productDetailBanner = findViewById(R.id.productDetailBanner);
        cardOprator = findViewById(R.id.cardOprator);
        cardSaleTitle = findViewById(R.id.cardSaleTitle);
        monthFee = findViewById(R.id.monthFee);
        setmealName = findViewById(R.id.setmealName);
        oprator = findViewById(R.id.oprator);
        action = findViewById(R.id.action);
        cardAllEvaluation = findViewById(R.id.cardAllEvaluation);
        cardParamDetail = findViewById(R.id.cardParamDetail);
        submit = findViewById(R.id.submit);


        CommandBar commandBar = findViewById(R.id.commandBar);
        commandBar.setTitle("套餐详情");


        submit.setOnClickListener((v) -> {
            Intent intent = new Intent(CardDetailActivity.this, SubmitCardProgress.class);
            intent.putExtra(Card.class.getSimpleName(), card);
            startActivity(intent);

        });


        //套餐的所有的评论的点击事件的处理
        cardAllEvaluation.setOnClickListener((v) -> {

        });

        //套餐card的其他详细信息的点击事件的处理__
        cardParamDetail.setOnClickListener((v) -> {

            loadPop();
        });
    }


    private void loadData() {
        loadNetData();
    }


    private ProgressBar progressBar;


    private Card card;

    /**
     * 加载传递的数据
     */
    private void initData() {
        Intent intent = getIntent();
        card = (Card) intent.getSerializableExtra(Card.class.getSimpleName());
    }

    private LinearLayout productDetailPage;
    private ProductDetailBanner productDetailBanner;
    private TextView cardOprator, cardSaleTitle, monthFee, setmealName, oprator, action, cardAllEvaluation, oprateStep;
    private RelativeLayout cardParamDetail;
    private Button submit;


    private WindowManager windowManager;

    private void loadPop() {

        windowManager = getWindowManager();
        //设置弹出的窗口的高度的值为屏幕大小的3/4
        float height = getResources().getDisplayMetrics().heightPixels * 3 / 4;

        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(-1, (int) height, WindowManager.LayoutParams.TYPE_APPLICATION,
                WindowManager.LayoutParams.FLAG_DIM_BEHIND, PixelFormat.RGBA_8888
        );
        layoutParams.windowAnimations = R.style.windowanim;
        layoutParams.dimAmount = 0.4f;
        layoutParams.gravity = Gravity.BOTTOM;
        View view = LayoutInflater.from(this).inflate(R.layout.pop_dialog, null, false);
        eventProgress(view);
        windowManager.addView(view, layoutParams);

        //设置背景变暗
        resetProductParam(view);
    }


    /**
     * 这个是弹出窗口的事件的处理
     *
     * @param view
     */
    private void eventProgress(View view) {

        view.findViewById(R.id.closePopWindow).setOnClickListener((v) -> {

            windowManager.removeViewImmediate(view);

        });

    }

    /**
     * 设置弹出窗口的参数，因为里面的数据是需要进行赋值操作的
     */
    private void resetProductParam(View view) {

        TextView redPacket, itv, broadBand, other, addValueSer, offhook, flowCount, callCount, oprator, cardSetmealType;

        redPacket = view.findViewById(R.id.redPacket);
        itv = view.findViewById(R.id.itv);
        broadBand = view.findViewById(R.id.broadBand);
        other = view.findViewById(R.id.other);
        addValueSer = view.findViewById(R.id.addValueSer);
        offhook = view.findViewById(R.id.offhook);
        flowCount = view.findViewById(R.id.flowCount);
        callCount = view.findViewById(R.id.callCount);
        oprator = view.findViewById(R.id.oprator);
        cardSetmealType = view.findViewById(R.id.cardSetmealType);

        redPacket.setText(card.getRedPacket());
        itv.setText(card.getTv());
        broadBand.setText(card.getBrandBand());
        other.setText(card.getOtherSer());
        addValueSer.setText(card.getValueAddSer());
        offhook.setText(card.getOffhook());
        flowCount.setText(card.getNetFlowCount());
        callCount.setText(card.getTalkTime() + "分钟");
        oprator.setText(card.getOprator());
        cardSetmealType.setText(card.getClassfy());

    }

    /**
     * 设置card的详情界面的显示效果，其实加载的就是一些图片
     *
     * @param imageAddress
     */

    public void setProductDetailPage(List<String> imageAddress) {
        for (int i = 0; i < imageAddress.size(); i++) {
            ImageView imageView = new ImageView(this);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-1, -2);
            Glide.with(this).load(NetRequest.serverMain + imageAddress.get(i)).into(imageView);
            imageView.setLayoutParams(layoutParams);
            productDetailPage.addView(imageView);
        }
    }


    private String url;

    /**
     * 加载网络数据
     */
    public void loadNetData() {

        url = NetRequest.productSalePageAction
                + "?class=" + this.getClass().getSimpleName()
                + "&cityId=" + ParentActivity.city.getCityId()
                + "&cardId=" + card.getCardId();

        Looger.D("加载的url\t" + url);


        progressBar = LoadProgress.loadProgress(this);

        //加载网络的套餐的数据
        NetRequest.requestUrl(url, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                UiThread.getUiThread().post(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(CardDetailActivity.this, "数据出错", Toast.LENGTH_SHORT).show();
                        LoadProgress.removeLoadProgress(CardDetailActivity.this, progressBar);
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String jsonData = response.body().string();
                UiThread.getUiThread().post(() -> {
                    progressBackData(jsonData);
                });
            }
        });
    }

    /**
     * 处理网络操作返回的数据
     *
     * @param jsonData
     */
    private void progressBackData(String jsonData) {

        Gson gson = new Gson();
        card = gson.fromJson(jsonData, Card.class);
        reloadData();
        //在布局资源全部设置完成过后才取消进度条
        LoadProgress.removeLoadProgress(CardDetailActivity.this, progressBar);

    }

    private void reloadData() {

        List<String> imageUrl = card.getImage();
        productDetailBanner.createImageViewForPage(imageUrl);
        cardOprator.setText(card.getOprator());
        SpannableStringBuilder span = new SpannableStringBuilder(cardSaleTitle.getText() + card.getSaleTitle());
        span.setSpan(new ForegroundColorSpan(Color.TRANSPARENT), 0, 5,
                Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        cardSaleTitle.setText(span);

        monthFee.setText(card.getMonthFee());

        setmealName.setText(card.getClassfy());

        oprator.setText(card.getOprator());

        action.setText(card.getAction());

        setProductDetailPage(card.getCardDetailPageImg());


    }


}
