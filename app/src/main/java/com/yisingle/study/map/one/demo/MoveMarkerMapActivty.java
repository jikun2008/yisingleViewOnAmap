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


    private LatLng latLng1 = new LatLng(30.554871, 104.068827);


    private LatLng latLng2 = new LatLng(30.555305, 104.069047);


    private LatLng latLng3 = new LatLng(30.556141, 104.069005);


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
                .setTotalDuration(10000)
                .setIcon(BitmapDescriptorFactory.fromResource(R.mipmap.car)).create();


        List<LatLng> latLngList = TestDataUtils.readLatLngsnow();

        moveCamre(latLngList);

        moveMarkerView.setLatLngList(latLngList);

        moveMarkerView.startMove();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        moveMarkerView.destory();
    }


    public void startMove(View view) {


        List<LatLng> latLngList = TestDataUtils.readLatLngsnow();
        moveCamre(latLngList);

        moveMarkerView.setLatLngList(latLngList);
        moveMarkerView.startMove();
    }

    public void stopMove(View view) {


        moveMarkerView.stopMove();
    }

    public void resumeMove(View view) {

        moveCamre(TestDataUtils.readLatLngsAll());
        moveMarkerView.startMove(TestDataUtils.readLatLngsresume(), true);

    }


    public void startOtherMove(View view) {

        List<LatLng> latLngList = TestDataUtils.readLatLngsresume();
        moveCamre(latLngList);
        moveMarkerView.setLatLngList(latLngList);
        moveMarkerView.startMove();


    }


    public void moveToCameraAndDrawLine() {


        // 获取轨迹坐标点
        List<LatLng> points = TestDataUtils.readLatLngsnow();

        List<LatLng> points1 = TestDataUtils.readLatLngsresume();

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


    private void moveCamre(List<LatLng> latLngList) {
        LatLngBounds.Builder b = LatLngBounds.builder();
        for (int i = 0; i < latLngList.size(); i++) {
            b.include(latLngList.get(i));
        }
        LatLngBounds bounds = b.build();
        getMapView().getMap().animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 100));
    }
}
