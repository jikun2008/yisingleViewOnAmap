package com.yisingle.amapview.lib.view;

import android.content.Context;
import android.support.annotation.NonNull;

import com.amap.api.maps.AMap;
import com.amap.api.maps.model.LatLng;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.route.BusRouteResult;
import com.amap.api.services.route.DriveRouteResult;
import com.amap.api.services.route.RideRouteResult;
import com.amap.api.services.route.RouteSearch;
import com.amap.api.services.route.WalkRouteResult;
import com.yisingle.amapview.lib.base.BaseBuilder;
import com.yisingle.amapview.lib.base.BaseView;
import com.yisingle.amapview.lib.param.PathPlaningParam;
import com.yisingle.amapview.lib.utils.GaoDeErrorUtils;

/**
 * @author jikun
 * Created by jikun on 2018/5/15.
 */
public class PathPlaningView<W> extends BaseView {


    private PointMarkerView<W> startPointMarkerView;


    private PointMarkerView<W> endPointMarkerView;


    private RouteLineView simpleRouteLineView;

    private PathPlaningParam param;


    private OnPathPlaningCallBack callBack;

    private PathPlaningView(Context context, AMap amap, @NonNull PathPlaningParam param) {
        super(context, amap);
        this.param = param;
    }

    @Override
    public void addToMap() {
        if (isRemove()) {
            startPointMarkerView.addToMap();
            endPointMarkerView.addToMap();
            simpleRouteLineView.addToMap();
        }


    }


    @Override
    public void removeFromMap() {
        startPointMarkerView.removeFromMap();
        endPointMarkerView.removeFromMap();
        simpleRouteLineView.removeFromMap();
    }

    @Override
    public void destory() {
        startPointMarkerView.destory();
        endPointMarkerView.destory();
        simpleRouteLineView.destory();
        callBack = null;


    }

    public void draw(DriveRouteResult driveRouteResult) {
        startPointMarkerView.setPosition(new LatLng(driveRouteResult.getStartPos().getLatitude(), driveRouteResult.getStartPos().getLongitude()));
        endPointMarkerView.setPosition(new LatLng(driveRouteResult.getTargetPos().getLatitude(), driveRouteResult.getTargetPos().getLongitude()));
        simpleRouteLineView.setDrivePath(driveRouteResult.getPaths().get(0));
    }

    /**
     * 开始路径规划
     */
    public void beginDriveRouteSearched(LatLonPoint stratLatLonPoint, LatLonPoint endLatLonPoint, OnPathPlaningCallBack onPathPlaningCallBack) {

        setStartLatLonPoint(stratLatLonPoint);
        setEndLatLonPoint(endLatLonPoint);
        this.callBack = onPathPlaningCallBack;


        if (null != callBack) {
            callBack.onStart();
        }

        if (null == getParam().getStartLatLonPoint() || null == getParam().getEndLatLonPoint()) {
            callBack.onFailed("起点或者终点为null");
            return;
        }


        RouteSearch routeSearch = new RouteSearch(getContext());
        routeSearch.setRouteSearchListener(new RouteSearch.OnRouteSearchListener() {
            @Override
            public void onBusRouteSearched(BusRouteResult busRouteResult, int i) {

            }

            @Override
            public void onDriveRouteSearched(DriveRouteResult driveRouteResult, int i) {
                if (i == 1000) {
                    if (null != callBack) {
                        callBack.onSucccess(driveRouteResult);
                    }
                    if (param.isAuotDrawPath()) {
                        if (isRemove()) {
                            addToMap();
                        }

                        draw(driveRouteResult);
                    }
                } else {
                    if (null != callBack) {
                        callBack.onFailed(GaoDeErrorUtils.getErrorInfo(i));
                    }
                }
            }

            @Override
            public void onWalkRouteSearched(WalkRouteResult walkRouteResult, int i) {

            }

            @Override
            public void onRideRouteSearched(RideRouteResult rideRouteResult, int i) {

            }
        });

        RouteSearch.FromAndTo fromAndTo = new RouteSearch.FromAndTo(stratLatLonPoint, endLatLonPoint);
        RouteSearch.DriveRouteQuery query = new RouteSearch.DriveRouteQuery(fromAndTo, RouteSearch.DRIVING_SINGLE_SHORTEST, null, null, "");
        routeSearch.calculateDriveRouteAsyn(query);

    }

    @Override
    public boolean isRemove() {
        return startPointMarkerView == null || startPointMarkerView.isRemove();
    }


