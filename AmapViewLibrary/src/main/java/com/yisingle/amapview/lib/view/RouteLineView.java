package com.yisingle.amapview.lib.view;

import android.content.Context;
import android.support.annotation.NonNull;

import com.amap.api.maps.AMap;
import com.amap.api.maps.model.BitmapDescriptor;
import com.amap.api.maps.model.LatLng;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.route.DrivePath;
import com.amap.api.services.route.DriveStep;
import com.amap.api.services.route.TMC;
import com.yisingle.amapview.lib.base.BaseBuilder;
import com.yisingle.amapview.lib.base.BaseView;
import com.yisingle.amapview.lib.base.param.RouteLineParam;
import com.yisingle.amapview.lib.base.view.polyline.BasePolyLineView;
import com.yisingle.amapview.lib.base.view.polyline.BaseTrafficMutilyPolyLineView;

import java.util.ArrayList;
import java.util.List;

/**
 * 显示路线的UI效果
 *
 * @author jikun
 * Created by jikun on 2018/5/10.
 */
public class RouteLineView extends BaseView {


    /**
     * 一条路径规划的路线 如果显示交通状态  那么是由多个PolyLine组成的
     */
    private BaseTrafficMutilyPolyLineView trafficPolyLinewView;


    /**
     * 路线显示箭头 /SpatialRelationUtil spatialRelationUtil;
     */
    private BasePolyLineView arrowPolyLineView;


    /**
     * 默认的路线如果不需要显示交通状态的路线     那么我们就可以只用一条PolyLineView就可以了
     */
    private BasePolyLineView defaultBasePolyLineView;


    private RouteLineParam routeLineParam;


    private DrawData drawData;


    private RouteLineView(@NonNull Context context, @NonNull AMap amap, @NonNull RouteLineParam routeLineParam) {
        super(context, amap);
        this.routeLineParam = routeLineParam;
        trafficPolyLinewView = new BaseTrafficMutilyPolyLineView.Builder(getContext(), getAmap())
                .setParam(routeLineParam.getTrafficParam()).create();


        arrowPolyLineView = new BasePolyLineView.Builder(getContext(), getAmap())
                .sezIndex(routeLineParam.getArrowLineZindex())
                .setCustomTexture(routeLineParam.getArrowRouteBitMap())
                .setWidth(routeLineParam.getTrafficParam().getRouteWidth())
                .create();


        defaultBasePolyLineView = new BasePolyLineView.Builder(context, amap)
                .sezIndex(routeLineParam.getArrowLineZindex())
                //添加纹理图片
                .setCustomTexture(routeLineParam.getDefaultRouteBimap())
                //添加路线宽度
                .setWidth(routeLineParam.getTrafficParam().getRouteWidth())
                .create();
    }


    public List<TMC> getTmcList() {
        if (null == trafficPolyLinewView) {
            return null;
        } else {
            return trafficPolyLinewView.getTmcList();
        }
    }

    public List<LatLng> getTotalLatLng() {
        if (null == arrowPolyLineView) {
            return null;
        } else {
            return arrowPolyLineView.getLatLngList();
        }


    }


    @Override
    public void addToMap() {
        if (isRemove()) {
            draw(getDrawData());
        }
    }

    @Override
    public void removeFromMap() {
        if (null != trafficPolyLinewView) {
            trafficPolyLinewView.removeFromMap();

        }

        if (null != arrowPolyLineView) {
            arrowPolyLineView.removeFromMap();

        }

        if (null != defaultBasePolyLineView) {
            defaultBasePolyLineView.removeFromMap();

        }

    }


    @Override
    public void destory() {
        removeFromMap();

    }

    public BaseTrafficMutilyPolyLineView getTrafficPolyLinewView() {
        return trafficPolyLinewView;
    }

    public BasePolyLineView getArrowPolyLineView() {
        return arrowPolyLineView;
    }

    public BasePolyLineView getDefaultBasePolyLineView() {
        return defaultBasePolyLineView;
    }

    @Override
    public boolean isRemove() {
        return defaultBasePolyLineView.isRemove();
    }


    public RouteLineParam getRouteLineParam() {
        return routeLineParam;
    }

    public void setRouteLineParam(RouteLineParam routeLineParam) {
        this.routeLineParam = routeLineParam;
    }


    public DrawData getDrawData() {
        return drawData;
    }


    public void draw(DrivePath drivePath) {
        this.drawData = DrawData.produceDrawData(drivePath);
        if (isRemove()) {
            addToMap();
        } else {
            draw(getDrawData());
        }

    }


    public void draw(DrawData data) {
        this.drawData = data;
        if (null != drawData) {
            trafficPolyLinewView.draw(drawData.getTrafficTmcs());
            trafficPolyLinewView.setVisible(getRouteLineParam().isTrafficshow());
            defaultBasePolyLineView.draw(drawData.getTotalLatlngList());
            defaultBasePolyLineView.setVisible(!getRouteLineParam().isTrafficshow());
            arrowPolyLineView.draw(drawData.getTotalLatlngList());
            arrowPolyLineView.setVisible(getRouteLineParam().isArrowRouteShow());
        }


    }


