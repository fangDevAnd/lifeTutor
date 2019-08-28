package com.xiaofangfang.rice2_verssion.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.xiaofangfang.rice2_verssion.ParentActivity;
import com.xiaofangfang.rice2_verssion.R;
import com.xiaofangfang.rice2_verssion.model.City;
import com.xiaofangfang.rice2_verssion.model.Province;
import com.xiaofangfang.rice2_verssion.network.NetRequest;
import com.xiaofangfang.rice2_verssion.tool.DialogTool;
import com.xiaofangfang.rice2_verssion.tool.Looger;
import com.xiaofangfang.rice2_verssion.tool.UiThread;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.litepal.crud.DataSupport;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * 另外一个过滤条，实现的是对的地区的过滤   过滤的是地区和价格
 */
public class FilterConditionNew1Bar extends LinearLayout implements View.OnClickListener {

    public FilterConditionNew1Bar(Context context) {
        this(context, null);
    }

    public FilterConditionNew1Bar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FilterConditionNew1Bar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    //价格     地区      过滤
    private TextView filterOne, filterTwo, filterThree;

    private void initView() {

        View view = LayoutInflater.from(getContext()).inflate(R.layout.filter_condition_new1_bar, null, false);

        filterOne = view.findViewById(R.id.filterOne);
        filterOne.setOnClickListener(this);
        filterTwo = view.findViewById(R.id.filterTwo);
        filterThree = view.findViewById(R.id.filterThree);
        filterThree.setOnClickListener(this);
        filterTwo.setOnClickListener(this);
        setIconForPrice();
        filterTwo.setText(ParentActivity.city.getName());
        LayoutParams layoutParams1 = new LayoutParams(-1, -2);
        this.addView(view, layoutParams1);


    }

    private ConsumeToobar.LocationChangeListener lo;
    private DialogTool.AddressSelectDialog addressSelectDialog;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.filterTwo:
//                progressLocationButtonEvent();

                addressSelectDialog = new DialogTool.AddressSelectDialog(getContext());
                addressSelectDialog.setLocationChangeListener((City city, Province province) -> {
                    filterTwo.setText(city.getName());
                    if (lo != null) {
                        lo.change(province, city);
                    }
                });
                addressSelectDialog.progress();


                break;
            case R.id.filterOne:
                currentPriceSortAsc = !currentPriceSortAsc;
                setIconForPrice();
                if (filterOptionClickListner != null) {
                    filterOptionClickListner.onPriceClick(v, currentPriceSortAsc);
                }
                break;
            case R.id.filterThree:
                if (filterOptionClickListner != null) {
                    filterOptionClickListner.onFilterCondition(v);
                }
                break;
        }

    }


    AlertDialog alertDialog;

    /**
     * 处理位置按钮的点击事件的处理
     */
    private void progressLocationButtonEvent() {

        //清除上一次的状态
        provinceId = -1;
        leavel = -1;

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext())
                .setView(R.layout.location_select);

        alertDialog = builder.create();
        //设置不可取消
        alertDialog.setCancelable(true);
        alertDialog.show();

        progressInnerViewEvent(alertDialog);

        int width = getResources().getDisplayMetrics().widthPixels;
        int height = getResources().getDisplayMetrics().heightPixels;

        WindowManager.LayoutParams layoutParams = alertDialog.getWindow().getAttributes();
        layoutParams.width = width;
        layoutParams.height = height / 2;
        Window window = alertDialog.getWindow();
        window.setAttributes(layoutParams);
        window.setGravity(Gravity.TOP);
        window.setWindowAnimations(R.style.location_select_animation);
    }


    /**
     * 当前选中的水平
     */
    public static int provinceId = -1;
    //用来存放当前的cityList
    public List<City> cityList = new ArrayList<>();
    public int leavel = -1;
    public List<Province> provinces;
    MyAreaSelectViewAdapter myAreaSelectViewAdapter;

    private void progressInnerViewEvent(final AlertDialog alertDialog) {

        ListView listView = alertDialog.findViewById(R.id.locationList);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Looger.D("当前的leavl=" + leavel);
                if (leavel == 0) {//代表当前的是省份的数据的点击事件的处理
                    City city = cityList.get(position);
                    provinceId = city.getProvinceId();
                    Looger.D("当前的provinceID=" + provinceId);
                    netLoadData(provinceUrl + "/" + provinceId);
                    return;
                }
                if (leavel == 1) {//代表的是对市级数据的点击事件的处理
                    alertDialog.cancel();

                    ParentActivity.city = cityList.get(position);
                    filterTwo.setText(cityList.get(position).getName());

                    //在这里设置接口的点击回调

                    if (locationChangeListener != null) {
                        //进行回调
                        locationChangeListener.onSucessfulResponse();
                    }
                    return;
                }

                if (leavel == -1) {
                    provinces.clear();
                }

            }
        });

        myAreaSelectViewAdapter = new MyAreaSelectViewAdapter();
        listView.setAdapter(myAreaSelectViewAdapter);

        if (provinceId == -1) {//直接获取省级的数据
            provinces = DataSupport.findAll(Province.class);
            if (provinces == null || provinces.size() == 0) {
                //代表的是没有数据，通过网络加载
                netLoadData(provinceUrl);
            } else {
                //存在数据，就直接更新当前的显示
                addDataResource();
            }
        }

    }


    /**
     * 获得赛选的条件
     *
     * @return
     */
    public String getFilterCondition() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("cityId=").append(ParentActivity.city.getCityId())
                .append("&").append("priceSortAsc=").append(currentPriceSortAsc);
        return stringBuilder.toString();
    }


    public String provinceUrl = "http://guolin.tech/api/china";

