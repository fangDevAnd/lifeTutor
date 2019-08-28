package com.xiaofangfang.lifetatuor.Activity.fragment.menu.Fragment.parent;

import androidx.annotation.Nullable;
import android.widget.Toast;

import com.xiaofangfang.lifetatuor.dao.DbOpener;
import com.xiaofangfang.lifetatuor.net.JockRequest;
import com.xiaofangfang.lifetatuor.net.requestModel.JockParamValue;
import com.xiaofangfang.lifetatuor.tools.Looger;
import com.xiaofangfang.lifetatuor.tools.ThreadSingle;

import java.io.IOException;

import androidx.fragment.app.Fragment;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;



/**
 * 这个类是joke的父类,实现的功能是对父类的部分功能的封装
 */
public class ParentFragment extends Fragment implements Callback {


    protected String response;

    /**
     * 这个方法是一个空实现,子类对其进行继承,实现自己的封装方法
     *
     * @param responseData
     */
    protected void progressResult(String responseData) {
        Looger.d("调用的是父类的方法");
    }


    /**
     * 检查我们是否需要进行网络的链接进行更新数据
     * 该方法运行在子线程中
     */
    protected void checkUpdate(String className,
                               JockParamValue jokeParam) {

        final String response = DbOpener.readInfo(getContext(), className);
        if (response != null && (!"".equals(response))) {
            //在这里我们执行子线程操作
            ThreadSingle.getSingleThread().execute(new Runnable() {
                @Override
                public void run() {
                    // 如果存在数据就去取出数据
                    Looger.d("存在当前的数据" + response);
                    progressResult(response);
                }
            });
        } else {
            Looger.d("不存在文件,我们开始进行网络请求");
            requestData(className, jokeParam);
            //这里使用本地数据来测试,减少网络访问
            // progressResult(data);

        }

    }

    /**
     * 类型是请求的数返回的类性的类的名称
     * 这个type可一不用传
     * 根据不同的请求数据的类型我们发送不同的请求
     */
    protected void requestData(String className, JockParamValue jokeParam) {
        switch (className) {
            case "ImgByTime":
                JockRequest.queryImageByTime(jokeParam, this);
                break;
            case "JokeByTime":
                JockRequest.queryJockWithTime(jokeParam, this);
                break;
            case "NewstJoke":
                JockRequest.queryNowJock(jokeParam, this);
                break;
            case "NewstImg":
                JockRequest.queryNewImage(jokeParam, this);
                break;
        }
    }


    /**
     * 请求的回调,请求成功后将数据回调到rogressResult里面,通过
     * 子类覆写这个方法得到复杂的数据
     *
     * @param call
     * @param e
     */
    @Override
    public void onFailure(Call call, IOException e) {
        Toast.makeText(getContext(), "请求信息失败,请稍后再试",
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {
        String responseData = response.body().string();
        //将数据存放到全局
        Looger.d("请求的数据" + responseData);
        progressResult(responseData);
    }


    static String data = "{\"result\":[{\"content\":\"丈夫躺在床上，他得了感冒。亲爱的，如果我死了，你会因想念我而感到有点忧郁吗？那当然，亲爱的，因为你是知道的，任何微不足道的小事我都要哭一场的。\",\"hashId\":\"DF57F98C7A8E1D0C339C49257FB8C23B\",\"unixtime\":\"1418745238\",\"updatetime\":\"2014-12-16 23:53:58\"},{\"content\":\"老公文件忘拿了叫我送去他单位，我打的到他单位想打电话叫他下来拿。一找手机忘带了，那就给送上去吧，文件也忘拿了，回家从拿，钥匙也没带！只有叫老公把我送回去他自己拿文件！\",\"hashId\":\"D9C605E103CDC7FC4F061E090FF1177E\",\"unixtime\":\"1418745238\",\"updatetime\":\"2014-12-16 23:53:58\"},{\"content\":\"某女要去法国出差两周，临行前问丈夫：需要带什么礼物回来呀。丈夫说：法国女孩儿！！等回来的时候某女告诉丈夫：我已经尽力了，但是至于是男孩还是女孩，要等几个月才能知道！\",\"hashId\":\"A85FA078615C098304615BDD8388A220\",\"unixtime\":\"1418745238\",\"updatetime\":\"2014-12-16 23:53:58\"},{\"content\":\"“亲爱的，昨晚我做噩梦了。”“什么梦啊？”“我梦到你被绑架了！”“好感动啊，做梦都梦见我，然后呢，发生什么了？”“绑架居然把你送回来了！！！”\",\"hashId\":\"1E72B96D229F4DC4506AE2A53B45CCC4\",\"unixtime\":\"1418745238\",\"updatetime\":\"2014-12-16 23:53:58\"},{\"content\":\"老公：老婆今天咱们结婚有什么想说的么。老婆：我好庆幸当初选择了你陪我一生。老公：哎今天大喜的日子就别提不开心的事了！…\",\"hashId\":\"F25A81440B957C55B66A0636E67E253F\",\"unixtime\":\"1418745238\",\"updatetime\":\"2014-12-16 23:53:58\"},{\"content\":\"中午老公仔细地盯着我看了半天，说道：“不做家务的女人永远不可能美丽。”晚上他回来晚了，没办法，我只好炒菜。老公又仔细地看我半天，说道：“做家务的女人永远都是美丽的，但是你例外。”然后我就罢工。\",\"hashId\":\"0DF7B0DE12C6528D896B158CF2F53930\",\"unixtime\":\"1418745238\",\"updatetime\":\"2014-12-16 23:53:58\"},{\"content\":\"夏天到了，问老公，现在觉得我老不老？老公说感觉比以前小了，我问为什么呢，老公说刚认识你的时候觉得你很深沉，现在有点幼稚了，通俗点说就是刚认识那会很装逼，现在很二逼。。。\",\"hashId\":\"D41AE95F3E12F8400AF5DDD74E815D50\",\"unixtime\":\"1418745238\",\"updatetime\":\"2014-12-16 23:53:58\"},{\"content\":\"那天在公交车上，老公站我身后。由于之前两个人在冷战中，我越想越来气，回头看了他一眼……突然大叫，你摸我屁股干嘛，变态啊你！然后我老公在全车人鄙视的眼神中下车了，把我丢下了。。。\",\"hashId\":\"C29663312C0E9DC037FD104F80D1DD7E\",\"unixtime\":\"1418745238\",\"updatetime\":\"2014-12-16 23:53:58\"},{\"content\":\"吃完晚饭，我和老婆都懒得洗碗，我提议：“要不咱们猜拳吧？输的人洗！”她摇摇头娇羞地说：“才不要呢，人家是淑女来着，猜拳这么粗鲁！”我想了想又提议：“那咱们猜硬币吧！”说罢，我从口袋里掏出一枚硬币。突然她怒道：“你竟然敢藏私房钱！！！罚你洗碗三天。”\",\"hashId\":\"1F820843DA039E144AFEB2F892AEDF4B\",\"unixtime\":\"1418745238\",\"updatetime\":\"2014-12-16 23:53:58\"},{\"content\":\"门口街上路过一结婚车队！一家三口同时哇塞！却各有各看法！媳妇说：“看人家这车队，真高档，真气派！”。四岁女儿说：“气球好漂亮呀”。我说：“新娘真美”！结果媳妇一脸黑线，怒曰：“滚回去做饭去！！！”\",\"hashId\":\"32720510643A161931AA51FF23D5EF38\",\"unixtime\":\"1418745238\",\"updatetime\":\"2014-12-16 23:53:58\"}],\"error_code\":0,\"reason\":\"Succes\"}";

}
