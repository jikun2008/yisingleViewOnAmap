package com.yisingle.study.map.one.demo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.TextureMapView;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.LatLngBounds;
import com.yisingle.amapview.lib.base.view.marker.BaseMarkerView;
import com.yisingle.amapview.lib.utils.move.MovePathPlanningUtils;
import com.yisingle.amapview.lib.utils.move.MovePathPlanningUtils.DistanceDurationData;
import com.yisingle.amapview.lib.view.CarMoveOnPathPlaningView;
import com.yisingle.amapview.lib.viewholder.MapInfoWindowViewHolder;
import com.yisingle.study.map.one.R;
import com.yisingle.study.map.one.TestDataUtils;
import com.yisingle.study.map.one.base.BaseMapActivity;

import java.util.List;

/**
 * @author jikun
 * Created by jikun on 2018/7/5.
 */
public class CarMoveOnPathPlaningViewActivity extends BaseMapActivity {


    private TextureMapView textureMapView;

    private CarMoveOnPathPlaningView<DistanceDurationData, String, String> carMoveOnLineViewGroup;

    private List<LatLng> nowListPoints = TestDataUtils.readLatLngscarMove();


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
                .create();


        carMoveOnLineViewGroup.bingMoveCarInfoWindowView(new BaseMarkerView.BaseInfoWindowView<DistanceDurationData>(R.layout.info_window, null) {
            @Override
            public void bindData(MapInfoWindowViewHolder viewHolder, DistanceDurationData data) {
                Log.e("测试代码","测试代码Thread=="+Thread.currentThread().getName());
                viewHolder.setText(R.id.tvInfoWindow, "距离=" + data.getDistance() + "时间=" + data.getDuration());

            }
        });

        carMoveOnLineViewGroup.setListener(new MovePathPlanningUtils.OnDistanceDurationListener() {
            @Override
            public void onDataCallBack(MovePathPlanningUtils.DistanceDurationData data) {
                carMoveOnLineViewGroup.showMoveCarInfoWindow(data);
            }
        });
        moveCamre(nowListPoints);
        carMoveOnLineViewGroup.startMove(nowListPoints, new LatLng(30.657616, 104.06625));


    }

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
        carMoveOnLineViewGroup.destory();
    }

    public void test(View view) {

    }
}
