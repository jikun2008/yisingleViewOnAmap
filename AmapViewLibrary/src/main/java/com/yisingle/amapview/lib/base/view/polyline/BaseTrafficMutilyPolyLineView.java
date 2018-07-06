package com.yisingle.amapview.lib.base.view.polyline;

import android.content.Context;

import com.amap.api.maps.AMap;
import com.amap.api.maps.model.BitmapDescriptor;
import com.amap.api.services.route.TMC;
import com.yisingle.amapview.lib.base.BaseBuilder;
import com.yisingle.amapview.lib.base.BaseView;
import com.yisingle.amapview.lib.base.param.TrafficMutilyPolyLineParam;
import com.yisingle.amapview.lib.utils.TrafficMutilyPolyLineUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author jikun
 * Created by jikun on 2018/6/15.
 */
public class BaseTrafficMutilyPolyLineView extends BaseView {


    /**
     * 一条路径规划的路线 如果显示交通状态  那么是由多个PolyLine组成的
     */
    private List<BasePolyLineView> trafficPolyLineViews;


    List<TMC> tmcList;

    private TrafficMutilyPolyLineParam param;


    protected BaseTrafficMutilyPolyLineView(Context context, AMap amap, TrafficMutilyPolyLineParam param) {
        super(context, amap);
        this.param = param;
    }

    @Override
    public void addToMap() {
        if (null != getAmap()) {
            if (isRemove()) {
                trafficPolyLineViews = TrafficMutilyPolyLineUtils.addTrafficStateLine(getContext(), getAmap(), getTmcList(), getParam());
            }
        }

    }

    /**
     * 设置线段的可见属性。当不可见时，线段不会被绘制。
     *
     * @param visible - true: 可见; false: 不可见。
     */
    public void setVisible(boolean visible) {
        if (null != trafficPolyLineViews) {
            for (BasePolyLineView basePolyLineView : trafficPolyLineViews) {
                if (null != basePolyLineView) {
                    basePolyLineView.setVisible(visible);
                }

            }

        }

    }


    public List<BasePolyLineView> getTrafficPolyLineViews() {
        return trafficPolyLineViews;
    }


    @Override
    public void removeFromMap() {
        if (null != trafficPolyLineViews) {
            for (BasePolyLineView basePolyLineView : trafficPolyLineViews) {
                if (null != basePolyLineView) {
                    basePolyLineView.removeFromMap();
                }

            }
            trafficPolyLineViews.clear();
            trafficPolyLineViews = null;
        }

    }

    @Override
    public boolean isRemove() {
        return trafficPolyLineViews == null || trafficPolyLineViews.isEmpty();
    }


    public void draw(List<TMC> tmcs) {
        this.tmcList = tmcs;
        if (isRemove()) {
            addToMap();
        } else {
            trafficPolyLineViews = TrafficMutilyPolyLineUtils.changeTrafficStateLine(getContext(), getAmap(), trafficPolyLineViews, getTmcList(), getParam());
        }

    }

    public List<TMC> getTmcList() {
        return tmcList;
    }


    public TrafficMutilyPolyLineParam getParam() {
        return param;
    }

    public void setParam(TrafficMutilyPolyLineParam param) {
        this.param = param;
    }

    public static final class Builder extends BaseBuilder {

        private BaseTrafficMutilyPolyLineView trafficView;

        private TrafficMutilyPolyLineParam param = new TrafficMutilyPolyLineParam();

        public Builder(Context context, AMap map) {
            super(context, map);
            trafficView = new BaseTrafficMutilyPolyLineView(context, map, param);
        }


        public TrafficMutilyPolyLineParam getParam() {
            return param;
        }

        public Builder setParam(TrafficMutilyPolyLineParam param) {
            this.param = param;
            return this;
        }

        public BaseTrafficMutilyPolyLineView create() {
            return trafficView;
        }


        public Builder setRouteWidth(int routeWidth) {
            param.setRouteWidth(routeWidth);
            return this;
        }


        public Builder setUnknownTrafficRouteBitMap(BitmapDescriptor unknownTrafficRouteBitMap) {
            param.setUnknownTrafficRouteBitMap(unknownTrafficRouteBitMap);
            return this;
        }


        public Builder setSmoothTrafficRouteBitMap(BitmapDescriptor smoothTrafficRouteBitMap) {
            param.setSmoothTrafficRouteBitMap(smoothTrafficRouteBitMap);
            return this;
        }


        public Builder setSlowTrafficRouteBitMap(BitmapDescriptor slowTrafficRouteBitMap) {
            param.setSlowTrafficRouteBitMap(slowTrafficRouteBitMap);
            return this;
        }


        public Builder setJamTrafficRouteBitMap(BitmapDescriptor jamTrafficRouteBitMap) {
            param.setJamTrafficRouteBitMap(jamTrafficRouteBitMap);
            return this;
        }


        public Builder setVeryJamTrafficRouteBitMap(BitmapDescriptor veryJamTrafficRouteBitMap) {
            param.setJamTrafficRouteBitMap(veryJamTrafficRouteBitMap);
            return this;
        }
    }


}
