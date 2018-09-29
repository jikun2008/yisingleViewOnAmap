package com.yisingle.amapview.lib.view;

import android.content.Context;
import android.util.Log;

import com.amap.api.maps.AMap;
import com.amap.api.maps.AMapUtils;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.yisingle.amap.lib.R;
import com.yisingle.amapview.lib.base.BaseBuilder;
import com.yisingle.amapview.lib.base.BaseView;
import com.yisingle.amapview.lib.base.view.marker.AbstractMarkerView;
import com.yisingle.amapview.lib.base.view.marker.BaseMarkerView;
import com.yisingle.amapview.lib.utils.move.MovePathPlanningUtils;

import java.util.List;

/**
 * @author jikun
 * Created by jikun on 2018/7/18.
 */
public class CarMoveOnPathPlaningView<C, S, E> extends BaseView {

    private final float DISTANCE = 2000f;

    /**
     * 移动车辆图标
     */
    private PointMarkerView<C> carMoveMarkerView;


    /**
     * 路径规划
     */
    private PathPlaningView<S, E> pathPlaningView;


    private MovePathPlanningUtils movePathPlanningUtils;

    private LatLng endLatlng;


    private CarMoveOnPathPlaningView(Context context, AMap amap, PointMarkerView<C> carView, PathPlaningView<S, E> pathPlaningView) {
        super(context, amap);
        this.carMoveMarkerView = carView;
        this.pathPlaningView = pathPlaningView;
        movePathPlanningUtils = new MovePathPlanningUtils(context, pathPlaningView);
        carMoveMarkerView.setMoveListener(new AbstractMarkerView.OnMoveListener() {
            @Override
            public void onMove(LatLng latLng) {
                //如果endLatln为null 不用画线
                Log.e("测试代码", "测试代码onMove----onMove----onMove------onMove");
                if (null == endLatlng || null == latLng) {
                    return;
                }
                movePathPlanningUtils.moveCalcuDistanceTime(latLng, endLatlng);
            }
        });
    }


    public void setListener(MovePathPlanningUtils.OnDistanceDurationListener listener) {
        if (null != movePathPlanningUtils) {
            movePathPlanningUtils.setOnDistanceDurationListener(listener);
        }
    }


    public void startMove(List<LatLng> list, int time) {

        if (null != list && list.size() > 0 && null != carMoveMarkerView) {

            LatLng nowLatLng = carMoveMarkerView.getPosition();
            if (null == nowLatLng) {
                //如果carMoveView的坐标点为空 那么直接移动
                carMoveMarkerView.startMove(list, time, true);

            } else {
                //----1/2=0.5  强制转换为0 实际为0
                int middle = list.size() / 2;
                LatLng middleLatLng = list.get(middle);
                //当传递过来的坐标数组中间的坐标   与marker现在的坐标距离大于distance
                // 那么跳过以前坐标数组  直接到现在的坐标运动
                if (AMapUtils.calculateLineDistance(nowLatLng, nowLatLng) >= DISTANCE) {
                    carMoveMarkerView.startMove(list, time, true);
                } else {
                    carMoveMarkerView.startMove(list, time, true);
                }

            }


        }


    }

    public void bindMoveCarInfoWindowView(BaseMarkerView.BaseInfoWindowView<C> infoWindowView) {
        if (null != carMoveMarkerView) {
            carMoveMarkerView.bindInfoWindowView(infoWindowView);
        }
    }


    public void showMoveCarInfoWindow(C data) {
        if (null != carMoveMarkerView) {
            carMoveMarkerView.showInfoWindow(data);
        }
    }

    public void hideMoveCarInfoWindow() {
        if (null != carMoveMarkerView) {
            carMoveMarkerView.hideInfoWindow();
        }
    }


    public PointMarkerView<C> getCarMoveMarkerView() {
        return carMoveMarkerView;
    }

    public PathPlaningView<S, E> getPathPlaningView() {
        return pathPlaningView;
    }

    public MovePathPlanningUtils getMovePathPlanningUtils() {
        return movePathPlanningUtils;
    }

    public LatLng getEndLatlng() {
        return endLatlng;
    }

