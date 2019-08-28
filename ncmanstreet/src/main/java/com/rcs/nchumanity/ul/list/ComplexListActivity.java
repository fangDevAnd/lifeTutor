package com.rcs.nchumanity.ul.list;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.annotation.UiThread;

import com.rcs.nchumanity.R;
import com.rcs.nchumanity.adapter.ListViewCommonsAdapter;
import com.rcs.nchumanity.ul.BasicResponseProcessHandleActivity;
import com.rcs.nchumanity.ul.ParentActivity;
import com.rcs.nchumanity.view.CommandBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * 这个列表是复用功能模块的代码
 * 用来实现的具体的操作从数据库中获取相对应的数据的信息
 * ，本身是一个列表，的展示，对于这个界面，存在两种情况、
 * <p>
 * 1.对于基本的信息展示的话，只需要跳转到
 * #{{@link ComplexListDetailActivity}} 界面进行文本信息的展示
 * 2.另一种情况，需要跳转到 地图的展示界面，传递的数据类似于一个地图点，我们根据地图点，进入
 */
public abstract class ComplexListActivity<T> extends BasicResponseProcessHandleActivity implements AbsListView.OnScrollListener {


    private static final String TAG = "test";
    @BindView(R.id.listView)
    ListView listView;

    ListViewCommonsAdapter<T> lvca;

    private List<T> tList;

    @BindView(R.id.toolbar)
    CommandBar toolbar;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complex);
        ButterKnife.bind(this);

        tList = new ArrayList<>();


        listView.setAdapter(lvca = new ListViewCommonsAdapter<T>((ArrayList<T>) tList, getLayout()) {
            @Override
            public void bindView(ViewHolder holder, T obj) {
                bindViewValue(holder, obj);
            }

            @Override
            public int getCount() {
                return tList.size();
            }
        });

        listView.setOnItemClickListener((AdapterView<?> parent, View view, int position, long id) -> {

            Log.d(TAG, "click: ====");
            itemClick(parent, view, position, id, lvca.getItem(position));
        });

        listView.setOnScrollListener(this);

    }

    /**
     * 用来实现对应的ListView不同的view的数据初始化操作
     *
     * @param holder
     * @param obj
     */
    protected abstract void bindViewValue(ListViewCommonsAdapter.ViewHolder holder, T obj);

    /**
     * @param parent
     * @param view
     * @param position
     * @param id
     * @param item
     */
    protected abstract void itemClick(AdapterView<?> parent, View view, int position, long id, T item);

    /**
     * 获得对应的布局
     *
     * @return
     */
    protected abstract int getLayout();


    /**
     * 用来设置数据
     * 当我们进行异步加载数据的时候，加载结束之后，通过调用该方法实现数据的设置
     *
     * @param listData
     */
    @UiThread
    public void setDataList(List<T> listData) {
        tList.clear();
        tList.addAll(listData);
        /**
         * 刷新UI
         */
        lvca.notifyDataSetChanged();

    }


    public void setTitle(String title) {
        if (toolbar != null) {
            toolbar.setTitle(title);
        }
    }


    /**
     * 添加数据
     *
     * @param listData
     */
    public void addDataList(List<T> listData) {
        tList.addAll(listData);
        lvca.notifyDataSetChanged();
    }


    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

        if (firstVisibleItem + visibleItemCount == totalItemCount && totalItemCount != 0) {
            scrollToBottom();
        }
    }

    protected void scrollToBottom() {
        Log.d(TAG, "滚动到最底部 ");
    }


}
