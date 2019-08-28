package com.xiaofangfang.rice2_verssion.Fragment.subFragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.xiaofangfang.rice2_verssion.Fragment.ParentFragment;
import com.xiaofangfang.rice2_verssion.R;
import com.xiaofangfang.rice2_verssion.view.ExpandableLayout;
import com.xiaofangfang.rice2_verssion.view.PriceSpace;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Response;

/**
 * 这个是过滤的界面，通过设置不同的参数，来对应的加载不同的过滤条件
 */
public class ProductSaleFilterFragment extends ParentFragment implements ExpandableLayout.OnItemClickListener {


    private ViewGroup viewGroup;
    private ExpandableLayout filterOne, filterTwo, filterThree, filterFour;
    private PriceSpace priceSpace;
    private StringBuilder filterUrl;
    private List<String> filterConditionList;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        filterConditionList = new ArrayList<>();
        filterUrl = new StringBuilder();
        myFilterConditions = new ArrayList<>();
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        viewGroup = (ViewGroup) inflater.inflate(R.layout.product_sale_filter_fragment_layout, null, false);
        filterView = new ArrayList<>();
        filterOne = viewGroup.findViewById(R.id.filterConditionOne);
        filterTwo = viewGroup.findViewById(R.id.filterConditionTwo);
        filterThree = viewGroup.findViewById(R.id.filterConditionThree);
        filterFour = viewGroup.findViewById(R.id.filterConditionFour);
        priceSpace = viewGroup.findViewById(R.id.filterPrice);
        //添加到集合中
        filterView.add(filterOne);
        filterView.add(filterTwo);
        filterView.add(filterThree);
        filterView.add(filterFour);

        // initData();
        addViewCache(viewGroup);
        return viewGroup;
    }


    /**
     * 初始化布局，决定了哪个布局可以显示出来
     */
    private void initData() {
        for (int i = 0; i < myFilterConditions.size(); i++) {
            ExpandableLayout expandableLayout = (ExpandableLayout) filterView.get(i);
            expandableLayout.setVisibility(View.VISIBLE);
            expandableLayout.setTitle(myFilterConditions.get(i).title);
            expandableLayout.setDataResource(myFilterConditions.get(i).filterItems);
            expandableLayout.setOnItemClickListener(this);
        }
    }

    /**
     * 对特定的专栏的数据的过滤进行数据条件的更新
     *
     * @param name
     */
    public void updateSpecficTitleFilterBar(String name, List<String> dataList) {

        for (int i = 0; i < myFilterConditions.size(); i++) {
            ExpandableLayout expandableLayout = (ExpandableLayout) filterView.get(i);
            if (expandableLayout.getTitle().equals(name)) {
                expandableLayout.setDataResource(dataList);
            }
        }
    }


    /**
     * 更新的数据内容
     */
    public void updateData() {
        initData();
    }

    /**
     * 增加缓存的view
     *
     * @param viewGroup
     */
    private void addViewCache(ViewGroup viewGroup) {

        for (ViewAdd viewAdd : viewAddList) {
            viewGroup.addView(viewAdd.view, viewAdd.index, viewAdd.layoutParams);
        }

    }

    /**
     * 动态的添加view，该界面的有一个点击按钮，用来执行具体的筛选
     * 被添加的view的点击事件的处理应该放在主界面中，通过点击将执行的查询条件附加到stringBuffer中
     * 当在这个按钮进行点击时，执行的是内容界面加载方法loadData()方法
     *
     * @param view
     */
    public void addView(View view, ViewGroup.LayoutParams layoutParams, int index) {
        viewAddList.add(new ViewAdd(view, index, layoutParams));
    }


    //存放过滤条件的集合
    private List<MyFilterCondition> myFilterConditions;
    //存放当前的条件布局view的集合
    private List<View> filterView;


    /**
     * 动态的添加view
     *
     * @param view
     * @param layoutParams
     */
    public void addView(View view, ViewGroup.LayoutParams layoutParams) {
        int count = viewAddList.size();
        addView(view, layoutParams, count);
    }

    public void addView(View view) {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-1, -2);
        addView(view, layoutParams);
    }

    /**
     * 设置过滤条件
     *
     * @param myFilterConditions
     */
    public void setMyFilterConditions(List<MyFilterCondition> myFilterConditions) {
        this.myFilterConditions = myFilterConditions;
    }

    //存放需要被添加的view
    List<ViewAdd> viewAddList = new ArrayList<>();


    /**
     * 内部控件的点击事件的处理
     *
     * @param view       点击的按钮
     * @param viewParent 被点击的view的parent 是一个线性布局
     * @param position   点击控件在父布局的位置，也就是在parent的位置
     * @param isSelected 是否被选中
     */
    @Override
    public void onClick(View view, ViewGroup viewParent, int position, boolean isSelected, String title) {
        String value = title + "=" + ((Button) view).getText().toString();
        if (isSelected) {
            filterConditionList.add(value);
        } else {
            filterConditionList.remove(value);
        }

        int sb_length = filterUrl.length();// 取得字符串的长度
        filterUrl.delete(0, sb_length);

        for (String value1 : filterConditionList) {
            filterUrl.append("&").append(value1);
        }
        if (innerItemClickListener != null) {
            innerItemClickListener.onClick(view, viewParent, position, isSelected, title);
        }

    }


    /**
     * 这个方法是这个界面最核心的方法，过滤的条件都来至这个界面的返回
     *
     * @return
     */
    public String getFilterConditionParamString() {

        if (filterUrl == null) {
            return "";
        }
        //当priceSpace初始化完成过后
        if (priceSpace != null) {

            int[] rank = priceSpace.getPriceRank();
            String value3 = null;
            if (rank[0] != -1) {
                value3 = "&startPrice=" + rank[0];
            }
            if (rank[1] != -1) {
                value3 += "&endPrice=" + rank[1];
            }

            if (value3 != null) {
                filterUrl.append(value3);
            }
        }
        return filterUrl.toString();
    }


    /**
     * 清空过滤条件的状态以及数据
     */
    public void cancelFilterConditionStaus() {

    }


    private class ViewAdd {
        View view;
        LinearLayout.LayoutParams layoutParams;
        int index;

        public ViewAdd(View view, int index, ViewGroup.LayoutParams layoutParams) {
            this.view = view;
            this.layoutParams = (LinearLayout.LayoutParams) layoutParams;
            this.index = index;
        }
    }


    /**
     * 过滤条件的包装对象
     */
    public static class MyFilterCondition {

        private String title;
        private List<String> filterItems;

        public MyFilterCondition(String title, List<String> filterItems) {
            this.title = title;
            this.filterItems = filterItems;
        }

        public MyFilterCondition() {
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public List<String> getFilterItems() {
            return filterItems;
        }

        public void setFilterItems(List<String> filterItems) {
            this.filterItems = filterItems;
        }
    }


    /**
     * 内部的item的点击事件的处理
     */
    public interface InnerItemClickListener {
        void onClick(View view, ViewGroup viewParent, int position, boolean isSelected, String title);
    }

    private InnerItemClickListener innerItemClickListener;

    public void setInnerItemClickListener(InnerItemClickListener innerItemClickListener) {
        this.innerItemClickListener = innerItemClickListener;
    }


    @Override
    public void onError(IOException e, String what) {

    }

    @Override
    public void onSucess(Response response, String what, String... backData) {

    }


}
