package com.xiaofangfang.lifetatuor.Activity.fragment.menu.Fragment;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.xiaofangfang.lifetatuor.Activity.fragment.menu.Fragment.parent.ParentFragment;
import com.xiaofangfang.lifetatuor.R;
import com.xiaofangfang.lifetatuor.dao.DbOpener;
import com.xiaofangfang.lifetatuor.model.joke.ImgByTime;
import com.xiaofangfang.lifetatuor.model.joke.NewstImg;
import com.xiaofangfang.lifetatuor.model.joke.ResultImg;
import com.xiaofangfang.lifetatuor.net.requestModel.JockParamValue;
import com.xiaofangfang.lifetatuor.set.SettingStandard;
import com.xiaofangfang.lifetatuor.tools.GsonParseData;
import com.xiaofangfang.lifetatuor.tools.Looger;
import com.xiaofangfang.lifetatuor.tools.UiThread;
import com.xiaofangfang.lifetatuor.view.adapter.ImgListViewAdapter;

import java.util.ArrayList;

/**
 * 最新的动态图的Fragment
 */
public class NewImgFragment extends ParentFragment implements AbsListView.OnScrollListener {


    private boolean isReflushBUtton = false;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        JockParamValue jpv = new JockParamValue(
                SettingStandard.Joke.JokeParam.pageDefault,
                SettingStandard.Joke.JokeParam.rowDefault,
                SettingStandard.Joke.JokeParam.sortDefault,
                SettingStandard.Joke.JokeParam.timeDaymic
        );

