package com.yisingle.amapview.lib.view;

import android.content.Context;
import android.support.annotation.NonNull;

import com.amap.api.maps.AMap;
import com.amap.api.maps.AMapUtils;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.yisingle.amap.lib.R;
import com.yisingle.amapview.lib.base.view.marker.AbstractMarkerView;
import com.yisingle.amapview.lib.utils.move.MovePathPlanningUtils;

import java.util.List;

/**
 * @author jikun
 * Created by jikun on 2018/7/5.
 */
public class CarMoveOnPathPlaningView1 {

    private final float DISTANCE = 2000f;


    private Context context;

    private AMap aMap;

    /**
     * 移动车辆图标
     */
    private PointMarkerView<String> carMoveView;


    private PathPlaningView<String, String> pathPlaningView;


    private LatLng endLatlng;

    private MovePathPlanningUtils movePathPlanningUtils;


    public CarMoveOnPathPlaningView1(@NonNull Context context, @NonNull AMap aMap) {
        this.context = context;
        this.aMap = aMap;


        carMoveView = new PointMarkerView.Builder(context, aMap)
                .setIcon(BitmapDescriptorFactory.fromResource(R.mipmap.move_car))
                .setAnchor(0.5f, 0.5f).create();


        pathPlaningView = new PathPlaningView.Builder(context, aMap)
                .create();


        movePathPlanningUtils = new MovePathPlanningUtils(context, pathPlaningView);


        carMoveView.setMoveListener(new AbstractMarkerView.OnMoveListener() {
            @Override
            public void onMove(LatLng latLng) {
                //如果endLatln为null 不用画线
                if (null == endLatlng || null == latLng) {
                    return;
                }

                movePathPlanningUtils.moveCalcuDistanceTime(latLng, endLatlng);


            }
        });
    }


    public void startMove(List<LatLng> list, LatLng endLatlng) {
        this.endLatlng = endLatlng;
        startMove(list);
    }

    private void startMove(List<LatLng> list) {

        if (null != list && list.size() > 0 && null != carMoveView) {

            LatLng nowLatLng = carMoveView.getPosition();
            if (null == nowLatLng) {
                //如果carMoveView的坐标点为空 那么直接移动
                carMoveView.startMove(list, false);

            } else {
                //----1/2=0.5  强制转换为0 实际为0
                int middle = list.size() / 2;
                LatLng middleLatLng = list.get(middle);
                //当传递过来的坐标数组中间的坐标   与marker现在的坐标距离大于distance
                // 那么跳过以前坐标数组  直接到现在的坐标运动
                if (AMapUtils.calculateLineDistance(nowLatLng, nowLatLng) >= DISTANCE) {
                    carMoveView.startMove(list, false);
                } else {
                    carMoveView.startMove(list, true);
                }

            }


        }


    }


    public void destory() {
        if (null != carMoveView) {
            carMoveView.destory();
        }


        if (null != pathPlaningView) {
            pathPlaningView.destory();
            movePathPlanningUtils.setPathPlaningView(null);
        }

        if (null != movePathPlanningUtils) {
            movePathPlanningUtils.detory();
        }


    }


}
