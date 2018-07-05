package com.yisingle.amapview.lib.param;

import com.amap.api.services.core.LatLonPoint;

/**
 * @author jikun
 * Created by jikun on 2018/5/24.
 */
public class PathPlaningParam {


    private LatLonPoint startLatLonPoint;

    private LatLonPoint endLatLonPoint;


    private float startMarkerZindex;

    private float endMarkerZindex;

    private float arrowLineZindex;

    private float trafficLineZindex;

    private float defaultLineZindex;


    public PathPlaningParam() {


        trafficLineZindex = 0f;

        startMarkerZindex = trafficLineZindex + 2;
        endMarkerZindex = trafficLineZindex + 2;
        arrowLineZindex = trafficLineZindex + 1;

        defaultLineZindex = trafficLineZindex + 1;


    }

    public LatLonPoint getStartLatLonPoint() {
        return startLatLonPoint;
    }

    public void setStartLatLonPoint(LatLonPoint startLatLonPoint) {
        this.startLatLonPoint = startLatLonPoint;
    }

    public LatLonPoint getEndLatLonPoint() {
        return endLatLonPoint;
    }

    public void setEndLatLonPoint(LatLonPoint endLatLonPoint) {
        this.endLatLonPoint = endLatLonPoint;
    }


    public float getStartMarkerZindex() {
        return startMarkerZindex;
    }

    public void setStartMarkerZindex(float startMarkerZindex) {
        this.startMarkerZindex = startMarkerZindex;
    }

    public float getEndMarkerZindex() {
        return endMarkerZindex;
    }

    public void setEndMarkerZindex(float endMarkerZindex) {
        this.endMarkerZindex = endMarkerZindex;
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
