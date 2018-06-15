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
     * 是否显示箭头路线
     */
    private boolean isArrowRouteShow;


    private TrafficMutilyPolyLineParam trafficParam;


    /**
     * 交通状态纹理图 箭头
     */
    private BitmapDescriptor arrowRouteBitMap;


    /**
     * 默认使用的线路图
     */
    private BitmapDescriptor defaultRouteBimap;

    private float arrowLineZindex;


    private float defaultLineZindex;


    public RouteLineParam() {

        /**
         * 是否显示交通状态
         */
        isTrafficshow = true;

        /**
         * 是否显示箭头路线
         */
        isArrowRouteShow = true;


        trafficParam = new TrafficMutilyPolyLineParam();


        /**
         * 交通状态纹理图 箭头
         */
        arrowRouteBitMap = BitmapDescriptorFactory.fromResource(R.mipmap.custtexture_arrow);


        defaultRouteBimap = BitmapDescriptorFactory.fromResource(R.mipmap.custtexture_unknown);

        defaultLineZindex = 0;

        trafficParam.setzIndex(defaultLineZindex + 1);

        arrowLineZindex = defaultLineZindex + 2;


    }

    public BitmapDescriptor getArrowRouteBitMap() {
        return arrowRouteBitMap;
    }

    public void setArrowRouteBitMap(BitmapDescriptor arrowRouteBitMap) {
        this.arrowRouteBitMap = arrowRouteBitMap;
    }


    public TrafficMutilyPolyLineParam getTrafficParam() {
        return trafficParam;
    }

    public void setTrafficParam(TrafficMutilyPolyLineParam trafficParam) {
        this.trafficParam = trafficParam;
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


    public float getArrowLineZindex() {
        return arrowLineZindex;
    }

    public void setArrowLineZindex(float arrowLineZindex) {
        this.arrowLineZindex = arrowLineZindex;
    }


    public float getDefaultLineZindex() {
        return defaultLineZindex;
    }

    public void setDefaultLineZindex(float defaultLineZindex) {
        this.defaultLineZindex = defaultLineZindex;
    }


    public boolean isArrowRouteShow() {
        return isArrowRouteShow;
    }

    public void setArrowRouteShow(boolean arrowRouteShow) {
        isArrowRouteShow = arrowRouteShow;
    }
}
