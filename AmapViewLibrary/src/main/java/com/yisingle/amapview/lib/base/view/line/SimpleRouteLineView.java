package com.yisingle.amapview.lib.base.view.line;

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
import com.yisingle.amapview.lib.base.view.polyline.PolyLineView;
import com.yisingle.amapview.lib.utils.RouteLineUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 显示路线的UI效果
 *
 * @author jikun
 * Created by jikun on 2018/5/10.
 */
public class SimpleRouteLineView extends BaseView {


    /**
     * 一条路径规划的路线 如果显示交通状态  那么是由多个PolyLine组成的
     */
    private List<PolyLineView> trafficPolyLinewViews;


    /**
     * 路线显示箭头
     */
    private PolyLineView arrowPolyLineView;


    /**
     * 默认的路线如果不需要显示交通状态的路线     那么我们就可以只用一条PolyLineView就可以了
     */
    private PolyLineView defaultBasePolyLineView;


    private RouteLineParam routeLineParam;


    private DrivePath drivePath;


    private SimpleRouteLineView(@NonNull Context context, @NonNull AMap amap, @NonNull RouteLineParam routeLineParam) {
        super(context, amap);
        this.routeLineParam = routeLineParam;
    }


    private void drawLine(DrivePath drivePath) {
        if (null == drivePath) {
            return;
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


        if (isRemove()) {
            //如果设置  需要根据交通状态画路线那么
            if (getRouteLineParam().isTrafficshow()) {
                //根据交通状态画路线
                trafficPolyLinewViews = RouteLineUtils.addTrafficStateLine(getContext(), getAmap(), trafficTmcs, getRouteLineParam());
            } else {
                defaultBasePolyLineView = RouteLineUtils.addDefaultLine(getContext(), getAmap(), totalLatLng, getRouteLineParam());
            }
            //画箭头
            arrowPolyLineView = RouteLineUtils.addArrowLine(getContext(), getAmap(), totalLatLng, getRouteLineParam());
        } else {
            if (getRouteLineParam().isTrafficshow()) {
                trafficPolyLinewViews = RouteLineUtils.changeTrafficStateLine(getContext(), getAmap(), trafficPolyLinewViews, trafficTmcs, getRouteLineParam());
            } else {
                defaultBasePolyLineView.setPoints(totalLatLng);
            }
            arrowPolyLineView.setPoints(totalLatLng);
        }

    }


    @Override
    public void addToMap() {

        if (isRemove()) {
            drawLine(getDrivePath());
        }


    }

    @Override
    public void removeFromMap() {
        if (null != trafficPolyLinewViews) {
            for (PolyLineView basePolyLineView : trafficPolyLinewViews) {
                if (null != basePolyLineView) {
                    basePolyLineView.removeFromMap();
                }

            }
            trafficPolyLinewViews.clear();
            trafficPolyLinewViews = null;
        }

        if (null != arrowPolyLineView) {
            arrowPolyLineView.removeFromMap();
            arrowPolyLineView = null;
        }

        if (null != defaultBasePolyLineView) {
            defaultBasePolyLineView.removeFromMap();
            arrowPolyLineView = null;
        }

    }


    @Override
    public void destory() {
        removeFromMap();

    }

    @Override
    public boolean isRemove() {
        return arrowPolyLineView == null;
    }


    public RouteLineParam getRouteLineParam() {
        return routeLineParam;
    }

    public void setRouteLineParam(RouteLineParam routeLineParam) {
        this.routeLineParam = routeLineParam;
    }


    public DrivePath getDrivePath() {
        return drivePath;
    }

    public void setDrivePath(DrivePath drivePath) {
        this.drivePath = drivePath;
        drawLine(getDrivePath());
    }

    public static final class Builder extends BaseBuilder {

        private SimpleRouteLineView routeLineView;

        private RouteLineParam routeLineParam = new RouteLineParam();

        public Builder(@NonNull Context context, @NonNull AMap map) {
            super(context, map);
            routeLineView = new SimpleRouteLineView(context, map, routeLineParam);
        }


        public Builder setArrowRouteBitMap(BitmapDescriptor bitMap) {
            routeLineParam.setArrowRouteBitMap(bitMap);
            return this;
        }

        public Builder setUnknownTrafficRouteBitMap(BitmapDescriptor bitMap) {
            routeLineParam.setUnknownTrafficRouteBitMap(bitMap);
            return this;
        }


        public Builder setSmoothTrafficRouteBitMap(BitmapDescriptor bitMap) {
            routeLineParam.setSmoothTrafficRouteBitMap(bitMap);
            return this;
        }

        public Builder setSlowTrafficRouteBitMap(BitmapDescriptor bitMap) {
            routeLineParam.setSlowTrafficRouteBitMap(bitMap);
            return this;
        }

        public Builder setJamTrafficRouteBitMap(BitmapDescriptor bitMap) {
            routeLineParam.setJamTrafficRouteBitMap(bitMap);
            return this;
        }

        public Builder setVeryJamTrafficRouteBitMap(BitmapDescriptor bitMap) {
            routeLineParam.setVeryJamTrafficRouteBitMap(bitMap);
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

        public Builder setArrowLineZindex(float zindex) {
            routeLineParam.setArrowLineZindex(zindex);
            return this;
        }


        public Builder setTrafficLineZindex(float zindex) {
            routeLineParam.setTrafficLineZindex(zindex);
            return this;
        }


        public Builder setDefaultLineZindex(float zindex) {
            routeLineParam.setDefaultLineZindex(zindex);
            return this;
        }


        public RouteLineParam getRouteLineParam() {
            return routeLineParam;
        }


        public SimpleRouteLineView create() {
            routeLineView.setRouteLineParam(getRouteLineParam());
            routeLineView.addToMap();
            return routeLineView;
        }


    }


}
