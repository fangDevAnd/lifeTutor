package com.xiaofangfang.rice2_verssion.activity;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.xiaofangfang.rice2_verssion.Fragment.subFragment.HandSaleMainFragment;
import com.xiaofangfang.rice2_verssion.Fragment.subFragment.ProductSaleFilterFragment;
import com.xiaofangfang.rice2_verssion.Fragment.subFragment.SaleOpration;
import com.xiaofangfang.rice2_verssion.ParentActivity;
import com.xiaofangfang.rice2_verssion.R;
import com.xiaofangfang.rice2_verssion.network.NetRequest;
import com.xiaofangfang.rice2_verssion.tool.Looger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import okhttp3.Response;

/**
 * 这个界面是产品销售的主界面，
 * 套餐卡的视图，最新手机的视图，二手机的视图
 * 全部通过该界面进行数据的赛选
 * 那么 进行数据的不同操作的是
 * <p>
 * 在进入该界面的时候会默认传递   tableName的string额外数据，   secondHandPhone代表的是二手手机的显示操作
 */

public class ProductSalePageActivity extends ParentActivity implements View.OnClickListener {

    public static final String EXTRA_NAME = "filter";


    private HandSaleMainFragment handSaleMainFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.product_sale_page);
        super.onCreate(savedInstanceState);
        //加载数据
        initData();
    }

    @Override
    public void initView() {
        drawerLayout = findViewById(R.id.drawableLayout);
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        drawerLayout.addDrawerListener(simpleDrawerListener);

        submitButton = findViewById(R.id.submit);
        submitButton.setOnClickListener(this);

        findViewById(R.id.backupPage).setOnClickListener((v) -> {
            finish();
        });

        initFilterFragmentLayout();
        loadHandLayout();
    }


    /**
     * 用来接受用户请求的过滤条件
     */
    public String filter;

    /**
     * 用于对数据的加载,这里面由于可能是通过点击对应的filter条件进来的,所以需要把保存对应的过滤条件
     * 这里的数据我们规定如下  返回的数据如下面
     * callcount=99&mscount=20&flowcount=4
     * 当然也存在下面的情况
     * 运营商=中国电信
     * 通话时长=中国移动
     * 上网流量=100分钟以下
     */
    private void initData() {

        Intent intent = getIntent();
        filter = intent.getStringExtra(EXTRA_NAME);
    }

    private DrawerLayout drawerLayout;
    private Button cancel, submitButton;//清空过滤条件


    //销售的过滤界面的显示
    private ProductSaleFilterFragment productSaleFilterFragment;


    public ProductSaleFilterFragment getProductSaleFilterFragment() {
        return productSaleFilterFragment;
    }


    /**
     * 初始化过滤界面的fragment的显示布局效果
     */
    private void initFilterFragmentLayout() {

        productSaleFilterFragment = new ProductSaleFilterFragment();
        replaceFragment(productSaleFilterFragment, R.id.fragmentReplaceLayout);
        productSaleFilterFragment.setInnerItemClickListener(
                (View view, ViewGroup viewParent, int position, boolean isSelected, String title) -> {
                });
        initCardFilterLayout();
    }


    private List<ProductSaleFilterFragment.MyFilterCondition> filterConditions;

    private String setmealType = "套餐类型";

    /**
     * 初始化卡的商品过滤的显示效果
     */
    public void initCardFilterLayout() {
        filterConditions = new ArrayList<>();
        //添加卡的类型
        List<String> strings1 = Arrays.asList(getResources().getStringArray(R.array.cardCall));


        ProductSaleFilterFragment.MyFilterCondition callTIme =
                new ProductSaleFilterFragment.MyFilterCondition("通话时长", strings1);
        filterConditions.add(callTIme);


        List<String> strings2 = Arrays.asList(getResources().getStringArray(R.array.flow));
        ProductSaleFilterFragment.MyFilterCondition flowCount =
                new ProductSaleFilterFragment.MyFilterCondition("上网流量", strings2);
        filterConditions.add(flowCount);

        filterConditions.add(new ProductSaleFilterFragment.MyFilterCondition("运营商", Arrays.asList(getResources().getStringArray(R.array.oprator))));

        //todo  这个请求的url需要改掉
        String url = NetRequest.productSalePageAction
                + "?class="
                + this.getClass().getSimpleName()
                + "&BrandType=1"
                + "&cityId="
                + ParentActivity.city.getCityId();
        loadData(url, "005");
    }

    /**
     * 加载二手的界面布局
     */
    private void loadHandLayout() {
        handSaleMainFragment = new HandSaleMainFragment();
        replaceFragment(handSaleMainFragment, R.id.mainPage);
        lastSaleFilterParam = productSaleFilterFragment.getFilterConditionParamString();
    }


    /**
     * 替换过滤界面显示的fragment ，同时也包括主界面的过滤菜单对应的fragment
     * R.id.fragmentReplaceLayout
     *
     * @param fragment R.id.mainPage
     */
    public void replaceFragment(Fragment fragment, int id) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(id, fragment);
        transaction.commit();
    }


    /**
     * 打开抽屉
     */
    public void openDrawer() {
        drawerLayout.openDrawer(Gravity.RIGHT);
    }


    /**
     * 上一次的过滤参数
     */
    private String lastSaleFilterParam;

    /**
     * 过滤条件里面对过滤条件进行重置以及实现提交的按钮
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.submit://提交按钮
                submitHandData(handSaleMainFragment);
                break;
        }
    }

    /**
     * 提交二手的界面的显示的布局的筛选的条件
     */
    private void submitHandData(SaleOpration saleOpration) {

        String url = productSaleFilterFragment.getFilterConditionParamString();
        Looger.D("请求的过滤面板的url=" + url);
        if ((!TextUtils.isEmpty(lastSaleFilterParam)) && (!(TextUtils.isEmpty(url)))) {//存在   存在
            compareParam(url, saleOpration);
            return;
        }
        if ((!TextUtils.isEmpty(lastSaleFilterParam)) && TextUtils.isEmpty(url)) {
            initParamAndStatus(saleOpration, url);
            return;
        }
        if ((!TextUtils.isEmpty(url)) && TextUtils.isEmpty(lastSaleFilterParam)) {
            if (saleOpration instanceof HandSaleMainFragment) {
                handSaleMainFragment.setConsumeFilter(false);
            }
            initParamAndStatus(saleOpration, url);
            return;
        }
        if (TextUtils.isEmpty(lastSaleFilterParam) && TextUtils.isEmpty(url)) {
            compareParam(url, saleOpration);
            return;
        }
    }

    public void compareParam(String url, SaleOpration saleOpration) {
        if (lastSaleFilterParam.equals(url)) {
            drawerLayout.closeDrawer(Gravity.RIGHT);
        } else {
            initParamAndStatus(saleOpration, url);
        }
    }

    public void initParamAndStatus(SaleOpration saleOpration, String url) {
        saleOpration.setFilterFragmentCondition(url);
        saleOpration.initParam();
        saleOpration.loadData();
        drawerLayout.closeDrawer(Gravity.RIGHT);
        lastSaleFilterParam = url;
    }


    private DrawerLayout.SimpleDrawerListener simpleDrawerListener = new DrawerLayout.SimpleDrawerListener() {
        @Override
        public void onDrawerSlide(View drawerView, float slideOffset) {
            super.onDrawerSlide(drawerView, slideOffset);
        }

        @Override
        public void onDrawerOpened(View drawerView) {
            super.onDrawerOpened(drawerView);
            drawerView.setClickable(true);
        }

        @Override
        public void onDrawerClosed(View drawerView) {
            super.onDrawerClosed(drawerView);
        }
    };


    @Override
    public void onSucessful(Response response, String what, String... backData) throws IOException {
        super.onSucessful(response, what);
        Gson gson = new Gson();
        final List<String> BrandType = gson.fromJson(backData[0], new TypeToken<List<String>>() {
        }.getType());

        if (what.equals("005")) {
            ProductSaleFilterFragment.MyFilterCondition pingpai =
                    new ProductSaleFilterFragment.MyFilterCondition(setmealType, BrandType);
            filterConditions.add(pingpai);
            productSaleFilterFragment.setMyFilterConditions(filterConditions);
            productSaleFilterFragment.updateData();
        }

    }


    @Override
    public void onError(IOException e, String what) {
        super.onError(e, what);
        if (what.equals("005")) {
            productSaleFilterFragment.setMyFilterConditions(filterConditions);
            productSaleFilterFragment.updateData();
        }
    }


}
