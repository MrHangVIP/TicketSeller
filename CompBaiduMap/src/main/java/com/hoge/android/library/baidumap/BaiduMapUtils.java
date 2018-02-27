package com.hoge.android.library.baidumap;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;
import android.widget.ZoomControls;

import com.baidu.location.BDLocation;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BaiduMap.OnMapClickListener;
import com.baidu.mapapi.map.BaiduMap.OnMarkerClickListener;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.InfoWindow.OnInfoWindowClickListener;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MarkerOptions.MarkerAnimateType;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationConfiguration.LocationMode;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.UiSettings;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.overlayutil.DrivingRouteOverlay;
import com.baidu.mapapi.overlayutil.MarkViewInterface;
import com.baidu.mapapi.overlayutil.TransitRouteOverlay;
import com.baidu.mapapi.overlayutil.WalkingRouteOverlay;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.route.DrivingRouteResult;
import com.baidu.mapapi.search.route.TransitRouteResult;
import com.baidu.mapapi.search.route.WalkingRouteResult;
import com.hoge.android.library.baidumap.bean.CurrentLocationListener;
import com.hoge.android.library.baidumap.util.CommonUtil;
import com.hoge.android.library.baidumap.util.LocationUtil;

import java.util.ArrayList;
import java.util.Map;

/**
 * 百度地图工具
 * 在使用百度地图之前必须初始化该实例
 * 创建人：wuguojin
 * 时间：17/9/23
 */
public class BaiduMapUtils {

    private Context mContext;
    // 地图组件
    private MapView mMapView;
    private BaiduMap mBaiduMap;
    private UiSettings mUiSettings;

    // 是否开启 正在定位文字显示
    private boolean openToastShow = false;
    private LocationMode mCurrentMode;
    // 地图是否聚焦到第一个覆盖物
    private boolean isMapFocus = true;

    private ArrayList<BitmapDescriptor> bdIcons;
    private BitmapDescriptor mapIcon;

    private int lineColor = 0;

    private int lineWidth = 0;

    private boolean showNodeIcon = true;

    private BitmapDescriptor startMark = null;

    private BitmapDescriptor endMark = null;

    private MarkViewInterface markViewInterface = null;

    /**
     * 百度地图最大缩放级别
     */
    private static final int MAX_LEVEL = 21;
    /**
     * 各级比例尺分母值数组
     */
    private static final int[] SCALES = {5, 10, 20, 50, 100, 200, 500, 1000,
            2000, 5000, 10000, 20000, 25000, 50000, 100000, 200000, 500000,
            1000000, 2000000};

    /**
     * 获取地图工具类实例
     *
     * @param mMapView
     * @return
     */
    public static BaiduMapUtils getInstance(MapView mMapView) {
        return new BaiduMapUtils(mMapView);
    }

    /**
     * 带地图控件的构造器
     *
     * @param mMapView
     */
    public BaiduMapUtils(MapView mMapView) {
        this.mMapView = mMapView;
        mContext = mMapView.getContext().getApplicationContext();
        mMapView.showZoomControls(false);// 设置是否显示缩放控件
        mCurrentMode = LocationMode.NORMAL;// 普通样式NORMAL；跟随样式FOLLOWING；罗盘样式COMPASS
        mBaiduMap = mMapView.getMap();
        mUiSettings = mBaiduMap.getUiSettings();
        mUiSettings.setOverlookingGesturesEnabled(false);// 俯视效果
        mBaiduMap.setBuildingsEnabled(false);// 楼快效果
        initBaseMap();
        initLocationMarker();
    }

