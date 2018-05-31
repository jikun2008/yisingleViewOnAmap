package com.yisingle.study.map.one.demo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.TextureMapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.LatLngBounds;
import com.amap.api.maps.model.PolylineOptions;
import com.yisingle.amapview.lib.view.MoveMarkerView;
import com.yisingle.study.map.one.R;
import com.yisingle.study.map.one.TestDataUtils;
import com.yisingle.study.map.one.base.BaseMapActivity;

import java.util.ArrayList;
import java.util.List;


/**
 * @author jikun
 * Created by jikun on 2018/4/27.
 */
public class MoveMarkerMapActivty extends BaseMapActivity {

    private TextureMapView textureMapView;

    private MoveMarkerView moveMarkerView;

    @Override
    protected void afterMapViewLoad() {

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_move_marker_map);
        textureMapView = findViewById(R.id.textureMapView);
        initMapView(savedInstanceState, textureMapView);

        //移动并画移动的坐标点线
        moveToCameraAndDrawLine();


        moveMarkerView = new MoveMarkerView.Builder(getApplicationContext(), getAmap())
                .setIcon(BitmapDescriptorFactory.fromResource(R.mipmap.car)).create();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        moveMarkerView.destory();
    }

    public void stopMove(View view) {
        moveMarkerView.stopMove();
    }

    public void startMoveNow(View view) {
        moveMarkerView.startMove(TestDataUtils.readLatLngs(), 20);

    }

    public void startOtherMove(View view) {
        moveMarkerView.startMove(TestDataUtils.readLatLngs1(), 30);

    }


    public void addToMap(View view) {
        moveMarkerView.addToMap();
        moveMarkerView.startMove(TestDataUtils.readLatLngs(), 20);
    }

    public void removeMap(View view) {
        moveMarkerView.removeFromMap();
    }


    public void moveToCameraAndDrawLine() {


        // 获取轨迹坐标点
        List<LatLng> points = TestDataUtils.readLatLngs();

        List<LatLng> points1 = TestDataUtils.readLatLngs1();

        //setCustomTextureList(bitmapDescriptors)
        getMapView().getMap().addPolyline(new PolylineOptions().setCustomTexture(BitmapDescriptorFactory.fromResource(R.mipmap.custtexture_smooth))
                .addAll(points)
                .useGradient(true)
                .width(30));

        //setCustomTextureList(bitmapDescriptors)
        getMapView().getMap().addPolyline(new PolylineOptions().setCustomTexture(BitmapDescriptorFactory.fromResource(R.mipmap.custtexture_slow))
                .addAll(points1)
                .useGradient(true)
                .width(30));

        LatLngBounds.Builder b = LatLngBounds.builder();
        List<LatLng> latLngList = new ArrayList<>();
        latLngList.addAll(points);
        latLngList.addAll(points1);
        for (int i = 0; i < latLngList.size(); i++) {
            b.include(latLngList.get(i));
        }
        LatLngBounds bounds = b.build();
        getMapView().getMap().animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 100));
    }
}