//    public String cityUrl = "http://guolin.tech/api/china/";

    private void netLoadData(final String url) {

        Looger.D("当前的url=" + url);

        new Thread(new Runnable() {
            @Override
            public void run() {
                NetRequest.requestUrl(url, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        UiThread.getUiThread().post(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getContext(), "网络加载错误", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        JSONArray allJsonData;
                        String data = response.body().string();
                        try {
                            Looger.D("响应数据" + data);
                            allJsonData = new JSONArray(data);

                            if (leavel == 0) {
                                cityList.clear();
                                for (int i = 0; i < allJsonData.length(); i++) {

                                    JSONObject jsonObject = allJsonData.getJSONObject(i);
                                    City city = new City();
                                    city.setProvinceId(provinceId);
                                    Looger.D("name=" + jsonObject.getString("name"));
                                    Looger.D("id=" + jsonObject.getInt("id"));
                                    city.setName(jsonObject.getString("name"));
                                    city.setCityId(jsonObject.getInt("id"));
                                    //city.save();
                                    cityList.add(city);
                                }
                            } else if (leavel == -1) {
                                for (int i = 0; i < allJsonData.length(); i++) {
                                    JSONObject jsonObject = allJsonData.getJSONObject(i);
                                    Province province = new Province();
                                    province.setProvinceId(jsonObject.getInt("id"));
                                    province.setName(jsonObject.getString("name"));
//                                    province.save();
                                    provinces.add(province);
                                }
                            }

                            UiThread.getUiThread().post(new Runnable() {
                                @Override
                                public void run() {
                                    if (leavel == 0) {
                                        myAreaSelectViewAdapter.setData(cityList);
                                        leavel = 1;
                                        //在这里设置返回按钮可见
                                    } else if (leavel == -1) {
                                        addDataResource();
                                    }
                                }
                            });


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        }).start();
    }


    private int[] saleDrawable = {
            R.drawable.ic_arrow_down,
            R.drawable.ic_arrow_up
    };


    /**
     * 当前的价格排序是否是降序排列
     */
    private boolean currentPriceSortAsc = true;


    private void addDataResource() {
        cityList.clear();
        for (int i = 0; i < provinces.size(); i++) {
            Province province = provinces.get(i);
            cityList.add(new City(-1, province.getProvinceId(), province.getName(), -1));
        }
        myAreaSelectViewAdapter.setData(cityList);
        leavel = 0;//代表的是获得了省份的数据了
    }


    class MyAreaSelectViewAdapter extends BaseAdapter {


        @Override

        public int getCount() {
            return cityList.size();
        }

        @Override
        public Object getItem(int position) {
            return cityList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        public void setData(List<City> cityList) {
            FilterConditionNew1Bar.this.cityList = cityList;
            notifyDataSetChanged();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            City city = cityList.get(position);

            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.area_select_list_sub, parent, false);
                viewHolder = new ViewHolder();
                viewHolder.textView = convertView.findViewById(R.id.select_area_single_item);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            viewHolder.textView.setText(city.getName());

            return convertView;
        }

        public class ViewHolder {
            TextView textView;
        }
    }

    private void setIcon(TextView view, int index) {
        Drawable drawable = getResources().getDrawable(saleDrawable[index]);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        view.setCompoundDrawables(null, null, drawable, null);
    }

    private void setIconForPrice() {
        if (currentPriceSortAsc) {
            setIcon(filterOne, 0);
        } else {
            setIcon(filterOne, 1);
        }
    }


    private FilterOptionClickListner filterOptionClickListner;


    public void setFilterOptionClickListner(FilterOptionClickListner filterOptionClickListner) {
        this.filterOptionClickListner = filterOptionClickListner;
    }

    public interface FilterOptionClickListner {

        void onPriceClick(View view, boolean currentPriceAsc);

        void onFilterCondition(View view);
    }


    /**
     * 位置改变的监听接口
     */
    private LocationChangeListener locationChangeListener;


    public void setLocationChangeListener(LocationChangeListener locationChangeListener) {
        this.locationChangeListener = locationChangeListener;
    }

    public interface LocationChangeListener {
        void onSucessfulResponse();
    }
}
