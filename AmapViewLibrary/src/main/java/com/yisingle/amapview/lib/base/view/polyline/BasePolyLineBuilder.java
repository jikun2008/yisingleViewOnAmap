package com.yisingle.amapview.lib.base.view.polyline;

import android.content.Context;
import android.support.annotation.NonNull;

import com.amap.api.maps.AMap;
import com.amap.api.maps.model.BitmapDescriptor;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.PolylineOptions;
import com.yisingle.amapview.lib.base.BaseBuilder;

/**
 * @author jikun
 * Created by jikun on 2018/5/10.
 */
public class BasePolyLineBuilder<T> extends BaseBuilder {


    private PolylineOptions polylineOptions = new PolylineOptions();


    private T t;


    public BasePolyLineBuilder(@NonNull Context context, @NonNull AMap map) {
        super(context, map);

    }


    public T getT() {
        return t;
    }

    public void setT(T t) {
        this.t = t;
    }

    /**
     * 追加一批顶点到线段的坐标集合。
     *
     * @param points - 要添加的顶点集合。
     * @return T
     */
    public T add(LatLng... points) {
        polylineOptions.add(points);
        return t;

    }

    /**
     * 追加一个顶点到线段的坐标集合。
     *
     * @param point
     * @return T
     */
    public T add(LatLng point) {
        polylineOptions.add(point);
        return t;
    }

    /**
     * 追加一批顶点到线段的坐标集合。
     *
     * @param points points - 要添加的顶点集合。
     * @return T
     */
    public T addAll(Iterable<LatLng> points) {
        polylineOptions.addAll(points);
        return t;

    }


    /**
     * 设置线段的颜色，需要传入32位的ARGB格式。
     *
     * @param color
     * @return T
     */
    public T setColor(int color) {
        polylineOptions.color(color);
        return t;

    }


    /**
     * 设置线段的颜色
     *
     * @param colors - 颜色列表
     * @return T
     */
    public T setColorValues(java.util.List<java.lang.Integer> colors) {
        polylineOptions.colorValues(colors);
        return t;
    }


    /**
     * 设置线段是否为大地曲线，默认false，不画大地曲线。
     *
     * @param isGeodesic - 一个表示线段是否为大地曲线的布尔值，true表示是大地曲线，false表示不是大地曲线。
     * @return T
     */
    public T setGeodesic(boolean isGeodesic) {
        polylineOptions.geodesic(isGeodesic);
        return t;
    }


    /**
     * 设置线段的纹理图，图片为2的n次方。如果不是，会自动放大至2的n次方。图片最好不大于128*128。
     *
     * @param customTexture- 用户设置线段的纹理
     * @return T
     */
    public T setCustomTexture(BitmapDescriptor customTexture) {
        polylineOptions.setCustomTexture(customTexture);
        return t;
    }


    /**
     * 设置线段纹理index数组
     *
     * @param custemTextureIndexs - 每一段对应的纹理，用纹理列表的index来对应
     * @return T
     */
    public T setCustomTextureIndex(java.util.List<java.lang.Integer> custemTextureIndexs) {
        polylineOptions.setCustomTextureIndex(custemTextureIndexs);
        return t;
    }


    /**
     * 设置线段纹理list
     *
     * @param customTextureList - 纹理列表
     * @return T
     */
    public T setCustomTextureList(java.util.List<BitmapDescriptor> customTextureList) {
        polylineOptions.setCustomTextureList(customTextureList);
        return t;
    }


    /**
     * 设置是否画虚线，默认为false，画实线。
     *
     * @param isDottedLine - true，画虚线；false，画实线。
     * @return T
     */
    public T setDottedLine(boolean isDottedLine) {
        polylineOptions.setDottedLine(isDottedLine);
        return t;
    }

    /**
     * 设置虚线形状。
     *
     * @param type PolylineOptions.DOTTEDLINE_TYPE_SQUARE:方形；
     *             PolylineOptions.DOTTEDLINE_TYPE_CIRCLE：圆形；
     * @return T
     */
    public T setDottedLineType(int type) {
        polylineOptions.setDottedLineType(type);
        return t;

    }

    /**
     * 设置线段的点坐标集合,如果以前已经存在点,则会清空以前的点。
     *
     * @param points - 要设置的顶点集合
     * @return T
     */
    public T setPoints(java.util.List<LatLng> points) {
        polylineOptions.setPoints(points);
        return t;

    }

    /**
     * 设置是否使用纹理贴图画线。
     *
     * @param useTexture - true，使用纹理贴图；false，不使用。默认为使用纹理贴图画线。
     * @return T
     */
    public T setIsUseTexture(boolean useTexture) {
        polylineOptions.setUseTexture(useTexture);
        return t;
    }

    /**
     * 设置线段的透明度0~1，默认是1,1表示不透明
     *
     * @param transparency - 透明度
     * @return T
     */
    public T setTransparency(float transparency) {
        polylineOptions.transparency(transparency);
        return t;
    }


    /**
     * 设置线段是否使用渐变色
     *
     * @param useGradient 是否使用渐变色，true：使用，false不使用，默认为false；
     * @return T
     */
    public T setIsUseGradient(boolean useGradient) {
        polylineOptions.useGradient(useGradient);
        return t;
    }


    /**
     * 设置线段的可见性。默认为可見
     *
     * @param isVisible 一个表示线段是否可见的布尔值，true表示可见，false表示不可见。
     * @return T
     */
    public T seTVisible(boolean isVisible) {
        polylineOptions.visible(isVisible);
        return t;
    }


    /**
     * 设置线段的宽度，默认为10。
     *
     * @param width width - 宽度 单位：像素
     * @return T
     */
    public T setWidth(float width) {
        polylineOptions.width(width);
        return t;
    }


    /**
     * 设置线段Z轴的值。
     *
     * @param zIndex 要设置的Z轴的值。
     * @return T
     */
    public T sezIndex(float zIndex) {
        polylineOptions.zIndex(zIndex);
        return t;
    }


    public PolylineOptions getPolylineOptions() {
        return polylineOptions;
    }

    public void setPolylineOptions(PolylineOptions polylineOptions) {
        this.polylineOptions = polylineOptions;
    }
}