    // 南京默认118.802962,32.064792
    private void initBaseMap() {
        // 设置缩放
        zoomTo(16);
        setMaxAndMinZoomLevel(MAX_LEVEL, 3);
        setBuildingsEnabled(true);
        // 隐藏原生按钮
        int count = mMapView.getChildCount();
        for (int i = 0; i < count; i++) {
            View child = mMapView.getChildAt(i);
            if (child instanceof ZoomControls) {
                child.setVisibility(View.INVISIBLE);
            }
        }

        double latitude = 32.064792;
        double longitude = 118.802962;
        try {
            Object getLatitudeResult = CommonUtil.invokeByStaticMethod(CommonUtil.CONFIGURATION_CLASS, "getMultiValue",
                    new Class[]{Map.class, String.class, String.class},
                    new Object[]{CommonUtil.getStaticField(CommonUtil.CONFIGURATION_CLASS, "baseset_map"), "city_latitude", "32.064792"});
            if (null != getLatitudeResult && getLatitudeResult instanceof String) {
                latitude = Double.valueOf((String) getLatitudeResult);
            }
            Object getLongitutdeResult = CommonUtil.invokeByStaticMethod(CommonUtil.CONFIGURATION_CLASS, "getMultiValue",
                    new Class[]{Map.class, String.class, String.class},
                    new Object[]{CommonUtil.getStaticField(CommonUtil.CONFIGURATION_CLASS, "baseset_map"), "city_longitude", "118.802962"});
            if (null != getLongitutdeResult && getLongitutdeResult instanceof String) {
                longitude = Double.valueOf((String) getLongitutdeResult);
            }

        } catch (Exception e) {
        }
        LatLng ll = new LatLng(latitude, longitude);
        MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(ll);
        mBaiduMap.animateMapStatus(u);

        mBaiduMap.setOnMapClickListener(new OnMapClickListener() {
            @Override
            public boolean onMapPoiClick(MapPoi arg0) {
                return false;
            }

            @Override
            public void onMapClick(LatLng arg0) {
                hideInfoWindow();
            }
        });
    }

    /**
     * 定位图标样式
     */
    private void initLocationMarker() {
        mBaiduMap.setMyLocationConfiguration(new MyLocationConfiguration(
                mCurrentMode, true, null));
    }

    /**
     * 自定义图标样式，及模式
     *
     * @param mode
     * @param icon
     */
    public void setCustomLocationMarker(LocationMode mode, BitmapDescriptor icon) {
        mBaiduMap.setMyLocationConfiguration(new MyLocationConfiguration(mode,
                true, icon));
    }

    /**
     * 根据缩放级别，得到对应比例尺在SCALES数组中的位置（索引）
     *
     * @param zoomLevel
     * @return
     */
    private static int getScaleIndex(int zoomLevel) {
        return MAX_LEVEL - zoomLevel;
    }

    /******************************************** 公共方法 ********************************************/

    /**
     * 根据缩放级别，得到对应比例尺,根据28px=1cm，获得屏幕下可见范围直径
     *
     * @param zoomLevel
     * @return
     */
    public int getScale(int zoomLevel) {
        return SCALES[getScaleIndex(zoomLevel)];
    }

    /**
     * 设置是否允许所有手势操作
     */
    public void setAllGesturesEnabled(boolean isEnable) {
        mUiSettings.setAllGesturesEnabled(isEnable);
    }

    /**
     * 实时路况 true为开启
     */
    public void openNowMap(boolean isOpen) {
        mBaiduMap.setTrafficEnabled(isOpen);
    }

    public void setMaxAndMinZoomLevel(int maxlevel, int minlevel) {
        mBaiduMap.setMaxAndMinZoomLevel(maxlevel, minlevel);
    }

    /**
     * 设置当前比例尺，缩放大小
     *
     * @param level
     */
    public void zoomTo(int level) {
        MapStatusUpdate msu = MapStatusUpdateFactory.zoomTo(level);
        mBaiduMap.setMapStatus(msu);
    }

    /**
     * 放大地图
     */
    public void mapZoomIn() {
        MapStatusUpdate status = MapStatusUpdateFactory.zoomIn();
        mBaiduMap.setMapStatus(status);
    }

    /**
     * 缩小地图
     */
    public void mapZoomOut() {
        MapStatusUpdate status = MapStatusUpdateFactory.zoomOut();
        mBaiduMap.setMapStatus(status);
    }