    @Deprecated
    @Override
    public void addToMap() {

    }

    @Override
    public void removeFromMap() {
        if (null != carMoveMarkerView) {
            carMoveMarkerView.removeFromMap();
        }

        if (null != pathPlaningView) {
            pathPlaningView.removeFromMap();
        }

    }

    @Override
    public boolean isRemove() {
        return carMoveMarkerView == null || carMoveMarkerView.isRemove();
    }

    @Override
    public void destory() {
        super.destory();
        if (null != carMoveMarkerView) {
            carMoveMarkerView.destory();
        }


        if (null != pathPlaningView) {
            pathPlaningView.destory();
            movePathPlanningUtils.setPathPlaningView(null);
        }

        if (null != movePathPlanningUtils) {
            movePathPlanningUtils.detory();
        }
    }

    public void setCarMoveMarkerView(PointMarkerView<C> carMoveMarkerView) {
        this.carMoveMarkerView = carMoveMarkerView;
    }

    public void setPathPlaningView(PathPlaningView<S, E> pathPlaningView) {
        this.pathPlaningView = pathPlaningView;
    }


    public int getCameraPaddingTop() {
        int startPadding = carMoveMarkerView.getCameraPaddingTop() + carMoveMarkerView.getInfoHeight();

        int endPadding = pathPlaningView.getCameraPaddingTop();

        Log.e("测试代码", "测试代码startPadding=" + startPadding + "---endPadding=" + endPadding);


        return startPadding > endPadding ? startPadding : endPadding;
    }


    public int getCameraPaddingLeft() {

        int startPadding = carMoveMarkerView.getCameraPaddingLeft() + carMoveMarkerView.getInfoWidth() / 2;

        int endPadding = pathPlaningView.getCameraPaddingLeft();


        return startPadding > endPadding ? startPadding : endPadding;
    }


    public int getCameraPaddingRight() {

        int startPadding = carMoveMarkerView.getCameraPaddingRight() + carMoveMarkerView.getInfoWidth() / 2;

        int endPadding = pathPlaningView.getCameraPaddingRight();


        return startPadding > endPadding ? startPadding : endPadding;
    }


    public int getCameraPaddingBottom() {

        int startPadding = carMoveMarkerView.getCameraPaddingBottom();

        int endPadding = pathPlaningView.getCameraPaddingBottom();


        return startPadding > endPadding ? startPadding : endPadding;
    }


    public static final class Builder extends BaseBuilder {
        private PointMarkerView.Builder carMoveMarkerBuilder;

        private PathPlaningView.Builder pathPlaningViewBuilder;

        public Builder(Context context, AMap map) {
            super(context, map);
            carMoveMarkerBuilder = new PointMarkerView.Builder(getContext(), getMap());
            carMoveMarkerBuilder.setIcon(BitmapDescriptorFactory.fromResource(R.mipmap.move_car));
            pathPlaningViewBuilder = new PathPlaningView.Builder(getContext(), getMap());

        }

        public Builder setCarMoveMarkerBuilder(PointMarkerView.Builder carMoveMarkerBuilder) {
            this.carMoveMarkerBuilder = carMoveMarkerBuilder;
            return this;
        }

        public Builder setPathPlaningViewBuilder(PathPlaningView.Builder pathPlaningViewBuilder) {
            this.pathPlaningViewBuilder = pathPlaningViewBuilder;
            return this;
        }

        public <C, S, E> CarMoveOnPathPlaningView<C, S, E> create() {

            pathPlaningViewBuilder.getStartMarkBuilder().setVisible(false)
                    .setTextVisible(false);
            PathPlaningView<S, E> pathPlaningView = pathPlaningViewBuilder.create();

            carMoveMarkerBuilder.setAnchor(0.5f, 0.5f);
            PointMarkerView<C> carMoveView = carMoveMarkerBuilder.create();

            CarMoveOnPathPlaningView<C, S, E> carMoveOnPathPlaningView = new CarMoveOnPathPlaningView<>(getContext(), getMap(), carMoveView, pathPlaningView);


            return carMoveOnPathPlaningView;
        }


    }
}
