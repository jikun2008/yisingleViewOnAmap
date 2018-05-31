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
    List<LatLng> latLngList = new ArrayList<>();

    int totalDuration = 40;


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
