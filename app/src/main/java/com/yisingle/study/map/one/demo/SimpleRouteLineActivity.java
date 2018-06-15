package com.yisingle.study.map.one.demo;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.amap.api.maps.TextureMapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.yisingle.amapview.lib.view.RouteLineView;
import com.yisingle.study.map.one.R;
import com.yisingle.study.map.one.base.BaseMapActivity;

/**
 * @author jikun
 * Created by jikun on 2018/6/15.
 */
public class SimpleRouteLineActivity extends BaseMapActivity {

    private TextureMapView textureMapView;


    private RouteLineView simpleRouteLineView;

    @Override
    protected void afterMapViewLoad() {
        simpleRouteLineView = new RouteLineView.Builder(getApplicationContext(), getAmap())
                //设置路线箭头图片
                .setArrowRouteBitMap(BitmapDescriptorFactory.fromResource(com.yisingle.amap.lib.R.mipmap.custtexture_arrow))

                /**
                 * setTrafficshow(false)设置是否显示有交通状态路线的路线 默认是true
                 * 如果要显示交通路线状态  还需要传入 DriverPath 这个类
                 * DriverPath 要根据高德SDK路径规划接口来取得的
                 *  这个会影响以下方法
                 setTrafficUnknownRouteBitMap 设置未知交通状态路线图片
                 setTrafficSmoothRouteBitMap  设置顺畅交通状态路线图片 一般是绿色图片
                 setTrafficSlowRouteBitMap    设置缓慢交通状态路线图片 一般是黄色图片
                 setTrafficJamRouteBitMap     设置拥堵交通状态路线图片 一般是红色图片
                 setTrafficVeryJamRouteBitMap 设置非常拥堵交通状态路线图片 一般是非常红图片
                 *
                 */
                .setTrafficshow(false)

                //设置未知交通状态路线图片
                .setTrafficUnknownRouteBitMap(BitmapDescriptorFactory.fromResource(com.yisingle.amap.lib.R.mipmap.custtexture_unknown))

                //设置顺畅交通状态路线图片 一般是绿色图片
                .setTrafficSmoothRouteBitMap(BitmapDescriptorFactory.fromResource(com.yisingle.amap.lib.R.mipmap.custtexture_smooth))

                //设置缓慢交通状态路线图片 一般是黄色图片
                .setTrafficSlowRouteBitMap(BitmapDescriptorFactory.fromResource(com.yisingle.amap.lib.R.mipmap.custtexture_slow))

                //设置拥堵交通状态路线图片 一般是红色图片
                .setTrafficJamRouteBitMap(BitmapDescriptorFactory.fromResource(com.yisingle.amap.lib.R.mipmap.custtexture_jam))

                //设置非常拥堵交通状态路线图片 一般是非常红图片
                .setTrafficVeryJamRouteBitMap(BitmapDescriptorFactory.fromResource(com.yisingle.amap.lib.R.mipmap.custtexture_jam))
                .create();

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_routeline);
        textureMapView = findViewById(R.id.textureMapView);
        initMapView(savedInstanceState, textureMapView);
    }
}
