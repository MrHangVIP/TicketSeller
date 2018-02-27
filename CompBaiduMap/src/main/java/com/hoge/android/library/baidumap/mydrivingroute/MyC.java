package com.hoge.android.library.baidumap.mydrivingroute;

import android.text.TextUtils;

import com.baidu.mapapi.common.Logger;
import com.baidu.mapapi.model.CoordUtil;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.model.inner.GeoPoint;
import com.baidu.mapapi.search.core.RouteNode;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.core.TaxiInfo;
import com.baidu.mapapi.search.route.DrivingRouteLine;
import com.baidu.mapapi.search.route.DrivingRouteResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * @discription draving路线规划中接口数据解析
 * @autor songzhihang 可以从返回的DrivingRouteLine 中获取taxiinfo中获取打车价格
 * @time 2017/12/27  下午5:47
 **/
public class MyC extends com.baidu.platform.core.d.c {

    public MyC() {
    }

    @Override
    public void a(String var1, DrivingRouteResult var2) {
        if (var1 != null && !var1.equals("")) {
            JSONObject var3 = null;

            try {
                var3 = new JSONObject(var1);
                if (var3.has("SDK_InnerError")) {
                    JSONObject var4 = var3.optJSONObject("SDK_InnerError");
                    if (var4.has("PermissionCheckError")) {
                        var2.error = SearchResult.ERRORNO.PERMISSION_UNFINISHED;
                        return;
                    }

                    if (var4.has("httpStateError")) {
                        String var5 = var4.optString("httpStateError");
                        if (var5.equals("NETWORK_ERROR")) {
                            var2.error = SearchResult.ERRORNO.NETWORK_ERROR;
                        } else if (var5.equals("REQUEST_ERROR")) {
                            var2.error = SearchResult.ERRORNO.REQUEST_ERROR;
                        } else {
                            var2.error = SearchResult.ERRORNO.SEARCH_SERVER_INTERNAL_ERROR;
                        }

                        return;
                    }
                }
            } catch (Exception var6) {
                var2.error = SearchResult.ERRORNO.RESULT_NOT_FOUND;
                return;
            }

            if (!this.a(var1, var2, false) && !this.b(var1, var2)) {
                var2.error = SearchResult.ERRORNO.RESULT_NOT_FOUND;
            }

        } else {
            var2.error = SearchResult.ERRORNO.RESULT_NOT_FOUND;
        }
    }

    private boolean b(String var1, DrivingRouteResult var2) {
        if (var1 != null && !"".equals(var1)) {
            JSONObject var3 = null;

            try {
                var3 = new JSONObject(var1);
            } catch (JSONException var27) {
                var27.printStackTrace();
                return false;
            }

            if (var3 == null) {
                return false;
            } else {
                JSONObject var4 = var3.optJSONObject("result");
                if (var4 == null) {
                    return false;
                } else {
                    int var5 = var4.optInt("error");
                    switch (var5) {
                        case 0:
                            JSONObject var6 = var3.optJSONObject("cars");
                            if (var6 == null) {
                                return false;
                            } else {
                                JSONObject var7 = var6.optJSONObject("option");
                                JSONObject var8 = var6.optJSONObject("content");
                                if (var7 != null && var8 != null) {
                                    RouteNode var9 = this.a(var7.optJSONObject("start"));
                                    ArrayList var10 = new ArrayList();
                                    RouteNode var11 = this.a((JSONArray) var7.optJSONArray("end"), (List) var10);
                                    List var12 = this.a(var8.optJSONArray("steps"), var8.optJSONArray("stepts"));
                                    ArrayList var13 = new ArrayList();
                                    JSONArray var14 = var8.optJSONArray("routes");
                                    if (var14 == null) {
                                        return false;
                                    }

                                    for (int var15 = 0; var15 < var14.length(); ++var15) {
                                        DrivingRouteLine var16 = new DrivingRouteLine();
                                        JSONObject var17 = var14.optJSONObject(var15);
                                        if (var17 != null) {
                                            JSONArray var18 = var17.optJSONArray("legs");
                                            if (var18 == null) {
                                                return false;
                                            }

                                            int var19 = var18.length();
                                            int var20 = 0;
                                            int var21 = 0;
                                            ArrayList var22 = new ArrayList();

                                            for (int var23 = 0; var23 < var19; ++var23) {
                                                JSONObject var24 = var18.optJSONObject(var23);
                                                if (var24 != null) {
                                                    var20 += var24.optInt("distance");
                                                    var21 += var24.optInt("duration");
                                                    JSONArray var25 = var24.optJSONArray("stepis");
                                                    List var26 = null;
                                                    var26 = this.b(var25, var12);
                                                    if (null != var26) {
                                                        var22.addAll(var26);
                                                    }
                                                }
                                            }

                                            var16.setStarting(var9);
                                            var16.setTerminal(var11);
                                            if (0 == var10.size()) {
                                                var16.setWayPoints((List) null);
                                            } else {
                                                var16.setWayPoints(var10);
                                            }

                                            var16.setDistance(var20);
                                            var16.setDuration(var21);
                                            var16.setCongestionDistance(var17.optInt("congestion_length"));
                                            var16.setLightNum(var17.optInt("light_num"));
                                            if (0 == var22.size()) {
                                                var16.setSteps((List) null);
                                            } else {
                                                var16.setSteps(var22);
                                            }

                                            var13.add(var16);
                                        }
                                    }

                                    var2.setRouteLines(var13);
                                    if (!TextUtils.isEmpty(var6.optString("taxis"))) {//兼容处理
                                        var2.setTaxiInfos(this.b(var6.optString("taxis")));
                                    } else {
                                        var2.setTaxiInfos(this.b(var8.optString("taxis")));
                                    }
                                    return true;
                                }

                                return false;
                            }
                        case 4:
                            var2.error = SearchResult.ERRORNO.ST_EN_TOO_NEAR;
                            return true;
                        default:
                            return false;
                    }
                }
            }
        } else {
            return false;
        }
    }

