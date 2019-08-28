package com.xiaofangfang.filterrice.Fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.xiaofangfang.filterrice.InsertResponse.ViewInsertResponse;
import com.xiaofangfang.filterrice.InsertResponse.ViewOprateCode;
import com.xiaofangfang.filterrice.R;
import com.xiaofangfang.filterrice.UpdateResponse.ViewUpdateResponse;
import com.xiaofangfang.filterrice.ViewDataBean.BannerDataRespnse;
import com.xiaofangfang.filterrice.ViewDataBean.FunctionDataResponse;
import com.xiaofangfang.filterrice.consumeView.BannerFlipContainer;
import com.xiaofangfang.filterrice.consumeView.ViewOprate;
import com.xiaofangfang.filterrice.tool.ClientActivityName;
import com.xiaofangfang.filterrice.tool.ConsumeViewMapping;
import com.xiaofangfang.filterrice.tool.NetRequest;
import com.xiaofangfang.filterrice.tool.SystemSetting;
import com.xiaofangfang.filterrice.tool.ViewResponseMapping;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class FragmentPageOne extends Fragment {


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        onloadData();
    }

    private String url;

    Handler handler;

    ExecutorService executorService;

    private void onloadData() {

        url = SystemSetting.servler + "/ViewCenter?"
                + "action=" + this.getClass().getSimpleName();

        Log.d("test", "url=" + url);


        handler = new Handler() {

            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 001:
                        Toast.makeText(getContext(), "请求失败", Toast.LENGTH_SHORT).show();
                        break;
                    case 002:
                        String responseData = (String) msg.obj;
                        progressResponseData(responseData);
                        break;
                }
            }
        };


        executorService = Executors.newSingleThreadExecutor();

        executorService.execute(() -> {

            NetRequest.requestUrl(url, new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    Message message = handler.obtainMessage();
                    message.what = 001;
                    handler.sendMessage(message);
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    Message message = handler.obtainMessage();
                    message.what = 002;
                    message.obj = response.body().string();
                    handler.sendMessage(message);
                }
            });


        });

    }

    /**
     * 处理返回的响应对象
     */
    private void progressResponseData(String responseData) {
        JSONArray jsonArray = null;
        Gson gson = new Gson();

        try {
            jsonArray = new JSONArray(responseData);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String currentJson = jsonObject.toString();


                int oprateCode = jsonObject.getInt("oprateCode");

                if (oprateCode == ViewOprateCode.update.getOprateCode()) {
                    int index = jsonObject.getInt("index");
                    ViewOprate viewOprate = (ViewOprate) view.getChildAt(index);
                    Class responseClass = ViewResponseMapping.getUpdateResponseClass(viewOprate.getClass().getSimpleName());
                    ViewUpdateResponse viewResponse = (ViewUpdateResponse) gson.fromJson(currentJson, responseClass);
                    viewOprate.updateDataResponse(viewResponse.getResponse(), viewResponse.updateLeavel);

                } else if (oprateCode == ViewOprateCode.insert.getOprateCode()) {//插入和删除

                    int index = jsonObject.getInt("index");
                    ViewOprate viewOprate = null;
                    int insertLeavel = jsonObject.getInt("insertLeavel");
                    String consumeViewName = jsonObject.getString("consumeViewName");
                    viewOprate = ConsumeViewMapping.getView(consumeViewName, getContext());
                    Class responseClass = ViewResponseMapping.getInsertResponseClass(viewOprate.getClass().getSimpleName());
                    ViewInsertResponse viewResponse = (ViewInsertResponse) gson.fromJson(currentJson, responseClass);
                    viewOprate.updateDataResponse(viewResponse.getResponse(), insertLeavel);
                    view.addView((View) viewOprate, index);


                } else if (oprateCode == ViewOprateCode.delete.getOprateCode()) {


                }

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    ViewGroup view;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = (ViewGroup) inflater.inflate(R.layout.fragment_page_one, container, false);
        initView((ViewGroup) view);
        return view;
    }


    BannerFlipContainer bannerFlipContainer;

    private void initView(ViewGroup view) {

        initBanner(view);
        initFunctionMode(view);

    }

    private void initFunctionMode(ViewGroup view) {
        int imageAddress[] = {

                R.drawable.ic_register_vip,
                R.drawable.ic_dianhuazixun_big,
                R.drawable.ic_qiyefuwu_big,
                R.drawable.ic_publish_phone
        };


        String[] titleArray = {
                "注册会员", "电话咨询",
                "企业服务", "发布二手机"
        };

        String titleTag = "常用快捷";
        List<FunctionDataResponse.FunctionModeSingleBean> singleBeans = new ArrayList<>();
        for (int i = 0; i < titleArray.length; i++) {

            singleBeans.add(new FunctionDataResponse.FunctionModeSingleBean(imageAddress[i], titleArray[i], null, null));
        }

        FunctionDataResponse functionDataResponse = new FunctionDataResponse(titleTag, singleBeans);

    }

    private void initBanner(ViewGroup view) {
        int[] imageAddress = {

                R.drawable.banner1,
                R.drawable.banner2,
                R.drawable.banner3,
                R.drawable.banner4,
                R.drawable.banner5

        };

        String[] title = {
                "套餐推荐,获得推荐套餐",
                "最新的优惠", "最便宜的价格",
                "最实惠的套餐", "最优惠的手机",
        };

        String[] clickStartActivity = {

                ClientActivityName.ProductSalePageActivity.activityName,
                ClientActivityName.RecentDiscountActivity.activityName,
                ClientActivityName.FlowHandleActivity.activityName,
                ClientActivityName.QueryBillActivity.activityName,
                ClientActivityName.QueryBillActivity.activityName
        };

        String[] tableNames = {
                null, null, null, null, null
        };

        List<BannerDataRespnse.SingleBannerDataResponse> singleBannerDataResponses = new ArrayList<>();


        for (int i = 0; i < imageAddress.length; i++) {

            BannerDataRespnse.SingleBannerDataResponse singleBannerDataResponse =
                    new BannerDataRespnse.SingleBannerDataResponse(imageAddress[i], null, title[i], clickStartActivity[i], tableNames[i]);
            singleBannerDataResponses.add(singleBannerDataResponse);
        }
        BannerDataRespnse bannerDataRespnse = new BannerDataRespnse(singleBannerDataResponses);
        bannerFlipContainer = view.findViewById(R.id.bannerFlipper);
        bannerFlipContainer.setDataResponse(bannerDataRespnse);
    }


}
