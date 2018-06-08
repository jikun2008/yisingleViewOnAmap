package com.yisingle.amapview.lib.base.param;

import com.amap.api.maps.model.BitmapDescriptor;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.yisingle.amap.lib.R;

/**
 * @author jikun
 * Created by jikun on 2018/5/14.
 */
public class RouteLineParam {


    /**
     * 是否显示交通状态
     */
    private boolean isTrafficshow;


    /**
     * 路线宽段
     */
    private int routeWidth;

    /**
     * 交通状态纹理图 箭头
     */
    private BitmapDescriptor arrowRouteBitMap;

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
    ;

    /**
     * 默认使用的线路图
     */
    private BitmapDescriptor defaultRouteBimap;

    private float arrowLineZindex;

    private float trafficLineZindex;

    private float defaultLineZindex;


    public RouteLineParam() {

        /**
         * 是否显示交通状态
         */
        isTrafficshow = true;

        /**
         * 路线宽段
         */
        routeWidth = 36;

        /**
         * 交通状态纹理图 箭头
         */
        arrowRouteBitMap = BitmapDescriptorFactory.fromResource(R.mipmap.custtexture_arrow);

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


        defaultRouteBimap = BitmapDescriptorFactory.fromResource(R.mipmap.custtexture_unknown);

        arrowLineZindex = 0;

        trafficLineZindex = arrowLineZindex + 1;


        defaultLineZindex = arrowLineZindex + 1;


    }

    public BitmapDescriptor getArrowRouteBitMap() {
        return arrowRouteBitMap;
    }

    public void setArrowRouteBitMap(BitmapDescriptor arrowRouteBitMap) {
        this.arrowRouteBitMap = arrowRouteBitMap;
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


    public BitmapDescriptor getDefaultRouteBimap() {
        return defaultRouteBimap;
    }

    public void setDefaultRouteBimap(BitmapDescriptor defaultRouteBimap) {
        this.defaultRouteBimap = defaultRouteBimap;
    }

    public boolean isTrafficshow() {
        return isTrafficshow;
    }

    public void setTrafficshow(boolean trafficshow) {
        isTrafficshow = trafficshow;
    }

    public int getRouteWidth() {
        return routeWidth;
    }

    public void setRouteWidth(int routeWidth) {
        this.routeWidth = routeWidth;
    }

    public float getArrowLineZindex() {
        return arrowLineZindex;
    }

    public void setArrowLineZindex(float arrowLineZindex) {
        this.arrowLineZindex = arrowLineZindex;
    }

    public float getTrafficLineZindex() {
        return trafficLineZindex;
    }

    public void setTrafficLineZindex(float trafficLineZindex) {
        this.trafficLineZindex = trafficLineZindex;
    }

    public float getDefaultLineZindex() {
        return defaultLineZindex;
    }

    public void setDefaultLineZindex(float defaultLineZindex) {
        this.defaultLineZindex = defaultLineZindex;
    }
}
