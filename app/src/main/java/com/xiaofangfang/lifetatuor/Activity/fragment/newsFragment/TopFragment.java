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
import com.xiaofangfang.lifetatuor.model.news.TopInfo;
import com.xiaofangfang.lifetatuor.model.news.parent.CommonNews;
import com.xiaofangfang.lifetatuor.set.SettingStandard;
import com.xiaofangfang.lifetatuor.tools.GsonParseData;
import com.xiaofangfang.lifetatuor.tools.Looger;
import com.xiaofangfang.lifetatuor.tools.UiThread;

/**
 * 头条新闻
 */
public class TopFragment extends ParentFragment {


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkUpdate(TopInfo.class.getSimpleName(),
                SettingStandard.News.NewsType.TOP);
    }


    @Override
    protected void progressResult(String responseData) {
        response = responseData;
        TopInfo topInfo = new TopInfo();
        CommonNews commonNews = GsonParseData.parseNews(responseData,
                topInfo);
        topInfo = new TopInfo(commonNews);
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
                inflater.inflate(R.layout.news_top, container, false);

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
    public void onDestroy() {
        super.onDestroy();
        DbOpener.saveInfo(TopInfo.class, getContext(), response);
        super.onDestroy();
        Looger.d("fragment被销毁");
    }
}
