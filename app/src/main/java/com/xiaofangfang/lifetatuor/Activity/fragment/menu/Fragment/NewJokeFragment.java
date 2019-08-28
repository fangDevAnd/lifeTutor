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
import com.xiaofangfang.lifetatuor.model.joke.NewstJoke;
import com.xiaofangfang.lifetatuor.model.joke.parent.JokeAndImg;
import com.xiaofangfang.lifetatuor.model.joke.parent.Result;
import com.xiaofangfang.lifetatuor.net.requestModel.JockParamValue;
import com.xiaofangfang.lifetatuor.set.SettingStandard;
import com.xiaofangfang.lifetatuor.tools.GsonParseData;
import com.xiaofangfang.lifetatuor.tools.Looger;
import com.xiaofangfang.lifetatuor.tools.UiThread;
import com.xiaofangfang.lifetatuor.view.adapter.JokeListViewAdapter;

import java.util.ArrayList;

public class NewJokeFragment extends ParentFragment implements AbsListView.OnScrollListener {


    private boolean executeUpdate = true;
    private SwipeRefreshLayout srf;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        JockParamValue jpv = new JockParamValue(
                "",
                SettingStandard.Joke.JokeParam.rowDefault,
                SettingStandard.Joke.JokeParam.sortDefault,
                ""
        );

