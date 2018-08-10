package com.yisingle.amapview.lib.utils.move;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.util.Pair;

import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.utils.SpatialRelationUtil;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.route.BusRouteResult;
import com.amap.api.services.route.DriveRouteResult;
import com.amap.api.services.route.RideRouteResult;
import com.amap.api.services.route.RouteSearch;
import com.amap.api.services.route.WalkRouteResult;
import com.yisingle.amapview.lib.base.view.polyline.BasePolyLineView;
import com.yisingle.amapview.lib.base.view.polyline.BaseTrafficMutilyPolyLineView;
import com.yisingle.amapview.lib.utils.ditance.DistanceUtils;
import com.yisingle.amapview.lib.view.PathPlaningView;
import com.yisingle.amapview.lib.view.RouteLineView;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


/**
 * @author jikun
 * Created by jikun on 2018/7/9.
 */
public class MovePathPlanningUtils {


    private final int successCode = 1000;

    private Context context;

    /**
     * 是否正在路径规划
     */
    private boolean isRouteSearching = false;

    /**
     * 是否需要进行路径规划
     */
    private boolean isRouteSearchSuccess = false;


    private ThreadPoolExecutor threadPoolExecutor;


    private PathPlaningView pathPlaningView;

    private DistanceDurationData distanceDurationData;

    private OnDistanceDurationListener onDistanceDurationListener;


    public MovePathPlanningUtils(Context context, PathPlaningView pathPlaningView) {
        this.context = context;
        this.pathPlaningView = pathPlaningView;


        //线程池  采用  线程池数为1 的
        threadPoolExecutor = new ThreadPoolExecutor(
                1,
                1,
                6000,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<Runnable>(5), new ThreadPoolExecutor.DiscardOldestPolicy());


    }

    private void searchRoute(LatLonPoint stratLatLonPoint, LatLonPoint endLatLonPoint) {

        //searchRoute
        isRouteSearching = true;
        RouteSearch routeSearch = new RouteSearch(getContext());
        routeSearch.setRouteSearchListener(new RouteSearch.OnRouteSearchListener() {
            @Override
            public void onBusRouteSearched(BusRouteResult busRouteResult, int i) {
                isRouteSearching = false;
            }

            @Override
            public void onDriveRouteSearched(DriveRouteResult driveRouteResult, int i) {
                isRouteSearching = false;
                if (i == successCode) {
                    if (null != driveRouteResult && null != driveRouteResult.getPaths() && driveRouteResult.getPaths().size() > 0) {
                        isRouteSearchSuccess = true;
                        if (null != pathPlaningView) {
                            Log.e("测试代码", "测试代码-----------");
                            pathPlaningView.draw(driveRouteResult);

                            if (null != pathPlaningView && null != pathPlaningView.getDrivePath()) {
                                distanceDurationData = new DistanceDurationData(pathPlaningView.getDrivePath().getDistance(), pathPlaningView.getDrivePath().getDuration());
                                if (null != onDistanceDurationListener) {
                                    onDistanceDurationListener.onDataCallBack(distanceDurationData);
                                }
                            }
                        }
                    }

                } else {

                    //searchRoute--failed
                }
            }

            @Override
            public void onWalkRouteSearched(WalkRouteResult walkRouteResult, int i) {
                isRouteSearching = false;
            }

            @Override
            public void onRideRouteSearched(RideRouteResult rideRouteResult, int i) {
                isRouteSearching = false;
            }
        });


        RouteSearch.FromAndTo fromAndTo = new RouteSearch.FromAndTo(stratLatLonPoint, endLatLonPoint);
        RouteSearch.DriveRouteQuery query = new RouteSearch.DriveRouteQuery(fromAndTo, RouteSearch.DRIVING_SINGLE_SHORTEST, null, null, "");
        routeSearch.calculateDriveRouteAsyn(query);


    }


    public void moveCalcuDistanceTime(LatLng move, LatLng end) {
        moveCalcuDistanceTime(new LatLonPoint(move.latitude, move.longitude), new LatLonPoint(end.latitude, end.longitude));
    }


    private float totalDistance = 0f;

    private LatLonPoint lastLatLonPoint = null;

    /**
     * 移动并计算距离
     */
    public void moveCalcuDistanceTime(final LatLonPoint move, LatLonPoint end) {

        if (isRouteSearching) {
            //如果正在路径规划,不进行计算  直接返回
            return;
        }

        if (!isRouteSearchSuccess) {
            //判断如果需要路径规划
            searchRoute(move, end);
        } else {


            if (lastLatLonPoint != null) {
                totalDistance = totalDistance + DistanceUtils.calculateTwoPointDistance(lastLatLonPoint, move);
                if (totalDistance < 2f) {
                    return;

                } else {
                    totalDistance = 0f;
                }


            }
            lastLatLonPoint = move;

            threadPoolExecutor.execute(new Runnable() {
                @Override
                public void run() {

                    calulateRouteLineView(move);


                }
            });

        }

    }

    private void calulateRouteLineView(LatLonPoint move) {
        if (null != pathPlaningView && null != pathPlaningView.getSimpleRouteLineView()) {
            RouteLineView routeLineView = pathPlaningView.getSimpleRouteLineView();
            if (null != routeLineView.getArrowPolyLineView()) {
                calculatePolyLineView(move, routeLineView.getArrowPolyLineView());
            }
            if (null != routeLineView.getDefaultBasePolyLineView()) {
                calculatePolyLineView(move, routeLineView.getDefaultBasePolyLineView());
            }
            if (null != routeLineView.getTrafficPolyLinewView()) {
                calculateTrafficePolyLineViews(move, routeLineView.getTrafficPolyLinewView());
            }

        }

    }

