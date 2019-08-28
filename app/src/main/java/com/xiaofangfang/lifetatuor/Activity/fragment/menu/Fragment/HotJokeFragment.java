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
import com.xiaofangfang.lifetatuor.model.joke.JokeByTime;
import com.xiaofangfang.lifetatuor.model.joke.parent.JokeAndImg;
import com.xiaofangfang.lifetatuor.model.joke.parent.Result;
import com.xiaofangfang.lifetatuor.net.requestModel.JockParamValue;
import com.xiaofangfang.lifetatuor.set.SettingStandard;
import com.xiaofangfang.lifetatuor.tools.GsonParseData;
import com.xiaofangfang.lifetatuor.tools.Looger;
import com.xiaofangfang.lifetatuor.tools.UiThread;
import com.xiaofangfang.lifetatuor.view.adapter.JokeListViewAdapter;

import java.util.ArrayList;

public class HotJokeFragment extends ParentFragment implements AbsListView.OnScrollListener {


    private boolean isReflushBUtton = false;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /**
         * 在加载参数这一块,我们默认使用的是系统默认的参数,但是自己也可以
         * 设置自己的参数,实现功能的细分,这一部分的功能将在设置
         * 里面进行添加
         */
        JockParamValue jpv = new JockParamValue(
                SettingStandard.Joke.JokeParam.pageDefault,
                SettingStandard.Joke.JokeParam.rowDefault,
                SettingStandard.Joke.JokeParam.sortDefault,
                //这里我们使用当前的事件挫
                SettingStandard.Joke.JokeParam.timeDaymic
        );

