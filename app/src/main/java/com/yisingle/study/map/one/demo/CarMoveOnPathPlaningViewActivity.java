package com.yisingle.study.map.one.demo;

import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.TextureMapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.LatLngBounds;
import com.yisingle.amapview.lib.base.view.marker.BaseMarkerView;
import com.yisingle.amapview.lib.utils.move.MovePathPlanningUtils;
import com.yisingle.amapview.lib.utils.move.MovePathPlanningUtils.DistanceDurationData;
import com.yisingle.amapview.lib.view.CarMoveOnPathPlaningView;
import com.yisingle.amapview.lib.view.PathPlaningView;
import com.yisingle.amapview.lib.view.PointMarkerView;
import com.yisingle.amapview.lib.viewholder.MapInfoWindowViewHolder;
import com.yisingle.study.map.one.R;
import com.yisingle.study.map.one.TestDataUtils;
import com.yisingle.study.map.one.base.BaseMapActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * @author jikun
 * Created by jikun on 2018/7/5.
 */
public class CarMoveOnPathPlaningViewActivity extends BaseMapActivity {


    private TextureMapView textureMapView;

    private CarMoveOnPathPlaningView<DistanceDurationData, String, String> carMoveOnLineViewGroup;

    private List<LatLng> nowListPoints = TestDataUtils.readLatLngscarMove();

    private static Handler handler;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_didi_detail);
        textureMapView = findViewById(R.id.textureMapView);
        initMapView(savedInstanceState, textureMapView);


    }

    @Override
    protected void afterMapViewLoad() {

        carMoveOnLineViewGroup = new CarMoveOnPathPlaningView.Builder(getApplicationContext(), getAmap())
                .setPathPlaningViewBuilder(new PathPlaningView.Builder(getApplicationContext(), getAmap())

                        .setEndMarkBuilder(new PointMarkerView.Builder(getApplicationContext(), getAmap())
                                .setIcon(BitmapDescriptorFactory.fromResource(R.mipmap.amap_end))
                        )
                )
                .create();


        carMoveOnLineViewGroup.bindMoveCarInfoWindowView(new BaseMarkerView.BaseInfoWindowView<DistanceDurationData>(R.layout.info_window, null) {
            @Override
            public void bindData(MapInfoWindowViewHolder viewHolder, DistanceDurationData data) {
                Log.e("测试代码", "测试代码Thread==" + Thread.currentThread().getName());
                viewHolder.setText(R.id.tvInfoWindow, "距离=" + data.getDistance() + "时间=" + data.getDuration());

            }
        });

        carMoveOnLineViewGroup.setListener(new MovePathPlanningUtils.OnDistanceDurationListener() {
            @Override
            public void onDataCallBack(MovePathPlanningUtils.DistanceDurationData data) {
                Log.e("测试代码", "测试代码-----onDataCallBack");

                carMoveOnLineViewGroup.showMoveCarInfoWindow(data);
            }

            @Override
            public void onDriverRouteSuccess() {
                Log.e("测试代码", "测试代码-----onDriverRouteSuccess----Rect");
                LatLng carLatLng = carMoveOnLineViewGroup.getCarMoveMarkerView().getPosition();

                Rect rect = new Rect(carMoveOnLineViewGroup.getCameraPaddingLeft(), carMoveOnLineViewGroup.getCameraPaddingTop(), carMoveOnLineViewGroup.getCameraPaddingRight(), carMoveOnLineViewGroup.getCameraPaddingBottom());
                moveToCamera(carLatLng, carMoveOnLineViewGroup.getEndLatlng(), rect);
            }
        });
        moveCamre(nowListPoints);


//        List<LatLng> list = new ArrayList<>();
////        list.add(new LatLng(30.55184472222222, 104.06796444444444));
//        list.addAll(nowListPoints);
//
//
//        carMoveOnLineViewGroup.startMove(list, new LatLng(30.569049, 103.928406));


        index = 0;
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (index < nowListPoints.size()) {
                    LatLng latLng = nowListPoints.get(index);
                    List<LatLng> list = new ArrayList<>();
                    list.add(latLng);
                    carMoveOnLineViewGroup.startMove(list, 30);
                }
                index = index + 1;
                sendEmptyMessageDelayed(0, 50);

            }
        };

        handler.sendEmptyMessage(0);


    }

    int index = 0;

    private void moveCamre(List<LatLng> latLngList) {
        LatLngBounds.Builder b = LatLngBounds.builder();
        for (int i = 0; i < latLngList.size(); i++) {
            b.include(latLngList.get(i));
        }
        LatLngBounds bounds = b.build();
        getMapView().getMap().animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 100));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
        handler = null;
        carMoveOnLineViewGroup.destory();

    }

    public void test(View view) {
        //carMoveOnLineViewGroup.showMoveCarInfoWindow(new MovePathPlanningUtils.DistanceDurationData(1000,100));
    }

    public void showOther(View view) {
        List<LatLng> list = new ArrayList<>();
        list.add(new LatLng(30.55184472222222, 104.06796444444444));
        carMoveOnLineViewGroup.startMove(list, 300);
    }
}
