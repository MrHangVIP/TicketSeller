package com.hoge.android.library.baidumap.mydrivingroute;

import com.baidu.mapapi.search.route.BikingRoutePlanOption;
import com.baidu.mapapi.search.route.DrivingRoutePlanOption;
import com.baidu.mapapi.search.route.IndoorRoutePlanOption;
import com.baidu.mapapi.search.route.MassTransitRoutePlanOption;
import com.baidu.mapapi.search.route.OnGetRoutePlanResultListener;
import com.baidu.mapapi.search.route.TransitRoutePlanOption;
import com.baidu.mapapi.search.route.WalkingRoutePlanOption;
import com.baidu.platform.base.SearchType;
import com.baidu.platform.base.a;
import com.baidu.platform.core.d.b;
import com.baidu.platform.core.d.e;
import com.baidu.platform.core.d.f;
import com.baidu.platform.core.d.h;
import com.baidu.platform.core.d.i;
import com.baidu.platform.core.d.l;
import com.baidu.platform.core.d.m;
import com.baidu.platform.core.d.n;
import com.baidu.platform.core.d.o;
import com.hoge.android.library.baidumap.mytrainsitroute.MyL;

public class MyJ extends a implements e {
    private OnGetRoutePlanResultListener b = null;

    public MyJ() {
    }

    @Override
    public void a(OnGetRoutePlanResultListener var1) {
        this.a.lock();
        this.b = var1;
        this.a.unlock();
    }

    @Override
    public boolean a(DrivingRoutePlanOption var1) {
        com.baidu.platform.core.d.c var2 = new MyC();
        var2.a(SearchType.k);
        MyD var3 = new MyD(var1);
        return this.a(var3, this.b, var2);
    }

    @Override
    public boolean a(TransitRoutePlanOption var1) {
        l var2 = new MyL();
        var2.a(SearchType.j);
        m var3 = new m(var1);
        return this.a(var3, this.b, var2);
    }

    @Override
    public boolean a(MassTransitRoutePlanOption var1) {
        h var2 = new h();
        var2.a(SearchType.i);
        i var3 = new i(var1);
        return this.a(var3, this.b, var2);
    }

    @Override
    public boolean a(WalkingRoutePlanOption var1) {
        n var2 = new n();
        var2.a(SearchType.m);
        o var3 = new o(var1);
        return this.a(var3, this.b, var2);
    }

    @Override
    public boolean a(IndoorRoutePlanOption var1) {
        f var2 = new f();
        var2.a(SearchType.n);
        MyG var3 = new MyG(var1);
        return this.a(var3, this.b, var2);
    }

    @Override
    public boolean a(BikingRoutePlanOption var1) {
        com.baidu.platform.core.d.a var2 = new com.baidu.platform.core.d.a();
        var2.a(SearchType.l);
        com.baidu.platform.core.d.b var3 = new b(var1);
        return this.a(var3, this.b, var2);
    }

    @Override
    public void a() {
        this.a.lock();
        this.b = null;
        this.a.unlock();
    }

}