        checkUpdate(NewstJoke.class.getSimpleName(), jpv);
    }


    private NewstJoke newstJoke;

    @Override
    protected void progressResult(String responseData) {
        super.progressResult(responseData);
        response = responseData;

        JokeAndImg jokeAndImg = GsonParseData.parsetJoke_talk(resData);
        newstJoke = new NewstJoke(jokeAndImg);
        onstartRunUi();
    }

    static String resData = "{\"result\":[{\"content\":\"假如给你五个愿望，你最想做什么呢？一、我希望父母身体健康，长命百岁。二、我希望孩子无忧无虑，健康成长。三、我希望爱人心想事成，事事顺利。四、我希望世界永久和平，国家昌盛。五个愿望，你已经用了四个，不想给自己留一个吗？呃……好吧，五、我希望你能履行诺言，愿我所希望的都能实现！\",\"hashId\":\"68b5d151d4ada31c77011e8a29639ca7\",\"unixtime\":\"1540774502\",\"updatetime\":\"2018-10-29 08:55:02\"},{\"content\":\"同事跟我说，前天晚上跟几个朋友喝酒，五个人都喝断片了。。。第二天起来家里就剩他一个人，老婆都不知道哪去了，电话打不通，信息也不回，直到第二天下午才回来，问她干嘛去了，支支吾吾的搪塞过去了。这几天同事心里很烦，每天都是上半天班就回家了。我总感觉哪里不对。\",\"hashId\":\"df88e92b718eab22ffc15873a4bb643c\",\"unixtime\":\"1540774502\",\"updatetime\":\"2018-10-29 08:55:02\"},{\"content\":\"四小伙在一起吃饭，结束的时候一小伙说：“咱们谁长的丑，谁结账呗！” 这时一服务员转头问：“是要AA制吗？\",\"hashId\":\"ff9f5eeeebfd2ebe0aaedab8d49081db\",\"unixtime\":\"1540609202\",\"updatetime\":\"2018-10-27 11:00:02\"},{\"content\":\"对我现在的女朋友来说，我就像是上帝。我一直看着她，但她却不知道我的存在。\",\"hashId\":\"8a46be55d0a071f77dacff1c040c72d9\",\"unixtime\":\"1540608902\",\"updatetime\":\"2018-10-27 10:55:02\"},{\"content\":\"儿子冒傻气:我辈 分怎么这么小啊！  我:你跟谁比辈 分小了？  儿子:跟你比辈 小，按辈分我是不是得管你叫叔叔？\",\"hashId\":\"17996ed988a455e46c0d6780cf3af702\",\"unixtime\":\"1540608901\",\"updatetime\":\"2018-10-27 10:55:01\"},{\"content\":\"当年军训完 我是全校最白的一个 ，咋晒都不黑 。  后来典礼上就让我去举牌子了 ，教导主任看看我 ，又看看其他人  骂了一句 :你特么背景挺硬啊 军训都不参加\",\"hashId\":\"57800594afd9108c8303e24b5385c799\",\"unixtime\":\"1540608901\",\"updatetime\":\"2018-10-27 10:55:01\"},{\"content\":\"有一位美女好友新交了一个男朋友，很帅，就是经济条件很一般。我们身边朋友都很不解，觉得以朋友的条件可以找一个更好的男朋友。    问起朋友选择这位男生的原因，她只是淡淡地说：虽然他没什么钱，但是能力强！我就想不通了，能力强怎么会混得不好。\",\"hashId\":\"b2bac2c54282d2185c5b705f4c72e282\",\"unixtime\":\"1540608901\",\"updatetime\":\"2018-10-27 10:55:01\"},{\"content\":\"和几个同事聊掉头发的问题，我说我每天早上梳头都掉一把头发。小丽说:我现在都能抓掉一小把。同事珊珊说:我也是，能抓掉很多。其他同事也纷纷附和，说着说着就互相抓一抓对方的头发，一边的秃顶老张惊恐的退后好几米看着我们说:你们敢动我一根头发试试？\",\"hashId\":\"d17949bc7bc37fdda5ce8b9980d90ab4\",\"unixtime\":\"1540608901\",\"updatetime\":\"2018-10-27 10:55:01\"},{\"content\":\"同学聚会，别人都是带着家属来的，班上的老大哥还是独身一人，有人调侃说“哥啊，别人都脱单了，你这是等啥呢？”老大哥憨笑一声，摸了摸 头顶说:“别人脱单我脱发\",\"hashId\":\"86c5dc74cca56ec1872e9159790676a0\",\"unixtime\":\"1540608901\",\"updatetime\":\"2018-10-27 10:55:01\"},{\"content\":\"从小我就是个吃货。有次妈妈带我出去逛街，遇见熟悉的阿姨，阿姨说给孩子买支冰棍，妈妈就各种推脱，各种不要，我就激情澎湃的大声吼道:“快买啊。。。快去买啊。。。再不买就走了啊啊啊啊啊。。”哈哈，我的翘臀就来自于妈妈这顿暴打\",\"hashId\":\"49e4987c5e876859a71e40b44a80fe5d\",\"unixtime\":\"1540608901\",\"updatetime\":\"2018-10-27 10:55:01\"}],\"error_code\":0,\"reason\":\"Succes\"}";

    private void onstartRunUi() {
        if (newJokeListViewAdapter == null) {//如果是第一次加载,
            // 那么按照没有加载完成,这里一定是本地
            //加载
            newJokeListViewAdapter = new JokeListViewAdapter(
                    getContext(), newstJoke.getResult());
            UiThread.getUiThread(getContext()).post(new Runnable() {
                @Override
                public void run() {

                    newJokeListView.setAdapter(newJokeListViewAdapter);
                }
            });
        } else if (newJokeListViewAdapter != null) {//一定是网络加载,这是后我们就不应该设置
            // 了,而是通过适配器将数据添加进去
            UiThread.getUiThread(getContext()).post(new Runnable() {
                @Override
                public void run() {
                    if (isReflushBUtton) {  //代表的是使用的通过上拉按钮进行刷新的
                        srf.setRefreshing(false);
                        newJokeListViewAdapter.removeListResult();
                        newJokeListViewAdapter.addListResult(newstJoke.getResult());
                        isReflushBUtton = false;
                    } else { //这里代表的是通过下拉进行刷新的
                        newJokeListViewAdapter.addListResult(newstJoke.getResult());
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
                inflater.inflate(R.layout.newjoke, container, false);

        initView(viewGroup);

        return viewGroup;
    }

    ListView newJokeListView;
    JokeListViewAdapter newJokeListViewAdapter;
    private boolean isReflushBUtton = false;

    /**
     * 初始化里面的子view
     *
     * @param viewGroup
     */
    private void initView(ViewGroup viewGroup) {
        newJokeListView = viewGroup.findViewById(
                R.id.newJokeListview);
        if (newstJoke != null && newstJoke.getResult() != null) {
            newJokeListViewAdapter = new JokeListViewAdapter(
                    getActivity(), newstJoke.getResult());
        } else {
            newJokeListViewAdapter = new JokeListViewAdapter(
                    getActivity(), new ArrayList<Result>());
        }
        newJokeListView.setAdapter(newJokeListViewAdapter);
        newJokeListView.setOnScrollListener(this);


        srf = viewGroup.findViewById(R.id.swip_refresh_newjoke);
        srf.setColorSchemeResources(R.color.menu_tab_indicator_color);
        srf.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                isReflushBUtton = true;
                //在这里执行刷新
                JockParamValue jpv = new JockParamValue(
                        SettingStandard.Joke.JokeParam.pageDefault,
                        SettingStandard.Joke.JokeParam.rowDefault,
                        SettingStandard.Joke.JokeParam.sortDefault2,
                        SettingStandard.Joke.JokeParam.timeDaymic
                );
                requestData(NewstJoke.class.getSimpleName(), jpv);
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        DbOpener.saveInfo(NewstJoke.class, getContext(), response);
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

        View view1 = newJokeListViewAdapter.getView(totalItemCount - 1, null, null);

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
            int page = Integer.parseInt(SettingStandard.Joke.JokeParam.pageDefault) + 1;

            int timevalue = Integer.parseInt(SettingStandard.Joke.JokeParam.timeDefault) + 100;

            JockParamValue jpv = new JockParamValue(
                    page + "",
                    SettingStandard.Joke.JokeParam.rowDefault,
                    SettingStandard.Joke.JokeParam.sortDefault,
                    timevalue + ""
            );
            requestData(NewstJoke.class.getSimpleName(), jpv);
        }
    }
}