    /**
     * 是否启用缩放手势
     *
     * @param flag
     */
    public void setZoomEnable(boolean flag) {
        mUiSettings.setZoomGesturesEnabled(flag);
    }

    /**
     * 是否启用平移手势
     *
     * @param flag
     */
    public void setScrollEnable(boolean flag) {
        mUiSettings.setScrollGesturesEnabled(flag);
    }

    /**
     * 是否启用旋转手势
     *
     * @param flag
     */
    public void setRotateEnable(boolean flag) {
        mUiSettings.setRotateGesturesEnabled(flag);
    }

    /**
     * 是否启用俯视手势
     *
     * @param flag
     */
    public void setOverlookEnable(boolean flag) {
        mUiSettings.setOverlookingGesturesEnabled(flag);
    }

    /**
     * 设置是否允许楼块效果
     *
     * @param flag
     */
    public void setBuildingsEnabled(boolean flag) {
        mBaiduMap.setBuildingsEnabled(flag);
    }

    /**
     * 是否启用指南针图层
     *
     * @param flag
     */
    public void setCompassEnable(boolean flag) {
        mUiSettings.setCompassEnabled(flag);
    }

    /**
     * 获取BaiduMap实例
     */
    public BaiduMap getBaiduMap() {
        return mBaiduMap;
    }

    /**
     * 显示弹出框
     */
    public void showInfoWindow(View view, LatLng llInfo,
                               OnInfoWindowClickListener mOnInfoWindowClickListener) {
        showInfoWindow(BitmapDescriptorFactory.fromView(view), llInfo,
                mOnInfoWindowClickListener);
    }

    /**
     * 显示弹出框 解决点击覆盖物里的图片点击两次响应
     */
    public void showInfoWindow(View view, LatLng llInfo) {
        showInfoWindow(BitmapDescriptorFactory.fromView(view), llInfo, null);
    }

    /**
     * 显示弹出框
     */
    public void showInfoWindow(BitmapDescriptor bd, LatLng llInfo,
                               OnInfoWindowClickListener mOnInfoWindowClickListener) {
        InfoWindow mInfoWindow = new InfoWindow(bd, llInfo, -47,
                mOnInfoWindowClickListener);
        mBaiduMap.showInfoWindow(mInfoWindow);
    }

    /**
     * 清楚所有覆盖物
     */
    public void clear() {
        mBaiduMap.clear();
    }

    /**
     * 设置地图点击事件
     *
     * @param mMapTouchListener
     */
    public void setMapTouchListener(final MapTouchListener mMapTouchListener) {
        if (mMapTouchListener == null) {
            return;
        }
        mBaiduMap.setOnMapStatusChangeListener(new BaiduMap.OnMapStatusChangeListener() {

            @Override
            public void onMapStatusChangeStart(MapStatus arg0) {
                // 地图拖动开始
                mMapTouchListener.touchMapStart(arg0);
            }

            @Override
            public void onMapStatusChangeStart(MapStatus mapStatus, int i) {

            }

            @Override
            public void onMapStatusChange(MapStatus arg0) {
                // 地图拖动
                mMapTouchListener.touchMaping(arg0);
            }

            @Override
            public void onMapStatusChangeFinish(MapStatus arg0) {
                // 地图拖动结束
                mMapTouchListener.touchMapFinish(arg0);
            }
        });

    }

    /**
     * 隐藏弹出框
     */
    public void hideInfoWindow() {
        mBaiduMap.hideInfoWindow();
    }

    /**
     * 定位图层
     */
    public void getLocation() {
        getLocation(null);
    }

    public void getLocation(LocationListener mLocationListener) {
        getLocation(true, mLocationListener);
    }

