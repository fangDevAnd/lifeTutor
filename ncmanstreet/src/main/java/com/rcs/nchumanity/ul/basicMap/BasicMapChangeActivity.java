package com.rcs.nchumanity.ul.basicMap;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
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
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BasicMapChangeActivity extends ParentActivity {


    public static final String TITLE = "title";
    private static final String TAG = "test";


    public static final String DATA = "data";


    public LocationClient mLocationClient;

    MapView mapView;

    private BaiduMap baiduMap;

    private boolean isFirstLocate = true;

    private List<? extends ILocaPoint> list;

    /**
     * 当前的位置
     */
    private LatLng currentPosition;

    /**
     * 定位点的列表
     */
    LinearLayout positionListView;


    private int layoutItemId = R.layout.map_item;


    /**
     * 搜索结果
     */
    RoutePlanSearch mSearch;


    /**
     * 大致的估算了南昌市的大小 ，根据aed分布的情况，将地图检索导数据后的
     * 缩放级别改为 9
     */
    private int scanLevel = 9;


    /**
     * 定位
     */
    private ImageButton position;


    private Map<LatLng, ILocaPoint> positionToMarker = new HashMap();

    /**
     * 距离到标记点的映射实现
     */
//    private Map<ILocaPoint, String> distanceToMarker = new HashMap<>();
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_basic);
        Bundle bundle = getIntent().getExtras();
        /**
         * 获得定位点
         */
        list = (List<? extends ILocaPoint>) bundle.getSerializable(DATA);

        if (list == null) {
            throw new IllegalArgumentException("point list is not null");
        }


        initMapView();

        mLocationClient = new LocationClient(getApplicationContext());

        LocationClientOption locationOption = new LocationClientOption();

        locationOption.setIsNeedAddress(true);
        //可选，设置是否需要设备方向结果
        locationOption.setNeedDeviceDirect(true);
        mLocationClient.setLocOption(locationOption);
        mLocationClient.registerLocationListener(new MyLocationListener());
        SDKInitializer.initialize(getApplicationContext());


        List<String> permissionList = new ArrayList<>();
        if (ContextCompat.checkSelfPermission(BasicMapChangeActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if (ContextCompat.checkSelfPermission(BasicMapChangeActivity.this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.READ_PHONE_STATE);
        }
        if (ContextCompat.checkSelfPermission(BasicMapChangeActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (!permissionList.isEmpty()) {
            String[] permissions = permissionList.toArray(new String[permissionList.size()]);
            ActivityCompat.requestPermissions(BasicMapChangeActivity.this, permissions, 1);
        } else {
            requestLocation();
        }

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

            AlertDialog.Builder builder = new AlertDialog.Builder(BasicMapChangeActivity.this)
                    .setTitle("导航提示")
                    .setMessage("是否导航到" + iLocaPoint.getPosition())
                    .setPositiveButton("确定", ((dialog, which) -> {

                    })).setNegativeButton("取消", (dialog, which) -> {

                    });

            builder.create().show();

            return true;
        }
    };

    private void initMapView() {

        positionListView = findViewById(R.id.positionListView);

        mapView = findViewById(R.id.mapView);
        mapView.setLogoPosition(LogoPosition.logoPostionRightBottom);
        baiduMap = mapView.getMap();
        baiduMap.setOnMarkerClickListener(listener);
        baiduMap.setMyLocationEnabled(true);


        findViewById(R.id.position).setOnClickListener((v) -> {
            if (currentPosition != null) {
                moveToLocation(currentPosition, 19f);
            }
        });

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

        for (int i = 0; i < viewHolders.size(); i++) {

            ILocaPoint point = list.get(i);
            String disText = getDistance(point.getLongitude(), point.getLatitude());
            TextView view = (TextView) viewHolders.get(i);
            view.setTag(disText);
            view.setText(disText);//0   25.9  25.9  26.0   25.9  13.5


            Log.d("test", "navigateTo: 位置更新中。。。。");
            MyLocationData.Builder locationBuilder = new MyLocationData.
                    Builder();
            locationBuilder.latitude(location.getLatitude());
            locationBuilder.longitude(location.getLongitude());
            MyLocationData locationData = locationBuilder.build();
            baiduMap.setMyLocationData(locationData);
        }
    }


    private String getDistance(double longitude, double latitude) {
        //获得距离的米
        int distance = (int) DistanceUtil.getDistance(
                currentPosition,
                new LatLng(latitude,
                        longitude));

        DecimalFormat decimalFormat = new DecimalFormat("0.0");

        String distances=null;
        String disText=null;
        if (distance<1000){
             distances = decimalFormat.format(distance);
             disText="距离"+distances+"米";
        }else {
            distances=decimalFormat.format(distance/1000.0);
            disText="距离" + distances + "千米";
        }
        return disText;
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
        option.setOpenGps(true);
        option.setCoorType("bd09ll");
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
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
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


    private List<View> viewHolders = new ArrayList<>();


    /**
     * 添加标记点
     */
    private void addMarkerPoint() {


        for (int i = 0; i < list.size(); i++) {

            ILocaPoint point = list.get(i);

            View view = LayoutInflater.from(this).inflate(layoutItemId, null);

            TextView pointName = view.findViewById(R.id.pointName);

            TextView distance = view.findViewById(R.id.distance);

            TextView position = view.findViewById(R.id.position);

            TextView nav = view.findViewById(R.id.nav);


            String disText;

            if (currentPosition == null) {
                disText = "位置计算中";
            } else {

                if (distance.getTag() != null) {
                    disText = (String) distance.getTag();
                } else {
                    disText = getDistance(point.getLongitude(), point.getLatitude());
                    distance.setTag(disText);
                }
            }
            distance.setText(disText);

            viewHolders.add(distance);


            if (point.isHelp()) {
                nav.setText("去救护");
                setDrawableTop(nav, R.drawable.ic_help);
            } else {
                nav.setText("去这里");
                setDrawableTop(nav, R.drawable.ic_nav);
            }


            position.setText(point.getPosition());

            pointName.setText(point.getLocationName());

            nav.setTag(point);
            nav.setOnClickListener((v) -> {
                routeNav((ILocaPoint) v.getTag());
            });

            positionListView.addView(view);


        }

        for (int i = 0; i < list.size(); i++) {
            ILocaPoint localPoint = list.get(i);
            //定义Maker坐标点
            LatLng point = new LatLng(localPoint.getLatitude(), localPoint.getLongitude());

            //构建Marker图标
            BitmapDescriptor bitmap;
            if (localPoint.isHelp()) {
                bitmap = BitmapDescriptorFactory.fromResource(R.drawable.ic_marker);
            } else {
                bitmap = BitmapDescriptorFactory.fromResource(R.drawable.ic_marker_help);
            }

            //构建MarkerOption，用于在地图上添加Marker
            OverlayOptions option = new MarkerOptions()
                    .position(point)
                    .icon(bitmap);

            //实现自定义类和绑定类的映射
            positionToMarker.put(point, localPoint);
            baiduMap.addOverlay(option);          //在地图上添加Marker，并显示
        }
    }

    private void routeNav(ILocaPoint obj) {
        //进行路线规划
        if (isInstallByread("com.baidu.BaiduMap")) {
            try {
                MapStartParam.invokeBaiduNavi(BasicMapChangeActivity.this, obj.getLatitude(), obj.getLongitude());
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
            Log.e("GasStation", "百度地图客户端已经安装");
            return;
        } else if (isInstallByread("com.autonavi.minimap")) {
            MapStartParam.invokeGaoDeNavi(BasicMapChangeActivity.this, obj.getLatitude(), obj.getLongitude(), obj.getLocationName());
            Log.e("GasStation", "高德地图客户端已经安装");
            return;
        } else if (isInstallByread("com.tencent.map")) {
            MapStartParam.invokeTecentNavi(BasicMapChangeActivity.this, obj.getLocationName(),
                    obj.getLongitude() + "," + obj.getLatitude());
            return;
        } else {
            Toast.makeText(BasicMapChangeActivity.this, "请安装地图软件", Toast.LENGTH_SHORT).show();
        }
    }

    //判断是否安装目标应用
    private boolean isInstallByread(String packageName) {
        return new File("/data/data/" + packageName)
                .exists();
    }


    public void setDrawableTop(TextView view, int drawableId) {
        if (view instanceof TextView) {
            Drawable res = view.getContext().getResources().getDrawable(drawableId);
            res.setBounds(0, 0, res.getMinimumWidth(), res.getMinimumHeight());
            ((TextView) view).setCompoundDrawables(null, res, null, null);
        }
    }


}