        checkUpdate(NewstImg.class.getSimpleName(), jpv);
    }


    private NewstImg newstImg;

    /**
     * 在这里我们的程序依然是异步实现的,下面的程序依然运行在子线程
     *
     * @param responseData
     */
    @Override
    protected void progressResult(String responseData) {
        super.progressResult(responseData);
        response = responseData;
        Looger.d("获得到的结果集" + responseData);

        ImgByTime imgByTime = GsonParseData.parserJoke_img(resData);
        newstImg = new NewstImg(imgByTime);
        onstartRunUi();
    }

    static String resData = "{\"result\":[{\"content\":\"生活教你认清自己\",\"hashId\":\"3C912CA1B471EA55C5A655C1B0BC6EC9\",\"unixtime\":\"1503632630\",\"updatetime\":\"2017-08-25 11:43:50\",\"url\":\"http://api.avatardata.cn/Joke/Img?file=5f653701740b4797a937858b921cfe78.jpg\"},{\"content\":\"说得太好了...洗头更衣见朋友，那还叫什么休息！！\",\"hashId\":\"1610E54791285582D20BB9F391E6D173\",\"unixtime\":\"1503632630\",\"updatetime\":\"2017-08-25 11:43:50\",\"url\":\"http://api.avatardata.cn/Joke/Img?file=8d0fc0434f2f482492e5f58457cb5f8f.jpg\"},{\"content\":\"看到一位南通博主的微博，有人开始砸车了…\",\"hashId\":\"1419516A72D1216476D835324640A514\",\"unixtime\":\"1503632630\",\"updatetime\":\"2017-08-25 11:43:50\",\"url\":\"http://api.avatardata.cn/Joke/Img?file=ee9f4ba8096a4c6aa8396a7776a03b5a.jpg\"},{\"content\":\"如何看待母子之间的sex love！这题超纲了\",\"hashId\":\"E593419264F96854BB17F2B2FF4E7314\",\"unixtime\":\"1503632630\",\"updatetime\":\"2017-08-25 11:43:50\",\"url\":\"http://api.avatardata.cn/Joke/Img?file=2187379796a6442f8c3df1348ea94d42.jpg\"},{\"content\":\"你应该问干爹买\",\"hashId\":\"A31ECC960442C9602C947C81A483EF8A\",\"unixtime\":\"1503632630\",\"updatetime\":\"2017-08-25 11:43:50\",\"url\":\"http://api.avatardata.cn/Joke/Img?file=c155c0f7889241ad9f5ee396e6085507.jpg\"},{\"content\":\"这是囊肿吧！\",\"hashId\":\"3AFEEF423472E6F12C019811F9C609A5\",\"unixtime\":\"1503632630\",\"updatetime\":\"2017-08-25 11:43:50\",\"url\":\"http://api.avatardata.cn/Joke/Img?file=d3d329c1014f4de4be26ad0679658b89.gif\"},{\"content\":\"难道不应该是捡肥皂引起的吗？\",\"hashId\":\"F64E7F9E8C025DE9474BF7A23C5028FD\",\"unixtime\":\"1503632630\",\"updatetime\":\"2017-08-25 11:43:50\",\"url\":\"http://api.avatardata.cn/Joke/Img?file=7a803ce42835472c949994c04dda2d26.gif\"},{\"content\":\"职业操守\",\"hashId\":\"39DFEC8382D592D3F1F55FC9BE38D0FD\",\"unixtime\":\"1503632630\",\"updatetime\":\"2017-08-25 11:43:50\",\"url\":\"http://api.avatardata.cn/Joke/Img?file=398f70891a98443da2b7fe2c4056d088.jpg\"},{\"content\":\"受到了无形的伤害\",\"hashId\":\"15742AB4F6A066B1B153B40DAD707600\",\"unixtime\":\"1503632630\",\"updatetime\":\"2017-08-25 11:43:50\",\"url\":\"http://api.avatardata.cn/Joke/Img?file=d5d4780a224f4afeb20b62e6b1621e96.jpg\"},{\"content\":\"感受一下什么才叫直播神器！\",\"hashId\":\"B0DEE6FCE4408185D7834444D45AC3F4\",\"unixtime\":\"1503632630\",\"updatetime\":\"2017-08-25 11:43:50\",\"url\":\"http://api.avatardata.cn/Joke/Img?file=3751295928de43e8b6d360b0fa8c69ec.jpg\"}],\"error_code\":0,\"reason\":\"Succes\"}";

    private void onstartRunUi() {
        if (newImgListViewAdapter == null) {//如果是第一次加载,
            // 那么按照没有加载完成,这里一定是本地
            //加载
            newImgListViewAdapter = new ImgListViewAdapter(
                    getContext(), newstImg.getResult());
            UiThread.getUiThread(getContext()).post(new Runnable() {
                @Override
                public void run() {

                    newImgListView.setAdapter(newImgListViewAdapter);
                }
            });
        } else if (newImgListViewAdapter != null) {

            UiThread.getUiThread(getContext()).post(new Runnable() {
                @Override
                public void run() {
                    if (isReflushBUtton) {  //代表的是使用的通过上拉按钮进行刷新的
                        srf.setRefreshing(false);
                        newImgListViewAdapter.removeListResultImgs();
                        newImgListViewAdapter.addListResultImgs(newstImg.getResult());
                        isReflushBUtton = false;
                    } else { //这里代表的是通过下拉进行刷新的

                        newImgListViewAdapter.addListResultImgs(newstImg.getResult());
                    }
                }
            });
            executeUpdate = true;
        }
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        ViewGroup viewGroup = (ViewGroup)
                inflater.inflate(R.layout.newimg, container, false);

        initView(viewGroup);

        return viewGroup;
    }

    ListView newImgListView;
    ImgListViewAdapter newImgListViewAdapter;
    private boolean executeUpdate = true;
    SwipeRefreshLayout srf;

    /**
     * 初始化里面的子view
     *
     * @param viewGroup
     */
    private void initView(ViewGroup viewGroup) {
        newImgListView = viewGroup.findViewById(
                R.id.newImgListview);
        //这里的操作是赋一个初值
        if (newstImg != null && newstImg.getResult() != null) {
            newImgListViewAdapter = new ImgListViewAdapter(
                    getActivity(), newstImg.getResult());
        } else {
            newImgListViewAdapter = new ImgListViewAdapter(
                    getActivity(), new ArrayList<ResultImg>());
        }
        newImgListView.setAdapter(newImgListViewAdapter);
        newImgListView.setOnScrollListener(this);


        srf = viewGroup.findViewById(R.id.swip_refresh_newimg);
        srf.setColorSchemeResources(R.color.menu_tab_indicator_color);

        /**
         * 刷新监听
         */
        srf.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                isReflushBUtton = true;
                //在这里执行刷新
                JockParamValue jpv = new JockParamValue(
                        SettingStandard.Joke.JokeParam.pageDefault,
                        SettingStandard.Joke.JokeParam.rowDefault,
                        SettingStandard.Joke.JokeParam.sortDefault,
                        SettingStandard.Joke.JokeParam.timeDaymic
                );
                requestData(NewstImg.class.getSimpleName(), jpv);
            }
        });

    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        DbOpener.saveInfo(NewstImg.class, getContext(), response);
        super.onDestroy();
        Looger.d("fragment被销毁");
    }


    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem,
                         int visibleItemCount, int totalItemCount) {
        Looger.d(firstVisibleItem + "  " + visibleItemCount + " " + totalItemCount);

        View view1 = newImgListViewAdapter.getView(totalItemCount - 1, null, null);

        if (firstVisibleItem + visibleItemCount == totalItemCount
                && totalItemCount > 1) {  //滚动到最后一行
            //在这里我们执行buttonImage的动画显示.然后继续加载属兔添加到LIstView中
            view1.setVisibility(View.VISIBLE);
            executeButtonLoadAnimation(view1);
        } else {//也就是我们没有拉倒最下面的时候,我们设置ProgressBar的可见性为INVISIBLE
            //获得当前的最后一位
            view1.setVisibility(View.INVISIBLE);
        }
    }

    private void executeButtonLoadAnimation(View view) {
        if (executeUpdate) {
            executeUpdate = false;
            Looger.d("最后一个view的名称是" + view.getClass().getSimpleName());
            ViewGroup viewGroup = (ViewGroup) view;
            ProgressBar pb = viewGroup.findViewById(R.id.loadDataProgressbar);
            //下面开始加载刷新视图,每次刷新加1

            JockParamValue jpv = new JockParamValue(
                    "",
                    SettingStandard.Joke.JokeParam.rowDefault,
                    SettingStandard.Joke.JokeParam.sortDefault,
                    ""
            );
            requestData(NewstImg.class.getSimpleName(), jpv);
        }
    }


}
