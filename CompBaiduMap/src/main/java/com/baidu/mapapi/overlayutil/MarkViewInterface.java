package com.baidu.mapapi.overlayutil;

import android.view.View;

import com.baidu.mapapi.map.Marker;

/**
 * @discription 路径规划markView的点击事件的提示
 * @autor songzhihang
 * @time 2017/12/27  下午6:37
 **/
public interface MarkViewInterface {

    /**
     * @param marker   当前点击的覆盖物
     * @return 点击覆盖物之后的弹框的View 返回空则不弹框
     */
    View onMarkViewClick(Marker marker);
}