        checkUpdate(JokeByTime.class.getSimpleName(), jpv);
    }

    private JokeByTime jokeByTime;

    @Override
    protected void progressResult(String responseData) {
        super.progressResult(responseData);
        response = responseData;
        JokeAndImg jokeAndImg = GsonParseData.parsetJoke_talk(resData);
        jokeByTime = new JokeByTime(jokeAndImg);
        onstartRunUi();
        Looger.d("开始运行ui的相关");
    }

    static String resData = "{\"result\":[{\"content\":\"丈夫躺在床上，他得了感冒。亲爱的，如果我死了，你会因想念我而感到有点忧郁吗？那当然，亲爱的，因为你是知道的，任何微不足道的小事我都要哭一场的。\",\"hashId\":\"DF57F98C7A8E1D0C339C49257FB8C23B\",\"unixtime\":\"1418745238\",\"updatetime\":\"2014-12-16 23:53:58\"},{\"content\":\"老公文件忘拿了叫我送去他单位，我打的到他单位想打电话叫他下来拿。一找手机忘带了，那就给送上去吧，文件也忘拿了，回家从拿，钥匙也没带！只有叫老公把我送回去他自己拿文件！\",\"hashId\":\"D9C605E103CDC7FC4F061E090FF1177E\",\"unixtime\":\"1418745238\",\"updatetime\":\"2014-12-16 23:53:58\"},{\"content\":\"某女要去法国出差两周，临行前问丈夫：需要带什么礼物回来呀。丈夫说：法国女孩儿！！等回来的时候某女告诉丈夫：我已经尽力了，但是至于是男孩还是女孩，要等几个月才能知道！\",\"hashId\":\"A85FA078615C098304615BDD8388A220\",\"unixtime\":\"1418745238\",\"updatetime\":\"2014-12-16 23:53:58\"},{\"content\":\"“亲爱的，昨晚我做噩梦了。”“什么梦啊？”“我梦到你被绑架了！”“好感动啊，做梦都梦见我，然后呢，发生什么了？”“绑架居然把你送回来了！！！”\",\"hashId\":\"1E72B96D229F4DC4506AE2A53B45CCC4\",\"unixtime\":\"1418745238\",\"updatetime\":\"2014-12-16 23:53:58\"},{\"content\":\"老公：老婆今天咱们结婚有什么想说的么。老婆：我好庆幸当初选择了你陪我一生。老公：哎今天大喜的日子就别提不开心的事了！…\",\"hashId\":\"F25A81440B957C55B66A0636E67E253F\",\"unixtime\":\"1418745238\",\"updatetime\":\"2014-12-16 23:53:58\"},{\"content\":\"中午老公仔细地盯着我看了半天，说道：“不做家务的女人永远不可能美丽。”晚上他回来晚了，没办法，我只好炒菜。老公又仔细地看我半天，说道：“做家务的女人永远都是美丽的，但是你例外。”然后我就罢工。\",\"hashId\":\"0DF7B0DE12C6528D896B158CF2F53930\",\"unixtime\":\"1418745238\",\"updatetime\":\"2014-12-16 23:53:58\"},{\"content\":\"夏天到了，问老公，现在觉得我老不老？老公说感觉比以前小了，我问为什么呢，老公说刚认识你的时候觉得你很深沉，现在有点幼稚了，通俗点说就是刚认识那会很装逼，现在很二逼。。。\",\"hashId\":\"D41AE95F3E12F8400AF5DDD74E815D50\",\"unixtime\":\"1418745238\",\"updatetime\":\"2014-12-16 23:53:58\"},{\"content\":\"那天在公交车上，老公站我身后。由于之前两个人在冷战中，我越想越来气，回头看了他一眼……突然大叫，你摸我屁股干嘛，变态啊你！然后我老公在全车人鄙视的眼神中下车了，把我丢下了。。。\",\"hashId\":\"C29663312C0E9DC037FD104F80D1DD7E\",\"unixtime\":\"1418745238\",\"updatetime\":\"2014-12-16 23:53:58\"},{\"content\":\"吃完晚饭，我和老婆都懒得洗碗，我提议：“要不咱们猜拳吧？输的人洗！”她摇摇头娇羞地说：“才不要呢，人家是淑女来着，猜拳这么粗鲁！”我想了想又提议：“那咱们猜硬币吧！”说罢，我从口袋里掏出一枚硬币。突然她怒道：“你竟然敢藏私房钱！！！罚你洗碗三天。”\",\"hashId\":\"1F820843DA039E144AFEB2F892AEDF4B\",\"unixtime\":\"1418745238\",\"updatetime\":\"2014-12-16 23:53:58\"},{\"content\":\"门口街上路过一结婚车队！一家三口同时哇塞！却各有各看法！媳妇说：“看人家这车队，真高档，真气派！”。四岁女儿说：“气球好漂亮呀”。我说：“新娘真美”！结果媳妇一脸黑线，怒曰：“滚回去做饭去！！！”\",\"hashId\":\"32720510643A161931AA51FF23D5EF38\",\"unixtime\":\"1418745238\",\"updatetime\":\"2014-12-16 23:53:58\"}],\"error_code\":0,\"reason\":\"Succes\"}";


    private void onstartRunUi() {
        if (hotJokeListViewAdapter == null) {//如果是第一次加载,
            // 那么按照没有加载完成,这里一定是本地
            //加载
            hotJokeListViewAdapter = new JokeListViewAdapter(
                    getContext(), jokeByTime.getResult());
            UiThread.getUiThread(getContext()).post(new Runnable() {
                @Override
                public void run() {

                    hotJokeListView.setAdapter(hotJokeListViewAdapter);
                }
            });
        } else if (hotJokeListViewAdapter != null) {//一定是网络加载,这是后我们就不应该设置
            // 了,而是通过适配器将数据添加进去
            UiThread.getUiThread(getContext()).post(new Runnable() {
                @Override
                public void run() {
                    if (isReflushBUtton) {  //代表的是使用的通过上拉按钮进行刷新的
                        srf.setRefreshing(false);
                        hotJokeListViewAdapter.removeListResult();
                        hotJokeListViewAdapter.addListResult(jokeByTime.getResult());
                        isReflushBUtton = false;
                    } else { //这里代表的是通过下拉进行刷新的
                        hotJokeListViewAdapter.addListResult(jokeByTime.getResult());
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
                inflater.inflate(R.layout.hotjoke, container, false);

        initView(viewGroup);

        return viewGroup;
    }

    ListView hotJokeListView;
    JokeListViewAdapter hotJokeListViewAdapter;
    private boolean executeUpdate = true;
    SwipeRefreshLayout srf;

    /**
     * 初始化里面的子view
     *
     * @param viewGroup
     */
    private void initView(ViewGroup viewGroup) {
        hotJokeListView = viewGroup.findViewById(
                R.id.hotJokeListView);
        if (jokeByTime != null && jokeByTime.getResult() != null) {
            hotJokeListViewAdapter = new JokeListViewAdapter(
                    getActivity(), jokeByTime.getResult());
        } else {
            hotJokeListViewAdapter = new JokeListViewAdapter(
                    getActivity(), new ArrayList<Result>());
        }
        hotJokeListView.setAdapter(hotJokeListViewAdapter);
        hotJokeListView.setOnScrollListener(this);

        srf = viewGroup.findViewById(R.id.swip_refresh_hotjoke);
        srf.setColorSchemeResources(R.color.menu_tab_indicator_color);
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
                requestData(JokeByTime.class.getSimpleName(), jpv);
            }
        });
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        DbOpener.saveInfo(JokeByTime.class, getContext(), response);
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

        View view1 = hotJokeListViewAdapter.getView(totalItemCount - 1, null, null);

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


            JockParamValue jpv = new JockParamValue(
                    page + "",
                    SettingStandard.Joke.JokeParam.rowDefault,
                    SettingStandard.Joke.JokeParam.sortDefault,
                    SettingStandard.Joke.JokeParam.timeDaymic
            );
            requestData(JokeByTime.class.getSimpleName(), jpv);
        }
    }
}
