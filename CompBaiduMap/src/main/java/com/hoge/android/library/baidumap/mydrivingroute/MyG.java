package com.hoge.android.library.baidumap.mydrivingroute;

import com.baidu.mapapi.model.CoordUtil;
import com.baidu.mapapi.model.inner.GeoPoint;
import com.baidu.mapapi.search.route.IndoorRoutePlanOption;
import com.baidu.platform.base.e;
import com.baidu.platform.domain.c;

public class MyG extends e {

    MyG(IndoorRoutePlanOption var1) {
        this.a(var1);
    }

    private void a(IndoorRoutePlanOption var1) {
        this.a.a("qt", "indoornavi");
        this.a.a("rp_format", "json");
        this.a.a("version", "1");
        GeoPoint var2 = CoordUtil.ll2mc(var1.mFrom.getLocation());
        String var3 = "";
        if (var2 != null) {
            var3 = String.format("%f,%f", new Object[]{Double.valueOf(var2.getLongitudeE6()), Double.valueOf(var2.getLatitudeE6())});
            var3 = var3 + "|" + var1.mFrom.getFloor();
            var3 = var3.replaceAll(" ", "");
            this.a.a("sn", var3);
        }

        GeoPoint var4 = CoordUtil.ll2mc(var1.mTo.getLocation());
        String var5 = "";
        if (var4 != null) {
            var5 = String.format("%f,%f", new Object[]{Double.valueOf(var4.getLongitudeE6()), Double.valueOf(var4.getLatitudeE6())});
            var5 = var5 + "|" + var1.mTo.getFloor();
            var5 = var5.replaceAll(" ", "");
            this.a.a("en", var5);
        }

    }

    @Override
    public String a(c var1) {
        return var1.l();
    }
}
