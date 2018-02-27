package com.hoge.android.library.baidumap.mytrainsitroute;

import com.baidu.mapapi.model.CoordUtil;
import com.baidu.mapapi.search.core.RouteNode;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.core.TaxiInfo;
import com.baidu.mapapi.search.core.VehicleInfo;
import com.baidu.mapapi.search.route.TransitRouteLine;
import com.baidu.mapapi.search.route.TransitRouteResult;
import com.baidu.platform.core.d.l;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
/**
 * @discription transit路线规划中接口数据解析
 * @autor   songzhihang 可以从返回的TransitRouteLine 中获取taxiinfo中获取公交和地铁的总价格
 * @time   2017/12/27  下午5:47
 **/
public class MyL extends l {

    public MyL() {
    }

    @Override
    public void a(String var1, TransitRouteResult var2) {
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

    private boolean b(String var1, TransitRouteResult var2) {
        if (var1 != null && var1.length() > 0) {
            JSONObject var3 = null;

            try {
                var3 = new JSONObject(var1);
            } catch (JSONException var26) {
                var26.printStackTrace();
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
                            JSONObject var6 = var3.optJSONObject("bus");
                            if (var6 == null) {
                                return false;
                            } else {
                                JSONObject var7 = var6.optJSONObject("taxi");
                                if (var7 != null) {
                                    var2.setTaxiInfo(this.a(var7));
                                }

                                JSONObject var8 = var6.optJSONObject("option");
                                if (var8 == null) {
                                    return false;
                                } else {
                                    RouteNode var9 = this.a(var8, "start");
                                    RouteNode var10 = this.a(var8, "end");
                                    JSONArray var11 = var6.optJSONArray("routes");
                                    if (var11 != null && var11.length() > 0) {
                                        ArrayList var12 = new ArrayList();

                                        for (int var13 = 0; var13 < var11.length(); ++var13) {
                                            JSONArray var14 = ((JSONObject) var11.opt(var13)).optJSONArray("legs");
                                            JSONObject var15 = (JSONObject) var14.opt(0);
                                            if (var15 != null) {
                                                TransitRouteLine var16 = new TransitRouteLine();
                                                var16.setDistance(var15.optInt("distance"));
                                                var16.setDuration(var15.optInt("duration"));
                                                var16.setStarting(var9);
                                                var16.setTerminal(var10);
                                                var16.setTaxitInfo(getTaxiInfo(var15.optInt("price")));//手动解析获取价格
                                                JSONArray var17 = var15.optJSONArray("steps");
                                                if (var17 != null && var17.length() > 0) {
                                                    ArrayList var18 = new ArrayList();

                                                    for (int var19 = 0; var19 < var17.length(); ++var19) {
                                                        JSONArray var20 = var17.optJSONObject(var19).optJSONArray("step");
                                                        if (var20 != null && var20.length() > 0) {
                                                            JSONObject var21 = var20.optJSONObject(0);
                                                            TransitRouteLine.TransitStep var22 = new TransitRouteLine.TransitStep();
                                                            var22.setEntrace(RouteNode.location(CoordUtil.decodeLocation(var21.optString("start_location"))));
                                                            var22.setExit(RouteNode.location(CoordUtil.decodeLocation(var21.optString("end_location"))));
                                                            int var23 = var21.optInt("type");
                                                            if (var23 == 5) {
                                                                var22.setStepType(TransitRouteLine.TransitStep.TransitRouteStepType.WAKLING);
                                                            } else {
                                                                var22.setStepType(TransitRouteLine.TransitStep.TransitRouteStepType.BUSLINE);
                                                            }

                                                            var22.setInstructions(this.b(var21.optString("instructions")));
                                                            var22.setDistance(var21.optInt("distance"));
                                                            var22.setDuration(var21.optInt("duration"));
                                                            var22.setPathString(var21.optString("path"));
                                                            if (var21.has("vehicle")) {
                                                                var22.setVehicleInfo(this.c(var21.optString("vehicle")));
                                                                JSONObject var24 = var21.optJSONObject("vehicle");
                                                                var22.getEntrance().setUid(var24.optString("start_uid"));
                                                                var22.getEntrance().setTitle(var24.optString("start_name"));
                                                                var22.getExit().setUid(var24.optString("end_uid"));
                                                                var22.getExit().setTitle(var24.optString("end_name"));
                                                                Integer var25 = Integer.valueOf(var24.optInt("type"));
                                                                if (var25 != null) {
                                                                    if (var25.intValue() == 1) {
                                                                        var22.setStepType(TransitRouteLine.TransitStep.TransitRouteStepType.SUBWAY);
                                                                    } else {
                                                                        var22.setStepType(TransitRouteLine.TransitStep.TransitRouteStepType.BUSLINE);
                                                                    }
                                                                } else {
                                                                    var22.setStepType(TransitRouteLine.TransitStep.TransitRouteStepType.BUSLINE);
                                                                }
                                                            }

                                                            var18.add(var22);
                                                        }
                                                    }

                                                    var16.setSteps(var18);
                                                    var12.add(var16);
                                                }
                                            }
                                        }

                                        var2.setRoutelines(var12);
                                        return true;
                                    }

                                    return false;
                                }
                            }
                        case 1:
                            var2.error = SearchResult.ERRORNO.ST_EN_TOO_NEAR;
                            return true;
                        case 200:
                            var2.error = SearchResult.ERRORNO.NOT_SUPPORT_BUS_2CITY;
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

    private TaxiInfo a(JSONObject var1) {
        if (var1 == null) {
            return null;
        } else {
            TaxiInfo var2 = new TaxiInfo();
            JSONArray var3 = var1.optJSONArray("detail");
            if (var3 != null && var3.length() > 0) {
                int var4 = var3.length();
                float var5 = 0.0F;
                float var6 = 0.0F;
                float var7 = 0.0F;

                for (int var9 = 0; var9 < var4; ++var9) {
                    JSONObject var10 = (JSONObject) var3.opt(var9);
                    if (var10 != null) {
                        String var8 = var10.optString("desc");
                        if (var8.contains("白天")) {
                            var5 = (float) var10.optDouble("km_price");
                            var6 = (float) var10.optDouble("start_price");
                            var7 = (float) var10.optDouble("total_price");
                            break;
                        }
                    }
                }

                var2.setDesc(var1.optString("remark"));
                var2.setDistance(var1.optInt("distance"));
                var2.setDuration(var1.optInt("duration"));
                var2.setTotalPrice(var7);
                var2.setStartPrice(var6);
                var2.setPerKMPrice(var5);
                return var2;
            } else {
                return null;
            }
        }
    }

    private RouteNode a(JSONObject var1, String var2) {
        if (var1 != null && var2 != null && !"".equals(var2)) {
            JSONObject var3 = var1.optJSONObject(var2);
            RouteNode var4 = new RouteNode();
            var4.setTitle(var3.optString("wd"));
            var4.setUid(var3.optString("uid"));
            var4.setLocation(CoordUtil.decodeLocation(var3.optString("pt")));
            return var4;
        } else {
            return null;
        }
    }

    private String b(String var1) {
        if (var1 == null) {
            return null;
        } else {
            StringBuilder var2 = new StringBuilder();
            char[] var3 = var1.toCharArray();
            boolean var4 = false;

            for (int var5 = 0; var5 < var3.length; ++var5) {
                if (var3[var5] == 60) {
                    var4 = true;
                } else if (var3[var5] == 62) {
                    var4 = false;
                } else if (!var4) {
                    var2.append(var3[var5]);
                }
            }

            return var2.toString();
        }
    }

    private VehicleInfo c(String var1) {
        JSONObject var2 = null;

        try {
            var2 = new JSONObject(var1);
        } catch (JSONException var4) {
            var4.printStackTrace();
        }

        if (var2 == null) {
            return null;
        } else {
            VehicleInfo var3 = new VehicleInfo();
            var3.setZonePrice(var2.optInt("zone_price"));
            var3.setTotalPrice(var2.optInt("total_price"));
            var3.setTitle(var2.optString("name"));
            var3.setPassStationNum(var2.optInt("stop_num"));
            var3.setUid(var2.optString("uid"));
            return var3;
        }
    }

    private TaxiInfo getTaxiInfo(int price) {
        TaxiInfo taxiInfo = new TaxiInfo();
        taxiInfo.setTotalPrice(price);
        return taxiInfo;
    }
}
