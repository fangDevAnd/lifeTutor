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
import com.xiaofangfang.lifetatuor.model.joke.ResultImg;
import com.xiaofangfang.lifetatuor.net.requestModel.JockParamValue;
import com.xiaofangfang.lifetatuor.set.SettingStandard;
import com.xiaofangfang.lifetatuor.tools.GsonParseData;
import com.xiaofangfang.lifetatuor.tools.Looger;
import com.xiaofangfang.lifetatuor.tools.UiThread;
import com.xiaofangfang.lifetatuor.view.adapter.ImgListViewAdapter;

import java.util.ArrayList;

/**
 * 热门趣图的碎片显示
 * 在这里我们制定一个刷新的策略,对于向下面我们刷新的是
 * page的数量,对于像上面刷新我们刷新的是时间挫
 */
public class HotImgFragment extends ParentFragment implements AbsListView.OnScrollListener {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        JockParamValue jpv = new JockParamValue(
                SettingStandard.Joke.JokeParam.pageDefault,
                SettingStandard.Joke.JokeParam.rowDefault,
                SettingStandard.Joke.JokeParam.sortDefault,
                SettingStandard.Joke.JokeParam.timeDaymic
        );
        checkUpdate(ImgByTime.class.getSimpleName(), jpv);
    }


    private ImgByTime imgByTime;

    private boolean executeUpdate = true;
    private boolean isReflushBUtton = false;

    @Override
    protected void progressResult(String responseData) {
        super.progressResult(responseData);
        response = responseData;
        //这个平台的数据出现了问题
        //使用本地数据源

        imgByTime = GsonParseData.parserJoke_img(resData);
        imgByTime = new ImgByTime(imgByTime);
        onstartRunUi();
    }

    static String resData="{\"result\":[{\"content\":\"笑得我停不下来哈哈哈哈哈哈哈哈哈\",\"hashId\":\"61132d66b74d2255f51f3e907d009063\",\"unixtime\":\"1418904731\",\"updatetime\":\"2014-12-18 20:12:11\",\"url\":\"http://api.avatardata.cn/Joke/Img?file=ecdec155dda44634a891f44e42cc2070.gif\"},{\"content\":\"IT牛人设计的风扇型电子钟，时钟电扇二合\",\"hashId\":\"408437edbc3217180aac950b8dc97031\",\"unixtime\":\"1418904731\",\"updatetime\":\"2014-12-18 20:12:11\",\"url\":\"http://api.avatardata.cn/Joke/Img?file=81b4a1f1f1cd43fb923468249a00152f.gif\"},{\"content\":\"男、女生身高的不同评价是这样的\",\"hashId\":\"677e95b9a6307572f45cce88111d68a2\",\"unixtime\":\"1418904731\",\"updatetime\":\"2014-12-18 20:12:11\",\"url\":\"http://api.avatardata.cn/Joke/Img?file=c9d5cc0057594d98a3cf09a45405069a.jpg\"},{\"content\":\"被汪星人的机智森森的给打败了\",\"hashId\":\"b8ab92e43ac8da74b1709831b0084dc7\",\"unixtime\":\"1418904731\",\"updatetime\":\"2014-12-18 20:12:11\",\"url\":\"http://api.avatardata.cn/Joke/Img?file=87f35ea38bf64fb38c7c120fb04e12a7.gif\"},{\"content\":\"这就是传说中的吊炸天吧。。\",\"hashId\":\"ebce1e713c7dbb4e010781709948ffdb\",\"unixtime\":\"1418904731\",\"updatetime\":\"2014-12-18 20:12:11\",\"url\":\"http://api.avatardata.cn/Joke/Img?file=f28df94abc5e4d27832a427efb1ad597.gif\"},{\"content\":\"你给老子过来！咬死你个王八蛋！\",\"hashId\":\"75e450ec12566dd3b61cd8c5f6880e25\",\"unixtime\":\"1418904731\",\"updatetime\":\"2014-12-18 20:12:11\",\"url\":\"http://api.avatardata.cn/Joke/Img?file=25d174ff0af64248abcaa1df7db9d075.gif\"},{\"content\":\"调戏逗逼版主第十季：提问～回答！\",\"hashId\":\"d47e559f3b16a8baae96ead8d9915042\",\"unixtime\":\"1418904731\",\"updatetime\":\"2014-12-18 20:12:11\",\"url\":\"http://api.avatardata.cn/Joke/Img?file=9144faa5e8cb42b0bc5b2c3689345fab.jpg\"},{\"content\":\"直升机这样停，驾驶员压力大不？\",\"hashId\":\"aace4b2e152c6221a172d58108b4345d\",\"unixtime\":\"1418904731\",\"updatetime\":\"2014-12-18 20:12:11\",\"url\":\"http://api.avatardata.cn/Joke/Img?file=399eacfd66b7405baf46b05e2016db43.jpg\"},{\"content\":\"刹车失效了\",\"hashId\":\"2ac1f45b37ce81e93530ae44839dee37\",\"unixtime\":\"1418904731\",\"updatetime\":\"2014-12-18 20:12:11\",\"url\":\"http://api.avatardata.cn/Joke/Img?file=c4f74c9b1b3d428187c901e7d824ef8b.gif\"},{\"content\":\"关东版的校花来了……\",\"hashId\":\"0ab38d20c5997b4942896e2debb99cbc\",\"unixtime\":\"1418904731\",\"updatetime\":\"2014-12-18 20:12:11\",\"url\":\"http://api.avatardata.cn/Joke/Img?file=b8269406fda64cbb984bb1c06edf8e8d.jpg\"}],\"error_code\":0,\"reason\":\"Succes\"}";

    private void onstartRunUi() {
        if (imgListViewAdapter == null) {//如果是第一次加载,
            // 那么按照没有加载完成,这里一定是本地
            //加载
            imgListViewAdapter = new ImgListViewAdapter(
                    getContext(), imgByTime.getResult());
            UiThread.getUiThread(getContext()).post(new Runnable() {
                @Override
                public void run() {

                    hotImageListView.setAdapter(imgListViewAdapter);
                }
            });
        } else if (imgListViewAdapter != null) {//一定是网络加载,这是后我们就不应该设置
            // 了,而是通过适配器将数据添加进去

            //是不是通过上拉按钮进行刷新的
            UiThread.getUiThread(getContext()).post(new Runnable() {
                @Override
                public void run() {
                    if (isReflushBUtton) {  //代表的是使用的通过上拉按钮进行刷新的
                        srf.setRefreshing(false);
                        imgListViewAdapter.removeListResultImgs();
                        imgListViewAdapter.addListResultImgs(imgByTime.getResult());
                        isReflushBUtton = false;
                    } else { //这里代表的是通过下拉进行刷新的
                        imgListViewAdapter.addListResultImgs(imgByTime.getResult());
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
                inflater.inflate(R.layout.hotimg, container, false);

        initView(viewGroup);

        return viewGroup;
    }


    ListView hotImageListView;
    ImgListViewAdapter imgListViewAdapter;
    SwipeRefreshLayout srf;

    /**
     * 初始化里面的子view
     *
     * @param viewGroup
     */
    private void initView(ViewGroup viewGroup) {
        hotImageListView = viewGroup.findViewById(
                R.id.hotImgListview);
        if (imgByTime != null && imgByTime.getResult() != null) {
            imgListViewAdapter = new ImgListViewAdapter(
                    getActivity(), imgByTime.getResult());
        } else {
            imgListViewAdapter = new ImgListViewAdapter(
                    getActivity(), new ArrayList<ResultImg>());
        }

        hotImageListView.setAdapter(imgListViewAdapter);
        hotImageListView.setOnScrollListener(this);

        srf = viewGroup.findViewById(R.id.swip_refresh_hotimg);
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
                requestData(ImgByTime.class.getSimpleName(), jpv);
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        DbOpener.saveInfo(ImgByTime.class, getContext(), response);

        //保存当前的

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

        View view1 = imgListViewAdapter.getView(totalItemCount - 1, null, null);

        if (firstVisibleItem + visibleItemCount == totalItemCount
                && totalItemCount > 1) {  //滚动到最后一行
            //这里我们设置的最小值是1,问不是0,原因是我们在添加视图的时候,会清空
            //当前的显示,这时候该函数回调,我们这时候其实是在进行上刷操作,所有对下
            //下面的操作需要屏蔽掉
            //在这里我们执行buttonImage的动画显示.然后继续加载属兔添加到LIstView中
            view1.setVisibility(View.VISIBLE);
            executeButtonLoadAnimation(view1);
        } else {//也就是我们没有拉倒最下面的时候,我们设置ProgressBar的可见性为INVISIBLE
            //获得当前的最后一位
            view1.setVisibility(View.INVISIBLE);
        }
    }

    private void executeButtonLoadAnimation(View view) {
        //我们在执行上刷新时,不能再次进行下拉刷新
        if (executeUpdate) {
            executeUpdate = false;
            Looger.d("最后一个view的名称是" + view.getClass().getSimpleName());
            ViewGroup viewGroup = (ViewGroup) view;
            ProgressBar pb = viewGroup.findViewById(R.id.loadDataProgressbar);
            //下面开始加载刷新视图,每次刷新加1
            int page = Integer.parseInt(SettingStandard.Joke.JokeParam.pageDefault) + 1;


            JockParamValue jpv = new JockParamValue(
                    page + "",
                    SettingStandard.Joke.JokeParam.rowDefault,
                    SettingStandard.Joke.JokeParam.sortDefault,
                    SettingStandard.Joke.JokeParam.timeDaymic
            );
            requestData(ImgByTime.class.getSimpleName(), jpv);
        }
    }
}
