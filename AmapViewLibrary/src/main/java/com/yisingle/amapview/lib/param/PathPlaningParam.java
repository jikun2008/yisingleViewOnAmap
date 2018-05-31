package com.yisingle.amapview.lib.param;

import com.amap.api.services.core.LatLonPoint;

/**
 * @author jikun
 * Created by jikun on 2018/5/24.
 */
public class PathPlaningParam {


    private boolean isAuotDrawPath;

    private LatLonPoint startLatLonPoint;

    private LatLonPoint endLatLonPoint;

    public PathPlaningParam() {
        this.isAuotDrawPath = true;
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

    public boolean isAuotDrawPath() {
        return isAuotDrawPath;
    }

    public void setAuotDrawPath(boolean auotDrawPath) {
        isAuotDrawPath = auotDrawPath;
    }
}
