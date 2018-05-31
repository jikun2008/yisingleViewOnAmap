package com.yisingle.amapview.lib.base.view.circle;

import android.content.Context;
import android.support.annotation.NonNull;

import com.amap.api.maps.AMap;
import com.amap.api.maps.model.CircleOptions;
import com.yisingle.amapview.lib.base.BaseBuilder;
import com.yisingle.amapview.lib.utils.AMapOptionsUtils;

/**
 * @author jikun
 */
public abstract class BaseCircleBuilder<T> extends BaseBuilder {


    private T t;
    /**
     * 设置一些默认的CircleOptions的值
     */
    private CircleOptions circleOptions = AMapOptionsUtils.getDefaultCircleOptions();


    public BaseCircleBuilder(@NonNull Context context, @NonNull AMap map) {
        super(context, map);
    }


    public void setT(T t) {
        this.t = t;
    }

    /**
     * 设置圆的填充颜色。填充颜色是绘制边框以内部分的颜色，ARGB格式。默认透明。
     *
     * @param color - 填充颜色ARGB格式
     */
    public T setCircleFillColor(int color) {
        circleOptions.fillColor(color);
        return t;
    }


    /**
     * 设置圆的半径，单位米。半径必须大于等于0。
     *
     * @param radius - 半径，单位米
     * @return
     */
    public T setCircleRadius(double radius) {
        circleOptions.radius(radius);
        return t;
    }


    /**
     * 设置圆的边框颜色，ARGB格式。如果设置透明，则边框不会被绘制。默认黑色。
     *
     * @param color - 设置边框颜色，ARGB格式。
     * @return
     */
    public T setCircleStrokeColor(int color) {
        circleOptions.strokeColor(color);
        return t;
    }


    /**
     * 设置圆的边框宽度，单位像素。参数必须大于等于0，默认10。
     *
     * @param width 边框宽度，单位像素
     * @return
     */
    public T setCircleStrokeWidth(float width) {
        circleOptions.strokeWidth(width);
        return t;
    }

    /**
     * 设置圆的可见属性
     *
     * @param visible true为可见，false为不可见
     * @return
     */
    public T setCircleVisible(boolean visible) {
        circleOptions.visible(visible);
        return t;

    }

    /**
     * 设置圆的Z轴数值，默认为0。
     *
     * @param zIndex zIndex - z轴数值
     * @return
     */
    public T setCircleZindex(float zIndex) {

        circleOptions.zIndex(zIndex);
        return t;

    }


    public CircleOptions getCircleOptions() {
        return circleOptions;
    }

    public void setCircleOptions(CircleOptions circleOptions) {
        this.circleOptions = circleOptions;
    }
}