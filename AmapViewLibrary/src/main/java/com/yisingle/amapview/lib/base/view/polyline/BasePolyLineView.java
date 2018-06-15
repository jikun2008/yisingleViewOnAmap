package com.yisingle.amapview.lib.base.view.polyline;

import android.content.Context;
import android.support.annotation.NonNull;

import com.amap.api.maps.AMap;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Polyline;
import com.amap.api.maps.model.PolylineOptions;

import java.util.List;


/**
 * @author jikun
 * Created by jikun on 2018/5/10.
 */
public class BasePolyLineView extends AbstractPolyLineView {


    private BasePolyLineView(@NonNull Context context, @NonNull AMap amap) {
        super(context, amap);
    }

    @Override
    public void addToMap() {
        if (null != getAmap()) {
            if (isRemove()) {
                polyline = getAmap().addPolyline(polylineOptions);
            }

        }
    }

    @Override
    public boolean isRemove() {
        return polyline == null;
    }

    @Override
    public void removeFromMap() {
        if (null != polyline) {
            polyline.remove();
            polyline = null;
        }

    }

    @Override
    public void destory() {
        removeFromMap();
        polyline = null;
        super.destory();

    }


    public void draw(List<LatLng> latLngList) {
        getPolylineOptions().setPoints(latLngList);
        if (isRemove()) {
            addToMap();
        } else {
            polyline.setPoints(latLngList);
        }


    }


    public Polyline getPolyline() {
        return polyline;
    }

    public void setPolyline(Polyline polyline) {
        this.polyline = polyline;
    }

    public PolylineOptions getPolylineOptions() {
        return polylineOptions;
    }

    public void setPolylineOptions(PolylineOptions polylineOptions) {
        this.polylineOptions = polylineOptions;
    }


    public static final class Builder extends BasePolyLineBuilder<Builder> {
        private BasePolyLineView basePolyLineView;

        public Builder(@NonNull Context context, @NonNull AMap map) {
            super(context, map);
            setT(this);
            basePolyLineView = new BasePolyLineView(getContext(), getMap());
        }

        public BasePolyLineView create() {
            basePolyLineView.setPolylineOptions(getPolylineOptions());
            basePolyLineView.addToMap();
            return basePolyLineView;
        }
    }
}
