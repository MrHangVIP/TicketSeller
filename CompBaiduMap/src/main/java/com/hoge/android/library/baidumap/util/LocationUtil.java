package com.hoge.android.library.baidumap.util;

import android.content.Context;
import android.text.TextUtils;

import com.baidu.location.Address;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.hoge.android.library.baidumap.bean.CurrentLocationListener;

import java.util.ArrayList;

/**
 * 百度地图定位工具
 * 创建人：wuguojin
 * 时间：17/9/21
 */

public class LocationUtil {

    public static LocationClient mLocClient = null;

    private static ArrayList<CurrentLocationListener> locationListeners = new ArrayList<>();
    private static boolean isLocationRun = false;

    public static void onDestroy(Context c) {
        try {
            if (mLocClient != null) {
                mLocClient.stop();
                mLocClient = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 获取定位信息，包括保存地理信息，错误时候取配置
     *
     * @param c
     * @param listener
     */
    public static void getLocation(Context c, CurrentLocationListener listener) {
        if (isLocationRun) {
            return;
        }
        locationListeners.add(listener);
        isLocationRun = true;
        mLocClient = new LocationClient(c);
        mLocClient.registerLocationListener(new MyBDLocationListener(c));
        // 定位配置
        LocationClientOption option = new LocationClientOption();
        option.setProdName(c.getPackageName());// 设置产品线名称
        option.setTimeOut(10000);// 设置网络超时时间
        option.setLocationMode(LocationClientOption.LocationMode.Battery_Saving);// 可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setCoorType("bd09ll");// 可选，默认gcj02，设置返回的定位结果坐标系
        option.setScanSpan(1000);// 可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        option.setIsNeedAddress(true);// 可选，设置是否需要地址信息，默认不需要
        option.setOpenGps(true);// 可选，默认false,设置是否使用gps
        option.setLocationNotify(true);// 可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
        option.setIsNeedLocationDescribe(true);// 可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        option.setIsNeedLocationPoiList(true);// 可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        option.setIgnoreKillProcess(false);// 可选，默认false，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认杀死
        option.SetIgnoreCacheException(false);// 可选，默认false，设置是否收集CRASH信息，默认收集
        option.setEnableSimulateGps(false);// 可选，默认false，设置是否需要过滤gps仿真结果，默认需要
        mLocClient.setLocOption(option);
        mLocClient.start();// 启动定位sdk
        new android.os.Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                isLocationRun = false;
            }
        }, 10000);
    }

    private static class MyBDLocationListener implements BDLocationListener {
        private Context mContext;

        public MyBDLocationListener(Context mContext) {
            super();
            this.mContext = mContext;
        }

        @Override
        public void onReceiveLocation(BDLocation location) {
            onDestroy(mContext);
            isLocationRun = false;
            if (location == null || location.getAddress() == null
                    || TextUtils.isEmpty(location.getAddress().address)) {
                for (CurrentLocationListener listener : locationListeners) {
                    if (listener != null) {
                        listener.onReceiveLocationFail();
                    }
                }
                //清除监听器
                locationListeners.clear();
                return;
            }
            CommonUtil.set(CommonUtil.VARIABLE_CLASS, "LAT", String.valueOf(location.getLatitude()));
            CommonUtil.set(CommonUtil.VARIABLE_CLASS, "LNG", String.valueOf(location.getLongitude()));

            /** 保存经纬度数据 */
            CommonUtil.getInstance().putIntoSp(mContext,
                    CommonUtil.getString(CommonUtil.CONSTANT_CLASS, "LAT_KEY"),
                    CommonUtil.getString(CommonUtil.VARIABLE_CLASS, "LAT"));
            CommonUtil.getInstance().putIntoSp(mContext,
                    CommonUtil.getString(CommonUtil.CONSTANT_CLASS, "LNG_KEY"),
                    CommonUtil.getString(CommonUtil.VARIABLE_CLASS, "LNG"));
            Address locationAddress = location.getAddress();

            CommonUtil.set(CommonUtil.VARIABLE_CLASS, "LOCATION_COUNTRY", locationAddress.country);
            CommonUtil.set(CommonUtil.VARIABLE_CLASS, "LOCATION_PROVINCE_NAME", locationAddress.province);
            CommonUtil.set(CommonUtil.VARIABLE_CLASS, "LOCATION_CITY_NAME", locationAddress.city);
            CommonUtil.set(CommonUtil.VARIABLE_CLASS, "LOCATION_DISTRICT_NAME", locationAddress.district);
            CommonUtil.set(CommonUtil.VARIABLE_CLASS, "LOCATION_STREET", TextUtils.isEmpty(locationAddress.street) ? ""
                    : locationAddress.street);

            /** 获得详细地址不包含省，市，区县 */
            String address = locationAddress.address;
            try {
                if (!TextUtils.isEmpty(address)) {
                    address = address.contains(CommonUtil.getString(CommonUtil.VARIABLE_CLASS, "LOCATION_COUNTRY")) ? address
                            .replace(CommonUtil.getString(CommonUtil.VARIABLE_CLASS, "LOCATION_COUNTRY"), "") : address;
                    CommonUtil.set(CommonUtil.VARIABLE_CLASS, "LOCATION_ADDRESS", address);

                    address = address.contains(CommonUtil.getString(CommonUtil.VARIABLE_CLASS, "LOCATION_PROVINCE_NAME")) ? address
                            .replace(CommonUtil.getString(CommonUtil.VARIABLE_CLASS, "LOCATION_PROVINCE_NAME"), "")
                            : address;
                    address = address.contains(CommonUtil.getString(CommonUtil.VARIABLE_CLASS, "LOCATION_CITY_NAME")) ? address
                            .replace(CommonUtil.getString(CommonUtil.VARIABLE_CLASS, "LOCATION_CITY_NAME"), "") : address;
                    address = address.contains(CommonUtil.getString(CommonUtil.VARIABLE_CLASS, "LOCATION_DISTRICT_NAME")) ? address
                            .replace(CommonUtil.getString(CommonUtil.VARIABLE_CLASS, "LOCATION_DISTRICT_NAME"), "")
                            : address;
                    CommonUtil.set(CommonUtil.VARIABLE_CLASS, "LOCATION_SHORT_ADDRESS", address);
                }
            } catch (Exception e) {
                CommonUtil.set(CommonUtil.VARIABLE_CLASS, "LOCATION_ADDRESS", "");
                CommonUtil.set(CommonUtil.VARIABLE_CLASS, "LOCATION_SHORT_ADDRESS", "");
            }
            for (CurrentLocationListener listener : locationListeners) {
                if (listener != null) {
                    listener.onReceiveLocationSuccess(location);
                }
            }
            //清除监听器
            locationListeners.clear();
        }
    }
}
