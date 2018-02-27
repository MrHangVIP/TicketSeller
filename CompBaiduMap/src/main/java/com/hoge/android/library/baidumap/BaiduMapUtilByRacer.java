package com.hoge.android.library.baidumap;

import android.text.TextUtils;

import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeOption;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.hoge.android.library.baidumap.bean.LocationBean;
import com.hoge.android.library.baidumap.util.CommonUtil;

import java.util.List;

/**
 * @author wuguojin
 * @ClassName: BaiduMapUtilByRacer
 * @Description: 百度工具类
 * @category 目前使用在围观及社区活动发布界面选择位置
 */
public class BaiduMapUtilByRacer {

    public static GeoCodeListener mGeoCodeListener = null;

    public interface GeoCodeListener {
        /**
         * 地理信息获取成功
         *
         * @param locationBean
         */
        void onGetSucceed(LocationBean locationBean);

        /**
         * 地理信息获取失败
         */
        void onGetFailed();
    }

    public static GeoCodePoiListener mGeoCodePoiListener = null;

    public interface GeoCodePoiListener {
        /**
         * 周边兴趣点获取成功
         *
         * @param locationBean
         * @param poiList
         */
        void onGetSucceed(LocationBean locationBean, List<PoiInfo> poiList);

        /**
         * 获取失败
         */
        void onGetFailed();
    }

    public static GeoCoder mGeoCoder = null;

    /**
     * @param lat
     * @param lon
     * @param listener
     * @return void
     * @throws
     * @Title: getPoiByGeoCode
     * @Description: 根据经纬度获取周边热点名
     */
    public static void getPoisByGeoCode(double lat, double lon,
                                        GeoCodePoiListener listener) {
        mGeoCodePoiListener = listener;
        if (mGeoCoder == null) {
            mGeoCoder = GeoCoder.newInstance();
        }
        mGeoCoder.setOnGetGeoCodeResultListener(new MyGeoCodeListener());
        // 反Geo搜索
        mGeoCoder.reverseGeoCode(new ReverseGeoCodeOption()
                .location(new LatLng(lat, lon)));
    }

    /**
     * @param address  搜索某一地址
     * @param listener
     */
    public static void getPoisByGeoCode(String address,
                                        GeoCodePoiListener listener) {
        mGeoCodePoiListener = listener;
        if (mGeoCoder == null) {
            mGeoCoder = GeoCoder.newInstance();
        }
        mGeoCoder.setOnGetGeoCodeResultListener(new MyGeoCodeListener());
        String cityNameResult = CommonUtil.getString(CommonUtil.VARIABLE_CLASS, "CITY_NAME");
        if (!TextUtils.isEmpty(cityNameResult)) {
            mGeoCoder.geocode(new GeoCodeOption().city(cityNameResult).address(address));
        }
    }

    /**
     * @ClassName: MyGeoCodeListener
     * @Description: geo搜索的回掉
     */
    public static class MyGeoCodeListener implements
            OnGetGeoCoderResultListener {

        @Override
        public void onGetGeoCodeResult(GeoCodeResult result) {
            if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
                if (mGeoCodeListener != null) {
                    mGeoCodeListener.onGetFailed();
                }
                if (mGeoCodePoiListener != null) {
                    mGeoCodePoiListener.onGetFailed();
                }
                destroyGeoCode();
                return;
            }
            // 反Geo搜索
            mGeoCoder.reverseGeoCode(new ReverseGeoCodeOption().location(result
                    .getLocation()));
        }

        @Override
        public void onGetReverseGeoCodeResult(ReverseGeoCodeResult result) {
            if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
                if (mGeoCodeListener != null) {
                    mGeoCodeListener.onGetFailed();
                }
                if (mGeoCodePoiListener != null) {
                    mGeoCodePoiListener.onGetFailed();
                }
                destroyGeoCode();
                return;
            }
            LocationBean mLocationBean = new LocationBean();
            mLocationBean.setCity(result.getAddressDetail().city);
            mLocationBean.setAddress(result.getAddress());
            mLocationBean.setLatitude(result.getLocation().latitude);
            mLocationBean.setLongitude(result.getLocation().longitude);
            if (mGeoCodeListener != null) {
                mGeoCodeListener.onGetSucceed(mLocationBean);
            }
            if (mGeoCodePoiListener != null) {
                mGeoCodePoiListener.onGetSucceed(mLocationBean,
                        result.getPoiList());
            }
            destroyGeoCode();
        }
    }

    /**
     * @return void
     * @throws
     * @Title: destroyGeoCode
     * @Description:销毁及置空geo搜索相关对象
     */
    public static void destroyGeoCode() {
        if (mGeoCoder != null) {
            mGeoCoder.destroy();
            mGeoCoder = null;
        }
        mGeoCodeListener = null;
        mGeoCodePoiListener = null;
    }
}
