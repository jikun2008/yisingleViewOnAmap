package com.yisingle.amapview.lib.utils;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.amap.api.maps.AMap;
import com.amap.api.maps.model.BitmapDescriptor;
import com.amap.api.maps.model.LatLng;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.route.TMC;
import com.yisingle.amapview.lib.base.param.RouteLineParam;
import com.yisingle.amapview.lib.base.view.line.TrafficStateData;
import com.yisingle.amapview.lib.base.view.polyline.PolyLineView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author jikun
 * Created by jikun on 2018/5/14.
 */
public class RouteLineUtils {

    /**
     * 2018年05月14日11:29:11
     * 根据交通状态返回BitmapDescriptor
     *
     * @param tmc           交通状态图标
     * @param routeLineData RouteLine数据图片
     * @return
     */
    public static BitmapDescriptor getTrafficStausBitMapByTmc(@NonNull TMC tmc, @NonNull RouteLineParam routeLineData) {


        String status = tmc.getStatus();
        BitmapDescriptor bitmapDescriptor = null;
        if (status == null) {
            status = "";
        }
        if (status.equals("畅通")) {
            bitmapDescriptor = routeLineData.getSmoothTrafficRouteBitMap();
        } else if (status.equals("缓行")) {
            bitmapDescriptor = routeLineData.getSlowTrafficRouteBitMap();
        } else if (status.equals("拥堵")) {
            bitmapDescriptor = routeLineData.getJamTrafficRouteBitMap();
        } else if (status.equals("严重拥堵")) {
            bitmapDescriptor = routeLineData.getVeryJamTrafficRouteBitMap();
        } else {
            bitmapDescriptor = routeLineData.getDefaultRouteBimap();
        }
        return bitmapDescriptor;

    }


    /**
     * 根据不同的路段拥堵情况展示不同的颜色
     *
     * @param tmcList
     */
    public static List<PolyLineView> addTrafficStateLine(@NonNull Context context, @NonNull AMap amap, @NonNull List<TMC> tmcList, @NonNull RouteLineParam routeLineParam) {

        List<PolyLineView> polyLineViewList = new ArrayList<>();
        if (amap == null) {
            return polyLineViewList;
        }
        if (tmcList == null || tmcList.size() <= 0) {
            return polyLineViewList;
        }

        List<TrafficStateData> list = buildTrafficListData(tmcList);
        for (TrafficStateData data : list) {
            PolyLineView basePolyLineView = new PolyLineView.Builder(context, amap)
                    //设置路线上的点
                    .setPoints(data.getLatLngList())
                    //添加纹理图片
                    .setCustomTexture(getTrafficStausBitMapByTmc(data.getTmc(), routeLineParam))
                    //添加路线宽度
                    .setWidth(routeLineParam.getRouteWidth())
                    .create();
            basePolyLineView.addToMap();

            polyLineViewList.add(basePolyLineView);
        }

        return polyLineViewList;
    }


    public static List<PolyLineView> changeTrafficStateLine(@NonNull Context context, @NonNull AMap amap, @NonNull List<PolyLineView> polyLineViewList, @NonNull List<TMC> tmcList, @NonNull RouteLineParam routeLineParam) {

        List<TrafficStateData> list = buildTrafficListData(tmcList);
        boolean isPolyLineMore = polyLineViewList.size() > list.size();

        Log.e("测试代码", "测试代码traffic" + "-----新的TrafficStateData.size = " + list.size());
        Log.e("测试代码", "测试代码traffic" + "-----原始的PolyLineViewList.size = " + polyLineViewList.size());
        int count = 0;
        for (int i = 0; i < list.size(); i++) {
            TrafficStateData trafficStateData = list.get(i);
            if (i < polyLineViewList.size()) {
                PolyLineView currentPolyLineView = polyLineViewList.get(i);
                currentPolyLineView.setPoints(trafficStateData.getLatLngList());
                currentPolyLineView.setCustomTexture(getTrafficStausBitMapByTmc(trafficStateData.getTmc(), routeLineParam));
            }

            if (i >= polyLineViewList.size()) {
                //因为PolyLineView的数量不够所以要重新生成并添加到 polyLineViewList上
                PolyLineView basePolyLineView = new PolyLineView.Builder(context, amap)
                        //设置路线上的点
                        .setPoints(trafficStateData.getLatLngList())
                        //添加纹理图片
                        .setCustomTexture(getTrafficStausBitMapByTmc(trafficStateData.getTmc(), routeLineParam))
                        //添加路线宽度
                        .setWidth(routeLineParam.getRouteWidth())
                        .create();
                basePolyLineView.addToMap();
                count = count + 1;
                polyLineViewList.add(basePolyLineView);
            }
        }
        Log.e("测试代码", "测试代码traffic" + "-----添加了PolyLineView的数量为 size=" + count);

        if (isPolyLineMore) {
            //移除多余的PolyLineView
            List<PolyLineView> moreThanPolyLineViewList = new ArrayList<>();
            for (int i = list.size(); i < polyLineViewList.size(); i++) {
                moreThanPolyLineViewList.add(polyLineViewList.get(i));
            }
            Log.e("测试代码", "测试代码traffic  移除了moreThanPolyLineView的数量为 size=" + moreThanPolyLineViewList.size());
            polyLineViewList.removeAll(moreThanPolyLineViewList);

            for (PolyLineView view : moreThanPolyLineViewList) {
                view.destory();

            }
        }

        Log.e("测试代码", "测试代码traffic 最后产生的值=" + list.size() + "-----polyLineViewList.size=" + polyLineViewList.size());
        return polyLineViewList;

    }

