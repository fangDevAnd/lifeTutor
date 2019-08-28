package com.xiaofangfang.lifetatuor.Activity.fragment.weather.Fragment;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.xiaofangfang.lifetatuor.R;
import com.xiaofangfang.lifetatuor.dao.DbOpener;
import com.xiaofangfang.lifetatuor.model.LocationInfo;
import com.xiaofangfang.lifetatuor.model.weather.Air;
import com.xiaofangfang.lifetatuor.model.weather.Aqi;
import com.xiaofangfang.lifetatuor.model.weather.Astro;
import com.xiaofangfang.lifetatuor.model.weather.Basic;
import com.xiaofangfang.lifetatuor.model.weather.City;
import com.xiaofangfang.lifetatuor.model.weather.Comf;
import com.xiaofangfang.lifetatuor.model.weather.Cw;
import com.xiaofangfang.lifetatuor.model.weather.Daily_forecast;
import com.xiaofangfang.lifetatuor.model.weather.Drsg;
import com.xiaofangfang.lifetatuor.model.weather.Flu;
import com.xiaofangfang.lifetatuor.model.weather.HeWeather5;
import com.xiaofangfang.lifetatuor.model.weather.Now;
import com.xiaofangfang.lifetatuor.model.weather.Root;
import com.xiaofangfang.lifetatuor.model.weather.Sport;
import com.xiaofangfang.lifetatuor.model.weather.Suggestion;
import com.xiaofangfang.lifetatuor.model.weather.Trav;
import com.xiaofangfang.lifetatuor.model.weather.Uv;
import com.xiaofangfang.lifetatuor.net.WeatherRequest;
import com.xiaofangfang.lifetatuor.set.SettingStandard;
import com.xiaofangfang.lifetatuor.tools.DataConvert;
import com.xiaofangfang.lifetatuor.tools.GsonParseData;
import com.xiaofangfang.lifetatuor.tools.Looger;
import com.xiaofangfang.lifetatuor.tools.ScreenTools;
import com.xiaofangfang.lifetatuor.tools.UiThread;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * 这个类是城市信息的基础类,用来构建自己的天气信息
 * 该类是基类,所有的信息都是通过该类进行显示的
 */
@RequiresApi(api = Build.VERSION_CODES.M)
public class WeatherInfoFragment extends Fragment implements Callback, View.OnClickListener {
    public static String[] weatherClass = {
            "晴", "阴", "雨", "雪", "多云"};
    public static int[] weatherClassIcon = {
            R.drawable.ic_qing,
            R.drawable.ic_yin,
            R.drawable.ic_yu,
            R.drawable.ic_xue,
            R.drawable.ic_duoyun
    };


    private LocationInfo li;
    //执行的是刷新
    private boolean isFlush;

    @SuppressLint(value = "ValidFragment")
    public WeatherInfoFragment(LocationInfo li) {
        this();
        this.li = li;
    }

    public WeatherInfoFragment() {
        super();
    }

