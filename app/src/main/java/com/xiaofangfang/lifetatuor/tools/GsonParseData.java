package com.xiaofangfang.lifetatuor.tools;

import com.google.gson.Gson;
import com.xiaofangfang.lifetatuor.model.joke.ImgByTime;
import com.xiaofangfang.lifetatuor.model.joke.parent.JokeAndImg;
import com.xiaofangfang.lifetatuor.model.news.parent.CommonNews;
import com.xiaofangfang.lifetatuor.model.weather.Root;

/**
 * gson的解析类,提供了对返回的gson数据的解析
 */
public class GsonParseData {


    /**
     * 解析天气信息
     *
     * @param response
     * @return
     */
    public static Root parseWeatherInfo(String response) {
        Gson gson = new Gson();
        Root root = gson.fromJson(response, Root.class);
        return root;
    }

    /**
     * 解析新闻的相关信息
     * 我们发现在返回的数据中,不管是top 社会等所有的数据都是相同的
     * 我们封装成父类,然后实现多态
     *
     * @param response
     * @param commonNews 请传递CommonNews的具体的实现类,我们在里面进行数据的填充,然后进行返回
     * @return
     */
    public static CommonNews parseNews(String response,
                                       CommonNews commonNews) {
        String responses = response;
        //下面是一个复杂的工程
        Gson gson = new Gson();
        CommonNews commonNews1 = null;
        try {

            commonNews1 = gson.fromJson(responses, CommonNews.class);
        } catch (Exception e) {
            e.printStackTrace();
            Looger.d("json解析出现错误");
        }
        return commonNews1;
    }


    /**
     * 解析获得动态笑话图片的信息
     *
     * @param response
     * @return
     */
    public static ImgByTime parserJoke_img(String response) {

        String response1 = response;
        Gson gson = new Gson();
        ImgByTime imgByTime =
                gson.fromJson(response1, ImgByTime.class);
        return imgByTime;
    }

    /**
     * 获得笑话的信息 该类获得的是笑话的顶级父类,不能直接进行转换
     * 需要调用响应子类进行包装数据
     *
     * @param response
     * @return
     */
    public static JokeAndImg parsetJoke_talk(String response) {
        String response1 = response;
        Gson gson = new Gson();
        JokeAndImg jokeAndImg = gson.fromJson(response1, JokeAndImg.class);
        return jokeAndImg;
    }


}
