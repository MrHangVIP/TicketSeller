package com.baidu.mapapi.overlayutil;

import android.graphics.Color;
import android.os.Bundle;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.Overlay;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.Polyline;
import com.baidu.mapapi.map.PolylineOptions;
import com.baidu.mapapi.search.route.TransitRouteLine;
import com.baidu.mapapi.search.route.TransitRouteLine.TransitStep;
import com.baidu.mapapi.search.route.TransitRouteLine.TransitStep.TransitRouteStepType;
import com.hoge.android.library.baidumap.R;
import com.hoge.android.library.baidumap.util.CommonUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BusTransferOverlay extends OverlayManager {
    private TransitRouteLine mRouteLine = null;
    private int mainColor;

    /**
     * 构造函数
     *
     * @param baiduMap 该TransitRouteOverlay引用的 BaiduMap 对象
     */
    public BusTransferOverlay(BaiduMap baiduMap) {

        super(baiduMap);
        try {
            Object getMultiColorResult = CommonUtil.invokeByStaticMethod(CommonUtil.CONFIGURATION_CLASS, "getMultiColor",
                    new Class[]{Map.class, String.class, String.class},
                    new Object[]{CommonUtil.getStaticField(CommonUtil.CONFIGURATION_CLASS, "config_map"),
                            CommonUtil.getString(CommonUtil.TEMPLATE_CLASS, "colorScheme"), "#03b6a7"});
            if (getMultiColorResult != null && getMultiColorResult instanceof Integer) {
                mainColor = (int) getMultiColorResult;
            }
        } catch (Exception e) {
            mainColor = Color.parseColor("#03b6a7");
        }
    }

    @Override
    public final List<OverlayOptions> getOverlayOptions() {

        if (mRouteLine == null) {
            return null;
        }

        List<OverlayOptions> overlayOptionses = new ArrayList<OverlayOptions>();
        // step node

        if (mRouteLine.getAllStep() != null
                && mRouteLine.getAllStep().size() > 0) {
            for (int i = 0; i < mRouteLine.getAllStep().size(); i++) {
                TransitStep step = mRouteLine.getAllStep().get(i);
                Bundle b = new Bundle();
                b.putInt("index", mRouteLine.getAllStep().indexOf(step));
                if (step.getEntrance() != null) {
                    overlayOptionses.add((new MarkerOptions())
                            .position(step.getEntrance().getLocation())
                            .anchor(0.5f, 0.5f).zIndex(10).extraInfo(b)
                            .icon(getIconForStep(step)));
                }
                // 最后路段绘制出口点
                if (mRouteLine.getAllStep().indexOf(step) == (mRouteLine
                        .getAllStep().size() - 1) && step.getExit() != null) {
                    overlayOptionses.add((new MarkerOptions())
                            .position(step.getExit().getLocation())
                            .anchor(0.5f, 0.5f).zIndex(10)
                            .icon(getIconForStep(step)));
                }
            }
        }

        if (mRouteLine.getStarting() != null) {
            overlayOptionses.add((new MarkerOptions())
                    .position(mRouteLine.getStarting().getLocation())
                    .icon(getStartMarker() != null ? getStartMarker() :
                            BitmapDescriptorFactory
                                    .fromResource(R.drawable.transfer_startsation_bg)).zIndex(10));
        }
        if (mRouteLine.getTerminal() != null) {
            overlayOptionses
                    .add((new MarkerOptions())
                            .position(mRouteLine.getTerminal().getLocation())
                            .icon(getTerminalMarker() != null ? getTerminalMarker() :
                                    BitmapDescriptorFactory
                                            .fromResource(R.drawable.transfer_endsation_bg)).zIndex(10));
        }
        // polyline
        if (mRouteLine.getAllStep() != null
                && mRouteLine.getAllStep().size() > 0) {

            for (TransitStep step : mRouteLine.getAllStep()) {
                if (step.getWayPoints() == null || step.getWayPoints().size() == 0) {
                    continue;
                }
                int color = 0;
                if (step.getStepType() == TransitRouteStepType.WAKLING) {
                    color = getLineColor() != 0 ? getLineColor() : Color.parseColor("#92A7E3");
                } else {
                    color = getLineColor() != 0 ? getLineColor() : mainColor;
                }
                overlayOptionses.add(new PolylineOptions()
                        .points(step.getWayPoints()).width(10).color(color)
                        .zIndex(0));
            }
        }
        return overlayOptionses;
    }

    private BitmapDescriptor getIconForStep(TransitStep step) {
        switch (step.getStepType()) {
            case BUSLINE:
                return BitmapDescriptorFactory.fromAssetWithDpi("Icon_bus_station.png");
            case SUBWAY:
                return BitmapDescriptorFactory.fromAssetWithDpi("Icon_subway_station.png");
            case WAKLING:
                return BitmapDescriptorFactory.fromAssetWithDpi("Icon_walk_route.png");
            default:
                return null;
        }
    }

    /**
     * 设置路线数据
     *
     * @param routeOverlay 路线数据
     */
    public void setData(TransitRouteLine routeOverlay) {
        this.mRouteLine = routeOverlay;
    }

    /**
     * 覆写此方法以改变默认起点图标
     *
     * @return 起点图标
     */
    public BitmapDescriptor getStartMarker() {
        return null;
    }

    /**
     * 覆写此方法以改变默认终点图标
     *
     * @return 终点图标
     */
    public BitmapDescriptor getTerminalMarker() {
        return null;
    }

    public int getLineColor() {
        return 0;
    }

    /**
     * 覆写此方法以改变起默认点击行为
     *
     * @param i 被点击的step在
     *          {@link TransitRouteLine#getAllStep()}
     *          中的索引
     * @return 是否处理了该点击事件
     */
    public boolean onRouteNodeClick(int i) {
        if (mRouteLine.getAllStep() != null
                && mRouteLine.getAllStep().get(i) != null) {
        }
        return false;
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        for (Overlay mMarker : mOverlayList) {
            if (mMarker instanceof Marker && mMarker.equals(marker)) {
                if (marker.getExtraInfo() != null) {
                    onRouteNodeClick(marker.getExtraInfo().getInt("index"));
                }
            }
        }
        return true;
    }

    @Override
    public boolean onPolylineClick(Polyline polyline) {
        // TODO Auto-generated method stub
        return false;
    }

}
