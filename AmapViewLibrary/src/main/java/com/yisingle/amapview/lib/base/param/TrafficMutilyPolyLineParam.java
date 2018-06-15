package com.yisingle.amapview.lib.base.param;

import com.amap.api.maps.model.BitmapDescriptor;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.yisingle.amap.lib.R;

/**
 * @author jikun
 * Created by jikun on 2018/6/15.
 */
public class TrafficMutilyPolyLineParam {

    /**
     * 路线宽段
     */
    private int routeWidth;



    /**
     * 未知
     */
    private BitmapDescriptor unknownTrafficRouteBitMap;

    /**
     * 顺畅 一般来说绿色
     */
    private BitmapDescriptor smoothTrafficRouteBitMap;
    /**
     * 缓慢 一般来说 是黄色
     */
    private BitmapDescriptor slowTrafficRouteBitMap;
    /**
     * 拥堵 一般来说 是红色
     */
    private BitmapDescriptor jamTrafficRouteBitMap;
    /**
     * 非常拥堵 一般来说非常红
     */
    private BitmapDescriptor veryJamTrafficRouteBitMap;


    private float zIndex;


    public TrafficMutilyPolyLineParam() {

        /**
         * 路线宽段
         */
        routeWidth = 36;


        zIndex = 0f;



        /**
         * 未知
         */
        unknownTrafficRouteBitMap = BitmapDescriptorFactory.fromResource(R.mipmap.custtexture_unknown);

        /**
         * 顺畅 一般来说绿色
         */
        smoothTrafficRouteBitMap = BitmapDescriptorFactory.fromResource(R.mipmap.custtexture_smooth);
        /**
         * 缓慢 一般来说 是黄色
         */
        slowTrafficRouteBitMap = BitmapDescriptorFactory.fromResource(R.mipmap.custtexture_slow);
        /**
         * 拥堵 一般来说 是红色
         */
        jamTrafficRouteBitMap = BitmapDescriptorFactory.fromResource(R.mipmap.custtexture_jam);
        /**
         * 非常拥堵 一般来说非常红
         */
        veryJamTrafficRouteBitMap = BitmapDescriptorFactory.fromResource(R.mipmap.custtexture_very_jam);


    }


    public int getRouteWidth() {
        return routeWidth;
    }

    public void setRouteWidth(int routeWidth) {
        this.routeWidth = routeWidth;
    }


    public BitmapDescriptor getUnknownTrafficRouteBitMap() {
        return unknownTrafficRouteBitMap;
    }

    public void setUnknownTrafficRouteBitMap(BitmapDescriptor unknownTrafficRouteBitMap) {
        this.unknownTrafficRouteBitMap = unknownTrafficRouteBitMap;
    }

    public BitmapDescriptor getSmoothTrafficRouteBitMap() {
        return smoothTrafficRouteBitMap;
    }

    public void setSmoothTrafficRouteBitMap(BitmapDescriptor smoothTrafficRouteBitMap) {
        this.smoothTrafficRouteBitMap = smoothTrafficRouteBitMap;
    }

    public BitmapDescriptor getSlowTrafficRouteBitMap() {
        return slowTrafficRouteBitMap;
    }

    public void setSlowTrafficRouteBitMap(BitmapDescriptor slowTrafficRouteBitMap) {
        this.slowTrafficRouteBitMap = slowTrafficRouteBitMap;
    }

    public BitmapDescriptor getJamTrafficRouteBitMap() {
        return jamTrafficRouteBitMap;
    }

    public void setJamTrafficRouteBitMap(BitmapDescriptor jamTrafficRouteBitMap) {
        this.jamTrafficRouteBitMap = jamTrafficRouteBitMap;
    }

    public BitmapDescriptor getVeryJamTrafficRouteBitMap() {
        return veryJamTrafficRouteBitMap;
    }

    public void setVeryJamTrafficRouteBitMap(BitmapDescriptor veryJamTrafficRouteBitMap) {
        this.veryJamTrafficRouteBitMap = veryJamTrafficRouteBitMap;
    }


    public float getzIndex() {
        return zIndex;
    }

    public void setzIndex(float zIndex) {
        this.zIndex = zIndex;
    }
}