    protected void setStartPointMarkerView(PointMarkerView<W> startPointMarkerView) {
        this.startPointMarkerView = startPointMarkerView;
    }

    protected void setEndPointMarkerView(PointMarkerView<W> endPointMarkerView) {
        this.endPointMarkerView = endPointMarkerView;
    }

    protected void setSimpleRouteLineView(RouteLineView simpleRouteLineView) {
        this.simpleRouteLineView = simpleRouteLineView;
    }

    public PointMarkerView getStartPointMarkerView() {
        return startPointMarkerView;
    }


    public PointMarkerView getEndPointMarkerView() {
        return endPointMarkerView;
    }


    public RouteLineView getSimpleRouteLineView() {
        return simpleRouteLineView;
    }


    public PathPlaningParam getParam() {
        return param;
    }

    public void setParam(PathPlaningParam param) {
        this.param = param;
    }

    public void setStartLatLonPoint(LatLonPoint startLatLonPoint) {
        getParam().setStartLatLonPoint(startLatLonPoint);

    }


    public void setEndLatLonPoint(LatLonPoint endLatLonPoint) {
        getParam().setEndLatLonPoint(endLatLonPoint);
    }


    public static interface OnPathPlaningCallBack {

        void onStart();

        void onSucccess(DriveRouteResult routeResult);

        void onFailed(String errorInfo);
    }


    public static final class Builder<W> extends BaseBuilder {
        private RouteLineView.Builder lineBuilder;
        private PointMarkerView.Builder startMarkBuilder;
        private PointMarkerView.Builder endMarkBuilder;
        private PathPlaningParam param;


        public Builder setRouteLineBuilder(@NonNull RouteLineView.Builder builder) {
            lineBuilder = builder;
            return this;
        }


        public Builder setStartMarkBuilder(@NonNull PointMarkerView.Builder startMarkBuilder) {
            this.startMarkBuilder = startMarkBuilder;

            return this;
        }


        public Builder setEndMarkBuilder(@NonNull PointMarkerView.Builder endMarkBuilder) {
            this.endMarkBuilder = endMarkBuilder;
            return this;
        }

        public Builder setStartMarkerZindex(float zindex) {
            getParam().setStartMarkerZindex(zindex);
            return this;
        }


        public Builder setEndMarkerZindex(float zindex) {
            getParam().setEndMarkerZindex(zindex);
            return this;
        }


        public Builder setArrowLineZindex(float zIndex) {
            getParam().setArrowLineZindex(zIndex);
            return this;
        }


        public Builder setTrafficLineZindex(float zIndex) {
            getParam().setTrafficLineZindex(zIndex);
            return this;
        }


        public Builder setDefaultLineZindex(float zIndex) {
            getParam().setDefaultLineZindex(zIndex);
            return this;
        }


        public Builder setAuotDrawPath(boolean auotDrawPath) {
            getParam().setAuotDrawPath(auotDrawPath);
            return this;
        }


        public Builder setStartLatLonPoint(LatLonPoint startLatLonPoint) {
            getParam().setStartLatLonPoint(startLatLonPoint);
            return this;
        }


        public Builder setEndLatLonPoint(LatLonPoint endLatLonPoint) {
            getParam().setEndLatLonPoint(endLatLonPoint);
            return this;
        }

        public Builder(Context context, AMap map) {
            super(context, map);
            lineBuilder = new RouteLineView.Builder(getContext(), getMap());
            startMarkBuilder = new PointMarkerView.Builder(getContext(), getMap());
            endMarkBuilder = new PointMarkerView.Builder(getContext(), getMap());
            param = new PathPlaningParam();
        }


        public PathPlaningView<W> create() {


            lineBuilder.setArrowLineZindex(getParam().getArrowLineZindex());
            lineBuilder.setDefaultLineZindex(getParam().getDefaultLineZindex());
            lineBuilder.setTrafficLineZindex(getParam().getTrafficLineZindex());

            startMarkBuilder.setZindex(getParam().getStartMarkerZindex());
            endMarkBuilder.setZindex(getParam().getEndMarkerZindex());


            PathPlaningView pathPlaningView = new PathPlaningView<W>(getContext(), getMap(), getParam());
            pathPlaningView.setSimpleRouteLineView(lineBuilder.create());
            pathPlaningView.setStartPointMarkerView(startMarkBuilder.create());
            pathPlaningView.setEndPointMarkerView(endMarkBuilder.create());
            pathPlaningView.addToMap();
            return pathPlaningView;


        }

        public PathPlaningParam getParam() {
            return param;
        }


    }


}