    /**
     * 添加箭头图片
     *
     * @param latLngList 坐标集合
     * @return
     */
    public static PolyLineView addArrowLine(@NonNull Context context, @NonNull AMap amap, @NonNull List<LatLng> latLngList, @NonNull RouteLineParam routeLineData) {


        PolyLineView basePolyLineView = new PolyLineView.Builder(context, amap)
                //设置路线上的点
                .setPoints(latLngList)
                //添加纹理图片
                .setCustomTexture(routeLineData.getArrowRouteBitMap())
                //添加路线宽度
                .setWidth(routeLineData.getRouteWidth())
                .create();
        basePolyLineView.addToMap();

        return basePolyLineView;

    }


    public static PolyLineView addDefaultLine(@NonNull Context context, @NonNull AMap amap, @NonNull List<LatLng> latLngList, @NonNull RouteLineParam routeLineData) {

        PolyLineView basePolyLineView = new PolyLineView.Builder(context, amap)
                //设置路线上的点
                .setPoints(latLngList)
                //添加纹理图片
                .setCustomTexture(routeLineData.getDefaultRouteBimap())
                //添加路线宽度
                .setWidth(routeLineData.getRouteWidth())
                .create();
        basePolyLineView.addToMap();

        return basePolyLineView;
    }


    /**
     * 是用来防止出现多条线之间的断点问题。
     * 根据TMC交通状态数组数据
     * (1.多个PolyLineView的数量是等于List<TMC> tmcList的数量）
     * (2.一个PolyLineView显示需要通过List<LatLonPoint> listLatLonPoint=TMC.getPolyline()数据 来显示）
     * 为了防止多个PolyLineView路线的断点问题。使用如下算法:
     * 来绘制多个PolyLineView的数据
     *
     * @param tmcList 交通状态数据
     * @return
     */
    public static List<TrafficStateData> buildTrafficListData(List<TMC> tmcList) {
        //这是下面循环中上一次的交通坐标集合对象
        TMC previousTmc = null;
        List<TrafficStateData> trafficStateDataList = new ArrayList<>();
        for (int i = 0; i < tmcList.size(); i++) {

            TMC tmc = tmcList.get(i);
            //得到previousTmc的最后的点
            LatLng previousLatLng = null;
            if (null != previousTmc && null != previousTmc.getPolyline() && previousTmc.getPolyline().size() > 0) {
                int num = previousTmc.getPolyline().size() - 1;
                LatLonPoint lastLatLonPoint = previousTmc.getPolyline().get(num);
                previousLatLng = new LatLng(lastLatLonPoint.getLatitude(), lastLatLonPoint.getLongitude());
            }


            List<LatLng> latLngList = new ArrayList<>();

            if (previousLatLng != null) {
                //如果传递过来是上一个地图线的最后坐标不为null
                // 那么把这个坐标点添加到地图线的坐标前面去防止多条线段的断点问题。
                latLngList.add(previousLatLng);
            }

            //根据交通状况设置路线的坐标集合
            for (LatLonPoint latLonPoint : tmc.getPolyline()) {
                latLngList.add(new LatLng(latLonPoint.getLatitude(), latLonPoint.getLongitude()));
            }

            trafficStateDataList.add(new TrafficStateData(tmc, latLngList));


            //循环结束赋值给previousTmc，保留这个对象。
            previousTmc = tmcList.get(i);
        }
        return trafficStateDataList;

    }


}
