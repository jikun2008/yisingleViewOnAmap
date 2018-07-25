package com.yisingle.study.map.one.demo;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.TextureMapView;
import com.amap.api.maps.model.BitmapDescriptor;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.yisingle.amapview.lib.utils.ViewToBitMapUtil;
import com.yisingle.study.map.one.R;
import com.yisingle.study.map.one.base.BaseMapActivity;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author jikun
 * Created by jikun on 2018/7/24.
 */
public class TestMemoryActivity extends BaseMapActivity {

    private TextureMapView textureMapView;

    private Marker marker;
    private Marker recycleMarker;

    private ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
            1,
            1,
            50,
            TimeUnit.SECONDS,
            new ArrayBlockingQueue<Runnable>(5), new ThreadPoolExecutor.DiscardOldestPolicy());

    @Override
    protected void afterMapViewLoad() {
        marker = getAmap().addMarker(new MarkerOptions().position(new LatLng(30.554563, 104.06831)));
        moveCamre(new LatLng(30.554563, 104.06831));

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_didi_detail);
        textureMapView = findViewById(R.id.textureMapView);
        initMapView(savedInstanceState, textureMapView);
    }

    private void moveCamre(LatLng latLng) {

        getAmap().animateCamera(CameraUpdateFactory.newLatLng(latLng));
    }

    private int i = 0;

    public void test(View view) {


        i = i + 1;
        threadPoolExecutor.execute(new Runnable() {
            @Override
            public void run() {


                bindView();


            }
        });

    }


    private void bindView() {


        View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.info_window, null);
        TextView textView = view.findViewById(R.id.tvInfoWindow);
        textView.setText(i + "----");


        Bitmap bitmap = ViewToBitMapUtil.convertViewToBitmap(view);
        Log.e("测试代码", "测试代码" + i + "---bitmap=" + bitmap);


        BitmapDescriptor bitmapDescriptor = BitmapDescriptorFactory.fromBitmap(bitmap);
        marker.setIcon(bitmapDescriptor);

        test(null);


    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        threadPoolExecutor.shutdown();
    }
}
