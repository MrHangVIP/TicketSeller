package com.hoge.android.library.baidumap;

import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.busline.BusLineResult;
import com.baidu.mapapi.search.busline.BusLineSearch;
import com.baidu.mapapi.search.busline.BusLineSearchOption;
import com.baidu.mapapi.search.busline.OnGetBusLineSearchResultListener;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.district.DistrictResult;
import com.baidu.mapapi.search.district.DistrictSearch;
import com.baidu.mapapi.search.district.DistrictSearchOption;
import com.baidu.mapapi.search.district.OnGetDistricSearchResultListener;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiCitySearchOption;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiIndoorResult;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.baidu.mapapi.search.route.BikingRouteResult;
import com.baidu.mapapi.search.route.DrivingRoutePlanOption;
import com.baidu.mapapi.search.route.DrivingRouteResult;
import com.baidu.mapapi.search.route.IndoorRouteResult;
import com.baidu.mapapi.search.route.MassTransitRouteResult;
import com.baidu.mapapi.search.route.OnGetRoutePlanResultListener;
import com.baidu.mapapi.search.route.PlanNode;
import com.baidu.mapapi.search.route.TransitRoutePlanOption;
import com.baidu.mapapi.search.route.TransitRouteResult;
import com.baidu.mapapi.search.route.WalkingRoutePlanOption;
import com.baidu.mapapi.search.route.WalkingRouteResult;
import com.baidu.mapapi.search.sug.OnGetSuggestionResultListener;
import com.baidu.mapapi.search.sug.SuggestionResult;
import com.baidu.mapapi.search.sug.SuggestionSearch;
import com.baidu.mapapi.search.sug.SuggestionSearchOption;
import com.hoge.android.library.baidumap.util.CommonUtil;

/**
 * 百度搜索工具类
 * <p>
 * 创建人：wuguojin
 * 时间：17/11/30
 */

public class BaiduSearchUtil {

    private static BaiduSearchUtil mSearchInstance;
    private Context mContext;
    private BaiduMapUtils mMapUtil;

    // 路径规划搜索
    private MyRoutePlanSearch mRoutePlanSearch = null;
    // 兴趣点搜索
    private PoiSearch mPoiSearch = null;
    // 搜索框智能提示
    private SuggestionSearch mSuggestionSearch = null;
    // 坐标反向解析地理信息
    private GeoCoder mGeoCoderSearch = null;
    // 公交线路搜索引擎
    private BusLineSearch mBusLineSearch = null;
    // 行政区搜索引擎
    private DistrictSearch mDistrictSearch = null;

    /**
     * 实例化对象
     *
     * @param mContext
     * @return BaiduSearchUtil
     */
    public static BaiduSearchUtil getInstance(Context mContext) {
        if (mSearchInstance == null) {
            mSearchInstance = new BaiduSearchUtil(mContext);
        }
        return mSearchInstance;
    }

    BaiduSearchUtil(Context context) {
        mContext = context;
    }

    /**
     * 设置地图工具类的实例
     * 如果需要在地图上同时显示搜索结果，则需要初始化地图工具类并传入
     *
     * @param mapUtil
     */
    public void setMapUtilToSearch(BaiduMapUtils mapUtil) {
        mMapUtil = mapUtil;
    }

    /**
     * 初始化路径规划
     *
     * @param mRoutePlanListener
     */
    public void initRoutePlan(final boolean isShowResultOnMap, final SearchRoutePlanListener mRoutePlanListener) {
        if (mContext == null) {
            return;
        }
        mRoutePlanSearch = MyRoutePlanSearch.newInstance();
        mRoutePlanSearch.setOnGetRoutePlanResultListener(new OnGetRoutePlanResultListener() {

            @Override
            public void onGetWalkingRouteResult(WalkingRouteResult result) {
                if (mRoutePlanListener != null) {
                    mRoutePlanListener.getWalkingRouteResult(result);
                }
                if (isShowResultOnMap && mMapUtil != null) {
                    if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
                        showToast(mContext.getString(R.string.route_search_empty));
                        return;
                    }
                    mMapUtil.showRouteResultOnMap(result);
                }
            }

            @Override
            public void onGetTransitRouteResult(TransitRouteResult result) {
                if (mRoutePlanListener != null) {
                    mRoutePlanListener.getTransitRouteResult(result);
                }
                if (isShowResultOnMap && mMapUtil != null) {
                    if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
                        showToast(mContext.getString(R.string.route_search_empty));
                        return;
                    }
                    mMapUtil.showRouteResultOnMap(result);
                }
            }

            @Override
            public void onGetMassTransitRouteResult(MassTransitRouteResult massTransitRouteResult) {

            }

            @Override
            public void onGetDrivingRouteResult(DrivingRouteResult result) {
                if (mRoutePlanListener != null) {
                    mRoutePlanListener.getDrivingRouteResult(result);
                }
                if (isShowResultOnMap && mMapUtil != null) {
                    if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
                        showToast(mContext.getString(R.string.route_search_empty));
                        return;
                    }
                    mMapUtil.showRouteResultOnMap(result);
                }
            }

