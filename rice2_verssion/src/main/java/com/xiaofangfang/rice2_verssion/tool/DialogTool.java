package com.xiaofangfang.rice2_verssion.tool;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import androidx.annotation.DrawableRes;
import androidx.annotation.IdRes;
import androidx.annotation.LayoutRes;
import androidx.annotation.RequiresApi;
import androidx.transition.Slide;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.xiaofangfang.rice2_verssion.R;
import com.xiaofangfang.rice2_verssion.model.City;
import com.xiaofangfang.rice2_verssion.model.Province;
import com.xiaofangfang.rice2_verssion.network.NetRequest;

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
 * 提供了一些定义好的dialog
 */
public abstract class DialogTool<T> implements DialogSet {


    private ViewGroup view;
    //1.操作成功
    //2,警告弹窗
    //3提示弹窗

    private Context context;

    public Dialog getDialog(Context context, @LayoutRes int viewResId, T... t) {

        this.context = context;

        view = (ViewGroup) LayoutInflater.from(context).inflate(viewResId, null, false);

        Dialog dialog = new AlertDialog.Builder(context)
                .setView(view)
                .setCancelable(true)
                .create();
        bindView(this, dialog, t);
        return dialog;
    }


    public abstract void bindView(DialogTool<T> d, Dialog dialog, T... t);

    @Override
    public void setText(int id, String text) {
        View view1 = view.findViewById(id);
        if (view1 instanceof TextView) {
            ((TextView) view1).setText(text);
        }

    }

    @Override
    public void setClickListener(int id, View.OnClickListener click) {
        View view1 = view.findViewById(id);
        view1.setOnClickListener(click);
    }

    @Override
    public void setAsyncImage(int id, int res) {
        View view2 = view.findViewById(id);
        if (view2 instanceof ImageView) {
            ((ImageView) view2).setImageResource(res);
        }
    }

    public void setTextSize(int id, int size) {

        float realSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, size, context.getResources().getDisplayMetrics());
        View view2 = view.findViewById(id);
        if (view2 instanceof TextView) {
            ((TextView) view2).setTextSize(realSize);
        }
    }

    public void setVisibility(int id, int visiable) {
        View view1 = view.findViewById(id);
        view1.setVisibility(visiable);
    }


    //下面是实现的地址选择的弹窗,为了代码移植的正确,我们暂时不在复用上面的代码

    /**
     * 地址选择的弹窗
     * <p>
     * 这个类的使用是  构建对象,设置位置改变的监听,  调用progress方法
     */
    public static class AddressSelectDialog {
        private Context context;

        public AddressSelectDialog(Context context) {
            this.context = context;
        }

        private AlertDialog alertDialog;
        //当前选中的水平
        private static int provinceId = -1;
        //用来存放当前的cityList
        private List<City> cityList = new ArrayList<>();
        private int leavel = -1;
        private List<Province> provinces;
        private MyAreaSelectViewAdapter myAreaSelectViewAdapter;
        private String provinceUrl = "http://guolin.tech/api/china";
        private Province currSeleProvi;

        /**
         * 位置处理
         */
        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)//android 5.0才能使用的方法
        public void progress() {

            //清除上一次的状态
            provinceId = -1;
            leavel = -1;
            AlertDialog.Builder builder = new AlertDialog.Builder(context)
                    .setView(R.layout.location_select);
            alertDialog = builder.create();
            //设置可取消
            alertDialog.setCancelable(true);
            alertDialog.show();
            progressInnerViewEvent(alertDialog);
            int height = Tools.getScreenDimension(context)[1];
            setPosition(height / 2, Gravity.TOP);
        }


        public void setPosition(int height, int gravity) {
            int width = Tools.getScreenDimension(context)[0];
            WindowManager.LayoutParams layoutParams = alertDialog.getWindow().getAttributes();
            layoutParams.width = width;
            layoutParams.height = height;
            Window window = alertDialog.getWindow();
            window.setAttributes(layoutParams);
            window.setGravity(gravity);
            window.setWindowAnimations(R.style.location_select_animation);
        }


        private void progressInnerViewEvent(final AlertDialog alertDialog) {

            ListView listView = alertDialog.findViewById(R.id.locationList);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if (leavel == 0) {//代表当前的是省份的数据的点击事件的处理
                        City city = cityList.get(position);
                        provinceId = city.getProvinceId();
                        netLoadData(provinceUrl + "/" + provinceId);
                        currSeleProvi = new Province(city.getId(), city.getName(), city.getProvinceId());
                        return;
                    }
                    if (leavel == 1) {//代表的是对市级数据的点击事件的处理
                        alertDialog.cancel();
                        //在这里设置接口的点击回调
                        if (locationChangeListener != null) {
                            //进行回调
                            locationChangeListener.onSucessfulResponse(cityList.get(position), currSeleProvi);
                            Log.d("test", "选中的地区" + cityList.get(position) + "\n" + currSeleProvi);
                        }
                        return;
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


        private void netLoadData(final String url) {

            new Thread(() -> {
                NetRequest.requestUrl(url, new Callback() {
                    @Override

                    public void onFailure(Call call, IOException e) {
                        UiThread.getUiThread().post(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(context, "网络加载错误", Toast.LENGTH_SHORT).show();
                                Log.d("test", "网络加载出错=" + e.getLocalizedMessage());
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
                                    city.save();
                                    cityList.add(city);
                                }
                            } else if (leavel == -1) {
                                for (int i = 0; i < allJsonData.length(); i++) {
                                    JSONObject jsonObject = allJsonData.getJSONObject(i);
                                    Province province = new Province();
                                    province.setProvinceId(jsonObject.getInt("id"));
                                    province.setName(jsonObject.getString("name"));
                                    //province.save();  会出错,自己的代码问题,不去检测了  lazy
                                    provinces.add(province);
                                }
                            }

                            UiThread.getUiThread().post(() -> {
                                if (leavel == 0) {
                                    myAreaSelectViewAdapter.setData(cityList);
                                    leavel = 1;
                                    //在这里设置返回按钮可见
                                } else if (leavel == -1) {
                                    addDataResource();
                                }
                            });


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }).start();
        }


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
                AddressSelectDialog.this.cityList = cityList;
                notifyDataSetChanged();
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                AddressSelectDialog.MyAreaSelectViewAdapter.ViewHolder viewHolder;
                City city = cityList.get(position);

                if (convertView == null) {
                    convertView = LayoutInflater.from(context).inflate(R.layout.area_select_list_sub, parent, false);
                    viewHolder = new MyAreaSelectViewAdapter.ViewHolder();
                    viewHolder.textView = convertView.findViewById(R.id.select_area_single_item);
                    convertView.setTag(viewHolder);
                } else {
                    viewHolder = (MyAreaSelectViewAdapter.ViewHolder) convertView.getTag();
                }

                viewHolder.textView.setText(city.getName());

                return convertView;
            }

            public class ViewHolder {
                TextView textView;
            }
        }

        private LocationChangeListener locationChangeListener;

        public void setLocationChangeListener(LocationChangeListener locationChangeListener) {
            this.locationChangeListener = locationChangeListener;
        }

        public interface LocationChangeListener {
            void onSucessfulResponse(City city, Province province);
        }

    }


}
