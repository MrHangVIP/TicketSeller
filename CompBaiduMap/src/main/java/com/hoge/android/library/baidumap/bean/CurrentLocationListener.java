package com.hoge.android.library.baidumap.bean;

import com.baidu.location.BDLocation;

/**
 * 定位监听器
 *
 * @author George
 *
 */
public interface CurrentLocationListener {

    /**
     * 定位获取位置成功
     * @param location
     */
    void onReceiveLocationSuccess(BDLocation location);

    /**
     * 定位失败回调
     */
    void onReceiveLocationFail();
}
