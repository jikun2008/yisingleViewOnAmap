package com.yisingle.study.map.one.demo;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.TextureMapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.yisingle.amapview.lib.param.TextMarkerParam;
import com.yisingle.amapview.lib.view.TextMarkerView;
import com.yisingle.study.map.one.R;
import com.yisingle.study.map.one.base.BaseMapActivity;

/**
 * @author jikun
 * Created by jikun on 2018/5/7.
 */
@SuppressWarnings("ALL")
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
                //设置文字的TextPaint
                //.setTextPaint(new TextPaint())
                //设置文字
                .setText("王王王王王123")
                //设置文字距离左边或者右边的距离 默认为10
                .setTextPaddingLeftOrRight(20)
                //设置文字间的行距是字体多少倍 默认是1倍
                .setTextRowSpaceMult(2)
                //设置行距是多少  默认是0px
                //.setTextRowSpaceAdd(90)
                //设置单行文字最大字数 默认是6
                .setTextMaxTextLength(12)
                //是否只显示文字  默认值为false
                .setTextPointShow(false)
                //设置Ponit显示的图片
                .setTextPointIcon(BitmapDescriptorFactory.fromResource(R.mipmap.point_test_horizontal))
//                .setTextPointIcon(BitmapDescriptorFactory.fromResource(R.mipmap.hot_point))
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
                .create();
        moveToCamera(latLng);

        testSetPoint(null);


    }


    public void testChangeText(View view) {

        Log.e("测试代码", "测试代码U" + textMarkerView.getMarker().getAnchorU());
        Log.e("测试代码", "测试代码V" + textMarkerView.getMarker().getAnchorV());
        if ("王王王王王".equals(textMarkerView.getText())) {
            textMarkerView.setText("王王王王王123");
        } else {
            textMarkerView.setText("王王王王王");
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
