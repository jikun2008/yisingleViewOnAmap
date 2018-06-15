package com.yisingle.amapview.lib.base.data;

import com.amap.api.maps.model.LatLng;
import com.amap.api.services.route.TMC;

import java.util.List;

/**
 * @author jikun
 * Created by jikun on 2018/5/28.
 * 对应交通状态路线数据 用来绘制路线
 */
public class TrafficStateData {

    TMC tmc;

    List<LatLng> latLngList;


    public TrafficStateData(TMC tmc, List<LatLng> latLngList) {
        this.tmc = tmc;
        this.latLngList = latLngList;
    }

    public TMC getTmc() {
        return tmc;
    }

    public void setTmc(TMC tmc) {
        this.tmc = tmc;
    }

    public List<LatLng> getLatLngList() {
        return latLngList;
    }

    public void setLatLngList(List<LatLng> latLngList) {
        this.latLngList = latLngList;
    }
}
