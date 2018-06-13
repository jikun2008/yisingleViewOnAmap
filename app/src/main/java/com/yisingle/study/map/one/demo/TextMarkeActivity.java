package com.yisingle.study.map.one.demo;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.TextureMapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.yisingle.amapview.lib.view.TextMarkerView;
import com.yisingle.study.map.one.R;
import com.yisingle.study.map.one.base.BaseMapActivity;

/**
 * @author jikun
 * Created by jikun on 2018/5/7.
 */
public class TextMarkeActivity extends BaseMapActivity {
    private TextureMapView textureMapView;

    private TextMarkerView textMarkerView;

    private LatLng latLng = new LatLng(30.657457, 104.06577);


    @Override
    protected void afterMapViewLoad() {

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_maker_map);
        textureMapView = findViewById(R.id.textureMapView);
        initMapView(savedInstanceState, textureMapView);


        textMarkerView = new TextMarkerView.Builder(getApplicationContext(), getAmap())
                //是否只显示文字 默认值为false
                .setTextOnlyTextShow(false)
                .setTextPaddingLeftOrRight(20)
                .setTextRowSpaceAdd(90)
                .setTextColor(Color.parseColor("#FF4040"))
                .setTextSize(44f)
                .setText("天府广场设计费速度快放假时代峻峰")
                .setTextPointIcon(BitmapDescriptorFactory.fromResource(R.mipmap.hot_point)).create();
        moveToCamera(latLng);

        testSetPoint(null);


    }


    public void testAddMarker(View view) {
        textMarkerView.addToMap();
        textMarkerView.setPosition(latLng);
        moveToCamera(latLng);

    }


    public void testRemoveMarker(View view) {
        textMarkerView.removeFromMap();


    }

    public void testChangeText(View view) {
        if (textMarkerView.getText().equals("春熙路春熙路")) {
            textMarkerView.setText("天府广场设计费速度快放假时代峻峰");
        } else {
            textMarkerView.setText("春熙路春熙路");
        }

    }


    public void testSetPoint(View view) {
        if (null != textMarkerView.getPosition() && textMarkerView.getPosition().equals(latLng)) {
            textMarkerView.setPosition(new LatLng(30.65832, 104.065936));
        } else {
            textMarkerView.setPosition(latLng);
        }
    }


    public void showOrHideMarker(View view) {
        boolean isShow = !textMarkerView.isVisible();
        textMarkerView.setVisible(isShow);
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


    @Override
    public void onDestroy() {
        super.onDestroy();
        textMarkerView.destory();


    }
}
