package com.rcs.nchumanity.dialog;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Build;
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

import androidx.annotation.DrawableRes;
import androidx.annotation.LayoutRes;
import androidx.annotation.RequiresApi;
import androidx.annotation.StyleRes;

;import com.rcs.nchumanity.R;
import com.rcs.nchumanity.entity.City;
import com.rcs.nchumanity.entity.Province;
import com.rcs.nchumanity.net.NetRequest;
import com.rcs.nchumanity.tool.Tool;
import com.rcs.nchumanity.tool.UiThread;

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


    public ViewGroup getView() {
        return view;
    }

    private Context context;

    /**
     * 一个简化版的dialog
     * 与
     * {@link #openDialog(Context, int, boolean, boolean, int, int, int, Object[])}相比较
     * 取消了下面几个
     * 1.对是否可以取消进行定制
     * 2.是否是屏幕宽度的大小
     * 3.显示的位置
     * 4.动画风格
     * 5.设置背景
     *
     * @param context
     * @param viewResId
     * @param t
     * @return
     */
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


    public void addView(int id, View view) {


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


    /**
     * 打开一个dialog
     *
     * @param context       上下文
     * @param viewid        视图布局的id
     * @param cancelable    是否可以取消
     * @param isWidthScreen 是否是宽屏显示
     * @param position      显示的位置
     * @param style         动画的id
     */
    public Dialog openDialog(Context context, @LayoutRes int viewid, boolean cancelable, boolean isWidthScreen, int position, @StyleRes int style, @DrawableRes int drawableBg, T... t) {

        view = (ViewGroup) LayoutInflater.from(context).inflate(viewid, null, false);

        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(context)
                .setView(view)
                .setCancelable(cancelable);
        androidx.appcompat.app.AlertDialog dialog = builder.create();
        if (isWidthScreen) {
            dialog.getWindow().getDecorView().setPadding(0, 0, 0, 0);
            /**
             * 我们发现了,上面所说的是正确的,那么通过设置背景,就能将dectorView的确定大小确定下来
             */
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                dialog.getWindow().getDecorView().setBackground(context.getResources().getDrawable(drawableBg));
            }
        }
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(-1, (int) -2, WindowManager.LayoutParams.TYPE_APPLICATION,
                WindowManager.LayoutParams.FLAG_DITHER, PixelFormat.RGBA_8888
        );
        layoutParams.gravity = position;
        if(style!=-1){
            layoutParams.windowAnimations = style;//小心使用的style错误
        }
        dialog.getWindow().setAttributes(layoutParams);
        bindView(this, dialog, t);
        return dialog;
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
            int height = Tool.getScreenDimension(context)[1];
            setPosition(height / 2, Gravity.TOP);
        }


        public void setPosition(int height, int gravity) {
            int width = Tool.getScreenDimension(context)[0];
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
//                            Looger.D("响应数据" + data);
                            allJsonData = new JSONArray(data);

                            if (leavel == 0) {
                                cityList.clear();
                                for (int i = 0; i < allJsonData.length(); i++) {

                                    JSONObject jsonObject = allJsonData.getJSONObject(i);
                                    City city = new City();
                                    city.setProvinceId(provinceId);
//                                    Looger.D("name=" + jsonObject.getString("name"));
//                                    Looger.D("id=" + jsonObject.getInt("id"));
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
