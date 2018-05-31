package com.yisingle.amapview.lib.base.view.circle;

import android.content.Context;
import android.support.annotation.NonNull;

import com.amap.api.maps.AMap;
import com.amap.api.maps.model.Circle;
import com.amap.api.maps.model.CircleOptions;
import com.amap.api.maps.model.LatLng;
import com.yisingle.amapview.lib.base.BaseView;

/**
 * @author jikun
 * Created by jikun on 2018/4/26.
 */
public class SimpleCircleView extends BaseView {

    private String TAG = SimpleCircleView.class.getSimpleName();


    private Circle circle;

    private CircleOptions options;


    private SimpleCircleView(@NonNull Context context, @NonNull AMap amap) {
        super(context, amap);
    }


    @Override
    public void addToMap() {
        if (null != getAmap()) {
            if (isRemove()) {
                circle = getAmap().addCircle(options);
            }

        }

    }

    @Override
    public void removeFromMap() {
        if (null != circle) {
            circle.remove();
            circle = null;
        }


    }

    @Override
    public void destory() {
        removeFromMap();
        circle = null;
        super.destory();

    }

    @Override
    public boolean isRemove() {
        return circle == null;
    }


    public static class Builder extends BaseCircleBuilder<Builder> {

        private SimpleCircleView baseCircleView;


        public Builder(@NonNull Context context, @NonNull AMap map) {
            super(context, map);
            setT(this);
            baseCircleView = new SimpleCircleView(getContext(), getMap());
        }

        public SimpleCircleView create() {
            baseCircleView.setOptions(getCircleOptions());
            return baseCircleView;

        }
    }

    public CircleOptions getOptions() {
        return options;
    }

    public void setOptions(CircleOptions options) {
        this.options = options;
    }


    public void setCenter(LatLng center) {
        if (null != circle) {
            circle.setCenter(center);
        }
    }

    public void setFillColor(int color) {
        if (null != circle) {
            circle.setFillColor(color);
        }
    }


    public void setRadius(double radius) {
        if (null != circle) {
            circle.setRadius(radius);
        }
    }

    public void setStrokeColor(int color) {
        if (null != circle) {
            circle.setRadius(color);
        }
    }

    public void setStrokeWidth(float width) {
        if (null != circle) {
            circle.setStrokeWidth(width);
        }
    }

    public void setVisible(boolean visible) {
        if (null != circle) {
            circle.setVisible(visible);
        }
    }

    public void setZIndex(float zIndex) {
        if (null != circle) {
            circle.setZIndex(zIndex);
        }
    }
}
