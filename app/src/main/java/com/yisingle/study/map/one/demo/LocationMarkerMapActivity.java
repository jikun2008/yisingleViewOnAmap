package com.yisingle.study.map.one.demo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.TextureMapView;
import com.amap.api.maps.model.LatLng;
import com.yisingle.amapview.lib.view.LocationMarkerView;
import com.yisingle.study.map.one.R;
import com.yisingle.study.map.one.base.BaseMapActivity;

/**
 * @author jikun
 * Created by jikun on 2018/4/11.
 */
public class LocationMarkerMapActivity extends BaseMapActivity {

    private TextureMapView textureMapView;


    private LocationMarkerView<String> locationMarkerView;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_maker_map);
        textureMapView = findViewById(R.id.textureMapView);
        initMapView(savedInstanceState, textureMapView);


    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        locationMarkerView.destory();

    }

    @Override
    protected void afterMapViewLoad() {

        locationMarkerView =
                new LocationMarkerView.Builder(getApplicationContext(), getAmap())
                        //设置没有方向传感器的图片资源ID
                        .create();

        locationMarkerView.startLocation();


        moveToCamera(new LatLng(30.551662, 104.068551));


    }


    public void testAddMarker(View view) {
        locationMarkerView.addToMap();
        locationMarkerView.startLocation();
    }


    public void testRemoveMarker(View view) {
        locationMarkerView.removeFromMap();

    }


    public void testLocation(View view) {

        if (null != locationMarkerView.getMarker() && null != locationMarkerView.getMarker().getPosition()) {
            moveToCamera(locationMarkerView.getMarker().getPosition());
        }

    }


    public void testShowInfoWindow(View view) {
        boolean isShow = locationMarkerView.isShowInfoWindow();
        if (isShow) {
            locationMarkerView.hideInfoWindow();
        } else {
            locationMarkerView.showInfoWindow("显示的文字效果");
        }

        TextView textView = (TextView) view;
        textView.setText(isShow ? "显示Window" : "隐藏Window");

    }


    public void showOrHideView(View view) {
        boolean isShow = !locationMarkerView.isVisible();
        locationMarkerView.setVisible(isShow);
        TextView textView = (TextView) view;
        textView.setText(!isShow ? "显示Marker" : "隐藏Marker");
    }

    private void moveToCamera(LatLng latLng) {
        //设置缩放级别
        float zoom = 17;
        if (null != getAmap()) {
            //zoom - 缩放级别，[3-20]。
            getAmap().animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));
        }

    }


}