    public void getLocation(final boolean autoFocus,
                            final LocationListener mLocationListener) {
        if (openToastShow) {
            showToast("正在定位...");
        }
        // 开启定位图层
        mBaiduMap.setMyLocationEnabled(true);
        // 定位初始化
        LocationUtil.getLocation(mContext, new CurrentLocationListener() {

            @Override
            public void onReceiveLocationSuccess(BDLocation location) {
                MyLocationData locData = new MyLocationData.Builder()
                        .accuracy(location.getRadius())
                        // 此处设置开发者获取到的方向信息，顺时针0-360
                        .direction(100).latitude(location.getLatitude())
                        .longitude(location.getLongitude()).build();
                mBaiduMap.setMyLocationData(locData);
                if (autoFocus) {
                    LatLng ll = new LatLng(location.getLatitude(), location
                            .getLongitude());
                    MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(ll);
                    mBaiduMap.animateMapStatus(u);
                }
                if (mLocationListener != null) {
                    mLocationListener.getLocationSuccess(location);
                }
            }

            @Override
            public void onReceiveLocationFail() {

            }

        });
    }

    /**
     * 设置地图增加覆盖物之后是否聚焦到第一个点
     */
    public void setMapViewFocus(boolean isMapFocus) {
        this.isMapFocus = isMapFocus;
    }

    /**
     * 设置定位中---正在定位文字是否显示
     *
     * @param openToastShow
     */
    public void setOpenToastShow(boolean openToastShow) {
        this.openToastShow = openToastShow;
    }

    /**
     * 聚焦到某个点
     */
    public void animateMapStatus(double latitude, double longitude) {
        LatLng ll = new LatLng(latitude, longitude);
        animateMapStatus(ll);
    }

    /**
     * 聚焦到某个点
     */
    public void animateMapStatus(LatLng ll) {
        MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(ll);
        mBaiduMap.animateMapStatus(u);
    }

    public Marker addSingleMapMarker(String lat, String log,
                                     BitmapDescriptor mapIcon,
                                     OnMarkerClickListener mOnMarkerClickListener) {
        return addSingleMapMarker(lat, log, mapIcon, MarkerAnimateType.grow,
                mOnMarkerClickListener);
    }

    /**
     * 增加单个覆盖物，如果有点击事件需要实现showInfoWindow()方法，可以参考自行车，路况模块
     *
     * @param lat
     * @param log
     * @param mapIcon
     * @param mOnMarkerClickListener
     */
    public Marker addSingleMapMarker(String lat, String log,
                                     BitmapDescriptor mapIcon, MarkerAnimateType animType,
                                     OnMarkerClickListener mOnMarkerClickListener) {
        this.mapIcon = mapIcon;
        if (mBaiduMap == null || TextUtils.isEmpty(lat)
                || TextUtils.isEmpty(log) || mapIcon == null) {
            return null;
        }
        LatLng geo = new LatLng(Double.parseDouble(lat),
                Double.parseDouble(log));
        MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(geo);
        if (u == null) {
            return null;
        }
        mBaiduMap.animateMapStatus(u);
        mBaiduMap.clear();
        MarkerOptions ooB = new MarkerOptions().position(geo).icon(mapIcon)
                .period(1).zIndex(1).animateType(animType);
        Marker marker = (Marker) mBaiduMap.addOverlay(ooB);
        if (mOnMarkerClickListener != null) {
            mBaiduMap.setOnMarkerClickListener(mOnMarkerClickListener);
        }
        return marker;
    }

    /**
     * 增加多个覆盖物，如果有点击事件需要实现showInfoWindow()方法，可以参考自行车，路况模块
     *
     * @param geolist                坐标列表
     * @param bundles                mOnMarkerClickListener
     * @param bdIcons                对应的view的列表
     * @param mOnMarkerClickListener 监听
     * @return
     */
    public ArrayList<Marker> addMultiMapMarker(ArrayList<LatLng> geolist,
                                               ArrayList<Bundle> bundles, ArrayList<BitmapDescriptor> bdIcons,
                                               OnMarkerClickListener mOnMarkerClickListener) {
        return addMultiMapMarker(0, geolist, bundles, bdIcons,
                MarkerAnimateType.grow, mOnMarkerClickListener);
    }