    public static final class Builder extends BaseBuilder {

        private RouteLineView routeLineView;

        private RouteLineParam routeLineParam = new RouteLineParam();

        public Builder(@NonNull Context context, @NonNull AMap map) {
            super(context, map);
            routeLineView = new RouteLineView(context, map, routeLineParam);
        }


        public Builder setArrowRouteBitMap(BitmapDescriptor bitMap) {
            routeLineParam.setArrowRouteBitMap(bitMap);
            return this;
        }

        public Builder setTrafficUnknownRouteBitMap(BitmapDescriptor bitMap) {
            routeLineParam.getTrafficParam().setUnknownTrafficRouteBitMap(bitMap);
            return this;
        }


        public Builder setTrafficSmoothRouteBitMap(BitmapDescriptor bitMap) {
            routeLineParam.getTrafficParam().setSmoothTrafficRouteBitMap(bitMap);
            return this;
        }

        public Builder setTrafficSlowRouteBitMap(BitmapDescriptor bitMap) {
            routeLineParam.getTrafficParam().setSlowTrafficRouteBitMap(bitMap);
            return this;
        }

        public Builder setTrafficJamRouteBitMap(BitmapDescriptor bitMap) {
            routeLineParam.getTrafficParam().setJamTrafficRouteBitMap(bitMap);
            return this;
        }

        public Builder setTrafficVeryJamRouteBitMap(BitmapDescriptor bitMap) {
            routeLineParam.getTrafficParam().setVeryJamTrafficRouteBitMap(bitMap);
            return this;
        }

        public Builder setDefaultRouteBimap(BitmapDescriptor bitMap) {
            routeLineParam.setDefaultRouteBimap(bitMap);
            return this;
        }

        public Builder setTrafficshow(boolean isShow) {
            routeLineParam.setTrafficshow(isShow);
            return this;
        }


        public Builder setArrowRouteShow(boolean isShow) {
            routeLineParam.setArrowRouteShow(isShow);
            return this;
        }

        public Builder setArrowLineZindex(float zindex) {
            routeLineParam.setArrowLineZindex(zindex);
            return this;
        }


        public Builder setTrafficLineZindex(float zindex) {
            routeLineParam.getTrafficParam().setzIndex(zindex);
            return this;
        }


        public Builder setDefaultLineZindex(float zindex) {
            routeLineParam.setDefaultLineZindex(zindex);
            return this;
        }


        public RouteLineParam getRouteLineParam() {
            return routeLineParam;
        }


        public RouteLineView create() {
            routeLineView.setRouteLineParam(getRouteLineParam());
            return routeLineView;
        }


    }

    public static class DrawData {
        List<TMC> trafficTmcs;
        List<LatLng> totalLatlngList;


        private DrawData(List<TMC> trafficTmcs, List<LatLng> totalLatlngList) {
            this.trafficTmcs = trafficTmcs;
            this.totalLatlngList = totalLatlngList;
        }


        public List<TMC> getTrafficTmcs() {
            return trafficTmcs;
        }

        public void setTrafficTmcs(List<TMC> trafficTmcs) {
            this.trafficTmcs = trafficTmcs;
        }

        public List<LatLng> getTotalLatlngList() {
            return totalLatlngList;
        }

        public void setTotalLatlngList(List<LatLng> totalLatlngList) {
            this.totalLatlngList = totalLatlngList;
        }


        /**
         * 生成 绘制的数据
         *
         * @param drivePath 地图路径规划返回的类
         * @return 返回DrawData
         */

        public static DrawData produceDrawData(DrivePath drivePath) {
            if (null == drivePath) {
                return new DrawData(new ArrayList<TMC>(), new ArrayList<LatLng>());
            }
            //线路中所有的坐标
            List<LatLng> totalLatLng = new ArrayList<>();


            //线路中交通情况
            List<TMC> trafficTmcs = new ArrayList<>();
            //组装数据
            List<DriveStep> driveStepList = drivePath.getSteps();
            for (DriveStep step : driveStepList) {
                // 获取交通状态不同的坐标点的集合
                // 后面将根据trafficTmcs来进行交通状态画线
                // trafficPolyLinewViews会根据trafficTmcs来画线
                trafficTmcs.addAll(step.getTMCs());
                //根据数据来区分将 defaultBasePolyLineView将根据这个来画线
                List<LatLonPoint> latlonPoints = step.getPolyline();
                for (LatLonPoint latlonpoint : latlonPoints) {
                    //将所有的点添加到totalLatLng中为后面使用做准备
                    totalLatLng.add(new LatLng(latlonpoint.getLatitude(), latlonpoint.getLongitude()));
                }
            }


            return new DrawData(trafficTmcs, totalLatLng);

        }


        public static DrawData produceDrawData(List<TMC> trafficTmcs, List<LatLng> totalLatlngList) {

            return new DrawData(trafficTmcs, totalLatlngList);

        }
    }


}