    boolean isNet = false;
    SharedPreferences sp;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //在这里加载天气信息
        /**
         * 进行封装然后进行传递
         */
        Looger.d("位置信息得到了" + li.toString());
        sp = getContext().getSharedPreferences(SettingStandard.Weather.UPDATE_TIME, Context.MODE_PRIVATE);
        String time = sp.getString(SettingStandard.Weather.time, "");
        if ("".equals(time) ||
                (!DataConvert.obtainCurrentDate().equals(time))) {
            WeatherRequest.getWeatherInfo(li.getCity(), this);
            Looger.d("发起网络请求");
        } else if (DataConvert.obtainCurrentDate().equals(time)) {
            //已经执行过更新了
            String response =
                    DbOpener.readInfo(getContext(), Root.class.getSimpleName());
            obtainResponseData(response);
            Looger.d("直接读取文件信息数据");
        }
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.weatherinfo_fragment, container, false);
        initView(viewGroup);
        return viewGroup;
    }

    private SwipeRefreshLayout srf;

    MyRichuPageAdapter richuPageAdapter;
    //sp
    //主要信息的显示区域
    private RelativeLayout weather_maindis;
    //现在的温度
    private TextView newTemporary;
    //当前的位置　比街道大一级的定位坐标
    private TextView position;
    //定位的街道
    private TextView positionRoute;
    //空气的质量和分数　　　标准写法　　　"空气良 61"
    private TextView airQualityAndScore;
    //天气的状况
    private TextView weatherStatus;
    //日出的viewpager
    ViewPager richuPage;
    //当前界面的滚动
    ScrollView weatherInfoScroll;
    //存放三天天气信息的listView
    ListView save_three_day_weather_info;
    //  存放两排数据　，两行存放的数据都是图片　　通过点击　显示listView下面显示详情信息的ListView

    //存放指数信息的文本框
    TextView detailIndexInfo;

    //详细信息的显示区域
    Button detailInfo;
    //日出时间
    TextView richuTime;
    //日落时间
    TextView riluoTime;
    //月出时间
    TextView yueshengTime;
    //月落时间
    TextView yueluoTime;
    //湿度
    TextView hum;
    //降雨量
    TextView pcpn;
    //大气压强
    TextView pres;
    //紫外线
    TextView uv;
    // 能见度　
    TextView visible;
    // 风速　　
    TextView windSpeed;
    // 降水概率
    TextView pop;
    //天气状况
    TextView weatherStatus1;

    TextView windDir;
    TextView windSC;

    /**
     * 初始化view
     *
     * @param viewGroup
     */
    private void initView(ViewGroup viewGroup) {

        newTemporary = viewGroup.findViewById(R.id.newTemporary);
        position = viewGroup.findViewById(R.id.position);
        positionRoute = viewGroup.findViewById(R.id.positionRoute);
        airQualityAndScore = viewGroup.findViewById(R.id.airQualityAndScore);
        weatherStatus = viewGroup.findViewById(R.id.weatherStatus);
        weatherStatus1 = viewGroup.findViewById(R.id.weatherStatus1);
        //上面是主显示区的数据显示

        srf = viewGroup.findViewById(R.id.weather_swipereflush);
        srf.setColorSchemeResources(R.color.menu_tab_indicator_color);

        srf.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                //.....执行功能
                WeatherRequest.getWeatherInfo(li.getCity(),
                        WeatherInfoFragment.this);
                isFlush = true;
            }
        });
        //刷新视图

        //主显示区域
        weather_maindis = viewGroup.findViewById(R.id.weather_maindis);

        other_day_info_container = viewGroup.findViewById(R.id.other_day_info_container);

        // 滚动视图
        weatherInfoScroll = viewGroup.findViewById(R.id.weatherInfoScroll);
        //存放三天的信息的列表
        save_three_day_weather_info = viewGroup.findViewById(R.id.save_three_day_weather_info);
        //指数信息显示的数据
        //详细指数信息的显示区域
        detailIndexInfo = viewGroup.findViewById(R.id.detailIndexInfo);


        //日出时间
        richuTime = viewGroup.findViewById(R.id.richuTime);
        //日落时间
        riluoTime = viewGroup.findViewById(R.id.riluoTime);
        //月升时间
        yueshengTime = viewGroup.findViewById(R.id.yueshengTime);
        //月落时间
        yueluoTime = viewGroup.findViewById(R.id.yueluoTime);
        //湿度
        hum = viewGroup.findViewById(R.id.hum);
        //降雨量
        pcpn = viewGroup.findViewById(R.id.pcpn);
        //大气压
        pres = viewGroup.findViewById(R.id.pres);
        //紫外线
        uv = viewGroup.findViewById(R.id.uv);
        //可见性
        visible = viewGroup.findViewById(R.id.visible);
        //降水概率
        pop = viewGroup.findViewById(R.id.pop);
        //风速
        windSpeed = viewGroup.findViewById(R.id.windSpeed);

        windDir = viewGroup.findViewById(R.id.windDir);

        windSC = viewGroup.findViewById(R.id.windSC);


        initOtherDetailInfo(viewGroup);

        initIndexView(viewGroup);
    }

    Button kongqi;
    Button shushidu;
    Button xiche;
    Button chuanyi;
    Button ganmao;
    Button yundong;
    Button lvyou;
    Button ziwaixian;

    /*
    初始化Index指数信息
     */
    private void initIndexView(ViewGroup viewGroup) {
        kongqi = viewGroup.findViewById(R.id.kongqi);
        kongqi.setOnClickListener(this);
        shushidu = viewGroup.findViewById(R.id.shushidu);
        shushidu.setOnClickListener(this);
        xiche = viewGroup.findViewById(R.id.wash_car);
        xiche.setOnClickListener(this);
        chuanyi = viewGroup.findViewById(R.id.chuanyi);
        chuanyi.setOnClickListener(this);
        ganmao = viewGroup.findViewById(R.id.ganmao);
        ganmao.setOnClickListener(this);
        yundong = viewGroup.findViewById(R.id.yundong);
        yundong.setOnClickListener(this);
        lvyou = viewGroup.findViewById(R.id.lvyou);
        lvyou.setOnClickListener(this);
        ziwaixian = viewGroup.findViewById(R.id.ziwaixian);
        ziwaixian.setOnClickListener(this);

    }

    //其他的详细信息
    LinearLayout other_day_info_container;


    //下面是对显示详细信息的点击的处理数据的结果
    //今天的天气
    TextView tomorrowWeather;
    //明天的天气
    TextView afterTomorrowWeather;


    TextView otherPres;

    TextView otherHum;

    TextView otherPcpn;

    TextView otherWind;

    TextView otherWindSC;


    /**
     * 处理其他的详情显示的信息
     *
     * @param viewGroup
     */
    private void initOtherDetailInfo(ViewGroup viewGroup) {

        //查看更过多的详情信息
        detailInfo = viewGroup.findViewById(R.id.detailInfo);
        detailInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //设置其他的信息为显示
                if (other_day_info_container.getVisibility() == View.VISIBLE) {
                    other_day_info_container.setVisibility(View.GONE);
                } else {
                    other_day_info_container.setVisibility(View.VISIBLE);

                    //加载数据
                    if (daily_forecasts != null) {
                        //设置默认的加载数据是位置１
                        progressOtherDayWeatherInfo(daily_forecasts, 1);
                    }
                }
            }
        });

        tomorrowWeather = viewGroup.findViewById(R.id.tomorrowWeather);
        //明天的天气显示
        tomorrowWeather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressOtherDayWeatherInfo(daily_forecasts, 1);
            }
        });
        afterTomorrowWeather = viewGroup.findViewById(R.id.afterTomorrow);
        //后天的天气显示
        afterTomorrowWeather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressOtherDayWeatherInfo(daily_forecasts, 2);
            }
        });
        otherPres = viewGroup.findViewById(R.id.otherPres);
        otherPcpn = viewGroup.findViewById(R.id.otherPcpn);
        otherHum = viewGroup.findViewById(R.id.otherHum);
        otherWind = viewGroup.findViewById(R.id.otherWind);
        otherWindSC = viewGroup.findViewById(R.id.otherWindSC);

        //首先应该是对明天进行赋值操作


    }


    @Override
    public void onResume() {
        super.onResume();
        final LinearLayout.MarginLayoutParams ml =
                (LinearLayout.MarginLayoutParams) weather_maindis.getLayoutParams();
        int[] dimensions = ScreenTools.getScreenDimension(getContext());
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, (float) dimensions[1] / 2);
        valueAnimator.setDuration(300);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float height = (float) animation.getAnimatedValue();
                ml.height = (int) height;
                weather_maindis.requestLayout();
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onFailure(Call call, IOException e) {
        Looger.d("天气信息响应失败" + e.getMessage());
    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {
        Looger.d("天气信息响应成功");
        if (isFlush) {
            srf.setRefreshing(false);
            isFlush = false;
        }

        String responseData = response.body().string();
        //responseData = weatherInfoJson;
        Looger.d("天气信息" + responseData);
        //写入更新
        SharedPreferences sp = getContext().getSharedPreferences(
                SettingStandard.Weather.UPDATE_TIME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(SettingStandard.Weather.time,
                DataConvert.obtainCurrentDate());
        editor.commit();
        //下面是存放缓存的数据
        DbOpener.saveInfo(Root.class, getContext(), responseData);
        obtainResponseData(responseData);


    }

    static String weatherInfoJson = "{\"HeWeather5\":[{\"aqi\":{\"city\":{\"aqi\":\"192\",\"qlty\":\"中度污染\",\"pm25\":\"144\",\"pm10\":\"160\",\"no2\":\"118\",\"so2\":\"7\",\"co\":\"2.0\",\"o3\":\"16\"}},\"basic\":{\"city\":\"北京\",\"cnty\":\"中国\",\"id\":\"CN101010100\",\"lat\":\"39.90498734\",\"lon\":\"116.4052887\",\"update\":{\"loc\":\"2018-11-02 18:46\",\"utc\":\"2018-11-02 10:46\"}},\"daily_forecast\":[{\"astro\":{\"mr\":\"00:16\",\"ms\":\"14:27\",\"sr\":\"06:45\",\"ss\":\"17:09\"},\"cond\":{\"code_d\":\"100\",\"code_n\":\"100\",\"txt_d\":\"晴\",\"txt_n\":\"晴\"},\"date\":\"2018-11-02\",\"hum\":\"38\",\"pcpn\":\"0.0\",\"pop\":\"0\",\"pres\":\"1027\",\"tmp\":{\"max\":\"15\",\"min\":\"4\"},\"uv\":\"2\",\"vis\":\"10\",\"wind\":{\"deg\":\"176\",\"dir\":\"南风\",\"sc\":\"1-2\",\"spd\":\"9\"}},{\"astro\":{\"mr\":\"01:26\",\"ms\":\"15:04\",\"sr\":\"06:46\",\"ss\":\"17:08\"},\"cond\":{\"code_d\":\"101\",\"code_n\":\"101\",\"txt_d\":\"多云\",\"txt_n\":\"多云\"},\"date\":\"2018-11-03\",\"hum\":\"48\",\"pcpn\":\"0.0\",\"pop\":\"2\",\"pres\":\"1024\",\"tmp\":{\"max\":\"16\",\"min\":\"5\"},\"uv\":\"1\",\"vis\":\"20\",\"wind\":{\"deg\":\"-1\",\"dir\":\"无持续风向\",\"sc\":\"1-2\",\"spd\":\"10\"}},{\"astro\":{\"mr\":\"02:34\",\"ms\":\"15:38\",\"sr\":\"06:47\",\"ss\":\"17:07\"},\"cond\":{\"code_d\":\"305\",\"code_n\":\"101\",\"txt_d\":\"小雨\",\"txt_n\":\"多云\"},\"date\":\"2018-11-04\",\"hum\":\"53\",\"pcpn\":\"0.0\",\"pop\":\"24\",\"pres\":\"1023\",\"tmp\":{\"max\":\"12\",\"min\":\"3\"},\"uv\":\"0\",\"vis\":\"20\",\"wind\":{\"deg\":\"5\",\"dir\":\"北风\",\"sc\":\"1-2\",\"spd\":\"6\"}}],\"now\":{\"cond\":{\"code\":\"101\",\"txt\":\"多云\"},\"fl\":\"9\",\"hum\":\"75\",\"pcpn\":\"0.0\",\"pres\":\"1024\",\"tmp\":\"10\",\"vis\":\"2\",\"wind\":{\"deg\":\"26\",\"dir\":\"东北风\",\"sc\":\"1\",\"spd\":\"3\"}},\"status\":\"ok\",\"suggestion\":{\"air\":{\"brf\":\"中\",\"txt\":\"气象条件对空气污染物稀释、扩散和清除无明显影响，易感人群应适当减少室外活动时间。\"},\"comf\":{\"brf\":\"较舒适\",\"txt\":\"今天夜间天气晴好，会感觉偏凉，舒适、宜人。\"},\"cw\":{\"brf\":\"较适宜\",\"txt\":\"较适宜洗车，未来一天无雨，风力较小，擦洗一新的汽车至少能保持一天。\"},\"drsg\":{\"brf\":\"较冷\",\"txt\":\"建议着厚外套加毛衣等服装。年老体弱者宜着大衣、呢外套加羊毛衫。\"},\"flu\":{\"brf\":\"少发\",\"txt\":\"各项气象条件适宜，无明显降温过程，发生感冒机率较低。\"},\"sport\":{\"brf\":\"较适宜\",\"txt\":\"天气较好，无雨水困扰，较适宜进行各种运动，但因气温较低，在户外运动请注意增减衣物。\"},\"trav\":{\"brf\":\"适宜\",\"txt\":\"天气较好，同时又有微风伴您一路同行。虽会让人感觉有点凉，但仍适宜旅游，可不要错过机会呦！\"},\"uv\":{\"brf\":\"最弱\",\"txt\":\"属弱紫外线辐射天气，无需特别防护。若长期在户外，建议涂擦SPF在8-12之间的防晒护肤品。\"}}}]}";


    List<Daily_forecast> daily_forecasts;
    Suggestion suggestion;

    /**
     * 获得响应的结果数据
     *
     * @param responseData
     */
    private void obtainResponseData(String responseData) {

        final Root root = GsonParseData.parseWeatherInfo(responseData);
        //处理主显示区域
        HeWeather5 heWeather5 = root.getHeWeather5().get(0);
        //获得具体的信息
        daily_forecasts = heWeather5.getDaily_forecast();
        //当天的详细信息
        final Daily_forecast daily_forecast = heWeather5.getDaily_forecast().get(0);
        Aqi aqi = heWeather5.getAqi();
        Basic basic = heWeather5.getBasic();
        final Now now = heWeather5.getNow();
        suggestion = heWeather5.getSuggestion();
        final City city = aqi.getCity();
        Looger.d(city.toString());


        UiThread.getUiThread(getContext()).post(new Runnable() {
            @Override
            public void run() {

                mainInfo(now, city, daily_forecast);
                //处理三天的天气信息
                threeDayInfo(now, daily_forecasts, city);
                //处理显示详情
                progressDetailInfo(suggestion);
                //天文信息，提供的是对日出日落封装
                progressAstro(daily_forecast.getAstro());
                //风力　　湿度　降雨量　　　气压
                progressNowWeather(daily_forecast);
                //紫外线　　能见度　　风速　　降雨率
                progressNowOtherWeather(daily_forecast);
                //处理指数
                progressIndex(suggestion);
            }
        });
    }


    /**
     * TextView kongqi;
     * TextView shushidu;
     * TextView xiche;
     * TextView chuanyi;
     * TextView ganmao;
     * TextView yundong;
     * TextView lvyou;
     * TextView ziwaixian;
     * 处理指数显示
     *
     * @param suggestion
     */
    private void progressIndex(Suggestion suggestion) {
        kongqi.setText(suggestion.getAir().getBrf());
        shushidu.setText(suggestion.getComf().getBrf());
        xiche.setText(suggestion.getCw().getBrf());
        chuanyi.setText(suggestion.getDrsg().getBrf());
        ganmao.setText(suggestion.getFlu().getBrf());
        yundong.setText(suggestion.getSport().getBrf());
        lvyou.setText(suggestion.getTrav().getBrf());
        ziwaixian.setText(suggestion.getUv().getBrf());
    }


    /**
     * 处理
     *
     * @param daily_forecasts
     * @param position
     */
    private void progressOtherDayWeatherInfo(List<Daily_forecast> daily_forecasts, int position) {
        Daily_forecast daily_forecast = null;
        if (position >= daily_forecasts.size()) {
            Looger.d("位置越界");
        } else {
            daily_forecast = daily_forecasts.get(position);
        }

        if (daily_forecast != null) {
            otherPres.setText(daily_forecast.getPres());
            otherWind.setText(daily_forecast.getWind().getDir());
            otherWindSC.setText(daily_forecast.getWind().getSc());
            otherHum.setText(daily_forecast.getHum());
            otherPcpn.setText(daily_forecast.getPcpn() + "mb");
        }


    }


    //处理今天的其他天气信息　　　紫外线　　能见度　　风速　　降雨率
    private void progressNowOtherWeather(Daily_forecast daily_forecast) {
        uv.setText(daily_forecast.getUv());
        visible.setText(daily_forecast.getVis());
        windSpeed.setText(daily_forecast.getWind().getSpd());
        pop.setText(daily_forecast.getPop());
    }

    /**
     * 处理今天的天气信息　　　　　风力　　湿度　降雨量　　　气压
     *
     * @param daily_forecast
     */
    private void progressNowWeather(Daily_forecast daily_forecast) {

        windSC.setText(daily_forecast.getWind().getSc());
        windDir.setText(daily_forecast.getWind().getDir());
        hum.setText(daily_forecast.getHum());
        pcpn.setText(daily_forecast.getPcpn());
        pres.setText(daily_forecast.getPres());

    }

    /**
     * 处理天文相关的数据
     *
     * @param astro
     */
    private void progressAstro(Astro astro) {
        richuTime.setText(astro.getSr());
        riluoTime.setText(astro.getSs());
        yueshengTime.setText(astro.getMr());
        yueluoTime.setText(astro.getMs());
    }

    //处理显示详情  通过了一个下拉的显示
    private void progressDetailInfo(Suggestion suggestion) {

    }


    MyDayAdapter myDayAdapter;

    /**
     * 处理三天的天气信息
     *
     * @param now
     * @param daily_forecasts
     * @param city
     */
    private void threeDayInfo(Now now, List<Daily_forecast> daily_forecasts, City city) {
        List<DayBrieflyInfo> dbf = new ArrayList<>();
        String[] array = {"今天", "明天", "后天"};
        for (int i = 0; i < daily_forecasts.size(); i++) {
            Daily_forecast df = daily_forecasts.get(i);
            DayBrieflyInfo dayBrieflyInfo = new DayBrieflyInfo(
                    array[i],
                    -1, df.getCondInner().getTxt_d(),
                    df.getWind().getDir(),
                    df.getTmp().getMax() + "/" + df.getTmp().getMin() + "℃"
            );
            dbf.add(dayBrieflyInfo);

        }

        myDayAdapter = new MyDayAdapter(dbf);
        save_three_day_weather_info.setAdapter(myDayAdapter);
    }

    /**
     * 处理主显示区域
     *
     * @param now
     * @param city
     * @param daily_forecast
     */
    private void mainInfo(Now now, City city, Daily_forecast daily_forecast) {
        Looger.d(daily_forecast.getCondInner().toString());
        newTemporary.setText(now.getTmp());
        position.setText(li.getCountry());//获得县乡地址
        positionRoute.setText(li.getStreet());//获的街道信息
        weatherStatus.setText(daily_forecast.getCondInner().getTxt_d());//获得白天的天气信息
        airQualityAndScore.setText(city.getQlty() + " " + city.getAqi());
        Looger.d(daily_forecast.getWind().toString());
        weatherStatus1.setText(daily_forecast.getCondInner().getTxt_d());

    }


    /**
     * 处理点击事件
     * 这个对点击事件的处理只适用于对
     * ｉｎｄｅｘ的处理
     *
     * @param v
     */

    @Override
    public void onClick(View v) {
        String value = null;
        switch (v.getId()) {
            case R.id.kongqi:
                Air air = suggestion.getAir();
                value = air.getTxt();
                break;
            case R.id.shushidu:
                Comf comf = suggestion.getComf();
                value = comf.getTxt();
                break;
            case R.id.wash_car:
                Cw cw = suggestion.getCw();
                value = cw.getTxt();
                break;
            case R.id.chuanyi:
                Drsg drsg = suggestion.getDrsg();
                value = drsg.getTxt();
                break;
            case R.id.ganmao:
                Flu flu = suggestion.getFlu();
                value = flu.getTxt();
                break;
            case R.id.yundong:
                Sport sport = suggestion.getSport();
                value = sport.getTxt();
                break;
            case R.id.lvyou:
                Trav trav = suggestion.getTrav();
                value = trav.getTxt();
                break;
            case R.id.ziwaixian:
                Uv uv = suggestion.getUv();
                value = uv.getTxt();
                break;
        }
        detailIndexInfo.setText(value);
        detailIndexInfo.setVisibility(View.VISIBLE);

    }

    /**
     * 初始化指数详细信息
     *
     * @param info
     */
    public void initIndexDetailInfo(String info) {

    }


    /**
     * 日出page的适配器
     */
    class MyRichuPageAdapter extends FragmentPagerAdapter {

        List<Fragment> fragmentList;

        public MyRichuPageAdapter(FragmentManager fm, List<Fragment> fragmentList) {
            super(fm);
            this.fragmentList = fragmentList;
        }


        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }


        public List<Fragment> getFragmentList() {
            return fragmentList;
        }

        public void setFragmentList(List<Fragment> fragmentList) {
            this.fragmentList = fragmentList;
        }

        /**
         * 添加一些fragments
         *
         * @param fragments
         */
        public void addFragment(List<Fragment> fragments) {
            if (fragments.size() > 0) {
                fragments.clear();
            }
            this.fragmentList.addAll(fragments);
            notifyDataSetChanged();
        }
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {
            //当可见的时候执行操作
            Looger.d("界面可见");
        } else {
            // 不可见时执行相应的操作
            Looger.d("界面不可见");
        }
    }

    /*
    这个类的作用是存放的是是三天天气相关的数据信息
     */
    class MyDayAdapter extends BaseAdapter {


        private List<DayBrieflyInfo> dayBrieflyInfos;

        /**
         * 适配器的构造函数
         *
         * @param dayBrieflyInfos
         */
        MyDayAdapter(List<DayBrieflyInfo> dayBrieflyInfos) {
            this.dayBrieflyInfos = dayBrieflyInfos;
        }


        @Override
        public int getCount() {

            return dayBrieflyInfos.size();
        }

        @Override
        public Object getItem(int position) {
            return dayBrieflyInfos.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            DayBrieflyInfo dayBrieflyInfo = dayBrieflyInfos.get(position);
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext())
                        .inflate(R.layout.day_briefly_weather_info,
                                parent, false);
                viewHolder = new ViewHolder();
                viewHolder.day = convertView.findViewById(R.id.weatherDay);
                viewHolder.weather = convertView.findViewById(R.id.weatherIcon);
                viewHolder.weatherDes = convertView.findViewById(R.id.weatherDes);
                viewHolder.windDir = convertView.findViewById(R.id.windDir);
                viewHolder.temporary = convertView.findViewById(R.id.temporary);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            viewHolder.day.setText(dayBrieflyInfo.getDay());
            for (int i = 0; i < weatherClass.length; i++) {
                String weatherDes = dayBrieflyInfo.getWeatherDes();
                if (weatherDes.contains(weatherClass[i])) {
                    viewHolder
                            .weather
                            .setBackgroundResource(weatherClassIcon[i]);
                }

            }

            viewHolder.weatherDes.setText(dayBrieflyInfo.getWeatherDes());
            viewHolder.windDir.setText(dayBrieflyInfo.getWindDir());
            viewHolder.temporary.setText(dayBrieflyInfo.getTemporary());

            return convertView;
        }

        class ViewHolder {
            TextView day;
            ImageView weather;
            TextView weatherDes;
            TextView windDir;
            TextView temporary;
        }
    }


    /**
     * 天气的简要信息
     */
    class DayBrieflyInfo {

        private String day;
        private int weatherIconResId;
        private String weatherDes;
        private String windDir;
        private String temporary;

        public DayBrieflyInfo(String day, int weatherIconResId, String weatherDes, String windDir, String temporary) {
            this.day = day;
            this.weatherIconResId = weatherIconResId;
            this.weatherDes = weatherDes;
            this.windDir = windDir;
            this.temporary = temporary;
        }

        public DayBrieflyInfo() {
        }

        public String getDay() {
            return day;
        }

        public void setDay(String day) {
            this.day = day;
        }

        public int getWeatherIconResId() {
            return weatherIconResId;
        }

        public void setWeatherIconResId(int weatherIconResId) {
            this.weatherIconResId = weatherIconResId;
        }

        public String getWeatherDes() {
            return weatherDes;
        }

        public void setWeatherDes(String weatherDes) {
            this.weatherDes = weatherDes;
        }

        public String getWindDir() {
            return windDir;
        }

        public void setWindDir(String windDir) {
            this.windDir = windDir;
        }

        public String getTemporary() {
            return temporary;
        }

        public void setTemporary(String temporary) {
            this.temporary = temporary;
        }
    }


}