    public ArrayList<Marker> addMultiMapMarker(int mIndex,
                                               ArrayList<LatLng> geolist, ArrayList<Bundle> bundles,
                                               ArrayList<BitmapDescriptor> bdIcons, MarkerAnimateType animType,
                                               OnMarkerClickListener mOnMarkerClickListener) {
        return addMultiMapMarker(mIndex, true, geolist, bundles, bdIcons,
                animType, mOnMarkerClickListener);
    }

    /**
     * /** 增加多个覆盖物，如果有点击事件需要实现showInfoWindow()方法，可以参考自行车，路况模块
     *
     * @param mIndex                 当前的选中标记
     * @param geolist                坐标列表
     * @param bundles                对象列表
     * @param bdIcons                对应的view的列表
     * @param animType               动画类型
     * @param mOnMarkerClickListener 监听
     * @return
     */
    public ArrayList<Marker> addMultiMapMarker(int mIndex, boolean autoClear,
                                               ArrayList<LatLng> geolist, ArrayList<Bundle> bundles,
                                               ArrayList<BitmapDescriptor> bdIcons, MarkerAnimateType animType,
                                               OnMarkerClickListener mOnMarkerClickListener) {
        if (geolist == null || geolist.size() == 0 || bundles == null
                || bundles.size() == 0 || bdIcons == null
                || bdIcons.size() == 0) {
            return null;
        }
        LatLng firstll = null;
        this.bdIcons = bdIcons;
        if (mBaiduMap != null && autoClear) {
            mBaiduMap.clear();
        }
        ArrayList<Marker> markers = new ArrayList<Marker>();
        for (int i = 0, size = geolist.size(); i < size; i++) {
            LatLng geo = geolist.get(i);
            Bundle bundle = bundles.get(i);
            BitmapDescriptor markicon = bdIcons.get(i);
            MarkerOptions ooB = new MarkerOptions().position(geo)
                    .icon(markicon).zIndex(i).extraInfo(bundle).period(1)
                    .animateType(animType);
            Marker marker = (Marker) mBaiduMap.addOverlay(ooB);
            markers.add(marker);
            if (i == mIndex) {
                firstll = geo;
            }
        }
        if (isMapFocus && firstll != null) {
            MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(firstll);
            mBaiduMap.setMapStatus(u);
        }
        if (mOnMarkerClickListener != null) {
            mBaiduMap.setOnMarkerClickListener(mOnMarkerClickListener);
        }
        return markers;
    }

    /**
     * 在地图上展示路径规划结果
     *
     * @param searchResult
     */
    public void showRouteResultOnMap(SearchResult searchResult) {
        if (mBaiduMap == null) {
            return;
        }
        if (searchResult instanceof WalkingRouteResult) {
            // 步行路径结果
            WalkingRouteOverlay overlay = new MyWalkingRouteOverlay(mBaiduMap);
            mBaiduMap.setOnMarkerClickListener(overlay);
            overlay.setData(((WalkingRouteResult) searchResult).getRouteLines().get(0));
            overlay.addToMap();
            overlay.zoomToSpan();
        } else if (searchResult instanceof TransitRouteResult) {
            TransitRouteOverlay overlay = new MyTransitRouteOverlay(mBaiduMap);
            mBaiduMap.setOnMarkerClickListener(overlay);
            overlay.setData(((TransitRouteResult) searchResult).getRouteLines().get(0));
            overlay.addToMap();
            overlay.zoomToSpan();
        } else if (searchResult instanceof DrivingRouteResult) {
            DrivingRouteOverlay overlay = new MyDrivingRouteOverlay(mBaiduMap);
            mBaiduMap.setOnMarkerClickListener(overlay);
            overlay.setData(((DrivingRouteResult) searchResult).getRouteLines().get(0));
            overlay.addToMap();
            overlay.zoomToSpan();
        }
    }


    /**
     * 地图onPause
     */
    public void onPause() {
        if (mMapView != null) {
            mMapView.onPause();
        }
    }

    /**
     * 地图onResume
     */
    public void onResume() {
        if (mMapView != null) {
            mMapView.onResume();
        }
    }

