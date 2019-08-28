package com.xiaofangfang.lifetatuor.Activity.fragment.newsFragment;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xiaofangfang.lifetatuor.Activity.fragment.newsFragment.parent.ParentFragment;
import com.xiaofangfang.lifetatuor.R;
import com.xiaofangfang.lifetatuor.dao.DbOpener;
import com.xiaofangfang.lifetatuor.model.news.Domestic;
import com.xiaofangfang.lifetatuor.model.news.parent.CommonNews;
import com.xiaofangfang.lifetatuor.set.SettingStandard;
import com.xiaofangfang.lifetatuor.tools.GsonParseData;
import com.xiaofangfang.lifetatuor.tools.Looger;
import com.xiaofangfang.lifetatuor.tools.UiThread;

/**
 * 国内新闻
 */
public class DomesticFragment extends ParentFragment {


    /**
     * 我们在程序进行加载的工程中,使用的是预加载技术,那么对于每次的数据我么应该使用的是
     * 先加载硬盘资源,然后进行刷新时加载而网络资源,
     * <p>
     * 对于上边的刷新我们使用的是 直接匹配数据,不进行数据存储操作
     * 对于下边的刷新由于我们需要保持上边的数据不会出现丢失,我们使用的append的方法
     * 向Listview的Adapter里面添加一个add方法添加附加的数据源信息
     * <p>
     * <p>
     * 考虑到存放序列化数据较为麻烦(解析过程比较耗费内存资源),
     * 我们将没有进行反序列化为类的数据保存下来,也就是我们在当前的fragment进行销毁的时候我们
     * 将会话信息进行保存
     *
     * @param savedInstanceState
     */


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /**
         * 检查更新
         */
        checkUpdate(Domestic.class.getSimpleName(),
                SettingStandard.News.NewsType.GUONEI);

    }


    protected void progressResult(String responseData) {
        /**
         *接收到信息的回调
         */
        response=responseData;
        Domestic domestic = new Domestic();
        CommonNews commonNews = GsonParseData.parseNews(
                responseData, domestic);
        domestic = new Domestic(commonNews);
        onstartRunUi();
    }

    private void onstartRunUi() {

        UiThread.getUiThread(getContext()).post(new Runnable() {
            @Override
            public void run() {

            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        ViewGroup viewGroup = (ViewGroup)
                inflater.inflate(R.layout.news_domestic, container, false);

        initView(viewGroup);

        return viewGroup;
    }

    /**
     * 初始化里面的子view
     *
     * @param viewGroup
     */
    private void initView(ViewGroup viewGroup) {


    }


    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        // Looger.d("开始保存状态");
        //DbOpener.saveInfo(Domestic.class, getContext(), response);
    }


    /**
     * 在视图被销毁的时候保存网页的会话数据
     */
    @Override
    public void onDestroy() {
        DbOpener.saveInfo(Domestic.class, getContext(), response);
        super.onDestroy();
        Looger.d("fragment被销毁");
    }
}
