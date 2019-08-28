package com.rcs.nchumanity.ul.basicMap;

import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.LogoPosition;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.route.RoutePlanSearch;
import com.baidu.mapapi.utils.DistanceUtil;
import com.rcs.nchumanity.R;
import com.rcs.nchumanity.adapter.ListViewCommonsAdapter;
import com.rcs.nchumanity.tool.DensityConvertUtil;
import com.rcs.nchumanity.tool.Tool;
import com.rcs.nchumanity.ul.ParentActivity;
import com.rcs.nchumanity.view.CommandBar;

import java.io.File;
import java.net.URISyntaxException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class BasicMapActivity extends ParentActivity {


    public static final String TITLE = "title";
    private static final String TAG = "test";


    public LocationClient mLocationClient;

    private MapView mapView;

    private BaiduMap baiduMap;

    private boolean isFirstLocate = true;

    private List<? extends ILocaPoint> list;


    private int layoutItemId = R.layout.map_item;


    /**
     * 当前的位置
     */
    private LatLng currentPosition;

    /**
     * 定位点的列表
     */
    private ListView positionListView;

    private ImageView icon;

    private int iconHeight;


    /**
     * 大致的估算了南昌市的大小 ，根据aed分布的情况，将地图检索导数据后的
     * 缩放级别改为 9
     */
    private int scanLevel = 9;


    /**
     * 通用的toolbar
     */
    private CommandBar toolbar;


    /**
     * 搜索结果
     */
    RoutePlanSearch mSearch;

    /**
     * 查询到的列表区域
     */
    private LinearLayout listArea;

    /**
     * card布局
     */
//    private CardView cardView;

    /**
     * 结果的数量
     */
//    private TextView resultCount;

    /**
     * 搜索的标题
     */
//    private TextView title;

    /**
     * 向上的按钮
     */
    private ImageButton up;

    /**
     * 位置详情界面
     */
    private LinearLayout positionDetail;


    /**
     * 定位
     */
    private ImageButton position;


    private Map<LatLng, ILocaPoint> positionToMarker = new HashMap();

    /**
     * 距离到标记点的映射实现
     */
    private Map<ILocaPoint, String> distanceToMarker = new HashMap<>();


    private boolean markerClicked = false;

    private int positionOffsetY = -100;


    public int positionDetailHeight;


    @Override

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);


        Bundle bundle = getIntent().getExtras();

        if (bundle == null) {
            throw new RuntimeException("please bundle param to " + getClass().getName() + "");
        }

        /**
         * 获得定位点
         */
        list = (List<? extends ILocaPoint>) bundle.getSerializable(ArrayList.class.getSimpleName());
        String titleS = bundle.getString(TITLE);

        if (list == null) {
            throw new IllegalArgumentException("point list is not null");
        }

        if (titleS == null) {
            throw new RuntimeException("please set search title");
        }

        mLocationClient = new LocationClient(getApplicationContext());

        LocationClientOption locationOption = new LocationClientOption();

        locationOption.setIsNeedAddress(true);
        //可选，设置是否需要设备方向结果
        locationOption.setNeedDeviceDirect(true);
        mLocationClient.setLocOption(locationOption);
        mLocationClient.registerLocationListener(new MyLocationListener());
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.map_test);

        findView();


        List<String> permissionList = new ArrayList<>();
        if (ContextCompat.checkSelfPermission(BasicMapActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if (ContextCompat.checkSelfPermission(BasicMapActivity.this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.READ_PHONE_STATE);
        }
        if (ContextCompat.checkSelfPermission(BasicMapActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (!permissionList.isEmpty()) {
            String[] permissions = permissionList.toArray(new String[permissionList.size()]);
            ActivityCompat.requestPermissions(BasicMapActivity.this, permissions, 1);
        } else {
            requestLocation();
        }
    }

    private void findView() {
        //        title = findViewById(R.id.title);
//        title.setText(titleS);
//        resultCount = findViewById(R.id.resultCount);
//        resultCount.setText(list.size() + "个结果");

        positionDetail = findViewById(R.id.positionDetail);

        icon = findViewById(R.id.icon);
        icon.post(new Runnable() {
            @Override
            public void run() {
                if (iconHeight == 0) {
                    iconHeight = icon.getMeasuredHeight();
                }
            }
        });
        icon.setOnClickListener((v) -> {

            switch ((String) v.getTag()) {
                case "1":
                    anim("3", Tool.getScreenDimension(this)[1] + iconHeight);
                    break;

                case "2":
                    anim("1", toolbar.getMeasuredHeight());
                    break;
            }
        });

        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("定位");
//        cardView = findViewById(R.id.cardView);
        listArea = findViewById(R.id.listArea);
        up = findViewById(R.id.up);
        position = findViewById(R.id.position);
        position.setOnClickListener((v) -> {
            moveToLocation(currentPosition, 19f);

            // if (markerClicked) {
            anim("2", listTop);
            markerClicked = false;
//            }

        });


        initMapView();


        positionListView = findViewById(R.id.searchResult);
        baiduMap = mapView.getMap();
        baiduMap.setMyLocationEnabled(true);
        //设置定位图标点击事件监听
        baiduMap.setOnMarkerClickListener(listener);

        //默认向下不可见
        positionDetail.post(() -> {
            positionDetail.setTranslationY(positionDetailHeight = positionDetail.getMeasuredHeight());
        });

    }


    /**
     * 标记点的点击事件的处理
     */
    BaiduMap.OnMarkerClickListener listener = new BaiduMap.OnMarkerClickListener() {
        /**
         * 地图 Marker 覆盖物点击事件监听函数
         * @param marker 被点击的 marker
         */
        public boolean onMarkerClick(Marker marker) {

            Log.d(TAG, "onMarkerClick: ");


            LatLng markPosition = marker.getPosition();

            moveToLocation(markPosition, 19f);

            ILocaPoint iLocaPoint = positionToMarker.get(markPosition);

            markerClicked = true;

            //接下来显示点的详情信息
            positionDetailAnim = animDetailDis(iLocaPoint, markPosition);
            positionDetailAnim.start();


            return true;
        }
    };

    private void initMapView() {
        mapView = (MapView) findViewById(R.id.bmapView);
        mapView.setLogoPosition(LogoPosition.logoPostionRightBottom);

    }


    private ObjectAnimator positionDetailAnim;


    public ObjectAnimator animDetailDis(ILocaPoint iLocaPoint, LatLng markPosition) {
        /**
         *
         *使用动画属性设置显示的效果
         *提供导航按钮，进行导航
         */

        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(positionDetail, "translationY", 0);
        objectAnimator.setInterpolator(new DecelerateInterpolator());
        objectAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {

                TextView positionName = positionDetail.findViewById(R.id.pointName);
                TextView distaince = positionDetail.findViewById(R.id.distance);
                TextView position = positionDetail.findViewById(R.id.position);

                TextView nav = positionDetail.findViewById(R.id.nav);

                positionName.setText(iLocaPoint.getLocationName());
                distaince.setText(distanceToMarker.get(iLocaPoint));
                position.setText(iLocaPoint.getPosition());
                nav.setOnClickListener((v) -> {
                    try {
                        MapStartParam.invokeBaiduNavi(BasicMapActivity.this, markPosition.longitude, markPosition.latitude);
                    } catch (URISyntaxException e) {
                        e.printStackTrace();
                    }
                });
            }

            @Override
            public void onAnimationEnd(Animator animation) {

                anim("3", Tool.getScreenDimension(BasicMapActivity.this)[1] + iconHeight);
            }
        });

        objectAnimator.setDuration(500);
        return objectAnimator;
    }


    public void animDetailHidden() {
        markerClicked = false;
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(positionDetail, "translationY", positionDetailHeight);
        objectAnimator.setInterpolator(new DecelerateInterpolator());
        objectAnimator.start();
    }


    private int listTop;

    /**
     * 初始化布局
     */
    private void initPositionListViewLayout() {
        int screenHeight = Tool.getScreenDimension(this)[1];

        listTop = screenHeight / 2 + DensityConvertUtil.dpi2px(this, positionOffsetY);
        listTop = Math.max(screenHeight - positionListView.getMeasuredHeight(), listTop);

        Log.d("test", "initPositionListViewLayout:===" + listTop);

        listArea.setTranslationY(listTop);

        position.setTranslationY(listTop - Tool.getScreenDimension(
                BasicMapActivity.this)[1] + DensityConvertUtil.dpi2px(BasicMapActivity.this, positionOffsetY));

    }


    private void anim(String tag, int offsetY) {

        ObjectAnimator oba = ObjectAnimator.ofFloat(listArea, "translationY", offsetY);
        oba.setInterpolator(new AccelerateDecelerateInterpolator());
        oba.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                if (tag.equals("1")) {//全屏
//                    cardView.setVisibility(View.INVISIBLE);
                    toolbar.setVisibility(View.VISIBLE);
                    icon.setImageResource(R.drawable.ic_down);
                    position.setVisibility(View.INVISIBLE);
                    icon.setTag(tag);
                    animDetailHidden();
//                    markerClicked = false;

                } else if (tag.equals("2")) {//半屏
//                    cardView.setVisibility(View.VISIBLE);
                    toolbar.setVisibility(View.INVISIBLE);
                    icon.setImageResource(R.drawable.ic_up);
                    up.setVisibility(View.INVISIBLE);
                    position.setTranslationY(offsetY - Tool.getScreenDimension(
                            BasicMapActivity.this)[1] + DensityConvertUtil.dpi2px(BasicMapActivity.this, positionOffsetY));
                    icon.setTag(tag);
                } else if (tag.equals("3")) {//未出现

                    if (markerClicked) {
                        //点击的是
                        up.setVisibility(View.INVISIBLE);
                    } else {
                        up.setVisibility(View.VISIBLE);

                        up.setOnClickListener((v) -> {
                            anim("2", listTop);
                            animDetailHidden();
                        });
                    }
                    position.setVisibility(View.VISIBLE);
                    position.setTranslationY(DensityConvertUtil.dpi2px(BasicMapActivity.this, positionOffsetY));

                }
            }
        });
        oba.start();
    }


    private void navigateTo(BDLocation location) {

        currentPosition = new LatLng(location.getLatitude(), location.getLongitude());
        if (isFirstLocate) {
//        Toast.makeText(this, "nav to " + location.getAddrStr(), Toast.LENGTH_SHORT).show();
            moveToLocation(currentPosition, scanLevel);
            isFirstLocate = false;
            Log.d(TAG, "navigateTo: " + location.getLongitude() + "\t" + location.getLatitude());
        }

        //回调到信息列表，计算当前位置与标记点的位置的距离

        for (int i = 0; i < list.size(); i++) {

            ILocaPoint point = list.get(i);

            //获得距离的米
            int distance = (int) DistanceUtil.getDistance(
                    currentPosition,
                    new LatLng(point.getLongitude(),
                            point.getLatitude()));

            DecimalFormat decimalFormat = new DecimalFormat(".0");

            String distances = decimalFormat.format(distance / 1000.0);
            String disText = "距离" + distances + "千米";//10167positionOffsetY  823146  648142  511232

            distanceToMarker.put(point, disText);

            ListViewCommonsAdapter.ViewHolder viewHolder = new ArrayList<>(viewHolders).get(i);//
            if (viewHolder != null) {
                viewHolder.setText(R.id.distance, disText);
            }
        }


        Log.d("test", "navigateTo: 位置更新中。。。。");
        MyLocationData.Builder locationBuilder = new MyLocationData.
                Builder();
        locationBuilder.latitude(location.getLatitude());
        locationBuilder.longitude(location.getLongitude());
        MyLocationData locationData = locationBuilder.build();
        baiduMap.setMyLocationData(locationData);
    }

    /**
     * 请求位置
     */
    private void requestLocation() {
        initLocation();
        mLocationClient.start();
        if (isFirstLocate) {
            initView();
        }
    }

    /**
     * 移动到当前的位置
     */
    public void moveToLocation(LatLng position, float level) {
        MapStatusUpdate update = MapStatusUpdateFactory.newLatLng(position);
        baiduMap.animateMapStatus(update);
        update = MapStatusUpdateFactory.zoomTo(level);
        baiduMap.animateMapStatus(update);
    }


    /**
     * 初始化视图数据
     */
    private void initView() {

        /**
         * 定位之后，我们设置定位点列表的数据
         */

        addMarkerPoint();

    }

    /**
     * 初始化位置
     */
    private void initLocation() {
        LocationClientOption option = new LocationClientOption();
        option.setScanSpan(5000);
        option.setIsNeedAddress(true);
        mLocationClient.setLocOption(option);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mLocationClient.stop();
        mapView.onDestroy();
        baiduMap.setMyLocationEnabled(false);
        if (mSearch != null) {
            mSearch.destroy();
        }

    }

    /**
     * 请求权限的回调函数
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0) {
                    for (int result : grantResults) {
                        if (result != PackageManager.PERMISSION_GRANTED) {
                            Toast.makeText(this, "必须同意所有权限才能使用本程序", Toast.LENGTH_SHORT).show();
                            finish();
                            return;
                        }
                    }
                    requestLocation();
                } else {
                    Toast.makeText(this, "发生未知错误", Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
            default:
        }
    }

    public class MyLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            if (location.getLocType() == BDLocation.TypeGpsLocation
                    || location.getLocType() == BDLocation.TypeNetWorkLocation) {
                navigateTo(location);
            }
        }

    }


    /**
     * 列表信息的适配器
     */
    private ListViewCommonsAdapter<ILocaPoint> listViewCommonsAdapter;


    private Set<ListViewCommonsAdapter.ViewHolder> viewHolders = new HashSet<>();


    private AlertDialog load;


    /**
     * 添加标记点
     */
    private void addMarkerPoint() {


        listViewCommonsAdapter = new ListViewCommonsAdapter<ILocaPoint>((ArrayList<ILocaPoint>) list, layoutItemId) {
            @Override
            public void bindView(ViewHolder holder, ILocaPoint obj) {
                holder.setText(R.id.pointName, obj.getLocationName());

                String disText = null;
                if (currentPosition == null) {
                    disText = "位置计算中";
                }

                Log.d("test", "bindView: 当前view的高度" + positionListView.getMeasuredHeight());

                if (positionListView.getMeasuredHeight() > 0 && isFirstLocate) {
                    initPositionListViewLayout();
                }

                holder.setText(R.id.distance, disText);

                viewHolders.add(holder);

                holder.setText(R.id.position, obj.getPosition());

                holder.setOnClickListener(R.id.nav, (v) -> {

                    //进行路线规划
                    if (isInstallByread("com.baidu.BaiduMap")) {
                        try {
                            MapStartParam.invokeBaiduNavi(BasicMapActivity.this, obj.getLatitude(), obj.getLongitude());
                        } catch (URISyntaxException e) {
                            e.printStackTrace();
                        }
                        Log.e("GasStation", "百度地图客户端已经安装");
                        return;
                    } else if (isInstallByread("com.autonavi.minimap")) {
                        MapStartParam.invokeGaoDeNavi(BasicMapActivity.this, obj.getLatitude(), obj.getLongitude(), obj.getLocationName());
                        Log.e("GasStation", "高德地图客户端已经安装");
                        return;
                    } else if (isInstallByread("com.tencent.map")) {
                        MapStartParam.invokeTecentNavi(BasicMapActivity.this, obj.getLocationName(),
                                obj.getLongitude() + "," + obj.getLatitude());
                        return;
                    }else {
                        Toast.makeText(BasicMapActivity.this, "请安装地图软件", Toast.LENGTH_SHORT).show();
                    }
                });

            }

            @Override
            public int getCount() {
                return list.size();
            }
        };

        positionListView.setAdapter(listViewCommonsAdapter);

        for (int i = 0; i < list.size(); i++) {
            ILocaPoint localPoint = list.get(i);
            //定义Maker坐标点
            LatLng point = new LatLng(localPoint.getLongitude(), localPoint.getLatitude());

            //构建Marker图标
            BitmapDescriptor bitmap = BitmapDescriptorFactory
                    .fromResource(R.drawable.marker_1);
            //构建MarkerOption，用于在地图上添加Marker
            OverlayOptions option = new MarkerOptions()
                    .position(point)
                    .icon(bitmap);

            //实现自定义类和绑定类的映射
            positionToMarker.put(point, localPoint);
            baiduMap.addOverlay(option);          //在地图上添加Marker，并显示
        }
    }

    //判断是否安装目标应用
    private boolean isInstallByread(String packageName) {
        return new File("/data/data/" + packageName)
                .exists();
    }
}