    private RouteNode a(JSONObject var1) {
        if (var1 == null) {
            return null;
        } else {
            RouteNode var2 = new RouteNode();
            var2.setTitle(var1.optString("wd"));
            var2.setUid(var1.optString("uid"));
            GeoPoint var3 = new GeoPoint(0.0D, 0.0D);
            JSONArray var4 = var1.optJSONArray("spt");
            if (var4 != null) {
                var3.setLongitudeE6((double) var4.optInt(0));
                var3.setLatitudeE6((double) var4.optInt(1));
            }

            var2.setLocation(CoordUtil.mc2ll(var3));
            return var2;
        }
    }

    private RouteNode a(JSONArray var1, List<RouteNode> var2) {
        int var3;
        if (var1 != null && (var3 = var1.length()) > 0) {
            for (int var4 = 0; var4 < var3; ++var4) {
                JSONObject var5 = var1.optJSONObject(var4);
                if (var5 != null) {
                    RouteNode var6 = this.a(var5);
                    if (var4 == var3 - 1) {
                        return var6;
                    }

                    var2.add(var6);
                }
            }

            return null;
        } else {
            return null;
        }
    }

    private List<TaxiInfo> b(String var1) {
        if (null != var1 && 0 < var1.length()) {
            Object var2 = null;
            ArrayList var3 = new ArrayList();

            try {
                JSONArray var4 = new JSONArray(var1);
                if (var4 == null) {
                    return null;
                } else {
                    for (int var5 = 0; var5 < var4.length(); ++var5) {
                        JSONObject var6 = var4.getJSONObject(var5);
                        if (var6 != null) {
                            TaxiInfo var7 = new TaxiInfo();
                            String var8 = var6.optString("total_price");
                            if (var8 != null && !var8.equals("")) {
                                var7.setTotalPrice(Float.parseFloat(var8));
                            } else {
                                var7.setTotalPrice(0.0F);
                            }

                            var3.add(var7);
                        }
                    }

                    return var3;
                }
            } catch (JSONException var9) {
                if (Logger.debugEnable()) {
                    var9.printStackTrace();
                }

                return null;
            }
        } else {
            return null;
        }
    }

