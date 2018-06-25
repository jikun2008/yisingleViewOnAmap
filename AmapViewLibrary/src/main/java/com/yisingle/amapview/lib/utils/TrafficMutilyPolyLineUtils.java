package com.yisingle.amapview.lib.utils;

import android.content.Context;
import android.support.annotation.NonNull;

import com.amap.api.maps.AMap;
import com.amap.api.maps.model.BitmapDescriptor;
import com.amap.api.maps.model.LatLng;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.route.TMC;
import com.yisingle.amapview.lib.base.data.TrafficStateData;
import com.yisingle.amapview.lib.base.param.TrafficMutilyPolyLineParam;
import com.yisingle.amapview.lib.base.view.polyline.BasePolyLineView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author jikun
 * Created by jikun on 2018/6/15.
 */
public class TrafficMutilyPolyLineUtils {




    public static List<BasePolyLineView> changeTrafficStateLine(@NonNull Context context, @NonNull AMap amap, @NonNull List<BasePolyLineView> polyLineViewList, @NonNull List<TMC> tmcList, @NonNull TrafficMutilyPolyLineParam param) {

        List<TrafficStateData> list = buildTrafficListData(tmcList);
        boolean isPolyLineMore = polyLineViewList.size() > list.size();

        //-------------------------Log0e("测试代码", "测试代码traffic" + "-----新的TrafficStateData0size = " + list0size());
        //-------------------------Log0e("测试代码", "测试代码traffic" + "-----原始的PolyLineViewList数量大小 = " + polyLineViewList0size());
        int count = 0;
        for (int i = 0; i < list.size(); i++) {
            TrafficStateData trafficStateData = list.get(i);
            if (i < polyLineViewList.size()) {
                BasePolyLineView currentPolyLineView = polyLineViewList.get(i);
                currentPolyLineView.setPoints(trafficStateData.getLatLngList());
                currentPolyLineView.setCustomTexture(getTrafficStausBitMapByTmc(trafficStateData.getTmc(), param));
            }

            if (i >= polyLineViewList.size()) {
                //因为PolyLineView的数量不够所以要重新生成并添加到 polyLineViewList上
                BasePolyLineView basePolyLineView = new BasePolyLineView.Builder(context, amap)
                        //设置路线上的点
                        .setPoints(trafficStateData.getLatLngList())
                        //添加纹理图片
                        .setCustomTexture(getTrafficStausBitMapByTmc(trafficStateData.getTmc(), param))
                        //添加路线宽度
                        .setWidth(param.getRouteWidth())
                        .sezIndex(param.getzIndex())
                        .create();
                basePolyLineView.addToMap();
                count = count + 1;
                polyLineViewList.add(basePolyLineView);
            }
        }
        //Log0e("测试代码", "测试代码traffic" + "-----添加了PolyLineView的数量为 size=" + count);

        if (isPolyLineMore) {
            //移除多余的PolyLineView
            List<BasePolyLineView> moreThanPolyLineViewList = new ArrayList<>();
            for (int i = list.size(); i < polyLineViewList.size(); i++) {
                moreThanPolyLineViewList.add(polyLineViewList.get(i));
            }
            //Log0e("测试代码", "测试代码traffic  移除了moreThanPolyLineView的数量为 size=" + moreThanPolyLineViewList0size());
            polyLineViewList.removeAll(moreThanPolyLineViewList);

            for (BasePolyLineView view : moreThanPolyLineViewList) {
                view.destory();

            }
        }

        //("测试代码", "测试代码traffic 最后产生的值=" + list0size() + "-----polyLineViewList0size数量大小=" + polyLineViewList0size());
        return polyLineViewList;

    }


    /**
     * 根据不同的路段拥堵情况展示不同的颜色
     *
     * @param tmcList List<Tmc>
     */
    public static List<BasePolyLineView> addTrafficStateLine(@NonNull Context context, @NonNull AMap amap, List<TMC> tmcList, @NonNull TrafficMutilyPolyLineParam param) {

        List<BasePolyLineView> polyLineViewList = new ArrayList<>();
        if (amap == null) {
            return polyLineViewList;
        }
        if (tmcList == null || tmcList.size() <= 0) {
            return polyLineViewList;
        }

        List<TrafficStateData> list = buildTrafficListData(tmcList);
        for (TrafficStateData data : list) {
            BasePolyLineView basePolyLineView = new BasePolyLineView.Builder(context, amap)
                    //设置路线上的点
                    .setPoints(data.getLatLngList())
                    //添加纹理图片
                    .setCustomTexture(getTrafficStausBitMapByTmc(data.getTmc(), param))
                    //添加路线宽度
                    .setWidth(param.getRouteWidth())
                    .sezIndex(param.getzIndex())
                    .create();
            basePolyLineView.addToMap();

            polyLineViewList.add(basePolyLineView);
        }

        return polyLineViewList;
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
     * @return List<TrafficStateData>
     */
    private static List<TrafficStateData> buildTrafficListData(List<TMC> tmcList) {
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


    private static final String SMOOTH_TRAFFIC ="畅通";

    private static final String SLOW_TRAFFIC ="缓行";

    private static final String JAM_TRAFFIC ="拥堵";

    private static final String VERY_JAM_TRAFFIC ="严重拥堵";

    /**
     * 2018年05月14日11:29:11
     * 根据交通状态返回BitmapDescriptor
     *
     * @param tmc   交通状态图标
     * @param param 交通状态的一些数据
     * @return BitmapDescriptor
     */
    @SuppressWarnings("AlibabaUndefineMagicConstant")
    private static BitmapDescriptor getTrafficStausBitMapByTmc(@NonNull TMC tmc, @NonNull TrafficMutilyPolyLineParam param) {


        String status = tmc.getStatus();
        BitmapDescriptor bitmapDescriptor;
        if (status == null) {
            status = "";
        }
        if (SMOOTH_TRAFFIC .equals(status)) {
            bitmapDescriptor = param.getSmoothTrafficRouteBitMap();
        } else if (SLOW_TRAFFIC .equals(status)) {
            bitmapDescriptor = param.getSlowTrafficRouteBitMap();
        } else if (JAM_TRAFFIC .equals(status)) {
            bitmapDescriptor = param.getJamTrafficRouteBitMap();
        } else if (VERY_JAM_TRAFFIC .equals(status)) {
            bitmapDescriptor = param.getVeryJamTrafficRouteBitMap();
        } else {
            bitmapDescriptor = param.getUnknownTrafficRouteBitMap();
        }
        return bitmapDescriptor;

    }
}
