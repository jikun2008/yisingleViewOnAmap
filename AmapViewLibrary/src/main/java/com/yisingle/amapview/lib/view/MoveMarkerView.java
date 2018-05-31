package com.yisingle.amapview.lib.view;

import android.content.Context;
import android.support.annotation.NonNull;

import com.amap.api.maps.AMap;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.utils.overlay.SmoothMoveMarker;
import com.yisingle.amapview.lib.base.view.marker.BaseMarkerBuilder;
import com.yisingle.amapview.lib.base.view.marker.BaseMarkerView;
import com.yisingle.amapview.lib.param.MoveMarkerParam;

import java.util.List;

/**
 * @author jikun
 * Created by jikun on 2018/4/27.
 */
public class MoveMarkerView<W> extends BaseMarkerView<MoveMarkerParam, W> {

    private SmoothMoveMarker smoothMoveMarker;

    private MoveMarkerView(@NonNull Context context, @NonNull AMap amap, @NonNull MoveMarkerParam param) {
        super(context, amap, param);
    }


    @Override
    public void addToMap() {
        if (isRemove()) {
            smoothMoveMarker = new SmoothMoveMarker(getAmap());
            smoothMoveMarker.setDescriptor(getParam().getOptions().getIcon());
            smoothMoveMarker.setTotalDuration(getParam().getTotalDuration());
        }


    }

    @Override
    public void removeFromMap() {
        super.removeFromMap();
        if (null != smoothMoveMarker) {
            smoothMoveMarker.stopMove();
            smoothMoveMarker.removeMarker();
            smoothMoveMarker = null;
        }
    }

    @Override
    public void setVisible(boolean isVisible) {
        super.setVisible(isVisible);
        smoothMoveMarker.setVisible(isVisible);
    }

    @Override
    public void destory() {
        removeFromMap();
        smoothMoveMarker = null;
        super.destory();
    }

    @Override
    public boolean isRemove() {
        return smoothMoveMarker == null;
    }

    public void startMove() {
        startMove(getParam().getLatLngList(), getParam().getTotalDuration());
    }

    public void startMove(List<LatLng> list, int duration) {
        getParam().setLatLngList(list);
        getParam().setTotalDuration(duration);
        if (null != smoothMoveMarker) {
            smoothMoveMarker.stopMove();
            smoothMoveMarker.setPoints(list);
            smoothMoveMarker.setTotalDuration(duration);
            smoothMoveMarker.startSmoothMove();
        }

    }

    public void stopMove() {
        if (null != smoothMoveMarker) {
            smoothMoveMarker.stopMove();

        }
    }


    public static final class Builder extends BaseMarkerBuilder<Builder, MoveMarkerParam> {


        public Builder(@NonNull Context context, @NonNull AMap map) {
            super(context, map);
        }

        @Override
        protected MoveMarkerParam returnDefaultParam() {
            return new MoveMarkerParam();
        }


        @Override
        protected Builder getChild() {
            return this;
        }

        @Override
        public <W> MoveMarkerView<W> create() {
            MoveMarkerView<W> moveMarkerView = new MoveMarkerView<>(getContext(), getMap(), getParam());
            moveMarkerView.addToMap();
            return moveMarkerView;
        }


        public Builder setLatLngList(List<LatLng> latLngList) {
            getParam().setLatLngList(latLngList);
            return this;
        }


        public Builder setTotalDuration(int totalDuration) {
            getParam().setTotalDuration(totalDuration);
            return this;
        }


    }


}
