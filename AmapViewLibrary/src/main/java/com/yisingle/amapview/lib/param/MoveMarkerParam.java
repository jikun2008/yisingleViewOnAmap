package com.yisingle.amapview.lib.param;

import com.amap.api.maps.model.LatLng;
import com.yisingle.amapview.lib.base.param.BaseMarkerParam;

import java.util.ArrayList;
import java.util.List;

/**
 * @author jikun
 * Created by jikun on 2018/5/22.
 */
public class MoveMarkerParam extends BaseMarkerParam {


    public MoveMarkerParam() {
        getOptions().belowMaskLayer(true);
        getOptions().anchor(0.5F, 0.5F);
    }
    private List<LatLng> latLngList = new ArrayList<>();

    private int totalDuration = 60 * 1000;


    public void setLatLngList(List<LatLng> latLngList) {
        this.latLngList = latLngList;
    }


    public void setTotalDuration(int totalDuration) {
        this.totalDuration = totalDuration;

    }

    public List<LatLng> getLatLngList() {
        return latLngList;
    }

    public int getTotalDuration() {
        return totalDuration;
    }
}
