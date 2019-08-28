package com.xiaofangfang.rice2_verssion.Fragment;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.xiaofangfang.rice2_verssion.ParentActivity;
import com.xiaofangfang.rice2_verssion.R;
import com.xiaofangfang.rice2_verssion.activity.CardDetailActivity;
import com.xiaofangfang.rice2_verssion.activity.FlowInfoRecordActivity;
import com.xiaofangfang.rice2_verssion.activity.ProductSalePageActivity;
import com.xiaofangfang.rice2_verssion.model.Card;
import com.xiaofangfang.rice2_verssion.model.City;
import com.xiaofangfang.rice2_verssion.model.Province;
import com.xiaofangfang.rice2_verssion.network.NetRequest;
import com.xiaofangfang.rice2_verssion.tool.Looger;
import com.xiaofangfang.rice2_verssion.tool.SystemSet;
import com.xiaofangfang.rice2_verssion.view.BannerFlip;
import com.xiaofangfang.rice2_verssion.view.ConsumeToobar;
import com.xiaofangfang.rice2_verssion.view.FullRoLLView;
import com.xiaofangfang.rice2_verssion.view.OnItemClickListener;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import okhttp3.Response;

import static com.xiaofangfang.rice2_verssion.ParentActivity.city;
import static com.xiaofangfang.rice2_verssion.tool.SystemSet.DEFALUT_LOCATION_CITY_NAME;


public class MainFragment extends ParentFragment {

    private String url;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //在这里加载数据


    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.main_fragment, null, false);
        url = NetRequest.productSalePageAction + "?class="
                + getClass().getSimpleName() + "&city=" + city.getCityId();
        Looger.D("url\t" + url);
        loadData(url, "034");
        return view;
    }

    private BannerFlip bannerFlip;

    private ViewGroup cardContainer;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        cardContainer = view.findViewById(R.id.cardContainer);

        Integer[] value = {
                R.drawable.banner1,
                R.drawable.banner2,
                R.drawable.banner3,
                R.drawable.banner4,
                R.drawable.banner5,
                R.drawable.banner6
        };
        bannerFlip = view.findViewById(R.id.banner);
        bannerFlip.setImageUrl(Arrays.asList(value));
        bannerFlip.setBannerHeight(600);
        bannerFlip.startAutoRoll(2000);

        FullRoLLView roll = view.findViewById(R.id.fullRoll);
        roll.setData(Arrays.asList(getResources().getStringArray(R.array.myroll)));
        roll.autoRun(3000);

        view.findViewById(R.id.startRecord).setOnClickListener((v) -> {
            Intent intent = new Intent(getContext(), FlowInfoRecordActivity.class);
            startActivity(intent);
        });

        view.findViewById(R.id.action).setOnClickListener((v) -> {
            Intent intent = new Intent(getContext(), FlowInfoRecordActivity.class);
            startActivity(intent);
        });


        View[] filterCondi = {
                view.findViewById(R.id.call1),
                view.findViewById(R.id.call2),
                view.findViewById(R.id.call3),
                view.findViewById(R.id.call4),

                view.findViewById(R.id.flow1),
                view.findViewById(R.id.flow2),
                view.findViewById(R.id.flow3),
                view.findViewById(R.id.flow4),

                view.findViewById(R.id.oprator1),
                view.findViewById(R.id.oprator2),
                view.findViewById(R.id.oprator3),
        };

        for (View view1 : filterCondi) {
            view1.setOnClickListener(new OnItemClickListener() {

                @Override
                public void onClick(View v) {
                    super.onClick(v);
                    Intent intent = new Intent(getContext(), ProductSalePageActivity.class);
                    //这里设置了一个filter,过滤的条件是 一个键值对 filter=result
                    //具体的请参考xml里面的内容
                    intent.putExtra("filter", "&" + ((View) v.getParent()).

                            getTag() + "=" + v.getTag());

                    startActivity(intent);
                }
            });

        }

        consumeBar = view.findViewById(R.id.consumeBar);
        consumeBar.setLocation(getMyActivity().getMyApplication().setting.getString(DEFALUT_LOCATION_CITY_NAME, SystemSet.DEF_LOC_NAME_VAL));

        consumeBar.setLocationChange((Province province, City city) -> {
            ParentActivity.city = city;
            loadData(url, "034");
        });


    }

    private ConsumeToobar consumeBar;


    @Override
    public void onError(IOException e, String what) {
        if ("034".equals(what)) {
            Toast.makeText(getActivity(), "网络加载出错", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onSucess(Response response, String what, String... backData) throws IOException {

        Gson gson = new Gson();
        List<Card> cards = gson.fromJson(backData[0], new TypeToken<List<Card>>() {//这里我们直接获得所有的数据，减低和服务器交互
        }.getType());

        Looger.D("加载数据=" + cards.size());


        for (int i = 0; i < cards.size(); i++) {

            View view;
            view = LayoutInflater.from(getContext()).inflate(R.layout.card_sub_dis, null, false);

            int finalI = i;
            view.findViewById(R.id.setmeal).setOnClickListener(new OnItemClickListener() {
                @Override
                public void onClick(View v) {
                    super.onClick(v);
                    Intent intent = new Intent(getMyActivity(), CardDetailActivity.class);
                    intent.putExtra(Card.class.getSimpleName(), cards.get(finalI));
                    startActivity(intent);
                }
            });

            TextView name = view.findViewById(R.id.card);
            TextView price = view.findViewById(R.id.card_price);
            TextView card_des = view.findViewById(R.id.card_des);
            ImageView card_img = view.findViewById(R.id.card_img);

            Card card = cards.get(i);

            name.setText(card.getCardName());
            price.setText(card.getPrice() + "元");
            card_des.setText(card.getOprator());
            Glide.with(getContext()).load(NetRequest.serverMain + card.getRoughImageAddress()).into(card_img);

            int width = cardContainer.getMeasuredWidth() / 3;

            cardContainer.addView(view, new ViewGroup.LayoutParams(width, -2));
        }
    }
}
