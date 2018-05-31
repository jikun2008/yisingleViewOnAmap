package com.yisingle.amapview.lib.base.view.marker;

import android.content.Context;
import android.support.annotation.NonNull;

import com.amap.api.maps.AMap;
import com.amap.api.maps.model.BitmapDescriptor;
import com.amap.api.maps.model.LatLng;
import com.yisingle.amapview.lib.base.BaseBuilder;
import com.yisingle.amapview.lib.base.param.BaseMarkerParam;

import java.util.ArrayList;

/**
 * @author jikun
 */
public abstract class BaseMarkerBuilder<T, D extends BaseMarkerParam> extends BaseBuilder {


    private T build;

    private D param;


    public BaseMarkerBuilder(@NonNull Context context, @NonNull AMap map) {
        super(context, map);

        build = getChild();
        param = returnDefaultParam();

    }

    protected abstract D returnDefaultParam();


    /**
     * 获取子类对象 并初始化参数
     *
     * @return
     */
    protected abstract T getChild();


    public D getParam() {
        return param;
    }

    public abstract <W> BaseMarkerView create();


    /**
     * 设置Marker覆盖物的透明度
     *
     * @param alpha 透明度 alpha - 透明度范围[0,1] 1为不透明
     * @return T
     */
    public T setAlpha(float alpha) {
        getParam().getOptions().alpha(alpha);
        return build;

    }

    /**
     * 设置Marker覆盖物的锚点比例。锚点是marker 图标接触地图平面的点。图标的左顶点为（0,0）点，右底点为（1,1）点。
     * 默认为（0.5,1.0）
     *
     * @param u 锚点水平范围的比例，建议传入0 到1 之间的数值
     * @param v 锚点垂直范围的比例，建议传入0 到1 之间的数值
     * @return T
     */
    public T setAnchor(float u, float v) {
        getParam().getOptions().anchor(u, v);
        return build;
    }

    /**
     * 设置Marker覆盖物是否可拖拽。
     *
     * @param enable - 一个布尔值，表示Marker是否可拖拽，true表示可拖拽，false表示不可拖拽。
     * @return T
     */
    public T setDraggable(boolean enable) {
        getParam().getOptions().draggable(enable);
        return build;
    }


    /**
     * 设置Marker覆盖物的图标。相同图案的 icon 的 Marker 最好使用同一个 BitmapDescriptor 对象以节省内存空间。
     *
     * @param icon - 图标的BitmapDescriptor对象
     * @return T
     */
    public T setIcon(BitmapDescriptor icon) {
        getParam().getOptions().icon(icon);
        return build;
    }

    /**
     * 设置Marker覆盖物的动画帧图标列表，多张图片模拟gif的效果。
     *
     * @param icons Marker的动画帧列表。
     * @return T
     */
    public T setIcons(ArrayList<BitmapDescriptor> icons) {
        getParam().getOptions().icons(icons);
        return build;
    }


    /**
     * 设置多少帧刷新一次图片资源，Marker动画的间隔时间，值越小动画越快。
     *
     * @param period- 帧数， 刷新周期，值越小速度越快。默认为20，最小为1。
     * @return T
     */
    public T setPeriod(int period) {
        getParam().getOptions().period(period);
        return build;
    }

    /**
     * 设置Marker覆盖物的位置坐标。Marker经纬度坐标不能为Null，坐标无默认值。
     *
     * @param position - 当前MarkerOptions对象的经纬度。
     * @return T
     */
    public T setPosition(LatLng position) {
        getParam().getOptions().position(position);
        return build;
    }

    /**
     * 设置Marker覆盖物的图片旋转角度，从正北开始，逆时针计算。
     *
     * @param rotate - Marker图片旋转的角度，从正北开始，逆时针计算。
     * @return T
     */
    public T setRotateAngle(float rotate) {
        getParam().getOptions().rotateAngle(rotate);
        return build;
    }

    /**
     * 设置Marker覆盖物是否平贴地图。
     *
     * @param flat 平贴地图设置为 true，面对镜头设置为 false。
     * @return T
     */
    public T setFlat(boolean flat) {
        getParam().getOptions().setFlat(flat);
        return build;
    }

    /**
     * 设置Marker覆盖物的坐标是否是Gps，默认为false。
     *
     * @param isGps- true 是Gps，false 不是Gps。
     * @return T
     */
    public T setGps(boolean isGps) {
        getParam().getOptions().setGps(isGps);
        return build;
    }

    /**
     * 设置Marker覆盖物的InfoWindow相对Marker的偏移。
     * 坐标系原点为marker的中上点，InfoWindow相对此原点的像素偏移，
     * 向左和向上上为负，向右和向下为正。
     * InfoWindow的初始位置为marker上边线与InfoWindow下边线重合，
     * 并且两者的中线在一条线上。
     *
     * @param offsetX- InfoWindow相对原点的横向像素偏移量，单位：像素。
     * @param offsetY- InfoWindow相对原点的纵向像素偏移量，单位：像素。
     * @return T
     */
    public T setInfoWindowOffset(int offsetX, int offsetY) {
        getParam().getOptions().setInfoWindowOffset(offsetX, offsetY);
        return build;
    }


    /**
     * 设置 Marker覆盖物的 文字描述
     *
     * @param snippet - Marker上的文字描述
     * @return T
     */
    public T setSnippet(String snippet) {
        getParam().getOptions().snippet(snippet);
        return build;
    }


    /**
     * 设置 Marker覆盖物 的标题
     *
     * @param title Marker 的标题
     * @return T
     */
    public T setTitle(String title) {
        getParam().getOptions().title(title);
        return build;
    }

    /**
     * 设置Marker覆盖物是否可见。
     *
     * @param visible visible - Marker的可见性。
     * @return T
     */
    public T setVisible(boolean visible) {
        getParam().getOptions().visible(visible);
        return build;
    }

    /**
     * 设置Marker覆盖物 zIndex。
     *
     * @param zIndex z轴上的值
     * @return T
     */
    public T setZindex(float zIndex) {
        getParam().getOptions().zIndex(zIndex);
        return build;
    }


}