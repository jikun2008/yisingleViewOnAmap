package com.yisingle.amapview.lib.base.view.polyline;

import android.content.Context;

import com.amap.api.maps.AMap;
import com.amap.api.maps.model.BitmapDescriptor;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Polyline;
import com.amap.api.maps.model.PolylineOptions;
import com.yisingle.amapview.lib.base.BaseView;

import java.util.List;

/**
 * @author jikun
 * Created by jikun on 2018/5/10.
 */
public abstract class AbstractPolyLineView extends BaseView {

    protected Polyline polyline;

    protected PolylineOptions polylineOptions;

    public AbstractPolyLineView(Context context, AMap amap) {
        super(context, amap);
    }


    /**
     * 设置线段的颜色
     *
     * @param color 颜色的ARGB格式
     */
    public void setColor(int color) {
        if (null != polyline) {
            polyline.setColor(color);
        }
    }


    /**
     * 设置线段的自定义纹理
     *
     * @param customTexture 纹理图片 BitmapDescriptor
     */
    public void setCustomTexture(BitmapDescriptor customTexture) {
        if (null != polyline) {
            polyline.setCustomTexture(customTexture);
        }
    }

    /**
     * 设置线段是否虚线，默认为false，画实线。
     *
     * @param isDottedLine - true，画虚线；false，画实线。
     */
    public void setDottedLine(boolean isDottedLine) {
        if (null != polyline) {
            polyline.setDottedLine(isDottedLine);
        }
    }

    /**
     * 设置线段是否画大地曲线，默认false，不画大地曲线。
     *
     * @param isGeodesic- true：画大地曲线；false：不画大地曲线。
     */
    public void setGeodesic(boolean isGeodesic) {
        if (null != polyline) {
            polyline.setGeodesic(isGeodesic);
        }
    }

    /**
     * 设置线段选项信息
     * 设置多个属性时可以使用此方法,单个属性设置建议使用各自对应的方法。
     *
     * @param options - 线段选项信息 PolylineOptions
     */
    public void setOptions(PolylineOptions options) {
        if (null != polyline) {
            this.polylineOptions = polylineOptions;
            polyline.setOptions(options);
        }
    }


    /**
     * 设置线段的坐标点列表。如果调用此方法后修改了List的值将不会影响到线段。
     *
     * @param points - 线段顶点的坐标列表
     */
    public void setPoints(List<LatLng> points) {
        if (null != polyline) {
            polyline.setPoints(points);
        }
    }

    /**
     * 设置线段的透明度，需要使用纹理，此方法才有效，如果只设置颜色透明度，使用color即可
     *
     * @param transparency - 透明度 范围0~1, 0为不透明
     */
    public void setTransparency(float transparency) {
        if (null != polyline) {
            polyline.setTransparency(transparency);
        }
    }

    /**
     * 设置线段的可见属性。当不可见时，线段不会被绘制。
     *
     * @param visible - true: 可见; false: 不可见。
     */
    public void setVisible(boolean visible) {
        if (null != polyline) {
            polyline.setVisible(visible);
        }
    }


    /**
     * 设置线段的宽度
     *
     * @param width - 线段宽度 单：像素
     */
    public void setWidth(float width) {
        if (null != polyline) {
            polyline.setWidth(width);
        }
    }

    /**
     * 设置线段的z轴值。
     *
     * @param zIndex- z轴值。
     */
    public void setZIndex(float zIndex) {
        if (null != polyline) {
            polyline.setZIndex(zIndex);
        }
    }


}