    private List<DrivingRouteLine.DrivingStep> a(JSONArray var1, JSONArray var2) {
        boolean var3 = false;
        int var16;
        if (var1 != null && (var16 = var1.length()) > 0) {
            boolean var4 = true;
            int var5 = 0;
            if (var2 == null || (var5 = var2.length()) <= 0) {
                var4 = false;
            }

            ArrayList var6 = new ArrayList();
            int var7 = 0;
            String var8 = "";

            for (int var9 = 0; var9 < var16; ++var9) {
                JSONObject var10 = var1.optJSONObject(var9);
                if (var10 != null) {
                    DrivingRouteLine.DrivingStep var11 = new DrivingRouteLine.DrivingStep();
                    var11.setDistance(var10.optInt("distance"));
                    var11.setDirection(30 * var10.optInt("direction"));
                    String var12 = var10.optString("instructions");
                    if (null != var12 || var12.length() >= 4) {
                        var12 = var12.replaceAll("/?[a-zA-Z]{1,10};", "").replaceAll("<[^>]*>", "").replaceAll("[(/>)<]", "");
                    }

                    var11.setInstructions(var12);
                    var8 = var10.optString("start_instructions");
                    if (var8 == null) {
                        int var13 = var11.getDistance();
                        if (var13 < 1000) {
                            var8 = " - " + var13 + "米";
                        } else {
                            var8 = " - " + (double) var13 / 1000.0D + "公里";
                        }

                        if (var7 <= var6.size()) {
                            var8 = ((DrivingRouteLine.DrivingStep) var6.get(var7 - 1)).getExitInstructions() + var8;
                        }
                    }

                    var11.setEntranceInstructions(var8);
                    var11.setExitInstructions(var10.optString("end_instructions"));
                    var11.setNumTurns(var10.optInt("turn"));
                    List var17 = this.a(var10.optJSONArray("spath"));
                    var11.setPathList(var17);
                    if (var17 != null) {
                        RouteNode var14 = new RouteNode();
                        var14.setLocation((LatLng) var17.get(0));
                        var11.setEntrance(var14);
                        RouteNode var15 = new RouteNode();
                        var15.setLocation((LatLng) var17.get(var17.size() - 1));
                        var11.setExit(var15);
                    }

                    if (var4 && var9 < var5) {
                        int[] var18 = this.b(var2.optJSONObject(var9));
                        var11.setTrafficList(var18);
                    }

                    ++var7;
                    var6.add(var11);
                }
            }

            return var6;
        } else {
            return null;
        }
    }

    private int[] b(JSONObject var1) {
        if (var1 == null) {
            return null;
        } else {
            JSONArray var2 = var1.optJSONArray("end");
            JSONArray var3 = var1.optJSONArray("status");
            if (var2 != null && var3 != null) {
                ArrayList var4 = new ArrayList();
                int var5 = var2.length();
                int var6 = var3.length();

                int var8;
                for (int var7 = 0; var7 < var5; ++var7) {
                    var8 = var2.optInt(var7);
                    int var9 = 0;
                    if (var7 < var6) {
                        var9 = var3.optInt(var7);
                    }

                    for (int var10 = 0; var10 < var8; ++var10) {
                        var4.add(Integer.valueOf(var9));
                    }
                }

                int[] var11 = new int[var4.size()];

                for (var8 = 0; var8 < var4.size(); ++var8) {
                    var11[var8] = ((Integer) var4.get(var8)).intValue();
                }

                return var11;
            } else {
                return null;
            }
        }
    }

    private List<LatLng> a(JSONArray var1) {
        boolean var2 = false;
        int var10;
        if (var1 != null && (var10 = var1.length()) >= 6) {
            ArrayList var3 = new ArrayList();
            double var4 = 0.0D;
            double var6 = 0.0D;

            for (int var8 = 5; var8 < var10; ++var8) {
                if (var8 % 2 != 0) {
                    var4 += (double) var1.optInt(var8);
                } else {
                    var6 += (double) var1.optInt(var8);
                    GeoPoint var9 = new GeoPoint(var6, var4);
                    var3.add(CoordUtil.mc2ll(var9));
                }
            }

            return var3;
        } else {
            return null;
        }
    }

    private List<DrivingRouteLine.DrivingStep> b(JSONArray var1, List<DrivingRouteLine.DrivingStep> var2) {
        boolean var3 = false;
        int var10;
        if (var1 != null && (var10 = var1.length()) > 0 && var2 != null) {
            ArrayList var4 = new ArrayList();

            for (int var5 = 0; var5 < var10; ++var5) {
                JSONObject var6 = var1.optJSONObject(var5);
                if (var6 != null) {
                    int var7 = var6.optInt("n");
                    int var8 = var6.optInt("s");

                    for (int var9 = 0; var9 < var7; ++var9) {
                        if (var8 + var9 < var2.size()) {
                            var4.add(var2.get(var8 + var9));
                        }
                    }
                }
            }

            return var4;
        } else {
            return null;
        }
    }
}
