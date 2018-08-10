package com.yisingle.amapview.lib.view;

import android.content.Context;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.util.Log;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.model.BitmapDescriptor;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.LatLngBounds;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.route.BusRouteResult;
import com.amap.api.services.route.DrivePath;
import com.amap.api.services.route.DriveRouteResult;
import com.amap.api.services.route.RideRouteResult;
import com.amap.api.services.route.RouteSearch;
import com.amap.api.services.route.WalkRouteResult;
import com.yisingle.amapview.lib.base.BaseBuilder;
import com.yisingle.amapview.lib.base.BaseView;
import com.yisingle.amapview.lib.base.view.marker.BaseMarkerView;
import com.yisingle.amapview.lib.param.PathPlaningParam;
import com.yisingle.amapview.lib.utils.GaoDeErrorUtils;

/**
 * @author jikun
 * Created by jikun on 2018/5/15.
 */
public class PathPlaningView<S, E> extends BaseView {


    private final int successCode = 1000;

    private final static String TAG = PathPlaningView.class.getSimpleName();

    private PointMarkerView<S> startPointMarkerView;


    private PointMarkerView<E> endPointMarkerView;


    private RouteLineView simpleRouteLineView;

    private PathPlaningParam param;


    private OnPathPlaningCallBack callBack;