    /**
     * 退出时候需要调用该方法
     */
    public void onDestroy() {
        if (mBaiduMap != null) {
            // 关闭定位图层
            mBaiduMap.setMyLocationEnabled(false);
        }
        if (mMapView != null) {
            mMapView.onDestroy();
            mMapView = null;
        }
        if (mapIcon != null) {
            mapIcon.recycle();
            mapIcon = null;
        }
        if (bdIcons != null) {
            for (BitmapDescriptor icon : bdIcons) {
                icon.recycle();
            }
            bdIcons.clear();
        }
    }

    private class MyDrivingRouteOverlay extends DrivingRouteOverlay {

        public MyDrivingRouteOverlay(BaiduMap baiduMap) {
            super(baiduMap);
        }

        @Override
        public BitmapDescriptor getStartMarker() {
            return startMark;
        }

        @Override
        public BitmapDescriptor getTerminalMarker() {
            return endMark;
        }

        @Override
        public int getLineColor() {
            return lineColor;
        }

        @Override
        public int getLineWidth() {
            return lineWidth;
        }

        @Override
        public boolean showNodeIcon() {
            return showNodeIcon;
        }

        @Override
        public MarkViewInterface getMarkViewInterface() {
            return markViewInterface;
        }
    }

    private class MyWalkingRouteOverlay extends WalkingRouteOverlay {

        public MyWalkingRouteOverlay(BaiduMap baiduMap) {
            super(baiduMap);
        }

        @Override
        public BitmapDescriptor getStartMarker() {
            return startMark;
        }

        @Override
        public BitmapDescriptor getTerminalMarker() {
            return endMark;
        }

        @Override
        public int getLineColor() {
            return lineColor;
        }

        @Override
        public int getLineWidth() {
            return lineWidth;
        }

        @Override
        public boolean showNodeIcon() {
            return showNodeIcon;
        }

        @Override
        public MarkViewInterface getMarkViewInterface() {
            return markViewInterface;
        }
    }

    private class MyTransitRouteOverlay extends TransitRouteOverlay {

        public MyTransitRouteOverlay(BaiduMap baiduMap) {
            super(baiduMap);
        }

        @Override
        public BitmapDescriptor getStartMarker() {
            return startMark;
        }

        @Override
        public BitmapDescriptor getTerminalMarker() {
            return endMark;
        }

        @Override
        public int getLineColor() {
            return lineColor;
        }

        @Override
        public int getLineWidth() {
            return lineWidth;
        }

        @Override
        public boolean showNodeIcon() {
            return showNodeIcon;
        }

        @Override
        public MarkViewInterface getMarkViewInterface() {
            return markViewInterface;
        }
    }

    public void showToast(String msg) {
        Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
    }


    /**
     * 自定义路径规划
     *
     * @param lineColor
     */
    public void setLineColor(int lineColor) {
        this.lineColor = lineColor;
    }

    public void setLineWidth(int lineWidth) {
        this.lineWidth = lineWidth;
    }

    public void setShowNodeIcon(boolean showNodeIcon) {
        this.showNodeIcon = showNodeIcon;
    }

    public void setStartMark(BitmapDescriptor startMark) {
        this.startMark = startMark;
    }

    public void setEndMark(BitmapDescriptor endMark) {
        this.endMark = endMark;
    }

    /**
     * 设置 路线规划中的mark的点击事件，并且自定义提示内容
     *
     * @param markViewInterface
     */
    public void setMarkViewInterface(MarkViewInterface markViewInterface) {
        this.markViewInterface = markViewInterface;
    }

    public interface LocationListener {
        void getLocationSuccess(BDLocation location);
    }

    public interface MapTouchListener {
        /**
         * 开始触摸监听
         *
         * @param mapStatus
         */
        void touchMapStart(MapStatus mapStatus);

        /**
         * 触摸监听
         *
         * @param mapStatus
         */
        void touchMaping(MapStatus mapStatus);

        /**
         * 结束触摸监听
         *
         * @param mapStatus
         */
        void touchMapFinish(MapStatus mapStatus);
    }

}