    private void calculatePolyLineView(LatLonPoint move, @NonNull final BasePolyLineView basePolyLineView) {

        if (null != basePolyLineView && null != basePolyLineView.getLatLngList() && basePolyLineView.getLatLngList().size() > 0) {
            List<LatLng> list = basePolyLineView.getLatLngList();

            calculatListLatLng(move, list, new OnCallBack() {
                @Override
                public void onListCallBack(List<LatLng> list) {
                    basePolyLineView.setPoints(list);
                }

                @Override
                public void nearByLastPoint() {


                }

                @Override
                public void onFarAwayPath() {


                }

                @Override
                public void onMoveDistance(float moveDistance) {

                }
            });
        }

    }

    private void calculateTrafficePolyLineViews(LatLonPoint move, @NonNull BaseTrafficMutilyPolyLineView trafficView) {

        final List<BasePolyLineView> polyLineViewList = trafficView.getTrafficPolyLineViews();
        if (null != polyLineViewList && polyLineViewList.size() > 0) {
            final BasePolyLineView basePolyLineView = polyLineViewList.get(0);
            List<LatLng> list = basePolyLineView.getLatLngList();

            calculatListLatLng(move, list, new OnCallBack() {
                @Override
                public void onListCallBack(List<LatLng> list) {
                    basePolyLineView.setPoints(list);
                }

                @Override
                public void nearByLastPoint() {
                    basePolyLineView.destory();
                    polyLineViewList.remove(0);
                }

                @Override
                public void onFarAwayPath() {
                    isRouteSearchSuccess = false;

                }

                @Override
                public void onMoveDistance(float moveDistance) {

                    if (null != distanceDurationData) {

                        calculatDistanceAndDuration(distanceDurationData, moveDistance);

                        if (null != onDistanceDurationListener) {
                            onDistanceDurationListener.onDataCallBack(distanceDurationData);
                        }

                    }
                }
            });
        }

    }


    /**
     * 计算距离和时间
     */
    private void calculatDistanceAndDuration(@NonNull DistanceDurationData data, float moveDistance) {
        if (data.getDistance() <= 0) {
            data.setDistance(1);
        }
        if (0 != data.getDuration()) {
            BigDecimal speed = new BigDecimal(data.getDistance()).divide(new BigDecimal(data.getDuration()), 2, RoundingMode.HALF_DOWN);
            data.setDistance(data.getDistance() - moveDistance);
            if (0 != speed.intValue()) {
                long nowduration = new BigDecimal(data.getDistance()).divide(speed, 2, RoundingMode.HALF_DOWN).longValue();
                data.setDuration(nowduration);
            }


        }

        if (data.getDistance() <= 0) {
            data.setDistance(1);
        }
        if (data.getDuration() <= 0) {
            data.setDuration(60);
        }


    }


    private void calculatListLatLng(@NonNull LatLonPoint move, @NonNull List<LatLng> latLngList, OnCallBack onCallBack) {
        Pair<Integer, LatLng> pair = SpatialRelationUtil.calShortestDistancePoint(latLngList, new LatLng(move.getLatitude(), move.getLongitude()));

        if (null == pair) {
            return;
        }
        float shortDistance = DistanceUtils.calculateTwoPointDistance(pair.second, latLngList.get(0));
        if (shortDistance == 0 && latLngList.size() >= 2) {
            float ditance = DistanceUtils.calculateTwoPointDistance(latLngList.get(0), latLngList.get(1));
            if (ditance > 50) {
                if (null != onCallBack) {
                    onCallBack.onFarAwayPath();
                }

            } else if (ditance < 10 && latLngList.size() == 2) {
                if (null != onCallBack) {
                    onCallBack.nearByLastPoint();
                }
            }

        }
        latLngList.set(pair.first, new LatLng(move.getLatitude(), move.getLongitude()));

        latLngList.subList(0, pair.first).clear();

        if (null != onCallBack) {
            onCallBack.onMoveDistance(shortDistance);
            onCallBack.onListCallBack(latLngList);
        }


    }


    public PathPlaningView getPathPlaningView() {
        return pathPlaningView;
    }

    public void setPathPlaningView(PathPlaningView pathPlaningView) {
        this.pathPlaningView = pathPlaningView;
    }

    public Context getContext() {
        return context;
    }

    public OnDistanceDurationListener getOnDistanceDurationListener() {
        return onDistanceDurationListener;
    }

    public void setOnDistanceDurationListener(OnDistanceDurationListener onDistanceDurationListener) {
        this.onDistanceDurationListener = onDistanceDurationListener;
    }

    public void detory() {
        threadPoolExecutor.shutdownNow();
        pathPlaningView = null;
        onDistanceDurationListener = null;
    }


    private interface OnCallBack {


        void onListCallBack(List<LatLng> list);

        /**
         * 当接近最后一个点
         */
        void nearByLastPoint();

        /**
         * 原来当前的路线了 可以进行路径规划
         */
        void onFarAwayPath();

        /**
         * 移动过的距离
         *
         * @param moveDistance
         */
        void onMoveDistance(float moveDistance);

    }


    public  interface OnDistanceDurationListener {

        void onDataCallBack(DistanceDurationData data);

    }

    public static class DistanceDurationData {
        private float distance;
        private long duration;

        public DistanceDurationData(float distance, long duration) {
            this.distance = distance;
            this.duration = duration;
        }


        public float getDistance() {
            return distance;
        }

        public void setDistance(float distance) {
            this.distance = distance;
        }

        public long getDuration() {
            return duration;
        }

        public void setDuration(long duration) {
            this.duration = duration;
        }

        @Override
        public String toString() {
            return "DistanceDurationData{" +
                    "distance=" + distance +
                    ", duration=" + duration +
                    '}';
        }
    }


}
