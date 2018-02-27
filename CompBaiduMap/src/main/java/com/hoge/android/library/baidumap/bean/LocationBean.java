package com.hoge.android.library.baidumap.bean;

import java.io.Serializable;

/**
 * 定位信息实体类
 *
 * @author hudebo
 * @Description: TODO(用一句话描述该文件做什么)
 * @date 2016年3月24日
 */
public class LocationBean implements Cloneable, Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 地址
     */
    private String address;
    /**
     * 地名
     */
    public String locName;
    /**
     * 省名
     */
    public String province;
    /**
     * 城市
     */
    public String city;
    /**
     * 区名
     */
    public String district;
    /**
     * 街道
     */
    public String street;
    /**
     * 街道号
     */
    public String streetNum;
    /**
     * 纬度
     */
    public Double latitude;
    /**
     * 经度
     */
    public Double longitude;

    @Override
    public Object clone() {
        LocationBean o = null;
        try {
            // Object中的clone()识别出你要复制的是哪一个对象。
            o = (LocationBean) super.clone();
        } catch (CloneNotSupportedException e) {
        }
        return o;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLocName() {
        return locName;
    }

    public void setLocName(String locName) {
        this.locName = locName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getStreetNum() {
        return streetNum;
    }

    public void setStreetNum(String streetNum) {
        this.streetNum = streetNum;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

}
