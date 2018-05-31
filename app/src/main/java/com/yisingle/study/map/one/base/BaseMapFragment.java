package com.yisingle.study.map.one.base;

import android.os.Bundle;
import android.support.v4.app.Fragment;


import com.amap.api.maps.AMap;
import com.amap.api.maps.AMapOptions;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.TextureMapView;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.LatLngBounds;
import com.amap.api.navi.model.NaviLatLng;

/**
 * @author jikun
 * Created by jikun on 2018/3/30.
 */

public abstract class BaseMapFragment extends Fragment {

    private TextureMapView textureMapView;


    protected TextureMapView getMapView() {
        return textureMapView;
    }

    protected void initMapView(Bundle savedInstanceState, TextureMapView textureMapView) {
        this.textureMapView = textureMapView;


        if (null != getMapView() && null != getMapView().getMap()) {

            getMapView().getMap().setOnMapLoadedListener(new AMap.OnMapLoadedListener() {
                @Override
                public void onMapLoaded() {
                    //地图MapView加载完成后回调 用来UiSetting设置参数
                    afterMapViewLoad();
                    initMapUiSetting();

                }
            });

            //调用TextureMapView.onCreate方法来
            getMapView().onCreate(savedInstanceState);

        }
    }

    /**
     * 地图MapView加载完成后回调
     */
    protected abstract void afterMapViewLoad();

    /**
     * 方法必须重写
     */
    @Override

    public void onResume() {
        super.onResume();
        if (null != getMapView()) {
            getMapView().onResume();
        }

    }

    /**
     * 方法必须重写
     */
    @Override
    public void onPause() {
        super.onPause();
        if (null != getMapView()) {
            getMapView().onPause();
        }
    }

    /**
     * 方法必须重写
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (null != getMapView()) {
            getMapView().onSaveInstanceState(outState);
        }
    }

    /**
     * 方法必须重写
     */
    @Override
    public void onDestroy() {
        super.onDestroy();

        if (null != getMapView()) {
            getMapView().onDestroy();
        }
    }


    protected void initMapUiSetting() {

        //实例化UiSettings类对象
        UiSettings uiSettings = textureMapView.getMap().getUiSettings();
        //设置是否允许显示缩放按钮
        uiSettings.setZoomControlsEnabled(false);
        //设置Logo在底部右下角
        uiSettings.setLogoPosition(AMapOptions.LOGO_POSITION_BOTTOM_LEFT);
        //禁止旋转手势
        uiSettings.setRotateGesturesEnabled(false);
        //禁止倾斜手势
        uiSettings.setTiltGesturesEnabled(false);

    }

    /**
     * 在导航的地图MapView上移动视角
     */
    public void moveCameraMapView(NaviLatLng start, NaviLatLng end) {

        LatLngBounds.Builder builder = new LatLngBounds.Builder();


        builder.include(new LatLng(start.getLatitude(), start.getLongitude()));
        builder.include(new LatLng(end.getLatitude(), end.getLongitude()));
        LatLngBounds latLngBounds = builder.build();
        //newLatLngBoundsRect(LatLngBounds latlngbounds,
        //int paddingLeft,设置经纬度范围和mapView左边缘的空隙。
        //int paddingRight,设置经纬度范围和mapView右边缘的空隙
        //int paddingTop,设置经纬度范围和mapView上边缘的空隙。
        //int paddingBottom)设置经纬度范围和mapView下边缘的空隙。
        if (null != getMapView() && null != getMapView().getMap()) {
            getMapView().getMap()
                    .animateCamera(CameraUpdateFactory.newLatLngBoundsRect(latLngBounds, 80, 80, 80, 80));
        }


    }
}
