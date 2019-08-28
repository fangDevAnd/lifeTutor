package com.xiaofangfang.rice2_verssion.Fragment.subFragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.xiaofangfang.rice2_verssion.Fragment.ParentFragment;
import com.xiaofangfang.rice2_verssion.ParentActivity;
import com.xiaofangfang.rice2_verssion.R;
import com.xiaofangfang.rice2_verssion.activity.CardDetailActivity;
import com.xiaofangfang.rice2_verssion.activity.ProductSalePageActivity;
import com.xiaofangfang.rice2_verssion.model.Card;
import com.xiaofangfang.rice2_verssion.network.NetRequest;
import com.xiaofangfang.rice2_verssion.tool.LoadProgress;
import com.xiaofangfang.rice2_verssion.tool.Looger;
import com.xiaofangfang.rice2_verssion.tool.UiThread;
import com.xiaofangfang.rice2_verssion.view.FilterConditionNew1Bar;
import com.xiaofangfang.rice2_verssion.view.adapter.MyAdapter;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;


/**
 * 二手的布局视图的显示
 * 这是一个新的布局的显示
 */
public class HandSaleMainFragment extends ParentFragment implements SaleOpration,
        FilterConditionNew1Bar.FilterOptionClickListner, FilterConditionNew1Bar.LocationChangeListener {

    //价格   地区  过滤
    private FilterConditionNew1Bar filterConditionNew1Bar;
    private MyAdapter<Card> cardMyAdapter;
    private ArrayList<Card> cardList = new ArrayList<>();
    private int[] viewAddress = {
            R.layout.card_sale_product_layout_page
    };

    public void setConsumeFilter(boolean consumeFilter) {
        this.consumeFilter = consumeFilter;
    }

    public boolean isConsumeFilter() {
        return consumeFilter;
    }

    /**
     * 过滤条过滤的条件 也就是地区和价格的过滤条件
     */
    private String filterConditionParamString;

    //代表的是自定义的查询条件，刚进去之后使用的是自定义的查询条件
    private boolean consumeFilter = true;

    /**
     * 请求的数据的页数
     */
    private int page = 0;

    /**
     * 当前的请求的数据的数量
     */
    private int size = 10;
    /**
     * 过滤界面的过滤的数据
     */
    private String filterFragmentCondition = "";
    /**
     * 产品的列表
     */
    private ListView productList;
    /**
     * 代表的是没有数据
     */
    private boolean notData;

    /**
     * 代表的是否能够进行刷新，默认的状态下是true
     * 之所以采用这个的判断是源于我们滑动到底层的时候会一直触发loadData()事件，这时会产生多线程问题
     * 当isFlush=true,代表的是数据刷新成功，也就代表这我们可以在一次的进行刷新
     */
    private boolean isFlush = true;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.hand_product_sale_fragment_layout, container, false);

        filterConditionNew1Bar = view.findViewById(R.id.filterConditionBar);
        filterConditionParamString = filterConditionNew1Bar.getFilterCondition();
        productList = view.findViewById(R.id.productList);
        productList.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

                if (firstVisibleItem + visibleItemCount == totalItemCount && totalItemCount != 0) {
                    if (isFlush) {
                        isFlush = false;
                        page++;
                        //页面++，然后继续加载数据
                        loadData();
                    }
                }
            }
        });

        productList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                cal = System.currentTimeMillis() - startTime;

                if (Math.abs(cal) < distance) {
                    return;
                }
                startTime = System.currentTimeMillis();

                Intent intent = getStartIntent();
                Object object;
                object = ((MyAdapter) productList.getAdapter()).getItem(position);
                intent.putExtra(object.getClass().getSimpleName() + "", (Serializable) object);
                startActivity(intent);
            }

        });

        initView();
        return view;
    }

    private long startTime;
    private long distance = 1000;
    private long cal;


    /**
     * 设置筛选fragment的条件
     *
     * @param filterFragmentCondition
     */
    public void setFilterFragmentCondition(String filterFragmentCondition) {
        this.filterFragmentCondition = filterFragmentCondition;
    }

    /**
     * 获得对应的赛选条件
     *
     * @return
     */
    public String getFilterConditionParamString() {
        return filterConditionParamString;
    }


    /**
     * 初始化参数以及布局
     */
    private void initView() {
        filterConditionNew1Bar.setFilterOptionClickListner(this);
        filterConditionNew1Bar.setLocationChangeListener(this);
        cardMyAdapter = new MyAdapter<Card>(cardList, viewAddress[0]) {
            @Override
            public void bindView(ViewHolder holder, final Card obj) {

                if (obj == null) {
                    return;
                }
                holder.setAsyncImageResource(R.id.card_image,
                        NetRequest.serverMain + obj.getRoughImageAddress());
                holder.setText(R.id.cardDestribute, obj.getCardName());
                if (obj.getTotalPrice() > 0) {
                    holder.setText(R.id.realPrice, "计算价格" + obj.getTotalPrice() + "元"); //month  price
                    holder.setText(R.id.price, obj.getMonthFee());
                } else {
                    holder.setText(R.id.realPrice, obj.getPrice() + "元"); //month  price
                    holder.setText(R.id.price, obj.getMonthFee());
                }
            }

            @Override
            public int getCount() {
                return cardList.size();
            }
        };
        productList.setAdapter(cardMyAdapter);
        loadData();

    }

    private String tableName;

    @Override
    public void setTableName(String tableName) {
        this.tableName = tableName;
    }


    private ProgressBar progressBar;

    private String url;

    /**
     * 获得请求的url
     *
     * @return
     */
    @Override
    public String getUrl() {
        return url;
    }

    @Override
    public void loadData() {

        //加载url
        loadUrl();

        Looger.D("请求的uri=" + url);

        loadData(url, "008");

        progressBar = LoadProgress.loadProgress(getContext());
        new Thread(new Runnable() {
            @Override
            public void run() {
                NetRequest.requestUrl(url, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        UiThread.getUiThread().post(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getActivity(), "请求失败", Toast.LENGTH_SHORT).show();
                                LoadProgress.removeLoadProgress(getContext(), progressBar);
                            }
                        });
                    }

                    @Override
                    public void onResponse(Call call, final Response response) throws IOException {

                        final String responseData = response.body().string();
                        UiThread.getUiThread().post(new Runnable() {
                            @Override
                            public void run() {
                                LoadProgress.removeLoadProgress(getContext(), progressBar);
                                progressBackData(responseData);
                            }
                        });
                    }
                });

            }
        }).start();


    }

    /**
     * 加载card的list数据
     */
    private void loadUrl() {

        if (consumeFilter) {
            url = NetRequest.productSalePageAction
                    + "?" + filterConditionParamString
                    + "&class=" + this.getClass().getSimpleName()
                    + "&page=" + page
                    + "&size=" + size
                    + ((ProductSalePageActivity) getActivity()).filter;
        } else {
            //这个代表的是请求的是我们通过主页的过滤条件进来的--->或则是通过显示界面的过滤条件点击进行的
            url = NetRequest.productSalePageAction +
                    "?" + filterConditionParamString
                    + "&class=" + this.getClass().getSimpleName()
                    + "&page=" + page
                    + "&size=" + size
                    + filterFragmentCondition;
        }


    }


    public void initParam() {

        if (cardList != null) {
            if (cardList.size() > 0) {
                this.cardList.clear();//不能直接进行赋值，这样会将原来的list指向新的位置，我们只能清除数据
            }
        }
        notData = false;
        page = 0;

    }

    @Override
    public Intent getStartIntent() {
        Intent intent = new Intent(getContext(), CardDetailActivity.class);
        return intent;
    }


    /**
     * 处理服务器返回的数据 ,返回的数据是Card的
     *
     * @param responseData
     */
    private void progressBackData(String responseData) {

        int dataListSize = 0;


        Gson gson = new Gson();

        final List<Card> cards = gson.fromJson(responseData, new TypeToken<List<Card>>() {
        }.getType());

//        for (int i = 0; i < cards.size(); i++) {
//            Looger.D(cards.get(i).toString());
//        }

        dataListSize = cards.size();
        if (dataListSize > 0) {

            if (cards.get(0).getTotalPrice() > 0 && cardList.size() > 0) {
                //代表的是通过排序进来的数据
                for (int j = 0; j < cards.size(); j++) {//20 50 40
                    for (int i = j; i < cardList.size(); i++) {//20 44 67  78 97
                        if (cardList.get(i).getTotalPrice() >= cards.get(j).getTotalPrice()) {
                            cardList.add(i, cards.get(j));
                            break;
                        }
                    }
                }
            } else {//反之通过其他
                this.cardList.addAll(cards);//添加过去的
            }
            cardMyAdapter.notifyDataSetChanged();
        }


        serverNotData(dataListSize);

        //当数据加载完成后，判断是否是进行的刷新，如果是就代表刷新结束
        if (!notData) {
            isFlush = true;//代表我们已经可以再一次的刷新了
        } else {
            isFlush = false;//如果没有数据，就不能在刷新
        }

    }

    private void serverNotData(int size) {
        if (size < this.size) {
            notData = true;
        }
    }


    @Override
    public void onPriceClick(View view, boolean currentPriceAsc) {
        filterConditionParamString = filterConditionNew1Bar.getFilterCondition();
        initParam();
        loadData();
    }

    @Override
    public void onFilterCondition(View view) {
        ((ProductSalePageActivity) getActivity()).openDrawer();
    }


    private String kw = "套餐";

    @Override
    public void onSucessfulResponse() {


        updateBrandType();

        filterConditionParamString = filterConditionNew1Bar.getFilterCondition();
        initParam();
        loadData();
    }

    @Override
    public void onError(IOException e, String what) {

    }

    @Override
    public void onSucess(Response response, String what, String... backData) throws IOException {
        if (what.equals("006")) {
            Gson gson = new Gson();
            final List<String> brandType = gson.fromJson(backData[0], new TypeToken<List<String>>() {
            }.getType());
            ((ProductSalePageActivity) getActivity()).getProductSaleFilterFragment().updateSpecficTitleFilterBar(kw, brandType);
        }
    }

    public void updateBrandType() {
        final String url = NetRequest.productSalePageAction + "?tableName=" + getClass() + "&BrandType=1" + "&cityId=" + ParentActivity.city.getCityId();
        loadData(url, "006");
    }


}