    private DrivePath drivePath;

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
        if (null != driveRouteResult && null != driveRouteResult.getPaths() && driveRouteResult.getPaths().size() > 0) {
            startPointMarkerView.setPosition(new LatLng(driveRouteResult.getStartPos().getLatitude(), driveRouteResult.getStartPos().getLongitude()));
            endPointMarkerView.setPosition(new LatLng(driveRouteResult.getTargetPos().getLatitude(), driveRouteResult.getTargetPos().getLongitude()));
            drivePath = driveRouteResult.getPaths().get(0);
            simpleRouteLineView.draw(drivePath);
        }
    }

    /**
     * 可以根据数据只画Line
     *
     * @param drawData DrawData
     */
    public void drawLine(RouteLineView.DrawData drawData) {
        simpleRouteLineView.draw(drawData);
    }


    public RouteLineView.DrawData getRouteLineDrawData() {
        if (null != simpleRouteLineView) {
            return simpleRouteLineView.getDrawData();
        } else {
            return null;
        }


    }

    public void setText(String startText, String endText) {
        setStartText(startText);
        setEndText(endText);
    }

    public void setStartText(String text) {
        startPointMarkerView.setText(text);
    }

    public void setStartIcon(BitmapDescriptor icon) {
        startPointMarkerView.setIcon(icon);
    }

    public void bindStartInfoWindowView(@NonNull BaseMarkerView.BaseInfoWindowView<S> infoWindowView) {
        startPointMarkerView.bindInfoWindowView(infoWindowView);
    }

    public void bindEndInfoWindowView(@NonNull BaseMarkerView.BaseInfoWindowView<E> infoWindowView) {
        endPointMarkerView.bindInfoWindowView(infoWindowView);
    }


    public void setStartTextSize(float textsize) {
        startPointMarkerView.setTextSize(textsize);
    }


    public void setStartTextColor(@ColorInt int color) {
        startPointMarkerView.setTextColor(color);
    }


    public void setEndIcon(BitmapDescriptor icon) {
        endPointMarkerView.setIcon(icon);
    }


    public void setEndText(String text) {
        endPointMarkerView.setText(text);
    }

    public void setEndTextSize(float textsize) {
        endPointMarkerView.setTextSize(textsize);
    }


    public void setEndTextColor(@ColorInt int color) {
        endPointMarkerView.setTextColor(color);
    }


    /**
     * 开始路径规划
     */
    public void beginDriveRouteSearched(LatLonPoint stratLatLonPoint, LatLonPoint endLatLonPoint, final boolean isAuotDrawPath, OnPathPlaningCallBack onPathPlaningCallBack) {

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
                if (i == successCode) {

                    if (isAuotDrawPath) {
                        draw(driveRouteResult);
                    }

                    if (null != callBack) {
                        callBack.onSucccess(driveRouteResult);
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


    protected void setStartPointMarkerView(PointMarkerView<S> startPointMarkerView) {
        this.startPointMarkerView = startPointMarkerView;
    }

    protected void setEndPointMarkerView(PointMarkerView<E> endPointMarkerView) {
        this.endPointMarkerView = endPointMarkerView;
    }

    protected void setSimpleRouteLineView(RouteLineView simpleRouteLineView) {
        this.simpleRouteLineView = simpleRouteLineView;
    }

    public PointMarkerView<S> getStartPointMarkerView() {
        return startPointMarkerView;
    }


    public PointMarkerView<E> getEndPointMarkerView() {
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


    public void moveCamera() {
        if (null != getAmap() && null != getParam().getStartLatLonPoint() && null != getParam().getEndLatLonPoint()) {
            LatLng start = new LatLng(getParam().getStartLatLonPoint().getLatitude(), getParam().getStartLatLonPoint().getLongitude());

            LatLng end = new LatLng(getParam().getEndLatLonPoint().getLatitude(), getParam().getEndLatLonPoint().getLongitude());
            LatLngBounds latLngBounds = new LatLngBounds(start, end);
            getAmap().moveCamera(CameraUpdateFactory.newLatLngBounds(latLngBounds, 0));
        } else {
            Log.e(TAG, TAG + ":Amap==null is" + (getAmap() == null) +
                    "---getStartLatLonPoint==null" + (getParam().getStartLatLonPoint() == null) +
                    "---getEndLatLonPoint==null" + (getParam().getEndLatLonPoint() == null)
            );
        }

    }

    public LatLonPoint getStartLatLonPoint() {
        return getParam().getStartLatLonPoint();
    }

    public LatLonPoint getEndLatLonPoint() {
        return getParam().getEndLatLonPoint();
    }


    public interface OnPathPlaningCallBack {

        /**
         * 路径规划开始
         */
        void onStart();

        /**
         * 路径规划成功
         *
         * @param routeResult DriveRouteResult
         */
        void onSucccess(DriveRouteResult routeResult);

        /**
         * 路径规划失败
         *
         * @param errorInfo 失败信息
         */
        void onFailed(String errorInfo);
    }


    public static final class Builder extends BaseBuilder {
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


        public Builder setStartMarkerZindex(float zindex) {
            getParam().setStartMarkerZindex(zindex);
            return this;
        }


        public Builder setEndMarkerZindex(float zindex) {
            getParam().setEndMarkerZindex(zindex);
            return this;
        }


        /**
         * 路径规划的起点
         *
         * @param startLatLonPoint 路径规划的起点
         * @return Builder
         */
        public Builder setStartLatLonPoint(LatLonPoint startLatLonPoint) {
            getParam().setStartLatLonPoint(startLatLonPoint);
            return this;
        }


        /**
         * 路径规划的终点
         *
         * @param endLatLonPoint 路径规划的终点
         * @return Builder
         */
        public Builder setEndLatLonPoint(LatLonPoint endLatLonPoint) {
            getParam().setEndLatLonPoint(endLatLonPoint);
            return this;
        }

        public RouteLineView.Builder getLineBuilder() {
            return lineBuilder;
        }

        public PointMarkerView.Builder getStartMarkBuilder() {
            return startMarkBuilder;
        }

        public PointMarkerView.Builder getEndMarkBuilder() {
            return endMarkBuilder;
        }

        public Builder(Context context, AMap map) {
            super(context, map);
            lineBuilder = new RouteLineView.Builder(getContext(), getMap());
            startMarkBuilder = new PointMarkerView.Builder(getContext(), getMap());
            endMarkBuilder = new PointMarkerView.Builder(getContext(), getMap());
            param = new PathPlaningParam();
        }


        public <S, E> PathPlaningView<S, E> create() {


            lineBuilder.setArrowLineZindex(getParam().getArrowLineZindex());
            lineBuilder.setDefaultLineZindex(getParam().getDefaultLineZindex());
            lineBuilder.setTrafficLineZindex(getParam().getTrafficLineZindex());

            startMarkBuilder.setZindex(getParam().getStartMarkerZindex());
            endMarkBuilder.setZindex(getParam().getEndMarkerZindex());


            PathPlaningView<S, E> pathPlaningView = new PathPlaningView<>(getContext(), getMap(), getParam());
            pathPlaningView.setSimpleRouteLineView(lineBuilder.create());
            PointMarkerView<S> startMarkerView = startMarkBuilder.create();
            pathPlaningView.setStartPointMarkerView(startMarkerView);

            PointMarkerView<E> endMarkerView = endMarkBuilder.create();
            pathPlaningView.setEndPointMarkerView(endMarkerView);
            return pathPlaningView;


        }

        public PathPlaningParam getParam() {
            return param;
        }


    }

    public DrivePath getDrivePath() {
        return drivePath;
    }

    public void setDrivePath(DrivePath drivePath) {
        this.drivePath = drivePath;
    }


    public int getCameraPaddingTop() {
        int startPadding = startPointMarkerView.getCameraPaddingTop();

        int endPadding = endPointMarkerView.getCameraPaddingTop();


        return startPadding > endPadding ? startPadding : endPadding;
    }


    public int getCameraPaddingLeft() {

        int startPadding = startPointMarkerView.getCameraPaddingLeft();

        int endPadding = endPointMarkerView.getCameraPaddingLeft();


        return startPadding > endPadding ? startPadding : endPadding;
    }


    public int getCameraPaddingRight() {

        int startPadding = startPointMarkerView.getCameraPaddingRight();

        int endPadding = endPointMarkerView.getCameraPaddingRight();


        return startPadding > endPadding ? startPadding : endPadding;
    }


    public int getCameraPaddingBottom() {

        int startPadding = startPointMarkerView.getCameraPaddingBottom();

        int endPadding = endPointMarkerView.getCameraPaddingBottom();


        return startPadding > endPadding ? startPadding : endPadding;
    }
}
