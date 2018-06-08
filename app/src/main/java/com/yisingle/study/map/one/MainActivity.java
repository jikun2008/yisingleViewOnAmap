package com.yisingle.study.map.one;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.route.BusRouteResult;
import com.amap.api.services.route.DriveRouteResult;
import com.amap.api.services.route.RideRouteResult;
import com.amap.api.services.route.RouteSearch;
import com.amap.api.services.route.WalkRouteResult;
import com.yisingle.amapview.lib.utils.GaoDeErrorUtils;
import com.yisingle.study.map.one.demo.LocationMarkerActivity;
import com.yisingle.study.map.one.demo.PointMarkUseMoveActivty;
import com.yisingle.study.map.one.demo.PathPlaningActivity;
import com.yisingle.study.map.one.demo.PointMarkActivity;
import com.yisingle.study.map.one.demo.TextMarkeActivity;
import com.yisingle.study.map.one.demo.ThreadActivity;


public class MainActivity extends AppCompatActivity {

    private RouteSearch routeSearch;

    private TextView tvInfo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvInfo = findViewById(R.id.tvInfo);
        routeSearch = new RouteSearch(this);


        routeSearch.setRouteSearchListener(new RouteSearch.OnRouteSearchListener() {
            @Override
            public void onBusRouteSearched(BusRouteResult busRouteResult, int i) {

            }

            @Override
            public void onDriveRouteSearched(DriveRouteResult driveRouteResult, int i) {


                tvInfo.setText(GaoDeErrorUtils.getErrorInfo(i));

            }

            @Override
            public void onWalkRouteSearched(WalkRouteResult walkRouteResult, int i) {

            }

            @Override
            public void onRideRouteSearched(RideRouteResult rideRouteResult, int i) {

            }
        });
        startConfimGaode();


    }

    public void startConfimGaode() {
        LatLonPoint from = new LatLonPoint(30.537107, 104.06951);
        LatLonPoint to = new LatLonPoint(30.657349, 104.065837);
        RouteSearch.FromAndTo fromAndTo = new RouteSearch.FromAndTo(from, to);
        RouteSearch.DriveRouteQuery query = new RouteSearch.DriveRouteQuery(fromAndTo, RouteSearch.DRIVING_SINGLE_SHORTEST, null, null, "");
        routeSearch.calculateDriveRouteAsyn(query);
        tvInfo.setText("使用地图的路径规划中");
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public void test(View view) {
        startConfimGaode();
    }


    public void toLocationMarkerMapActivity(View view) {
        Intent intent = new Intent();
        intent.setClass(this, LocationMarkerActivity.class);
        startActivity(intent);
    }

    public void toMoveMarkerMapActivty(View view) {
        Intent intent = new Intent();
        intent.setClass(this, PointMarkUseMoveActivty.class);
        startActivity(intent);
    }


    public void toNearByMarkeActivity(View view) {
        Intent intent = new Intent();
        intent.setClass(this, TextMarkeActivity.class);
        startActivity(intent);
    }

    public void toStartEndActivity(View view) {
        Intent intent = new Intent();
        intent.setClass(this, PointMarkActivity.class);
        startActivity(intent);
    }

    public void toPathPlaningActivity(View view) {
        Intent intent = new Intent();
        intent.setClass(this, PathPlaningActivity.class);
        startActivity(intent);
    }

    public void toThreadActivity(View view) {
        Intent intent = new Intent();
        intent.setClass(this, ThreadActivity.class);
        startActivity(intent);
    }


}
