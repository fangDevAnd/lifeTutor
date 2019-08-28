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
import com.xiaofangfang.lifetatuor.model.news.Sociology;
import com.xiaofangfang.lifetatuor.model.news.parent.CommonNews;
import com.xiaofangfang.lifetatuor.set.SettingStandard;
import com.xiaofangfang.lifetatuor.tools.GsonParseData;
import com.xiaofangfang.lifetatuor.tools.Looger;
import com.xiaofangfang.lifetatuor.tools.UiThread;

/**
 * 社会的相关新闻的fragment
 */
public class SociologyFragment extends ParentFragment {


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkUpdate(Sociology.class.getSimpleName(),
                SettingStandard.News.NewsType.SHEHUI);
    }


    @Override
    protected void progressResult(String responseData) {
        response=responseData;
        Sociology sociology = new Sociology();
        CommonNews commonNews = GsonParseData.parseNews(responseData,
                sociology);
        sociology = new Sociology(commonNews);
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
                inflater.inflate(R.layout.news_sociology, container, false);

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
        DbOpener.saveInfo(Sociology.class, getContext(), response);
        super.onDestroy();
        Looger.d("fragment被销毁");
    }
}
