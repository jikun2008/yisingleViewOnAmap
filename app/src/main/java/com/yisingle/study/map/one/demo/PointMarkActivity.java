package com.yisingle.study.map.one.demo;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.TextureMapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.yisingle.amapview.lib.base.view.marker.BaseMarkerView;
import com.yisingle.amapview.lib.param.TextMarkerParam;
import com.yisingle.amapview.lib.view.PointMarkerView;
import com.yisingle.amapview.lib.viewholder.MapInfoWindowViewHolder;
import com.yisingle.study.map.one.R;
import com.yisingle.study.map.one.base.BaseMapActivity;

/**
 * @author jikun
 * Created by jikun on 2018/5/16.
 */
public class PointMarkActivity extends BaseMapActivity {
    private TextureMapView textureMapView;


    private PointMarkerView<String> endPointMarkerView;

    private LatLng latLng = new LatLng(30.65769, 104.062388);


    private LatLng latLng1 = new LatLng(30.657792, 104.064759);


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startend_maker_map);
        textureMapView = findViewById(R.id.textureMapView);
        initMapView(savedInstanceState, textureMapView);


        endPointMarkerView = new PointMarkerView.Builder(getApplicationContext(), getAmap())

                //设置Marker覆盖物的透明度 alpha 透明度 alpha - 透明度范围[0,1] 1为不透明
                .setAlpha(1f)

                //设置Marker覆盖物的锚点比例。锚点是marker 图标接触地图平面的点。图标的左顶点为（0,0）点，右底点为（1,1）点。
                .setAnchor(0.5f, 1)

                //设置Marker覆盖物是否可拖拽。
                .setDraggable(false)

                //设置Marker覆盖物的图标
                .setIcon(BitmapDescriptorFactory.fromResource(R.mipmap.point_test))

                //设置Marker覆盖物的动画帧图标列表，多张图片模拟gif的效果。
                //.setIcons()

                //设置多少帧刷新一次图片资源，Marker动画的间隔时间，值越小动画越快。 帧数， 刷新周期，值越小速度越快。默认为20，最小为1。
                //.setPeriod(1)

                //设置Marker覆盖物的位置坐标。Marker经纬度坐标不能为Null，坐标无默认值
                //.setPosition()

                //设置Marker覆盖物的图片旋转角度，从正北开始，逆时针计算。
                //.setRotateAngle

                //设置Marker覆盖物是否平贴地图。
                //setFlat(true)


                //设置Marker覆盖物是否可见。
                .setVisible(true)

                //设置Marker覆盖物 zIndex。
                .setZindex(0f)

                //下面面是text的一些信息
                //.setTextMarkerBuilder()
                //设置文字
                .setText("天府广场天府广场天府广场天府广场天府广场天府广场")
                //设置文字距离左边或者右边的距离 默认为10
                .setTextPaddingLeftOrRight(20)
                //设置文字间的行距是字体多少倍 默认是1倍
                .setTextRowSpaceMult(2)
                //设置行距是多少  默认是0px
                .setTextRowSpaceAdd(90)
                //设置单行文字最大字数 默认是6
                .setTextMaxTextLength(6)
                //是否只显示文字  默认值为false
                .setTextOnlyTextShow(false)
                //设置Ponit显示的图片
                .setTextPointIcon(BitmapDescriptorFactory.fromResource(R.mipmap.hot_point))
                //设置文字描边的范围 默认是6
                .setTextStrokeWidth(20)
                //设置文字描边的字体颜色值 默认是 Color.parseColor("#FFFFFF")
                .setTextStrokeColor(Color.parseColor("#FFFFFF"))
                //设置文字是在居左 居中 居右  默认是居中
                .setTextAlign(TextMarkerParam.TextAlign.LEFT)
                //设置文字大小 默认值24 也会设置描边的字体大小
                .setTextSize(44f)
                //设置文字字体的颜色值 默认是Color.parseColor("#00C3A6")
                .setTextColor(Color.parseColor("#FF4040"))
                //上面是text的一些信息


                .create();


        endPointMarkerView.setInfoWindowView(new BaseMarkerView.InfoWindowView<String>(R.layout.info_window1, "结束") {
            @Override
            public void bindData(MapInfoWindowViewHolder viewHolder, String data) {
                viewHolder.setText(R.id.tvInfoWindow1, data);
            }
        });


        endPointMarkerView.showInfoWindow("结束");


        moveToCamera(latLng);

        testSetPoint(null);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    @Override
    protected void afterMapViewLoad() {

    }


    public void testSetPoint(View view) {
        if (null != endPointMarkerView.getPosition() && endPointMarkerView.getPosition().equals(latLng)) {
            endPointMarkerView.setPosition(latLng1);
            moveToCamera(latLng1);
        } else {
            endPointMarkerView.setPosition(latLng);
            moveToCamera(latLng);
        }


    }


    int j = 0;

    public void testChangeText(View view) {
        j = j + 1;
        endPointMarkerView.setText("修改文字大小" + j);
    }

    int i = 0;

    public void testShowInfoWindow(View veiw) {

        i = i + 1;
        endPointMarkerView.showInfoWindow("End" + i);
    }


    private void moveToCamera(LatLng latLng) {
        //设置缩放级别
        float zoom = 15;
        if (null != getAmap()) {
            //zoom - 缩放级别，[3-20]。


            getAmap().animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));
        }

    }

}