            @Override
            public void onGetIndoorRouteResult(IndoorRouteResult indoorRouteResult) {

            }

            @Override
            public void onGetBikingRouteResult(BikingRouteResult bikingRouteResult) {

            }

        });
    }


    /**
     * 获取路径规划路线 调用之前需要调用initRoutePlan()
     *
     * @param tlat
     * @param tlog
     * @param routetype 0为步行；1为公交；2为自驾
     */
    public void getRoutePlan(String tlat, String tlog, int routetype) {
        if (mContext == null || mRoutePlanSearch == null) {
            return;
        }
        if (TextUtils.isEmpty(CommonUtil.getString(CommonUtil.VARIABLE_CLASS, "LAT"))
                || TextUtils.isEmpty(CommonUtil.getString(CommonUtil.VARIABLE_CLASS, "LNG"))
                || TextUtils.isEmpty(tlat) || TextUtils.isEmpty(tlog)) {
            showToast(mContext.getString(R.string.wrong_location_lat));
            return;
        }
        LatLng mylocation = new LatLng(Double.parseDouble(CommonUtil.getString(CommonUtil.VARIABLE_CLASS, "LAT")),
                Double.parseDouble(CommonUtil.getString(CommonUtil.VARIABLE_CLASS, "LNG")));
        LatLng endlocation = new LatLng(Double.valueOf(tlat), Double.valueOf(tlog));
        PlanNode stNode = PlanNode.withLocation(mylocation);
        PlanNode enNode = PlanNode.withLocation(endlocation);
        getRoutePlan(stNode, enNode, routetype);
    }

    /**
     * 获取路径规划路线 调用之前需要调用initRoutePlan()
     *
     * @param stNode
     * @param enNode
     * @param routetype 0为步行；1为公交；2为自驾
     */
    public void getRoutePlan(PlanNode stNode, PlanNode enNode, int routetype) {
        if (mContext == null || mRoutePlanSearch == null) {
            return;
        }
        String cityName = CommonUtil.getString(CommonUtil.VARIABLE_CLASS, "CITY_NAME");
        ;
        TransitRoutePlanOption.TransitPolicy policy = TransitRoutePlanOption.TransitPolicy.EBUS_TIME_FIRST; //默认
        getRoutePlan(stNode, enNode, routetype, cityName, policy);
    }

    /**
     * 获取路径规划路线 调用之前需要调用initRoutePlan()
     *
     * @param stNode
     * @param enNode
     * @param routetype routetype 0为步行；1为公交；2为自驾
     * @param cityname
     * @param policy
     */
    public void getRoutePlan(PlanNode stNode, PlanNode enNode, int routetype, String cityname, TransitRoutePlanOption.TransitPolicy policy) {
        if (mContext == null || mRoutePlanSearch == null) {
            return;
        }
        if (stNode == null || enNode == null) {
            showToast(mContext.getString(R.string.wrong_location_lat));
            return;
        }
        switch (routetype) {
            case 0:
                mRoutePlanSearch.walkingSearch((new WalkingRoutePlanOption()).from(stNode).to(enNode));
                break;
            case 1:
                mRoutePlanSearch.transitSearch(new TransitRoutePlanOption().city(cityname).from(stNode).to(enNode).policy(policy));
                break;
            case 2:
                mRoutePlanSearch.drivingSearch(new DrivingRoutePlanOption().currentCity(cityname).from(stNode).to(enNode).policy(DrivingRoutePlanOption.DrivingPolicy.ECAR_FEE_FIRST));
                break;
            default:
                break;
        }
        if (mMapUtil != null) {
            mMapUtil.getBaiduMap().clear();
            LatLng mylocation = new LatLng(Double.parseDouble(CommonUtil.getString(CommonUtil.VARIABLE_CLASS, "LAT")),
                    Double.parseDouble(CommonUtil.getString(CommonUtil.VARIABLE_CLASS, "LNG")));
            mMapUtil.animateMapStatus(mylocation);
        }
    }

    /**
     * 初始化兴趣点搜索引擎
     *
     * @param mPoiSearchListener
     */
    public void initPoiSearchEngine(final PoiSearchResultListener mPoiSearchListener) {
        if (mContext == null) {
            return;
        }
        mPoiSearch = PoiSearch.newInstance();
        mPoiSearch.setOnGetPoiSearchResultListener(new OnGetPoiSearchResultListener() {
            @Override
            public void onGetPoiResult(PoiResult poiResult) {
                if (mPoiSearchListener != null) {
                    mPoiSearchListener.getPoiResult(poiResult);
                }
            }

            @Override
            public void onGetPoiDetailResult(PoiDetailResult poiDetailResult) {
                if (mPoiSearchListener != null) {
                    mPoiSearchListener.getPoiDetailResult(poiDetailResult);
                }
            }

            @Override
            public void onGetPoiIndoorResult(PoiIndoorResult poiIndoorResult) {
                if (mPoiSearchListener != null) {
                    mPoiSearchListener.getPoiIndoorResult(poiIndoorResult);
                }
            }
        });
    }

    /**
     * 根据关键词搜索poi
     *
     * @param keyword
     * @param cityName
     */
    public void getPoiSearchResult(String keyword, String cityName) {
        if (mContext == null || mPoiSearch == null) {
            return;
        }
        mPoiSearch.searchInCity(new PoiCitySearchOption()
                .keyword(keyword).city(cityName));
    }

    /**
     * 初始化智能提示搜索引擎
     *
     * @param suggestionResultListener
     */
    public void initSuggestionSearchEngine(final SuggestionResultListener suggestionResultListener) {
        if (mContext == null) {
            return;
        }
        mSuggestionSearch = SuggestionSearch.newInstance();
        mSuggestionSearch.setOnGetSuggestionResultListener(new OnGetSuggestionResultListener() {
            @Override
            public void onGetSuggestionResult(SuggestionResult suggestionResult) {
                if (suggestionResultListener != null) {
                    suggestionResultListener.getSuggestionResult(suggestionResult);
                }
            }
        });
    }

    /**
     * 搜索智能提示结果
     *
     * @param keyword
     * @param cityName
     */
    public void getSuggestSearchResult(String keyword, String cityName) {
        if (mContext == null || mSuggestionSearch == null) {
            return;
        }
        mSuggestionSearch.requestSuggestion(new SuggestionSearchOption()
                .keyword(keyword).city(cityName));
    }

    /**
     * 初始化坐标反向解析地理信息引擎
     *
     * @param geoCoderResultListener
     */
    public void initGeoCoderEngine(final GeoCoderResultListener geoCoderResultListener) {
        if (mContext == null) {
            return;
        }
        mGeoCoderSearch = GeoCoder.newInstance();
        mGeoCoderSearch.setOnGetGeoCodeResultListener(new OnGetGeoCoderResultListener() {
            @Override
            public void onGetGeoCodeResult(GeoCodeResult geoCodeResult) {
                if (geoCoderResultListener != null) {
                    geoCoderResultListener.getGeoCodeResult(geoCodeResult);
                }
            }

            @Override
            public void onGetReverseGeoCodeResult(ReverseGeoCodeResult reverseGeoCodeResult) {
                if (geoCoderResultListener != null) {
                    geoCoderResultListener.getReverseGeoCodeResult(reverseGeoCodeResult);
                }
            }
        });
    }

    /**
     * 获取坐标对应的地理信息
     *
     * @param latLng
     */
    public void getGeoInfoSearchResult(LatLng latLng) {
        if (mContext == null || mGeoCoderSearch == null) {
            return;
        }
        mGeoCoderSearch.reverseGeoCode(new ReverseGeoCodeOption().location(latLng));
    }

    /**
     * 初始化公交线路搜索引擎
     *
     * @param busLineSearchResultListener
     */
    public void initBusLineSearchEngine(final BusLineSearchResultListener busLineSearchResultListener) {
        if (mContext == null) {
            return;
        }
        mBusLineSearch = BusLineSearch.newInstance();
        mBusLineSearch.setOnGetBusLineSearchResultListener(new OnGetBusLineSearchResultListener() {
            @Override
            public void onGetBusLineResult(BusLineResult busLineResult) {
                if (busLineSearchResultListener != null) {
                    busLineSearchResultListener.getBusLineResult(busLineResult);
                }
            }
        });
    }

    /**
     * 搜索对应公交编号的线路结果
     *
     * @param cityname
     * @param busId
     */
    public void getBusLineSearchResult(String cityname, String busId) {
        if (mContext == null || mBusLineSearch == null) {
            return;
        }
        mBusLineSearch.searchBusLine((new BusLineSearchOption()
                .city(cityname).uid(busId)));
    }

    /**
     * 初始化行政区搜索引擎
     *
     * @param districSearchResultListener
     */
    public void initDistrictSearchEngine(final DistricSearchResultListener districSearchResultListener) {
        if (mContext == null) {
            return;
        }
        mDistrictSearch = DistrictSearch.newInstance();
        mDistrictSearch.setOnDistrictSearchListener(new OnGetDistricSearchResultListener() {
            @Override
            public void onGetDistrictResult(DistrictResult districtResult) {
                if (districSearchResultListener != null) {
                    districSearchResultListener.getDistrictResult(districtResult);
                }

            }
        });
    }

    /**
     * 搜索城市对应的行政区结果
     *
     * @param cityname
     */
    public void getDistrictSearchResult(String cityname) {
        if (mContext == null || mDistrictSearch == null) {
            return;
        }
        //请求行政区数据
        mDistrictSearch.searchDistrict(new DistrictSearchOption()
                .cityName(cityname));
    }


    /**
     * 路径搜索结果回调
     */
    public interface SearchRoutePlanListener {
        void getDrivingRouteResult(DrivingRouteResult result);

        void getTransitRouteResult(TransitRouteResult result);

        void getWalkingRouteResult(WalkingRouteResult result);
    }

    /**
     * 兴趣点搜索结果回调
     */
    public interface PoiSearchResultListener {
        void getPoiResult(PoiResult result);

        void getPoiDetailResult(PoiDetailResult result);

        void getPoiIndoorResult(PoiIndoorResult result);
    }

    /**
     * 智能提示搜索结果回调
     */
    public interface SuggestionResultListener {
        void getSuggestionResult(SuggestionResult result);
    }

    /**
     * 地理坐标反向解析结果回调
     */
    public interface GeoCoderResultListener {
        void getGeoCodeResult(GeoCodeResult result);

        void getReverseGeoCodeResult(ReverseGeoCodeResult result);
    }

    /**
     * 公交线路搜索结果回调
     */
    public interface BusLineSearchResultListener {
        void getBusLineResult(BusLineResult result);
    }

    /**
     * 行政区搜索结果回调
     */
    public interface DistricSearchResultListener {
        void getDistrictResult(DistrictResult result);
    }

    public void showToast(String msg) {
        Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
    }

    /**
     * 退出时候需要调用该方法
     */
    public void onDestory() {
        if (mRoutePlanSearch != null) {
            // 退出时销毁路径规划
            mRoutePlanSearch.destroy();
        }
        if (mPoiSearch != null) {
            // 退出时销毁poi搜索引擎
            mPoiSearch.destroy();
        }
        if (mSuggestionSearch != null) {
            // 销毁智能提示搜索引擎
            mSuggestionSearch.destroy();
        }
        if (mGeoCoderSearch != null) {
            // 销毁地理坐标反向解析引擎
            mGeoCoderSearch.destroy();
        }
        if (mBusLineSearch != null) {
            // 销毁公交线路搜索引擎
            mBusLineSearch.destroy();
        }
        if (mDistrictSearch != null) {
            // 销毁行政区搜索引擎
            mDistrictSearch.destroy();
        }

    }
}
