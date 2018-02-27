package com.hoge.android.library.baidumap.mydrivingroute;

import com.baidu.mapapi.search.route.DrivingRoutePlanOption;
import com.baidu.mapapi.search.route.PlanNode;
import com.baidu.platform.base.e;

import java.util.List;

public class MyD extends e {

    public MyD(DrivingRoutePlanOption var1) {
        this.a(var1);
    }

    private void a(DrivingRoutePlanOption var1) {
        this.a.a("qt", "cars");
        this.a.a("sy", var1.mPolicy.getInt() + "");
        this.a.a("ie", "utf-8");
        this.a.a("lrn", "20");
        this.a.a("version", "6");
        this.a.a("extinfo", "32");
        this.a.a("mrs", "1");
        this.a.a("rp_format", "json");
        this.a.a("rp_filter", "mobile");
        this.a.a("route_traffic", var1.mtrafficPolicy.getInt() + "");
        this.a.a("sn", this.a((PlanNode) var1.mFrom));
        this.a.a("en", this.a((PlanNode) var1.mTo));
        if (null != var1.mCityName) {
            this.a.a("c", var1.mCityName);
        }

        if (null != var1.mFrom) {
            this.a.a("sc", var1.mFrom.getCity());
        }

        if (null != var1.mTo) {
            this.a.a("ec", var1.mTo.getCity());
        }

        List var2 = var1.mWayPoints;
        String var3 = new String();
        String var4 = new String();
        if (null != var2) {
            for (int var5 = 0; var5 < var2.size(); ++var5) {
                PlanNode var6 = (PlanNode) var2.get(var5);
                if (var6 != null) {
                    var3 = var3 + this.a((PlanNode) var6);
                    var4 = var4 + var6.getCity();
                    if (var5 != var2.size() - 1) {
                        var3 = var3 + "|";
                        var4 = var4 + "|";
                    }
                }
            }

            this.a.a("wp", var3);
            this.a.a("wpc", var4);
        }

    }

    @Override
    public String a(com.baidu.platform.domain.c var1) {
        return var1.i();
    }
}
