package com.hoge.android.library.baidumap;


import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.search.core.i;
import com.baidu.mapapi.search.route.BikingRoutePlanOption;
import com.baidu.mapapi.search.route.DrivingRoutePlanOption;
import com.baidu.mapapi.search.route.IndoorRoutePlanOption;
import com.baidu.mapapi.search.route.MassTransitRoutePlanOption;
import com.baidu.mapapi.search.route.OnGetRoutePlanResultListener;
import com.baidu.mapapi.search.route.TransitRoutePlanOption;
import com.baidu.mapapi.search.route.WalkingRoutePlanOption;
import com.baidu.platform.core.d.e;
import com.hoge.android.library.baidumap.mydrivingroute.MyJ;

/**
 * @discription 对百度地图路径规划重写，手动解析百度地图的打车信息，因为sdk解析不对
 * @autor songzhihang
 * @time 2017/12/21  下午1:16
 **/
public class MyRoutePlanSearch extends i {
    private e a = new MyJ();
    private boolean b = false;

    MyRoutePlanSearch() {
    }

    public static MyRoutePlanSearch newInstance() {
        BMapManager.init();
        return new MyRoutePlanSearch();
    }

    public void setOnGetRoutePlanResultListener(OnGetRoutePlanResultListener var1) {
        if (this.a == null) {
            throw new IllegalStateException("RoutePlanSearch is null, please call newInstance() first.");
        } else if (var1 == null) {
            throw new IllegalArgumentException("listener can not be null");
        } else {
            this.a.a(var1);
        }
    }

    public boolean transitSearch(TransitRoutePlanOption var1) {
        if (this.a == null) {
            throw new IllegalStateException("RoutePlanSearch is null, please call newInstance() first.");
        } else if (var1 != null && var1.mCityName != null && var1.mTo != null && var1.mFrom != null) {
            return this.a.a(var1);
        } else {
            throw new IllegalArgumentException("route plan option,origin or destination or city can not be null");
        }
    }

    public boolean masstransitSearch(MassTransitRoutePlanOption var1) {
        if (this.a == null) {
            throw new IllegalStateException("RoutePlanSearch is null, please call newInstance() first.");
        } else if (var1 != null && var1.mTo != null && var1.mFrom != null) {
            if (var1.mFrom.getLocation() != null || var1.mFrom.getName() != null && var1.mFrom.getCity() != null) {
                if (var1.mTo.getLocation() != null || var1.mTo.getName() != null && var1.mTo.getCity() != null) {
                    return this.a.a(var1);
                } else {
                    throw new IllegalArgumentException("route plan option,destination is illegal");
                }
            } else {
                throw new IllegalArgumentException("route plan option,origin is illegal");
            }
        } else {
            throw new IllegalArgumentException("route plan option,origin or destination can not be null");
        }
    }

    public boolean walkingSearch(WalkingRoutePlanOption var1) {
        if (this.a == null) {
            throw new IllegalStateException("RoutePlanSearch is null, please call newInstance() first.");
        } else if (var1 != null && var1.mTo != null && var1.mFrom != null) {
            return this.a.a(var1);
        } else {
            throw new IllegalArgumentException("option , origin or destination can not be null");
        }
    }

    public boolean walkingIndoorSearch(IndoorRoutePlanOption var1) {
        if (this.a == null) {
            throw new IllegalStateException("RoutePlanSearch is null, please call newInstance() first.");
        } else if (var1 != null && var1.mTo != null && var1.mFrom != null) {
            return this.a.a(var1);
        } else {
            throw new IllegalArgumentException("option , origin or destination can not be null");
        }
    }

    public boolean drivingSearch(DrivingRoutePlanOption var1) {
        if (this.a == null) {
            throw new IllegalStateException("RoutePlanSearch is null, please call newInstance() first.");
        } else if (var1 != null && var1.mTo != null && var1.mFrom != null) {
            return this.a.a(var1);
        } else {
            throw new IllegalArgumentException("route plan option , origin or destination can not be null");
        }
    }

    public boolean bikingSearch(BikingRoutePlanOption var1) {
        if (this.a == null) {
            throw new IllegalStateException("RoutePlanSearch is null, please call newInstance() first.");
        } else if (var1 != null && var1.mTo != null && var1.mFrom != null) {
            if (var1.mFrom.getLocation() != null || var1.mFrom.getName() != null && var1.mFrom.getName() != "") {
                if (var1.mTo.getLocation() != null || var1.mTo.getName() != null && var1.mTo.getName() != "") {
                    return this.a.a(var1);
                } else {
                    throw new IllegalArgumentException("route plan option , destination is illegal");
                }
            } else {
                throw new IllegalArgumentException("route plan option , origin is illegal");
            }
        } else {
            throw new IllegalArgumentException("route plan option , origin or destination can not be null");
        }
    }

    public void destroy() {
        if (!this.b) {
            this.b = true;
            this.a.a();
            BMapManager.destroy();
        }
    }
}
