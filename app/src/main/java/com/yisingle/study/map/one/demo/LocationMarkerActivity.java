package com.yisingle.study.map.one.demo;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.TextureMapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.yisingle.amapview.lib.view.LocationMarkerView;
import com.yisingle.study.map.one.R;
import com.yisingle.study.map.one.base.BaseMapActivity;

/**
 * @author jikun
 * Created by jikun on 2018/4/11.
 */
public class LocationMarkerActivity extends BaseMapActivity {

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
                        //设置定位的时间间隔 默认为5000ms  1000=1S
                        .setLocationDurtion(6000)
                        //设置有方向的定位图片
                        .setIcon(BitmapDescriptorFactory.fromResource(com.yisingle.amap.lib.R.mipmap.navi_map_gps_locked))
                        //设置没有方向传感器的时候的显示的图片 也可以不设置则使用有方向定位的图片
                        .setWithOutSensorDrawableId(com.yisingle.amap.lib.R.mipmap.gps_point)
                        //设置精度圆圈填充的颜色值 默认 Color.argb(40, 3, 145, 255)
                        .setCircleFillColor(Color.argb(40, 3, 145, 255))
                        //设置圆的边框颜色，ARGB格式。如果设置透明，则边框不会被绘制. 默认 Color.argb(10, 0, 0, 180)
                        .setCircleStrokeColor(Color.argb(10, 0, 0, 180))
                        //设置圆的边框宽度，单位像素。参数必须大于等于0，默认1f。
                        .setCircleStrokeWidth(1f)
                        //设置圆的可见属性 默认可见
                        .setCircleVisible(true)
                        //设置圆的Z轴数值，默认为0。
                        .setCircleZindex(0f)
                        .create();


        //设置locationMarkerView的监听器
        locationMarkerView.setListener(new LocationMarkerView.onLocationMarkerViewListener() {
            @Override
            public void onLocationSuccess(AMapLocation loc) {
                //当定位成功的时候回调
                Log.e("onLocationSuccess", "onLocationSuccess" + "--ThreadName=" + Thread.currentThread().getName());
                moveToCamera(new LatLng(loc.getLatitude(), loc.getLongitude()));

            }

            @Override
            public void onLocationFailed(AMapLocation loc) {
                //当定位失败的时候回调
                Log.e("onLocationFailed", "onLocationFailed" + "--ThreadName=" + Thread.currentThread().getName());
                Toast.makeText(getApplicationContext(), loc.getErrorInfo(), Toast.LENGTH_LONG).show();


            }

            @Override
            public void onRotationSuccess(float angle) {
                //当转动手机(并且有方向传感器)的时候回调
                //这里解释一下  在一些Android手机中会有没有方向传感器或磁力传感器。这个时候无论你怎么转动手机都无法指示方向。


            }

            @Override
            public void onRotationFailed(String erroInfo) {
                //没有方向传感器的时候 回调这里
                Toast.makeText(getApplicationContext(), erroInfo, Toast.LENGTH_LONG).show();

            }
        });

        locationMarkerView.startLocation();


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
